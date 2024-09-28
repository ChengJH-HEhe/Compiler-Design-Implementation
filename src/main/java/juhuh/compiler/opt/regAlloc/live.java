package juhuh.compiler.opt.regAlloc;

import java.util.HashMap;
import java.util.HashSet;

public class live {
  public HashSet<String> in, out, def, use;
  void Def(HashMap<String,Integer> reg, String d) {
    if(reg.containsKey(d)) {
      reg.put(d, reg.get(d) + 1);
    } else {
      reg.put(d, 1);
    }
    if(def == null) {
      def = new HashSet<>();
    }
    def.add(d);
  }
  void Use(HashMap<String,Integer> reg, String u) {
    if(reg.containsKey(u)) {
      reg.put(u, reg.get(u) + 1);
    } else {
      reg.put(u, 1);
    }
    if(use == null) {
      use = new HashSet<>();
    }
    use.add(u);
  }
  void Out(String o) {
    if(out == null) {
      out = new HashSet<>();
    }
    out.add(o);
  }
  void In(String i) {
    if(in == null) {
      in = new HashSet<>();
    }
    in.add(i);
  }
}
