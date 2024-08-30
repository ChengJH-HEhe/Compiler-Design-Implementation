package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscR extends asmNode{
  private String op;
  private String rd;
  private String rs1;
  private String rs2;

  @Override
  public String toString() {
    return op + " " + rd + ", " + rs1 + ", " + rs2;
  }
}
