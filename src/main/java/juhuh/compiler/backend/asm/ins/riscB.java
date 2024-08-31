package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscB extends asmNode{
  private String op;
  private String rs2;
  private String rs1;
  private String label;

  @Override
  public String toString() {
    return op + " " + rs1 + ", " + rs2 + ", " + label;
  }
}
