package juhuh.compiler.opt.regAlloc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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

  public boolean exist(String str) {
    if (str.charAt(0) == '%')
      return regs.containsKey(str);
    else
      return true;
  }

  irFuncDef curfunc;

  @SuppressWarnings("unchecked")
  public allocator(domBuilder Dom, irFuncDef func) {
    dom = Dom;
    curfunc = func;
    liveStmt = new vector[Dom.cnt];
    for (int i = 0; i < Dom.cnt; i++) {
      liveStmt[i] = new vector<live>();
    }
    visit(func);
    spill2Col(func.getParavaluelist());

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
    assert (curStmt >= 0);
    var def = liveStmt[curBlock].get(curStmt).def;
    
    if(liveStmt[curBlock].get(curStmt).Out(reg))
    if (!def.contains(reg)) {
      scanIn();
    }
    curBlock = tmp1;
    curStmt = tmp2;
  }

  void scanIn() {
    assert (curStmt >= 0);
    if (curStmt == 0) {
      // no preceding
      if(!liveStmt[curBlock].get(curStmt).In(reg))
        return;
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
      if(liveStmt[curBlock].get(curStmt).In(reg)){
        --curStmt;
        assert (curStmt >= 0);
        var def = liveStmt[curBlock].get(curStmt).def;
        
        if(liveStmt[curBlock].get(curStmt).Out(reg))
        if (!def.contains(reg)) {
          scanIn();
        }
      }
    }
  }

  private HashMap<String, Integer> phi2blckId = new HashMap<>();
  // private HashMap<String, vector<Integer>> useB = new HashMap<>();
  void findphiDef() {
    for (int i = 0; i < dom.cnt; i++) {
      for (var stmt : dom.id2B.get(i).getPhi().entrySet()) {
        var phi = stmt.getValue();
        phi2blckId.put(phi.getReg(), i);
      }
    }
    Queue<String> phiDef = new LinkedList<>();
    for (int i = 0; i < dom.cnt; i++) {
      for (var stmt : dom.id2B.get(i).getPhi().entrySet()) {
        var phi = stmt.getValue();
        if (regs.get(phi.getReg()) == null) {
          continue;
        }
        for (var use : phi.getLabel2val().entrySet()) {
          if (use.getValue() != null && regs.get(use.getValue()) == null) {
            // block, def should not be visited
            regs.put(use.getValue(), pow(dom.id.get(use.getKey())));
            if (phi2blckId.get(use.getValue()) != null)
              phiDef.add(use.getValue());
          }
        }
      }
    }
    while (!phiDef.isEmpty()) {
      var reg = phiDef.poll();
      int blockId = phi2blckId.get(reg);
      for (var stmt : dom.id2B.get(blockId).getPhi().entrySet()) {
        var phi = stmt.getValue();
        if (phi.getReg().equals(reg)) {
          for (var use : phi.getLabel2val().entrySet()) {
            if (use.getValue() != null && regs.get(use.getValue()) == null) {
              regs.put(use.getValue(), pow(dom.id.get(use.getKey())));
              if (phi2blckId.get(use.getValue()) != null)
                phiDef.add(use.getValue());
            }
          }
        }
      }
    }
  }

  void addphiDef(live lv, irBlock blk) {
    var phiset = blk.getPhi().entrySet();
    for (var phi : phiset) {
      lv.in.add(phi.getValue().getReg());
    }
  }

  @Override
  public void visit(irFuncDef node) throws error {
    regs = new HashMap<String, Integer>();
    // getRegs keyset.
    System.err.println("begin live");
    visit(node.getEntry());
    var live = node.getEntry().getLive();
    for (var block : node.getBody()) {
      visit(block);
      for (var in : block.getLive().in)
        live.in.add(in);
    }
    visit(node.getRet());
    for (var in : node.getRet().getLive().in)
      live.in.add(in);
    System.err.println("end live");
    // phi def in regs, then phi use is all effective, should be added.
    findphiDef();
    // phi.def si?
    addphiDef(live, node.getEntry());
    addphiDef(live, node.getRet());

    for (var block : node.getBody())
      addphiDef(live, block);

    // phi use -> phi def effective.
    System.err.println("begin scan");
    dom.collectUse();
    // scan
    scanned = new BitSet(dom.cnt);
    // begin ssa liveness analysis
    // initialization
    for (var reg1 : regs.keySet()) {
      reg = reg1;
      scanned.clear();
      // postRevOrder.
      for (int i: dom.postRev) {
        curBlock = i;
        var Node = dom.id2B.get(i);
        // scan use for phi
        scanPhi(Node);
      }
      if(dom.useIns.get(reg) != null)
      for(var stmt : dom.useIns.get(reg)) {
        curBlock = stmt.getBlock();
        curStmt = stmt.getStmt();
        scanIn();
      }
    //   for (int i: dom.postRev) {
    //     curBlock = i;
    //     // scan use for specified variable
    //     for (int j = 0; j < liveStmt[i].size(); j++) {
    //       var stmt = liveStmt[i].get(j);
    //       if (stmt.use != null && stmt.use.contains(reg)) {
    //         //assert(.contains(dom.id2B.get(i).getStmts().get(j)));
    //         curStmt = j;
    //         scanIn();
    //       }
    //     }
    //   }
    }
    System.err.println("END scan");
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
    for (int i = 0; i < dom.cnt; ++i) {
      phiIn.get(i).remove(reg);
      for (int j = 0; j < liveStmt[i].size(); ++j) {
        liveStmt[i].get(j).spReg(reg);
      }
    }
    regColor.addSpill(reg);
  }
  vector<HashSet<String>> phiIn = new vector<>();
  private boolean spillPhi(int id) {
    HashSet<String> out = phiIn.get(id);
    // check if [id].[0].in is phi.out
    int sz = out.size();
    if (sz > regColor.K) {
      spillReg();
      return true;
    }
    return false;
  }

  private vector<String> debug;

  public boolean spReg() {
    // spill phi
    boolean allsat = true;
    for (int i = 0; i < dom.cnt; ++i)
      if (spillPhi(i)){
        return false;
      } else {
        allsat &= phiIn.get(i).size() <= regColor.K;
      }
    // spill normal stmt
    
    for (int i = 0; i < dom.cnt; ++i) {
      for (int j = 0; j < liveStmt[i].size(); ++j) {
        var stmt = liveStmt[i].get(j);
        allsat &= stmt.sp.size() <= regColor.K;
        if (stmt.out.contains(reg)) {
          int sz = stmt.sp.size();
          if (sz <= regColor.K)
            continue;
          if (reg.equals("%f.3.5.for.cond4.0.0")) {
            if (sz == 25) {
              debug = new vector<String>();
              for (var outReg : stmt.out) {
                if (!spilled(outReg))
                  debug.add(outReg);
              }
            }
            System.err.println("Debug");
          }
          if (sz > regColor.K) {
            spillReg();
            return false;
          }
        }
      }
    }
    return allsat;
  }

  public void preColor(int blockId) {
    // inUse should bit-OR in [0]in (phi.out)
    @SuppressWarnings("unchecked")
    HashSet<String> out = (HashSet<String>) (liveStmt[blockId].get(0).in).clone();

    @SuppressWarnings("unchecked")
    HashSet<String> in = (HashSet<String>) (liveStmt[blockId].get(0).in).clone();
    System.err.println(dom.id2B.get(blockId).getLabel() + ":\n");
    regColor.orLiveIn(in, dom.id2B.get(blockId).getPhi().keySet());

    // phi def should be colored(if in [0]in)
    for (var phiuse : dom.id2B.get(blockId).getPhi().entrySet()) {
      var regSet = phiuse.getValue().getLabel2val();
      for (var reg : regSet.values())
        if (reg != null && !out.contains(reg)) {
          regColor.eraseReg(reg);
          System.err.println(dom.id2B.get(blockId).getLabel() + " phi erase " + " " + reg);
        }
    }

    // color phi def when out contains def
    for (var phiuse : dom.id2B.get(blockId).getPhi().entrySet()) {
      var def = phiuse.getValue().getReg();
      if (out.contains(def)) {
        if (def.equals(("%f.3.5.for.cond4.0.0")))
          System.err.println(dom.id2B.get(blockId).toString());
        regColor.addReg(def, false);
      }
    }
    // // color simple stmt
    // if (dom.id2B.get(blockId).getLabel().equals("for.cond2.0.0"))
    // System.err.println("debug");
    for (var live : liveStmt[blockId]) {
      // erase use & not in liveout
      for (var uses : live.use)
        if (!live.out.contains(uses)) {
          // System.err.println(live.node.toString() + " erase " + " " + uses);
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

  @SuppressWarnings("unchecked")
  public void spill2Col(vector<String> args) {
    // spill the > k
    regColor = new regCol();
    regColor.argsId = (args.size());// notspilled count store
    
    /*  pre work !! */
    
    for(int i = 0; i < dom.cnt; ++i)
      phiIn.add((HashSet<String>)(liveStmt[i].get(0).in.clone()));
    List<HashMap.Entry<String, Integer>> entryList = sortByCost();
    // reverse entryList

    /*  pre work done !! */
    System.err.println(entryList.size());
    int sz = 0;
    for (Map.Entry<String, Integer> entry : entryList) {
      reg = entry.getKey();
      // System.err.println(reg);
      ++sz;
      if(spReg()) {
        System.err.println(sz);
        break;
      }
    }

    var spC = 0;
    for (int i = 0; i < dom.cnt; ++i) {
      spC = Math.max(spC, regColor.getSpillCount(liveStmt[i].get(0).in));
      for (int j = 0; j < liveStmt[i].size(); ++j)
        spC = Math.max(spC, regColor.getSpillCount(liveStmt[i].get(j).out));
    }
    regColor.setSpillCount(spC);

    // col reg. dominate tree preorder.
    // recolor args
    int tmpcnt = 0;
    for (var arg : args) {
      regColor.addArg(arg, tmpcnt);
      tmpcnt++;
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

  live NewLive(irStmt node) {
    var livee = new live(node);
    node.setB2S(curBlock, liveStmt[curBlock].size());
    node.setLive(livee);
    return livee;
  }

  @Override
  public void visit(irBinary node) throws error {
    // def: res, use: val1, val2
    var newlive = NewLive(node);
    liveUse(newlive, node.getOp1());
    liveUse(newlive, node.getOp2());
    liveDef(newlive, node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irIcmp node) throws error {
    //
    var newlive = NewLive(node);
    liveUse(newlive, node.getOp1());
    liveUse(newlive, node.getOp2());
    liveDef(newlive, node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irSelect node) throws error {
    // def, use
    var newlive = NewLive(node);
    liveUse(newlive, node.getCond());
    liveUse(newlive, node.getVal1());
    liveUse(newlive, node.getVal2());
    liveDef(newlive, node.getRes());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irBranch node) throws error {
    // cond use
    var newlive = NewLive(node);
    liveUse(newlive, node.getCond());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irJump node) throws error {
    // no condition
    liveStmt[curBlock].add(NewLive(node));
  }

  @Override
  public void visit(irRet node) throws error {
    // only use
    var newLive = NewLive(node);
    if (node.getVal() != null && !node.getVal().equals("") && node.getVal().charAt(0) == '%') {
      newLive.Use(regs, pow(curBlock), node.getVal());
    }
    liveStmt[curBlock].add(newLive);
  }

  @Override
  public void visit(irCall node) throws error {
    var newlive = NewLive(node);
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
    var newlive = NewLive(node);
    liveDef(newlive, node.getRes());
    liveUse(newlive, node.getPtrval());
    liveUse(newlive, node.getId1());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irLoad node) throws error {
    //
    var newlive = NewLive(node);

    liveDef(newlive, node.getRes());
    liveUse(newlive, node.getPtr());
    liveStmt[curBlock].add(newlive);
  }

  @Override
  public void visit(irStore node) throws error {
    //
    var newlive = NewLive(node);
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
    var live = new live(node);
    for (var i : liveStmt[curBlock]) {
      for (var in : i.use)
        live.in.add(in);
      for (var in : i.def)
        live.in.add(in);
    }
    node.setLive(live);
    curBlock++;
  }
}
