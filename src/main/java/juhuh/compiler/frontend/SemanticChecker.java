package juhuh.compiler.frontend;

import juhuh.compiler.ast.node.astNode;
import juhuh.compiler.ast.node.astRoot;
import juhuh.compiler.ast.node.def.*;
import juhuh.compiler.ast.node.expr.*;
import juhuh.compiler.ast.node.stmt.*;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.ClassInfo;
import juhuh.compiler.util.info.FuncInfo;
import juhuh.compiler.util.info.Info;
import juhuh.compiler.util.info.typeinfo;
import juhuh.compiler.util.globalScope;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.Scope.ScopeType;

public class SemanticChecker implements astVisitor<String> {
  private Scope curS;
  private globalScope gScope;
  // builtin types
  public static typeinfo voidType = new typeinfo("void", 0);
  static typeinfo intType = new typeinfo("int", 0);
  static typeinfo boolType = new typeinfo("bool", 0);
  static typeinfo stringType = new typeinfo("string", 0);
  static typeinfo nullType = new typeinfo("null", 1, true);
  static typeinfo thisType = new typeinfo("this", 0);
  static typeinfo ptrType = new typeinfo("ptr", 0, true);
  public static typeinfo[] builtinTypes = { voidType, intType, boolType, nullType, thisType, ptrType };
  // builtin funcs
  public static FuncInfo printFunc = new FuncInfo("print", voidType, stringType);
  public static FuncInfo printlnFunc = new FuncInfo("println", voidType, stringType);
  public static FuncInfo printIntFunc = new FuncInfo("printInt", voidType, intType);
  public static FuncInfo printlnIntFunc = new FuncInfo("printlnInt", voidType, intType);
  public static FuncInfo getStringFunc = new FuncInfo("getString", stringType);
  public static FuncInfo getIntFunc = new FuncInfo("getInt", intType);
  public static FuncInfo toStringFunc = new FuncInfo("toString", stringType, intType);
  
  public static FuncInfo mallocFunc = new FuncInfo("_malloc", ptrType, intType);
  public static FuncInfo arrMallocFunc = new FuncInfo("_arr_init", ptrType, intType);
  // string add
  public static FuncInfo stringAddFunc = new FuncInfo("_add", stringType, stringType, stringType);
  public static FuncInfo strcmpFunc = new FuncInfo("_strcmp_", intType, stringType, stringType); 

  public static FuncInfo[] builtinFuncs = { printFunc, printlnFunc, printIntFunc, printlnIntFunc, getStringFunc, getIntFunc,
      toStringFunc,mallocFunc,arrMallocFunc,stringAddFunc, strcmpFunc};
  public static FuncInfo arraySizeFunc = new FuncInfo("_arr_size", intType, stringType);
  public static FuncInfo stringLengthFunc = new FuncInfo("length", intType);
  public static FuncInfo stringSubstringFunc = new FuncInfo("substring", stringType, intType, intType);
  public static FuncInfo stringParseintFunc = new FuncInfo("parseInt", intType);
  public static FuncInfo stringOrdFunc = new FuncInfo("ord", intType, intType);
  public static vector<FuncInfo> builtinInfo = new vector<FuncInfo>(stringLengthFunc, stringSubstringFunc, stringParseintFunc, stringOrdFunc);
  public static ClassInfo stringClass = new ClassInfo("string",
      builtinInfo);

  public SemanticChecker(globalScope gScope) {
    curS = this.gScope = gScope;
    for (var func : builtinFuncs) {
      gScope.defineVariable(func.getName(), func);
    }

    stringClass.func = builtinInfo;
    gScope.addType("string", stringClass);
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
    if (!(Type instanceof typeinfo))
      return false;
    var type = (typeinfo) Type;
    return (type.isBuiltin() && (type.getName().equals("int")
        || type.getName().equals("bool") || type.getName().equals("string")))
        || gScope.getTypeFromName(type.getName()) != null;
  }

