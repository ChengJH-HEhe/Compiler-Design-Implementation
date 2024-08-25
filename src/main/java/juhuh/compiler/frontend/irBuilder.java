package juhuh.compiler.frontend;

import juhuh.compiler.ast.node.astNode;
import juhuh.compiler.ast.node.astRoot;
import juhuh.compiler.ast.node.def.*;
import juhuh.compiler.ast.node.expr.*;
import juhuh.compiler.ast.node.stmt.*;
import juhuh.compiler.ir.irNode;
import juhuh.compiler.ir.irRoot;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.ir.stmt.*;

import juhuh.compiler.ir.entity.*;
import juhuh.compiler.ir.ins.*;

import juhuh.compiler.util.Scope;
import juhuh.compiler.util.globalScope;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.ClassInfo;
import juhuh.compiler.util.info.FuncInfo;
import juhuh.compiler.util.info.typeinfo;

public class irBuilder implements astVisitor<irNode> {
  private Scope curS;
  private globalScope gScope;
  private irBlock curdef, curBlock;
  private irFuncDef curFunc;
  private String label;

  // add label String

  private void enter(Scope scope, int depth, int selfN) {
    curS = scope;
    curS.depth = depth;
    curS.selfN = selfN;
  }

  private void exit() {
    curS = curS.parentScope();
  }

  public irBuilder(globalScope gSc) {
    curS = gSc;
    gScope = gSc;
    gSc.depth = 0;
    gSc.selfN = 0;
  }

  public static String tp(typeinfo tp) {
    if (tp.equals(SemanticChecker.intType)) {
      return "i32";
    } else if (tp.equals(SemanticChecker.boolType)) {
      return "i1";
    } else
      return "ptr";
  }

  public static vector<String> tp(vector<astExprNode> tp) {
    vector<String> res = new vector<String>();
    for (var tps : tp) {
      res.add(tp((typeinfo) tps.getType()));
    }
    return res;
  }

  @Override
  public irNode visit(astNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'ir visit astNode'");
  }

  @Override
  public irNode visit(astRoot node) throws error {
    irRoot rt = irRoot.builder()
        .globalDef(new vector<irGlobalDef>())
        .globalFDecl(new vector<irFuncDecl>())
        .FDef(new vector<irFuncDef>())
        .build();
    irFuncDef globalInit = irFuncDef.builder()
        .fName("__init")
        .anonyNum(0)
        .retType("void")
        .paratypelist(new vector<String>())
        .paravaluelist(new vector<String>())
        .entry(irBlock.builder()
            .label("entry")
            .stmts(new vector<irStmt>())
            .terminalstmt(irRet.builder().tp("void").val("").build())
            .build())
        .build();
    irFuncDef main = null;
    // main add to Fdef
    astNode mainNode = null;

    var stringInfo = ((ClassInfo) gScope.getTypeFromName("string")).func;
    // string this & declare
    rt.add(irStructDef.builder()
        .name("%class.string")
        .member(new vector<String>())
        .build());
    for (var decl : stringInfo) {
      decl.setName("string_" + decl.getName());
      var func = irFuncDecl.builder()
          .info(decl)
          .paratypelist(new vector<String>("ptr"))
          .paravaluelist(new vector<String>("%this"))
          .build();
      for (var para : decl.getArgsType()) {
        func.getParatypelist().add(tp(para));
        func.getParavaluelist().add(para.getName());
      }
      rt.add(func);
    }
    // other builtin declare (TODO add ? string)

    for (var def : node.getDefs()) {
      if (def instanceof astFuncDefNode) {
        if (((astFuncDefNode) def).getInfo().getName().equals("main")) {
          mainNode = def;
        } else {
          rt.add((irFuncDef) def.accept(this));
        }
      } else if (def instanceof astClassDefNode) {
        irClassDef cls = ((irClassDef) def.accept(this));
        rt.add(cls.getStruct());
        for (var f : cls.getFuncDefs()) {
          rt.add(f);
        }
      } else if (def instanceof astVarDefNode) {
        // global var
        // String def
        var Def = (astVarDefNode) def;
        curBlock = globalInit.getEntry();
        // TODO array
        irGlobalDef globalvar = null;
        if (!(Def).getType().getInfo().equals(SemanticChecker.intType)
            && !(Def).getType().getInfo().equals(SemanticChecker.boolType)) {
          // def %class.typename
          globalvar = (irGlobalDef.builder()
              .name("@" + (Def).getName())
              .type("%class." + (Def).getType().getInfo().getName())
              .init("null")
              .build());
        } else {
          globalvar = irGlobalDef.builder()
              .name((Def).getName())
              .type(tp((Def).getType().getInfo()))
              .init("0")
              .build();
        }
        if ((Def).getUnit() != null) {
          var init = visit((Def).getUnit());
          curBlock.add(irStore.builder()
              .tp(globalvar.getType())
              .res(init.toString())
              .ptr("@" + globalvar.getName())
              .build());
          rt.add(globalvar);
        }
      }
    }
    main = (irFuncDef) mainNode.accept(this);
    // main init global
    main.getEntry().add(irCall.builder()
        .res("")
        .func(new FuncInfo(globalInit.getFName(), SemanticChecker.voidType))
        .type(new vector<String>())
        .val(new vector<String>())
        .build());
    rt.add(globalInit);
    rt.add(main);
    return rt;
  }

