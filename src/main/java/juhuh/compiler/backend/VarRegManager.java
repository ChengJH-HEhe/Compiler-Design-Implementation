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
      return Var2Id.get(var) + maxArgs;
    {
      Var2Id.put(var, (size++));
      return 0;
    }
  }
  public int getArgM(int i){
    return size + i - 8;
  }

  // 16 multiply
  public int getSize() {
    maxArgs += ((size + maxArgs) % 4 == 0 ? 0 : (4 - (size + maxArgs)%4));
    return size + maxArgs;
  }

  // difference? call: caller; define: callee
  // before call, caller store value to the args place
  // when calling, callee store args to the localptr
  void storeT() {
    // curT[i] = size + i
    for (int i = 0; i < 5; ++i) {
      curB.add(riscS.builder()
          .op("sw")
          .rs2("t" + i)
          .imm((-(i+1)) * 4)
          .rs1("sp")
          .build());
    }
    size += 5;
  }

  void storeA(vector<String> varArr) {
    for (int i = 8; i < varArr.size(); ++i) {
      String arg = varArr.get(i);
      add(arg);
    }
  }
  void argsInc(int sz){
    if(sz > 8)
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
    } else if (name.getBytes()[0] == '@') {
      curB.add(pseudo.builder()
          .strs(new vector<String>("la", "t4", name.substring(1)))
          .build());
      curB.add(riscL.builder()
          .op(tp)
          .rd(reg)
          .imm(0)
          .rs1("t4")
          .build());
    }
  }

  // t0-6 loop %7 int is the reg id
  // call add args, return the vecIns add before call
  public void addvec(vector<String> varArr, vector<String> vartype) {
    for (int curId = 0; curId < varArr.size(); ++curId) {
      var arg = varArr.get(curId);
      String tmpvar = "t3";
      if (curId < 8)
        tmpvar = "a" + curId;
      // load to the tmpvar
      if (arg.getBytes()[0] == '%' || arg.getBytes()[0] == '@') {
        ptr2reg(arg, tmpvar, vartype.get(curId).equals("i1") ? "lb" : "lw");
      } else {
        curB.add(pseudo.builder()
            .strs(new vector<String>("li", tmpvar, arg))
            .build());
      }
      if (curId >= 8) {
        // store tmpvar to sp + (curId - 8) * 4
        curB.add(riscS.builder()
            .op(vartype.get(curId).equals("i1") ? "sb" : "sw")
            .rs2(tmpvar)
            .imm((curId - 8) * 4)
            .rs1("sp")
            .build());
      }
    }
  }

  // callee do nothing knows the args & the sp+i

}
