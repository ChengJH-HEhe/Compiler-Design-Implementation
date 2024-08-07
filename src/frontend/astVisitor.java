package frontend;
import ast.node.astNode;
import ast.node.astRoot;

public interface astVisitor<T> {
  public T visit(astNode node) throws Error;
  public T visit(astRoot node) throws Error;

  // public T visit(astFuncDefNode node) throws Error;
  // public T visit(astClassDefNode node) throws Error;
  // public T visit(astVarDefnNode node) throws Error;
  
  // public T visit(astNewExprNode node) throws Error;
  // public T visit(astMemberExprNode node) throws Error;
  // public T visit(astCallExprNode node) throws Error;
  // public T visit(astArrayExprNode node) throws Error;
  // public T visit(astPostUnaryExprNode node) throws Error;
  // public T visit(astPreUnaryExprNode node) throws Error;
  // public T visit(astBinaryExprNode node) throws Error;
  // public T visit(astConditionalExprNode node) throws Error;
  // public T visit(astAssignExprNode node) throws Error;
  // public T visit(astAtomExprNode node) throws Error;

  // public T visit(astBlockStmtNode node) throws Error;
  // public T visit(astIfStmtNode node) throws Error;
  // public T visit(astForStmtNode node) throws Error;
  // public T visit(astWhileStmtNode node) throws Error;
  // public T visit(astContinueStmtNode node) throws Error;
  // public T visit(astBreakStmtNode node) throws Error;
  // public T visit(astReturnStmtNode node) throws Error;
  // public T visit(astExprStmtNode node) throws Error;
  public T visit(astVarDefStmtNode node) throws Error;
  // public T visit(astEmptyStmtNode node) throws Error;
}
