package juhuh.compiler.opt.regAlloc;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import juhuh.compiler.backend.VarRegManager;
import juhuh.compiler.backend.asm.*;
import juhuh.compiler.backend.asm.ins.pseudo;
import juhuh.compiler.backend.asm.ins.riscS;
import juhuh.compiler.util.*;
import juhuh.compiler.util.error.error;

public class regCol {
  HashMap<String, color> regs;
  BitSet inUse, spReg;;
  public int spillCount = 0, argsId = 0;
  int K = 25;

  public int getSpillCount(HashSet<String> out) {
    int count = 0;
    for (var str : out) {
      if (regs.get(str) != null && regs.get(str).spilled) {
        if(regs.get(str).id < 0)
          count++;
      }
    }
    return count + Math.max(0, argsId - 8);
  }
  public void setSpillCount(int cnt) {
    spillCount = cnt;
    spReg = new BitSet(cnt);
  }

  public regCol() {
    regs = new HashMap<String, color>();
    inUse = new BitSet(25);
    
  }

  public color getCol(String name) {
    return regs.get(name);
  }

  public int getSpillReg(String name) {
    var res = regs.get(name);
    assert (res.spilled);
    return res.id;
  }

// t0~4 s0~2 a7~4 s3~s11  a3~0
String[] arg = {
  "a0","a1","a2", "a3","a4",
  "t0","t1","t2","t3","t4",
  "s3","s4","s5","s6",
  "s7","s8","s9","s10","s11",
  "s0","s1","s2",
  "a7","a6","a5",
};
  
private int anum(int num) {
  for(int i = 0; i < K; ++i)
    if(arg[i].equals("a" + num))
      return i;
  // lol
  return 233333333;
}
  public String getRegName(int id) {
    if(id < 0) return null;
    return arg[id];
    // if (id <= 11)
    //   return "s" + id;
    // else {
    //   id -= 12;
    //   if (id <= 3)
    //     return "a" + (7 - id);
    //   else {
    //     id -= 4;
    //     if (id <= 4)
    //       return "t" + id;
    //     else{
    //       // assert(false);
    //       return "a" + (8 - id);
    //     }
    //   }
    // }
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
  public HashSet<String> getReg(HashSet<String> in) {
    var retg = new HashSet<String>();
    for(var inReg : in) {
      int num = getReg(inReg);
      if(num >= 0)
        retg.add(getRegName(num));
    }
    return retg;
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
    // for(var in : liveIn)
    //   if(regs.get(in) != null) {
    //     System.err.println(in + " a" + regs.get(in).id);
    //   }
    // System.err.println();
    for (int i = 0; i < spillCount; ++i)
      spReg.set(i);
    for(int i = 0; i < K; ++i)
      inUse.set(i);
    for (String reg : liveIn) {
      if (regs.get(reg) != null) {
        var col = regs.get(reg);
        if (!col.spilled)
          inUse.clear(col.id);
        else
          if(col.id != -114514)
            spReg.clear(col.id);
      }
    }
  }
  public boolean used(int num) {
    return !inUse.get(num);
  }
  private int findCol() {
    
    int res = inUse.nextSetBit(0);
    if(res == -1) {
      throw new error("no more color");
    }
    inUse.clear(res);
    return res;
  }


  private int findSpilled() {
    int res = spReg.nextSetBit(0);
    if(res == -1) {
      throw new error("no more sp");
    }
    spReg.clear(res);
    return res;
  }

  // spill or color after spill (determine with isSpilled)
  
  public void addSpill(String reg) {
    color c = new color();
    c.spilled = true;
    c.id = -114514;
    regs.put(reg, c);
  }

  // change: a'i' shouldn't be allocated.

  public void addArg(String name, int num) {
    color c = new color();
    // if (num <= 3) {
    //   c.spilled = false;
    //   c.id = 19 - (7 - num);
    //   inUse.add(c.id);
    // } else 
    if(num <= 7){
      c.spilled = false;
      c.id = anum(num);
    } else {
      c.spilled = true;
      c.id = num - 8;
    }
    regs.put(name, c);
  }

  public void addReg(String reg, boolean isSpilled) {
    color c = new color();
    if (regs.get(reg) != null && regs.get(reg).id == -114514) {
      // if (reg.equals("%a"))
      //   System.err.println("debug");
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
      System.err.println(reg + " a" + c.id);
    }
    regs.put(reg, c);
  }

  public void eraseReg(String reg) {
  
    if (regs.get(reg) == null) {
      return;
    }
    var col = regs.get(reg);
    if (!col.spilled)
      inUse.set(col.id);
    else {
      if (col.id != -114514)
        spReg.set(col.id);
    }
  }
}
