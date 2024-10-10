package juhuh.compiler.opt.regAlloc;

import java.util.HashMap;
import java.util.HashSet;

public class live {
  public HashSet<String> in, out, def, use;
  public live() {
    in = new HashSet<String>();
    out = new HashSet<String>();
    def = new HashSet<String>();
    use = new HashSet<String>();
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
  void Out(String o) {
    out.add(o);
  }
  void In(String i) {
    in.add(i);
  }
}