  @Override
  public irNode visit(astFuncDefNode node) throws error {
    // get irFuncDef cond -> findfor with only depth as the effective info
    irFuncDef func = irFuncDef.builder()
        .anonyNum(0)
        .fName("@" + node.getInfo().getName())
        .retType(tp(node.getInfo().getRetType()))
        .paratypelist(new vector<String>())
        .paravaluelist(new vector<String>())
        .entry(irBlock.builder()
            .label("entry")
            .stmts(new vector<irStmt>())
            .build())
        .ret(irBlock.builder()
            .label("return")
            .stmts(new vector<irStmt>())
            .terminalstmt(irRet.builder().tp(tp(node.getInfo().getRetType())).val("%ret.val").build())
            .build())
        .body(new vector<irBlock>())
        .build();
    // reminder curDef to be the FuncDef entry block, entry br to the first
    // block
    // curBlock to be the curBlock
    // different label jump
    curdef = func.getEntry();
    curBlock = curdef;
    enter(node.getFuncScope(), 1, curS.sonN++);

    if (!node.getRet().equals("void")) {
      curdef.add(irAlloca.builder()
          .res("%ret.val")
          .tp(tp(node.getInfo().getRetType()))
          .build());
    }
    curS = node.getFuncScope();
    // add this for class function
    if (curS.parentScope().type != Scope.ScopeType.GLOBAL) {
      func.getParatypelist().add("ptr");
      func.getParavaluelist().add("%this");
      curdef.add(irAlloca.builder()
          .res("%this.addr." + curS.selfN)
          .tp("ptr")
          .build());
      curdef.add(irStore.builder()
          .tp("ptr")
          .res("%this")
          .ptr("%this.addr." + curS.selfN)
          .build());
      curdef.add(irLoad.builder()
          .tp("ptr")
          .res("%this.copy")
          .ptr("%this.addr." + curS.selfN)
          .build());
      // unused has set in symbol collector
      // func.setFName("@" + curS.parentScope().info.getName() + "_" +
      // node.getInfo().getName());
    }
    for (var para : node.getArgs()) {
      func.getParatypelist().add(tp(para.getType().getInfo()));
      func.getParavaluelist().add(para.getName());
      curdef.add(irAlloca.builder()
          .res("%" + para.getName() + ".1." + curS.selfN)
          .tp(tp(para.getType().getInfo()))
          .build());
      curdef.add(irStore.builder()
          .tp(tp(para.getType().getInfo()))
          .res("%" + para.getName())
          .ptr("%" + para.getName() + ".1." + curS.selfN)
          .build());
    }
    for (var stmt : node.getBlock().getStmts()) {
      // body stmt when to use curBlock? before any label
      visit(stmt);
      if (label != "" && func.getEntry().getTerminalstmt() == null) {
        func.getEntry().setTerminalstmt(irJump.builder().dest(label).build());
        label = "";
      }
    }

    exit();
    return func;
  }

