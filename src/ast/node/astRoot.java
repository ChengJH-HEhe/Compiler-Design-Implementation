package ast.node;
import ast.node.def.astDefNode;
import frontend.astVisitor;
import util.globalScope;
import util.vector;
import util.error.error;

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
}
