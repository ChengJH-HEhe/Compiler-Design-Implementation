package juhuh.compiler.backend.asm.def;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmStrDef extends asmVarDef{
  String label, value;
  int length;
  @Override
  public String toString() {
    return label + ":\n  .asciz \"" + value + "\"\n  .size " + label.substring(1) + ", " + length + "\n";
  }
}
