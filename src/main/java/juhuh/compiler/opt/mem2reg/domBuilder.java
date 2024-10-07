package juhuh.compiler.opt.mem2reg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import juhuh.compiler.backend.asmBuilder;
import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.*;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.ir.ins.*;
import juhuh.compiler.ir.stmt.irBlock;
import juhuh.compiler.ir.stmt.irStmt;
import juhuh.compiler.opt.regAlloc.allocator;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

import java.util.BitSet;

public class domBuilder implements irVisitor {
  private vector<dom> doms;
  public HashMap<String, Integer> id;
  public vector<irBlock> id2B;
  public int cnt = 0;
  public vector<Integer>[] preds, ch;
  private vector<Integer>[] dom;
  private vector<Integer> postRev;
  BitSet[] domFlag;

  HashMap<Integer, Integer> loopHeader;
  public int[] loopBody;

  private asmBuilder asm;
  public domBuilder(asmBuilder asm) {
    this.asm = asm;
  }
  public void delPhi(irRoot root) {
    for (var def : root.getFDef())
      delPhi(def);
  }

  public void visit(irRoot node) throws error {
    for (var def : node.getFDef()) {
      visit(def);
    }
  }

  private void initBlock(irBlock block) {
    // consider block.name == "entry", it will be put at first
    System.err.println("init block " + block.getLabel() + " " + cnt);
    id.put(block.getLabel(), cnt);
    id2B.add(block);
    ++cnt;
  }

  private boolean visitBlock(int idx) {
    // System.err.println(idx + " " + id2B.get(idx).getLabel() +
    // (doms.get(idx).getDomF() == null));
    if (doms.get(idx).getDomF() != null) {
      return false;
    }
    doms.set(idx, new dom(null));
    return true;
  }

  private void initFunc(irFuncDef node) {
    // init doms
    cnt = 0;
    id2B = new vector<irBlock>();
    id = new HashMap<>();
    postRev = new vector<Integer>();
    initBlock(node.getEntry());
    for (var block : node.getBody()) {
      initBlock(block);
    }
    initBlock(node.getRet());
  }

  @SuppressWarnings("unchecked")
  private void initPreds(irFuncDef node) {
    preds = new vector[cnt];
    dom = new vector[cnt];
    doms = new vector<dom>();
    for (int i = 0; i < cnt; ++i) {
      preds[i] = new vector<>();
      dom[i] = new vector<>();
      doms.add(new dom(null, null));
    }
  }

  @SuppressWarnings("unchecked")
  private void getDom() {
    BitSet tmp = new BitSet(cnt);
    domFlag = new BitSet[cnt];
    for (int i = 0; i < cnt; ++i) {
      domFlag[i] = new BitSet(cnt);
      domFlag[i].set(0, cnt);
    }
    boolean changed = true;

    while (changed) {
      changed = false;
      for (var i : postRev) {
        tmp.clear();
        if (i != 0)
          tmp.set(0, cnt);
        // System.err.println(i + ": ");
        for (var pred : preds[i]) {
          // System.err.println(i + " pred " + pred);
          tmp.and(domFlag[pred]);
        }
        tmp.set(i, true);
        if (!tmp.equals(domFlag[i])) {
          domFlag[i] = (BitSet) tmp.clone();
          changed = true;
        }
        // System.err.println(i + domFlag[i].toString());
      }
    }
    for (int i = 0; i < cnt; ++i) {
      for (int j = 0; j < cnt; ++j) {
        if (domFlag[i].get(j)) {
          dom[i].add(j);
          // System.err.println(j + " dom " + i);
        }
      }
    }
    // fix idom && build dom tree
    ch = new vector[cnt];
    for (int i = 0; i < cnt; ++i) {
      ch[i] = new vector<>();
    }
    for (int i = 1; i < cnt; ++i) {
      boolean flag = false;

      for (var idom : dom[i])
        if (dom[idom].size() + 1 == dom[i].size()) {
          doms.get(i).setIDom(idom);
          flag = true;
          ch[idom].add(i);
          break;
        }
      if (flag == false) {
        // this block is unreachable
        id2B.get(i).setUnreachable(true);
        System.err.println(id2B.get(i).getLabel() + " has no idom");
      } else {
        System.err.println("idom for " + id2B.get(i).getLabel() + " is  " + id2B.get(doms.get(i).getIDom()).getLabel());
      }
    }
    // set domFrontier in preds.dom \ i.dom
    for (int i = 0; i < cnt; ++i) {
      if (id2B.get(i).isUnreachable())
        continue;
      System.err.println(domFlag[i]);
      tmp.clear();
      var tmp1 = (BitSet) domFlag[i].clone();
      for (var pred : preds[i]) {
        // double true -> false
        tmp.or(domFlag[pred]);
      }
      tmp.xor(tmp1);
      tmp.set(i, false);
      for (int j = tmp.nextSetBit(0); j >= 0; j = tmp.nextSetBit(j + 1)) {
        doms.get(j).getDomF().add(i);
        System.err.println(j + " domF " + i);
      }
    }
  }

