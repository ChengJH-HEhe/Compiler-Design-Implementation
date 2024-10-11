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
  public regCol regColor; // spill color it

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
    for (var stmt : node.getPhi().entrySet()) {
      var phi = stmt.getValue();
      for (var val : phi.getLabel2val().entrySet()) {
        if (val.getValue() != null && val.getValue().equals(reg)) {
          scanBlock(dom.id.get(val.getKey()), val.getValue());
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
    assert (curStmt >= 0);
    if (curStmt == 0) {
      // no preceding
      liveStmt[curBlock].get(curStmt).In(reg);
      var setPhi = dom.id2B.get(curBlock).getPhi();
      if (setPhi != null) {
        for (var phi : setPhi.entrySet()) {
          // phi def use reg?
          if (phi.getValue() != null && phi.getValue().getReg().equals(reg)) {
            return;
          }
        }
      }
      for (var pred : dom.preds[curBlock]) {
        scanBlock(pred, reg);
      }
    } else {
      // Code that may throw an exception
      liveStmt[curBlock].get(curStmt).In(reg);
      --curStmt;
      scanOut();
    }
  }

  void scanOut() {
    assert (curStmt >= 0);
    var def = liveStmt[curBlock].get(curStmt).def;
    liveStmt[curBlock].get(curStmt).Out(reg);
    if (!def.contains(reg)) {
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
          if (stmt.use != null && stmt.use.contains(reg)) {
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
        return -e2.getValue().compareTo(e1.getValue());
      }
    });
    return entryList;
  }

  private boolean spilled(String reg) {
    return regColor.regs.containsKey(reg) && regColor.regs.get(reg).spilled;
  }

  private void spillReg() {
    regColor.addSpill(reg);
  }

  private boolean spillPhi(int id) {
    @SuppressWarnings("unchecked")
    HashSet<String> out = (HashSet<String>) (liveStmt[id].get(0).in).clone();
    // TODO : check if [id].[0].in is phi.out

    int sz = out.size();
    for (var outReg : out) {
      if (spilled(outReg))
        --sz;
    }
    if (sz > regColor.K) {
      spillReg();
      return true;
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
          if (sz <= regColor.K)
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
        if (!live.out.contains(uses)) {
          regColor.eraseReg(uses);
        }
      // color def
      for (var defs : live.def)
        if (live.out.contains(defs)) {
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
    regColor = new regCol();
    regColor.argsId = Math.min(8, args.size());// notspilled count store
    regColor.spillCount = args.size() - 8;
    List<HashMap.Entry<String, Integer>> entryList = sortByCost();
    // reverse entryList

    for (int i = 0; i < dom.cnt; ++i) {
      for (int j = 0; j < liveStmt[i].size(); ++j)
        regColor.setSpillCount(liveStmt[i].get(j).out.size());
    }
    for (Map.Entry<String, Integer> entry : entryList) {
      reg = entry.getKey();
      spReg();
    }
    // col reg. dominate tree preorder.
    // recolor args
    int tmpcnt = 0;
    for (var arg : args) {
      tmpcnt++;
      if (tmpcnt <= 8)
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

  private void liveUse(live newlive, String reg) {
    if (reg != null && reg.charAt(0) == '%') {
      newlive.Use(regs, pow(curBlock), reg);
    }
  }

  private void liveDef(live newlive, String reg) {
    if (reg != null && reg.charAt(0) == '%') {
      newlive.Def(regs, pow(curBlock), reg);

    }
  }

  @Override
  public void visit(irBinary node) throws error {
    // def: res, use: val1, val2
    var newlive = new live(node);
    liveUse(newlive, node.getOp1());
    liveUse(newlive, node.getOp2());
    liveDef(newlive, node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irIcmp node) throws error {
    //
    var newlive = new live(node);
    liveUse(newlive, node.getOp1());
    liveUse(newlive, node.getOp2());
    liveDef(newlive, node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irSelect node) throws error {
    // def, use
    var newlive = new live(node);
    liveUse(newlive, node.getCond());
    liveUse(newlive, node.getVal1());
    liveUse(newlive, node.getVal2());
    liveDef(newlive, node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irBranch node) throws error {
    // cond use
    var newlive = new live(node);
    liveUse(newlive, node.getCond());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irJump node) throws error {
    // no condition
    liveStmt[curBlock].add(new live(node));
  }

  @Override
  public void visit(irRet node) throws error {
    // only use
    var newLive = new live(node);
    if (node.getVal() != null && !node.getVal().equals("") && node.getVal().charAt(0) == '%') {
      newLive.Use(regs, pow(curBlock), node.getVal());
    }
    liveStmt[curBlock].add(newLive);
  }

  @Override
  public void visit(irCall node) throws error {
    var newlive = new live(node);
    if (!node.getRes().equals(""))
      newlive.Def(regs, pow(curBlock), node.getRes());
    for (var arg : node.getVal()) {
      if (arg != null && arg.charAt(0) == '%') {
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
    var newlive = new live(node);
    liveDef(newlive, node.getRes());
    liveUse(newlive, node.getPtrval());
    liveUse(newlive, node.getId1());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irLoad node) throws error {
    //
    var newlive = new live(node);

    liveDef(newlive, node.getRes());
    liveUse(newlive, node.getPtr());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irStore node) throws error {
    //
    var newlive = new live(node);
    liveUse(newlive, node.getRes());
    liveUse(newlive, node.getPtr());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irBlock node) throws error {
    //
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
