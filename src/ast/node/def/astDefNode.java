package ast.node.def;

import ast.node.astNode;
import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter

// program 3 interface: funcdef, classdef, vardef

public class astDefNode extends astNode{  
  private String name;
  @Override
  public <T> T accept(astVisitor<T> visitor) throws error {
    return visitor.visit(this);
  }
}
