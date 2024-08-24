package juhuh.compiler.frontend;

import juhuh.compiler.ir.irNode;
import juhuh.compiler.ir.irRoot;
import juhuh.compiler.util.error.error;

public interface irVisitor<T> {
  // TODO VISITOR 
  public T visit(irNode node) throws error;
  public T visit(irRoot node) throws error;
  
}
