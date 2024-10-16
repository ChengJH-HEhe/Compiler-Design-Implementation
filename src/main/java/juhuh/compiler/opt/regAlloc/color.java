package juhuh.compiler.opt.regAlloc;

public class color {
  boolean spilled;
  int id;
  public boolean equals(Object o) {
    if (o instanceof color) {
      color c = (color) o;
      return c.id == id && c.spilled == spilled;
    }
    return false;
  }
}
