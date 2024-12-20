package juhuh.compiler.ir.ins;

import java.util.Arrays;
import java.util.HashSet;

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
    else throw new UnsupportedOperationException("getOp");
    return op;
  }
  @Override
  public String toString(){
    return res + " = " + op + " " + tp + " " + op1 + ", " + op2;  
  }
  @Override
  public void accept(irVisitor visitor) throws error{
    visitor.visit(this);
  }
  @Override
  public HashSet<String> getUse() {
    return new HashSet<String>(Arrays.asList(op1, op2));
  }
  @Override
  public String getDef() {
    return res;
  }
}
