package juhuh.compiler.util;

import java.util.ArrayList;
import java.util.List;

public class vector<E> extends ArrayList<E> {
  public vector() {
    super();
  }

  public vector(vector<E> tags) {
    super((ArrayList<E>)tags);
  }
  
  public vector(List<E> tags) {
    super(tags);
  }

  public vector(E tag) {
    super();
    add(tag);
  }
  
  @SafeVarargs
  public vector(E tag, E... vectag) {
    super();
    add(tag);
    for (E tag2 : vectag)
      add(tag2);
  }
  public E getlst() {
    if(size() == 0) return null;
    return get(size() - 1);
  }
  public void rmlst() {
    if(size() == 0) return;
    remove(size() - 1);
  }
  public String toString(String pre, String spr, String suf) {
    if(size() == 0) {
      return "";
    }
    String res = "";
    boolean first = true;
    for(E tag : this) {
      if(first) {
        first = false;
      } else {
        res += spr;
      }
      res += pre + tag.toString();
    }
    res += suf;
    return res;
  }
}
