package juhuh.compiler.backend.asm.def;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmGlobalVarDef extends asmVarDef {
  private String name;
  private int size;
  @Override
  public String toString() {
    return "  .globl " + name.substring(1) + "\n  .p2align 2\n" + name.substring(1) + ":\n  .word 0\n.size " + name + ", " + size + "\n";
  }
}
