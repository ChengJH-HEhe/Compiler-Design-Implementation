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
import juhuh.compiler.util.info.typeinfo;

public class irBuilder implements astVisitor<irNode> {
  private Scope curS;
  private irBlock curdef, curBlock;
  private irFuncDef curFunc;
  private globalScope gScope;
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
  }

  String tp(typeinfo tp) {
    if (tp.equals(SemanticChecker.intType)) {
      return "i32";
    } else if (tp.equals(SemanticChecker.boolType)) {
      return "i1";
    } else
      return "ptr";
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
        curBlock = globalInit.getEntry();
        irGlobalDef globalvar = null;
        if (!((astVarDefNode) def).getType().getInfo().equals(SemanticChecker.intType)
            && !((astVarDefNode) def).getType().getInfo().equals(SemanticChecker.boolType)) {
          // def %class.typename
          globalvar = (irGlobalDef.builder()
              .name("@" + ((astVarDefNode) def).getName())
              .type("%class." + ((astVarDefNode) def).getType().getInfo().getName())
              .init("zeroinitializer")
              .build());
        } else {
          globalvar = irGlobalDef.builder()
              .name(((astVarDefNode) def).getName())
              .type(tp(((astVarDefNode) def).getType().getInfo()))
              .init("0")
              .build();
        }
        if (((astVarDefNode) def).getUnit() != null) {
          var init = visit(((astVarDefNode) def).getUnit());
          if (init instanceof constant) {
            globalvar.setInit(((constant) init).toString());
          } else {
            // need to initialize with an init not constant;

            var exprinit = (register) init;
            curBlock.add(irStore.builder()
                .tp(globalvar.getType())
                .res(exprinit.getName())
                .ptr("@" + globalvar.getName())
                .build());
          }
          rt.add(globalvar);
          mainNode = def;
        } else {
          def.accept(this);
        }
      }
    }
    main = (irFuncDef) mainNode.accept(this);
    // main init global
    main.getEntry().add(irCall.builder()
        .res("")
        .retType("void")
        .func(globalInit.getFName())
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
            .terminalstmt(irRet.builder().tp(tp(node.getInfo().getRetType())).val("%0ret").build())
            .build())
        .body(new vector<irBlock>())
        .build();
    // TODO reminder curDef to be the FuncDef entry block, entry br to the first
    // block
    // curBlock to be the curBlock
    // TODO different label jump
    curdef = func.getEntry();
    curBlock = curdef;
    enter(node.getFuncScope(), 1, curS.sonN++);

    if (!node.getRet().equals("void")) {
      curdef.add(irAlloca.builder()
          .res("%0ret")
          .tp(tp(node.getInfo().getRetType()))
          .build());
    }
    curS = node.getFuncScope();
    // add this for class function
    if (curS.parentScope().type != Scope.ScopeType.GLOBAL) {
      func.getParatypelist().add("ptr");
      func.getParavaluelist().add("%this");
      curdef.add(irAlloca.builder()
          .res("%this.addr")
          .tp("ptr")
          .build());
      curdef.add(irStore.builder()
          .tp("ptr")
          .res("%this")
          .ptr("%this.addr")
          .build());
      func.setFName("@" + curS.parentScope().info.getName());
    }
    for (var para : node.getArgs()) {
      func.getParatypelist().add(tp(para.getType().getInfo()));
      func.getParavaluelist().add(para.getName());
      curdef.add(irAlloca.builder()
          .res("%" + para.getName() + ".addr")
          .tp(tp(para.getType().getInfo()))
          .build());
      curdef.add(irStore.builder()
          .tp(tp(para.getType().getInfo()))
          .res("%" + para.getName())
          .ptr("%" + para.getName() + ".addr")
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
    String tmpname = "%" + curS.Varrename(node.getName());

    // if class
    if (!node.getType().getInfo().equals(SemanticChecker.intType)
        && !node.getType().getInfo().equals(SemanticChecker.boolType)) {
      // def %class.typename
      curdef.add(irAlloca.builder()
          .res(tmpname)
          .tp("%class." + node.getType().getInfo().getName())
          .build());
    } else {
      curdef.add(irAlloca.builder()
          .res(tmpname)
          .tp(tp(node.getType().getInfo()))
          .build());
    }
    if (node.getUnit() != null) {
      var init = visit(node.getUnit());
      if (init instanceof constant) {
        curBlock.add(irStore.builder()
            .tp(tp(node.getType().getInfo()))
            .res(((constant) init).toString())
            .ptr(tmpname)
            .build());
      } else {
        // need to initialize with an init not constant;
        // guarantee that result is a register
        var exprinit = (register) init;
        curBlock.add(irStore.builder()
            .tp(tp(node.getType().getInfo()))
            .res(exprinit.getName())
            .ptr(tmpname)
            .build());
      }
    }
    return null;
  }

  @Override
  public irNode visit(astConstrNode node) throws error {
    // TODO a::a(ptr this) another funcdef
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astNewArrayExprNode node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astCallExprNode node) throws error {
    // TODO call func manage expr add to curBlock
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astArrayExprNode node) throws error {
    // TODO return ptr using elementptr
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astArrayConstExpr node) throws error {
    // TODO what?
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astMemberExprNode node) throws error {
    // TODO Lvalue -> ptr not only value
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
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
      // TODO get label name
      String target = node.getOp().equals("LogicAnd") ? "1" : "0";
      // not equal target -> end
      String resul = curFunc.tmprename();
      entity res1 = (entity) visit(node.getLhs()),
          res2 = (entity) visit(node.getRhs());
      curBlock.add(irBinary.builder()
          .op("eq")
          .res(resul)
          .tp("i1")
          .op1(((register) res1).getName())
          .op2("0")
          .build());
      if (node.getOp().equals("LogicAnd")) {
        curBlock.add(irCond.builder()
            .op("br")
            .cond(resul)
            .trueDest(label1)
            .falseDest(label2)
            .build());
        curBlock.add(irBlock.builder()
            .label(label1)
            .stmts(new vector<irStmt>())
            .build());
        curBlock.add(irBinary.builder()
            .op("ne")
            .res(resul)
            .tp("i1")
            .op1(((register) res2).getName())
            .op2("0")
            .build());
        curBlock.add(irJump.builder()
            .dest(label2)
            .build());
        curBlock.add(irBlock.builder()
            .label(label2)
            .stmts(new vector<irStmt>())
            .build());
      } else {
        curBlock.add(irCond.builder()
            .op("br")
            .cond(resul)
            .trueDest(label2)
            .falseDest(label1)
            .build());
        curBlock.add(irBlock.builder()
            .label(label1)
            .stmts(new vector<irStmt>())
            .build());
        curBlock.add(irBinary.builder()
            .op("ne")
            .res(resul)
            .tp("i1")
            .op1(((register) res2).getName())
            .op2("0")
            .build());
        curBlock.add(irJump.builder()
            .dest(label2)
            .build());
        curBlock.add(irBlock.builder()
            .label(label2)
            .stmts(new vector<irStmt>())
            .build());
      }
      
    } else {
      String Optp = irIcmp.builder().build().getop(node.getOp());
      if (Optp != null) {
        entity res1 = (entity) visit(node.getLhs()),
            res2 = (entity) visit(node.getRhs());
        String resul = curFunc.tmprename();
        curBlock.add(irIcmp.builder()
            .op(Optp)
            .res(resul)
            .tp(((register) res1).getTp())
            .op1(((register) res1).getName())
            .op2(((register) res2).getName())
            .build());
        return register.builder()
            .name(resul)
            .build();
      } else {
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
    // TODO select cmd
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astAssignExprNode node) throws error {
    // TODO calc lhs.ptr, calc rhs.val , store to lhs.ptr
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astAtomExprNode node) throws error {
    // TODO use astAtom res to return a const or stringconst or a funccall
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astBlockStmtNode node) throws error {
    // TODO simple block; upd scope depth
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astIfStmtNode node) throws error {
    // TODO calc cond? true if.then or if.end whole is a stmt; scope update for num
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astForStmtNode node) throws error {
    // TODO for.init cond upd stmts still same block; different scope for label
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astWhileStmtNode node) throws error {
    // TODO same as for
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astContinueStmtNode node) throws error {
    // TODO br the for scope to for end or while end
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astBreakStmtNode node) throws error {
    // TODO br the for scope to for end or while end
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public irNode visit(astReturnStmtNode node) throws error {
    // TODO br funcdef ret
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
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
