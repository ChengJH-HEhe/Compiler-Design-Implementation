package juhuh.compiler.ast.node.expr;
import juhuh.compiler.ast.node.astNode;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.Info;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
@lombok.EqualsAndHashCode(callSuper = false)
public class astExprNode extends astNode{
  private Info type;
  private boolean LValue, Member;
  public astExprNode(){
    super();
    LValue = false;
    Member = false;
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
