package ast.node.def;

import ast.node.stmt.astBlockStmtNode;
import ast.node.stmt.astStmtNode;
import ast.node.stmt.scopeStmt;
import frontend.astVisitor;
import util.FuncInfo;
import util.Scope;
import util.vector;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class astFuncDefNode extends astDefNode implements scopeStmt{
  private FuncInfo info;
  private Scope funcscope;
  private final vector<astVarDefNode> args;
  private final astBlockStmtNode block;
  public String getRetType() {
    return getName();
  }
  public vector<astStmtNode> getBody() {
    return block.getStmts();
  }

  @Override
  public String toString() {
    return getName() + "(" + args.toString() + ")" + block.toString();
  }
  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
