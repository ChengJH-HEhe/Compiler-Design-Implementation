package juhuh.compiler.backend.asm.def;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmStrDef extends asmVarDef{
  String label, value;
  int length;
  @Override
  public String toString() {
    return ".p2align 2\n" + label.substring(1) + ":\n  .asciz \"" + value + "\"\n  .size " + label.substring(1) + ", " + (length) + "\n";
  }
}
