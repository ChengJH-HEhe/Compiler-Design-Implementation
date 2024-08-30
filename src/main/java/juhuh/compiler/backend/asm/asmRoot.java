

package juhuh.compiler.backend.asm;

import juhuh.compiler.backend.asm.def.*;
import juhuh.compiler.util.vector;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
// add directly to the 
public class asmRoot extends asmNode {
  // .text、.bss、.data、.rodata
  private vector<asmFuncDef> text;
  private vector<asmVarDef> data, rodata;
  @Override 
  public String toString() {
    String s = "  .section text\n";
    for(var txt : text) {
      s += txt.toString() + "\n";
    }
    s += " .section data\n";
    for(var dat : data) {
      s += dat.toString() + "\n";
    }
    s += " .section rodata\n";
    for(var rodat : rodata) {
      s += rodat.toString() + "\n";
    }
    return s;
  }
}
