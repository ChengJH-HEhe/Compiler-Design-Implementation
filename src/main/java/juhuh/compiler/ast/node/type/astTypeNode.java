package juhuh.compiler.ast.node.type;

import juhuh.compiler.ast.node.astNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.typeinfo;

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
