package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscRI extends asmNode{
  private String op;
  private String rd;
  private String rs1;
  private String imm;

  @Override
  public String toString() {
    return op + " " + rd + ", " + rs1 + ", " + imm;
  }
}
