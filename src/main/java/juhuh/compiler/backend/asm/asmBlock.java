package juhuh.compiler.backend.asm;

import juhuh.compiler.backend.asm.ins.pseudo;
import juhuh.compiler.backend.asm.ins.riscR;
import juhuh.compiler.backend.asm.ins.riscRI;
import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmBlock extends asmNode {
  private String label;
  private vector<asmNode> nodes;

  public void add(asmNode ins) {
    nodes.add(ins);
  }

  public void adS(String rd, String rs1, int imm) {
    if (imm > 2047 || imm < -2048) {
      add(pseudo.builder()
          .strs(new vector<String>("li", "t6", Integer.toString(imm)))
          .build());
      add(riscR.builder().op("add")
          .rd(rd)
          .rs1(rs1)
          .rs2("t6")
          .build());
    } else 
    {
      add(riscRI.builder().op("addi")
          .rd(rd)
          .imm(imm)
          .rs1(rs1)
          .build());
    }
  }

  @Override
  public String toString() {
    String s = label + ":\n";
    for (asmNode node : nodes) {
      if(node != null)
        s += "  " + node.toString() + "\n";
    }
    return s;
  }
}
