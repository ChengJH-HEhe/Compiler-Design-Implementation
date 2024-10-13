package juhuh.compiler.opt.regAlloc;

import java.util.HashMap;
import java.util.HashSet;

import juhuh.compiler.backend.VarRegManager;
import juhuh.compiler.backend.asm.*;
import juhuh.compiler.backend.asm.ins.pseudo;
import juhuh.compiler.backend.asm.ins.riscS;
import juhuh.compiler.util.*;
import juhuh.compiler.util.error.error;

public class regCol {
  HashMap<String, color> regs;
  HashSet<Integer> inUse;
  public int spillCount = 0, argsId;
  int K = 27;
  public void setSpillCount(int count) {
    spillCount = Math.max(spillCount, count - K);
  }
  public regCol() {
    regs = new HashMap<String, color>();
    inUse = new HashSet<Integer>();
    spillregs = new HashSet<>();
  }
  public color getCol(String name) {
    return regs.get(name);
  } 
  public int getSpillReg(String name) {
    var res = regs.get(name);
    assert(res.spilled);
    return res.id;
  }
  public String getRegName(int id) {
    // s0~11 a7~4 t0~6 a3~0
    if(id <= 11)
      return "s" + id;
    else {
      id -= 12;
      if(id <= 3)
        return "a" + (7 - id);
      else {
        id -= 4;
        if(id <= 6)
          return "t" + id;
        else
          return "a" + (10-id); 
      }
    } 
  }
  public asmNode getColResult(color co, String resul, String tp, VarRegManager VRM) {
    // is not spilled return the register; else return the riscS,tempvar(t5)
    var res = col2i(co);
    if(res >= 0) {
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
    if(res >= 0) {
      return pseudo.builder()
        .strs(new vector<String>("mv", getRegName(res), resul))
      .build();
    } else {
      if(res == -114514191) {
        return null;
      }
      return riscS.builder()
        .op(tp)
        .rs2(resul)
        .rs1("sp")
        .imm(VRM.getOffset(res))
         // rd the spill structure 
        .build();
    }
  }
  public int col2i(color c) {
    if(c.spilled)
      return (c.id+1) * (-1);
    return c.id;
  }
  public int getReg(String name) {
    var res = regs.get(name);
    if(res == null) {
      return -114514191;
    }
    if(res.spilled)
      return (res.id+1) * (-1);
    return res.id;
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
    if(num <= 3)
      return K - num;
    else
      return 16 - num;
  }
  // call arg store used-a;
  public void stCaller() {

  }
  public void stCallee() {

  }

  public void addArg(String name, int num) {
    color c = new color();
    c.spilled = false;
    c.id = anum(num);
    regs.put(name, c);
  }

  public void addReg(String reg, boolean isSpilled) {
    if (regs.get(reg) != null && regs.get(reg).id == -114514) {
      return;
    }
    color c = new color();
    if (isSpilled) {
      c.spilled = true;
      c.id = findSpilled();
    } else {
      c.spilled = false;
      c.id = findCol();
    }
    System.err.println(reg + "colored " + c.spilled + " " + c.id);
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
      spillregs.add(col.id);
    }
  }
}
