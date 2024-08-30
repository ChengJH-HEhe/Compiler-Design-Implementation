package juhuh.compiler.backend;

import java.util.HashMap;

import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.backend.asm.ins.pseudo;
import juhuh.compiler.backend.asm.ins.riscL;
import juhuh.compiler.backend.asm.ins.riscS;
import juhuh.compiler.util.vector;

public class VarRegManager {
  // alloc %.(ptr) or %num = (val) all to sp + id * 4
  // manaager for a
  private HashMap<String, Integer> Var2Id;
  private int size = 0;
  public int getOffset(int ptr) {
    return ptr;
  }
  // local start with num = : ; start with alpha, alloca, first scan, add all
  public int add(String var) {
    if(Var2Id.containsKey(var)) 
      return Var2Id.get(var);
    { Var2Id.put(var, (size++)); return size;}
  }
  
  // 16 multiply
  public int getSize(){
    return (size + 3) / 4 * 4;
  }
  // difference? call: caller; define: callee 
  // before call, caller store value to the args place 
  // when calling, callee store args to the localptr
  
  // t0-6 loop %7 int is the reg id
  // call add args, return the vecIns add before call
  public vector<asmNode> addvec(vector<String> varArr, vector<String> vartype) {
    vector<asmNode> vec = new vector<asmNode>();
    for(int curId = 0; curId < varArr.size(); ++curId) {
      var arg = varArr.get(curId);
      String tmpvar = "t3";
      if(curId < 8)
        tmpvar = "a" + curId;
      // load to the tmpvar
      if(arg.getBytes()[0] == '%') {
        // in sp + id
        var res = add(arg);
        vec.add(riscL.builder()
            .op(vartype.get(curId).equals("i1")?"lb":"lw")
            .rd(tmpvar)
            .imm(add(arg) * 4)
            .rs1("sp")
          .build());
      } else if(arg.getBytes()[0] == '@') {
        vec.add(pseudo.builder()
            .strs(new vector<String>("la", "t4", arg.substring(1)) )
          .build());
        vec.add(riscL.builder()
            .op(vartype.get(curId).equals("i1")?"lb":"lw")
            .rd(tmpvar)
            .imm(0)
            .rs1("t4")
          .build());
      } else {
        vec.add(pseudo.builder()
            .strs(new vector<String>("li", tmpvar, arg))
          .build());
      }
      if(curId >= 8) {
        // store tmpvar to sp + (curId - 8) * 4
        vec.add(riscS.builder()
            .op(vartype.get(curId).equals("i1")?"sb":"sw")
            .rs2(tmpvar)
            .imm((size++)*4)
            .rs1("sp")
          .build());
      }
    }
    return vec;
  }

  // callee do nothing knows the args & the sp+i

}
