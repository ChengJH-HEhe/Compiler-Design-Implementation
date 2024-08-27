package juhuh.compiler.ir.def;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.stmt.irBlock;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irFuncDef extends irDefNode {
  String retType, fName;
  int anonyNum;
  vector<String> paratypelist, paravaluelist;
  irBlock entry, ret;
  vector<irBlock> body;

  public String tmprename() {
    return "%" + (anonyNum++);
  }

  public void add(irBlock b) {
    body.add(b);
  }

  // public void checkRet(irIns terminalstmt) {

  // if (entry.getTerminalstmt() == null)
  // entry.setTerminalstmt(terminalstmt);
  // else if (body.getlst().getTerminalstmt() == null) {
  // body.getlst().setTerminalstmt(terminalstmt);
  // } else {
  // throw new error("irFuncDef: checkRet: multiple return statement");
  // }
  // }

  @Override
  public String toString() {
    // checkRet(irJump.builder()
    // .dest("return")
    // .build());
    String paralist = "";
    if (this.paratypelist.size() > 0) {
      paralist = this.paratypelist.get(0) + " " + this.paravaluelist.get(0);
      for (int i = 1; i < this.paratypelist.size(); i++) {
        paralist += ", " + this.paratypelist.get(i) + " " + this.paravaluelist.get(i);
      }
    }
    String body = "";
    if (this.body != null) {
      boolean first = true;
      for (var b : this.body) {
        if(fName == "main" && first)
          first = false;
        else
          body += b.toString() + "\n";
      }
    }
    return "define " + retType + " @" + fName + "(" + paralist + ") {\n" +
        entry.toString() + "\n" + body + "\n" + ret.toString() + "\n}\n";
  }

  @Override
  public <T> T accept(irVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
