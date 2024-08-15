package juhuh.compiler.ast.node.expr;
import juhuh.compiler.ast.node.astNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.Info;
import juhuh.compiler.util.error.error;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class astExprNode extends astNode{
  private Info type;
  private boolean isLValue;
  public astExprNode(){
    super();
    isLValue = false;
  }
  @Override
  public String toString() {
      if(type == null) return "";
      return super.toString() + type.toString();
  }
  @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
      return visitor.visit(this);
    }
  // add info below
}
