package util;

import mir.register;
import util.error.semanticError;
import java.util.HashMap;

public class Scope {

  private HashMap<String, Info> members;
  public HashMap<String, register> entities = new HashMap<>();
  private Scope parentScope;

  public Scope(Scope parentScope) {
    members = new HashMap<>();
    this.parentScope = parentScope;
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
