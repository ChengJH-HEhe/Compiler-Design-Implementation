package juhuh.compiler.ir.def;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.ins.irIns;
import juhuh.compiler.ir.ins.irJump;
import juhuh.compiler.ir.ins.irStore;
import juhuh.compiler.ir.stmt.irBlock;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irFuncDef extends irDefNode {
  String retType, fName;
  int id;
  int anonyNum;
  vector<String> paratypelist, paravaluelist;
  irBlock entry, ret;
  public irBlock curBlock;

  LinkedList<irBlock> body;
  private HashMap<String, String> globalVar;
  public boolean unUsed(String ptr) {
    return globalVar == null || !globalVar.containsKey(ptr);
  }
  public void addGlobal(String ptr,String tp) {
    
    if(globalVar == null) {
      globalVar = new HashMap<>();
    }
    globalVar.put(ptr, tp);
  }
  public String tmprename() {
    if(anonyNum == 1283)
      System.err.println("debug");
    return "%_" + (anonyNum++);
  }

  public void add(irBlock b) {
    body.add(b);
  }

  public void add(irBlock nw, irBlock b) {
    ListIterator<irBlock> iterator = body.listIterator();
    if (nw.getLabel().equals("entry")) {
      iterator.add(b);
      return;
    }
    while (iterator.hasNext()) {
      irBlock element = iterator.next();
      if (element.equals(nw)) {
        iterator.add(b);
        return;
      }
    }
    iterator.add(b);
  }

  public void checkRet(irIns terminalstmt) {
    // irStore?
    if (curBlock.getTerminalstmt() == null)
      if (terminalstmt instanceof irStore) {
        {
          curBlock.add(terminalstmt);
          curBlock.setEndTerm(irJump.builder()
              .dest("return" + id)
              .build());
        }
      } else {
        curBlock.setEndTerm(terminalstmt);
      }
    //
  }

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
    if (this.body != null)
      for (var b : this.body) {
        if (b.isUnreachable())
          continue;
        body += b.toString() + "\n";
      }
    return "define " + retType + " @" + fName + "(" + paralist + ") {\n" +
        entry.toString() + "\n" + body + "\n" + ret.toString() + "\n}\n";
  }

  @Override
  public void accept(irVisitor visitor) throws error {
    visitor.visit(this);
  }
}
