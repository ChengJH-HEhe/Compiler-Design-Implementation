package juhuh.compiler.backend;

import juhuh.compiler.backend.asm.asmBlock;
import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.backend.asm.asmRoot;
import juhuh.compiler.backend.asm.def.asmFuncDef;
import juhuh.compiler.backend.asm.def.asmGlobalVarDef;
import juhuh.compiler.backend.asm.def.asmStrDef;
import juhuh.compiler.backend.asm.def.asmVarDef;
import juhuh.compiler.backend.asm.ins.Ret;
import juhuh.compiler.backend.asm.ins.riscJ;
import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.irNode;
import juhuh.compiler.ir.irRoot;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.ir.ins.*;
import juhuh.compiler.ir.stmt.*;
import juhuh.compiler.util.*;
import juhuh.compiler.util.error.error;

public class asmBuilder implements irVisitor {
  private asmRoot Rt;
  private asmFuncDef func;
  private asmBlock curB;
  public asmRoot getRt() {
    return Rt;
  }
  @Override
  public void visit(irNode node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irRoot node) throws error {
    // TODO visit Program
    asmRoot rt = asmRoot.builder()
    .text(new vector<asmFuncDef>())
    .data(new vector<asmVarDef>())
    .rodata(new vector<asmVarDef>())
    .build();
    Rt = rt;
  }

  @Override
  public void visit(irFuncDef node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }
  @Override
  public void visit(irGlobalDef node) throws error {
    //  def node & set word size all 4 for i32 it's still the same
    Rt.getData().add(asmGlobalVarDef.builder()
    .name(node.getName())
    .size(4)
    .build());
  }

  @Override
  public void visit(irStrDef node) throws error {
    // StrConst
    Rt.getRodata().add(asmStrDef.builder()
    .label(node.getRes())
    .value(node.getInit())
    .length(node.getSize())
    .build());
  }

  @Override
  public void visit(irAlloca node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irBinary node) throws error {
    // TODO 

    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irBranch node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irCall node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irGetElement node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irIcmp node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irLoad node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irJump node) throws error {
    // jump label
    curB.add(riscJ.builder()
    .label(node.getDest())
    .build());
  }

  @Override
  public void visit(irRet node) throws error {
    if(!node.getTp().equals("void")) {
      // TODO store retval

    }
    curB.add(Ret.builder().build());
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }
  @Override
  public void visit(irSelect node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irStore node) throws error {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'visit'");
  }

  @Override
  public void visit(irBlock node) throws error {
    var blck = asmBlock.builder()
    .label(node.getLabel())
    .nodes(new vector<asmNode>())
    .build();
    curB = blck;
    for(var ins : node.getStmts()) {
      ins.accept(this);
    }
    func.getNodes().add(blck);
  }
}
