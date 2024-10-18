package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.util.vector;
import juhuh.compiler.backend.asmBuilder;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter


public class riscRI extends asmNode{
  private String op;
  private String rd;
  private String rs1;
  private int imm;

  @Override
  public String toString() {
    if (asmBuilder.isImm(rs1) ) {
      var psu = pseudo.builder()
        .strs(new vector<String>("li", rd))
        .build();
      switch (op) {
        case "div":
          if (imm == 0)
            psu = null;
          else
            psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) / imm));
          break;
        case "rem":
          if (imm == 0)
            psu = null;
          else
            psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) % imm));
          break;
        case "sll":
        case "slli":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) << imm));
          break;
        case "sra":
        case "srai":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) >> imm));
          break;
        case "sub":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) - imm));
          break;
        case "add":
        case "addi":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) + imm));
          break;
        case "mul":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) * imm));
          break;
        case "and":
        case "andi":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) & imm));
          break;
        case "xor":
        case "xori":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) ^ imm));
          break;
        case "or":
        case "ori":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) | imm));
          break;
      }
      if (psu != null)
        return psu.toString();
    } 
    return op + " " + rd + ", " + rs1 + ", " + imm;
  }
}
