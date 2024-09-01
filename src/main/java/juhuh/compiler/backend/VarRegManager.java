package juhuh.compiler.backend;

import java.util.HashMap;

import juhuh.compiler.backend.asm.asmBlock;
import juhuh.compiler.backend.asm.ins.*;
import juhuh.compiler.util.vector;

public class VarRegManager {
  // alloc %.(ptr) or %num = (val) all to sp + id * 4
  // manaager for a
  private HashMap<String, Integer> Var2Id = new HashMap<String, Integer>();
  private int size = 0, maxArgs = 0; // t0~t4
  private asmBlock curB;

  // local start with num = : ; start with alpha, alloca, first scan, add all
  // first add return -1 for func sp member check
  public void setCurB(asmBlock B) {
    curB = B;
  }

  public int add(String var) {
    if (var.getBytes()[0] != '%')
      return 0;
    if (Var2Id.containsKey(var))
      return Var2Id.get(var) + maxArgs + 8;
    {
      Var2Id.put(var, (size++));
      return 0;
    }
  }

  public int getArgM(int i) {
    return i - 8;
  }

  public int getCurSize() {
    return size + maxArgs + 8;
  }

  // 16 multiply
  public int getSize() {
    maxArgs += ((size + maxArgs) % 4 == 0 ? 0 : (4 - (size + maxArgs) % 4));
    return size + maxArgs + 8;
  }

  public int getMaxargs() {
    return maxArgs;
  }

  // difference? call: caller; define: callee
  // before call, caller store value to the args place
  // when calling, callee store args to the localptr
  void store() {
    // curT[i] = size + i
    for (int i = 1; i < 6; ++i) {
      curB.add(riscS.builder()
          .op("sw")
          .rs2("t" + i)
          .imm((-(i + 1)) * 4)
          .rs1("sp")
          .build());
    }
    curB.add(riscS.builder().op("sw")
        .rs2("ra")
        .imm((-1) * 4)
        .rs1("sp")
        .build());
    size += 6;
  }

  void storeA(vector<String> varArr) {
    for (int i = 8; i < varArr.size(); ++i) {
      String arg = varArr.get(i);
      add(arg);
    }
  }

  void argsInc(int sz) {
    if (sz > 8)
      maxArgs = Math.max(maxArgs, sz - 8);
  }

  void ptr2reg(String name, String reg, String tp) {
    if (name.getBytes()[0] == '%') {
      // in sp + id
      curB.add(riscL.builder()
          .op(tp)
          .rd(reg)
          .imm(add(name) * 4)
          .rs1("sp")
          .build());
      curB.add(riscL.builder()
          .op(tp)
          .rd(reg)
          .imm(0)
          .rs1(reg)
          .build());
    } else if (name.getBytes()[0] == '@') {
      curB.add(pseudo.builder()
          .strs(new vector<String>("la", "t4", name.substring(1)))
          .build());
      if (name.getBytes()[1] == '.')
        curB.add(pseudo.builder()
            .strs(new vector<String>("mv", reg, "t4"))
            .build());
      else {
        curB.add(riscL.builder()
            .op(tp)
            .rd(reg)
            .imm(0)
            .rs1("t4")
            .build());
      }
    }
  }

  // t0-6 loop %7 int is the reg id
  // call add args, add before call

  // callee do nothing knows the args & the sp+i

}
