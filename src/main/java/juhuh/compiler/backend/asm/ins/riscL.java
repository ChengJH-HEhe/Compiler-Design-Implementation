package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscL extends asmNode{
  private String op;
  private String rd;
  private String rs1;
  private String imm;

  @Override
  public String toString() {
    return op + " " + rd + ", " + imm + "(" + rs1 + ")";
  }
}
