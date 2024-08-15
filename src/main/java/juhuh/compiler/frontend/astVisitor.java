package juhuh.compiler.frontend;
import juhuh.compiler.ast.node.def.*;
import juhuh.compiler.ast.node.expr.*;
import juhuh.compiler.ast.node.stmt.*;

import juhuh.compiler.ast.node.*;

import juhuh.compiler.util.error.*;
public interface astVisitor<T> {
  public T visit(astNode node) throws error;
  public T visit(astRoot node) throws error;

  public T visit(astFuncDefNode node) throws error;
  public T visit(astClassDefNode node) throws error;
  public T visit(astVarDefNode node) throws error;
  public T visit(astConstrNode node) throws error;

  public T visit(astNewArrayExprNode node) throws error;
  public T visit(astCallExprNode node) throws error;
  public T visit(astArrayExprNode node) throws error;
  public T visit(astArrayConstExpr node) throws error;
  public T visit(astMemberExprNode node) throws error;
  

  public T visit(astFStrExpr node) throws error;
  public T visit(astUnaryExprNode node) throws error;
  public T visit(astPreSelfExprNode node) throws error;
  public T visit(astBinaryExprNode node) throws error;
  public T visit(astConditionalExprNode node) throws error;
  public T visit(astAssignExprNode node) throws error;
  public T visit(astAtomExprNode node) throws error;

  public T visit(astBlockStmtNode node) throws error;
  public T visit(astIfStmtNode node) throws error;
  public T visit(astForStmtNode node) throws error;
  public T visit(astWhileStmtNode node) throws error;
  public T visit(astContinueStmtNode node) throws error;
  public T visit(astBreakStmtNode node) throws error;
  public T visit(astReturnStmtNode node) throws error;
  public T visit(astExprStmtNode node) throws error;
  public T visit(astVarDefStmtNode node) throws error;
  public T visit(astEmptyStmtNode node) throws error;
}