  @Override
  public irNode visit(astClassDefNode node) throws error {
    enter(node.getClassScope(), 1, curS.sonN++);
    irClassDef cls = irClassDef.builder()
        .struct(irStructDef.builder()
            .name("%" + "class." + node.getInfo().getName())
            .member(new vector<String>())
            .build())
        .funcDefs(new vector<irFuncDef>())
        .build();
    // fields
    cls.getFuncDefs().add((irFuncDef) visit(node.getConstructor()));
    for (var field : node.getFields()) {
      cls.getStruct().getMember().add(tp(field.getType().getInfo()));
    }
    // methods
    for (var method : node.getMethods()) {
      cls.getFuncDefs().add((irFuncDef) method.accept(this));
    }
    return cls;
  }

  @Override
  public irNode visit(astVarDefNode node) throws error {
    // curdef add alloca,
    // no this a.val
    String tmpname = curS.Varrename(node.getName());

    // class
    if (!node.getType().getInfo().equals(SemanticChecker.intType)
        && !node.getType().getInfo().equals(SemanticChecker.boolType)) {
      // def %class.typename
      curdef.add(irAlloca.builder()
          .res(tmpname)
          .tp("%class." + node.getType().getInfo().getName())
          .build());
    } else {
      // int bool str
      curdef.add(irAlloca.builder()
          .res(tmpname)
          .tp(tp(node.getType().getInfo()))
          .build());
    }
    if (node.getUnit() != null) {
      var init = visit(node.getUnit());
      curBlock.add(irStore.builder()
          .tp(tp(node.getType().getInfo()))
          .res((init).toString())
          .ptr(tmpname)
          .build());
    }
    return null;
  }

  @Override
  public irNode visit(astConstrNode node) throws error {
    // a::a(ptr this) another funcdef
    var Func = irFuncDef.builder()
        .fName("@" + node.getClassName() + "_" + node.getClassName())
        .retType("void")
        .paratypelist(new vector<String>("ptr"))
        .paravaluelist(new vector<String>("%this"))
        .entry(irBlock.builder()
            .label("entry")
            .stmts(new vector<irStmt>())
            .build())
        .ret(irBlock.builder()
            .label("return")
            .terminalstmt(irRet.builder().tp("void").val("").build())
            .build())
        .body(new vector<irBlock>())
        .build();
    curdef = Func.getEntry();
    curdef.add(irAlloca.builder()
        .res("%this.addr")
        .tp("ptr")
        .build());
    curdef.add(irStore.builder()
        .tp("ptr")
        .res("%this")
        .ptr("%this.addr")
        .build());
    curdef.add(irLoad.builder()
        .tp("ptr")
        .res("%this.copy")
        .ptr("%this.addr")
        .build());
    curBlock = curdef;
    visit(node.getBlock());
    return Func;
  }

  @Override
  public irNode visit(astNewArrayExprNode node) throws error {
    // TODO array
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astCallExprNode node) throws error {
    // call func manage expr add to curBlock

    var callnode = irCall.builder()
        .res(((FuncInfo) node.getFunc().getType()).getRetType().equals(SemanticChecker.voidType) ? ""
            : curFunc.tmprename())
        .func((FuncInfo) node.getFunc().getType()) // funcname updated in sema , @ +
        .type(tp(node.getArgs()))
        .val(new vector<String>())
        .build();
    entity caller = null;
    if (node.getFunc() instanceof astMemberExprNode) {
      // add caller
      caller = (entity) visit(((astMemberExprNode) node.getFunc()).getExpr());
    } else {
      // class (this.func)
      var tmpS = curS;
      while (tmpS.type != null && tmpS.type != Scope.ScopeType.CLASS) {
        tmpS = tmpS.parentScope();
      }
      if (tmpS != null && tmpS.containsVariable(((astAtomExprNode) node.getFunc()).getType().getName(), false) != null)
        caller = register.builder()
            .name("%this.copy")
            .ptr("%this.copy")
            .build();
    }
    if (caller != null) {
      callnode.getVal().add(((register) caller).getPtr());
      callnode.getType().add("ptr");
    }
    for (var arg : node.getArgs()) {
      entity res = (entity) visit(arg);
      callnode.getType().add(tp((typeinfo) arg.getType()));
      if (callnode.getType().getlst().equals("ptr"))
        callnode.getVal().add(((register) res).getPtr());
      else
        callnode.getVal().add(res.toString());
    }
    curBlock.add(callnode);
    return null;
  }

