package juhuh.compiler.backend;

import java.util.HashMap;

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
import juhuh.compiler.opt.regAlloc.regCol;
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

  private regCol regColor;

  public void setCol(regCol regColor) {
    this.regColor = regColor;
  }

  public asmBuilder() {
    Rt = asmRoot.builder()
        .text(new vector<asmFuncDef>())
        .data(new vector<asmVarDef>())
        .rodata(new vector<asmVarDef>())
        .build();
  }

  public asmRoot getRt() {
    return Rt;
  }

  @Override
  public void visit(irNode node) throws error {
    node.accept(this);
  }

  @Override
  public void visit(irRoot node) throws error {
    // visit Program function in irFuncDef
    for (var globl : node.getGlobalDef()) {
      globl.accept(this);
    }
  }

  private void visitFunc(irFuncDef node) {
    curFunc = node;
    visit(node.getEntry());
    // entry -= size
    for (var get : node.getBody()) {
      if (get.isUnreachable())
        continue;
      get.accept(this);
    }
    // ret += size;
    visit(node.getRet());
  }

  private void global2ValReg(String label, String reg, String type) {
    curB.add(pseudo.builder()
        .strs(new vector<String>("la", reg, label))
        .build());
    if (label.charAt(0) != '.')
      curB.add(riscL.builder()
          .op(type)
          .rd(reg)
          .rs1(reg)
          .imm(0)
          .build());
  }

  // private void offset2PtrPtrReg(int offset, String reg) {
  // curB.add(riscL.builder()
  // .op("lw")
  // .rd(reg)
  // .rs1("sp")
  // .imm(offset)
  // .build());
  // }

  // private void offset2Val(int offset, String reg, String type) {
  // curB.add(riscL.builder()
  // .op(type)
  // .rd(reg)
  // .rs1("sp")
  // .imm(offset)
  // .build());
  // }

  // private void ptrPtr2ValReg(String result, String reg, String type) {
  // curB.add(riscL.builder()
  // .op(type)
  // .rd(result)
  // .rs1(reg)
  // .imm(0)
  // .build());
  // }

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
    // entry storeA > 8, store T
    visitFunc(node);
    // ret resetT value
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
    throw new error("should not visit irAlloca");
    // int res = vrM.add(node.getRes());
    // vrM.add(node.getRes() + ".RESULT");
    // if (status == false) {
    // curB.adS(t0, "sp", res * 4 + 4);
    // curB.add(riscS.builder()
    // .op("sw")
    // .rs2(t0)
    // .imm(res * 4)
    // .rs1("sp")
    // .build());
    // }
    // alloca ptr only a address

  }

  // for usual anonymous variable
  // TODO: if is spilled then direct store into the val;
  public String mem2a(String name, int num, String tp) {
    if (name == null)
      return null;
    if (name.getBytes()[0] != '%' && name.getBytes()[0] != '@') {
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", "t" + num,
              name.equals("false") ? "0" : name.equals("true") ? "1" : name.equals("null") ? "0" : name))
          .build());
      return "t" + num;
    } else if (name.charAt(0) == '%') {
      var res = regColor.getReg(name);
      if (res >= 0) {
        // normal reg
        return regColor.getRegName(res);
      } else {
        if (res == -114514191) {
          throw new error(name + "not colored");
        } else {
          curB.add(riscL.builder()
              .op(tp)
              .rd("t" + num)
              .rs1("sp")
              .imm(vrM.getOffset(res))
              .build());
          return "t" + num;
        }
      }

      // int op1 = vrM.add(name);
      // offset2Val(op1 * 4, "t" + num, "l" + tp);
    } else {
      global2ValReg(name.substring(1), "t" + num, "l" + tp);
      return "t" + num;
    }
  }

  public String mem2aS(String name, String num, String tp) {
    if (name == null)
      return null;
    if (name.getBytes()[0] != '%' && name.getBytes()[0] != '@') {
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", num,
              name.equals("false") ? "0" : name.equals("true") ? "1" : name.equals("null") ? "0" : name))
          .build());
      return num;
    } else if (name.charAt(0) == '%') {
      // int op1 = vrM.add(name);
      var res = regColor.getReg(name);
      if (res >= 0) {
        // normal reg
        return regColor.getRegName(res);
      } else {
        if (res == -114514191) {
          throw new error(name + "not colored");
        } else {
          curB.add(riscL.builder()
              .op(tp)
              .rd(num)
              .rs1("sp")
              .imm(vrM.getOffset(res))
              .build());
          return num;
        }
      }
    } else {
      // la + mv
      curB.add(pseudo.builder()
          .strs(new vector<String>("la", num, name.substring(1)))
          .build());
      return num;
    }
  }

  // res = valPtr
  @Override
  public void visit(irBinary node) throws error {
    // binary

    String tp = tBool(node.getTp().equals("i1"));
    if (node.getOp1() != null)
      if (node.getOp().equals("add") && node.getOp1().equals("0")) {
        // special: res = add 0, x1 const: li, res, mv

      }
    var t0 = mem2a(node.getOp1(), 5, tp);
    var t1 = mem2a(node.getOp2(), 6, tp);
    // calc t0 t1 to t2
    var resul = regColor.getResult(node.getRes(), "t5", tp, vrM);
    if (node.getOp().equals("sdiv") || node.getOp().equals("srem")) {
      curB.add(riscR.builder()
          .op(node.getOp().substring(1))
          .rd("t5")
          .rs1(t0)
          .rs2(t1)
          .build());
    } else {
      var cmd = riscR.builder()
          .op(node.getOp())
          .rd("t5")
          .rs1(t0)
          .rs2(t1)
          .build();
      if (node.getOp().equals("shl")) {
        cmd.setOp("sll");
      } else if (node.getOp().equals("ashr")) {
        cmd.setOp("sra");
      }
      curB.add(cmd);
    }
    // store to res
    if (resul != null)
      curB.add(resul);
    // curB.add(riscS.builder()
    // .op("s" + tp)
    // .rs2("t2")
    // .imm(res * 4)
    // .rs1("sp")
    // .build());
  }

  @Override
  public void visit(irBranch node) throws error {
    if (status == true)
      return;
    var str = mem2a(node.getCond(), 5, "b");
    curB.add(pseudo.builder()
        .strs(new vector<String>("beqz", str, ".false" + branCount))
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
      return;
    }
    // BUG: a0~a7 preserved by call node
    curB.add(pseudo.builder()
        .strs(new vector<String>("#Call  "))
        .build());
    vrM.setCurB(curB);

    // maxargs , maxargs + 7
    for (int i = 0; i < 8; ++i) {
      curB.add(riscS.builder()
          .op("sw")
          .rs2("a" + i)
          .imm((vrM.getMaxargs() + i) * 4)
          .rs1("sp")
          .build());
    }

    var varArr = node.getVal();
    var vartype = node.getType();

    for (int curId = 0; curId < varArr.size(); ++curId) {
      var arg = varArr.get(curId);
      curB.add(pseudo.builder()
          .strs(new vector<String>("# " + arg))
          .build());

      String tmpvar = "t5";
      if (curId < 8)
        tmpvar = "a" + curId;
      // load to the tmpvar
      if (arg == null) {
        mem2aS("null", tmpvar, vartype.get(curId).equals("i1") ? "b" : "w");
      } else if (arg.getBytes()[0] == '%' || arg.getBytes()[0] == '@') {
        mem2aS(arg, tmpvar, vartype.get(curId).equals("i1") ? "b" : "w");
      } else {
        curB.add(pseudo.builder()
            .strs(new vector<String>("li", tmpvar,
                arg.equals("false") ? "0" : arg.equals("true") ? "1" : arg.equals("null") ? "0" : arg))
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
      // not void call
      var tp = "s" + tBool(node.getFunc().getRetType().equals(SemanticChecker.boolType));
      var res = regColor.getResult(node.getRes(), "a0", tp, vrM);
      // store retval to res
      if (res == null) {
        // store to z0
        curB.add(pseudo.builder()
            .strs(new vector<String>("#mv", "z0", "a0"))
            .build());
      } else {
        if (!(res instanceof pseudo))
          ((riscS) res).setRs2("a0");
        curB.add(res);
      }
    }
    // store a regs
    for (int i = 0; i < 8; ++i) {
      curB.add(riscL.builder()
          .op("lw")
          .rd("a" + i)
          .imm((vrM.getMaxargs() + i) * 4)
          .rs1("sp")
          .build());
    }

  }

  @Override
  public void visit(irGetElement node) throws error {
    // should change name -> id
    curB.add(pseudo.builder()
        .strs(new vector<String>("#getElement"))
        .build());
    var tmp = getPtr(node.getPtrval()); // 存放地址寄存器

    // t0
    String offset = "t5";
    if (node.getId1().getBytes()[0] != '%')
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", offset,
              ((Integer) (Integer.parseInt(node.getId1()) * 4)).toString()))
          .build());
    else {
      offset = mem2a(node.getId1(), 5, "w");
      // t0 <<= 2; offset
      curB.add(riscRI.builder()
          .op("slli")
          .rd(offset)
          .rs1(offset)
          .imm(2)
          .build());
    }
    // tmp += t0
    var res = regColor.getResult(node.getRes(), "t6", "sw", vrM);
    curB.add(riscR.builder()
        .op("add")
        .rd("t6")
        .rs1(tmp)
        .rs2(offset)
        .build());
    // load ptr to t3;
    // store t3 to res
    curB.add(res);
  }

  @Override
  public void visit(irIcmp node) throws error {
    //
    if (status == true)
      return;

    // t0 t1 -> "t5"
    var t0 = mem2a(node.getOp1(), 5, tBool(node.getTp().equals("i1")));
    var t1 = mem2a(node.getOp2(), 6, tBool(node.getTp().equals("i1")));
    switch (node.getOp()) {
      case "sgt":
        curB.add(riscR.builder()
            .op("slt")
            .rd("t5")
            .rs1(t1)
            .rs2(t0)
            .build());
        break;
      case "slt":
        curB.add(riscR.builder()
            .op("slt")
            .rd("t5")
            .rs1(t0)
            .rs2(t1)
            .build());
        break;
      case "sge":
        curB.add(riscR.builder()
            .op("slt")
            .rd("t5")
            .rs1(t0)
            .rs2(t1)
            .build());
        curB.add(riscRI.builder()
            .op("xori")
            .rd("t5")
            .rs1("t5")
            .imm(1)
            .build());
        break;
      case "sle":
        curB.add(riscR.builder()
            .op("slt")
            .rd("t5")
            .rs1(t1)
            .rs2(t0)
            .build());
        curB.add(riscRI.builder()
            .op("xori")
            .rd("t5")
            .rs1("t5")
            .imm(1)
            .build());
        break;
      case "eq":
        curB.add(riscR.builder()
            .op("xor")
            .rd("t5")
            .rs1(t0)
            .rs2(t1)
            .build());
        curB.add(pseudo.builder()
            .strs(new vector<String>("seqz", "t5", "t5"))
            .build());
        break;
      case "ne":
        curB.add(riscR.builder()
            .op("xor")
            .rd("t5")
            .rs1(t0)
            .rs2(t1)
            .build());
        curB.add(pseudo.builder()
            .strs(new vector<String>("snez", "t5", "t5"))
            .build());
        break;
    }

    // store to res
    var resul = regColor.getResult(node.getRes(), "t5", "b", vrM);
    curB.add(resul);
    // curB.add(riscS.builder()
    // .op("sb")
    // .rs2("t2")
    // .imm(res * 4)
    // .rs1("sp")
    // .build());
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
      if (node.getVal() == null)
        return;
      // store retval to a0
      // %ret.val -> addr -> a0
      // store register value to "a0":
      var id = mem2a(node.getVal(), 5, tBool(node.getTp().equals("i1")));
      curB.add(pseudo.builder()
          .strs(new vector<String>("mv", "a0", id))
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
    // curB.add(riscL.builder()
    // .op("lw")
    // .rd("t" + i)
    // .imm((sz - i - 1) * 4)
    // .rs1("sp")
    // .build());
    // }

    curB.adS("sp", "sp", vrM.getSize() * 4);
    curB.add(pseudo.builder()
        .strs(new vector<String>("ret   "))
        .build());
  }

  @Override
  public void visit(irSelect node) throws error {
    if (status == true)
      return;
    // beqz
    // load cond
    var t0 = mem2a(node.getCond(), 5, "b");
    // branch

    curB.add(pseudo.builder()
        .strs(new vector<String>("beqz", t0, ".false" + branCount))
        .build());
    // store a1 -> res

    var res = regColor.getResult(node.getRes(),
        mem2a(node.getVal1(), 5, tBool(node.getTp1().equals("i1"))), "w", vrM);
    curB.add(res);

    curB.add(pseudo.builder()
        .strs(new vector<String>("j", ".end" + branCount))
        .build());
    curB.add(pseudo.builder()
        .strs(new vector<String>(".false" + branCount + ":"))
        .build());
    res = regColor.getResult(node.getRes(),
        mem2a(node.getVal2(), 5, tBool(node.getTp1().equals("i1"))), "w", vrM);
    curB.add(res);

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
    if (name != null)
      if (name.getBytes()[0] == '@') {
        curB.add(pseudo.builder()
            .strs(new vector<String>("la", "t4", name.substring(1)))
            .build());
      } else {
        // TODO : Load word from name
        // curB.add(riscL.builder()
        // .op("lw")
        // .rd("t4")
        // .imm(vrM.add(name) * 4)
        // .rs1("sp")
        // .build());
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
      rd = "t5";
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", rd,
              node.getRes().equals("false") ? "0"
                  : node.getRes().equals("true") ? "1" : node.getRes().equals("null") ? "0" : node.getRes()))
          .build());
    } else if (node.getRes().charAt(0) == '@') {
      // global str
      rd = "t5";
      global2ValReg(node.getRes().substring(1), rd, "l" + tp);
    } else if (rd == null) {
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
          // res = vrM.add(node.getRes());
          rd = mem2a(node.getRes(), 5, tp);
          // curB.add(riscL.builder()
          // .op("l" + tp)
          // .rd(rd)
          // .imm(res * 4)
          // .rs1("sp")
          // .build());
        } else {
          // rd.ptr point to->args.val
          rd = "t5";
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
    // res starts "with” % it's ok
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
    var res = regColor.getResult(node.getRes(), addr, tp, vrM);
    curB.add(res);
    // curB.add(riscS.builder()
    // .op("s" + tp)
    // .rs2(addr)
    // .imm(offset * 4)
    // .rs1("sp").build());
  }

  // [num] st -> ed tempvar
  HashMap<Integer, vector<Integer>> edge, num;

  private void shuPhi(vector<irBinary> phi) {
    // rearrange the order
    edge = new HashMap<>();
    num = new HashMap<>();
    // int id = 0;
    for (var ph : phi) {
      // if (!num.containsKey(regColor.getReg(ph.getRes())))
        // ++id;
    }

    // add phi
  }

  @Override
  public void visit(irBlock node) throws error {

    asmBlock blck = asmBlock.builder()
        .label(node.getLabel())
        .nodes(new vector<asmNode>())
        .build();

    curB = blck;
    shuPhi(node.getPhiDel());
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
