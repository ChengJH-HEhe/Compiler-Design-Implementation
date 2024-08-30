package juhuh.compiler.backend;

import juhuh.compiler.backend.asm.asmBlock;
import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.backend.asm.asmRoot;
import juhuh.compiler.backend.asm.def.*;
import juhuh.compiler.backend.asm.ins.*;
import juhuh.compiler.frontend.irVisitor;
import juhuh.compiler.ir.*;
import juhuh.compiler.ir.def.*;
import juhuh.compiler.ir.ins.*;
import juhuh.compiler.ir.stmt.*;
import juhuh.compiler.util.*;
import juhuh.compiler.util.error.error;

public class asmBuilder implements irVisitor {
  private asmRoot Rt;
  private asmFuncDef func;
  private asmBlock curB;
  private VarRegManager vrM;
  
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
  public void visit(irJump node) throws error {
    // jump label
    curB.add(riscJ.builder()
      .label(node.getDest())
      .build());
  }
  String tBool(boolean isI1) {
    return isI1? "b" : "w";
  }
  @Override
  public void visit(irRet node) throws error {
    if (!node.getTp().equals("void")) {
      // store retval to a0
      var id = vrM.add(node.getVal());
        // store register value to "a0": t3 tmpvar then mv t3,t0
      curB.add(riscL.builder()
          .op("l" + tBool(node.getTp().equals("i1")))
          .rd("t3")
          .imm(id * 4)
          .rs1("sp")
        .build());
      }
      curB.add(pseudo.builder().strs(new vector<>("ret")).build());
  }
  @Override
  public void visit(irSelect node) throws error {
    // TODO Auto-generated method stub
    
  }
  // result in 't4'
  public String getPtr(String name) {
    if(name.getBytes()[0] == '@') {
      curB.add(pseudo.builder()
        .strs(new vector<String>("la", "t4", name.substring(1)) )
        .build());
    } else {
      curB.add(riscL.builder()
          .rd("t4")
          .op("lw")
          .imm(vrM.add(name) * 4)
          .rs1("sp")
        .build());
    }
    return "t4";
  }
  
  @Override
  public void visit(irStore node) throws error {
    // TODO
    var tp = tBool(node.getTp().equals("i1"));
    var addr = getPtr(node.getPtr()); // store res into addr “t4”
    var offset = vrM.add(node.getRes()); // res starts "with” % it's ok
    // get res as t3
    curB.add(riscL.builder()
        .op("s" + tp)
        .rd("t3")
        .imm(offset)
        .rs1("sp")
      .build());
    curB.add(riscS.builder()
        .op("l" + tp)
        .rs2("t3")
        .imm(0)
        .rs1(addr)
      .build());
  }
  @Override
  public void visit(irLoad node) throws error {
    // TODO Auto-generated method stub
    var tp = tBool(node.getTp().equals("i1"));
    var addr = getPtr(node.getPtr()); // store res into addr
    var offset = vrM.add(node.getRes()); // res starts "with” % it's ok
    // get res as t3
    curB.add(riscL.builder()
      .op("l" + tp)
      .rd("t3")
      .imm(0)
      .rs1(addr)
      .build());
    curB.add(riscS.builder()
      .op("l" + tp)
      .rs2("t3")
      .imm(offset)
      .rs1("sp")
      .build());
  }
  @Override
  public void visit(irBlock node) throws error {
    var blck = asmBlock.builder()
      .label(node.getLabel())
      .nodes(new vector<asmNode>())
      .build();
    curB = blck;
    for (var ins : node.getStmts()) {
      ins.accept(this);
    }
    func.getNodes().add(blck);
  }
}
