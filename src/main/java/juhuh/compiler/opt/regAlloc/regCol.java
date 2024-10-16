package juhuh.compiler.opt.regAlloc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import juhuh.compiler.backend.VarRegManager;
import juhuh.compiler.backend.asm.*;
import juhuh.compiler.backend.asm.ins.pseudo;
import juhuh.compiler.backend.asm.ins.riscS;
import juhuh.compiler.util.*;

public class regCol {
  HashMap<String, color> regs;
  HashSet<Integer> inUse;
  public int spillCount = 0, argsId = 0;
  int K = 25;

  public void setSpillCount(HashSet<String> out) {
    int count = 0;
    for (var str : out) {
      if (regs.get(str) != null && regs.get(str).spilled) {
        if(regs.get(str).id < 0)
          count++;
      }
    }
    spillCount = Math.max(spillCount, count + Math.max(0, argsId - 8));
  }

  public regCol() {
    regs = new HashMap<String, color>();
    inUse = new HashSet<Integer>();
    spillregs = new HashSet<>();
    for (int i = 0; i < spillCount; i++) {
      spillregs.add(i);
    }
  }

  public color getCol(String name) {
    return regs.get(name);
  }

  public int getSpillReg(String name) {
    var res = regs.get(name);
    assert (res.spilled);
    return res.id;
  }

  public String getRegName(int id) {
    // s0~11 a7~4 t0~4 a3~0
    if (id <= 11)
      return "s" + id;
    else {
      id -= 12;
      if (id <= 3)
        return "a" + (7 - id);
      else {
        id -= 4;
        if (id <= 4)
          return "t" + id;
        else
          return "a" + (8 - id);
      }
    }
  }

  public asmNode getColResult(color co, String resul, String tp, VarRegManager VRM) {
    // is not spilled return the register; else return the riscS,tempvar(t5)
    if (co == null) {
      return null;
    }
    var res = col2i(co);
    if (res >= 0) {
      return pseudo.builder()
          .strs(new vector<String>("mv", getRegName(res), resul))
          .build();
    } else {
      return riscS.builder()
          .op(tp)
          .rs2(resul)
          .rs1("sp")
          .imm(VRM.getOffset(res))
          // rd the spill structure
          .build();
    }
  }

  public asmNode getResult(String name, String resul, String tp, VarRegManager VRM) {
    // is not spilled return the register; else return the riscS,tempvar(t5)
    var res = getReg(name);
    if (res >= 0) {
      return pseudo.builder()
          .strs(new vector<String>("mv", getRegName(res), resul))
          .build();
    } else {
      if (res == -114514191) {
        return null;
      }
      return riscS.builder()
          .op("s" + tp)
          .rs2(resul)
          .rs1("sp")
          .imm(VRM.getOffset(res))
          // rd the spill structure
          .build();
    }
  }

  public int col2i(color c) {
    if (c.spilled)
      return (c.id + 1) * (-1);
    return c.id;
  }

  public int getReg(String name) {
    var res = regs.get(name);
    if (res == null || res.id == -114514) {
      return -114514191;
    }
    if (res.spilled)
      return (res.id + 1) * (-1);
    return res.id;
  }

  public void orLiveIn(HashSet<String> liveIn, Set<String> phidef) {

    // livein del phidef
    for (var def : phidef)
      liveIn.remove(def);

    for (int i = 0; i < spillCount; ++i)
      spillregs.add(i);
    inUse.clear();
    for (String reg : liveIn) {
      if (regs.get(reg) != null) {
        var col = regs.get(reg);
        if (!col.spilled)
          inUse.add(col.id);
        else
          spillregs.remove(col.id);
      }
    }
  }

  private int findCol() {
    for (int i = 0; i < K; i++) {
      if (!inUse.contains(i)) {
        inUse.add(i);
        // System.err.println("Register " + i + " is found");
        return i;
      }
    }
    System.err.println("No available color for " + K + " colors");
    return 23333333;
  }

  private HashSet<Integer> spillregs;

  private int findSpilled() {
    int res = spillregs.iterator().next();
    spillregs.remove(res);
    return res;
  }

  // spill or color after spill (determine with isSpilled)
  public void addSpill(String reg) {
    color c = new color();
    c.spilled = true;
    c.id = -114514;
    regs.put(reg, c);
  }

  private int anum(int num) {
    if (num <= 3)
      return K - num - 1;
    else
      return 19 - num;
  }

  public void addArg(String name, int num) {
    color c = new color();
    if (num <= 7) {
      c.spilled = false;
      c.id = anum(num);
      inUse.add(c.id);
    } else {
      c.spilled = true;
      c.id = num - 8;
    }
    regs.put(name, c);
  }

  public void addReg(String reg, boolean isSpilled) {
    color c = new color();
    if (regs.get(reg) != null && regs.get(reg).id == -114514) {
      if (reg.equals("%a"))
        System.err.println("debug");
      c.spilled = true;
      c.id = findSpilled();
      regs.put(reg, c);
      return;
    }
    if (regs.get(reg) != null) {
      return;
    }
    if (isSpilled) {
      // func args.
      c.spilled = true;
      c.id = findSpilled();
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
    if (!col.spilled)
      inUse.remove(col.id);
    else {
      if (col.id != -114514)
        spillregs.add(col.id);
    }
  }
}
