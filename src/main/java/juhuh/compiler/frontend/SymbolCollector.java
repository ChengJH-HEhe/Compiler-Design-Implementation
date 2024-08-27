package juhuh.compiler.frontend;

import juhuh.compiler.ast.node.astNode;
import juhuh.compiler.ast.node.astRoot;
import juhuh.compiler.ast.node.def.*;
import juhuh.compiler.ast.node.expr.*;
import juhuh.compiler.ast.node.stmt.*;
import juhuh.compiler.util.Scope;
import juhuh.compiler.util.globalScope;
import juhuh.compiler.util.Scope.ScopeType;
import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.ClassInfo;
import juhuh.compiler.util.info.FuncInfo;
import juhuh.compiler.util.info.typeinfo;

public class SymbolCollector implements astVisitor<astNode> {
  private globalScope gScope;
  private Scope curS, curOrigin;
  public void enter(Scope scope, Scope copy) {
    curS = scope;
    curOrigin = copy;
  }
  public globalScope getCopy() {
    return (globalScope)curOrigin;
  }
  public void exit() {
    curS = curS.parentScope();
    curOrigin = curOrigin.parentScope();
  }
  boolean checkValidType(typeinfo type) {
    return (type.isBuiltin() && (type.getName().equals("int")
    || type.getName().equals("bool") || type.getName().equals("string"))) 
    || gScope.getTypeFromName(type.getName()) != null;
  }
  public SymbolCollector(globalScope gScope, globalScope copy) {
    this.gScope = gScope; 
    this.curOrigin = copy;
    curS = gScope;
    curOrigin = copy;
    for(var tp : SemanticChecker.builtinTypes) {
      gScope.addType(tp.getName(), tp);
      ((globalScope)curOrigin).addType(tp.getName(), tp);
      // output the tp.getName
    }
    // curOrigin = gScope.clone(null);
  }
  
  @Override
  public astNode visit(astNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
    public astNode visit(astRoot node) throws error {
        // program
        for(var def : node.getDefs()) {
            if(def instanceof astClassDefNode) {
              gScope.addType(def.getName(), 
              (ClassInfo)(((astClassDefNode)def).getInfo()));

              ((globalScope)curOrigin).addType(def.getName(), 
              (ClassInfo)(((astClassDefNode)def).getInfo()));
            } else if(def instanceof astFuncDefNode) {
              var info = ((astFuncDefNode)def).getInfo();
              if(gScope.getSafeTypeFromName(info.getName()) != null) {
                throw new error("Multiple Definitions");
              }
              gScope.defineVariable(info.getName(), info);
            }
        }
        for(var def : node.getDefs()) {
          if(def instanceof astClassDefNode || def instanceof astFuncDefNode)
            def.accept(this);
      }
      var info = gScope.containsVariable("main", false);
      if(info == null || ! (info instanceof FuncInfo)) {
        throw new error("main func not found");
      }
      exit();
      return node;
    }
  @Override
  public astNode visit(astFuncDefNode node) throws error {
    // name returntype 
    node.setFuncScope(new Scope(curS, node.getInfo(), ScopeType.FUNC));
    node.setOrigin(new Scope(curOrigin, node.getInfo(), ScopeType.FUNC));
    assert(node.getFuncScope().parentScope() != null);
    enter(node.getFuncScope(), node.getOrigin());
    if(node.getInfo().getName().equals("main")) {
      if(!node.getInfo().retType.equals(SemanticChecker.intType))
        throw new error("Type Mismatch");
      if(node.getArgs().size() > 0)
        throw new error("Undefined Identifier");
    }
    if(node.getInfo().retType != null && !checkValidType(node.getInfo().retType)) {
      throw new error("Invalid Type");
    }
    for(var arg : node.getArgs()) {
      visit(arg);
    } 
    exit();
    return node;
  }

  @Override
  public astNode visit(astClassDefNode node) throws error {
    // classname (member, fields) all together
    node.setClassScope(new Scope(curS, new typeinfo(node.getName(), 0), ScopeType.CLASS));
    node.setOrigin(new Scope(curOrigin, new typeinfo(node.getName(), 0), ScopeType.CLASS));
    enter(node.getClassScope(), node.getOrigin());
    // forward reference
    // funcinfo args.type saved
    for(var func : node.getMethods()) {
      func.accept(this);
      curS.defineVariable(func.getName(), func.getInfo());
      curOrigin.defineVariable(node.getName() + "." + func.getName(), func.getInfo());
    }
    for(var vars : node.getFields()) {
      curS.defineVariable(vars.getName(), vars.getType().getInfo());
      curOrigin.defineVariable(vars.getName(), vars.getType().getInfo());
    }
    exit();

    return node;
  }

  @Override
  public astNode visit(astVarDefNode node) throws error {
    typeinfo type = node.getType().getInfo();
    if(!checkValidType(type))
      throw new error("Invalid Type");
    curS.defineVariable(node.getName(), type);
    curOrigin.defineVariable(node.getName(), type);
    return node;
  }
  @Override
  public astNode visit(astConstrNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astNewArrayExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astCallExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astArrayExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astArrayConstExpr node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astMemberExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astFStrExpr node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astUnaryExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astPreSelfExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astBinaryExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astConditionalExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astAssignExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astAtomExprNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astBlockStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astIfStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astForStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astWhileStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astContinueStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astBreakStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }
  @Override
  public astNode visit(astReturnStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astExprStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astVarDefStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public astNode visit(astEmptyStmtNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

}
