package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscL extends asmNode{
  private String op;
  private String rd;
  private String rs1;
  private int imm;

  @Override
  public String toString() {
    // if(imm > 2047 || imm < -2048) {
    //   return "li" + " " + "t6" + ", " + imm + "\n  " +
    //       "add" + " " + "t6" + ", " + rs1 + ", " + "t6\n" +
    //       op + " " + rd + ", " + "0(t6)";
    // }
    return op + " " + rd + ", " + imm + "(" + rs1 + ")";
  }
}
