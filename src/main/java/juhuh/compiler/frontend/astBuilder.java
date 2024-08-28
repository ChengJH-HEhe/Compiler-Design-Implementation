package juhuh.compiler.frontend;
import juhuh.compiler.util.*;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.ClassInfo;
import juhuh.compiler.util.info.FuncInfo;
import juhuh.compiler.util.info.typeinfo;
import juhuh.compiler.ast.node.astNode;
import juhuh.compiler.ast.node.astRoot;
import juhuh.compiler.ast.node.def.*;
import juhuh.compiler.ast.node.stmt.*;
import juhuh.compiler.ast.node.expr.*;
import juhuh.compiler.ast.node.type.*;
import juhuh.compiler.parser.MxBaseVisitor;
import juhuh.compiler.parser.MxParser;

public class astBuilder extends MxBaseVisitor<astNode> {

  @Override
  public astNode visitProgram(MxParser.ProgramContext ctx) {
    //
    var defs = new vector<astDefNode>();
    for (var def : ctx.children) {
      if (def instanceof MxParser.ClassDefContext || def instanceof MxParser.FuncDefContext) {
        defs.add((astDefNode) visit(def));
      } else if (def instanceof MxParser.VardefstmtContext) {
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
    // METHODS, fields in info
    for (var def : ctx.children) {
      if (def instanceof MxParser.FuncDefContext)
        methods.add((astFuncDefNode) visit(def));
      else if (def instanceof MxParser.VardefstmtContext) {
        var varDef = (astVarDefStmtNode) visit(def);
        for (var vartmp : varDef.getArray())
          fields.add(vartmp);  
      }
      else if (def instanceof MxParser.ConstrContext) {
        if (constructor != null)
          throw new error("Multiple Definitions");
        constructor = (astConstrNode) visit(def);
        if (!constructor.getClassName().equals(ctx.Identifier().getText()))
          throw new error("Undefined Identifier");
      }
    }
    String name = ctx.Identifier().getText();
    var ClassDef = astClassDefNode.builder()
        .name(name)
        .constructor(constructor)
        .methods(methods)
        .fields(fields)
        .info(new ClassInfo(name, fields, methods, constructor))
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
  public astNode visitType(MxParser.TypeContext ctx) {
    if(ctx.arrayUnit() != null){
      for(var emp : ctx.arrayUnit()) // 
        if(emp.expr() != null)
            throw new error("Invalid Identifier");
      return astTypeNode.builder()
          .info(new typeinfo(ctx.typename().getText(), ctx.arrayUnit().size()))
          .build();
    }else {
      return astTypeNode.builder()
          .info(new typeinfo(ctx.typename().getText(), 0))
          .build();
    }
  }
  @Override
  public astNode visitFuncDef(MxParser.FuncDefContext ctx) {
    var retType = new typeinfo("",0);
    var arglist = ctx.arglist();
    var args = new vector<astVarDefNode>();
    if (ctx.returnType().Void() != null) {
      retType = SemanticChecker.voidType;
    } else {
      retType = ((astTypeNode) visitType(ctx.returnType().type())).getInfo();
    }    
    var info = FuncInfo.builder()
    .name(ctx.Identifier().getText())
    .retType(retType)
    .argsType(new vector<typeinfo>())
    .build();
    if (arglist != null) {
      // type identifier all vector
      var vectype = arglist.type();
      var vecname = arglist.Identifier();
      assert (arglist.type().size() == arglist.Identifier().size());
      for (int i = 0; i < arglist.type().size(); ++i) {
        var typenode = (astTypeNode) visit(vectype.get(i));
        info.argsType.add(typenode.getInfo());
        args.add(astVarDefNode.builder()
            .name(vecname.get(i).getText())
            .type(typenode)
            .build());
      }
    }
    
    var blocknode = (astBlockStmtNode) (visit(ctx.block()));
    var Funcdef = astFuncDefNode.builder()
        .info(info)
        .block(blocknode)
        .args(args)
        .name(ctx.Identifier().getText())
        .build();
    Funcdef.getBlock().setParent(Funcdef);
    return Funcdef;
  }

  @Override
  // VarDef
  public astNode visitVardefStmt(MxParser.VardefStmtContext ctx) {
    return visit(ctx.vardefstmt());
  }
  public astNode visitVardefstmt(MxParser.VardefstmtContext ctx) {
    var vecvardef = new vector<astVarDefNode>();
    // name type unit; 1type&2vardefn
    var type = (astTypeNode) visit(ctx.type());
    for (var vardef : ctx.varDefn()) {
      var varDefNode = astVarDefNode.builder()
          .type(type)
          .name(vardef.Identifier().getText())
          .unit((vardef.Assign() != null ? (astExprNode) visit(vardef.expr()) : null))
          .build();
      if (vardef.Assign() != null)
        varDefNode.getUnit().setParent(varDefNode);
      
      vecvardef.add(varDefNode);
    }
    var vardefStmt = astVarDefStmtNode.builder()
        .array(vecvardef)
        .build();
    // //System.err.println("vardefStmt size: " + vardefStmt.getArray().size());
    return vardefStmt;
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
    for (var stmt : blocknode.getStmts()) {
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
    ifstmt.getThenStmt().setParent(ifstmt);
    ifstmt.getCond().setParent(ifstmt);
    if (ctx.elseStmt != null)
      ifstmt.getElseStmt().setParent(ifstmt);
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
    whileStmt.getCond().setParent(whileStmt);
    whileStmt.getStmt().setParent(whileStmt);
    return whileStmt;
  }

  @Override
  public astNode visitForStmt(MxParser.ForStmtContext ctx) {
    astExprNode cond = null, step = null;
    if(ctx.condExpr != null)
      cond = (astExprNode) visit(ctx.condExpr);
    if(ctx.stepexpr != null)
      step = (astExprNode) visit(ctx.stepexpr);
    var init = (astStmtNode) visit(ctx.initStmt);
    var stmt = (astStmtNode) visit(ctx.bodystmt);
    var ForStmt = astForStmtNode.builder()
        .init(init)
        .cond(cond)
        .update(step)
        .stmt(stmt)
        .build();
    if(cond != null)
      ForStmt.getCond().setParent(ForStmt);
    if(step != null)
      ForStmt.getUpdate().setParent(ForStmt);
    ForStmt.getStmt().setParent(ForStmt);
    ForStmt.getInit().setParent(ForStmt);
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
      returnstmt.getExpr().setParent(returnstmt);
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
    return astExprStmtNode.builder()
        .expr((astExprNode) visit(ctx.expr()))
        .build();
  }

  @Override
  public astNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
    return new astEmptyStmtNode();
  }
  // literal
  @Override
  public astNode visitLiteral(MxParser.LiteralContext ctx) {
    // int string null bool ->type.getname
    
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
          .type(new typeinfo("null", 0, true))
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
    } else if (ctx.arrayConst() != null){
      return (astArrayConstExpr)visit(ctx.arrayConst());
    } else {
      throw new error("Invalid Identifier");
      // return null;
    }
  }

  // atom
  @Override
  public astNode visitAtomExpr(MxParser.AtomExprContext ctx) {
    var prim = ctx.primary();    
    // 3 type identifier -> only type; 
    if(prim.Identifier() != null) {
      return astAtomExprNode.builder()
          .Value(prim.Identifier().getText())
          .type(new typeinfo("1custom", 0))
          .build();
    } else if(prim.literal() != null) {
      // literal type defined
      return visit(prim.literal());
    } else {
      // this -> class
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
    condexpr.getCond().setParent(condexpr);
    condexpr.getLhs().setParent(condexpr);
    condexpr.getRhs().setParent(condexpr);
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
    astBinaryExpr.getLhs().setParent(astBinaryExpr);
    astBinaryExpr.getRhs().setParent(astBinaryExpr);
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
    else if(ctx.Increment()!=null)
      UOp = "Increment";
    else if(ctx.Decrement() != null)
      UOp = "Decrement";
    var astUnaryExpr = astUnaryExprNode.builder()
        .expr(expr)
        .op(UOp)
        .build();
    astUnaryExpr.getExpr().setParent(astUnaryExpr);
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
    astPreselfExpr.getExpr().setParent(astPreselfExpr);
    return astPreselfExpr;
  }

  @Override
  public astNode visitAssignExpr(MxParser.AssignExprContext ctx) {
    astExprNode lhs = (astExprNode) visit(ctx.expr(0)),
        rhs = (astExprNode) visit(ctx.expr(1));
    var AssignExprNode = astAssignExprNode.builder()
        .lhs(lhs).rhs(rhs)
        .build();
    AssignExprNode.getLhs().setParent(AssignExprNode);
    AssignExprNode.getRhs().setParent(AssignExprNode);
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
      Value(fstr.FstringConst().getText().substring(1, fstr.FstringConst().getText().length()-1)).build().toString());
      return astFStrExpr.builder()
          .vecStr(vecStr)
          .vecExpr(vecExpr)
          .build();
    }
    vecStr.add(astAtomExprNode.builder().
      Value(fstr.Fstring_l().getText().substring(2,fstr.Fstring_l().getText().length()-1)).build().toString());
    for(var fstr_m : fstr.Fstring_m())
      vecStr.add(astAtomExprNode.builder().
      Value(fstr_m.getText().substring(1,fstr_m.getText().length()-1)).build().toString());
    vecStr.add(astAtomExprNode.builder().
      Value(fstr.Fstring_lst().getText().substring(1, fstr.Fstring_lst().getText().length()-1)).build().toString());
    for (var expr : fstr.expr()) {
      vecExpr.add((astExprNode) visit(expr));
    }
    var Fstrnode = astFStrExpr.builder()
        .vecStr(vecStr)
        .vecExpr(vecExpr)
        .build();
    for (var expr : Fstrnode.getVecExpr())
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
    var arrayConst = astArrayConstExpr.builder()
        .vec(vec)
        .build();
    for (var expr : arrayConst.getVec())
      expr.setParent(arrayConst);
    return arrayConst;
  }

  // newarray
  @Override
  public astNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) {
    var lengths = new vector<astExprNode>();
    var type = new typeinfo(ctx.typename().getText(), ctx.arrayUnit().size());
    boolean inittrue = true;
    for (int i = 0; i < ctx.arrayUnit().size(); ++i) {
      if (ctx.arrayUnit(i).expr() != null) {
        if (!inittrue)
          throw new error("Invalid Identifier");
        lengths.add((astExprNode) visit(ctx.arrayUnit(i).expr()));
      } else {
        inittrue = false;
      }
    }
    var newarray = astNewArrayExprNode.builder()
        .lengths(lengths)
        .type(type)
        .build();
    for (var newExpr : newarray.getLengths())
      if (newExpr != null)
        newExpr.setParent(newarray);
    if (ctx.arrayConst() != null) {
      if(inittrue) {
        throw new error("Invalid Identifier");
      }
      newarray.setInit((astArrayConstExpr) visit(ctx.arrayConst()));
    }
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
      astCallNode.getArgs().get(i).setParent(astCallNode);
    return astCallNode;
  }

  // array
  @Override
  public astNode visitArrayExpr(MxParser.ArrayExprContext ctx) {
    var array = (astExprNode) visit(ctx.expr());
    if(ctx.arrayUnit().expr() == null)
      throw new error("Invalid Identifier");
    var index = (astExprNode) visit(ctx.arrayUnit().expr());
    var arrayExpr = astArrayExprNode.builder()
        .array(array)
        .sub(index)
        .build();
    arrayExpr.getArray().setParent(arrayExpr);
    arrayExpr.getSub().setParent(arrayExpr);
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
    membernode.getExpr().setParent(membernode);
    return membernode;
  }
}
