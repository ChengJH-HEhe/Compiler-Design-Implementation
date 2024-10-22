package juhuh.compiler.opt;

import java.util.HashMap;

import juhuh.compiler.ir.stmt.*;
import juhuh.compiler.util.vector;

public class cfgBuilder {
  int blockCnt;
  vector<Integer>[] out, pred;
  HashMap<String, Integer> block2index;
  vector<irBlock> index2block;
  public void accept(int cnt, vector<Integer>[] outCnt, vector<Integer>[] preds,
  HashMap<String, Integer> b2i, vector<irBlock> i2B) {
    blockCnt = cnt;
    out = outCnt;
    pred = preds;
    block2index = b2i;
    index2block = i2B;
  }
  HashMap<String, Integer> repl = new HashMap<>();
  public void jumpElim() {
    for(int i = 0; i < blockCnt; ++i) {
      var block = index2block.get(i);
      // TODO jumpElim.
      if(out[i].size() != 1 || block.isUnreachable()) continue;
        if(block.isEmpty()) {
          repl.put(block.getLabel(), out[i].get(0));
          block.setUnreachable(true);
        }
    }
  }
}