  @Override
  public irNode visit(astArrayExprNode node) throws error {
    // return ptr using elementptr
    // array + sub
    entity res = (entity) visit(node.getArray());
    var resul = curFunc.tmprename();
    var reg = register.builder()
        .ptr(resul)
        .name(curFunc.tmprename())
        .build();
    // arrayexpr
    var gep = irGetElement.builder()
        .res(resul)
        .tp(tp((typeinfo) node.getType()))
        .ptrval(((register) res).getPtr())
        .build();
    // all need to add 0
    {
      gep.setTp1("i32");
      gep.setId1("0");
      gep.setTp2("i32");
      gep.setId2((visit(node.getSub())).toString());
    }
    curBlock.add(irLoad.builder()
        .res(reg.getName())
        .tp(tp((typeinfo) node.getType()))
        .ptr(reg.getPtr())
        .build());
    curBlock.add(gep);
    return reg;
  }

  @Override
  public irNode visit(astArrayConstExpr node) throws error {
    // TODO what?
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astMemberExprNode node) throws error {
    // Lvalue -> ptr
    // a + . + b ?
    entity res = (entity) visit(node.getExpr());
    String ptr = ((register) res).getPtr();
    String resul = ptr + "." + node.getMember();
    var reg = register.builder()
        .name(curFunc.tmprename())
        .ptr(resul)
        .build();
    // reg ptr? -> name null else name = Store
    ClassInfo info = (ClassInfo) gScope.getTypeFromName(((typeinfo) node.getExpr().getType()).getName());
    var gep = irGetElement.builder()
        .res(resul)
        .tp(tp((typeinfo) node.getType()))
        .ptrval(((register) res).getPtr())
        .tp1("i32").id1("0").tp2("i32")
        .id2(info.varsId.get(node.getMember()).toString())
        .build();
    curBlock.add(gep);
    curBlock.add(irLoad.builder()
        .res(reg.getName())
        .tp(tp((typeinfo) node.getType()))
        .ptr(resul)
        .build());
    return reg;
  }

  @Override
  public irNode visit(astFStrExpr node) throws error {
    // TODO get the final String in the global scope
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astUnaryExprNode node) throws error {
    entity res = (entity) visit(node.getExpr());
    if (node.getOp().equals("Plus")) {
      return res;
    } else if (node.getOp().equals("Minus")) {
      String resul = curFunc.tmprename();
      curBlock.add(irBinary.builder()
          .op("sub")
          .res(resul)
          .tp("i32")
          .op1("0")
          .op2((res.toString()))
          .build());
      return register.builder()
          .name(resul)
          .build();
    } else if (node.getOp().equals("Not")) {
      String resul = curFunc.tmprename();
      curBlock.add(irBinary.builder()
          .op("xor")
          .res(resul)
          .tp("i32")
          .op1("-1")
          .op2(((register) res).getName())
          .build());
      return register.builder()
          .name(resul)
          .build();
    } else if (node.getOp().equals("LogicNot")) {
      String resul = curFunc.tmprename();
      curBlock.add(irBinary.builder()
          .op("xor")
          .res(resul)
          .op1("1")
          .op2(((register) res).getName())
          .build());
      return register.builder()
          .name(resul)
          .build();
    } else if (node.getOp().equals("Increment") || node.getOp().equals("Decrement")) {
      // res in a name & ptr store in the ptrname;
      String resul = curFunc.tmprename();
      curBlock.add(irBinary.builder()
          .op(node.getOp().equals("Increment") ? "add" : "sub")
          .res(resul)
          .tp("i32")
          .op1(((register) res).getName())
          .op2("1")
          .build());
      curBlock.add(irStore.builder()
          .tp("i32")
          .res(resul)
          .ptr(((register) res).getPtr())
          .build());
      return register.builder()
          .name(resul)
          .ptr(((register) res).getPtr())
          .build();
    } else {
      throw new UnsupportedOperationException("Unimplemented method 'visitUnary'");
    }
  }

