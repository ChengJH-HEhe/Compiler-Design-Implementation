package frontend;


import ast.node.astNode;
import ast.node.astRoot;
import ast.node.def.*;
import ast.node.expr.*;
import ast.node.stmt.*;
import util.ClassInfo;
import util.FuncInfo;
import util.Info;
import util.Scope;
import util.error.error;
import util.globalScope;
import util.typeinfo;
import util.vector;
import util.Scope.ScopeType;

public class SemanticChecker implements astVisitor<String> {
  private Scope curS;
  private globalScope gScope;
  // builtin types
  typeinfo voidType = new typeinfo("void", 0);
  typeinfo intType = new typeinfo("int", 0);
  typeinfo boolType = new typeinfo("bool", 0);
  typeinfo stringType = new typeinfo("string", 0);
  typeinfo nullType = new typeinfo("null", 0);
  typeinfo thisType = new typeinfo("this", 0);

  // builtin funcs
  FuncInfo printFunc = new FuncInfo("print", voidType, stringType);
  FuncInfo printlnFunc = new FuncInfo("println", voidType, stringType);
  FuncInfo printIntFunc = new FuncInfo("printInt", voidType, intType);
  FuncInfo printlnIntFunc = new FuncInfo("printlnInt", voidType, intType);
  FuncInfo getStringFunc = new FuncInfo("getString", stringType);
  FuncInfo getIntFunc = new FuncInfo("getInt", intType);
  FuncInfo toStringFunc = new FuncInfo("toString", stringType, intType);
  FuncInfo[] builtinFuncs = { printFunc, printlnFunc, printIntFunc, printlnIntFunc, getStringFunc, getIntFunc,
    toStringFunc };
  FuncInfo arraySizeFunc = new FuncInfo("size", intType);
  FuncInfo stringLengthFunc = new FuncInfo("length", intType);
  FuncInfo stringSubstringFunc = new FuncInfo("substring", stringType, intType, intType);
  FuncInfo stringParseintFunc = new FuncInfo("parseInt", intType);
  FuncInfo stringOrdFunc = new FuncInfo("ord", intType, intType);
  
  ClassInfo stringClass = new ClassInfo("string", new vector<FuncInfo>(stringLengthFunc, stringSubstringFunc, stringParseintFunc, stringOrdFunc));
  public SemanticChecker(globalScope gScope) {
    curS = this.gScope = gScope;
  }

  public void enter(Scope scope) {
    if (gScope == null)
      gScope = (globalScope) scope;
    curS = scope;
  }

  public void exit() {
    curS = curS.parentScope();
  }
  boolean checkValidType(Info Type) {
    if(! (Type instanceof typeinfo)) return false;
    var type = (typeinfo)Type;
    return (type.isBuiltin() && (type.getName().equals("int")
    || type.getName().equals("bool") || type.getName().equals("string"))) 
    || gScope.getTypeFromName(type.getName()) != null;
  }

