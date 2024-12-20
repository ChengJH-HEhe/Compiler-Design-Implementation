package juhuh.compiler.frontend;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

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
  private irFuncDef curFunc;
  private irRoot rt;
  // count update
  private int binaryCount = 0, loopCount = 0;
  private int funCount = 0;
  // add label String

  private void enter(Scope scope, int depth, int selfN) {
    curS = scope;
    curS.depth = depth;
    curS.selfN = selfN;
  }

  Stack<irIns> termStack;
  private boolean defStatus = false;

  private void exit() {
    curS = curS.parentScope();
  }

  //
  private irIns switchBlock(irBlock thenB) {
    // var terminalTmp = curFunc.curBlock.getTerminalstmt();
    // actual true -> mustn't be changed
    irIns endTerm = curFunc.curBlock.getEndTerm();
    if (curFunc.curBlock.getTerminalstmt() == null) {
      curFunc.curBlock.setTerminal(irJump.builder()
          .dest(thenB.getLabel())
          .build());
    }
    // cond block may not br but the ones after it must br,
    // terminal > nxt.endterm > endterm
    if (!curFunc.curBlock.getLabel().equals("entry"))
      curFunc.add(curFunc.curBlock);
    curFunc.curBlock = thenB;
    return endTerm;
  }

  private void joinBlock(irBlock thenB, irIns endTerm) {
    if (curFunc.curBlock.getTerminalstmt() == null) {
      curFunc.curBlock.setTerminal(endTerm);
    }
    if (!curFunc.curBlock.getLabel().equals("entry"))
      curFunc.add(curFunc.curBlock);
    curFunc.curBlock = thenB;
  }

  private void add(irIns ins) {
    if (!curS.findTAG())
      curFunc.curBlock.add(ins);
  }

  public irBuilder(globalScope gSc) {
    curS = gSc;
    gScope = gSc;
    for (var func : SemanticChecker.builtinFuncs) {
      curS.defineVariable(func.getName(), func);
    }
    SemanticChecker.stringClass.func = SemanticChecker.builtinInfo;
    gScope.addType("string", SemanticChecker.stringClass);
    gSc.depth = 0;
    gSc.selfN = 0;
  }

  public static String tp(typeinfo tp) {
    if (tp.equals(SemanticChecker.intType)) {
      return "i32";
    } else if (tp.equals(SemanticChecker.boolType)) {
      return "i1";
    } else if (tp.equals(SemanticChecker.voidType)) {
      return "void";
    } else
      return "ptr";
  }

  public static String basetp(typeinfo tp) {
    if (tp.equals(SemanticChecker.intType)) {
      return "i32";
    } else if (tp.equals(SemanticChecker.boolType)) {
      return "i1";
    } else if (tp.equals(SemanticChecker.ptrType) || tp.getDim() > 0) {
      return "ptr";
    } else
      return "%class." + tp.getName();
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
        .info(SemanticChecker.mallocFunc)
        .paratypelist(new vector<String>("i32"))
        .paravaluelist(new vector<String>("%size"))
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

    // global init
    Scope InitScope = new Scope(gScope, null, ScopeType.FUNC);
    // branch, return? curFunc.curBlock not needed
    irFuncDef globalInit = irFuncDef.builder()
        .fName("__init__")
        .anonyNum(0)
        .id(funCount)
        .retType("void")
        .paratypelist(new vector<String>())
        .paravaluelist(new vector<String>())
        .entry(irBlock.builder()
            .label("entry")
            .stmts(new vector<irStmt>())
            .build())
        .body(new LinkedList<irBlock>())
        .ret(irBlock.builder()
            .label("return" + funCount)
            .stmts(new vector<irStmt>())
            .endTerm(irRet.builder().tp("void").val("").build())
            .build())
        .build();
    ++funCount;
    globalInit.curBlock = globalInit.getEntry();
    irFuncDef main = null;
    // main add to Fdef
    astNode mainNode = null;

    builtinDef();
    global = new HashMap<String, String>();
    for (var globaldef : node.getDefs()) {
      if (globaldef instanceof astVarDefNode) {
        var Def = (astVarDefNode) globaldef;
        var ptrName = curS.setNewVarPtr(Def.getName());
        global.put(ptrName, tp(Def.getType().getInfo()));
      }
    }
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
        var ptrName = curS.getValPtr(Def.getName());
        curFunc = globalInit;
        enter(InitScope, 1, curS.sonN++);
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
              .name(ptrName)
              .type("ptr")
              .init("null")
              .build());
        } else {
          globalvar = irGlobalDef.builder()
              .name(ptrName)
              .type(tp((Def).getType().getInfo()))
              .init("0")
              .build();
        }
        rt.add(globalvar);
        if ((Def).getUnit() != null) {
          var tmp = global2local;
          global2local = false;
          var init = (Def).getUnit().accept(this); // Call? store? main not needed.
          global2local = tmp;
          curFunc.curBlock.setVal(globalvar.getName(), init.toString(), globalvar.getType());
          add(irStore.builder()
              .tp(globalvar.getType())
              .res(init.toString())
              .ptr(globalvar.getName())
              .build());
        } // set null is unimportant
        exit();
        curS.defineVariable(def.getName(), ((astVarDefNode) def).getType().getInfo());
      }
    }
    // global init solve

    // main solve
    main = (irFuncDef) mainNode.accept(this);
    // add global init call infront!
    var entry = main.getEntry();
    // getEntry SWITCH
    // add global init?
    var entry1 = irBlock.builder()
        .label("entry")
        .stmts(new vector<irStmt>(irCall.builder()
            .res("")
            .func(new FuncInfo(globalInit.getFName(), SemanticChecker.voidType))
            .type(new vector<String>())
            .val(new vector<String>())
            .build()))
        .build();
    // storeGlobalInit();
    for (var stmt : entry.getStmts()) {
      entry1.add(stmt);
    }
    main.getEntry().setStmts(entry1.getStmts());

    if (globalInit.curBlock != globalInit.getEntry())
      globalInit.add(globalInit.curBlock);
    globalInit.checkRet(irJump.builder()
        .dest("return" + globalInit.getId())
        .build());
    rt.add(globalInit);
    // System.err.print(globalInit.toString());
    rt.add(main);
    // System.err.print(main.toString());
    return rt;
  }
  @Override
  public irNode visit(astFuncDefNode node) throws error {
    // get irFuncDef cond -> findfor with only depth as the effective info
    curFunc = irFuncDef.builder()
        .anonyNum(0)
        .fName(node.getInfo().getName())
        .id(funCount)
        .retType(tp(node.getInfo().getRetType()))
        .paratypelist(new vector<String>())
        .paravaluelist(new vector<String>())
        .entry(irBlock.builder()
            .label("entry")
            .stmts(new vector<irStmt>())
            .build())
        .body(new LinkedList<irBlock>())
        .build();
    ++funCount;
    // reminder curFunc.curBlock to be the FuncDef entry block, entry br to the
    // first
    // block
    // curFunc.curBlock to be the curFunc.curBlock
    // different label jump

    curFunc.curBlock = curFunc.getEntry();
    enter(node.getOrigin(), 1, curS.sonN++);
    global2local = (!(node.hasCall()));
    funcGlobalVarInit();
    if (!((FuncInfo) node.getInfo()).getRetType().equals(SemanticChecker.voidType)) {
      curFunc.getEntry().add(irAlloca.builder()
          .res("%ret.val")
          .tp(tp(node.getInfo().getRetType()))
          .build());
    }
    // add this for class function
    if (curS.parentScope().type != Scope.ScopeType.GLOBAL) {
      curFunc.getParatypelist().add("ptr");
      curFunc.getParavaluelist().add("%this");
      curFunc.getEntry().add(irAlloca.builder()
          .res("%this.addr." + curS.selfN)
          .tp("ptr")
          .build());
      curFunc.getEntry().setVal("%this.addr." + curS.selfN, "%this", "ptr");
      add(irStore.builder()
          .tp("ptr")
          .res("%this")
          .ptr("%this.addr." + curS.selfN)
          .build());
      add(irLoad.builder()
          .tp("ptr")
          .res("%this")
          .ptr("%this.addr." + curS.selfN)
          .build());
      // unused has set in symbol collector
      // func.setFName("@" + curS.parentScope().info.getName() + "." +
      // node.getInfo().getName());
    }
    // para? % + _?
    for (var para : node.getArgs()) {
      curFunc.getParatypelist().add(tp(para.getType().getInfo()));
      curFunc.getParavaluelist().add("%" + para.getName());
      var ptr = curS.setNewVarPtr(para.getName());
      curFunc.getEntry().add(irAlloca.builder()
          .res(ptr)
          .tp(tp(para.getType().getInfo()))
          .build());
      curFunc.getEntry().setVal(ptr, "%" + para.getName(), tp(para.getType().getInfo()));
      add(irStore.builder()
          .tp(tp(para.getType().getInfo()))
          .res("%" + para.getName())
          .ptr(ptr)
          .build());
    }
    if (node.getBlock() != null)
      for (var stmt : node.getBlock().getStmts()) {
        // body stmt when to use curFunc.curBlock? before any label
        if (curS.flowTag)
          break;
        stmt.accept(this);
      }
    // if !def add firstdef for main ret.val
    if (curFunc.getFName().equals("main")) {
      if (curFunc.curBlock.findVal("%ret.val") == null)
        curFunc.curBlock.setVal("%ret.val", "0", "i32");
      // store tp
      curFunc.checkRet(irStore.builder()
          .tp("i32")
          .res("0")
          .ptr("%ret.val")
          .build());
    } else
      curFunc.checkRet(irJump.builder()
          .dest("return" + curFunc.getId())
          .build());
    if (curFunc.curBlock != curFunc.getEntry())
      curFunc.add(curFunc.curBlock);
    if (((FuncInfo) node.getInfo()).getRetType().equals(SemanticChecker.voidType)) {
      curFunc.setRet(irBlock.builder()
          .label("return" + curFunc.getId())
          .stmts(new vector<irStmt>())
          .endTerm(irRet.builder().tp("void").val("").build())
          .build());
    } else {
      var result = curFunc.tmprename();
      var ret = irBlock.builder()
          .label("return" + curFunc.getId())
          .stmts(new vector<irStmt>(
              irLoad.builder()
                  .tp(tp(node.getInfo().getRetType()))
                  .res(result)
                  .ptr("%ret.val")
                  .build()))
          .endTerm(irRet.builder().tp(tp(node.getInfo().getRetType())).val(result).build())
          .build();
      ret.setFirstLoad("%ret.val", result);
      // regs
      ret.setVal("%ret.val", result, tp(node.getInfo().getRetType()));
      curFunc.setRet(ret);
    }
    curFunc.curBlock = curFunc.getRet();
    funcGlobalVarRestore();
    exit();
    return curFunc;
  }

  @Override
  public irNode visit(astClassDefNode node) throws error {
    enter(node.getOrigin(), 1, curS.sonN++);
    irClassDef cls = irClassDef.builder()
        .struct(irStructDef.builder()
            .name("%class." + node.getInfo().getName())
            .member(new vector<String>())
            .build())
        .funcDefs(new vector<irFuncDef>())
        .build();
    // fields
    if (node.getConstructor() != null) {
      cls.getFuncDefs().add((irFuncDef) (node.getConstructor()).accept(this));
    } else {
      // why not add a default constructor
      cls.getFuncDefs().add((irFuncDef) (astConstrNode.builder()
          .className(node.getInfo().getName())
          .scope(new Scope(curS, null, null))
          .build()).accept(this));
    }
    for (var field : node.getFields()) {
      cls.getStruct().getMember().add(tp(field.getType().getInfo()));
    }
    // methods
    for (var method : node.getMethods()) {
      irFuncDef result = (irFuncDef) method.accept(this);
      result.setFName(node.getInfo().getName() + "." + result.getFName());
      cls.getFuncDefs().add(result);
      // System.err.println(result.getFName());
    }
    exit();
    return cls;
  }

  @Override
  public irNode visit(astVarDefNode node) throws error {
    // curFunc.curBlock add alloca,
    // no this a.val
    String tmpname = curS.setNewVarPtr(node.getName());
    // class
    if (!node.getType().getInfo().equals(SemanticChecker.intType)
        && !node.getType().getInfo().equals(SemanticChecker.boolType)) {
      // def %class.typename
      /*
       * tp
       * node.getType().getInfo().isBuiltin()?
       * "ptr" : "%class." + node.getType().getInfo().getName()
       */
      curFunc.getEntry().add(irAlloca.builder()
          .res(tmpname)
          .tp("ptr")
          .build());
    } else {
      // int bool str
      curFunc.getEntry().add(irAlloca.builder()
          .res(tmpname)
          .tp(tp(node.getType().getInfo()))
          .build());
    }
    if (node.getUnit() != null) {
      var init = (node.getUnit()).accept(this);
      add(irStore.builder()
          .tp(tp(node.getType().getInfo()))
          .res((init).toString())
          .ptr(tmpname)
          .build());
      curFunc.curBlock.setVal(tmpname, (init).toString(), tp(node.getType().getInfo()));
    }
    curS.defineVariable(node.getName(), node.getType().getInfo());
    return null;
  }

  // 基类指针 引用类型 ptr alloc对应空间
  public entity baseRes(typeinfo tp, boolean newVar) {
    int size;
    if (tp == SemanticChecker.nullType)
      return register.builder()
          .name("null")
          .build();
    if (tp.isBuiltin()) {
      size = 1;
    } else
      size = gScope.getTypeSizeFromName(tp.getName());
    register res = register.builder()
        .name(curFunc.tmprename())
        .build();
    add(irCall.builder()
        .res(res.getName())
        .func(SemanticChecker.mallocFunc)
        .type(new vector<String>("i32"))
        .val(new vector<String>(Integer.toString(size)))
        .build());
    // 初始化函数
    if (!tp.isBuiltin() && newVar) {
      FuncInfo func = new FuncInfo(tp.getName() + "." + tp.getName(), SemanticChecker.ptrType);
      add(irCall.builder()
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
    enter(new Scope(curS, null, ScopeType.FUNC), curS.depth + 1, curS.sonN++);
    // change1 constructor name

    curFunc = irFuncDef.builder()
        .fName(node.getClassName() + "." + node.getClassName())
        .retType("void")
        .id(funCount)
        .paratypelist(new vector<String>("ptr"))
        .paravaluelist(new vector<String>("%this"))
        .entry(irBlock.builder()
            .label("entry")
            .stmts(new vector<irStmt>())
            .build())
        .ret(irBlock.builder()
            .label("return" + funCount)
            .stmts(new vector<irStmt>())
            .endTerm(irRet.builder().tp("void").val("").build())
            .build())
        .body(new LinkedList<irBlock>())
        .build();
    ++funCount;
    curFunc.curBlock = curFunc.getEntry();
    global2local = (!(node.hasCall()));
    funcGlobalVarInit();
    // construct func

    curFunc.getEntry().add(irAlloca.builder()
        .res("%this.addr")
        .tp("ptr")
        .build());
    curFunc.getEntry().setVal("%this.addr." + curS.selfN, "%this", "ptr");
    add(irStore.builder()
        .tp("ptr")
        .res("%this")
        .ptr("%this.addr")
        .build());
    add(irLoad.builder()
        .tp("ptr")
        .res("%this")
        .ptr("%this.addr")
        .build());
    if (node.getBlock() != null)
      (node.getBlock()).accept(this);
    if (curFunc.curBlock != curFunc.getEntry())
      curFunc.add(curFunc.curBlock);
    // last but 1
    curFunc.checkRet(irJump.builder()
    .dest("return" + curFunc.getId()).build());

    curFunc.setCurBlock(curFunc.getRet());
    funcGlobalVarRestore();
    exit();
    return curFunc;

  }

  entity newArray(typeinfo tp, int remainLayer, vector<astExprNode> lengths) {
    // 1-D array
    // size

    entity size = (entity) (lengths.get(lengths.size() - remainLayer)).accept(this);
    // size | ptr | ptr ... (ptr * length + 1) binary + 1
    // <- ptr
    /*
     * upd : 1-d ptr -> gep return their value type
     * remove %i = manytimes use ptr %.i to note the "i" ptr
     */
    register res = register.builder()
        .name(curFunc.tmprename())
        .build();
    var call = irCall.builder()
        .res(res.getName())
        .func(SemanticChecker.arrMallocFunc)
        .type(new vector<String>("i32"))
        .val(new vector<String>(size.toString()))
        .build();
    // res = malloc(size)
    add(call);
    if (remainLayer == 1) {
      return res;
    }
    // for(i 1->sz) res[i] = newArray(tp, remainLayer-1, lengths)
    // int i = 1
    var i = loopCount++;
    curFunc.getEntry().add(irAlloca.builder()
        .res("%." + i)
        .tp("i32")
        .build());
    add(irStore.builder()
        .tp("i32")
        .res("0")
        .ptr("%." + i)
        .build());
    curFunc.curBlock.setVal("%." + i, "0", "i32");

    var resCond = curFunc.tmprename(); // load i
    var brCond = curFunc.tmprename(); // determine i <= size

    var forCond = irBlock.builder()
        .label("for.cond." + i)
        .stmts(new vector<irStmt>(
            irLoad.builder()
                .res(resCond)
                .tp("i32")
                .ptr("%." + i)
                .build(),
            irIcmp.builder()
                .res(brCond)
                .op("slt")
                .tp("i32")
                .op1(resCond)
                .op2(size.toString())
                .build()))
        .terminalstmt(
            irBranch.builder()
                .cond(brCond)
                .iftrue("for.body." + i)
                .iffalse("for.end." + i)
                .build())
        .build();
    forCond.setFirstLoad("%." + i, resCond);
    forCond.setVal("%." + i, resCond, "i32");
    var endBTerm = switchBlock(forCond);

    var forBody = irBlock.builder()
        .label("for.body." + i)
        .stmts(new vector<irStmt>())
        // forBody
        .endTerm(
            irJump.builder()
                .dest("for.inc." + i)
                .build())
        .build();
    switchBlock(forBody);

    var resul = (entity) (newArray(tp, remainLayer - 1, lengths));
    var reg = register.builder()
        .name(curFunc.tmprename())
        .build();
    forBody.setFirstLoad("%." + i, resCond);
    add(irGetElement.builder()
        .res(reg.getName())
        .tp("ptr")
        .ptrval(res.getName())
        .tp1("i32")
        .id1(resCond)
        .build());
    add(irStore.builder()
        .tp("ptr")
        .res(resul.toString())
        .ptr(reg.getName())
        .build());

    var resInc = curFunc.tmprename();
    var forInc = irBlock.builder()
        .label("for.inc." + i)
        .stmts(new vector<irStmt>(
            irBinary.builder()
                .res(resInc)
                .op("add")
                .tp("i32")
                .op1(resCond)
                .op2("1")
                .build(),
            irStore.builder()
                .tp("i32")
                .res(resInc)
                .ptr("%." + i)
                .build()))
        .terminalstmt(
            irJump.builder()
                .dest("for.cond." + i)
                .build())

        .build();
    forInc.setFirstLoad("%." + i, resCond);
    forInc.setVal("%." + i, resInc, "i32");
    switchBlock(forInc);
    var forEnd = irBlock.builder()
        .label("for.end." + i)
        .stmts(new vector<irStmt>())
        .endTerm(endBTerm)
        .build();
    switchBlock(forEnd);
    return res;
  }

  entity ArrayConst(int remainLayer, astArrayConstExpr arrConst) throws error {
    typeinfo tp = (typeinfo) arrConst.getType();
    if (remainLayer == 0 || tp == SemanticChecker.nullType) {
      return baseRes(new typeinfo(tp.getName(), 0), true);
    } else {
      // store
      var reg = register.builder()
          .name(curFunc.tmprename())
          .build();
      add(irCall.builder()
          .res(reg.getName())
          .func(SemanticChecker.arrMallocFunc)
          .type(new vector<String>("i32"))
          .val(new vector<String>(Integer.toString(arrConst.getVec().size())))
          .build());
      for (int i = 0; i < arrConst.getVec().size(); ++i) {
        entity val = (remainLayer != 1)
            ? (entity) ArrayConst(remainLayer - 1, (astArrayConstExpr) (arrConst.getVec().get(i)))
            : (entity) arrConst.getVec().get(i).accept(this);
        var reg1 = register.builder()
            .name(curFunc.tmprename())
            .build();
        add(irGetElement.builder()
            .res(reg1.toString())
            .tp("ptr")
            .ptrval(reg.getName())
            .tp1("i32")
            .id1(Integer.toString(i))
            .build());
        add(irStore.builder()
            .tp(remainLayer != 1 ? "ptr" : basetp(new typeinfo(tp.getName(), 0)))
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
    if (node.getInit() != null)
      // array const
      tp = (typeinfo) node.getInit().getType();
    if (tp.getDim() == 0) {
      return baseRes(tp, true);
    }
    if (node.getInit() != null) {
      // array const
      return ArrayConst(tp.getDim(), node.getInit());
    }
    // getDim 4 getlengths = 2
    //
    return (tp.getDim() == (node.getLengths() == null ? 0 : node.getLengths().size()))
        ? newArray(tp, tp.getDim(), node.getLengths())
        : newArray(SemanticChecker.nullType, node.getLengths() == null ? 0 : node.getLengths().size(),
            node.getLengths());
  }

  // node.getFunc.name correct
  private boolean global2local = true;

  void funcGlobalVarInit() {
    if (!global2local)
      return;
      // all global var? not so much! 
      // the ones used in the function is enough
    for (var gv : global.entrySet()) {
      var tmp = "%" + gv.getKey().substring(1) + ".local";
      add(irAlloca.builder()
          .tp(gv.getValue())
          .res(tmp)
          .build());
      var tmpLoad = curFunc.tmprename();
      var tp = gv.getValue();
      curFunc.getEntry().add(irLoad.builder()
          .tp(tp)
          .res(tmpLoad)
          .ptr(gv.getKey())
          .build());
      curFunc.getEntry().add(irStore.builder()
        .res(tmpLoad)
        .ptr(tmp)
        .tp(tp)
      .build());
      // unused.
      // if (tmp.equals("%countC.0.2.local"))
      //   System.err.println("233");
      curFunc.getEntry().setVal(tmp, tmpLoad, tp);
    }
  }
  private boolean added = true;
  void funcGlobalVarRestore() {
    if (!global2local)
      return;
    for (var gvE : global.entrySet()) {
      var tmp = "%" + gvE.getKey().substring(1) + ".local";
      // load from tmp, store to gv;
      var tp = gvE.getValue();
      var reg = register.builder().ptr(tmp).build();
      added = false;
      LoadLvalue(reg, tp);
      added = true;
      curFunc.curBlock.add(irStore.builder()
          .tp(tp)
          .res(reg.toString())
          .ptr(gvE.getKey())
          .build());
    }
  }
  // current unUsed not always unUsed.
  private void storeGlobal() {
    if (!global2local)
      return;
    for (var gvE : global.entrySet()) {
      var tmp = "%" + gvE.getKey().substring(1) + ".local";
      // load from tmp, store to gv;
      var reg = register.builder()
          .ptr(tmp)
          .build();
      LoadLvalue(reg, gvE.getValue());
      add(irStore.builder()
          .tp(gvE.getValue())
          .res(reg.getName())
          .ptr(gvE.getKey())
          .build());
    }
  }

  private void loadGlobal() {
    if (!global2local)
      return;
    for (var gvE : global.entrySet()) {
      var tmpLoad = curFunc.tmprename();
      var tp = gvE.getValue();
      var tmp = "%" + gvE.getKey().substring(1) + ".local";
      add(irLoad.builder()
          .tp(tp)
          .res(tmpLoad)
          .ptr(gvE.getKey())
          .build());
      add(irStore.builder()
          .tp(tp)
          .res(tmpLoad)
          .ptr(tmp)
          .build());
      curFunc.curBlock.setVal(tmp, tmpLoad, tp);
    }
  }

  @Override
  public irNode visit(astCallExprNode node) throws error {
    // call func manage expr add to curFunc.curBlock
    entity caller = null;
    // consider str call this str.substring
    storeGlobal();
    if (node.getFunc() instanceof astMemberExprNode) {
      // add caller
      // caller.method
      // arr_size
      if (((typeinfo) (((astMemberExprNode) node.getFunc()).getExpr()).getType()).getDim() > 0) {
        // size
        entity res = (entity) (((astMemberExprNode) node.getFunc()).getExpr()).accept(this);
        var resul = curFunc.tmprename();
        var reg = register.builder()
            .name(resul)
            .build();
        add(irCall.builder()
            .res(resul)
            .func(SemanticChecker.arraySizeFunc)
            .type(new vector<String>("ptr"))
            .val(new vector<String>(res.toString()))
            .build());
        return reg;
      }
      caller = (entity) (((astMemberExprNode) node.getFunc()).getExpr()).accept(this);
    } else {
      // class (this).func
      var tmpS = curS;
      while (tmpS != null && tmpS.type != Scope.ScopeType.CLASS) {
        tmpS = tmpS.parentScope();
      }
      // System.err.print("IR PROCESS ");
      // System.err.print(((astAtomExprNode) node.getFunc()).getType().getName() + "
      // ");
      if (tmpS != null
          && tmpS.containsVariable(((astAtomExprNode) node.getFunc()).getType().getName(), false) != null) {
        caller = register.builder()
            .name("%this")
            .build();
        // S ystem.err.print(((astAtomExprNode) node.getFunc()).getType().getName());
      }
      // S ystem.err.println(" IR ATOM");
    }
    // callnode , add class.name
    var callnode = irCall.builder()
        .func((FuncInfo) node.getFunc().getType()) // funcname updated in sema , @ +
        .type(new vector<String>()) // add 2 times
        .val(new vector<String>())
        .build();
    if (caller != null) {
      callnode.getVal().add(caller.toString());
      callnode.getType().add("ptr");
    }
    for (var arg : node.getArgs()) {
      callnode.getType().add(tp((typeinfo) arg.getType()));
    }
    // CALLER = %this
    for (var arg : node.getArgs()) {
      entity res = (entity) (arg).accept(this);
      // res
      callnode.getVal().add(res.toString());
    }
    callnode.setRes(((FuncInfo) node.getFunc().getType()).getRetType()
        .equals(SemanticChecker.voidType) ? "" : curFunc.tmprename());

    add(callnode);
    // return val
    loadGlobal();
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

    // arrayexpr getelement
    var resul = curFunc.tmprename();
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
    add(gep);// resul
    add(irLoad.builder() // reg
        .res(reg.getName())
        .tp(tp((typeinfo) node.getType()))
        .ptr(reg.getPtr())
        .build());
    return reg;
  }

  @Override
  public irNode visit(astArrayConstExpr node) throws error {
    // not expected to visit
    typeinfo tp = (typeinfo) node.getType();
    return ArrayConst(tp.getDim(), node);
  }

  @Override
  public irNode visit(astMemberExprNode node) throws error {
    // Lvalue -> ptr
    // a + . + b ?
    entity res = (entity) (node.getExpr()).accept(this);
    // String ptr = ((register) res).getPtr();
    var reg = register.builder()
        .build();
    // reg ptr? -> name null else name = Store
    ClassInfo info = (ClassInfo) gScope.getTypeFromName(((typeinfo) node.getExpr().getType()).getName());

    reg.setPtr(curFunc.tmprename());
    var gep = irGetElement.builder()
        .res(reg.getPtr())
        .tp(basetp(info.vars.get(node.getMember())))
        .ptrval(res.toString())
        .tp1("i32").id1("0")
        .tp2("i32")
        .id2(info.varsId.get(node.getMember()).toString())
        .build();
    add(gep);
    reg.setName(curFunc.tmprename());
    add(irLoad.builder()
        .res(reg.getName())
        .tp(tp((typeinfo) node.getType()))
        .ptr(reg.getPtr())
        .build());
    return reg;
  }

  @Override
  public irNode visit(astFStrExpr node) throws error {
    // get the final String
    var VecExpr = new vector<String>();
    var VecStr = new vector<String>();
    // @.str.true

    if (node.getVecExpr().size() == 0) {
      String def = ("@.str." + (irStrDef.strNum++));
      rt.add(irStrDef.builder()
          .res(VecStr.get(0))
          .init(node.getVecStr().get(0).replace("$$", "$"))
          .build());
      return register.builder()
          .name(def)
          .build();
    }

    for (var str : node.getVecExpr()) {
      entity res = (entity) (str).accept(this);
      if (str.getType().equals(SemanticChecker.stringType)) {
        VecExpr.add(res.toString());
      } else if (str.getType().equals(SemanticChecker.intType)) {
        VecExpr.add(curFunc.tmprename());
        add(irCall.builder()
            .res(VecExpr.getlst())
            .func(SemanticChecker.toStringFunc)
            .type(new vector<String>("i32"))
            .val(new vector<String>(res.toString()))
            .build());
      } else {
        assert (str.getType().equals(SemanticChecker.boolType));
        VecExpr.add(curFunc.tmprename());
        add(irSelect.builder()
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
      VecStr.add(resul);
      rt.add(irStrDef.builder()
          .res(resul)
          .init(str.replace("$$", "$"))
          .build());
    }
    // call string add
    // if vecStr 1
    var resul = "%.str." + (irStrDef.strNum++);
    if (VecExpr.size() > 0)
      add(irCall.builder()
          .res(resul)
          .func(SemanticChecker.stringAddFunc)
          .type(new vector<String>("ptr", "ptr"))
          .val(new vector<String>(VecStr.get(0), VecExpr.get(0)))
          .build());
    else {
      add(irCall.builder()
          .res(resul)
          .func(SemanticChecker.stringAddFunc)
          .type(new vector<String>("ptr", "ptr"))
          .val(new vector<String>(VecStr.get(0), VecStr.get(1)))
          .build());
    }
    for (int i = 1; i < VecStr.size(); ++i) {
      var resul1 = "%.str." + (irStrDef.strNum++);
      add(irCall.builder()
          .res(resul1)
          .func(SemanticChecker.stringAddFunc)
          .type(new vector<String>("ptr", "ptr"))
          .val(new vector<String>(resul, VecStr.get(i)))
          .build());
      resul = resul1;
      if (i < VecExpr.size()) {
        resul1 = "%.str." + (irStrDef.strNum++);
        add(irCall.builder()
            .res(resul1)
            .func(SemanticChecker.stringAddFunc)
            .type(new vector<String>("ptr", "ptr"))
            .val(new vector<String>(resul, VecExpr.get(i)))
            .build());
        resul = resul1;
      }
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
      add(irBinary.builder()
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
      add(irBinary.builder()
          .op("xor")
          .res(resul)
          .tp("i32")
          .op2("-1")
          .op1(((register) res).getName())
          .build());
      return register.builder()
          .name(resul)
          .build();
    } else if (node.getOp().equals("LogicNot")) {
      String resul = curFunc.tmprename();
      add(irBinary.builder()
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
      String resul1 = curFunc.tmprename();
      // fix : should store res.tostring
      add(irBinary.builder()
          .op(node.getOp().equals("Increment") ? "add" : "sub")
          .res(resul1)
          .tp("i32")
          .op1(res.toString())
          .op2("1")
          .build());
      curFunc.curBlock.setVal(((register) res).getPtr(), resul1, "i32");
      add(irStore.builder()
          .tp("i32")
          .res(resul1)
          .ptr(((register) res).getPtr())
          .build());
      return register.builder()
          .name(res.toString())
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

    // res in a name & ptr store in the ptrname;
    String resul = curFunc.tmprename();
    add(irBinary.builder()
        .op(node.getOp().equals("Increment") ? "add" : "sub")
        .res(resul)
        .tp("i32")
        .op1(((register) res).getName())
        .op2("1")
        .build());
    curFunc.curBlock.setVal(((register) res).getPtr(), resul, "i32");
    add(irStore.builder()
        .tp("i32")
        .res(resul)
        .ptr(((register) res).getPtr())
        .build());
    return register.builder()
        .name(resul)
        .ptr(((register) res).getPtr())
        .build();

  }

  // TODO select: cond marked;
  private String cond;

  @Override
  public irNode visit(astBinaryExprNode node) throws error {
    // calc the res to the curFunc.curBlock & return the res to a register node
    if (node.getOp().equals("LogicAnd") || node.getOp().equals("LogicOr")) {
      // short circuit
      int target = node.getOp().equals("LogicAnd") ? 0 : 1;
      // and 1 -> log.false
      cond = "%log.res" + binaryCount;
      curFunc.getEntry().add(irAlloca.builder()
          .res(cond)
          .tp("i1")
          .build());

      String[] label = { "log.rhs" + (binaryCount), "log.lhs" + (binaryCount), "log.end" + (binaryCount),
          "log.cond" + (binaryCount) };
      binaryCount++;
      // iftrue -> res1赋值
      // iffalse 跳过res2赋值
      var codi = (irBlock.builder()
          .label(label[3])
          .stmts(new vector<irStmt>())
          .build());
      var endBTerm = switchBlock(codi);

      entity res1 = (entity) (node.getLhs()).accept(this);
      // lhs may still not right
      var curBTerm = (irIns) (irBranch.builder()
          .cond(res1.toString())
          .iftrue(label[target])
          .iffalse(label[target ^ 1])
          .build());

      var lhs = (irBlock.builder().label(label[1]).stmts(new vector<irStmt>()).build());
      joinBlock(lhs, curBTerm);
      curBTerm = irJump.builder().dest(label[2]).build();
      // 3 m -> 同一级
      lhs.setVal(cond, res1.toString(), "i1");
      lhs.add(irStore.builder().res(res1.toString()).ptr(cond).tp("i1").build());

      var rhs = (irBlock.builder().label(label[0]).stmts(new vector<irStmt>()).build());
      joinBlock(rhs, curBTerm);
      // 3 m -> 同一级
      entity res2 = (entity) (node.getRhs()).accept(this);
      rhs.setVal(cond, res2.toString(), "i1");
      rhs.add(irStore.builder().res(res2.toString()).ptr(cond).tp("i1").build());

      var endB = irBlock.builder()
          .label(label[2])
          .stmts(new vector<irStmt>())
          .endTerm(endBTerm)
          .build();
      joinBlock(endB, curBTerm);

      String resul = curFunc.tmprename();
      endB.setFirstLoad(cond, resul);
      endB.setVal(cond, resul,"i1");
      endB.add(irLoad.builder()
          .res(resul)
          .ptr(cond)
          .tp("i1")
          .build());
      return register.builder()
          .name(resul)
          .build();
    } else if (node.getLhs().getType().equals(SemanticChecker.stringType)) {
      entity res1 = (entity) (node.getLhs()).accept(this);
      entity res2 = (entity) (node.getRhs()).accept(this);
      // string add

      String resul = curFunc.tmprename();
      var call = irCall.builder()
          .res(resul)
          .type(new vector<String>("ptr", "ptr"))
          .val(new vector<String>(res1.toString(), (res2.toString())))
          .build();
      String op = node.getOp();
      if (op.equals("Plus")) {
        call.setFunc(SemanticChecker.stringAddFunc);
        add(call);
      } else {
        call.setFunc(SemanticChecker.strcmpFunc);
        add(call);
        resul = curFunc.tmprename();
        var icmp = irIcmp.builder()
            .res(resul)
            .tp("i32")
            .op(irIcmp.getop(op))
            .op1(call.getRes())
            .op2("0")
            .build();
        add(icmp);
      }
      return register.builder()
          .name(resul)
          .build();
    } else {
      String Optp = irIcmp.getop(node.getOp());
      if (Optp != null) {
        // res
        entity res1 = (entity) (node.getLhs()).accept(this);
        entity res2 = (entity) (node.getRhs()).accept(this);

        String resul = curFunc.tmprename();
        add(irIcmp.builder()
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
        // arith expr done
        entity res1 = (entity) (node.getLhs()).accept(this);
        entity res2 = (entity) (node.getRhs()).accept(this);
        String resul = curFunc.tmprename();
        add(irBinary.builder()
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
    String[] label = { "cond.true" + binaryCount, "cond.false" + binaryCount, "cond.end" + binaryCount,
    };
    binaryCount++;
    // then else 都会算，只是最后要不要跳过的问题 ? conditionbegin
    // ->end last option
    // 链接上一个block 的 endBTerm

    // 再加入一个点

    irIns endBTerm = curFunc.curBlock.getEndTerm();
    irIns curBTerm = irJump.builder().dest(label[2]).build();
    // 万一没有 terminal怎么办？
    curFunc.curBlock.setTerminal(irBranch.builder()
        .cond(cond.toString())
        .iftrue(label[0])
        .iffalse(label[1])
        .build());
    irBlock thenB = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .endTerm(curBTerm)
        .build();
    joinBlock(thenB, curBTerm);
    var val1 = (node.getLhs()).accept(this); // terminal stmt change if not -> thenB.label

    irBlock elseB = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .build();
    // 需要跳过的 不能直接链接 不然就没用了
    // 达到上一级终点
    joinBlock(elseB, curBTerm); // 设置为 jump
    curBTerm = irJump.builder()
        .dest(label[2])
        .build();
    var val2 = (node.getRhs()).accept(this);
    // endB
    irBlock endB = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .endTerm(endBTerm)
        .build();
    // 到上一级
    joinBlock(endB, curBTerm);

    String resul = null;
    if (node.getLhs().getType() != SemanticChecker.voidType) {
      resul = curFunc.tmprename();
      var sel = irSelect.builder()
          .res(resul)
          .cond("TBD")
          .tp1(tp((typeinfo) node.getLhs().getType()))
          .val1(val1.toString())
          .tp2(tp((typeinfo) node.getRhs().getType()))
          .val2(val2.toString())
          .build();
      sel.setCond(cond.toString());
      add(sel);
    }

    return register.builder()
        .name(resul)
        .build();
  }

  @Override
  public irNode visit(astAssignExprNode node) throws error {
    // calc lhs.ptr, calc rhs.val , store to lhs.ptr
    entity res2 = (entity) (node.getRhs()).accept(this);
    // only consider a = 1; or a = b + c don't consider this.a = 1;
    if (node.getLhs() instanceof astAtomExprNode)
      defStatus = true;
    entity res1 = (entity) (node.getLhs()).accept(this);
    defStatus = false;
    curFunc.curBlock.setVal(((register) res1).getPtr(), res2.toString(), tp((typeinfo) node.getLhs().getType()));
    add(irStore.builder()
        .tp(tp((typeinfo) node.getLhs().getType()))
        .res(res2.toString())
        .ptr(((register) res1).getPtr())
        .build());

    return register.builder()
        .name(res2.toString())
        .ptr(((register) res1).getPtr())
        .build();
  }

  /*
   * change atom load -> if value is needed load it's val a = b, b is lvalue
   * but doesn't need to load
   * block ptr is unique add in funcdef block
   * add global init a unique func,
   */

  private HashMap<String, String> global;

  private void LoadLvalue(register reg, String tp) {
    // curB ptr -> reg;
    // var res = curFunc.curBlock.findVal(reg.getPtr());
    if (reg.getPtr().charAt(0) == '@') {
      // global -> % name + .local
      if(added)
        curFunc.addGlobal(reg.getPtr(), tp);
      if(global2local) 
      // first load?
        reg.setPtr("%" + reg.getPtr().substring(1) + ".local");
    }

    if (defStatus) {
      return;
    }

    // decide? cur Func exist global variable
    var name = curFunc.curBlock.findVal(reg.getPtr());
    if (name != null) {
      reg.setName(name);
      return;
    }
    // first load
    reg.setName(curFunc.tmprename());
    curFunc.curBlock.add(irLoad.builder()
        .res(reg.getName())
        .tp(tp)
        .ptr(reg.getPtr())
        .build());
    
    curFunc.curBlock.setFirstLoad(reg.getPtr(), reg.getName());
    curFunc.curBlock.setVal(reg.getPtr(), reg.getName(), tp);
  }

  @Override
  public irNode visit(astAtomExprNode node) throws error {
    // use astAtom res to return a const or stringconst (or a funccall x)
    // register ? Lvalue = true
    /*
     * can't load value for Lvalue in atomexpr
     * when define / assign a ptr defstatus = true change ptr's reg
     * other times get ptr -> reg directly from map
     */
    if (node.isLValue()) {
      // Consider (this.)?
      String ptr = curS.getValPtr(node.getValue());
      var reg = register.builder()
          .ptr(ptr)
          .build();
      // member of class (this)? ptr re handle
      if (ptr.charAt(1) == '0' && ptr.length() > 2) {
        // this.i
        var resul1 = curFunc.tmprename();
        var gep = irGetElement.builder()
            .res(resul1)
            .tp(basetp((typeinfo) node.getType()))
            .ptrval("%this") // this -> this.addr -> this
            .tp1("i32").id1("0")
            .tp2("i32")
            .id2(ptr.substring(2))
            .build();
        reg.setPtr(resul1);
        add(gep);
        reg.setName(curFunc.tmprename());
        add(irLoad.builder()
            .res(reg.getName())
            .tp(tp((typeinfo) node.getType()))
            .ptr(reg.getPtr())
            .build());
      } else {
        // only consider lhs is directly alloca's result
        // or global varName. -> %varName.local, if global appears, it must occur here!
        // so store those occurred, delete those haven't used.
        LoadLvalue(reg, tp((typeinfo) node.getType()));
      }
      return reg;
    } else {
      if (node.getType().equals(SemanticChecker.thisType)) {
        // this -> this.addr -> this
        return register.builder()
            .name("%this")
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
            .build();
        // System.err.println(node.getValue());
        // System.err.println(node.toString());
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
    enter(new Scope(curS, null, ScopeType.BLOCK), curS.depth + 1, curS.sonN++);
    for (var stmt : node.getStmts()) {
      if (curS.findTAG())
        break;
      visit(stmt);
    }
    exit();
    return null;
  }

  @Override
  public irNode visit(astIfStmtNode node) throws error {
    // calc cond? true if.then if.else if.end whole is a stmt;

    entity cond = (entity) (node.getCond()).accept(this);

    String[] label = { "if.then" + (curS.depth + 1) + "." + curS.sonN + "." + binaryCount,
        "if.else" + (curS.depth + 1) + "." + curS.sonN + "." + binaryCount,
        "if.end" + (curS.depth + 1) + "." + curS.sonN + "." + binaryCount };
    binaryCount++;
    // scope update for num
    curS.sonN++;
    //
    irIns endBTerm = curFunc.curBlock.getEndTerm();

    irIns curBTerm = irJump.builder().dest(label[2]).build();
    // 万一没有 terminal怎么办？
    curFunc.curBlock.setTerminal(irBranch.builder()
        .cond(cond.toString())
        .iftrue(label[0])
        .iffalse(label[1])
        .build());
    var thenB = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .endTerm(curBTerm)
        .build();
    // cond block -> endBTerm;
    joinBlock(thenB, endBTerm);

    enter(new Scope(curS, null, ScopeType.BLOCK), curS.depth + 1, curS.sonN++);
    visit(node.getThenStmt());
    exit();

    var elseB = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .endTerm(curBTerm)
        .build();
    joinBlock(elseB, curBTerm);

    enter(new Scope(curS, null, ScopeType.BLOCK), curS.depth + 1, curS.sonN++);
    if (node.getElseStmt() != null) {
      visit(node.getElseStmt());
    }
    exit();

    var endB = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .endTerm(endBTerm)
        .build();
    joinBlock(endB, curBTerm);
    return null;
  }

  @Override
  public irNode visit(astForStmtNode node) throws error {
    // for.init -> curblock
    // cond upd stmts still same block; different scope for label
    enter(new Scope(curS, null, ScopeType.LOOP), curS.depth + 1, curS.sonN++);
    curS.count = loopCount++;
    if (node.getInit() != null)
      (node.getInit()).accept(this);
    String[] label = { "for.cond" + curS.depth + "." + curS.selfN + "." + curS.count,
        "for.body" + curS.depth + "." + curS.selfN + "." + curS.count,
        "for.inc" + curS.depth + "." + curS.selfN + "." + curS.count,
        "for.end" + curS.depth + "." + curS.selfN + "." + curS.count };
    curS.loopbr = label[3];
    curS.loopct = label[2];
    // calc cond to new branch -> TBD

    var cond = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .build();

    irIns curBTerm = irBranch.builder()
        .cond("TBD")
        .iftrue(label[1])
        .iffalse(label[3])
        .build();
    var endBTerm = switchBlock(cond);
    var res = node.getCond() != null ? (entity) (node.getCond()).accept(this)
        : constant.builder()
            .name("true")
            .build();
    // if (cond == curFunc.curBlock) // foolish!
    ((irBranch) curBTerm).setCond(res.toString());

    var body = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .build();

    joinBlock(body, curBTerm);

    curBTerm = irJump.builder()
        .dest(label[2])
        .build();

    // block or only a break?

    if (node.getStmt() instanceof astBlockStmtNode) {
      node.getStmt().accept(this);
    } else if (node.getStmt() != null) {
      enter(new Scope(curS, null, ScopeType.BLOCK), curS.depth + 1, curS.sonN++);
      (node.getStmt()).accept(this);
      exit();
    }
    // body end;
    var inc = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .build();
    joinBlock(inc, curBTerm);
    curBTerm = irJump.builder()
        .dest(label[0])
        .build();
    if (node.getUpdate() != null)
      visit(node.getUpdate());
    var endB = irBlock.builder()
        .label(label[3])
        .stmts(new vector<irStmt>())
        .endTerm(endBTerm)
        .build();
    joinBlock(endB, curBTerm);
    exit();
    return null;
  }

  @Override
  public irNode visit(astWhileStmtNode node) throws error {
    // same as for
    enter(new Scope(curS, null, ScopeType.LOOP), curS.depth + 1, curS.sonN++);
    curS.count = loopCount++;
    String[] label = { "while.cond" + curS.depth + "." + curS.selfN + "." + curS.count,
        "while.body" + curS.depth + "." + curS.selfN + "." + curS.count,
        "while.end" + curS.depth + "." + curS.selfN + "." + curS.count };
    curS.loopbr = label[2];
    curS.loopct = label[0];

    var cond = irBlock.builder()
        .label(label[0])
        .stmts(new vector<irStmt>())
        .build();
    var curBTerm = (irIns) (irBranch.builder()
        .cond("TBD")
        .iftrue(label[1])
        .iffalse(label[2])
        .build());
    var endBTerm = switchBlock(cond);

    var res = (entity) (node.getCond()).accept(this);
    ((irBranch) curBTerm).setCond(res.toString());

    // cond end;
    var body = irBlock.builder()
        .label(label[1])
        .stmts(new vector<irStmt>())
        .build();

    joinBlock(body, curBTerm);
    curBTerm = (irJump.builder()
        .dest(label[0])
        .build());

    if (node.getStmt() instanceof astBlockStmtNode) {
      node.getStmt().accept(this);
    } else if (node.getStmt() != null) {
      enter(new Scope(curS, null, ScopeType.BLOCK), curS.depth + 1, curS.sonN++);
      (node.getStmt()).accept(this);
      exit();
    }
    // body end;
    var endB = irBlock.builder()
        .label(label[2])
        .stmts(new vector<irStmt>())
        .endTerm(endBTerm)
        .build();
    joinBlock(endB, curBTerm);
    exit();
    return null;
  }

  @Override
  public irNode visit(astContinueStmtNode node) throws error {
    // br the for scope to for end or while end
    if (curS.findTAG())
      return null;
    curFunc.curBlock.setTerminal(irJump.builder()
        .dest(curS.getflow("ct"))
        .build());
    curS.flowTag = true;
    return null;
  }

  @Override
  public irNode visit(astBreakStmtNode node) throws error {
    // br the for scope to for end or while end
    if (!curS.findTAG()) {
      curFunc.curBlock.setTerminal(irJump.builder()
          .dest(curS.getflow("br"))
          .build());
      curS.flowTag = true;
    }
    return null;
  }

  @Override
  public irNode visit(astReturnStmtNode node) throws error {
    // br funcdef ret
    if (curS.findTAG()) {
      return null;
    }
    if (node.getExpr() != null) {
      entity res = (entity) (node.getExpr()).accept(this);
      curFunc.curBlock.setVal("%ret.val", res.toString(), tp((typeinfo) node.getExpr().getType()));
      add(irStore.builder()
          .tp(tp((typeinfo) node.getExpr().getType()))
          .ptr("%ret.val")
          .res(res.toString())
          .build());
    }
    curS.flowTag = true;
    curFunc.curBlock.setTerminal(irJump.builder()
        .dest("return" + curFunc.getId())
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