  @Override
  public irNode visit(astPreSelfExprNode node) throws error {
    // rvalue
    entity res = (entity) visit(node.getExpr());
    String resul = curFunc.tmprename(),
        resul1 = curFunc.tmprename();
    curBlock.add(irBinary.builder()
        .op("add")
        .res(resul)
        .tp("i32")
        .op1(((register) res).getName())
        .op2("0")
        .build());
    curBlock.add(irBinary.builder()
        .op(node.getOp().equals("Increment") ? "add" : "sub")
        .res(resul1)
        .tp("i32")
        .op1(((register) res).getName())
        .op2("1")
        .build());
    curBlock.add(irStore.builder()
        .tp("i32")
        .res(resul1)
        .ptr(((register) res).getPtr())
        .build());
    return register.builder()
        .name(resul)
        .ptr(((register) res).getPtr())
        .build();
  }

  @Override
  public irNode visit(astBinaryExprNode node) throws error {
    // calc the res to the curBlock & return the res to a register node
    if (node.getOp().equals("LogicAnd") || node.getOp().equals("LogicOr")) {
      // short circuit
      int target = node.getOp().equals("LogicAnd") ? 0 : 1;
      String resul = curFunc.tmprename();
      // and 1 -> log.false
      entity res1 = (entity) visit(node.getLhs());
      String[] label = { "log.false" + resul, "log.end" + resul };
      curBlock.add(irBranch.builder()
          .cond(((register) res1).getName())
          .iftrue(label[target])
          .iffalse(label[target ^ 1])
          .build());
      // iftrue -> res1赋值
      // iffalse 跳过res2赋值
      curBlock.add(irBlock.builder()
          .label(label[0])
          .stmts(new vector<irStmt>())
          .build());
      entity res2 = (entity) visit(node.getRhs());
      curBlock.add(irJump.builder()
          .dest(label[1])
          .build());
      curBlock.add(irBlock.builder()
          .label(label[1])
          .stmts(new vector<irStmt>())
          .build());
      if(node.getOp().equals("LogicOr"))
        curBlock.add(irSelect.builder()
            .res(resul)
            .cond(((register) res1).getName())
            .tp1(tp((typeinfo) node.getLhs().getType()))
            .val1(((register) res1).getName())
            .tp2(tp((typeinfo) node.getRhs().getType()))
            .val2(((register) res2).getName())
            .build());
      else 
        curBlock.add(irSelect.builder()
            .res(resul)
            .cond(((register) res1).getName())
            .tp1(tp((typeinfo) node.getRhs().getType()))
            .val1(((register) res2).getName())
            .tp2(tp((typeinfo) node.getLhs().getType()))
            .val2(((register) res1).getName())
            .build());
      return register.builder()
          .name(resul)
          .build();
    } else {
      String Optp = irIcmp.builder().build().getop(node.getOp());
      if (Optp != null) {
        // res
        entity res1 = (entity) visit(node.getLhs()),
            res2 = (entity) visit(node.getRhs());
        String resul = curFunc.tmprename();
        curBlock.add(irIcmp.builder()
            .op(Optp)
            .res(resul)
            .tp("i1")
            .op1(((register) res1).getName())
            .op2(((register) res2).getName())
            .build());
        return register.builder()
            .name(resul)
            .build();
      } else {
        // expr done
        entity res1 = (entity) visit(node.getLhs()),
            res2 = (entity) visit(node.getRhs());
        String resul = curFunc.tmprename();
        curBlock.add(irBinary.builder()
            .op(irBinary.builder().build().getop(node.getOp()))
            .res(resul)
            .tp("i32")
            .op1(((register) res1).getName())
            .op2(((register) res2).getName())
            .build());
        return register.builder()
            .name(resul)
            .build();
      }
    }
  }

