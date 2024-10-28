package juhuh.compiler.ast.node;
import juhuh.compiler.ast.node.def.astDefNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.globalScope;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class astRoot extends astNode{
  private globalScope gScope;
  private final vector<astDefNode> defs;
  public void addDef(astDefNode def) {
    defs.add(def);
  }
  @Override
  public String toString() {
    String str = defs.toString();
    return str;
  }

  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
  @Override
  public boolean hasCall() {
    return false;
  }
}