  @Override
  public String visit(astNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public String visit(astRoot node) throws error {
    // curS = gscope
    for(var def : node.getDefs()) {
      def.accept(this);
    }
    exit();
    return node.toString();
  }

  @Override
  public String visit(astFuncDefNode node) throws error {
    enter(node.getFuncScope());
    for(var stmt : node.getBlock().getStmts()) {
      stmt.accept(this);
    }
    if(node.getRet() != null && !node.getName().equals("main") && !curS.isexited) {
      throw new error("function " + node.getName() + " must return a value");
    }
    exit();
    return node.toString();
  }

  @Override
  public String visit(astClassDefNode node) throws error {
    enter(node.getClassScope());
    visit(node.getConstructor());
    for(var method : node.getMethods()) {
      method.accept(this);
    }
    exit();
    return node.toString();
  }

  @Override
  public String visit(astVarDefNode node) throws error {
    if(!checkValidType(node.getType().getInfo())) {
      throw new error("invalid type " + node.getType().getInfo().getName());
    }
    curS.defineVariable(node.getName(), node.getType().getInfo());
    if(node.getUnit() != null) {
      node.getUnit().accept(this);
      var info = node.getUnit().getType();
      if(info instanceof typeinfo && !info.equals(node.getType().getInfo())) {
        throw new error("type mismatch init:" + info.getName() + " var:" + node.getType().getInfo().getName());
      }
    }
    return node.toString();
  }

  @Override
  public String visit(astNewArrayExprNode node) throws error {
    if(!checkValidType(node.getType())
     || (((typeinfo)node.getType()).getDim() == 0 && ((typeinfo)node.getType()).isBuiltin())) {
      throw new error("invalid type " + node.getType().getName());
    }
    for(var length : node.getLengths()) {
      length.accept(this);
      if(!length.getType().equals(new typeinfo("int", 0))) {
        throw new error("invalid initial length type " + length.getType().getName());
      }
    }
    return node.toString();
  }
  @Override
  public String visit(astCallExprNode node) throws error {
    // funcscope name argstype
    // func -> funcinfo
    var funcinfo = curS.containsVariable(((astAtomExprNode)node.getFunc()).getValue(), true);
    if(funcinfo == null || !(funcinfo instanceof FuncInfo)) {
      throw new error("function " + ((astAtomExprNode)node.getFunc()).getValue() + " not found");
    }
    var func = (FuncInfo)funcinfo;
    // args -> type match funcinfo 
    if(func.getArgsType().size() != node.getArgs().size()) {
      throw new error("function " + func.getName() + " expected " + func.getArgsType().size() + " args, found" + node.getArgs().size());
    }
    for(int i = 0; i < node.getArgs().size(); i++) {
      node.getArgs().get(i).accept(this);
      if(!node.getArgs().get(i).getType().equals(func.getArgsType().get(i))) {
        throw new error("function " + func.getName() + " arg " + i + " type mismatch");
      }
    }
    // expr type -> func
    node.setType(func.getRetType());
    node.setLValue(false);
    return node.toString();
  }

  @Override
  public String visit(astArrayExprNode node) throws error {
    node.getArray().accept(this);
    Info arrayInfo = node.getArray().getType();
    if(!(arrayInfo instanceof typeinfo) || ((typeinfo)arrayInfo).getDim() == 0) {
      throw new error("invalid type " + arrayInfo.getName());
    }
    node.getSub().accept(this);
    // sub not int
    if(!node.getSub().getType().equals(new typeinfo("int", 0))) {
      throw new error("invalid index type " + node.getSub().getType().toString());
    }
    node.setType(new typeinfo(arrayInfo.getName(), ((typeinfo)arrayInfo).getDim()-1));
    node.setLValue(true);
    return node.toString();
  }

  @Override
  public String visit(astArrayConstExpr node) throws error {
    for(var expr : node.getVec()) {
      expr.accept(this);
    }
    if(node.getVec().size() == 0) {
      node.setType(new typeinfo("null", 1));
    } else {
      Info type = node.getVec().get(0).getType();
      if(!(type instanceof typeinfo)) {
        throw new error("invalid type " + type.getName());
      }
      for(var expr : node.getVec()) {
        if(!expr.getType().equals(type)) {
          throw new error("type mismatch " + expr.getType().getName() + " " + type.getName());
        }
      }
      node.setType(new typeinfo(type.getName(), ((typeinfo)type).getDim()+1, ((typeinfo)type).isDim_()));
    }
    return node.toString();
  }

  @Override
  public String visit(astMemberExprNode node) throws error {
    node.getExpr().accept(this);
    Info exprInfo = node.getExpr().getType();
    if(!(exprInfo instanceof typeinfo)) {
      throw new error("invalid type " + exprInfo.getName());
    }
    var expr = (typeinfo)exprInfo;
    if(expr.getDim() > 0) {
      if(node.getMember().equals("size")) {
        node.setType(new typeinfo("int", 0));
        node.setLValue(false);
        return node.toString();
      } else {
        throw new error("undefined " + expr.getName() + " array type called member");
      }
    } else {
      var Class = gScope.getTypeFromName(expr.getName());
      if(Class == null || !(Class instanceof ClassInfo)) {
        throw new error("class " + expr.getName() + " not found");
      }
      var classinfo = (ClassInfo)Class;
      var member = classinfo.getVars().get(node.getMember());
      if(member != null) {
        node.setType(member);
        node.setLValue(true);
        return node.toString();
      }
      var method = classinfo.getFuncs().get(node.getMember());
      if(method == null) {
        throw new error("member " + node.getMember() + " not found");
      } else {
        node.setType(method);
        return node.toString();
      }
    }
  }

  @Override
  public String visit(astUnaryExprNode node) throws error {
    // Increment | Decrement | Not | LogicNot | Minus | Plus
    node.getExpr().accept(this);
    var exprType = node.getExpr().getType();
    if(!(exprType instanceof typeinfo)) {
      throw new error("not type " + exprType.getName());
    }
    var Type = (typeinfo)exprType;
    if(node.getOp().equals("LogicNot")) {
      if(!Type.equals(boolType)) {
        throw new error("invalid type " + Type.getName());
      }
      node.setType(boolType);
      node.setLValue(false);
      return node.toString();
    }
    if(!Type.equals(intType))
        throw new error("invalid type " + node.getType().getName());
    if(node.getOp().equals("Increment") ||node.getOp().equals("Decrement")) {
      if(!node.getExpr().isLValue())
        throw new error("invalid rvalue " + node.getExpr().toString());
      node.setType(Type);
      node.setLValue(true);
    } else {
      node.setType(Type);
      node.setLValue(false);
    }
    return node.toString();
  }

  @Override
  public String visit(astPreSelfExprNode node) throws error {
    // notLvalue, int
    node.getExpr().accept(this);
    node.getExpr().accept(this);
    var exprType = node.getExpr().getType();
    if(!(exprType instanceof typeinfo)) {
      throw new error("not type " + exprType.getName());
    }
    var Type = (typeinfo)exprType;
    if(!(Type instanceof typeinfo))
      throw new error("invalid type " + Type.getName());
    if(!Type.equals(intType))
      throw new error("invalid type " + Type.getName() + " for " + node.getOp());
    if(!node.getExpr().isLValue())
      throw new error(node.getExpr().toString() + "not a Lvalue for " + node.getOp());
    node.setType(Type);
    node.setLValue(true);
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public String visit(astBinaryExprNode node) throws error {
    node.getLhs().accept(this);
    node.getRhs().accept(this);
    if(!(node.getLhs().getType() instanceof typeinfo)) {
      throw new error("invalid type " + node.getLhs().getType().getName());
    }
    var type = (typeinfo)node.getLhs().getType();
    if(!type.equals(node.getRhs().getType())) {
      throw new error("type mismatch " + node.getLhs().getType().getName() + " " + node.getRhs().getType().getName());
    }
    if(type.getDim() > 0 || type.equals(nullType)) {
      if(node.getOp().equals("Equal") || node.getOp().equals("UnEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return node.toString();
      } else {
        throw new error("invalid type " + type.getName() + " for " + node.getOp());
      }
    }
    if(type.equals(intType)) {
      if(node.getOp().equals("Plus") || node.getOp().equals("Minus") || node.getOp().equals("Mul") || node.getOp().equals("Div") || node.getOp().equals("Mod")) {
        node.setType(intType);
        node.setLValue(false);
        return node.toString();
      } else if(node.getOp().equals("LeftShift") || node.getOp().equals("RightShift") ||
       node.getOp().equals("And") || node.getOp().equals("Or") || node.getOp().equals("Xor")) {
        node.setType(intType);
        node.setLValue(false);
        return node.toString();
      } else if(node.getOp().equals("Greater") || node.getOp().equals("GreaterEqual") || node.getOp().equals("Less") || node.getOp().equals("LessEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return node.toString();
      } else if(node.getOp().equals("Equal") || node.getOp().equals("UnEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return node.toString();
      } else if(node.getOp().equals("LogicAnd") || node.getOp().equals("LogicOr")) {
        node.setType(boolType);
        node.setLValue(false);
        return node.toString();
      } else {
        throw new error("invalid type " + type.getName() + " for " + node.getOp());
      }
    } else if(type.equals(boolType)) {
      if(node.getOp().equals("LogicAnd") || node.getOp().equals("LogicOr")) {
        node.setType(boolType);
        node.setLValue(false);
        return node.toString();
      } else if(node.getOp().equals("Equal") || node.getOp().equals("UnEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return node.toString();
      } else {
        throw new error("invalid type " + type.getName() + " for " + node.getOp());
      }
    } else if(type.equals(stringType)) {
      if(node.getOp().equals("Plus")) {
        node.setType(stringType);
        node.setLValue(false);
        return node.toString();
      } else if(node.getOp().equals("Equal") || node.getOp().equals("UnEqual") 
      || node.getOp().equals("Greater") || node.getOp().equals("GreaterEqual") 
      || node.getOp().equals("Less") || node.getOp().equals("LessEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return node.toString();
      } else {
        throw new error("invalid type " + type.getName() + " for " + node.getOp());
      }
    } else {
      throw new error("invalid type " + type.getName() + " for " + node.getOp());
    }
    // Mul | Div | Mod Plus | Minus LeftShift | RightShift int
    // Greater | GreaterEqual | Less | LessEqual bool
    // Equal | UnEqual bool
    // And Xor Or bool
    // LogicAnd LogicOr 
  }

  @Override
  public String visit(astConditionalExprNode node) throws error {
    node.getLhs().accept(this);
    node.getRhs().accept(this);
    node.getCond().accept(this);
    if(!(node.getLhs().getType() instanceof typeinfo) || !(node.getRhs().getType() instanceof typeinfo) || !(node.getCond().getType() instanceof typeinfo)) {
      throw new error("invalid type for condExpr " + node.getLhs().getType().getName() + " " + node.getRhs().getType().getName() + " " + node.getCond().getType().getName());
    }
    if(node.getCond().getType().equals(boolType)) {
      if(node.getLhs().getType().equals(node.getRhs().getType())) {
        node.setType(node.getLhs().getType());
        node.setLValue(false);
        return node.toString();
      } else {
        throw new error("type mismatch " + node.getLhs().getType().getName() + " " + node.getRhs().getType().getName());
      }
    } else {
      throw new error("invalid type " + node.getCond().getType().getName());
    }
  }

  @Override
  public String visit(astAssignExprNode node) throws error {
    node.getLhs().accept(this);
    node.getRhs().accept(this);
    if(!(node.getLhs().getType() instanceof typeinfo) || !(node.getRhs().getType() instanceof typeinfo)) {
      throw new error("not type for AssignExpr " + node.getLhs().getType().getName() + " " + node.getRhs().getType().getName());
    }
    if(node.getLhs().isLValue()) {
      if(node.getLhs().getType().equals(node.getRhs().getType())) {
        node.setType(node.getLhs().getType());
        node.setLValue(true);
        return node.toString();
      } else {
        throw new error("type mismatch " + node.getLhs().getType().getName() + " " + node.getRhs().getType().getName());
      }
    } else {
      throw new error("not Lvalue " + node.getLhs().toString());
    }
  }

  @Override
  public String visit(astAtomExprNode node) throws error {
    // Atom 
    if(node.getType().equals(new typeinfo("custom", 0))) {
      var info = curS.containsVariable(node.getValue(), true);
      if(info == null) {
        throw new error("variable " + node.getValue() + " not found");
      }
      if(info instanceof FuncInfo) {
        node.setLValue(false);
      } else if(info instanceof typeinfo) {
        node.setLValue(true);
      } else {
        throw new error("invalid type " + info.getName());
      }
      node.setType(info);
    } else if(node.getType().equals(new typeinfo("this", 0))) {
      var info = curS.findCLASS();
      if(info == null) {
        throw new error(node.toString() + " not in class");
      }
      node.setType(info);
      node.setLValue(false);
    } else {
      node.setLValue(false);
    }
    return node.toString();
  }

  @Override
  public String visit(astBlockStmtNode node) throws error {
    node.setScope(new Scope(curS, null, ScopeType.BLOCK));
    for(var stmt : node.getStmts()) {
      stmt.accept(this);
    }
    exit();
    return node.toString();
  }

  @Override
  public String visit(astIfStmtNode node) throws error {
    node.getCond().accept(this);
    if(!(node.getCond().getType() instanceof typeinfo) || !node.getCond().getType().equals(boolType)) {
      throw new error("invalid type " + node.getCond().getType().getName());
    }
    node.setThenscope(new Scope(curS, null, ScopeType.BLOCK));
    curS = node.getThenscope();
    node.getThenStmt().accept(this);
    exit();
    if(node.getElseStmt() == null) 
      return node.toString();    
    node.setElsescope(new Scope(curS, null, ScopeType.BLOCK));
    curS = node.getElsescope();
    node.getElseStmt().accept(this);
    exit();
    return node.toString();
  }

  @Override
  public String visit(astForStmtNode node) throws error {
    curS = new Scope(curS, null, ScopeType.LOOP);
    node.getInit().accept(this);
    node.getCond().accept(this);
    if(!(node.getCond().getType() instanceof typeinfo) || !node.getCond().getType().equals(boolType)) {
      throw new error("invalid type " + node.getCond().getType().getName());
    }
    node.getUpdate().accept(this);
    if(node.getStmt() instanceof astBlockStmtNode)
      for(var stmt : ((astBlockStmtNode) node.getStmt()).getStmts()) {
        stmt.accept(this);
      }
    else 
      node.getStmt().accept(this);
    exit();
    return node.toString();
  }

  @Override
  public String visit(astWhileStmtNode node) throws error {
    curS = new Scope(curS, null, ScopeType.LOOP);
    node.getCond().accept(this);
    if(!(node.getCond().getType() instanceof typeinfo) || !node.getCond().getType().equals(boolType)) {
      throw new error("invalid type " + node.getCond().getType().getName());
    }
    if(node.getStmt() instanceof astBlockStmtNode)
      for(var stmt : ((astBlockStmtNode) node.getStmt()).getStmts()) {
        stmt.accept(this);
      }
    else 
      node.getStmt().accept(this);
    exit();
    return node.toString();
  }

  @Override
  public String visit(astContinueStmtNode node) throws error {
    if(!curS.findLOOP()) {
      throw new error("continue not in loop");
    } 
    return node.toString();
  }

  @Override
  public String visit(astBreakStmtNode node) throws error {
    if(!curS.findLOOP()) {
      throw new error("continue not in loop");
    } 
    return node.toString();
  }

  @Override
  public String visit(astReturnStmtNode node) throws error {
    var funcinfo = curS.findFunc();
    if(funcinfo == null){
      throw new error("return not in function");
    }
    var type = (((FuncInfo)funcinfo).getRetType());
    if(node.getExpr() == null) {
      if(type != null) {
        throw new error("return type mismatch");
      }
    } else {
      node.getExpr().accept(this);
      if(!node.getExpr().getType().equals(type)) {
        throw new error("return type mismatch");
      }
    }
    curS.isexited = true;
    return node.toString();
  }

  @Override
  public String visit(astExprStmtNode node) throws error {
    return visit(node.getExpr());
  }

  @Override
  public String visit(astVarDefStmtNode node) throws error {
    for(var def : node.getArray()) {
      def.accept(this);
    }
    return node.toString();
  }

  @Override
  public String visit(astEmptyStmtNode node) throws error {
    return node.toString();
  }

  @Override
  public String visit(astConstrNode node) throws error {
    curS = new Scope(curS, null, ScopeType.CLASS);
    for(var stmt : node.getBlock().getStmts()) {
      stmt.accept(this);
    }
    exit();
    return node.toString(); 
  }

  @Override
  public String visit(astFStrExpr node) throws error {
    for(var expr : node.getVecExpr())
      expr.accept(this);
    node.setType(stringType);
    node.setLValue(false);
    return node.toString();
  }
}
