package juhuh.compiler.backend.asm;

import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmBlock extends asmNode {
  private vector<String> prefix;
  private String label;
  private vector<asmNode> nodes;

  @Override
  public String toString() {
    String s = "";
    for(var p : prefix) {
      s += p + "\n";
    }
    s += label + ":\n";
    for (asmNode node : nodes) {
      s += node.toString() + "\n";
    }
    return s;
  }
}
