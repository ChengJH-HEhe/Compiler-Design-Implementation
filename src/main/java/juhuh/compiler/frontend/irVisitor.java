package juhuh.compiler.frontend;

import juhuh.compiler.ir.irNode;
import juhuh.compiler.ir.irRoot;
import juhuh.compiler.ir.def.irFuncDecl;
import juhuh.compiler.ir.ins.*;
import juhuh.compiler.ir.stmt.*;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.util.error.error;

public interface irVisitor<T> {
  public T visit(irNode node) throws error;
  public T visit(irRoot node) throws error;

  public T visit(irFuncDecl node) throws error;
  public T visit(irFuncDef node) throws error;
  public T visit(irGlobalDef node) throws error;
  public T visit(irStrDef node) throws error;

  public T visit(irAlloca node) throws error;
  public T visit(irBinary node) throws error;
  public T visit(irBranch node) throws error;
  public T visit(irCall node) throws error;
  public T visit(irGetElement node) throws error;
  public T visit(irIcmp node) throws error;
  public T visit(irLoad node) throws error;
  public T visit(irJump node) throws error;
  public T visit(irRet node) throws error;
  public T visit(irSelect node) throws error;
  public T visit(irStore node) throws error;

  public T visit(irBlock node) throws error;
}
