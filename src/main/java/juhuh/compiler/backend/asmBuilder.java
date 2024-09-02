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

  private void global2ValReg(String label, String reg, String type) {
    curB.add(pseudo.builder()
        .strs(new vector<String>("la", reg, label))
        .build());
    if(label.charAt(0) != '.')
    curB.add(riscL.builder()
        .op(type)
        .rd(reg)
        .rs1(reg)
        .imm(0)
        .build());
  }

  private void offset2PtrPtrReg(int offset, String reg) {
    curB.add(riscL.builder()
        .op("lw")
        .rd(reg)
        .rs1("sp")
        .imm(offset)
        .build());
  }

  private void offset2Val(int offset, String reg, String type) {
    curB.add(riscL.builder()
        .op(type)
        .rd(reg)
        .rs1("sp")
        .imm(offset)
        .build());
  }

  private void ptrPtr2ValReg(String result, String reg, String type) {
    curB.add(riscL.builder()
        .op(type)
        .rd(result)
        .rs1(reg)
        .imm(0)
        .build());
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
    int res = vrM.add(node.getRes());
    vrM.add(node.getRes() + ".RESULT");
    if (status == false) {
      curB.adS("t0", "sp", res * 4 + 4);
      curB.add(riscS.builder()
          .op("sw")
          .rs2("t0")
          .imm(res * 4)
          .rs1("sp")
          .build());
    }
    // alloca ptr only a address

  }

  // for usual anonymous variable
  public void mem2a(String name, int num, String tp) {
    if (name.getBytes()[0] != '%' && name.getBytes()[0] != '@') {
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", "t" + num, name.equals("false") ? "0" : name.equals("true") ? "1" :
           name.equals("null")?"0":name))
          .build());
    } else if (name.charAt(0) == '%') {
      int op1 = vrM.add(name);
      offset2Val(op1 * 4, "t" + num, "l" + tp);
    } else {
      global2ValReg(name.substring(1), "t" + num, "l" + tp);
    }
  }

  public void mem2aS(String name, String num, String tp) {
    if (name.getBytes()[0] != '%' && name.getBytes()[0] != '@') {
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", "t" + num, name.equals("false") ? "0" : name.equals("true") ? "1" :
           name.equals("null")?"0":name))
          .build());
    } else if (name.charAt(0) == '%') {
      int op1 = vrM.add(name);
      offset2Val(op1 * 4, num, "l" + tp);
    } else {
      // la + mv
      curB.add(pseudo.builder()
          .strs(new vector<String>("la", num, name.substring(1)))
          .build());
    }
  }

  // res = valPtr
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
    if (node.getOp().equals("sdiv") || node.getOp().equals("srem")) {
      curB.add(riscR.builder()
          .op(node.getOp().substring(1))
          .rd("t2")
          .rs1("t0")
          .rs2("t1")
          .build());
    } else {
      var cmd = riscR.builder()
          .op(node.getOp())
          .rd("t2")
          .rs1("t0")
          .rs2("t1")
          .build();
      if (node.getOp().equals("shl")) {
        cmd.setOp("sll");
      } else if (node.getOp().equals("ashr")) {
        cmd.setOp("sra");
      }
      curB.add(cmd);
    }
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
    // BUG:a0~a7 preserved by call node
    curB.add(pseudo.builder()
        .strs(new vector<String>("#Call  "))
        .build());
    vrM.setCurB(curB);
    // maxargs , maxargs + 7
    // for(int i = 0; i < 8; ++i) {
    // curB.add(riscS.builder()
    // .op("sw")
    // .rs2("a" + i)
    // .imm((vrM.getMaxargs() + i) * 4)
    // .rs1("sp")
    // .build());
    // }
    var varArr = node.getVal();
    var vartype = node.getType();
    for (int curId = 0; curId < varArr.size(); ++curId) {
      var arg = varArr.get(curId);
      curB.add(pseudo.builder()
          .strs(new vector<String>("# " + arg))
          .build());
      String tmpvar = "t3";
      if (curId < 8)
        tmpvar = "a" + curId;
      // load to the tmpvar
      if (arg.getBytes()[0] == '%' || arg.getBytes()[0] == '@') {
        mem2aS(arg, tmpvar, vartype.get(curId).equals("i1") ? "b" : "w");
      } else {
        curB.add(pseudo.builder()
            .strs(new vector<String>("li", tmpvar, arg.equals("false") ? "0" : arg.equals("true") ? "1" : 
            arg.equals("null")?"0":arg))
            .build());
      }
      if (curId >= 8) {
        // store tmpvar to sp + (curId - 8) * 4
        // "t3" store the val
        curB.add(riscS.builder()
            .op(vartype.get(curId).equals("i1") ? "sb" : "sw")
            .rs2(tmpvar)
            .imm((curId - 8) * 4)
            .rs1("sp")
            .build());
      }
    }
    curB.add(pseudo.builder()
        .strs(new vector<String>("call", node.getFunc().getName()))
        .build());
    if (!node.getRes().equals("") && !node.getRes().equals("void")) {
      // call void
      int res = chkSp(node.getRes());
      // store retval to res
      curB.add(riscS.builder()
          .op("s" + tBool(node.getFunc().getRetType().equals(SemanticChecker.boolType)))
          .rs2("a0")
          .imm(res * 4)
          .rs1("sp")
          .build());
    }
    // for(int i = 0; i < 8; ++i) {
    // curB.add(riscL.builder()
    // .op("lw")
    // .rd("a" + i)
    // .imm((vrM.getMaxargs() + i) * 4)
    // .rs1("sp")
    // .build());
    // }

    // store args
  }

  @Override
  public void visit(irGetElement node) throws error {
    var res = chkSp(node.getRes());
    if (status == true)
      return;
    // should change name -> id
    curB.add(pseudo.builder()
        .strs(new vector<String>("#getElement"))
        .build());
    var tmp = getPtr(node.getPtrval()); // 存放地址寄存器

    // t0
    if (node.getId1().getBytes()[0] != '%')
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", "t0", node.getId1()))
          .build());
    else
      mem2a(node.getId1(), 0, "w");
    // t0 <<= 2; offset
    curB.add(riscRI.builder()
        .op("slli")
        .rd("t0")
        .rs1("t0")
        .imm(2)
        .build());

    // tmp += t0
    curB.add(riscR.builder()
        .op("add")
        .rd("t3")
        .rs1(tmp)
        .rs2("t0")
        .build());
    // load ptr to t3;
    // store t3 to res
    curB.add(riscS.builder()
        .op("sw")
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
        .op("sb")
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
      // %ret.val -> addr -> a0
      var id = vrM.add(node.getVal());
      // store register value to "a0":
      curB.add(riscL.builder()
          .op("l" + tBool(node.getTp().equals("i1")))
          .rd("a0")
          .imm(id * 4)
          .rs1("sp")
          .build());
    }
    // reset T, add sp
    int sz = vrM.getSize();

    curB.add(riscL.builder()
        .op("lw")
        .rd("ra")
        .imm((sz - 1) * 4)
        .rs1("sp")
        .build());

    // for (int i = 1; i <= 5; ++i) {
    //   curB.add(riscL.builder()
    //       .op("lw")
    //       .rd("t" + i)
    //       .imm((sz - i - 1) * 4)
    //       .rs1("sp")
    //       .build());
    // }

    curB.adS("sp", "sp", vrM.getSize() * 4);
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
  // precisely get the ptr -> the real address
  public String getPtr(String name) {

    // problem is global?
    if (name.getBytes()[0] == '@') {
      curB.add(pseudo.builder()
          .strs(new vector<String>("la", "t4", name.substring(1)))
          .build());
    } else {
      //
      curB.add(riscL.builder()
          .op("lw")
          .rd("t4")
          .imm(vrM.add(name) * 4)
          .rs1("sp")
          .build());
      // t4
    }
    return "t4";
  }

  @Override
  public void visit(irStore node) throws error {
    // store rs2.val to rs1.addr
    if (status == true)
      return;
    curB.add(pseudo.builder()
        .strs(new vector<String>("#Store  "))
        .build());
    int res = 114514;
    // res starts "with” % it's ok
    var tp = tBool(node.getTp().equals("i1"));

    String addr = getPtr(node.getPtr()); // rs1's address

    String rd = null; // rd : RS2'S VALUE
    if (node.getRes().charAt(0) != '%' && node.getRes().charAt(0) != '@') {
      // imm
      rd = "t0";
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", rd,
              node.getRes().equals("false") ? "0" : node.getRes().equals("true") ? "1" : node.getRes().equals("null")?"0":node.getRes()))
          .build());
    } else if(node.getRes().charAt(0) == '@') {
      // global str
      rd = "t0";
      global2ValReg(node.getRes().substring(1), rd, "l" + tp);
    } else
    if (rd == null) {
      for (int i = 0; i < curFunc.getParavaluelist().size(); ++i)
        if (node.getRes().equals(curFunc.getParavaluelist().get(i))) {
          if (i < 8)
            res = -i - 1;
          else {
            res = vrM.getArgM(i);
          }
          break;
        }
      // rd contain the value
      if (res >= 0) {
        if (res == 114514) {
          res = vrM.add(node.getRes());
          rd = "t3";
          curB.add(riscL.builder()
              .op("l" + tp)
              .rd(rd)
              .imm(res * 4)
              .rs1("sp")
              .build());
        } else {
          // rd.ptr point to->args.val
          rd = "t3";
          curB.add(riscL.builder()
              .op("l" + tp)
              .rd(rd)
              .imm(res * 4)
              .rs1("sp")
              .build());
        }
      } else {
        // rd has contained the val;
        rd = "a" + (-res - 1);
      }
    }

    // store res into addr “t4”
    // addr -> the real addr
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
    var addr = getPtr(node.getPtr());
    // store addr.value(t4) to res.ptr(t3)
    // t4 is the val addr
    curB.add(riscL.builder()
        .op("l" + tp)
        .rd(addr)
        .imm(0)
        .rs1(addr)
        .build());
    // addr get the ptr.val, store into res.ptr
    curB.add(riscS.builder()
        .op("s" + tp)
        .rs2(addr)
        .imm(offset * 4)
        .rs1("sp").build());
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
        // entry storeT
        blck.setLabel(func.getName());
        vrM.setCurB(curB);
        vrM.store(); // size currect
        curB.adS("sp", "sp", -(vrM.getSize() / 4 * 16));
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