  @Override
  public irNode visit(astConditionalExprNode node) throws error {
    // select cmd rvalue
    var curFa = curBlock;

    entity cond = (entity) visit(node.getCond());
    String resul = curFunc.tmprename();
    
    irBlock thenB = irBlock.builder()
        .label("cond.true" + resul)
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest("cond.end" + resul)
            .build())
        .build();
    curBlock = thenB;
    register val1 = (register) visit(node.getLhs());
    // elseB
    irBlock elseB = irBlock.builder()
        .label("cond.false" + resul)
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest("cond.end" + resul)
            .build())
        .build();
    curBlock = elseB;
    register val2 = (register) visit(node.getRhs());
    // endB
    irBlock endB = irBlock.builder()
        .label("cond.end" + resul)
        .stmts(new vector<irStmt>())
        .build();
    curBlock = endB;
    var sel = irSelect.builder()
        .res(resul)
        .cond("TBD")
        .tp1(tp((typeinfo) node.getLhs().getType()))
        .val1(val1.getName())
        .tp2(tp((typeinfo) node.getRhs().getType()))
        .val2(val2.getName())
        .build();
    sel.setCond(cond.toString());
    curBlock.add(sel);

    curBlock = curFa;
    curBlock.add(thenB);
    curBlock.add(elseB);
    curBlock.add(endB);

    return register.builder()
        .name(resul)
        .build();
  }

  @Override
  public irNode visit(astAssignExprNode node) throws error {
    // calc lhs.ptr, calc rhs.val , store to lhs.ptr
    entity res2 = (entity) visit(node.getRhs());
    entity res1 = (entity) visit(node.getLhs());
    String resul;
    curBlock.add(irStore.builder()
        .tp(tp((typeinfo) node.getLhs().getType()))
        .res(res2.toString())
        .ptr(((register) res1).getPtr())
        .build());
    if (res2 instanceof register) {
      resul = ((register) res2).getName();
    } else {
      resul = curFunc.tmprename();
      curBlock.add(irBinary.builder()
          .op("add")
          .res(resul)
          .tp("i32")
          .op1(((constant) res2).toString())
          .op2("0")
          .build());
    }
    return register.builder()
        .name(resul)
        .ptr(((register) res1).getPtr())
        .build();
  }

  @Override
  public irNode visit(astAtomExprNode node) throws error {
    // use astAtom res to return a const or stringconst (or a funccall x)
    // register ? Lvalue = true
    // get the ptr where it is pre-located except for this.i
    if (node.isLValue()) {
      // Consider (this.)?
      String ptr = curS.getValPtr(node.getValue());
      var reg = register.builder()
          .name(curFunc.tmprename())
          .ptr(ptr)
          .build();
      // member of class
      if (ptr.charAt(1) == '0' && ptr.length() > 2) {
        // this.i
        var gep = irGetElement.builder()
            .res(curFunc.tmprename())
            .tp(tp((typeinfo) node.getType()))
            .ptrval("%this.copy") // this -> this.addr -> this.copy
            .tp1("i32").id1("0").tp2("i32")
            .id2(ptr.substring(2))
            .build();
        reg.setPtr(curFunc.tmprename());
        curBlock.add(gep);
      }

      curBlock.add(irLoad.builder()
          .res(reg.getName())
          .tp(tp((typeinfo) node.getType()))
          .ptr(ptr)
          .build());
      return reg;
    } else {
      if (node.getType().equals(SemanticChecker.thisType)) {
        // this -> this.addr -> this.copy
        var reg = register.builder()
            .name(curFunc.tmprename())
            .ptr("%this.copy")
            .build();
        curBlock.add(irLoad.builder()
            .res(reg.getName())
            .tp(tp((typeinfo) node.getType()))
            .ptr("%this.copy")
            .build());
        return reg;
      }
      if (!node.getType().equals(SemanticChecker.stringType))
        return constant.builder()
            .name(node.getValue())
            .build();
      else {
        // TODO String Const

        return null;
      }
    }
    // constant ? Lvalue = false

  }

  @Override
  public irNode visit(astBlockStmtNode node) throws error {
    for (var stmt : node.getStmts()) {
      visit(stmt);
    }
    return null;
  }

  @Override
  public irNode visit(astIfStmtNode node) throws error {
    // calc cond? true if.then if.else if.end whole is a stmt;

    entity cond = (entity) visit(node.getCond());

    String[] label = { "if.then" + curS.depth + "." + curS.sonN,
        "if.else" + (curS.depth + 1) + "." + curS.sonN,
        "if.end" + (curS.depth + 1) + "." + curS.sonN };
    // scope update for num
    curS.sonN++;
    //
    curBlock.add(irBranch.builder()
        .cond(((register) cond).getName())
        .iftrue(label[0])
        .iffalse(label[1])
        .build());
    var thenB = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[2])
            .build())
        .build();
    enter(node.getThenscope(), curS.depth + 1, curS.sonN++);
    curBlock = thenB;
    visit(node.getThenStmt());
    exit();

    var elseB = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[2])
            .build())
        .build();
    enter(node.getElsescope(), curS.depth + 1, curS.sonN++);
    curBlock = elseB;
    visit(node.getElseStmt());
    exit();
    curFunc.add(
        irIf.builder()
            .tbran(thenB)
            .fbran(elseB)
            .label(label[2])
            .build());
    return null;
  }

  @Override
  public irNode visit(astForStmtNode node) throws error {
    // for.init -> curblock
    // cond upd stmts still same block; different scope for label
    var curFa = curBlock;
    enter(node.getScope(), curS.depth + 1, curS.sonN++);
    visit(node.getInit());
    String[] label = { "for.cond" + curS.depth + "." + curS.selfN,
        "for.body" + curS.depth + "." + curS.selfN,
        "for.inc" + curS.depth + "." + curS.selfN,
        "for.end" + curS.depth + "." + curS.selfN };
    node.getScope().loopbr = label[3];
    node.getScope().loopct = label[2];
    curBlock.add(irJump.builder()
        .dest(label[0])
        .build());
    var cond = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .terminalstmt(irBranch.builder()
            .cond("TBD")
            .iftrue(label[1])
            .iffalse(label[3])
            .build())
        .build();
    curBlock = cond;
    var res = (entity) visit(node.getCond());
    if (res instanceof constant)
      ((irBranch) cond.getTerminalstmt()).setCond(((constant) res).toString());
    else
      ((irBranch) cond.getTerminalstmt()).setCond(((register) res).getName());
    // cond end;
    var body = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[2])
            .build())
        .build();
    curBlock = body;
    visit(node.getStmt());
    // body end;
    var inc = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[0])
            .build())
        .build();
    curBlock = inc;
    visit(node.getUpdate());

    curBlock = curFa;
    exit();
    curBlock.add(irFor.builder()
        .cond(cond)
        .inc(inc)
        .body(body)
        .label(label[3])
        .build());
    return null;
  }

  @Override
  public irNode visit(astWhileStmtNode node) throws error {
    // same as for
    var curFa = curBlock;
    enter(node.getScope(), curS.depth + 1, curS.sonN++);
    String[] label = { "while.cond" + curS.depth + "." + curS.selfN,
        "while.body" + curS.depth + "." + curS.selfN,
        "while.end" + curS.depth + "." + curS.selfN };
    node.getScope().loopbr = label[2];
    node.getScope().loopct = label[0];
    curS.sonN++;
    curBlock.add(irJump.builder()
        .dest(label[0])
        .build());
    var cond = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .terminalstmt(irBranch.builder()
            .cond("TBD")
            .iftrue(label[1])
            .iffalse(label[2])
            .build())
        .build();
    curBlock = cond;
    var res = (entity) visit(node.getCond());
    if (res instanceof constant)
      ((irBranch) cond.getTerminalstmt()).setCond(((constant) res).toString());
    else
      ((irBranch) cond.getTerminalstmt()).setCond(((register) res).getName());
    // cond end;
    var body = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[0])
            .build())
        .build();
    curBlock = body;
    visit(node.getStmt());
    // body end;
    curBlock = curFa;
    curBlock.add(irWhile.builder()
        .cond(cond)
        .body(body)
        .label(label[2])
        .build());
    exit();
    return null;
  }

  @Override
  public irNode visit(astContinueStmtNode node) throws error {
    // br the for scope to for end or while end
    curBlock.add(irJump.builder()
        .dest(curS.getflow("ct"))
        .build());
    return null;
  }

  @Override
  public irNode visit(astBreakStmtNode node) throws error {
    // br the for scope to for end or while end
    curBlock.add(irJump.builder()
        .dest(curS.getflow("br"))
        .build());
    return null;
  }

  @Override
  public irNode visit(astReturnStmtNode node) throws error {
    // br funcdef ret
    if (node.getExpr() != null) {
      entity res = (entity) visit(node.getExpr());
      curBlock.add(irStore.builder()
          .ptr("%ret.val")
          .res(res.toString())
          .build());
    }
    curBlock.add(irJump.builder()
        .dest("%0ret")
        .build());
    return null;
  }

  @Override
  public irNode visit(astExprStmtNode node) throws error {
    return visit(node.getExpr());
  }

  @Override
  public irNode visit(astVarDefStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astEmptyStmtNode node) throws error {
    return null;
  }

}
