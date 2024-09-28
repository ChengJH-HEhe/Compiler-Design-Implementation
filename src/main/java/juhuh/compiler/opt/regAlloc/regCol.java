package juhuh.compiler.opt.regAlloc;

import java.util.HashMap;
import java.util.HashSet;

import juhuh.compiler.util.error.error;

public class regCol {
  HashMap<String, color> regs;
  HashSet<Integer> inUse;
  int spillCount = 0;
  int K = 32;

  private int findCol() {
    if(inUse == null) {
      inUse = new HashSet<Integer>();
    }
    for (int i = 0; i < K; i++) {
      if (!inUse.contains(i)) {
        inUse.add(i);
        return i;
      }
    }
    throw new error("No available color for " + K + " colors");
  }
  // spill or color after spill (determine with isSpilled)
  public void addReg(String reg, boolean isSpilled) {
    if (regs == null) {
      regs = new HashMap<String, color>();
    }
    if (regs.get(reg) != null) {
      return;
    }
    color c = new color();
    if (isSpilled) {
      c.spilled = true;
      c.id = spillCount++;
    } else {
      c.spilled = false;
      c.id = findCol();
    }
  }
}
