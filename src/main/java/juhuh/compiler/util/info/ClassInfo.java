package juhuh.compiler.util.info;

import java.util.HashMap;

import juhuh.compiler.ast.node.def.astFuncDefNode;
import juhuh.compiler.ast.node.def.astVarDefNode;
import juhuh.compiler.ast.node.stmt.astConstrNode;
import juhuh.compiler.frontend.SemanticChecker;
import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ClassInfo extends Info {
  public HashMap<String, typeinfo> vars;
  public HashMap<String, Integer> varsId;
  private int size;
  public vector<FuncInfo> func;
  public HashMap<String, FuncInfo> funcs;
  public ClassInfo(String name, vector<astVarDefNode> vars, vector<astFuncDefNode> funcs, astConstrNode cstr) {
    super(name);
    this.vars = new HashMap<String, typeinfo>();
    this.varsId = new HashMap<String, Integer>();
    this.funcs = new HashMap<String, FuncInfo>();
    int num = 0;
    for (var v : vars) {
      this.varsId.put(v.getName(), num++);
      this.vars.put(v.getName(), v.getType().getInfo());
    }
    for (var func : funcs) {
      this.funcs.put(func.getName(), (FuncInfo) func.getInfo());
    }
    // construct for origin
    if(cstr != null) {
      this.funcs.put(name, new FuncInfo(name + "." + name, SemanticChecker.voidType));
    }
    size = vars.size(); // size*4
  }
  public ClassInfo(String name, vector<FuncInfo> funcs) {
    super(name);
    this.vars = new HashMap<String, typeinfo>();
    this.funcs = new HashMap<String, FuncInfo>();
    for (var func : funcs) {
      this.funcs.put(func.getName(), (FuncInfo) func);
    }
  }
}
