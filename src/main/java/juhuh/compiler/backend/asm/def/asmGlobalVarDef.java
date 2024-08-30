package juhuh.compiler.backend.asm.def;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class asmGlobalVarDef extends asmVarDef {
  private String name;
  private int size;
  @Override
  public String toString() {
    return "  .globl " + name + "\n" + name + ":\n  .word " + size + "\n";
  }
}
