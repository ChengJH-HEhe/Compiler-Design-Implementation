package juhuh.compiler.opt.regAlloc;

import java.util.HashMap;
import java.util.HashSet;

import juhuh.compiler.ir.irNode;

public class live {
  public HashSet<String> in, out, def, use, sp;
  public irNode node;
  public live(irNode node) {
    in = new HashSet<String>();
    out = new HashSet<String>();
    def = new HashSet<String>();
    use = new HashSet<String>();
    sp = new HashSet<String>();
    this.node = node;
  }
  void spReg(String ou) {
    sp.remove(ou);
  }
  void Def(HashMap<String,Integer> reg, int dep, String d) {
    if(reg.containsKey(d)) {
      reg.put(d, reg.get(d) + dep);
    } else {
      reg.put(d, dep);
    }
    def.add(d);
  }
  void Use(HashMap<String,Integer> reg, int dep, String u) {
    if(reg.containsKey(u)) {
      reg.put(u, reg.get(u) + dep);
    } else {
      reg.put(u, dep);
    }
    use.add(u);
  }
  boolean Out(String o) {
    if(o.equals("%_2"))
      System.err.println("debugggg %_2");
    if(out.contains(o)) 
      return false;
    out.add(o);
    sp.add(o);
    return true;
  }
  boolean In(String i) {
    
    if(in.contains(i))
      return false; 
    in.add(i);
    return true;
  }
}
