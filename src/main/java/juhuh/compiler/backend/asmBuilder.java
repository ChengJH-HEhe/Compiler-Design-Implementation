package juhuh.compiler.backend;

import juhuh.compiler.backend.asm.asmBlock;
import juhuh.compiler.backend.asm.asmNode;
import juhuh.compiler.backend.asm.asmRoot;
import juhuh.compiler.backend.asm.def.*;
import juhuh.compiler.backend.asm.ins.*;
import juhuh.compiler.frontend.SemanticChecker;
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
  private irFuncDef curFunc;
  private int branCount = 0;

  public asmRoot getRt() {
    return Rt;
  }

  private int chkSp(String name) {
    var res = vrM.add(name);
    if (status == true)
      return 0;
    else {
      return res;
    }
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
    for (var globl : node.getGlobalDef()) {
      globl.accept(this);
    }
    for (var func : node.getFDef()) {
      func.accept(this);
    }
  }

  private void visitFunc(irFuncDef node) {
    curFunc = node;
    visit(node.getEntry());
    // entry -= size
    for (var get : node.getBody()) {
      get.accept(this);
    }
    // ret += size;
    visit(node.getRet());
  }

  @Override
  public void visit(irFuncDef node) throws error {
    // check self arg, then brief visit first time;
    func = asmFuncDef.builder()
        .name(node.getFName())
        .nodes(new vector<asmBlock>())
        .build();
    vrM = new VarRegManager();
    // t0~t4 store into the sp + i
    status = true;
    visitFunc(node);
    status = false;
    // TODO entry storeA > 8, store T
    visitFunc(node);
    // TODO ret resetT value
    Rt.getText().add(func);
  }

  @Override
  public void visit(irGlobalDef node) throws error {
    if (status == true)
      return;
    // def node & set word size all 4 for i32 it's still the same
    Rt.getData().add(asmGlobalVarDef.builder()
        .name(node.getName())
        .size(4)
        .build());
  }

  @Override
  public void visit(irStrDef node) throws error {
    if (status == true)
      return;
    // StrConst
    Rt.getRodata().add(asmStrDef.builder()
        .label(node.getRes())
        .value(node.getInit())
        .length(node.getSize())
        .build());
  }

  @Override
  public void visit(irAlloca node) throws error {
    vrM.add(node.getRes());
    // alloca ptr only a address
  }

  public void mem2a(String name, int num, String tp) {
    if (name.getBytes()[0] != '%') {
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
  public void visit(irBinary node) throws error {
    // binary
    var res = vrM.add(node.getRes());
    if (status == true)
      return;

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
  public void visit(irBranch node) throws error {
    if (status == true)
      return;
    mem2a(node.getCond(), 0, "b");
    curB.add(pseudo.builder()
        .strs(new vector<String>("beqz", "t0", ".false" + branCount))
        .build());
    curB.add(riscJ.builder()
        .label(node.getIftrue())
        .build());
    curB.add(pseudo.builder()
        .strs(new vector<String>(".false" + branCount + ":"))
        .build());
    curB.add(riscJ.builder()
        .label(node.getIffalse())
        .build());
    ++branCount;
  }

  @Override
  public void visit(irCall node) throws error {
    if (status == true) {
      vrM.argsInc(node.getVal().size());
      if (!node.getRes().equals(""))
        chkSp(node.getRes());
      return;
    }
    curB.add(pseudo.builder()
      .strs(new vector<String>("#Call  "))
    .build());
    vrM.setCurB(curB);
    vrM.addvec(node.getVal(), node.getType());
    curB.add(pseudo.builder()
        .strs(new vector<String>("call", node.getFunc().getName()))
        .build());
    if (node.getRes().equals("")) {
      // call void
      return;
    }
    int res = chkSp(node.getRes());
    // store retval to res
    curB.add(riscS.builder()
        .op("s" + tBool(node.getFunc().getRetType().equals(SemanticChecker.boolType)))
        .rs2("a0")
        .imm(res * 4)
        .rs1("sp")
        .build());
    // store args
  }

  @Override
  public void visit(irGetElement node) throws error {
    if (status == true)
      return;
    var res = chkSp(node.getRes());
    var tmp = getPtr(node.getPtrval());
    // num or t0
    if (node.getId1().getBytes()[0] != '%')
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", "t0", node.getId1()))
          .build());
    else
      mem2a(node.getId1(), 0, "w");

    // tmp += t0; offset
    curB.add(riscR.builder()
        .op("add")
        .rd(tmp)
        .rs1("t0")
        .rs2(tmp)
        .build());
    // load ptr to t3;
    curB.add(riscL.builder()
        .op("lw")
        .rd("t3")
        .rs1(tmp)
        .imm(0)
        .build());
    // store t3 to res
    curB.add(riscS.builder()
        .op("s" + (node.getTp().equals("i1") ? "b" : "w"))
        .rs2("t3")
        .rs1("sp")
        .imm(res * 4)
        .build());
  }

  @Override
  public void visit(irIcmp node) throws error {
    //
    int res = chkSp(node.getRes());
    if (status == true)
      return;

    // "t0" "t1" -> "t2"
    mem2a(node.getOp1(), 0, tBool(node.getTp().equals("i1")));
    mem2a(node.getOp2(), 1, tBool(node.getTp().equals("i1")));
    switch (node.getOp()) {
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
        curB.add(pseudo.builder()
            .strs(new vector<String>("seqz", "t2", "t2"))
            .build());
        break;
      case "ne":
        curB.add(riscR.builder()
            .op("xor")
            .rd("t2")
            .rs1("t0")
            .rs2("t1")
            .build());
        curB.add(pseudo.builder()
            .strs(new vector<String>("snez", "t2", "t2"))
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
  public void visit(irJump node) throws error {
    if (status == true)
      return;
    // jump label
    curB.add(riscJ.builder()
        .label(node.getDest())
        .build());
  }

  String tBool(boolean isI1) {
    return isI1 ? "b" : "w";
  }

  @Override
  public void visit(irRet node) throws error {
    if (status == true)
      return;
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
    // reset T, add sp
    int sz = vrM.getSize();
    for (int i = 1; i <= 5; ++i)
      curB.add(riscL.builder()
          .op("lw")
          .rd("t" + i)
          .imm((sz - i - 1) * 4)
          .rs1("sp")
          .build());
    curB.add(riscL.builder()
        .op("lw")
        .rd("ra")
        .imm((sz-1)*4)
        .rs1("sp")
        .build());
    curB.add(riscRI.builder()
        .op("addi")
        .rd("sp")
        .imm(vrM.getSize() * 4)
        .rs1("sp")
        .build());
    curB.add(pseudo.builder()
        .strs(new vector<String>("ret   "))
        .build());
  }

  @Override
  public void visit(irSelect node) throws error {
    var res = chkSp(node.getRes());
    if (status == true)
      return;
    // beqz
    // load cond
    mem2a(node.getCond(), 0, "b");
    // branch

    curB.add(pseudo.builder()
        .strs(new vector<String>("beqz", "t0", ".false" + branCount))
        .build());
    // store a1 -> res
    mem2a(node.getVal1(), 1, tBool(node.getTp1().equals("i1")));
    curB.add(riscS.builder()
        .op("s" + tBool(node.getTp1().equals("i1")))
        .rs2("t1")
        .imm(res * 4)
        .rs1("sp")
        .build());
    curB.add(pseudo.builder()
        .strs(new vector<String>("j", ".end" + branCount))
        .build());
    curB.add(pseudo.builder()
        .strs(new vector<String>(".false" + branCount + ":"))
        .build());
    mem2a(node.getVal2(), 1, tBool(node.getTp2().equals("i1")));
    curB.add(riscS.builder()
        .op("s" + tBool(node.getTp1().equals("i1")))
        .rs2("t1")
        .imm(res * 4)
        .rs1("sp")
        .build());
    curB.add(pseudo.builder()
        .strs(new vector<String>("j", ".end" + branCount))
        .build());
    curB.add(pseudo.builder()
        .strs(new vector<String>(".end" + branCount + ":"))
        .build());
    ++branCount;
  }

  // result in 't4'
  public String getPtr(String name) {
    if (name.getBytes()[0] == '@') {
      curB.add(pseudo.builder()
          .strs(new vector<String>("la", "t4", name.substring(1)))
          .build());
    } else {
      curB.add(riscRI.builder()
          .rd("t4")
          .op("addi")
          .imm(vrM.add(name) * 4)
          .rs1("sp")
          .build());
    }
    return "t4";
  }

  @Override
  public void visit(irStore node) throws error {
    if (status == true)
      return;
    curB.add(pseudo.builder()
      .strs(new vector<String>("#Store  "))
    .build());
    int res = 114514;
    // res starts "with” % it's ok
    var tp = tBool(node.getTp().equals("i1"));
    String addr = null;
    String rd = null;
    if (node.getRes().getBytes()[0] != '%') {
      // imm
      rd = "a0";
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", rd, node.getRes()))
          .build());
    }
    if (rd == null) {
      for (int i = 0; i < Math.min(8, curFunc.getParavaluelist().size()); ++i)
        if (node.getRes().equals(curFunc.getParavaluelist().get(i))) {
          if (i < 8)
            res = -i;
          else {
            res = vrM.getArgM(i);
          }
          break;
        }
      if (res >= 0) {
        if (res == 114514)
          res = vrM.add(node.getRes());
        rd = "t3";
        curB.add(riscL.builder()
            .op("l" + tp)
            .rd(rd)
            .imm(res * 4)
            .rs1("sp")
            .build());
      } else {
        rd = "a" + (-res);
      }
    }
    if (node.getPtr().getBytes()[0] == '@') {
      // GLOBAL address

    }
    addr = getPtr(node.getPtr()); // store res into addr “t4”
    // get res as t3
    if (addr != null)
      curB.add(riscS.builder()
          .op("s" + tp)
          .rs2(rd)
          .imm(0)
          .rs1(addr)
          .build());

  }

  @Override
  public void visit(irLoad node) throws error {
    int offset = chkSp(node.getRes()); // res starts "with” % it's ok
    if (status == true)
      return;
    curB.add(pseudo.builder()
      .strs(new vector<String>("#Load  "))
    .build());
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
        .imm(offset * 4)
        .rs1("sp")
        .build());
  }

  @Override
  public void visit(irBlock node) throws error {
    asmBlock blck = asmBlock.builder()
        .label(node.getLabel())
        .nodes(new vector<asmNode>())
        .build();
    curB = blck;
    if (status == false)
      if (node.getLabel().equals("entry")) {
        // entry storeT, addi to sp
        blck.setLabel(func.getName());
        vrM.setCurB(curB);
        vrM.store(); // size currect
        curB.add(riscRI.builder()
            .op("addi")
            .rd("sp")
            .imm(-(vrM.getSize() / 4 * 16))
            .rs1("sp")
            .build());
      }
    if (node.getStmts() != null)
      for (irStmt ins : node.getStmts()) {
        ins.accept(this);
      }
    if (node.getTerminalstmt() != null)
      node.getTerminalstmt().accept(this);
    else
      node.getEndTerm().accept(this);
    if (status == false)
      func.getNodes().add(blck);
  }
}
