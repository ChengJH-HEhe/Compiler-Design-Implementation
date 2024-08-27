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
import juhuh.compiler.util.Scope.ScopeType;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.ClassInfo;
import juhuh.compiler.util.info.FuncInfo;
import juhuh.compiler.util.info.typeinfo;

public class irBuilder implements astVisitor<irNode> {
  private Scope curS;
  private globalScope gScope;
  private irBlock curBlock, gIBlock;
  private irFuncDef curFunc;
  private irRoot rt;
  // count update 
  private int binaryCount = 0, loopCount = 0;
  // add label String

  private void enter(Scope scope, int depth, int selfN) {
    curS = scope;
    curS.depth = depth;
    curS.selfN = selfN;
  }

  private void exit() {
    curS = curS.parentScope();
  }

  private void switchBlock(irBlock thenB) {
    // var terminalTmp = curBlock.getTerminalstmt();
    // boolean actTMP = curBlock.getActual_t();
    // actual false -> if not break\continue
    // actual true -> mustn't be changed
    //
    if (curBlock.getActual_t() == null)
      curBlock.setTerminal(
          irJump.builder()
              .dest(thenB.getLabel())
              .build());
    if (curBlock != curFunc.getEntry())
      curFunc.add(curBlock);
    curBlock = thenB;
  }

  public irBuilder(globalScope gSc) {
    curS = gSc;
    gScope = gSc;
    for (var func : SemanticChecker.builtinFuncs) {
      curS.defineVariable(func.getName(), func);
    }
    gScope.addType("string", SemanticChecker.stringClass);
    SemanticChecker.stringClass.func = SemanticChecker.builtinInfo;

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
    return node.accept(this);
  }

