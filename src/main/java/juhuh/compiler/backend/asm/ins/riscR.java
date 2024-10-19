package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asmBuilder;
import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class riscR extends asmNode {
  private String op;
  private String rd;
  private String rs1;
  private String rs2;

  @Override
  public String toString() {
    var psu = pseudo.builder()
        .strs(new vector<String>("li", rd))
        .build();
    if (asmBuilder.isImm(rs1) && asmBuilder.isImm(rs2)) {
      switch (op) {
        case "div":
          if (asmBuilder.Imm(rs2) == 0)
            psu = null;
          else
            psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) / asmBuilder.Imm(rs2)));
          break;
        case "rem":
          if (asmBuilder.Imm(rs2) == 0)
            psu = null;
          else
            psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) % asmBuilder.Imm(rs2)));
          break;
        case "sll":
        case "slli":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) << asmBuilder.Imm(rs2)));
          break;
        case "sra":
        case "srai":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) >> asmBuilder.Imm(rs2)));
          break;
        case "sub":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) - asmBuilder.Imm(rs2)));
          break;
        case "add":
        case "addi":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) + asmBuilder.Imm(rs2)));
          break;
        case "mul":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) * asmBuilder.Imm(rs2)));
          break;
        case "and":
        case "andi":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) & asmBuilder.Imm(rs2)));
          break;
        case "xor":
        case "xori":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) ^ asmBuilder.Imm(rs2)));
          break;
        case "or":
        case "ori":
          psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1) | asmBuilder.Imm(rs2)));
          break;
      }
      if (psu != null)
        return psu.toString();
      else
        return "";
    }
    else if(asmBuilder.isImm(rs2)) {
      if(op.equals("div") || op.equals("rem") || op.equals("mul")) {
        psu.getStrs().rmlst();
        psu.getStrs().add("t6");
        psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs2)));
        return psu.toString() + "\n  " + op + " " + rd + ", " + rs1 + ", " + "t6";
      }
    } else if(asmBuilder.isImm(rs1)) {
      psu.getStrs().rmlst();
      psu.getStrs().add("t5");
      psu.getStrs().add(Integer.toString(asmBuilder.Imm(rs1)));
      return psu.toString() + "\n  " + op + " " + rd + ", " + "t5" + ", " + rs2;
    }
    return op + " " + rd + ", " + rs1 + ", " + rs2;
  }
}
