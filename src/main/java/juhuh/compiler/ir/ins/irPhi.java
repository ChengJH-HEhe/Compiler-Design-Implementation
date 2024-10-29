package juhuh.compiler.ir.ins;

import java.util.HashMap;
import java.util.HashSet;

import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irPhi extends irIns {
  String res, labl, tp;
  private HashMap<String, String> label2val;
  public boolean used;

  public String getReg() {
    return res + "." + labl;
  }

  @Override
  public String toString() {
    // if(!used) return null;
    String s = getReg() + " = phi " + tp;
    String tpDefault;
    switch (tp) {
      case "i32":
        tpDefault = "0";
        break;
      case "i1":
        tpDefault = "false";
        break;
      default:
        tpDefault = "null";
    }
    for (var entry : label2val.entrySet()) {
      if (entry.getValue() == null) {
        s += " [" + tpDefault + ", %" + entry.getKey() + "],";
        continue;
      }
      s += " [ " + entry.getValue() + " , %" + entry.getKey() + "],";
    }
    return s.substring(0, s.length() - 1);
  }

  @Override
  public void accept(irVisitor visitor) throws error {
    visitor.visit(this);
  }
  @Override
  public HashSet<String> getUse() {
    throw new error("irPhi getUse");
  }
}
