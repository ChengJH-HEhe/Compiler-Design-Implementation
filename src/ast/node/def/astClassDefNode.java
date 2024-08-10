package ast.node.def;
import ast.node.stmt.scopeStmt;
import frontend.astVisitor;
import util.Scope;
import util.vector;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astClassDefNode extends astDefNode implements scopeStmt{
  private Scope FuncScope;
  private Scope VarScope;
  private final astFuncDefNode constructor;
  private final vector<astFuncDefNode> methods;
  private final vector<astVarDefNode> fields;
  @Override
  public String toString() {
    return getName() + "{" + constructor.toString() + "\n" + methods.toString() + "\n" +fields.toString() + "}";
  }
  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
