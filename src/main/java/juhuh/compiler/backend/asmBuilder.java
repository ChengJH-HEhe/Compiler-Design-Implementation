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
  private boolean status;
  public asmRoot getRt() {
    return Rt;
  }
  
  @Override
  public void visit(irNode node) throws error { 
    node.accept(this);
  }
  
  @Override
  public void visit(irRoot node) throws error { 
    // visit Program
    asmRoot rt = asmRoot.builder()
      .text(new vector<asmFuncDef>())
      .data(new vector<asmVarDef>())
      .rodata(new vector<asmVarDef>())
      .build();
    Rt = rt;
    for (var globl: node.getGlobalDef()) {
      globl.accept(this);
    }
    for (var func: node.getFDef()) {
      func.accept(this);
    }
  }
  private void visitFunc(irFuncDef node) {
    visit(node.getEntry());
    for(var get : node.getBody()) {
      get.accept(this);
    }
    visit(node.getRet());
  }
  @Override
  public void visit(irFuncDef node) throws error { 
    // check self arg, then brief visit first time;
    vrM = new VarRegManager();
    vrM.argsInc(node.getParatypelist().size());
    // t0~t4 store into the sp + i
    vrM.storeT();
    status = true;
    visitFunc(node);
    status = false;
    visitFunc(node);
    // and then visit second time to get the code 
  }
  
  @Override
  public void visit(irGlobalDef node) throws error { if(status == true) return;
    //  def node & set word size all 4 for i32 it's still the same
    Rt.getData().add(asmGlobalVarDef.builder()
      .name(node.getName())
      .size(4)
      .build());
  }
  
  @Override
  public void visit(irStrDef node) throws error { if(status == true) return;
    // StrConst
    Rt.getRodata().add(asmStrDef.builder()
      .label(node.getRes())
      .value(node.getInit())
      .length(node.getSize())
      .build());
  }
  
  @Override
  public void visit(irAlloca node) throws error { if(status == true) return;
    int offset = vrM.add(node.getRes());
    // alloca ptr only a address
    if(offset == 0) {return;}
  }
  
  public void mem2a(String name, int num, String tp) {
    if(name.getBytes()[0] != '%') {
      curB.add(pseudo.builder()
      .strs(new vector<String>("li", "t" + num, name))
      .build());
    } else {
      int op1 = vrM.add(name);
      curB.add(riscL.builder()
      .op("l" + tp)
      .rd("t" + num)
      .imm(op1 * 4)
      .rs1("sp")
      .build());
    }
  }

  @Override
  public void visit(irBinary node) throws error { if(status == true) return;
    // binary
    var res = vrM.add(node.getRes());
    if(res == -1) {
      return;
    }
    String tp = tBool(node.getTp().equals("i1"));
    mem2a(node.getOp1(), 0, tp);
    mem2a(node.getOp2(), 1, tp);
    // calc t0 t1 to t2
    curB.add(riscR.builder()
        .op(node.getOp())
        .rd("t2")
        .rs1("t0")
        .rs2("t1")
      .build());
    // store to res
    curB.add(riscS.builder()
        .op("s" + tp)
        .rs2("t2")
        .imm(res * 4)
        .rs1("sp")
      .build());
  }
  
  @Override
  public void visit(irBranch node) throws error { if(status == true) return;
    // TODO 
  }
  
  @Override
  public void visit(irCall node) throws error { if(status == true) return;
    // TODO 
  }
  
  @Override
  public void visit(irGetElement node) throws error { if(status == true) return;
    // TODO 
  }
  
  @Override
  public void visit(irIcmp node) throws error { if(status == true) return;
    //  
    int res = vrM.add(node.getRes());
    if(res == -1) return;
    // "t0" "t1" -> "t2"
    mem2a(node.getOp1(), 0, tBool(node.getTp().equals("i1")));
    mem2a(node.getOp2(), 1, tBool(node.getTp().equals("i1")));
    switch(node.getOp()) {
      case "sgt":
        curB.add(riscR.builder()
          .op("slt")
          .rd("t2")
          .rs1("t1")
          .rs2("t0")
          .build());
        break;
      case "slt":
        curB.add(riscR.builder()
          .op("slt")
          .rd("t2")
          .rs1("t0")
          .rs2("t1")
          .build());
        break;
      case "sge":
        curB.add(riscR.builder()
          .op("slt")
          .rd("t2")
          .rs1("t0")
          .rs2("t1")
          .build());
        curB.add(riscRI.builder()
          .op("xori")
          .rd("t2")
          .rs1("t2")
          .imm(1)
          .build());
        break;  
      case "sle":
        curB.add(riscR.builder()
          .op("slt")
          .rd("t2")
          .rs1("t1")
          .rs2("t0")
          .build());
        curB.add(riscRI.builder()
          .op("xori")
          .rd("t2")
          .rs1("t2")
          .imm(1)
          .build());
        break;
      case "eq":
        curB.add(riscR.builder()
          .op("xor")
          .rd("t2")
          .rs1("t0")
          .rs2("t1")
          .build());
        curB.add(riscRI.builder()
          .op("seqz")
          .rd("t2")
          .rs1("t2")
          .build());
        break;
      case "ne":
        curB.add(riscR.builder()
          .op("xor")
          .rd("t2")
          .rs1("t0")
          .rs2("t1")
          .build());
        curB.add(riscRI.builder()
          .op("snez")
          .rd("t2")
          .rs1("t2")
          .build());
        break;
    }
    
    // store to res
    curB.add(riscS.builder()
      .op("s" + tBool(node.getTp().equals("i1")))
      .rs2("t2")
      .imm(res * 4)
      .rs1("sp")
      .build());
  }
  
  @Override
  public void visit(irJump node) throws error { if(status == true) return;
    // jump label
    curB.add(riscJ.builder()
      .label(node.getDest())
      .build());
  }
  String tBool(boolean isI1) {
    return isI1? "b" : "w";
  }
  @Override
  public void visit(irRet node) throws error { if(status == true) return;
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
      curB.add(pseudo.builder().strs(new vector<String>("mv", "t3", "a0")).build());
      }

      curB.add(pseudo.builder().strs(new vector<>("ret")).build());
  }
  @Override
  public void visit(irSelect node) throws error { if(status == true) return;
    // TODO beqz  
// load cond

// branch

//

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
  public void visit(irStore node) throws error { if(status == true) return;
    int offset = vrM.add(node.getRes()); // res starts "with” % it's ok
    if(offset == 0) {return;}
    var tp = tBool(node.getTp().equals("i1"));
    var addr = getPtr(node.getPtr()); // store res into addr “t4”
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
  public void visit(irLoad node) throws error { if(status == true) return;
    int offset = vrM.add(node.getRes()); // res starts "with” % it's ok
    if(offset == 0) {return;}
    var tp = tBool(node.getTp().equals("i1"));
    var addr = getPtr(node.getPtr()); // store res into addr
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
  public void visit(irBlock node) throws error { if(status == true) return;
    asmBlock blck = asmBlock.builder()
      .label(node.getLabel())
      .nodes(new vector<asmNode>())
      .build();
    curB = blck;
    for (irStmt ins : node.getStmts()) {
      ins.accept(this);
    }
    func.getNodes().add(blck);
  }
}
