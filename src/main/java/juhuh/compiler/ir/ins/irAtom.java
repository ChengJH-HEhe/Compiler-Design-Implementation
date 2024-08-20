package juhuh.compiler.ir.ins;

import juhuh.compiler.ir.entity.entity;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class irAtom extends irIns{
  entity val;
}
