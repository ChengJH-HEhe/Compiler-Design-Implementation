package juhuh.compiler.ir.stmt;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.ins.irIns;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irBlock extends irStmt{
  vector<irStmt> stmts;
  irBlock parent;
  String label; // label & terminalstmt must add;
  Scope scope; // scope.parent == parent.scope -> hold back
  irIns terminalstmt;
  int anonyNum;
  public void add(irStmt stmt) {
    stmts.add(stmt);
  }
  @Override
  public String toString(){
    StringBuilder s = new StringBuilder();
    if(label != null){
      s.append(super.toString() + label + ":\n");
    }
    for(var stmt : stmts){
      s.append(super.toString() + stmt.toString() + "\n");
    }
    if(terminalstmt != null){
      s.append(super.toString() + terminalstmt.toString() + "\n");
    }
    return s.toString();
  }
  @Override
  public <T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
