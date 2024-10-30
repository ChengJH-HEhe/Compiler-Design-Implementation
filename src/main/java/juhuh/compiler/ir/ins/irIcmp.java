package juhuh.compiler.ir.ins;

import java.util.Arrays;
import java.util.HashSet;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irIcmp extends irIns {
  String res, op, op1, op2, tp;

  public static String getop(String p) {
    String op= null;
    if (p.equals("Greater"))
      op = "sgt";
    else if (p.equals("GreaterEqual"))
      op = "sge";
    else if (p.equals("Less"))
      op = "slt";
    else if (p.equals("LessEqual"))
      op = "sle";
    else if (p.equals("Equal"))
      op = "eq";
    else if (p.equals("UnEqual"))
      op = "ne";
    else
      op = null;
    return op;
  }

  @Override
  public String toString() {
    return res + " = icmp " + op + " " + tp + " " + op1 + ", " + op2;
  }

  
  @Override
  public void accept(irVisitor visitor) throws error {
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