  private void Visit() {
    // postReverseOrder
    irIns endT;
    int idS;
    Queue<Integer> q = new LinkedList<>();
    visitBlock(0);
    q.offer(0);
    while (!q.isEmpty()) {
      idS = q.poll();
      irBlock block = id2B.get(idS);
      if (block.getTerminalstmt() != null) {
        endT = block.getTerminalstmt();
      } else {
        endT = block.getEndTerm();
      }
      // System.err.println(endT.toString());
      if (endT instanceof irJump) {
        // label -> block
        var idx = id.get(((irJump) endT).getDest());
        preds[idx].add(idS);
        // System.err.println("jump to " + idx);
        if (visitBlock(idx))
          q.offer(idx);
      } else if (endT instanceof irBranch) {
        var idx = id.get(((irBranch) endT).iftrue);
        preds[idx].add(idS);
        // System.err.println("branch to " + idx);
        if (visitBlock(idx))
          q.offer(idx);
        idx = id.get(((irBranch) endT).iffalse);
        preds[idx].add(idS);
        // System.err.println("branch to " + idx);
        if (visitBlock(idx))
          q.offer(idx);
      } else {
        if (!(endT instanceof irRet))
          throw new error("invalid terminal statement");
      }
      postRev.add(idS);
    }
    // for (int i = 1; i < cnt; ++i) {
    // System.err.println(i + " pred :" + preds[i].toString());
    // }
  }

  vector<irBlock> path;

  boolean isTmpName(String name) {
    return name.length() >= 2 && name.charAt(0) == ('%') && name.charAt(1) != ('_');
  }

  boolean isConst(String reg) {
    return !(reg.charAt(0) == '%' || reg.charAt(0) == '@');
  }

  private void renameReg(int index) {
    // entry block has alloca need to neglect
    irBlock block = id2B.get(index);
    path.add(block);
    // upd thisblock's firstload reg->ptr replace vecStmt
    var vecStmt = block.getStmts();
    // replace stmts

    if (block.getRegs() != null)
      for (var entry : block.getRegs().entrySet()) {
        if (entry.getValue() != null)
          if (!isConst(entry.getValue()) && block.findFirstLoad(entry.getValue()) != null) {
            // lastdef = firstdef
            entry.setValue(replace(block, entry.getValue()));
          }
      }
    if (vecStmt != null) {
      vector<irStmt> vec = new vector<irStmt>();
      for (int i = 0; i < vecStmt.size(); ++i) {
        var stmt = (irIns) (vecStmt.get(i));
        if (stmt instanceof irAlloca ||
            (stmt instanceof irLoad && isTmpName(((irLoad) stmt).getPtr())) ||
            (stmt instanceof irStore && isTmpName(((irStore) stmt).getPtr()))) {
          continue;
        }
        // distinguish from phi or from lastBlock?
        // stmt's val
        stmt.accept(this);
        vec.add(stmt);
        // if from phi then replace it by thisBlock's phi
        // if from lastBlock then replace it by lastBlock's lastDef
      }
      // upd block's lastdef equals firstload
      block.setStmts(vec);
    }
    irIns endT;
    if (block.getTerminalstmt() != null) {
      block.getTerminalstmt().accept(this);
      endT = block.getTerminalstmt();
    } else {
      block.getEndTerm().accept(this);
      endT = block.getEndTerm();
    }
    // ENSURE: regs correct(lstdef)
    // upd CFG's nxt Phi's rhs using def
    var vec = new vector<Integer>();
    if (endT instanceof irJump) {
      vec.add(id.get(((irJump) endT).getDest()));
    } else if (endT instanceof irBranch) {
      vec.add(id.get(((irBranch) endT).iftrue));
      vec.add(id.get(((irBranch) endT).iffalse));
    }
    for (var domf : vec) {
      // set this domf's def
      var setPhi = id2B.get(domf).getPhi();
      for (var entry : setPhi.entrySet()) {
        entry.getValue().getLabel2val().put(block.getLabel(), replacePtr(block,
            entry.getKey()));
      }
    }
    for (var child : ch[id.get(block.getLabel())]) {
      renameReg(child);
    }
    path.rmlst();
  }

