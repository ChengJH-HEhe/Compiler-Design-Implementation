package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscS extends asmNode{
  private String op;
  private String rs2;
  private String rs1;
  private String imm;

  @Override
  public String toString() {
    return op + " " + rs2 + ", " + imm + "(" + rs1 + ")";
  }
}
