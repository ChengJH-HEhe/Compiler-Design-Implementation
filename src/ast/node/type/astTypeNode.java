package ast.node.type;

import ast.node.astNode;
import frontend.astVisitor;
import util.typeinfo;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astTypeNode extends astNode{
  private typeinfo info;

  @Override
  public String toString() {
    String s = info.getName();
    for(int i = 1; i <= info.getDim(); ++i)
      s += "[]";
    return s;
  }
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
