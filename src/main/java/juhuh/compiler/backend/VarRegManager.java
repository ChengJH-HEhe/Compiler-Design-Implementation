package juhuh.compiler.backend;

import java.util.HashMap;

import juhuh.compiler.util.vector;

public class VarRegManager {
  private HashMap<String, Integer> Var2Id;
  private int size = 0;
  // local start with num = : ; start with alpha, alloca, first scan, add all
  public int add(String var) {
    if(Var2Id.containsKey(var)) 
      return Var2Id.get(var);
    else { Var2Id.put(var, size++); return size;}
  }
  //
  public int incSize(){
    return size++;
  }
  public int getSize(){
    return (size + 3) / 4 * 4;
  }
  // difference? call: caller; define: callee 
  // before call, caller store value to the args place 
  // when calling, callee store args to the localptr 
  public void addvec(vector<String> varArr) {
    
  }

  // callee do nothing knows the args & the sp+i

}
