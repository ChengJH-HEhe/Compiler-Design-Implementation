package juhuh.compiler.ir.stmt;

import java.util.HashMap;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.ins.*;
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
  // this-phi -> this'lst-def, this-lst def -> domF's phi 
  HashMap<String, irPhi> phi; // those phi added first
  // stack stored in the block? curBlock's new Phi add in the ptr2reg
  // when exited ptr2reg is exited too
  private HashMap<String, String> regs, mtp; // store the last def

  private HashMap<String, String> ptr2reg; // store the first load

  private boolean unreachable;

  public void setFirstLoad(String ptr, String reg) {
    if(ptr2reg == null) 
      ptr2reg = new HashMap<>();
    if(ptr.charAt(0) == '%')
      ptr2reg.put(reg, ptr); 
    // if not DomF, replace it by faBlock's lastDef
  }
  public String findFirstLoad(String reg) {
    if(ptr2reg == null) 
      ptr2reg = new HashMap<>();
    if(ptr2reg.containsKey(reg))
      return ptr2reg.get(reg);
    else {
      // find val
      return null;
    }
  }
  public void setVal(String ptr, String reg, String tp) {
    if(regs == null) {
      regs = new HashMap<>();
      mtp = new HashMap<>();
    }
    regs.put(ptr, reg);
    mtp.put(ptr, tp);
  }
  public String findVal(String ptr) {
    if(regs == null)  {
      regs = new HashMap<>();
      mtp = new HashMap<>();
    }
    if(regs.containsKey(ptr))
      return regs.get(ptr);
    else {
      // find val
      return null;
    }
  }
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
    if(phi != null)
      for(var p : phi.values()){
        s.append("  " + p.toString() + "\n");
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
