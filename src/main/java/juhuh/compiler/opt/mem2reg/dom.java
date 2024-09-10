package juhuh.compiler.opt.mem2reg;

import juhuh.compiler.util.vector;

@lombok.Setter
@lombok.Getter

public class dom {
  // label, irBlock, domF
  private Integer iDom;
  private vector<Integer> domF;
  public dom(Integer iDom) {
    this.iDom = iDom;
    this.domF = new vector<Integer>();
  }
  public dom(Integer iDom, Integer domF) {
    assert(domF == null);
    this.iDom = iDom;
    this.domF = null;
  }
}
