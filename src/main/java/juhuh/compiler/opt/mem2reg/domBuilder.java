package juhuh.compiler.opt.mem2reg;

import java.util.HashMap;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.*;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.ir.ins.*;
import juhuh.compiler.ir.stmt.irBlock;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

import java.util.List;

public class domBuilder{
  private HashMap<Integer, dom> doms = new HashMap<>();
  private HashMap<String, Integer> id = new HashMap<>();
  private vector<irBlock> id2B;
  private int cnt = 0;
  private vector<Integer>[] preds, dom;
  private vector<Integer> postRev;
  public void visit(irRoot node) throws error {
    for(var def : node.getFDef()) {
      visit(def);
    }
  }
  private void initBlock(irBlock block) {
    // consider block.name == "entry", it will be put at first
    id.put(block.getLabel(), cnt);
    id2B.add(block);
    ++cnt;
  }
  private boolean visitBlock(int idx) {
    if(doms.containsKey(idx) && idx != 0) {
      return false;
    }
    doms.put(idx,
      new dom(null, null));
    return true;
  }
  private void initFunc(irFuncDef node) {
    // init doms
    cnt = 0;
    initBlock(node.getEntry());
    for(var block : node.getBody()) {
      initBlock(block);
    }
    initBlock(node.getRet());
  }
  private void addPred(irBlock block) {
    // addpred block.label <- block.jump.dest

  }
  @SuppressWarnings("unchecked")
  private void initPreds(irFuncDef node) {
    preds = new vector[cnt];
    dom = new vector[cnt];
    for(int i = 0; i < cnt; ++i) {
      preds[i] = new vector<>();
      dom[i] = new vector<>();
    }
  }
  private void Visit(int idS, irBlock block) {
    // postReverseOrder
    irIns endT;
    if(block.getTerminalstmt() != null) {
      endT = block.getTerminalstmt();
    } else {
      endT = block.getEndTerm();
    }
    if(endT instanceof irJump) {
      // label -> block
      var idx = id.get(((irJump)endT).getDest());
      preds[idx].add(idS);
      if(visitBlock(idx))
        Visit(idx, id2B.get(idx));
      
    } else if(endT instanceof irBranch) {
      
    } else {
      throw new error("invalid terminal statement");
    }
    postRev.add(idS);
  }
  public void visit(irFuncDef node) throws error {
    // cnt only acccesible in this function
    initFunc(node);
    // build graph postReverseOrder
    initPreds(node);
    visitBlock(0);
    Visit(0, node.getEntry());

  }
}
