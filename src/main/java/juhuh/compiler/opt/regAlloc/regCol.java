package juhuh.compiler.opt.regAlloc;

import java.util.HashMap;
import java.util.HashSet;

import juhuh.compiler.util.error.error;

public class regCol {
  HashMap<String, color> regs;
  HashSet<Integer> inUse;
  int spillCount = 0;
  int K = 32;
  public regCol() {
    regs = new HashMap<String, color>();
    inUse = new HashSet<Integer>();
  }
  public void orLiveIn(HashSet<String> liveIn) {
    for (String reg : liveIn) {
      if (regs.get(reg) != null) {
        var col = regs.get(reg);
        if(!col.spilled)
          inUse.add(col.id);
      }
    }
  }
  private int findCol() {
    for (int i = 0; i < K; i++) {
      if (!inUse.contains(i)) {
        inUse.add(i);
        System.err.println("Register " + i + " is found");
        return i;
      }
    }
    throw new error("No available color for " + K + " colors");
  }
  // spill or color after spill (determine with isSpilled)
  public void addReg(String reg, boolean isSpilled) {
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
    regs.put(reg, c);
  }
  public void eraseReg(String reg) {
    if (regs.get(reg) == null) {
      return;
    }
    var col = regs.get(reg);
    if(!col.spilled)
      inUse.remove(col.id);
    else {
      System.err.println("Spilled register " + reg + " is erased");
    }
  }
}
