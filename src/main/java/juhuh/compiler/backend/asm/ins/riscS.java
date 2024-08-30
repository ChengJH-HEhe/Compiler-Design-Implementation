package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscS extends asmNode{
  private String op;
  private String rs2;
  private String rs1;
  private int imm;

  @Override
  public String toString() {
    if(imm == -1)
      return op + " " + rs2 + " " + rs1;
    return op + " " + rs2 + ", " + imm + "(" + rs1 + ")";
  }
}
