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
    // if(imm > 2047 || imm < -2048) {
    //   return "li" + " " + "t6" + ", " + imm + "\n  " +
    //       "add" + " " + "t6" + ", " + rs1 + ", " + "t6\n" +
    //       op + " " + rs2 + ", " + "0(t6)";
    // }
    return op + " " + rs2 + ", " + imm + "(" + rs1 + ")";
  }
}
