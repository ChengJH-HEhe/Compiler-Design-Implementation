package juhuh.compiler.ast.node.def;
import juhuh.compiler.ast.node.stmt.astConstrNode;
import juhuh.compiler.ast.node.stmt.scopeStmt;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.ClassInfo;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astClassDefNode extends astDefNode implements scopeStmt{
  private Scope classScope;
  private final astConstrNode constructor;
  private final vector<astFuncDefNode> methods;
  private final vector<astVarDefNode> fields;
  private final ClassInfo info;
  @Override
  public String toString() {

    return getName() + "{" + (constructor!=null?constructor.toString():"") + "\n" + (methods!=null?methods.toString():"") + "\n" +
    (fields!=null?fields.toString():"") + "}";
  }
  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