  void builtinDef() {
    // string this & declare
    // other builtin declare
    // (add stringadd&strcmp)
    var stringInfo = ((ClassInfo) gScope.getTypeFromName("string")).func;
    rt.add(irStructDef.builder()
        .name("%class.string")
        .member(new vector<String>())
        .build());
    for (var decl : stringInfo) {
      decl.setName("string." + decl.getName());
      var func = irFuncDecl.builder()
          .info(decl)
          .paratypelist(new vector<String>("ptr"))
          .paravaluelist(new vector<String>("%this"))
          .build();
      int id = 0;
      for (var para : decl.getArgsType()) {
        func.getParatypelist().add(tp(para));
        func.getParavaluelist().add("%." + para.getName() + (id++));
      }
      rt.add(func);
    }
    rt.add(irStrDef.builder()
        .res("@.str.true")
        .init("true")
        .build());
    rt.add(irStrDef.builder()
        .res("@.str.false")
        .init("false")
        .build());
    // @_alloc_array(int length, ) length+=1, return malloc(length)
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.arraySizeFunc)
        .paratypelist(new vector<String>("ptr"))
        .paravaluelist(new vector<String>("%array"))
        .build());

    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.arrMallocFunc)
        .paratypelist(new vector<String>("i32"))
        .paravaluelist(new vector<String>("%size"))
        .build());

    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.stringAddFunc)
        .paratypelist(new vector<String>("ptr", "ptr"))
        .paravaluelist(new vector<String>("%str1", "%str2"))
        .build());

    // funcdecl
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.strcmpFunc)
        .paratypelist(new vector<String>("ptr", "ptr"))
        .paravaluelist(new vector<String>("%str1", "%str2"))
        .build());
    // int to string
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.toStringFunc)
        .paratypelist(new vector<String>("i32"))
        .paravaluelist(new vector<String>("%i"))
        .build());
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.printFunc)
        .paratypelist(new vector<String>("ptr"))
        .paravaluelist(new vector<String>("%str"))
        .build());
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.printlnFunc)
        .paratypelist(new vector<String>("ptr"))
        .paravaluelist(new vector<String>("%str"))
        .build());
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.printIntFunc)
        .paratypelist(new vector<String>("i32"))
        .paravaluelist(new vector<String>("%i"))
        .build());
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.printlnIntFunc)
        .paratypelist(new vector<String>("i32"))
        .paravaluelist(new vector<String>("%i"))
        .build());
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.getStringFunc)
        .paratypelist(new vector<String>())
        .paravaluelist(new vector<String>())
        .build());
    rt.add(irFuncDecl.builder()
        .info(SemanticChecker.getIntFunc)
        .paratypelist(new vector<String>())
        .paravaluelist(new vector<String>())
        .build());
  }

  @Override
  public irNode visit(astRoot node) throws error {
    irRoot rt = irRoot.builder()
        .globalDef(new vector<irGlobalDef>())
        .globalFDecl(new vector<irFuncDecl>())
        .FDef(new vector<irFuncDef>())
        .structDef(new vector<irStructDef>())
        .build();

    /*
     * string a = "123\n45\\";
     * int main(){}
     * 
     */
    this.rt = rt;
    // branch, return? curBlock not needed
    irFuncDef globalInit = irFuncDef.builder()
        .fName("__init__")
        .anonyNum(0)
        .retType("void")
        .paratypelist(new vector<String>())
        .paravaluelist(new vector<String>())
        .entry(irBlock.builder()
            .label("entry")
            .stmts(new vector<irStmt>())
            .terminalstmt(irJump.builder()
                .dest("return")
                .build())
            .build())
        .body(new vector<irBlock>())
        .ret(irBlock.builder()
            .label("return")
            .stmts(new vector<irStmt>())
            .terminalstmt(irRet.builder().tp("void").val("").build())
            .build())
        .build();
    gIBlock = globalInit.getEntry();
    irFuncDef main = null;
    // main add to Fdef
    astNode mainNode = null;

    builtinDef();

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

        var curtmp = curBlock;
        curBlock = gIBlock;
        curFunc = globalInit;
        irGlobalDef globalvar = null;
        if (!(Def).getType().getInfo().equals(SemanticChecker.intType)
            && !(Def).getType().getInfo().equals(SemanticChecker.boolType)) {
          // def %class.typename
          /*
           * tp all ptr
           * Def.getType().getInfo().isBuiltin()?
           * "ptr" : "%class." + Def.getType().getInfo().getName()
           */
          globalvar = (irGlobalDef.builder()
              .name(Def.getName() + ".0.0")
              .type("ptr")
              .init("null")
              .build());
        } else {
          globalvar = irGlobalDef.builder()
              .name((Def).getName() + ".0.0")
              .type(tp((Def).getType().getInfo()))
              .init("0")
              .build();
        }
        rt.add(globalvar);
        if ((Def).getUnit() != null) {
          var init = (Def).getUnit().accept(this);
          curBlock.add(irStore.builder()
              .tp(globalvar.getType())
              .res(init.toString())
              .ptr("@" + globalvar.getName())
              .build());
        }
        curS.defineVariable(def.getName(), ((astVarDefNode) def).getType().getInfo());
        curBlock = curtmp;
      }
    }

    main = (irFuncDef) mainNode.accept(this);
    // add global init call infront!
    var entry = main.getEntry();
      // getEntry SWITCH 
    var entry1 = irBlock.builder()
        .label("entry")
        .stmts(new vector<irStmt>(irCall.builder()
            .res("")
            .func(new FuncInfo(globalInit.getFName(), SemanticChecker.voidType))
            .type(new vector<String>())
            .val(new vector<String>())
            .build()))
        .build();
    for (var stmt : entry.getStmts()) {
      entry1.add(stmt);
    }
    entry1.setTerminalstmt(entry.getTerminalstmt());
    main.setEntry(entry1);

    rt.add(globalInit);
    rt.add(main);

    return rt;
  }

  @Override
  public irNode visit(astFuncDefNode node) throws error {
    // get irFuncDef cond -> findfor with only depth as the effective info
    irFuncDef func = irFuncDef.builder()
        .anonyNum(0)
        .fName(node.getInfo().getName())
        .retType(tp(node.getInfo().getRetType()))
        .paratypelist(new vector<String>())
        .paravaluelist(new vector<String>())
        .entry(irBlock.builder()
            .label("entry")
            .stmts(new vector<irStmt>())
            .build())
        .body(new vector<irBlock>())
        .build();
    // reminder curBlock to be the FuncDef entry block, entry br to the first
    // block
    // curBlock to be the curBlock
    // different label jump

    curBlock = func.getEntry();
    curFunc = func;

    enter(node.getOrigin(), 1, curS.sonN++);

    if (!node.getRet().equals("void")) {
      curBlock.add(irAlloca.builder()
          .res("%ret.val")
          .tp(tp(node.getInfo().getRetType()))
          .build());
    }
    // add this for class function
    if (curS.parentScope().type != Scope.ScopeType.GLOBAL) {
      func.getParatypelist().add("ptr");
      func.getParavaluelist().add("%this");
      curBlock.add(irAlloca.builder()
          .res("%this.addr." + curS.selfN)
          .tp("ptr")
          .build());
      curBlock.add(irStore.builder()
          .tp("ptr")
          .res("%this")
          .ptr("%this.addr." + curS.selfN)
          .build());
      curBlock.add(irLoad.builder()
          .tp("ptr")
          .res("%this.copy")
          .ptr("%this.addr." + curS.selfN)
          .build());
      // unused has set in symbol collector
      // func.setFName("@" + curS.parentScope().info.getName() + "." +
      // node.getInfo().getName());
    }
    for (var para : node.getArgs()) {
      func.getParatypelist().add(tp(para.getType().getInfo()));
      func.getParavaluelist().add("%" + para.getName());
      curBlock.add(irAlloca.builder()
          .res("%" + para.getName() + ".1." + curS.selfN)
          .tp(tp(para.getType().getInfo()))
          .build());
      curBlock.add(irStore.builder()
          .tp(tp(para.getType().getInfo()))
          .res("%" + para.getName())
          .ptr("%" + para.getName() + ".1." + curS.selfN)
          .build());
    }
    for (var stmt : node.getBlock().getStmts()) {
      // body stmt when to use curBlock? before any label
      stmt.accept(this);
    }

    if (node.getRet().equals("void")) {
      curFunc.setRet(irBlock.builder()
          .label("return")
          .terminalstmt(irRet.builder().tp("void").val("").build())
          .build());
    } else {
      var result = curFunc.tmprename();
      curFunc.setRet(
          irBlock.builder()
              .label("return")
              .stmts(new vector<irStmt>(
                  irLoad.builder()
                      .tp(tp(node.getInfo().getRetType()))
                      .res(result)
                      .ptr("%ret.val")
                      .build()))
              .terminalstmt(irRet.builder().tp(tp(node.getInfo().getRetType())).val(result).build())
              .build());
    }
    curBlock.setTerminal(irJump.builder()
        .dest("return")
        .build());
    curFunc.add(curBlock);
    exit();
    return func;
  }

  @Override
  public irNode visit(astClassDefNode node) throws error {
    enter(node.getOrigin(), 1, curS.sonN++);
    irClassDef cls = irClassDef.builder()
        .struct(irStructDef.builder()
            .name("%" + "class." + node.getInfo().getName())
            .member(new vector<String>())
            .build())
        .funcDefs(new vector<irFuncDef>())
        .build();
    // fields
    cls.getFuncDefs().add((irFuncDef) (node.getConstructor()).accept(this));
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
    // curBlock add alloca,
    // no this a.val
    String tmpname = curS.Varrename(node.getName());

    // class
    if (!node.getType().getInfo().equals(SemanticChecker.intType)
        && !node.getType().getInfo().equals(SemanticChecker.boolType)) {
      // def %class.typename
      /*
       * tp
       * node.getType().getInfo().isBuiltin()?
       * "ptr" : "%class." + node.getType().getInfo().getName()
       */
      curBlock.add(irAlloca.builder()
          .res(tmpname)
          .tp("ptr")
          .build());
    } else {
      // int bool str
      curBlock.add(irAlloca.builder()
          .res(tmpname)
          .tp(tp(node.getType().getInfo()))
          .build());
    }
    if (node.getUnit() != null) {
      var init = (node.getUnit()).accept(this);
      if (init != null)
        curBlock.add(irStore.builder()
            .tp(tp(node.getType().getInfo()))
            .res((init).toString())
            .ptr(tmpname)
            .build());
    }
    curS.defineVariable(node.getName(), node.getType().getInfo());
    return null;
  }

  public entity baseRes(typeinfo tp) {
    int size;
    if (tp.isBuiltin()) {
      size = 1;
    } else
      size = gScope.getTypeSizeFromName(tp.getName());
    register res = register.builder()
        .name(curFunc.tmprename())
        .build();
    curBlock.add(irAlloca.builder()
        .res(res.getName())
        .tp("ptr")
        .build());
    curBlock.add(irCall.builder()
        .res(curFunc.tmprename())
        .func(SemanticChecker.mallocFunc)
        .type(new vector<String>("i32"))
        .val(new vector<String>(Integer.toString(size)))
        .build());
    // 初始化函数
    if (!tp.isBuiltin()) {
      FuncInfo func = ((ClassInfo) gScope.getSafeTypeFromName(tp.getName())).funcs.get(tp.getName());
      curBlock.add(irCall.builder()
          .res("")
          .func(func)
          .type(new vector<String>("ptr"))
          .val(new vector<String>(res.getName()))
          .build());
    }
    return res;
  }

  @Override
  public irNode visit(astConstrNode node) throws error {
    // a::a(ptr this) another funcdef
    var Func = irFuncDef.builder()
        .fName("@" + node.getClassName() + "." + node.getClassName())
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
    curBlock = Func.getEntry();
    curFunc = Func;
    curBlock.add(irAlloca.builder()
        .res("%this.addr")
        .tp("ptr")
        .build());
    curBlock.add(irStore.builder()
        .tp("ptr")
        .res("%this")
        .ptr("%this.addr")
        .build());
    curBlock.add(irLoad.builder()
        .tp("ptr")
        .res("%this.copy")
        .ptr("%this.addr")
        .build());
    (node.getBlock()).accept(this);
    return Func;
  }

  entity newArray(typeinfo tp, int id, vector<astExprNode> lengths) {
    if (id == 0) {
      // typesize null
      return baseRes(tp);
    } else {
      // 1-D array
      // size
      entity size = (entity) (lengths.get(tp.getDim() - id)).accept(this);
      // size | ptr | ptr ... (ptr * length + 1) binary + 1
      // <- ptr

      register res = register.builder()
          .name(curFunc.tmprename())
          .build();
      res.setPtr(res.getName());
      var call = irCall.builder()
          .res(res.getName())
          .func(SemanticChecker.arrMallocFunc)
          .type(new vector<String>("i32"))
          .val(new vector<String>(size.toString()))
          .build();

      // res
      curBlock.add(call);

      // for(i 1->sz) res[i] = newArray(tp, id-1, lengths)
      // int i = 1
      var i = curFunc.getAnonyNum();
      curFunc.setAnonyNum(i + 1);
      curBlock.add(irBinary.builder()
          .res("%." + i)
          .op("and")
          .tp("i32")
          .op1("1")
          .op2("1")
          .build());
      var forCond = irBlock.builder()
          .label("for.cond." + i)
          .stmts(new vector<irStmt>(
              irBinary.builder()
                  .res("%cmp." + i)
                  .op("slt")
                  .tp("i32")
                  .op1("%." + i)
                  .op2(size.toString())
                  .build()))
          .terminalstmt(
              irBranch.builder()
                  .cond("%cmp." + i)
                  .iftrue("for.body." + i)
                  .iffalse("for.end." + i)
                  .build())
          .actual_t(false)
          .build();
      switchBlock(forCond);

      var forBody = irBlock.builder()
          .label("for.body." + i)
          .stmts(new vector<irStmt>())
          .terminalstmt(
              irJump.builder()
                  .dest("for.inc." + i)
                  .build())
          .actual_t(false)
          .build();
      // TODO: reminder actual_t==null -> terminal can be changed
      switchBlock(forBody);
      var resul = (entity) (newArray(tp, id - 1, lengths));
      var reg = register.builder()
          .name(curFunc.tmprename())
          .build();
      curBlock.add(irGetElement.builder()
          .res(reg.getName())
          .tp("ptr")
          .ptrval(res.getPtr())
          .tp1("i32")
          .id1("%." + i)
          .build());
      curBlock.add(irStore.builder()
          .tp("ptr")
          .res(resul.toString())
          .ptr(reg.getName())
          .build());
      var forInc = irBlock.builder()
          .label("for.inc." + i)
          .stmts(new vector<irStmt>(
              irBinary.builder()
                  .res("%." + i)
                  .op("add")
                  .tp("i32")
                  .op1("%." + i)
                  .op2("1")
                  .build()))
          .build();
      switchBlock(forInc);
      var forEnd = irBlock.builder()
          .label("for.end." + i)
          .stmts(new vector<irStmt>())
          .build();
      switchBlock(forEnd);
      return res;
    }
  }

  entity ArrayConst(int id, astArrayConstExpr arrConst) throws error {
    typeinfo tp = (typeinfo) arrConst.getType();
    if (id == 0 || tp == SemanticChecker.nullType) {
      return baseRes(tp);
    } else {
      // store
      var reg = register.builder()
          .name(curFunc.tmprename())
          .build();
      curBlock.add(irCall.builder()
          .res(reg.getName())
          .func(SemanticChecker.arrMallocFunc)
          .type(new vector<String>("i32"))
          .val(new vector<String>(Integer.toString(arrConst.getVec().size())))
          .build());
      for (int i = 0; i < arrConst.getVec().size(); ++i) {
        entity val = (id != 1) ? (entity) ArrayConst(id - 1, (astArrayConstExpr) (arrConst.getVec().get(i)))
            : (entity) arrConst.getVec().get(i).accept(this);
        var reg1 = register.builder()
            .name(curFunc.tmprename())
            .build();
        curBlock.add(irGetElement.builder()
            .res(reg1.toString())
            .tp("ptr")
            .ptrval(reg.getName())
            .tp1("i32")
            .id1(Integer.toString(i))
            .build());
        curBlock.add(irStore.builder()
            .tp(id != 1 ? "ptr" : tp(tp))
            .res(val.toString())
            .ptr(reg1.getName())
            .build());
      }
      return reg;
    }
  }

  @Override
  public irNode visit(astNewArrayExprNode node) throws error {
    typeinfo tp = (typeinfo) node.getType();
    if (node.getInit() != null) {
      // array const
      tp = (typeinfo) node.getInit().getType();
      return ArrayConst(tp.getDim(), node.getInit());
    }
    return (tp.getDim() == node.getLengths().size())
        ? newArray(tp, tp.getDim(), node.getLengths())
        : newArray(SemanticChecker.nullType, node.getLengths().size(), node.getLengths());
  }

  @Override
  public irNode visit(astCallExprNode node) throws error {
    // call func manage expr add to curBlock
    entity caller = null;
    if (node.getFunc() instanceof astMemberExprNode) {
      // add caller
      // arr_size
      if (((typeinfo) (((astMemberExprNode) node.getFunc()).getExpr()).getType()).getDim() > 0) {
        // size
        entity res = (entity) (((astMemberExprNode) node.getFunc()).getExpr()).accept(this);
        var resul = curFunc.tmprename();
        var reg = register.builder()
            .name(resul)
            .build();
        curBlock.add(irCall.builder()
            .res(resul)
            .func(SemanticChecker.arraySizeFunc)
            .type(new vector<String>("ptr"))
            .val(new vector<String>(res.toString()))
            .build());
        return reg;
      }
      caller = (entity) (((astMemberExprNode) node.getFunc()).getExpr()).accept(this);
    } else {
      // class (this.func)
      var tmpS = curS;
      while (tmpS != null && tmpS.type != Scope.ScopeType.CLASS) {
        tmpS = tmpS.parentScope();
      }
      if (tmpS != null && tmpS.containsVariable(((astAtomExprNode) node.getFunc()).getType().getName(), false) != null)
        caller = register.builder()
            .name("%this.copy")
            .ptr("%this.copy")
            .build();
    }

    var callnode = irCall.builder()
        .func((FuncInfo) node.getFunc().getType()) // funcname updated in sema , @ +
        .type(tp(node.getArgs())) // add 2 times
        .val(new vector<String>())
        .build();
    // CALLER = %this
    if (caller != null) {
      callnode.getVal().add(caller.toString());
      callnode.getType().add("ptr");
    }
    for (var arg : node.getArgs()) {
      entity res = (entity) (arg).accept(this);
      // res
      callnode.getVal().add(res.toString());
    }
    callnode.setRes(((FuncInfo) node.getFunc().getType()).getRetType()
        .equals(SemanticChecker.voidType) ? "" : curFunc.tmprename());
    curBlock.add(callnode);
    // return val

    return register.builder()
        .name(callnode.getRes())
        .build();
  }

  @Override
  public irNode visit(astArrayExprNode node) throws error {
    // return ptr using elementptr
    // array + sub
    entity res = (entity) (node.getArray()).accept(this);
    // res reg
    entity sub = (entity) (node.getSub()).accept(this);
    var resul = curFunc.tmprename();
    
    // arrayexpr getelement

    var gep = irGetElement.builder()
        .res(resul)
        .tp(tp((typeinfo) node.getType()))
        .ptrval(res.toString())
        .tp1("i32")
        .id1(sub.toString())
        .build();
    var reg = register.builder()
        .ptr(resul)
        .name(curFunc.tmprename())
        .build();
    // all need to add 0
    curBlock.add(gep);// resul
    curBlock.add(irLoad.builder() // reg
        .res(reg.getName())
        .tp(tp((typeinfo) node.getType()))
        .ptr(reg.getPtr())
        .build());
    
    return reg;
  }

  @Override
  public irNode visit(astArrayConstExpr node) throws error {
    // not expected to visit
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astMemberExprNode node) throws error {
    // Lvalue -> ptr
    // a + . + b ?
    entity res = (entity) (node.getExpr()).accept(this);
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
    // get the final String
    var VecExpr = new vector<String>();
    var VecStr = new vector<String>();
    // @.str.true
    for (var str : node.getVecExpr()) {
      entity res = (entity) (str).accept(this);
      if (str.getType() == SemanticChecker.stringType) {
        VecExpr.add(((register) res).getPtr());
      } else if (str.getType() == SemanticChecker.intType) {
        VecExpr.add(curFunc.tmprename());
        curBlock.add(irCall.builder()
            .res(VecExpr.getlst())
            .func(SemanticChecker.toStringFunc)
            .type(new vector<String>("i32"))
            .val(new vector<String>(res.toString()))
            .build());
      } else {
        assert (str.getType() == SemanticChecker.boolType);
        VecExpr.add(curFunc.tmprename());
        curBlock.add(irSelect.builder()
            .res(VecExpr.getlst())
            .cond(res.toString())
            .tp1("ptr")
            .val1("@.str.true")
            .tp2("ptr")
            .val2("@.str.false")
            .build());
      }
    }
    for (var str : node.getVecStr()) {
      // add StringConst
      var resul = "@.str." + (irStrDef.strNum++);
      rt.add(irStrDef.builder()
          .res(resul)
          .init(str.replace("$$", "$"))
          .build());
    }
    // call string add
    // if vecStr 1
    if (VecStr.size() == 1) {
      return register.builder()
          .name(VecStr.get(0))
          .ptr(VecStr.get(0))
          .build();
    }

    var resul = "@.str." + (irStrDef.strNum++);

    curBlock.add(irCall.builder()
        .res(resul)
        .func(SemanticChecker.stringAddFunc)
        .type(new vector<String>("ptr", "ptr"))
        .val(new vector<String>(VecStr.get(0), VecExpr.get(0)))
        .build());
    for (int i = 1; i < VecStr.size(); ++i) {
      var resul1 = "@.str." + (irStrDef.strNum++);
      curBlock.add(irCall.builder()
          .res(resul1)
          .func(SemanticChecker.stringAddFunc)
          .type(new vector<String>("ptr", "ptr"))
          .val(new vector<String>(resul, VecStr.get(i)))
          .build());
      resul = resul1;
      resul1 = "@.str." + (irStrDef.strNum++);
      if (i < VecExpr.size())
        curBlock.add(irCall.builder()
            .res(resul1)
            .func(SemanticChecker.stringAddFunc)
            .type(new vector<String>("ptr", "ptr"))
            .val(new vector<String>(resul, VecExpr.get(i)))
            .build());
      resul = resul1;
    }
    return register.builder()
        .name(resul)
        .ptr(resul)
        .build();
  }

  @Override
  public irNode visit(astUnaryExprNode node) throws error {
    entity res = (entity) (node.getExpr()).accept(this);
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
          .tp("i1")
          .op1("1")
          .op2(res.toString())
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
    entity res = (entity) (node.getExpr()).accept(this);
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
      // and 1 -> log.false
      irIns terminal = null;
      if (curBlock.getActual_t() != null && curBlock.getActual_t() == false) {
        terminal = curBlock.getTerminalstmt();
        curBlock.setActual_t(null);
      }
      String[] label = { "log.rhs" + (binaryCount), "log.end" + (binaryCount),
          "log.lhs" + (binaryCount), };
      binaryCount++;
      // iftrue -> res1赋值
      // iffalse 跳过res2赋值

      var lhs = (irBlock.builder()
          .label(label[2])
          .stmts(new vector<irStmt>())
          .build());
      switchBlock(lhs);
      entity res1 = (entity) (node.getLhs()).accept(this);
      curBlock.setTerminal(irBranch.builder()
          .cond(res1.toString())
          .iftrue(label[target])
          .iffalse(label[target ^ 1])
          .build());

      var rhs = (irBlock.builder()
          .label(label[0])
          .stmts(new vector<irStmt>())
          .terminalstmt(irJump.builder()
              .dest(label[1])
              .build())
          .actual_t(false)
          .build());
      switchBlock(rhs);
      entity res2 = (entity) (node.getRhs()).accept(this);

      var endB = irBlock.builder()
          .label(label[1])
          .stmts(new vector<irStmt>())
          .build();
      switchBlock(endB);
      if (terminal != null) {
        curBlock.setTerminal(terminal);
        curBlock.setActual_t(false);
      }

      String resul = curFunc.tmprename();
      if (node.getOp().equals("LogicOr"))
        curBlock.add(irSelect.builder()
            .res(resul)
            .cond(res1.toString())
            .tp1(tp((typeinfo) node.getLhs().getType()))
            .val1(res1.toString())
            .tp2(tp((typeinfo) node.getRhs().getType()))
            .val2(res2.toString())
            .build());
      else
        curBlock.add(irSelect.builder()
            .res(resul)
            .cond(res1.toString())
            .tp1(tp((typeinfo) node.getRhs().getType()))
            .val1(res2.toString())
            .tp2(tp((typeinfo) node.getLhs().getType()))
            .val2(res1.toString())
            .build());
      return register.builder()
          .name(resul)
          .build();
    } else if (node.getLhs().getType().equals(SemanticChecker.stringType)) {
      entity res1 = (entity) (node.getLhs()).accept(this),
          res2 = (entity) (node.getRhs()).accept(this);
      // string add

      String resul = curFunc.tmprename();
      var call = irCall.builder()
          .res(resul)
          .type(new vector<String>("ptr", "ptr"))
          .val(new vector<String>(((register) res1).getPtr(), ((register) res2).getPtr()))
          .build();
      String op = node.getOp();
      if (op.equals("Add")) {
        call.setFunc(SemanticChecker.stringAddFunc);
        curBlock.add(call);
      } else {
        call.setFunc(SemanticChecker.strcmpFunc);
        curBlock.add(call);
        resul = curFunc.tmprename();
        var icmp = irIcmp.builder()
            .res(resul)
            .tp("i32")
            .op(irIcmp.getop(op))
            .op1(call.getRes())
            .op2("0")
            .build();
        curBlock.add(icmp);
      }
      return register.builder()
          .ptr(resul)
          .build();
    } else {
      String Optp = irIcmp.getop(node.getOp());
      if (Optp != null) {
        // res
        entity res1 = (entity) (node.getLhs()).accept(this),
            res2 = (entity) (node.getRhs()).accept(this);

        String resul = curFunc.tmprename();
        curBlock.add(irIcmp.builder()
            .op(Optp)
            .res(resul)
            .tp(tp((typeinfo) node.getLhs().getType()))
            .op1(res1.toString())
            .op2(res2.toString())
            .build());
        return register.builder()
            .name(resul)
            .build();
      } else {
        // arithexpr done
        entity res1 = (entity) (node.getLhs()).accept(this),
            res2 = (entity) (node.getRhs()).accept(this);
        String resul = curFunc.tmprename();
        curBlock.add(irBinary.builder()
            .op(irBinary.builder().build().getop(node.getOp()))
            .res(resul)
            .tp("i32")
            .op1(res1.toString())
            .op2((res2).toString())
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

    entity cond = (entity) (node.getCond()).accept(this);
    String[] label = { "cond.true" + binaryCount, "cond.false" + binaryCount, "cond.end" + binaryCount };
    binaryCount++;
    irIns terminal = null;
    if (curBlock.getActual_t() != null && curBlock.getActual_t() == false) {
      terminal = curBlock.getTerminalstmt();
      curBlock.setActual_t(null);
    }
    // then else 都会算，只是最后要不要跳过的问题
    irBlock thenB = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .terminalstmt(irBranch.builder()
            .cond(cond.toString())
            .iftrue(label[2])
            .iffalse(label[1])
            .build())
        .actual_t(false)
        .build();
    // terminal stmt change if not -> thenB.label
    switchBlock(thenB);

    register val1 = (register) (node.getLhs()).accept(this);
    // elseB
    irBlock elseB = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[2])
            .build())
        .actual_t(false)
        .build();

    switchBlock(elseB);

    register val2 = (register) (node.getRhs()).accept(this);
    // endB
    irBlock endB = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .build();

    switchBlock(endB);
    if (terminal != null) {
      curBlock.setTerminal(terminal);
      curBlock.setActual_t(false);
    }
    String resul = curFunc.tmprename();
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

    return register.builder()
        .name(resul)
        .build();
  }

  @Override
  public irNode visit(astAssignExprNode node) throws error {
    // calc lhs.ptr, calc rhs.val , store to lhs.ptr
    entity res2 = (entity) (node.getRhs()).accept(this);
    entity res1 = (entity) (node.getLhs()).accept(this);
    curBlock.add(irStore.builder()
        .tp(tp((typeinfo) node.getLhs().getType()))
        .res(res2.toString())
        .ptr(((register) res1).getPtr())
        .build());
    return register.builder()
        .name(res2.toString())
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
        var resul = curFunc.tmprename();
        var gep = irGetElement.builder()
            .res(resul)
            .tp(tp((typeinfo) node.getType())) // TODO type?
            .ptrval("%this.copy") // this -> this.addr -> this.copy
            .tp1("i32").id1("0").tp2("i32")
            .id2(ptr.substring(2))
            .build();
        reg.setPtr(resul);
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
        return register.builder()
            .name("%this.copy")
            .build();
      }
      if (!node.getType().equals(SemanticChecker.stringType))
        return constant.builder()
            .name(node.getValue())
            .build();
      else {
        // StringConst global alloca; return ptr
        var resul = "@.str." + (irStrDef.strNum++);
        var reg = register.builder()
            .name(resul)
            .ptr(resul)
            .build();
        rt.add(irStrDef.builder()
            .res(resul)
            .init(node.toString())
            .build());
        return reg;
      }
    }
    // constant : Lvalue = false
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

    entity cond = (entity) (node.getCond()).accept(this);

    String[] label = { "if.then" + (curS.depth + 1) + "." + curS.sonN + "." + binaryCount,
        "if.else" + (curS.depth + 1) + "." + curS.sonN + "." + binaryCount,
        "if.end" + (curS.depth + 1) + "." + curS.sonN + "." + binaryCount};
    binaryCount++;
    // scope update for num
    curS.sonN++;
    //
    irIns terminal = null;
    if (curBlock.getActual_t() != null && curBlock.getActual_t() == false) {
      terminal = curBlock.getTerminalstmt();
      curBlock.setActual_t(null);
    }
    curBlock.setTerminal(irBranch.builder()
        .cond(cond.toString())
        .iftrue(label[0])
        .iffalse(label[1])
        .build());

    var thenB = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[2])
            .build())
        .actual_t(false)
        .build();
    switchBlock(thenB);

    enter(new Scope(curS, null, ScopeType.BLOCK), curS.depth + 1, curS.sonN++);
    visit(node.getThenStmt());
    exit();

    var elseB = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[2])
            .build())
        .actual_t(false)
        .build();
    switchBlock(elseB);

    enter(new Scope(curS, null, ScopeType.BLOCK), curS.depth + 1, curS.sonN++);
    if (node.getElseStmt() != null) {
      visit(node.getElseStmt());
    }
    exit();

    var endB = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .build();
    switchBlock(endB);
    if (terminal != null) {
      curBlock.setTerminal(terminal);
      curBlock.setActual_t(false);
    }
    return null;
  }

  @Override
  public irNode visit(astForStmtNode node) throws error {
    // for.init -> curblock
    // cond upd stmts still same block; different scope for label
    enter(new Scope(curS, null, ScopeType.LOOP), curS.depth + 1, curS.sonN++);
    curS.count = loopCount++;
    (node.getInit()).accept(this);
    String[] label = { "for.cond" + curS.depth + "." + curS.selfN + "." + curS.count,
        "for.body" + curS.depth + "." + curS.selfN + "." + curS.count,
        "for.inc" + curS.depth + "." + curS.selfN + "." + curS.count,
        "for.end" + curS.depth + "." + curS.selfN + "." + curS.count};
    node.getScope().loopbr = label[3];
    node.getScope().loopct = label[2];

    irIns terminal = null;
    if (curBlock.getActual_t() != null && curBlock.getActual_t() == false) {
      terminal = curBlock.getTerminalstmt();
      curBlock.setActual_t(null);
    }
    var cond = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .terminalstmt(irBranch.builder()
            .cond("TBD")
            .iftrue(label[1])
            .iffalse(label[3])
            .build())
        .actual_t(false)
        .build();
    switchBlock(cond);
    var res = (entity) (node.getCond()).accept(this);
    ((irBranch) cond.getTerminalstmt()).setCond(res.toString());

    var body = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[2])
            .build())
        .actual_t(false)
        .build();
    switchBlock(body);
    if (node.getStmt() instanceof astBlockStmtNode) {
      for (var stmt : ((astBlockStmtNode) node.getStmt()).getStmts()) {
        stmt.accept(this);
      }
    } else
      (node.getStmt()).accept(this);

    // body end;
    var inc = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[0])
            .build())
        .actual_t(false)
        .build();
    switchBlock(inc);
    visit(node.getUpdate());

    exit();

    var endB = irBlock.builder()
        .label(label[3])
        .stmts(new vector<irStmt>())
        .build();
    switchBlock(endB);
    if (terminal != null) {
      curBlock.setTerminal(terminal);
      curBlock.setActual_t(false);
    }
    return null;
  }

  @Override
  public irNode visit(astWhileStmtNode node) throws error {
    // same as for
    enter(new Scope(curS, null, ScopeType.LOOP), curS.depth + 1, curS.sonN++);
    curS.count = loopCount++;
    String[] label = { "while.cond" + curS.depth + "." + curS.selfN + "." + curS.count,
        "while.body" + curS.depth + "." + curS.selfN + "." + curS.count,
        "while.end" + curS.depth + "." + curS.selfN + "." + curS.count};
    node.getScope().loopbr = label[2];
    node.getScope().loopct = label[0];
    
    irIns terminal = null;
    if (curBlock.getActual_t() != null && curBlock.getActual_t() == false) {
      terminal = curBlock.getTerminalstmt();
      curBlock.setActual_t(null);
    }
    var cond = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .terminalstmt(irBranch.builder()
            .cond("TBD")
            .iftrue(label[1])
            .iffalse(label[2])
            .build())
        .actual_t(false)
        .build();
    switchBlock(cond);

    var res = (entity) (node.getCond()).accept(this);
    ((irBranch) cond.getTerminalstmt()).setCond(res.toString());

    // cond end;
    var body = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .terminalstmt(irJump.builder()
            .dest(label[0])
            .build())
        .actual_t(false)
        .build();
    switchBlock(body);
    if (node.getStmt() instanceof astBlockStmtNode) {
      for (var stmt : ((astBlockStmtNode) node.getStmt()).getStmts()) {
        stmt.accept(this);
      }
    } else
      (node.getStmt()).accept(this);

    // body end;
    var endB = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .build();
    curBlock.setTerminalstmt(irJump.builder()
        .dest(cond.getLabel())
        .build());
    switchBlock(endB);
    if (terminal != null) {
      curBlock.setTerminal(terminal);
      curBlock.setActual_t(false);
    }
    exit();
    return null;
  }

  @Override
  public irNode visit(astContinueStmtNode node) throws error {
    // br the for scope to for end or while end
    curBlock.setTerminal(irJump.builder()
        .dest(curS.getflow("ct"))
        .build());
    return null;
  }

  @Override
  public irNode visit(astBreakStmtNode node) throws error {
    // br the for scope to for end or while end
    curBlock.setTerminal(irJump.builder()
        .dest(curS.getflow("br"))
        .build());
    return null;
  }

  @Override
  public irNode visit(astReturnStmtNode node) throws error {
    // br funcdef ret
    if (node.getExpr() != null) {
      entity res = (entity) (node.getExpr()).accept(this);
      curBlock.add(irStore.builder()
          .tp(tp((typeinfo) node.getExpr().getType()))
          .ptr("%ret.val")
          .res(res.toString())
          .build());
    }
    curBlock.setTerminal(irJump.builder()
        .dest("return")
        .build());
    return null;
  }

  @Override
  public irNode visit(astExprStmtNode node) throws error {
    return node.getExpr().accept(this);
  }

  @Override
  public irNode visit(astVarDefStmtNode node) throws error {
    for (var def : node.getArray()) {
      def.accept(this);
    }
    return null;
  }

  @Override
  public irNode visit(astEmptyStmtNode node) throws error {
    return null;
  }

}
