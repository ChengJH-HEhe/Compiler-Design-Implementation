package juhuh.compiler.ir;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.util.vector;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class irRoot extends irNode {
  vector<irStructDef> structDef;
  vector<irGlobalDef> globalDef;
  vector<irFuncDecl> globalFDecl;
  vector<irFuncDef> FDef;
  public void add(irGlobalDef def){
    globalDef.add(def);
  }
  public void add(irStructDef def){
    structDef.add(def);
  }
  public void add(irFuncDecl decl){
    globalFDecl.add(decl);
  }
  public void add(irFuncDef def){
    FDef.add(def);
  }
  public String toString(){
    String s = "";
    for(var def : globalDef){
      s += def.toString() + "\n";
    }
    for(var def : structDef){
      s += def.toString() + "\n";
    }
    for(var decl : globalFDecl){
      s += decl.toString() + "\n";
    }
    for(var def : FDef){
      s += def.toString() + "\n";
    }
    return s;
  }
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
