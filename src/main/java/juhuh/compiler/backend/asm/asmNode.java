package juhuh.compiler.backend.asm;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public abstract class asmNode {
  public abstract String toString();
}
