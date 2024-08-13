package frontend;

import ast.node.astNode;
import ast.node.astRoot;
import ast.node.def.*;
import ast.node.expr.*;
import ast.node.stmt.*;
import util.ClassInfo;
import util.FuncInfo;
import util.Scope;
import util.globalScope;
import util.typeinfo;
import util.error.error;

public class SymbolCollector implements astVisitor<astNode> {
  private globalScope gScope;
  private Scope curS;
  public void enter(Scope scope) {
    if(gScope == null) gScope = (globalScope)scope;
    curS = scope;
  }
  public void exit() {
    curS = curS.parentScope();
  }
  boolean checkValidType(typeinfo type) {
    return (type.isIsbuiltin() && (type.getName().equals("int")
    || type.getName().equals("bool") || type.getName().equals("string"))) 
    || gScope.getTypeFromName(type.getName()) != null;
  }
  public SymbolCollector(globalScope gScope) {
    this.gScope = gScope;
  }
  
  @Override
  public astNode visit(astNode node) throws error {
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
    public astNode visit(astRoot node) throws error {
        // program
        node.setGScope(gScope);
        enter(gScope);
        for(var def : node.getDefs()) {
            if(def instanceof astClassDefNode) {
              gScope.addType(def.getName(), 
              (ClassInfo)(((astClassDefNode)def).getInfo()));
            } else if(def instanceof astFuncDefNode) {
              gScope.defineVariable(def.getName(), 
              (((astFuncDefNode)def).getInfo()));
            }
        }
        for(var def : node.getDefs()) {
          if(def instanceof astClassDefNode || def instanceof astFuncDefNode)
            def.accept(this);
      }
      var info = gScope.containsVariable("main", false);
      if(info != null && ! (info instanceof FuncInfo)) {
        throw new error("main func not found");
      }
      exit();
      return node;
    }
  @Override
  public astNode visit(astFuncDefNode node) throws error {
    // name returntype 
    node.setFuncScope(new Scope(curS));
    enter(node.getFuncScope());
    if(node.getInfo().getName().equals("main")) {
      if(!node.getRet().equals("int"))
        throw new error("Main return " + node.getInfo().retType.getName());
      if(node.getArgs().size() > 0)
        throw new error("Main have " + node.getArgs().size() + " args expected 0");
    }
    if(!checkValidType(node.getInfo().retType) && !node.getRet().equals("void")) {
      throw new error("Return type "+ node.getRet() + "is not defined");
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
    node.setClassScope(new Scope(curS));
    enter(node.getClassScope());
    // forward reference
    // funcinfo args.type saved
    for(var func : node.getMethods()) {
      func.accept(this);
      curS.defineVariable(func.getName(), func.getInfo());
    }
    for(var vars : node.getFields()) {
      curS.defineVariable(vars.getName(), vars.getType().getInfo());
    }
    exit();
    return node;
  }

  @Override
  public astNode visit(astVarDefNode node) throws error {
    typeinfo type = node.getType().getInfo();
    if(!checkValidType(type))
      throw new error("Type " + type.getName() + " Undefined");
    curS.defineVariable(node.getName(), type);
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
  public astNode visit(astPureExprNode node) throws error {
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
