package juhuh.compiler.opt.mem2reg;

public class dom {
  // label, irBlock, domF
  private Integer domF,iDom;
  public dom(Integer iDom, Integer domF) {
    this.iDom = iDom;
    this.domF = domF;
  }
}