  private void placePhi() {
    // upd this' lst-def -> domF's prePhi,
    // add phi only, lhs & blockname is it really helpful? add phi
    for (int i = 0; i < cnt; ++i) {
      var block = id2B.get(i);
      block.setPhi(new HashMap<String, irPhi>());
    }
    // put phi should be in domF order
    for (int i = 0; i < cnt; ++i) {
      visitPhi(i);
    }
    // update lastdef if no actual def
    for (int i = 0; i < cnt; ++i) {
      var block = id2B.get(i);
      for (var entry : block.getPhi().entrySet()) {
        if (block.findVal(entry.getKey()) == null)
          block.setVal(entry.getKey(), entry.getValue().getReg(), entry.getValue().getTp());
      }
    }
  }

  private void visitPhi(int i) {

    var block = id2B.get(i);
    if (block.isUnreachable())
      return;
    // if ptr2reg's reg equals lastDef, i.e. no actual def, not add phi
    BitSet visited = new BitSet(cnt);
    Queue<Integer> q = new LinkedList<>();
    q.offer(i);
    while (!q.isEmpty()) {
      var cur = q.poll();
      for (var domf : doms.get(cur).getDomF()) {
        if (!visited.get(domf)) {
          visited.set(domf);
          q.offer(domf);
        }
      }
    }
    // regs may only contain firstload result
    if (block.getRegs() != null)
      for (var entry : block.getRegs().entrySet()) {
        // fix: entry's reg not first load
        if (entry.getValue() != null
            && (isConst(entry.getValue()) || !entry.getKey().equals(block.findFirstLoad(entry.getValue())))) {
          // new irPhi
          // upd: lhs only local variables
          if (!isTmpName(entry.getKey()))
            continue;
          for (var domf = visited.nextSetBit(0); domf >= 0; domf = visited.nextSetBit(domf + 1)) {
            var domF = id2B.get(domf);
            domF.getPhi().put(entry.getKey(),
                irPhi.builder()
                    .labl(domF.getLabel())
                    .res(entry.getKey())
                    .tp(block.getMtp().get(entry.getKey()))
                    .label2val(new HashMap<String, String>())
                    .build());
          }
        }
      }
    // curBlocks regs should update with curBlock's phi-lhs
    // find-first-load only store %_

  }

  // redef critical edge
  private void delPhi(irFuncDef curFunc) {
    // phi -> add 0 : spj in asmBuilder
    // problem: critical edge? domF must have more than one pred,(endpoint ok) but
    // thisblock's outdeg may be one(startpoint)
    // only need to add 1 bb for st is many-child
    // if st is one-child, then add 0 in this block
    // else add 0 in a new created block
    boolean[] inOnly = new boolean[cnt];
    int[] outCnt = new int[cnt];

    for (int i = 1; i < cnt; ++i) {
      for (var pre : preds[i])
        ++outCnt[pre];
    }
    for (int i = 0; i < cnt; ++i) {
      if (outCnt[i] == 1) {
        inOnly[i] = true;
      }
    }
    // st not only-child, -> endterm is still certain
    // add 0 in asmbuilder
    for (int i = 0; i < cnt; ++i) {
      var block = id2B.get(i);
      if (block.isUnreachable())
        continue;
      if (!inOnly[i]) {
        // add 0 in new block
        block.setTerminal(block.getEndTerm());
        var newBlock = irBlock.builder()
            .label("_phi." + block.getLabel())
            .stmts(new vector<irStmt>())
            .terminalstmt(block.getTerminalstmt())
            .build();
        block.setTerminalstmt(irJump.builder().dest(newBlock.getLabel()).build());
        block = newBlock;
      }

      // directly add 0 in this block
      for (var domf : doms.get(i).getDomF()) {
        var domF = id2B.get(domf);
        // domF block's find this block's value should add 0 in this block
        for (var phiLhs : domF.getPhi().entrySet()) {
          if (phiLhs.getValue().getLabel2val().containsKey(block.getLabel()) &&
              phiLhs.getValue().getLabel2val().get(block.getLabel()) != null) {
            block.getStmts().add(irBinary.builder()
                .res(phiLhs.getKey() + "." + domF.getLabel())
                .op("add")
                .op2(phiLhs.getValue().getLabel2val().get(block.getLabel()))
                .op1("0")
                .tp(phiLhs.getValue().getTp())
                .build());
          }
        }
      }
      if (!inOnly[i]) {
        curFunc.add(block);
      }
    }
  }

