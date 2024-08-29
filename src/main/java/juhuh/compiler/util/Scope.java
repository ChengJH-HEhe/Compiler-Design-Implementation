package juhuh.compiler.util;

import juhuh.compiler.util.error.error;
import juhuh.compiler.util.info.ClassInfo;
import juhuh.compiler.util.info.FuncInfo;
import juhuh.compiler.util.info.Info;
import juhuh.compiler.util.info.typeinfo;

import java.util.HashMap;

public class Scope {
  public Info info;
  public int count;
  public boolean flowTag;
  public enum ScopeType {
    LOOP, BLOCK, FUNC, CLASS, GLOBAL;
  }

  public ScopeType type;
  public boolean isexited;

  private HashMap<String, Info> members;
  public HashMap<String, String> entities = new HashMap<>();
  private Scope parentScope;
  public String loopbr, loopct; // for while
  // scope to manage the rename job
  public int regnum = 0, depth = 0, sonN = 0, selfN = 0;
  public int getRegnum() {
    Scope tmp = this;
    while(tmp.parentScope() != null) tmp = tmp.parentScope();
    return tmp.regnum++;
  }
  
  public String getflow(String type) {
    if (this.type == ScopeType.LOOP) {
      if (type.equals("br"))
        return loopbr;
      else
        return loopct;
    } else
      return parentScope.getflow(type);
  }
  public String getValPtr(String name) {
    if (type == ScopeType.CLASS) {
      Scope tmp = this;
      while(tmp.parentScope() != null)
        tmp = tmp.parentScope();
      
      Integer id = ((ClassInfo)((globalScope)tmp).getSafeTypeFromName(this.info.getName()))
        .getVarsId().get(name);
      if(id != null) {
        // nothing previous
        return "%0" + id;
      }
    }
    if (entities.containsKey(name))
      return entities.get(name);
      // selfN may not unique add scope regname
    return parentScope.getValPtr(name);
  }
  public String setNewVarPtr(String name) {
    // make sure not in class
    String rename = (type == ScopeType.GLOBAL?"@" :"%") + name + "." + depth + "." + getRegnum();
    entities.put(name, rename);
    return rename;
  }

  public boolean findLOOP() {
    if (type == ScopeType.LOOP)
      return true;
    if (parentScope == null)
      return false;
    return parentScope.findLOOP();
  }

  public Info findFunc() {
    if (type == ScopeType.FUNC)
      return info;
    if (parentScope == null)
      return null;
    return parentScope.findFunc();
  }
  public boolean findTAG() { // FINDTAG
    if(flowTag)
      return true;
    if (parentScope == null)
      return false;
    return parentScope.findTAG();
  }
  public void setExit() {
    if (type == ScopeType.FUNC) {
      isexited = true;
      return;
    }
    parentScope.setExit();
  }

  public Info findCLASS() {
    if (type == ScopeType.CLASS)
      return info;
    if (parentScope == null)
      return null;
    return parentScope.findCLASS();
  }

  public Scope(Scope parentScope, Info info, ScopeType type) {
    members = new HashMap<>();
    this.parentScope = parentScope;
    this.info = info;
    this.type = type;
  }

  public Scope parentScope() {
    return parentScope;
  }

  public void defineVariable(String name, Info t) {
    // System.err.println("defineVariable: " + name);
    if (members.containsKey(name)) {
      if (t instanceof typeinfo)
        throw new error("Multiple Definitions");
      else {
        assert (t instanceof FuncInfo);
        throw new error("Multiple Definitions");
      }
    }
    members.put(name, t);
  }
  
  public Info containsVariable(String name, boolean lookUpon) {
    if (members.containsKey(name)) {
      Info method = members.get(name);
      if(method instanceof FuncInfo) {
        FuncInfo tmp = new FuncInfo((FuncInfo) method);
        if(type == ScopeType.CLASS) {
          tmp.setName(info.getName() + "." + name);
          System.err.print(info.getName() + " " + tmp.getName() + " " + method.getName());
        }
        return tmp;
      }  else {
        // type never change
        return method;
      }
    }
    else if (parentScope != null && lookUpon)
      return parentScope.containsVariable(name, true);
    else
      return null;
  }
}
