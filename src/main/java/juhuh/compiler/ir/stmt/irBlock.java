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
  String label; // label & terminalstmt must add;
  Scope scope; // scope.parent == parent.scope -> hold back
  irIns terminalstmt, endTerm;
  public void add(irStmt stmt) {
    stmts.add(stmt);
  }
  public void setTerminal(irIns stmt) {
    if(terminalstmt == null)
      terminalstmt = stmt;
  }
  @Override
  public String toString(){
    StringBuilder s = new StringBuilder();
    indent = "  ";
    if(label != null){
      s.append(label + ":\n");
    }
    if(stmts != null)
    for(var stmt : stmts){
      if(stmt instanceof irBlock)
        s.append(stmt.toString() + "\n");
      else
        s.append(super.toString() + stmt.toString() + "\n");
    }
    if(terminalstmt != null){
      s.append(super.toString() + terminalstmt.toString() + "\n");
    } else {
      if(endTerm != null)
        s.append(super.toString() + endTerm.toString() + "\n");
    }
    return s.toString();
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
}