  @Override
  public void visit(irFuncDef node) throws error {
    // cnt only acccesible in this function
    System.err.println("visit func " + node.getFName());
    initFunc(node);
    // build graph postReverseOrder
    initPreds(node);
    Visit();
    // build dominator tree using postReverseOrder
    // reverse postRev
    for (var i : postRev) {
      System.err.print(i + " ");
    }
    System.err.println("postRevOrder");
    getDom();
    // place PHI cmd
    placePhi();
    // rename regs
    path = new vector<>();
    renameReg(0);
    // reset phi: add 0, xi -> opt phi
    // ir should be right

    // get tempMap for allocator
    // spill cost calc
    // first step: loop header, loop body classify
    defLoop();
    // spill use & color
    var alloc = new allocator(this);
    alloc.visit(node);
    alloc.spill2Col(node.getParavaluelist());
    // delphi
    //delPhi(node);
    // asm rewrite

  }

  BitSet Loopvisited;

  private void LoopDfs(int target, int cur) {
    if (Loopvisited.get(cur))
      return;
    ++loopBody[cur];
    Loopvisited.set(cur);
    if (cur == target)
      return;
    for (var i : preds[cur])
      LoopDfs(target, i);
  }

  private void defLoop() {
    loopHeader = new HashMap<Integer, Integer>();
    loopBody = new int[cnt];
    // backedge
    for (int i = 0; i < cnt; ++i) {
      for (var j : preds[i]) {
        if (domFlag[j].get(i)) {
          loopHeader.put(i, j);
          System.err.println(id2B.get(i).getLabel() + " -> " + id2B.get(j).getLabel());
        }
      }
    }
    // loopbody -> loopheader
    for (var header : loopHeader.entrySet()) {
      Loopvisited = new BitSet(cnt);
      LoopDfs(header.getKey(), header.getValue());
    }
    for (int i = 0; i < cnt; ++i)
      System.err.println(id2B.get(i).getLabel() + " depth : " + loopBody[i]);
  }

  @Override
  public void visit(irNode node) throws error {
    node.accept(this);
  }

  @Override
  public void visit(irGlobalDef node) throws error {
    // do nothing
  }

  @Override
  public void visit(irStrDef node) throws error {
    // do nothing
  }

  private String findPre(String Ptr) throws error {
    for (int i = path.size() - 2; i >= 0; --i) {
      var res = path.get(i).findVal(Ptr);
      if (res != null)
        return res;
    }
    System.err.println("**** there is no pre-Def for " + Ptr + " !!!!");
    return null;
  }

  private String replace(irBlock block, String oldReg) {
    var res = block.findFirstLoad(oldReg);
    String ans = "";
    if (res != null) {
      if (block.getPhi().containsKey(res))
        ans = res + "." + block.getLabel(); // phi has contain the ptr name if needed
      else {
        ans = findPre(res);
      }
    } else
      ans = oldReg;
    return ans;
  }

  private String replacePtr(irBlock block, String res) {
    String ans = block.findVal(res);
    if (ans == null) {
      ans = findPre(res);
    }
    return ans;
  }

  @Override
  public void visit(irBinary node) throws error {
    var block = path.getlst();
    node.setOp1(replace(block, node.getOp1()));
    node.setOp2(replace(block, node.getOp2()));
  }

  @Override
  public void visit(irIcmp node) throws error {
    var block = path.getlst();
    node.setOp1(replace(block, node.getOp1()));
    node.setOp2(replace(block, node.getOp2()));
  }

  @Override
  public void visit(irSelect node) throws error {
    var block = path.getlst();
    node.setVal1(replace(block, node.getVal1()));
    node.setVal2(replace(block, node.getVal2()));
  }

  @Override
  public void visit(irBranch node) throws error {
    // do nothing
  }

  @Override
  public void visit(irJump node) throws error {
    // do nothing
  }

  @Override
  public void visit(irRet node) throws error {
    var block = path.getlst();
    if (node.getVal() != null) {
      node.setVal(replace(block, node.getVal()));
    }
  }

  @Override
  public void visit(irCall node) throws error {
    // replace node.val
    var block = path.getlst();
    for (int i = 0; i < node.getVal().size(); ++i) {
      node.getVal().set(i, replace(block, node.getVal().get(i)));
    }
  }

  @Override
  public void visit(irAlloca node) throws error {
    // do nothing
  }

  @Override
  public void visit(irGetElement node) throws error {
    //
    var block = path.getlst();
    node.setPtrval(replace(block, node.getPtrval()));
    node.setId1(replace(block, node.getId1()));
  }

  @Override
  public void visit(irLoad node) throws error {
    // done previously
    var block = path.getlst();
    node.setPtr(replace(block, node.getPtr()));
    node.setRes(replace(block, node.getRes()));
  }

  @Override
  public void visit(irStore node) throws error {
    // getelementptr's result should be added too
    // but if not?
    var block = path.getlst();
    node.setPtr(replace(block, node.getPtr()));
    node.setRes(replace(block, node.getRes()));
  }

  @Override
  public void visit(irBlock node) throws error {
    // shouldn't be visited
  }
}
