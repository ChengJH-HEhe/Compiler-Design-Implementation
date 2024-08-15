package juhuh.compiler.util;

import juhuh.compiler.mir.register;
import juhuh.compiler.util.error.semanticError;
import java.util.HashMap;

public class Scope {
  protected Info info;
  public enum ScopeType {
    LOOP, BLOCK, FUNC, CLASS, GLOBAL;
  }
  protected ScopeType type;
  public boolean isexited;

  private HashMap<String, Info> members;
  public HashMap<String, register> entities = new HashMap<>();
  private Scope parentScope;
  public boolean findLOOP(){
    if(type == ScopeType.LOOP)
      return true;
    if(parentScope == null)
      return false;
    return parentScope.findLOOP();
  }
  public Info findFunc(){
    if(type == ScopeType.FUNC)
      return info;
    if(parentScope == null)
      return null;
    return parentScope.findFunc();
  }
  public Info findCLASS(){
    if(type == ScopeType.CLASS)
      return info;
    if(parentScope == null)
      return null;
    return parentScope.findCLASS();
  }
  public Scope(Scope parentScope,Info info, ScopeType type) {
    members = new HashMap<>();
    this.parentScope = parentScope;
    this.info = info;
    this.type = type;
  }

  public Scope parentScope() {
    return parentScope;
  }

  public void defineVariable(String name, Info t) {
        if (members.containsKey(name)){
            if(t instanceof typeinfo)
                throw new semanticError("Semantic Error: variable" + name +" redefine");
            else {
              assert(t instanceof FuncInfo);
              throw new semanticError("Semantic Error: function" + name +" redefine");
            }
        }
        members.put(name, t);
    }

  public Info containsVariable(String name, boolean lookUpon) {
    if (members.containsKey(name))
      return members.get(name);
    else if (parentScope != null && lookUpon)
      return parentScope.containsVariable(name, true);
    else
      return null;
  }

  public register getEntity(String name, boolean lookUpon) {
    if (entities.containsKey(name))
      return entities.get(name);
    else if (parentScope != null && lookUpon)
      return parentScope.getEntity(name, true);
    return null;
  }
}
