package juhuh.compiler.opt.regAlloc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.*;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.ir.ins.*;
import juhuh.compiler.ir.stmt.*;
import juhuh.compiler.opt.mem2reg.*;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.*;

public class allocator implements irVisitor {
  // alloc curFunc;
  private domBuilder dom;
  public vector<live> liveStmt[];
  //
  public regCol regColor = new regCol(); // spill color it
  public int argsId = 0;
  int regIdCnt = 0;
  // tempvar bool spilled, int id;
  // inUse in here
  // decide which to spill?
  int curBlock = 0, curStmt = 0;

  @SuppressWarnings("unchecked")
  public allocator(domBuilder Dom) {
    dom = Dom;
    liveStmt = new vector[Dom.cnt];
    for (int i = 0; i < Dom.cnt; i++) {
      liveStmt[i] = new vector<live>();
    }
  }

  // liveout livein add
  @Override
  public void visit(irNode node) throws error {
    node.accept(this);
  }

  @Override
  public void visit(irRoot node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  BitSet scanned;
  private String reg;
  HashMap<String, Integer> regs;
  // q: a stmt, choose the longest reg to spill reduce the probability to spill.

  void scanPhi(irBlock node) {
    for (var stmt : node.getStmts()) {
      if (stmt instanceof irPhi) {
        var phi = (irPhi) stmt;
        for (var val : phi.getLabel2val().entrySet()) {
          if (val.getValue() != null && val.getValue().equals(reg)) {
            scanBlock(dom.id.get(val.getKey()), val.getValue());
          }
        }
      }
    }
  }

  void scanBlock(int blockId, String regName) {
    if (scanned.get(blockId)) {
      return;
    }
    scanned.set(blockId);
    int tmp1 = curBlock, tmp2 = curStmt;
    curBlock = blockId;
    curStmt = liveStmt[curBlock].size() - 1;
    scanOut();
    curBlock = tmp1;
    curStmt = tmp2;
  }

  void scanIn() {
    if (curStmt == 0) {
      // no preceding
      var setPhi = dom.id2B.get(curBlock).getPhi();
      if (setPhi != null) {

        for (var phi : setPhi.entrySet()) {
          // phi def use reg?
          if (phi.getValue().getReg().equals(reg)) {
            return;
          }
        }
      }
      liveStmt[curBlock].get(curStmt).In(reg);
      for (var pred : dom.preds[curBlock]) {
        scanBlock(pred, reg);
      }
    } else {
      liveStmt[curBlock].get(curStmt).In(reg);
      --curStmt;
      scanOut();
    }
  }

  void scanOut() {
    var def = liveStmt[curBlock].get(curStmt).def;
    liveStmt[curBlock].get(curStmt).Out(reg);
    if (def == null || !def.contains(reg)) {
      scanIn();
    }
  }

  @Override
  public void visit(irFuncDef node) throws error {
    regs = new HashMap<String, Integer>();
    visit(node.getEntry());
    for (var block : node.getBody()) {
      visit(block);
    }
    visit(node.getRet());
    scanned = new BitSet(dom.cnt);
    // begin ssa liveness analysis
    // initialization
    for (var reg1 : regs.keySet()) {
      reg = reg1;
      scanned.clear();
      for (int i = 0; i < dom.cnt; i++) {
        curBlock = i;
        var block = dom.id2B.get(i);
        // scan use for phi
        scanPhi(block);
        // scan use for specified variable
        for (int j = 0; j < liveStmt[i].size(); j++) {
          var stmt = liveStmt[i].get(j);
          if (stmt.def != null && stmt.def.contains(reg)) {
            curStmt = j;
            scanIn();
          }
        }
      }
    }

  }

  private List<HashMap.Entry<String, Integer>> sortByCost() {
    List<HashMap.Entry<String, Integer>> entryList = new ArrayList<>(regs.entrySet());
    entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
      @Override
      public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
        return e2.getValue().compareTo(e1.getValue());
      }
    });
    for (Map.Entry<String, Integer> entry : entryList) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
    return entryList;
  }

  private boolean spilled(String reg) {
    return regColor.regs.containsKey(reg);
  }

  private void spillReg() {
    regColor.addReg(reg, true);
  }

  private boolean spillPhi(int id) {
    @SuppressWarnings("unchecked")
    HashSet<String> out = (HashSet<String>) (liveStmt[id].get(0).in).clone();
    // TODO : check if [id].[0].in is phi.out
    {
      int sz = out.size();
      for (var outReg : out) {
        if (spilled(outReg))
          --sz;
      }
      if (sz > regColor.K) {
        spillReg();
        return true;
      }
    }
    return false;
  }

  public boolean spReg() {
    // spill phi
    for (int i = 0; i < dom.cnt; ++i)
      if (spillPhi(i))
        return true;
    // spill normal stmt
    for (int i = 0; i < dom.cnt; ++i) {
      for (int j = 0; j < liveStmt[i].size(); ++j) {
        var stmt = liveStmt[i].get(j);
        if (stmt.out.contains(reg)) {
          int sz = stmt.out.size();
          if (sz < regColor.K)
            continue;
          for (var outReg : stmt.out) {
            if (spilled(outReg))
              --sz;
          }
          if (sz > regColor.K) {
            spillReg();
            return true;
          }
        }
      }
    }
    return false;
  }

  public void preColor(int blockId) {
    // inUse should bit-OR in [0]in (phi.out)
    @SuppressWarnings("unchecked")
    HashSet<String> out = (HashSet<String>) (liveStmt[blockId].get(0).in).clone();
    regColor.orLiveIn(out);
    // phi def should be colored(if in [0]in)
    for (var phiuse : dom.id2B.get(blockId).getPhi().entrySet()) {
      var regSet = phiuse.getValue().getLabel2val();
      for (var reg : regSet.values())
        if (reg != null && !out.contains(reg)) {
          regColor.eraseReg(reg);
        }
    }
    // color phi def when out contains def
    for (var phiuse : dom.id2B.get(blockId).getPhi().entrySet()) {
      var def = phiuse.getValue().getReg();
      if (out.contains(def)) {
        regColor.addReg(def, false);
      }
    }
    // color simple stmt
    for (var live : liveStmt[blockId]) {
      // erase use & not in liveout
      for (var uses : live.use)
        if (!out.contains(uses)) {
          regColor.eraseReg(uses);
        }
      // color def
      for (var defs : live.def)
        if (out.contains(defs)) {
          regColor.addReg(defs, false);
        }
    }
    // precolor other
    for (var id2 : dom.ch[blockId]) {
      preColor(id2);
    }
  }

  public void spill2Col(vector<String> args) {
    // spill the > k
    argsId = Math.min(8, args.size());// notspilled count store
    List<HashMap.Entry<String, Integer>> entryList = sortByCost();
    for (Map.Entry<String, Integer> entry : entryList) {
      reg = entry.getKey();
      if (!spReg())
        break;
    }
    // col reg. dominate tree preorder.
    regColor = new regCol();
    // recolor args
    int tmpcnt = 0;
    for (var arg : args) {
      tmpcnt++;
      if(tmpcnt < 8)
        regColor.addReg(arg, false);
      else 
        regColor.addReg(arg, true);
    }
    preColor(0);
  }

  @Override
  public void visit(irGlobalDef node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irStrDef node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  private int pow(int blockId) {
    int res = 1, x = 10;
    int n = dom.loopBody[blockId];
    for (int i = 1; i <= n; ++i)
      res *= x;
    return res;
  }

  @Override
  public void visit(irBinary node) throws error {
    // def: res, use: val1, val2
    var newlive = new live();
    if (node.getOp1().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getOp1());
    }
    if (node.getOp2().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getOp2());
    }
    newlive.Def(regs, pow(curBlock), node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irIcmp node) throws error {
    //
    var newlive = new live();
    if (node.getOp1().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getOp1());
    }
    if (node.getOp2().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getOp2());
    }
    newlive.Def(regs, pow(curBlock), node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irSelect node) throws error {
    // def, use
    var newlive = new live();
    if (node.getCond().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getCond());
    }
    if (node.getVal1().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getVal1());
    }
    if (node.getVal2().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getVal2());
    }
    newlive.Def(regs, pow(curBlock), node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irBranch node) throws error {
    // cond use
    if (node.getCond().charAt(0) == '%') {
      var newLive = new live();
      newLive.Use(regs, pow(curBlock), node.getCond());
      liveStmt[curBlock].add(newLive);
    }
  }

  @Override
  public void visit(irJump node) throws error {
    // no condition
    liveStmt[curBlock].add(new live());
  }

  @Override
  public void visit(irRet node) throws error {
    // only use
    var newLive = new live();
    if (!node.getVal().equals("") && node.getVal().charAt(0) == '%') {
      newLive.Use(regs, pow(curBlock), node.getVal());
    }
    liveStmt[curBlock].add(newLive);
  }

  @Override
  public void visit(irCall node) throws error {
    var newlive = new live();
    newlive.Def(regs, pow(curBlock), node.getRes());
    for (var arg : node.getVal()) {
      if (arg.charAt(0) == '%') {
        newlive.Use(regs, pow(curBlock), arg);
      }
    }
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irAlloca node) throws error {
    // should not be here
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irGetElement node) throws error {
    //
    var newlive = new live();
    newlive.Def(regs, pow(curBlock), node.getRes());
    if (node.getPtrval().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getPtrval());
    }
    if (node.getId1().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getId1());
    }
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irLoad node) throws error {
    //
    var newlive = new live();
    newlive.Def(regs, pow(curBlock), node.getRes());
    if (node.getPtr().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getPtr());
    }
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irStore node) throws error {
    //
    var newlive = new live();
    newlive.Def(regs, pow(curBlock), node.getRes());
    if (node.getPtr().charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), node.getPtr());
    }
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irBlock node) throws error {
    //
    if (node.isUnreachable())
      return;
    if (node.getStmts() != null)
      for (curStmt = 0; curStmt < node.getStmts().size(); curStmt++) {
        var stmt = node.getStmts().get(curStmt);
        stmt.setBlock(curBlock);
        stmt.setStmt(curStmt);
        visit(stmt);
      }
    if (node.getTerminalstmt() == null)
      node.setTerminal(node.getEndTerm());
    var stmt = node.getTerminalstmt();
    stmt.setBlock(curBlock);
    stmt.setStmt(curStmt);
    visit(stmt);
    curBlock++;
  }
}
