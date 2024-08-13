package util;

import java.util.HashMap;

import ast.node.def.astFuncDefNode;
import ast.node.def.astVarDefNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ClassInfo extends Info {
  public HashMap<String, typeinfo> vars;
  public HashMap<String, FuncInfo> funcs;
  public ClassInfo(String name, vector<astVarDefNode> vars, vector<astFuncDefNode> funcs) {
    super(name);
    this.vars = new HashMap<String, typeinfo>();
    this.funcs = new HashMap<String, FuncInfo>();
    for (var v : vars) {
      this.vars.put(v.getName(), (typeinfo) v.getType().getInfo());
    }
    for (var func : funcs) {
      this.funcs.put(func.getName(), (FuncInfo) func.getInfo());
    }
  }
}
