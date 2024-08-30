package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscJ extends asmNode{
  private String label;

  @Override
  public String toString() {
    return "j " + label;
  }
}
