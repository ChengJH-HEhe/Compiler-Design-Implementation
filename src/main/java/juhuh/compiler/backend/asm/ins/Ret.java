package juhuh.compiler.backend.asm.ins;

import juhuh.compiler.backend.asm.asmNode;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class Ret extends asmNode{
  @Override
  public String toString() {
    return "ret";
  }
}
