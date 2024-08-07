package frontend;

import ast.*;
import parser.MxBaseVisitor;
import parser.MxParser;
import util.*;

import org.antlr.v4.runtime.ParserRuleContext;
// import ast.binaryExprNode.binaryOpType;
// import ast.cmpExprNode.cmpOpType;
import ast.node.astNode;
import ast.node.def.*;

public class astBuilder extends MxBaseVisitor<astNode> {

  private globalScope gScope;

  public astBuilder(globalScope gScope) {
    this.gScope = gScope;
  }

  @Override
  public astNode visitProgram(MxParser.ProgramContext ctx) {
    //
    var defs = new vector<astDefNode>();
    for (var def : ctx.children) {
      if (def instanceof MxParser.ClassDefContext || def instanceof MxParser.FuncDefContext) {
        defs.add((astDefNode) visit(def));
      } else if (def instanceof MxParser.VarDefContext) {
        var varDef = (astVarDefStmtNode) visit(def);
        for (var vartmp : varDef.getDef())
          defs.add(visit(vartmp));
      }
    }
    return new astRoot.builder()
    .parent(null)
    .position(new position(ctx.getStart()))
    .defs(defs)
    .build();
  }

  @Override
  public astNode visitClassDef(MxParser.ConstrContext ctx) {
    structDefNode structDef = new structDefNode(new position(ctx), ctx.Identifier().toString());
    ctx.varDef().forEach(vd -> structDef.varDefs.add((varDefStmtNode) visit(vd)));
    return structDef;
  }

  @Override
  public astNode visitMainFn(MxParser.MainFnContext ctx) {
    FnRootNode root = new FnRootNode(new position(ctx));
    intType = root.intType;
    boolType = root.boolType;
    gScope.addType("int", intType, root.getPos());
    gScope.addType("bool", boolType, root.getPos());

    if (ctx.suite() != null) {
      for (ParserRuleContext stmt : ctx.suite().statement())
        root.stmts.add((StmtNode) visit(stmt));
    }
    return root;
  }

  @Override
  public astNode visitVarDef(MxParser.VarDefContext ctx) {
    ExprNode expr = null;
    String typeName;
    if (ctx.type().Int() != null)
      typeName = ctx.type().Int().toString();
    else
      typeName = ctx.type().Identifier().toString();
    if (ctx.expression() != null)
      expr = (ExprNode) visit(ctx.expression());

    return new varDefStmtNode(typeName,
        ctx.Identifier().toString(),
        expr, new position(ctx));
  }

  @Override
  public astNode visitSuite(MxParser.SuiteContext ctx) {
    blockStmtNode node = new blockStmtNode(new position(ctx));

    if (!ctx.statement().isEmpty()) {
      for (ParserRuleContext stmt : ctx.statement()) {
        StmtNode tmp = (StmtNode) visit(stmt);
        if (tmp != null)
          node.stmts.add(tmp);
      }
    }
    return node;
  }

  @Override
  public astNode visitBlock(MxParser.BlockContext ctx) {
    return visit(ctx.suite());
  }

  @Override
  public astNode visitVardefStmt(MxParser.VardefStmtContext ctx) {
    return visit(ctx.varDef());
  }

  @Override
  public astNode visitIfStmt(MxParser.IfStmtContext ctx) {
    StmtNode thenStmt = (StmtNode) visit(ctx.trueStmt), elseStmt = null;
    ExprNode condition = (ExprNode) visit(ctx.expression());
    if (ctx.falseStmt != null)
      elseStmt = (StmtNode) visit(ctx.falseStmt);
    return new ifStmtNode(condition, thenStmt, elseStmt, new position(ctx));
  }

  @Override
  public astNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
    ExprNode value = null;
    if (ctx.expression() != null)
      value = (ExprNode) visit(ctx.expression());
    return new returnStmtNode(value, new position(ctx));
  }

  @Override
  public astNode visitPureExprStmt(MxParser.PureExprStmtContext ctx) {
    return new exprStmtNode((ExprNode) visit(ctx.expression()), new position(ctx));
  }

  @Override
  public astNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
    return null;
  }

  @Override
  public astNode visitAtomExpr(MxParser.AtomExprContext ctx) {
    return visit(ctx.primary());
  }

  @Override
  public astNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
    ExprNode lhs = (ExprNode) visit(ctx.expression(0)),
        rhs = (ExprNode) visit(ctx.expression(1));
    binaryOpType biOp = ctx.Plus() != null ? binaryOpType.add : (ctx.Minus() != null ? binaryOpType.sub : null);
    cmpOpType cmpOp = ctx.Equal() != null ? cmpOpType.eq : (ctx.NotEqual() != null ? cmpOpType.neq : null);

    return biOp != null ? (new binaryExprNode(lhs, rhs, biOp, intType, new position(ctx)))
        : (new cmpExprNode(lhs, rhs, cmpOp, boolType, new position(ctx)));
  }

  @Override
  public astNode visitAssignExpr(MxParser.AssignExprContext ctx) {
    ExprNode lhs = (ExprNode) visit(ctx.expression(0)),
        rhs = (ExprNode) visit(ctx.expression(1));
    return new assignExprNode(lhs, rhs, new position(ctx));
  }

  @Override
  public astNode visitPrimary(MxParser.PrimaryContext ctx) {
    if (ctx.expression() != null)
      return visit(ctx.expression());
    else if (ctx.literal() != null)
      return visit(ctx.literal());
    else
      return new varExprNode(ctx.Identifier().toString(), new position(ctx.Identifier()));
  }

  @Override
  public astNode visitLiteral(MxParser.LiteralContext ctx) {
    return new constExprNode(Integer.parseInt(ctx.DecimalInteger().toString()),
        intType, new position(ctx));
  }

}
