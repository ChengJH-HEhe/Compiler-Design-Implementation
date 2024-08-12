package frontend;
import parser.MxBaseVisitor;
import parser.MxParser;
import util.*;
import util.error.error;

// import ast.binaryExprNode.binaryOpType;
// import ast.cmpExprNode.cmpOpType;
import ast.node.astNode;
import ast.node.astRoot;
import ast.node.def.*;
import ast.node.stmt.*;
import ast.node.expr.*;
import ast.node.type.*;

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
        for (var vartmp : varDef.getArray())
          defs.add(vartmp);
      }
    }
    return astRoot.builder()
        .parent(null)
        .pos(new position(ctx.start))
        .defs(defs)
        .build();
  }

  @Override
  public astNode visitClassDef(MxParser.ClassDefContext ctx) {
    astConstrNode constructor = null;
    vector<astFuncDefNode> methods = new vector<astFuncDefNode>();
    vector<astVarDefNode> fields = new vector<astVarDefNode>();
    for (var def : ctx.children) {
      if (def instanceof MxParser.FuncDefContext)
        methods.add((astFuncDefNode) visit(def));
      else if (def instanceof MxParser.VarDefContext)
        fields.add((astVarDefNode) visit(def));
      else if (def instanceof MxParser.ConstrContext) {
        if (constructor != null)
          throw new error("Class has another constructor" + constructor.toString());
        constructor = (astConstrNode) visit(def);
        if (constructor.getClassName() != ctx.Identifier().getText())
          throw new error(
              "Class name " + ctx.Identifier().getText() + "not constructor's " + constructor.getClassName());
      }
    }
    var ClassDef = astClassDefNode.builder()
        .name(ctx.Identifier().getText())
        .constructor(constructor)
        .methods(methods)
        .fields(fields)
        .build();
    if (constructor != null)
      constructor.setParent(ClassDef);
    for (var method : methods)
      method.setParent(ClassDef);
    for (var field : fields)
      field.setParent(ClassDef);
    return ClassDef;
  }

  @Override
  public astNode visitConstr(MxParser.ConstrContext ctx) {
    String name = ctx.Identifier().getText();
    var constr = astConstrNode.builder()
        .className(name)
        .block((astBlockStmtNode) visit(ctx.block()))
        .build();
    constr.getBlock().setParent(constr);
    return constr;
  }

  // funcdef
  @Override
  public astNode visitFuncDef(MxParser.FuncDefContext ctx) {
    FuncInfo info = new FuncInfo();
    info.name = ctx.Identifier().getText();
    if (ctx.returnType().Void() != null) {
      info.retType = null;
    } else {
      info.retType = ((astTypeNode) visitType(ctx.returnType().type())).getInfo();
    }
    var arglist = ctx.arglist();
    var args = new vector<astVarDefNode>();
    if (arglist != null) {
      // type identifier all vector
      var vectype = arglist.type();
      var vecname = arglist.Identifier();
      assert (arglist.type().size() == arglist.Identifier().size());
      for (int i = 0; i < arglist.type().size(); ++i) {
        args.add(astVarDefNode.builder()
            .name(vecname.get(i).getText())
            .type((astTypeNode) visit(vectype.get(i)))
            .build());
      }
    }
    var blocknode = (astBlockStmtNode) (visit(ctx.block()));
    var Funcdef = astFuncDefNode.builder()
        .info(info)
        .block(blocknode)
        .args(args)
        .build();
    blocknode.setParent(Funcdef);
    return Funcdef;
  }

  @Override
  // VarDef
  public astNode visitVardefStmt(MxParser.VardefStmtContext ctx) {
    var vecvardef = new vector<astVarDefNode>();
    // name type unit; 1type&2vardefn
    var type = (astTypeNode) visit(ctx.varDef().type());
    var vardefStmt = astVarDefStmtNode.builder()
        .array(vecvardef)
        .build();
    for (var vardef : ctx.varDef().varDefn()) {
      var varDefNode = astVarDefNode.builder()
          .type(type)
          .name(vardef.Identifier().getText())
          .unit((vardef.Assign() != null ? (astExprNode) visit(vardef.expr()) : null))
          .build();
      if (vardef.Assign() != null)
        varDefNode.getUnit().setParent(varDefNode);
      vecvardef.add(varDefNode);
    }
    return vardefStmt;
  }

  @Override
  public astNode visitVarDef(MxParser.VarDefContext ctx) {
    assert (false);
    return new astNode();
  }

  @Override
  public astNode visitBlock(MxParser.BlockContext ctx) {
    var stmts = new vector<astStmtNode>();
    for (var stmt : ctx.stmt()) {
      stmts.add((astStmtNode) visit(stmt));
    }
    var blocknode = astBlockStmtNode.builder()
        .stmts(stmts)
        .build();
    for (var stmt : stmts) {
      stmt.setParent(blocknode);
    }
    return blocknode;
  }

  @Override
  public astNode visitIfStmt(MxParser.IfStmtContext ctx) {
    astStmtNode thenStmt = (astStmtNode) visit(ctx.thenStmt), elseStmt = null;
    astExprNode condition = (astExprNode) visit(ctx.expr());
    if (ctx.elseStmt != null)
      elseStmt = (astStmtNode) visit(ctx.elseStmt);
    var ifstmt = astIfStmtNode.builder()
        .cond(condition)
        .thenStmt(thenStmt)
        .elseStmt(elseStmt)
        .build();
    thenStmt.setParent(ifstmt);
    condition.setParent(ifstmt);
    if (ctx.elseStmt != null)
      elseStmt.setParent(ifstmt);
    return ifstmt;
  }

  @Override
  public astNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
    var expr = (astExprNode) visit(ctx.expr());
    var stmt = (astStmtNode) visit(ctx.stmt());
    var whileStmt = astWhileStmtNode.builder()
        .cond(expr)
        .stmt(stmt)
        .build();
    expr.setParent(whileStmt);
    stmt.setParent(whileStmt);
    return whileStmt;
  }

  @Override
  public astNode visitForStmt(MxParser.ForStmtContext ctx) {

    var cond = (astExprNode) visit(ctx.condExpr);
    var step = (astExprNode) visit(ctx.stepexpr);
    var init = (astStmtNode) visit(ctx.initStmt);
    var stmt = (astStmtNode) visit(ctx.bodystmt);
    var ForStmt = astForStmtNode.builder()
        .init(init)
        .cond(cond)
        .update(step)
        .stmt(stmt)
        .build();
    cond.setParent(ForStmt);
    step.setParent(ForStmt);
    stmt.setParent(ForStmt);
    init.setParent(ForStmt);
    return ForStmt;
  }

  @Override
  public astNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
    astExprNode value = null;
    if (ctx.expr() != null)
      value = (astExprNode) visit(ctx.expr());
    var returnstmt = astReturnStmtNode.builder()
        .expr(value)
        .build();
    if (value != null)
      value.setParent(returnstmt);
    return returnstmt;
  }

  @Override
  public astNode visitBreakStmt(MxParser.BreakStmtContext ctx) {
    return astBreakStmtNode.builder().build();
  }

  @Override
  public astNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {
    return astContinueStmtNode.builder().build();
  }

  @Override
  public astNode visitExprStmt(MxParser.ExprStmtContext ctx) {
    return astPureExprNode.builder()
        .expr((astExprNode) visit(ctx.expr()))
        .build();
  }

  @Override
  public astNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
    return null;
  }
  // literal
  @Override
  public astNode visitLiteral(MxParser.LiteralContext ctx) {
    if (ctx.IntegerConst() != null) {
      return astAtomExprNode.builder()
          .Value(ctx.IntegerConst().getText())
          .type(new typeinfo("int", 0))
          .build();
    } else if (ctx.StringConst() != null) {
      return astAtomExprNode.builder()
          .Value(ctx.StringConst().getText())
          .type(new typeinfo("string", 0))
          .build();
    } else if (ctx.Null() != null) {
      return astAtomExprNode.builder()
          .Value("null")
          .type(new typeinfo("null", 0))
          .build();
    } else if (ctx.True() != null) {
      return astAtomExprNode.builder()
          .Value(ctx.True().getText())
          .type(new typeinfo("bool", 0))
          .build();
    } else if (ctx.False() != null) {
      return astAtomExprNode.builder()
          .Value(ctx.False().getText())
          .type(new typeinfo("bool", 0))
          .build();
    } else {
      return null;
    }
  }

  // atom
  @Override
  public astNode visitAtomExpr(MxParser.AtomExprContext ctx) {
    var prim = ctx.primary();
    if(prim.Identifier() != null) {
      return astAtomExprNode.builder()
          .Value(prim.Identifier().getText())
          .type(new typeinfo("custom", 0))
          .build();
    } else if(prim.literal() != null) {
      return visit(prim.literal());
    } else {
      assert(prim.This() != null);
      return astAtomExprNode.builder()
          .Value("this")
          .type(new typeinfo("this", 0))
          .build();
    }
  }

  @Override
  public astNode visitParenExpr(MxParser.ParenExprContext ctx) {
    return visit(ctx.expr());
  }

  @Override
  public astNode visitConditionalExpr(MxParser.ConditionalExprContext ctx) {
    var cond = (astExprNode) visit(ctx.expr(0));
    var lhs = (astExprNode) visit(ctx.expr(1));
    var rhs = (astExprNode) visit(ctx.expr(2));

    var condexpr = astConditionalExprNode.builder()
        .cond(cond)
        .lhs(lhs)
        .rhs(rhs)
        .build();
    lhs.setParent(condexpr);
    rhs.setParent(condexpr);
    cond.setParent(condexpr);
    return condexpr;
  }

  @Override
  public astNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
    astExprNode lhs = (astExprNode) visit(ctx.expr(0)),
        rhs = (astExprNode) visit(ctx.expr(1));
    String biOp = null;
    if (ctx.Mul() != null)
      biOp = "Mul";
    else if (ctx.Div() != null)
      biOp = "Div";
    else if (ctx.Mod() != null)
      biOp = "Mod";
    else if (ctx.Plus() != null)
      biOp = "Plus";
    else if (ctx.Minus() != null)
      biOp = "Minus";
    else if (ctx.LeftShift() != null)
      biOp = "LeftShift";
    else if (ctx.RightShift() != null)
      biOp = "RightShift";
    else if (ctx.Greater() != null)
      biOp = "Greater";
    else if (ctx.GreaterEqual() != null)
      biOp = "GreaterEqual";
    else if (ctx.Less() != null)
      biOp = "Less";
    else if (ctx.LessEqual() != null)
      biOp = "LessEqual";
    else if (ctx.Equal() != null)
      biOp = "Equal";
    else if (ctx.UnEqual() != null)
      biOp = "UnEqual";
    else if (ctx.And() != null)
      biOp = "And";
    else if (ctx.Xor() != null)
      biOp = "Xor";
    else if (ctx.Or() != null)
      biOp = "Or";
    else if (ctx.LogicAnd() != null)
      biOp = "LogicAnd";
    else if (ctx.LogicOr() != null)
      biOp = "LogicOr";
    var astBinaryExpr = astBinaryExprNode.builder()
        .lhs(lhs).rhs(rhs).op(biOp)
        .build();
    lhs.setParent(astBinaryExpr);
    rhs.setParent(astBinaryExpr);
    return astBinaryExpr;
  }

  // unary
  @Override
  public astNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
    astExprNode expr = (astExprNode) visit(ctx.expr());
    String UOp = null;
    if (ctx.Plus() != null)
      UOp = "Plus";
    else if (ctx.Minus() != null)
      UOp = "Minus";
    else if (ctx.Not() != null)
      UOp = "Not";
    else if (ctx.LogicNot() != null)
      UOp = "LogicNot";
    var astUnaryExpr = astUnaryExprNode.builder()
        .expr(expr)
        .op(UOp)
        .build();
    expr.setParent(astUnaryExpr);
    return astUnaryExpr;
  }

  // preself
  @Override
  public astNode visitPreSelfExpr(MxParser.PreSelfExprContext ctx) {
    astExprNode expr = (astExprNode) visit(ctx.expr());
    String pOp = null;
    if (ctx.Increment() != null)
      pOp = "Increment";
    else if (ctx.Decrement() != null)
      pOp = "Decrement";
    var astPreselfExpr = astPreSelfExprNode.builder()
        .expr(expr)
        .op(pOp)
        .build();
    expr.setParent(astPreselfExpr);
    return astPreselfExpr;
  }

  @Override
  public astNode visitAssignExpr(MxParser.AssignExprContext ctx) {
    astExprNode lhs = (astExprNode) visit(ctx.expr(0)),
        rhs = (astExprNode) visit(ctx.expr(1));
    var AssignExprNode = astAssignExprNode.builder()
        .lhs(lhs).rhs(rhs)
        .build();
    lhs.setParent(AssignExprNode);
    rhs.setParent(AssignExprNode);
    return AssignExprNode;
  }

  // fstr
  @Override
  public astNode visitFStrExpr(MxParser.FStrExprContext ctx) {
    var vecStr = new vector<String>();
    var vecExpr = new vector<astExprNode>();
    var fstr = ctx.formatStr();
    if(fstr.FstringConst() != null) {
      vecStr.add(astAtomExprNode.builder().
      Value(fstr.FstringConst().getText()).build().toString());
      return astFStrExpr.builder()
          .vecStr(vecStr)
          .vecExpr(vecExpr)
          .build();
    }
    vecStr.add(astAtomExprNode.builder().
      Value(fstr.Fstring_l().getText()).build().toString());
    for(var fstr_m : fstr.Fstring_m())
      vecStr.add(astAtomExprNode.builder().
      Value(fstr_m.getText()).build().toString());
    vecStr.add(astAtomExprNode.builder().
      Value(fstr.Fstring_lst().getText()).build().toString());
    for (var expr : fstr.expr()) {
      vecExpr.add((astExprNode) visit(expr));
    }

    var Fstrnode = astFStrExpr.builder()
        .vecStr(vecStr)
        .vecExpr(vecExpr)
        .build();
    for (var expr : vecExpr)
      expr.setParent(Fstrnode);

    return Fstrnode;
  }

  // arrayConst
  @Override
  public astNode visitArrayConst(MxParser.ArrayConstContext ctx) {
    var vec = new vector<astExprNode>();
    for (var expr : ctx.literal()) {
      vec.add((astExprNode) visit(expr));
    }
    var type = vec.get(0).getType();
    for (var expr : vec) {
      if (!expr.getType().equals(type))
        throw new error("ArrayConst type not match");
    }
    var arrayConst = astArrayConstExpr.builder()
        .vec(vec)
        .constType(type)
        .build();
    for (var expr : vec)
      expr.setParent(arrayConst);
    return arrayConst;
  }

  // newarray
  @Override
  public astNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) {
    var lengths = new vector<astExprNode>();
    var type = new typeinfo(ctx.typename().getText(), ctx.LBracket().size());
    boolean inittrue = true;
    for (int i = 0; i < ctx.LBracket().size(); ++i) {
      if (ctx.expr(i) != null) {
        if (!inittrue)
          throw new error("\"" + ctx.getText() + "\"" + "init ends");
        lengths.add((astExprNode) visit(ctx.expr(i)));
      } else {
        inittrue = false;
        lengths.add(null);
      }
    }
    var newarray = astNewArrayExprNode.builder()
        .lengths(lengths)
        .type(type)
        .build();
    for (var newExpr : lengths)
      if (newExpr != null)
        newExpr.setParent(newarray);
    if (ctx.arrayConst() != null)
      newarray.setInit((astArrayConstExpr) visit(ctx.arrayConst()));
    return newarray;
  }

  // newvar
  @Override
  public astNode visitNewVarExpr(MxParser.NewVarExprContext ctx) {
    var type = new typeinfo(ctx.typename().getText(), 0);
    var newarray = astNewArrayExprNode.builder()
        .type(type)
        .build();
    return newarray;
  }

  // call
  @Override
  public astNode visitCallExpr(MxParser.CallExprContext ctx) {
    var func = (astExprNode) visit(ctx.expr(0));
    var args = new vector<astExprNode>();
    for (int i = 1; i < ctx.expr().size(); ++i) {
      args.add((astExprNode) visit(ctx.expr(i)));
    }
    var astCallNode = astCallExprNode.builder()
        .func(func)
        .args(args)
        .build();
    for (int i = 0; i < args.size(); ++i)
      args.get(i).setParent(astCallNode);
    return astCallNode;
  }

  // array
  @Override
  public astNode visitArrayExpr(MxParser.ArrayExprContext ctx) {
    var array = (astExprNode) visit(ctx.expr(0));
    var index = (astExprNode) visit(ctx.expr(1));
    var arrayExpr = astArrayExprNode.builder()
        .array(array)
        .sub(index)
        .build();
    array.setParent(arrayExpr);
    index.setParent(arrayExpr);
    return arrayExpr;
  }

  // member
  @Override
  public astNode visitMemberExpr(MxParser.MemberExprContext ctx) {
    var expr = (astExprNode) visit(ctx.expr());
    var member = ctx.Identifier().getText();
    var membernode = astMemberExprNode.builder()
        .expr(expr)
        .member(member)
        .build();
    expr.setParent(membernode);
    return membernode;
  }
}