  @Override
  public String visit(astNode node) throws error {
    // System.err.println("Unimplemented method 'visit'" + node.toString());
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public String visit(astRoot node) throws error {
    // curS = gscope
    for (var def : node.getDefs()) {
      //// System.err.println(def.toString());
      def.accept(this);
    }
    exit();
    return null;
  }

  @Override
  public String visit(astFuncDefNode node) throws error {
    enter(node.getFuncScope());
    for (var stmt : node.getBlock().getStmts()) {
      stmt.accept(this);
    }
    if (!node.getInfo().getRetType().equals(voidType) && !node.getName().equals("main") && !curS.isexited) {
      throw new error("Missing Return Statement");
    }
    exit();
    return null;
  }

  @Override
  public String visit(astClassDefNode node) throws error {
    enter(node.getClassScope());
    if (node.getConstructor() != null)
      node.getConstructor().accept(this);
    for (var method : node.getMethods()) {
      method.accept(this);
    }
    exit();
    return null;
  }

  @Override
  public String visit(astVarDefNode node) throws error {
    // System.err.println("Ln118"+node.getType());
    if (!checkValidType(node.getType().getInfo())) {
      throw new error("Invalid Type");
    }

    // //System.err.println(node.getName() + "defined as" +
    // node.getType().getInfo().toString());
    if (node.getUnit() != null) {
      // //System.err.println(node.getUnit().getType().toString() + ((typeinfo)
      // node.getUnit().getType()).getDim());
      node.getUnit().accept(this);
      // //System.err.println(node.getUnit().getType().toString() + ((typeinfo)
      // node.getUnit().getType()).getDim());
      var info = node.getUnit().getType();
      if(!(info instanceof typeinfo))
        throw new error("Invalid Type");
      
      if(!info.equals(node.getType().getInfo()) && !info.getName().equals(node.getType().getInfo().getName())) {
        throw new error("Type Mismatch");
      }
      if (!info.equals(node.getType().getInfo())) {
        throw new error("Type Mismatch");
      }
    }
    curS.defineVariable(node.getName(), node.getType().getInfo());

    return null;
  }

  @Override
  public String visit(astNewArrayExprNode node) throws error {
    if (!checkValidType(node.getType()))
      throw new error("Invalid Type");
    if (((typeinfo) node.getType()).getDim() == 0 && ((typeinfo) node.getType()).isBuiltin()) {
      throw new error("Invalid Type");
    }
    if (node.getLengths() != null)
      for (var length : node.getLengths()) {
        length.accept(this);
        if (!length.getType().equals(new typeinfo("int", 0))) {
          throw new error("Type Mismatch");
        }
      }
    if (node.getInit() != null) {
      // //System.err.println(node.getInit().getType().toString() + ((typeinfo)
      // node.getInit().getType()).getDim());
      node.getInit().accept(this);
      // //System.err.println(node.getInit().getType().toString() + ((typeinfo)
      // node.getInit().getType()).getDim());
      var info = node.getInit().getType();
      if (info instanceof typeinfo && !info.equals(node.getType())) {
        throw new error("Type Mismatch");
      }
    }
    return null;
  }

  @Override
  public String visit(astCallExprNode node) throws error {
    // funcscope name argstype
    // func -> funcinfo
    node.getFunc().accept(this);
    // System.err.println("ln157" + node.getFunc());
    var funcinfo = node.getFunc().getType();
    if (funcinfo == null || !(funcinfo instanceof FuncInfo)) {
      throw new error("Undefined Identifier");
    }
    var func = (FuncInfo) funcinfo;
    // args -> type match funcinfo
    if (func.getArgsType().size() != node.getArgs().size()) {
      throw new error("Undefined Identifier");
    }
    for (int i = 0; i < node.getArgs().size(); i++) {
      node.getArgs().get(i).accept(this);
      if (!node.getArgs().get(i).getType().equals(func.getArgsType().get(i))) {
        throw new error("Type Mismatch");
      }
    }
    // expr type -> func
    node.setType(func.getRetType());
    node.setLValue(false);
    // System.err.println("ln157 exited" + node.getFunc());
    return null;
  }

  @Override
  public String visit(astArrayExprNode node) throws error {
    node.getArray().accept(this);
    Info arrayInfo = node.getArray().getType();
    if (!(arrayInfo instanceof typeinfo) || ((typeinfo) arrayInfo).getDim() == 0) {
      throw new error("Dimension Out Of Bound");
    }
    node.getSub().accept(this);
    // sub not int
    if (!node.getSub().getType().equals(new typeinfo("int", 0))) {
      throw new error("Invalid Type");
    }
    node.setType(new typeinfo(arrayInfo.getName(), ((typeinfo) arrayInfo).getDim() - 1));
    node.setLValue(true);
    return null;
  }

  @Override
  public String visit(astArrayConstExpr node) throws error {
    for (var expr : node.getVec()) {
      expr.accept(this);
    }
    if (node.getVec().size() == 0) {
      node.setType(new typeinfo("null", 1, true));
    } else {
      Info type = node.getVec().get(0).getType();
      if (!(type instanceof typeinfo)) {
        throw new error("Undefined Identifier");
      }
      for (var expr : node.getVec()) {
        if (!type.equals(expr.getType())) {
          throw new error("Type Mismatch");
        }
        if (!((typeinfo) type).isDim_())
          if (!((typeinfo) expr.getType()).isDim_()
              || ((typeinfo) expr.getType()).getDim() > ((typeinfo) type).getDim()) {
            type = expr.getType();
          }
      }
      node.setType(new typeinfo(type.getName(), ((typeinfo) type).getDim() + 1, ((typeinfo) type).isDim_()));
    }
    return null;
  }

  @Override
  public String visit(astMemberExprNode node) throws error {
    node.getExpr().accept(this);
    // node.setMember(node.getExpr().isMember());
    Info exprInfo = node.getExpr().getType();
    if (!(exprInfo instanceof typeinfo)) {
      throw new error("Undefined Identifier");
    }
    var expr = (typeinfo) exprInfo;
    // System.err.println("semChk ln225" + expr.toString() + expr.getDim());
    if (expr.getDim() > 0) {
      if (node.getMember().equals("size")) {
        node.setType(new FuncInfo("_arr_size", intType));
        node.setLValue(false);
        //// System.err.println("semChk ln230" + expr.getName() + "Size");
        //// System.err.println("ln157 exited");
        return null;
      } else {
        throw new error("Undefined Identifier");
      }
    } else {
      var Class = gScope.getTypeFromName(expr.getName());
      if (Class == null || !(Class instanceof ClassInfo)) {
        throw new error("Undefined Identifier");
      }
      var classinfo = (ClassInfo) Class;
      var member = classinfo.getVars().get(node.getMember());
      if (member != null) {
        node.setType(member);
        // node.setMember(true);
        node.setLValue(true);
        return null;
      }
      // change method name
      String newName = classinfo.getName() + "." + node.getMember();
      var method = classinfo.getFuncs().get(node.getMember());
      if (method == null) {
        throw new error("Undefined Identifier");
      } else {
        method.setName(newName);
        node.setType(method); // but name is changing class_name old / TODO memberexpr has redeclared
        return null;
      }
    }
  }

  @Override
  public String visit(astUnaryExprNode node) throws error {
    // Increment | Decrement | Not | LogicNot | Minus | Plus
    node.getExpr().accept(this);
    var exprType = node.getExpr().getType();
    if (!(exprType instanceof typeinfo)) {
      throw new error("Undefined Identifier");
    }
    var Type = (typeinfo) exprType;
    if (node.getOp().equals("LogicNot")) {
      if (!Type.equals(boolType)) {
        throw new error("Type Mismatch");
      }
      node.setType(boolType);
      node.setLValue(false);
      return null;
    }
    if (!Type.equals(intType))
      throw new error("Type Mismatch");
    if (node.getOp().equals("Increment") || node.getOp().equals("Decrement")) {
      if (!node.getExpr().isLValue())
        throw new error("Type Mismatch");
      node.setType(Type);
      node.setLValue(false);
    } else {
      node.setType(Type);
      node.setLValue(false);
    }
    return null;
  }

  @Override
  public String visit(astPreSelfExprNode node) throws error {
    // notLvalue, int
    node.getExpr().accept(this);
    var exprType = node.getExpr().getType();
    if (!(exprType instanceof typeinfo)) {
      throw new error("Undefined Identifier");
    }
    var Type = (typeinfo) exprType;
    if (!(Type instanceof typeinfo))
      throw new error("Undefine Identifier");
    if (!Type.equals(intType))
      throw new error("Type Mismatch");
    if (!node.getExpr().isLValue())
      throw new error("Type Mismatch");
    node.setType(Type);
    node.setLValue(true);
    return null;
  }

  @Override
  public String visit(astBinaryExprNode node) throws error {
    node.getLhs().accept(this);
    node.getRhs().accept(this);
    if (!(node.getLhs().getType() instanceof typeinfo)) {
      throw new error("Undefined Identifier");
    }
    var type = (typeinfo) node.getLhs().getType();
    if (!type.equals(node.getRhs().getType())) {
      throw new error("Type Mismatch");
    }
    if (type.getDim() > 0 || type.equals(nullType)) {
      if (node.getOp().equals("Equal") || node.getOp().equals("UnEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return null;
      } else {
        throw new error("Invalid Type");
      }
    }
    if (type.equals(intType)) {
      if (node.getOp().equals("Plus") || node.getOp().equals("Minus") || node.getOp().equals("Mul")
          || node.getOp().equals("Div") || node.getOp().equals("Mod")) {
        node.setType(intType);
        node.setLValue(false);
        return null;
      } else if (node.getOp().equals("LeftShift") || node.getOp().equals("RightShift") ||
          node.getOp().equals("And") || node.getOp().equals("Or") || node.getOp().equals("Xor")) {
        node.setType(intType);
        node.setLValue(false);
        return null;
      } else if (node.getOp().equals("Greater") || node.getOp().equals("GreaterEqual") || node.getOp().equals("Less")
          || node.getOp().equals("LessEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return null;
      } else if (node.getOp().equals("Equal") || node.getOp().equals("UnEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return null;
      } else if (node.getOp().equals("LogicAnd") || node.getOp().equals("LogicOr")) {
        node.setType(boolType);
        node.setLValue(false);
        return null;
      } else {
        throw new error("Invalid Type");
      }
    } else if (type.equals(boolType)) {
      if (node.getOp().equals("LogicAnd") || node.getOp().equals("LogicOr")) {
        node.setType(boolType);
        node.setLValue(false);
        return null;
      } else if (node.getOp().equals("Equal") || node.getOp().equals("UnEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return null;
      } else {
        throw new error("Invalid Type");
      }
    } else if (type.equals(stringType)) {
      if (node.getOp().equals("Plus")) {
        node.setType(stringType);
        node.setLValue(false);
        return null;
      } else if (node.getOp().equals("Equal") || node.getOp().equals("UnEqual")
          || node.getOp().equals("Greater") || node.getOp().equals("GreaterEqual")
          || node.getOp().equals("Less") || node.getOp().equals("LessEqual")) {
        node.setType(boolType);
        node.setLValue(false);
        return null;
      } else {
        throw new error("Invalid Type");
      }
    } else {
      throw new error("Invalid Type");
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
    if (!(node.getLhs().getType() instanceof typeinfo) || !(node.getRhs().getType() instanceof typeinfo)
        || !(node.getCond().getType() instanceof typeinfo)) {
      throw new error("Undefine Identifier");
    }
    if (node.getCond().getType().equals(boolType)) {
      if (node.getLhs().getType().equals(node.getRhs().getType())) {
        node.setType(node.getLhs().getType());
        node.setLValue(false);
        return null;
      } else {
        throw new error("Type Mismatch");
      }
    } else {
      throw new error("Invalid Type");
    }
  }

  @Override
  public String visit(astAssignExprNode node) throws error {
    node.getLhs().accept(this);
    node.getRhs().accept(this);
    // System.err.println(node.getLhs().getType());
    if (!(node.getLhs().getType() instanceof typeinfo) || !(node.getRhs().getType() instanceof typeinfo)) {
      throw new error("Undefine Identifier");
    }
    if (node.getLhs().isLValue()) {
      if (node.getLhs().getType().equals(node.getRhs().getType())) {
        node.setType(new typeinfo((typeinfo) node.getLhs().getType()));
        node.setLValue(true);
        return null;
      } else {
        throw new error("Type Mismatch");
      }
    } else {
      throw new error("Type Mismatch");
    }
  }

  @Override
  public String visit(astAtomExprNode node) throws error {
    // Atom getType.
    // System.err.println(node.getType());
    if (node.getType().getName().equals(("1custom"))) {
      var info = curS.containsVariable(node.getValue(), true);
      if (info == null) {
        // System.err.println(node.getValue());
        throw new error("Undefined Identifier");
      }
      if (info instanceof FuncInfo) {
        node.setType(new FuncInfo((FuncInfo) info));
        node.setLValue(false);
        assert (node.getType() instanceof FuncInfo);
        // System.err.println(" : " + node.getType().toString());
      } else if (info instanceof typeinfo) {
        node.setType((typeinfo) info);
        // find member? 
        // var scp = curS;
        // while(scp.containsVariable(node.getValue(), false) == null)
        //   scp = scp.parentScope();
        // if(scp.type == ScopeType.CLASS && ((ClassInfo)scp.info).getVarsId().containsKey(node.getValue()))
        //   node.setMember(true);
        // else
        //   node.setMember(false);
        node.setLValue(true);
      } else {
        throw new error("Undefined Identifier");
      }
    } else if (node.getType().equals(new typeinfo("this", 0))) {
      var info = curS.findCLASS();
      if (info == null) {
        throw new error("Undefined Identifier");
      }
      node.setType(info);
      // node.setMember(true);
      node.setLValue(false);
    } else {
      node.setLValue(false);
    }
    return null;
  }

  @Override
  public String visit(astBlockStmtNode node) throws error {
    node.setScope(new Scope(curS, null, ScopeType.BLOCK));
    enter(node.getScope());
    for (var stmt : node.getStmts()) {
      stmt.accept(this);
    }
    exit();
    return null;
  }

  @Override
  public String visit(astIfStmtNode node) throws error {
    node.getCond().accept(this);
    if (!(node.getCond().getType() instanceof typeinfo))
      throw new error("Undefined Identifier");
    if(!node.getCond().getType().equals(boolType)) {
      throw new error("Invalid Type");
    }
    node.setThenscope(new Scope(curS, null, ScopeType.BLOCK));
    curS = node.getThenscope();
    node.getThenStmt().accept(this);
    exit();
    if (node.getElseStmt() == null)
      return null;
    node.setElsescope(new Scope(curS, null, ScopeType.BLOCK));
    curS = node.getElsescope();
    node.getElseStmt().accept(this);
    exit();
    return null;
  }

  @Override
  public String visit(astForStmtNode node) throws error {
    enter(new Scope(curS, null, ScopeType.LOOP));
    node.setScope(curS);
    node.getInit().accept(this);
    if (node.getCond() != null) {
      node.getCond().accept(this);
      if (!(node.getCond().getType() instanceof typeinfo))  {
        throw new error("Undefined Identifier");
      }
      if(!node.getCond().getType().equals(boolType))
        throw new error("Invalid Type");
    }

    if (node.getUpdate() != null)
      node.getUpdate().accept(this);
    
    if (node.getStmt() instanceof astBlockStmtNode)
      for (var stmt : ((astBlockStmtNode) node.getStmt()).getStmts()) {
        stmt.accept(this);
      }
    else
      node.getStmt().accept(this);
    
    exit();
    return null;
  }

  @Override
  public String visit(astWhileStmtNode node) throws error {
    node.setScope(new Scope(curS, null, ScopeType.LOOP));
    enter(node.getScope());
    node.getCond().accept(this);
    if (!(node.getCond().getType() instanceof typeinfo))
      throw new error("Undefined Identifier");
    if (!node.getCond().getType().equals(boolType)) {
      throw new error("Invalid Type");
    }
    if (node.getStmt() instanceof astBlockStmtNode)
      for (var stmt : ((astBlockStmtNode) node.getStmt()).getStmts()) {
        stmt.accept(this);
      }
    else
      node.getStmt().accept(this);
    exit();
    return null;
  }

  @Override
  public String visit(astContinueStmtNode node) throws error {
    if (!curS.findLOOP()) {
      throw new error("Invalid Control Flow");
    }
    return null;
  }

  @Override
  public String visit(astBreakStmtNode node) throws error {
    if (!curS.findLOOP()) {
      throw new error("Invalid Control Flow");
    }
    return null;
  }

  @Override
  public String visit(astReturnStmtNode node) throws error {
    var funcinfo = curS.findFunc();
    if (funcinfo == null) {
      throw new error("Invalid Control Flow");
    }
    var type = (((FuncInfo) funcinfo).getRetType());
    if (node.getExpr() == null) {
      if (type != null && type != voidType) {
        throw new error("Type Mismatch");
      }
    } else {
      node.getExpr().accept(this);
      if (!node.getExpr().getType().equals(type)) {
        throw new error("Type Mismatch");
      }
    }

    curS.setExit();
    return null;
  }

  @Override
  public String visit(astExprStmtNode node) throws error {
    return node.getExpr().accept(this);
  }

  @Override
  public String visit(astVarDefStmtNode node) throws error {
    if (node.getArray() == null)
      return null;
    for (var def : node.getArray()) {
      //// System.err.println(def instanceof astVarDefNode);
      ((astVarDefNode) def).accept(this);
    }
    return null;
  }

  @Override
  public String visit(astEmptyStmtNode node) throws error {
    return null;
  }

  @Override
  public String visit(astConstrNode node) throws error {
    node.setScope(new Scope(curS, new FuncInfo("construct", voidType), ScopeType.FUNC));
    enter(node.getScope());
    for (var stmt : node.getBlock().getStmts()) {
      stmt.accept(this);
    }
    exit();
    return null;
  }

  @Override
  public String visit(astFStrExpr node) throws error {
    for (var expr : node.getVecExpr()) {
      expr.accept(this);
      var type = expr.getType();
      if (!(type instanceof typeinfo)
          || (!type.equals(stringType) && !type.equals(intType) && !type.equals(boolType)) ||
          type.getName().equals("null")) {
        throw new error("Invalid Type");
      }
    }
    node.setType(stringType);
    node.setLValue(false);
    return null;
  }
}
