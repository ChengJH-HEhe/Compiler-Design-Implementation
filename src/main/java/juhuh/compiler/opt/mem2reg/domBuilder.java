package juhuh.compiler.opt.mem2reg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import juhuh.compiler.backend.asmBuilder;
import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.*;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.ir.ins.*;
import juhuh.compiler.ir.stmt.irBlock;
import juhuh.compiler.ir.stmt.irStmt;
import juhuh.compiler.opt.cfgBuilder;
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
  public vector<Integer> postRev;
  BitSet[] domFlag;
  boolean mem2reg = true;

  HashMap<Integer, Integer> loopHeader;
  public int[] loopBody;
  private irFuncDef curFunc;
  private asmBuilder asm;

  private boolean isGlobalName(String name) {
    return name != null && name.charAt(0) == ('@');
  }
  public domBuilder(asmBuilder asm, cfgBuilder cfg, irFuncDef node) {
    if(!mem2reg) return;
    this.asm = asm;
    curFunc = node;
    // cnt only acccesible in this function
    // System.err.println("visit func " + node.getFName());
    initFunc(node);
    // build graph postReverseOrder

    initPreds(node);
    Visit();
    initCFG(cfg);

    // build dominator tree using postReverseOrder
    // reverse postRev
    // place PHI cmd
  }

  public HashMap<String, HashSet<irStmt>> useIns;
  public HashMap<String, irStmt> defIns;

  public void DCE() {
    collectDef();
    // getUse; 
    Queue<irStmt> q = new LinkedList<>();
    for(var inS : defIns.values()) {
      q.offer(inS);
      inS.useful = true;
    }
    // Call branch store
    for(var i : postRev) {
      var block = id2B.get(i);
      if(block.isUnreachable())
        continue;

      var endT = block.getTerminalstmt();
      if(endT == null) {
        endT = block.getEndTerm();
        block.setTerminalstmt(endT);
      }
      if(endT instanceof irBranch)
        q.offer(endT);
      endT.useful = true;

      for(var stmt : block.getStmts()) {
        if(stmt instanceof irCall || stmt instanceof irBranch || stmt instanceof irStore) {
          if(!stmt.isUseful()) {
            q.offer(stmt);
            stmt.useful = true;
          }
        }
      }
    }
    //rebuild block.findVal;

    while(!q.isEmpty()) {
      var cur = q.poll();
      var use = cur.getUse();
      if(use != null)
      for(var ptr : use) {
        if(defIns.containsKey(ptr)) {
          var def = defIns.get(ptr);
          if(!def.isUseful()) {
            def.useful = true;
            q.offer(def);
          }
        }
      }
    }
    
    // reOrder stmt;
    for(var i : postRev) {
      var block = id2B.get(i);
      if(block.isUnreachable())
        continue;
      var vec = new vector<irStmt>();
      for(var stmt : block.getStmts()) {
        if(stmt.isUseful())
          vec.add(stmt);
      }
      block.setStmts(vec);
    }
    //findFirstLoad;
    //setLastDef;
    setLoadDef();
  }
  private void collectUnitUse(irStmt stmt) {
    if(stmt.getUse() != null)
    for (var use : stmt.getUse()) {
      if (!useIns.containsKey(use)) {
        useIns.put(use, new HashSet<>());
      }
      useIns.get(use).add(stmt);
    }
  }
  public void collectUse() {
    // curFunc;;
    useIns = new HashMap<>();
    for (var i : postRev) {
      var block = id2B.get(i);
      if(block.isUnreachable())
        continue;
      collectUnitUse(block.getTerminalstmt());
      for (var stmt : block.getStmts()) {
        collectUnitUse(stmt);
      }
    }
  }

  public void collectDef() {
    defIns = new HashMap<>();
    for (var i : postRev) {
      var block = id2B.get(i);
      if(block.isUnreachable())
        continue;
      for (var stmt : block.getStmts()) {
        var def = stmt.getDef();
        if(def != null) {
          defIns.put(def, stmt);
        }
      }
    }
  }

  public void setLoadDef() {
    for (var i : postRev) {
      var block = id2B.get(i);
      block.setPtr2reg(new HashMap<>());
      block.setRegs(new HashMap<>());
      block.setMtp(new HashMap<>());
      if(block.isUnreachable())
        continue;
       for (var stmt : block.getStmts()) {
        if(stmt instanceof irLoad) {
          var pt = ((irLoad) stmt).getPtr();
          if((isGlobalName(pt) || isTmpName(pt))) {
            if(block.findFirstLoad(pt) == null)
              block.setFirstLoad(pt, stmt.getDef());
            block.setVal(pt, stmt.getDef(), ((irLoad) stmt).getTp());
          }
        } else if(stmt instanceof irStore) {
          var pt = ((irStore) stmt).getPtr();
          if(pt.startsWith("@"))
            System.err.println("debug");
          if((isGlobalName(pt) || isTmpName(pt))) {
            block.setVal(pt, ((irStore)stmt).getRes(), ((irStore) stmt).getTp());
          }
        }
      }
      
    }
  }


  public void mem2reg() {
    if (mem2reg) {
      getDom();
      System.err.println("dom Build done");
      System.err.println("Rename reg begin");
      placePhi(); // phi Def

      // firstLoad -> phiDef
      // phi rhs -> def
      // rename regs
      path = new vector<>();
      renameRegs(0);

      path = new vector<>();
      renameReg(0);
      System.err.println("Rename Reg done");
    }
  }

  public void delPhi(irRoot root, allocator alloc) {
    for (var def : root.getFDef())
      delPhi(def, alloc);
  }

  public void visit(irRoot node) throws error {
    assert (false);
  }

  private void initBlock(irBlock block) {
    // consider block.name == "entry", it will be put at first
    // System.err.println("init block " + block.getLabel() + " " + cnt);
    id.put(block.getLabel(), cnt);
    id2B.add(block);
    ++cnt;
  }

  private boolean visitBlock(int idx) {
    // //System.err.println(idx + " " + id2B.get(idx).getLabel() +
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
        // //System.err.println(i + ": ");
        for (var pred : preds[i]) {
          // //System.err.println(i + " pred " + pred);
          tmp.and(domFlag[pred]);
        }
        tmp.set(i, true);
        if (!tmp.equals(domFlag[i])) {
          domFlag[i] = (BitSet) tmp.clone();
          changed = true;
        }
        // //System.err.println(i + domFlag[i].toString());
      }
    }
    for (int i = 0; i < cnt; ++i) {
      for (int j = 0; j < cnt; ++j) {
        if (domFlag[i].get(j)) {
          dom[i].add(j);
          // //System.err.println(j + " dom " + i);
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
        // System.err.println(id2B.get(i).getLabel() + " has no idom");
      } else {
        // System.err.println("idom for " + id2B.get(i).getLabel() + " is " +
        // id2B.get(doms.get(i).getIDom()).getLabel());
      }
    }
    // set domFrontier in preds.dom \ i.dom
    for (int i = 0; i < cnt; ++i) {
      if (id2B.get(i).isUnreachable())
        continue;
      // System.err.println(domFlag[i]);
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
        // System.err.println(j + " domF " + i);
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
      // //System.err.println(endT.toString());
      if (endT instanceof irJump) {
        // label -> block
        var idx = id.get(((irJump) endT).getDest());
        preds[idx].add(idS);
        // //System.err.println("jump to " + idx);
        if (visitBlock(idx))
          q.offer(idx);
      } else if (endT instanceof irBranch) {
        var idx = id.get(((irBranch) endT).iftrue);
        preds[idx].add(idS);
        // //System.err.println("branch to " + idx);
        if (visitBlock(idx))
          q.offer(idx);
        idx = id.get(((irBranch) endT).iffalse);
        preds[idx].add(idS);
        // //System.err.println("branch to " + idx);
        if (visitBlock(idx))
          q.offer(idx);
      } else {
        if (!(endT instanceof irRet))
          throw new error("invalid terminal statement");
      }
      postRev.add(idS);
    }
    // for (int i = 1; i < cnt; ++i) {
    // //System.err.println(i + " pred :" + preds[i].toString());
    // }
  }

  vector<irBlock> path;

  // alloca result.
  boolean isTmpName(String name) {
    return name.length() >= 2 && name.charAt(0) == ('%') && name.charAt(1) != ('_');
  }

  boolean isConst(String reg) {
    return !(reg.charAt(0) == '%' || reg.charAt(0) == '@');
  }

  public void renameRegs(int index) {
    irBlock block = id2B.get(index);
    path.add(block);
    var erase = new HashSet<String>();
    if (block.getRegs() != null) {

      for (var entry : block.getRegs().entrySet()) {
        if (!local2globalUnuse(entry.getKey())) {
          // entry getvalue is firstLoad? then replace the entry.getValue.
          if (entry.getValue() != null && !isConst(entry.getValue()))
            if(block.findFirstLoad(entry.getValue()) != null) {
            // lastdef = firstdef
            if(entry.getValue().equals("%_1283")) {
              System.err.println("233333");
            }
            if(block.getLabel().equals("log.rhs0"))
              System.err.println("rename Regs in " + block.getLabel());
            entry.setValue(replace(block, entry.getValue()));
          }
        } else {
          erase.add(entry.getKey());
        }
      }
      for (var unUsedGlobal : erase) {
        block.getRegs().remove(unUsedGlobal);
      }
    }
    for (var child : ch[index]) {
      renameRegs(child);
    }
    path.rmlst();
  }

  private boolean globalUnuse(String ptr) {
    return !curFunc.getFName().equals("__init__")
        && ptr.charAt(0) == '@' && curFunc.unUsed(ptr);
  }

  private void renameReg(int index) {
    // entry block has alloca need to neglect
    irBlock block = id2B.get(index);
    path.add(block);
    // upd thisblock's firstload reg->ptr replace vecStmt
    var vecStmt = block.getStmts();
    // replace stmts
    vector<irStmt> vec = new vector<irStmt>();
    if (vecStmt != null) {
      for (int i = 0; i < vecStmt.size(); ++i) {
        var stmt = (irIns) (vecStmt.get(i));
        if (stmt instanceof irAlloca || (stmt instanceof irLoad && globalUnuse(((irLoad) stmt).getPtr()))
            || (stmt instanceof irStore && globalUnuse(((irStore) stmt).getPtr())))
          continue;

        stmt.accept(this);
        if ((stmt instanceof irLoad && isTmpName(((irLoad) stmt).getPtr())) ||
            (stmt instanceof irStore && isTmpName(((irStore) stmt).getPtr()))) {
          continue;
        }

        // distinguish from phi or from lastBlock?
        // stmt's val
        vec.add(stmt);
        // if from phi then replace it by thisBlock's phi
        // if from lastBlock then replace it by lastBlock's lastDef
      }
    }
    
    block.setStmts(vec);
    
    var stmt = block.getTerminalstmt() == null ? block.getEndTerm() : block.getTerminalstmt();
    stmt.accept(this);
    // upd block's lastdef equals firstload
    block.setTerminalstmt(stmt);
    // ENSURE: regs correct(lstdef)
    // upd CFG's nxt Phi's rhs using findVal

    // del unUsed phidef.

    for (var nxt : outCnt[index]) {
      // set this nxt's def
      var setPhi = id2B.get(nxt).getPhi();
      for (var entry : setPhi.entrySet()) {
        if (entry.getValue().getReg().equals("%log.res299.log.end1")) 
          System.err.println("block " + block.getLabel());
        var repl = replace(block, replacePtr(block, entry.getKey()));
        entry.getValue().getLabel2val().put(block.getLabel(), repl);
      }
    }

    for (var child : ch[index]) {
      renameReg(child);
    }
    path.rmlst();
  }

  private boolean local2globalUnuse(String localPtr) {
    return localPtr.endsWith(".local") && globalUnuse("@" + localPtr.substring(1, localPtr.length() - 6));
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
        // LhsPhiDef setVal ; only null -> phi.
        if (block.findVal(entry.getKey()) == null)
          block.setVal(entry.getKey(), entry.getValue().getReg(), entry.getValue().getTp());
      }
    }
  }

  // visit every block to add phi Def
  private void visitPhi(int i) {

    var block = id2B.get(i);
    if (block.isUnreachable())
      return;
    // (WRONG!) if ptr2reg's reg equals lastDef, i.e. no actual def, not add phi
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
        if (!local2globalUnuse(entry.getKey()) && entry.getValue() != null
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

  vector<Integer>[] outCnt;

  // redef critical edge
  @SuppressWarnings("unchecked")
  public void initCFG(cfgBuilder cfg) {
    outCnt = new vector[cnt];
    for (int i = 0; i < cnt; ++i)
      outCnt[i] = new vector<>();
    for (int i = 1; i < cnt; ++i) {
      for (var pre : preds[i])
        outCnt[pre].add(i);
    }
    cfg.accept(cnt, outCnt, preds, id, id2B);

  }

  private void delPhi(irFuncDef curFunc, allocator alloc) {
    // phi -> add 0 : spj in asmBuilder
    // problem: critical edge? domF must have more than one pred,(endpoint ok) but
    // thisblock's outdeg may be one(startpoint)
    // only need to add 1 bb for st is many-child
    // if st is one-child, then add 0 in this block
    // else add 0 in a new created block
    boolean[] inOnly = new boolean[cnt];
    for (int i = 0; i < cnt; ++i) {
      inOnly[i] = (outCnt[i].size() == 1);
      boolean used = false;
      for (var Nxt : outCnt[i]) {
        // !inOnly[i] -> add new Block
        var nxt = id2B.get(Nxt);
        // nxt block's find this block's value should add 0 in this block
        for (var phiLhs : nxt.getPhi().entrySet())
          if (phiLhs.getValue().getLabel2val().get(id2B.get(i).getLabel()) != null &&
              alloc.exist(phiLhs.getValue().getReg())) {
            used = true;
            phiLhs.getValue().used = true; // used = false iff alloc !exist
            break;
          } else
            phiLhs.getValue().getLabel2val().put(id2B.get(i).getLabel(), null);
        if (used)
          break;
      }
      if (!used)
        inOnly[i] = true;
    }
    // st not only-child, -> endterm is still certain
    // add 0 in asmbuilder
    int tmpcnt = cnt;
    for (int i = 0; i < cnt; ++i) {
      var block = id2B.get(i);
      if (block.isUnreachable())
        continue;
      if (block.getPhiDel() == null)
        block.setPhiDel(new vector<irBinary>());
      if (!inOnly[i]) {
        // add 0 in new block
        block.setTerminal(block.getEndTerm());
        var newBlock = irBlock.builder()
            .label("_phi." + block.getLabel())
            .stmts(new vector<irStmt>())
            .terminalstmt(block.getTerminalstmt())
            .build();
        if (block.getLabel().equals("entry"))
          newBlock.setLabel("_phi." + curFunc.getFName());
        newBlock.setPhiDel(new vector<irBinary>());
        block.setTerminalstmt(irJump.builder().dest(newBlock.getLabel()).build());
        block = newBlock;
      }
      // directly add 0 in this block
      for (var Nxt : outCnt[i]) {
        var nxt = id2B.get(Nxt);
        // nxt block's find this block's value should add 0 in this block
        for (var phiLhs : nxt.getPhi().entrySet()) {
          // if (phiLhs.getValue().getReg().equals("%.1.for.cond.1"))
          // System.err.println("debug");
          if (alloc.exist(phiLhs.getValue().getReg())) {
            if (phiLhs.getValue().getLabel2val().get(block.getLabel()) != null) {
              block.getPhiDel().add(irBinary.builder()
                  .res(phiLhs.getKey() + "." + nxt.getLabel())
                  .op("add")
                  .op2(phiLhs.getValue().getLabel2val().get(block.getLabel()))
                  .op1("0")
                  .tp(phiLhs.getValue().getTp())
                  .build());
            }
          }
        }
      }
      if (!inOnly[i]) {
        id.put(block.getLabel(), tmpcnt);
        id2B.add(block);
        curFunc.add(id2B.get(i), block);
        tmpcnt++;
      }
    }
  }

  public allocator alloc;

  @Override
  public void visit(irFuncDef node) throws error {
    if (!mem2reg)
      return;
    // reset phi: add 0, xi -> opt phi
    // ir should be right

    // get tempMap for allocator
    // spill cost calc
    // first step: loop header, loop body classify
    defLoop();


    // spill use & color
    alloc = new allocator(this, node);

    // delphi
    delPhi(node, alloc);
    // asm rewrite

    asm.setCol(alloc.regColor);
    asm.visit(node);
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
          // System.err.println(id2B.get(i).getLabel() + " -> " + id2B.get(j).getLabel());
        }
      }
    }
    // loopbody -> loopheader
    for (var header : loopHeader.entrySet()) {
      Loopvisited = new BitSet(cnt);
      LoopDfs(header.getKey(), header.getValue());
    }
    // for (int i = 0; i < cnt; ++i)
    // //System.err.println(id2B.get(i).getLabel() + " depth : " + loopBody[i]);
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
  public HashMap<String, String> firstLoad2Phi = new HashMap<>();
  String f2P(String reg) {
    if(firstLoad2Phi.containsKey(reg))
      return firstLoad2Phi.get(reg);
    else return reg;
  }
  // find Ptr's def. setVal.
  private String findPre(String Ptr, int st) throws error {
    for (int i = st - 1; i >= 0; --i) {
      var res = path.get(i).findVal(Ptr);
      if (res != null)
        return f2P(res);
    }
    // System.err.println("**** there is no pre-Def for " + Ptr + " !!!!");
    return null;
  }

  private String replace(irBlock block, String oldReg) {
    String ans = oldReg;
    if(oldReg != null && oldReg.equals("%_1283")) {
      System.err.println("233333");
    }
    for (int i = path.size() - 1; i >= 0; --i) {
      var Block = path.get(i);
      var res = Block.findFirstLoad(oldReg);
      if (Block.findFirstLoad(oldReg) != null) {
        // this firstLoad -> dom preds's firstload.
        if (Block.getPhi().containsKey(res)) {
          firstLoad2Phi.put(oldReg, res + "." + Block.getLabel());
          ans = res + "." + Block.getLabel(); // phi has contain the ptr name if needed
          return ans;
        }
        else {
          ans = findPre(res, i);
          if(ans == null && i == 0)
            return oldReg;
          return ans;
        }
      }
    }
    return ans;
  }
  // res is ptr.
  private String replacePtr(irBlock block, String res) {
    String ans = block.findVal(res);
    if (ans == null) {
      ans = findPre(res, path.size() - 1);
    }
    return ans;
  }

  @Override
  public void visit(irBinary node) throws error {
    var block = path.getlst();
    if(node.getOp().equals("div"))
      System.err.println("Debug");
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
    node.setCond(replace(block, node.getCond()));
    node.setVal1(replace(block, node.getVal1()));
    node.setVal2(replace(block, node.getVal2()));
  }

  @Override
  public void visit(irBranch node) throws error {
    // do nothing
    var block = path.getlst();
    node.setCond(replace(block, node.getCond()));
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
    if (!isTmpName(node.getPtr()))
      node.setPtr(replace(block, node.getPtr()));
  }

  @Override
  public void visit(irStore node) throws error {
    // getelementptr's result should be added too
    // but if not?
    var block = path.getlst();
    if (!isTmpName(node.getPtr()))
      node.setPtr(replace(block, node.getPtr()));
    if (node.getRes().equals("%_38"))
      System.err.println("Dbug");
    node.setRes(replace(block, node.getRes()));
  }

  @Override
  public void visit(irBlock node) throws error {
    // shouldn't be visited
  }
}
