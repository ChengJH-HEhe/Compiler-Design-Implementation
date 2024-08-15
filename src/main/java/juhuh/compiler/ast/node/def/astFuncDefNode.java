package juhuh.compiler.ast.node.def;

import juhuh.compiler.ast.node.stmt.astBlockStmtNode;
import juhuh.compiler.ast.node.stmt.astStmtNode;
import juhuh.compiler.ast.node.stmt.scopeStmt;
import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.FuncInfo;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astFuncDefNode extends astDefNode implements scopeStmt{
  private FuncInfo info;
  private Scope funcScope;
  private final vector<astVarDefNode> args;
  private final astBlockStmtNode block;
  public String getRet() {
    return getName();
  }
  public vector<astStmtNode> getBody() {
    return block.getStmts();
  }

  @Override
  public String toString() {
    return getName() + "(" + (args!=null?args.toString():"" )+ ")" + (block!=null?block.toString():"");
  }
  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}