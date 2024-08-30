package juhuh.compiler.frontend;

import juhuh.compiler.ir.irNode;
import juhuh.compiler.ir.irRoot;
import juhuh.compiler.ir.ins.*;
import juhuh.compiler.ir.stmt.*;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.util.error.error;

public interface irVisitor {
  public void visit(irNode node) throws error;
  public void visit(irRoot node) throws error;

  public void visit(irFuncDef node) throws error;
  public void visit(irGlobalDef node) throws error;
  public void visit(irStrDef node) throws error;

  public void visit(irAlloca node) throws error;
  public void visit(irBinary node) throws error;
  public void visit(irBranch node) throws error;
  public void visit(irCall node) throws error;
  public void visit(irGetElement node) throws error;
  public void visit(irIcmp node) throws error;
  public void visit(irLoad node) throws error;
  public void visit(irJump node) throws error;
  public void visit(irRet node) throws error;
  public void visit(irSelect node) throws error;
  public void visit(irStore node) throws error;

  public void visit(irBlock node) throws error;
}
