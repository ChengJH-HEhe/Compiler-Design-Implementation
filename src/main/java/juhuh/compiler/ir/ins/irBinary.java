package juhuh.compiler.ir.ins;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irBinary extends irIns{
  String res, op, tp, op1, op2;
  public String getop(String p){
    if (p.equals("Mul"))
      op = "mul";
    else if (p.equals("Div"))
      op = "sdiv";
    else if (p.equals("Mod"))
      op = "srem";
    else if (p.equals("Plus"))
      op = "add";
    else if (p.equals("Minus"))
      op = "sub";
    else if (p.equals("LeftShift"))
      op = "shl";
    else if (p.equals("RightShift"))
      op = "ashr";
    else if (p.equals("And"))
      op = "and";
    else if (p.equals("Xor"))
      op = "xor";
    else if (p.equals("Or"))
      op = "or";
    else assert(false);
    return op;
  }
  @Override
  public String toString(){
    return res + " = " + op + " " + tp + " " + op1 + " " + op2;  
  }
  @Override
  public<T> T accept(irVisitor<T> visitor) throws error{
    return visitor.visit(this);
  }
}
