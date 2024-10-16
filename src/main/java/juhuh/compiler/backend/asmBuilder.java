package juhuh.compiler.backend;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

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
import juhuh.compiler.opt.regAlloc.color;
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
    vrM = new VarRegManager();
    vrM.setSize(regColor.spillCount);
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
  // if is spilled then direct store into the val="t"+num;
  // else return imm ; reg.
  public String mem2a(String name, int num, String tp) {
    if (name == null)
      return null;
    if (name.getBytes()[0] != '%' && name.getBytes()[0] != '@') {
      var psu = pseudo.builder()
          .strs(new vector<String>("li", "t" + num,
              name.equals("false") ? "0" : name.equals("true") ? "1" : name.equals("null") ? "0" : name))
          .build();
      if (isImm(name))
        return Integer.toString(Imm(name));
      else
        curB.add(psu);
      return "t" + num;
    } else if (name.charAt(0) == '%') {
      var res = regColor.getReg(name);
      curB.add(pseudo.builder()
          .strs(new vector<String>("#mem2a", name, (Integer.toString(res))))
          .build());
      if (res >= 0) {
        // normal reg
        return regColor.getRegName(res);
      } else {
        if (res == -114514191) {
          // return "233";
          return null;
        } else {
          var L = riscL.builder()
              .op("l" + tp)
              .rd("t" + num)
              .rs1("sp")
              .imm(vrM.getOffset(res))
              .build();
          curB.add(L);
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

  public String memCol2a(int res, int num, String tp) {

    if (res >= 0) {
      // normal reg
      curB.add(pseudo.builder()
          .strs(new vector<String>("mv", "t" + num, regColor.getRegName(res)))
          .build());
      return "t" + num;
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

  // tp = "w" or "b"
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
        if (num.charAt(0) == 'a') {
          var regname = regColor.getRegName(res);
          int aid = Integer.parseInt(regname.substring(1));
          if (regname.charAt(0) != 'a'
              || aid >= Math.min(regColor.argsId, Integer.parseInt(num.substring(1))))
            curB.add(pseudo.builder()
                .strs(new vector<String>("mv", num, regname))
                .build());
          else {
            int id = vrM.getMaxargs() + 5 + aid;
            curB.add(riscL.builder()
                .op("lw")
                .rd(num)
                .imm(id * 4)
                .rs1("sp")
                .build());
          }
          return num;
        } else
          return regColor.getRegName(res);
      } else {
        if (res == -114514191) {
          throw new error(name + "not colored");
        } else {
          curB.add(riscL.builder()
              .op("l" + tp)
              .rd(num)
              .rs1("sp")
              .imm(vrM.getOffset(res))
              .build());
          return num;
        }
      }
    } else {
      // la (string address)
      curB.add(pseudo.builder()
          .strs(new vector<String>("la", num, name.substring(1)))
          .build());
      return num;
    }
  }

  public static boolean isImm(String im) {
    if (im.equals("null") || im.equals("false") || im.equals("true"))
      return true;
    if (im.charAt(0) == 's' || im.charAt(0) == 'a' || im.charAt(0) == 't' || im.charAt(0) == '%'
        || im.charAt(0) == '@')
      return false;
    return Math.abs(Imm(im)) <= 2048;
  }

  // res = valPtr
  private void swap(String a, String b) {
    String c = a;
    a = b;
    b = c;
  }

  @Override
  public void visit(irBinary node) throws error {
    // binary
    if (status == true)
      return;
    String tp = tBool(node.getTp().equals("i1"));

    // calc t0 t1 to t2
    var resul = regColor.getResult(node.getRes(), "t5", tp, vrM);
    var rd = "t5";
    if (resul instanceof pseudo) {
      rd = ((pseudo) resul).getStrs().get(1);
      resul = null;
    }
    if (node.getOp().equals("sdiv") || node.getOp().equals("srem")) {
      var t0 = mem2a(node.getOp1(), 5, tp);
      var t1 = mem2a(node.getOp2(), 6, tp);
      curB.add(riscR.builder()
          .op(node.getOp().substring(1))
          .rd(rd)
          .rs1(t0)
          .rs2(t1)
          .build());
    } else {
      var cmd = riscR.builder()
          .op(node.getOp())
          .rd(rd)
          .build();
      if (node.getOp().equals("shl") ||
          node.getOp().equals("ashr")) {
        cmd.setRs1(mem2a(node.getOp1(), 5, tp));
        cmd.setRs2(mem2a(node.getOp2(), 6, tp));
        if (node.getOp().equals("shl")) {
          cmd.setOp("sll");
        } else if (node.getOp().equals("ashr")) {
          cmd.setOp("sra");
        }
        if (isImm(cmd.getRs2()))
          cmd.setOp(cmd.getOp() + "i");
      } else if (node.getOp().equals("sub")) {
        cmd.setRs1(mem2a(node.getOp1(), 5, tp));
        if (isImm(node.getOp2())) {
          cmd.setOp("addi");
          cmd.setRs2(Integer.toString(-Imm(node.getOp2())));
        } else {
          cmd.setRs2(mem2a(node.getOp2(), 6, tp));
        }
      } else {
        if (isImm(node.getOp1())) {
          swap(node.getOp1(), node.getOp2());
          swap(cmd.getRs1(), cmd.getRs2());
        }
        cmd.setRs1(mem2a(node.getOp1(), 5, tp));
        if (isImm(node.getOp2()) && !cmd.getOp().equals("mul")) {
          cmd.setOp(cmd.getOp() + "i");
          cmd.setRs2(Integer.toString(Imm(node.getOp2())));
        } else {
          cmd.setRs2(mem2a(node.getOp2(), 6, tp));
        }
      }
      curB.add(cmd);
    }
    // store to res
    if (resul != null)
      curB.add(resul);
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
    // maxargs , maxargs + 7

    vrM.storeCall(regColor.getReg(node.getLive().out));

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
        tmpvar = mem2aS("null", tmpvar, vartype.get(curId).equals("i1") ? "b" : "w");
      } else if (arg.getBytes()[0] == '%' || arg.getBytes()[0] == '@') {
        tmpvar = mem2aS(arg, tmpvar, vartype.get(curId).equals("i1") ? "b" : "w");
      } else {
        curB.add(pseudo.builder()
            .strs(new vector<String>("li", tmpvar,
                arg.equals("false") ? "0" : arg.equals("true") ? "1" : arg.equals("null") ? "0" : arg))
            .build());
      }

      if (curId >= 8) {
        // store tmpvar to sp + (aNum - curId) * 4
        // "t3" store the val
        curB.add(riscS.builder()
            .op(vartype.get(curId).equals("i1") ? "sb" : "sw")
            .rs2(tmpvar)
            .imm(vrM.getCallArgs(curId) * 4)
            .rs1("sp")
            .build());
      }
    }

    curB.add(pseudo.builder()
        .strs(new vector<String>("call", node.getFunc().getName()))
        .build());
    curB.add(pseudo.builder()
        .strs(new vector<String>("mv", "t6", "a0"))
        .build());
    vrM.restoreCall();
    if (!node.getRes().equals("") && !node.getRes().equals("void")) {
      // not void call
      var tp = tBool(node.getFunc().getRetType().equals(SemanticChecker.boolType));
      var res = regColor.getResult(node.getRes(), "t6", tp, vrM);
      // store retval to res
      if (res == null) {
        // store to z0
        curB.add(pseudo.builder()
            .strs(new vector<String>("#mv", "z0", "t6"))
            .build());
      } else {
        if (!(res instanceof pseudo))
          ((riscS) res).setRs2("t6");
        curB.add(res);
      }
    }
    // store a regs
  }

  @Override
  public void visit(irGetElement node) throws error {
    // should change name -> id
    if (status == true)
      return;
    curB.add(pseudo.builder()
        .strs(new vector<String>("#getElement"))
        .build());
    var tmp = getPtr(node.getPtrval()); // 存放地址寄存器

    // t0
    String offset = "t5";
    if (node.getId1().equals("0")) {
      offset = "0";
    } else if (node.getId1().getBytes()[0] != '%')
      offset = Integer.toString((Integer) (Integer.parseInt(node.getId1()) * 4));
    else {
      offset = mem2a(node.getId1(), 5, "w");
      // t0 <<= 2; offset
      curB.add(riscRI.builder()
          .op("slli")
          .rd("t5")
          .rs1(offset)
          .imm(2)
          .build());
      offset = "t5";
    }
    // tmp += t0
    var res = regColor.getResult(node.getRes(), "t6", "w", vrM);
    asmNode calc = riscR.builder()
        .op("add")
        .rd("t6")
        .rs1(tmp)
        .rs2(offset)
        .build();
    if (!offset.equals("t5")) {
      ((riscR) calc).setOp("addi");
    }
    // load ptr to t3;
    // store t3 to res
    if (res instanceof riscS) {
      curB.add(calc);
      curB.add(res);
    } else {
      ((riscR) calc).setRd(((pseudo) res).getStrs().get(1));
      curB.add(calc);
    }
  }

  @Override
  public void visit(irIcmp node) throws error {
    //
    if (status == true)
      return;
    var resul = regColor.getResult(node.getRes(), "t5", "b", vrM);
    if (resul == null)
      return;

    // t0 t1 -> "t5"
    var t0 = mem2a(node.getOp1(), 5, tBool(node.getTp().equals("i1")));
    var t1 = mem2a(node.getOp2(), 6, tBool(node.getTp().equals("i1")));
    switch (node.getOp()) {
      case "sgt", "sle":
        if (isImm(t1)) {
          curB.add(pseudo.builder()
              .strs(new vector<String>("li", "t6", t1))
              .build());
          t1 = "t6";
        }
        break;
      default:
        if (isImm(t0)) {
          curB.add(pseudo.builder()
              .strs(new vector<String>("li", "t5", t0))
              .build());
          t0 = "t5";
        }
    }
    var rd = "t5";
    if (resul instanceof pseudo) {
      rd = ((pseudo) resul).getStrs().get(1);
    }

    switch (node.getOp()) {
      case "sgt":
        curB.add(riscR.builder()
            .op("slt" + (isImm(t0) ? "i" : ""))
            .rd(rd)
            .rs1(t1)
            .rs2(t0)
            .build());
        break;
      case "slt":
        curB.add(riscR.builder()
            .op("slt" + (isImm(t1) ? "i" : ""))
            .rd(rd)
            .rs1(t0)
            .rs2(t1)
            .build());
        break;
      case "sge":
        curB.add(riscR.builder()
            .op("slt" + (isImm(t1) ? "i" : ""))
            .rd(rd)
            .rs1(t0)
            .rs2(t1)
            .build());
        curB.add(riscRI.builder()
            .op("xori")
            .rd(rd)
            .rs1(rd)
            .imm(1)
            .build());
        break;
      case "sle":
        curB.add(riscR.builder()
            .op("slt" + (isImm(t0) ? "i" : ""))
            .rd(rd)
            .rs1(t1)
            .rs2(t0)
            .build());
        curB.add(riscRI.builder()
            .op("xori")
            .rd(rd)
            .rs1(rd)
            .imm(1)
            .build());
        break;
      case "eq":
        curB.add(riscR.builder()
            .op("xor" + (isImm(t1) ? "i" : ""))
            .rd(rd)
            .rs1(t0)
            .rs2(t1)
            .build());
        curB.add(pseudo.builder()
            .strs(new vector<String>("seqz", rd, rd))
            .build());
        break;
      case "ne":
        curB.add(riscR.builder()
            .op("xor" + (isImm(t1) ? "i" : ""))
            .rd(rd)
            .rs1(t0)
            .rs2(t1)
            .build());
        curB.add(pseudo.builder()
            .strs(new vector<String>("snez", rd, rd))
            .build());
        break;
    }

    // store to res
    if (!(resul instanceof pseudo))
      curB.add(resul);
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
      if (!isImm(id))
        curB.add(pseudo.builder()
            .strs(new vector<String>("mv", "a0", id))
            .build());
      else
        curB.add(pseudo.builder()
            .strs(new vector<String>("li", "a0", id))
            .build());
    }
    // reset T, add sp

    vrM.restoreDef();

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
    if (isImm(t0)) {
      if (Imm(t0) != 0) {
        var res = regColor.getResult(node.getRes(),
            mem2a(node.getVal1(), 5, tBool(node.getTp1().equals("i1"))), "w", vrM);
        curB.add(res);
      } else {
        var res = regColor.getResult(node.getRes(),
            mem2a(node.getVal2(), 5, tBool(node.getTp2().equals("i1"))), "w", vrM);
        curB.add(res);
      }
      return;
    }
    // normal
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
            .strs(new vector<String>("la", "t6", name.substring(1)))
            .build());
      } else {
        // Load word from name
        return mem2a(name, 6, "w");
      }
    return "t6";
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
      var psu = pseudo.builder()
          .strs(new vector<String>("li", rd,
              node.getRes().equals("false") ? "0"
                  : node.getRes().equals("true") ? "1" : node.getRes().equals("null") ? "0" : node.getRes()))
          .build();
      curB.add(psu);
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
            res = vrM.getOffset(regColor.getSpillReg(node.getRes()));
          }
          break;
        }
      // rd contain the value
      if (res >= 0) {
        if (res == 114514) {
          // res = vrM.add(node.getRes());
          // normal reg;
          rd = mem2a(node.getRes(), 5, tp);
          if (isImm(rd)) {
            curB.add(pseudo.builder()
                .strs(new vector<String>("mv", "t6", rd))
                .build());
            rd = "t6";
          }
        } else {
          // rd.ptr point to->args.val
          // spill in stack.
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
    // store res into addr “t5”
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
    // store addr.value(t5) to res.ptr(t3)
    // t5 is the val addr

    var res = regColor.getResult(node.getRes(), "t5", tp, vrM);
    // 挖的坑，addr可能是有用的。
    if (res != null) {
      var Ld = riscL.builder()
          .op("l" + tp)
          .rd("t5")
          .imm(0)
          .rs1(addr)
          .build();
      if (res instanceof pseudo) {
        Ld.setRd(((pseudo) res).getStrs().get(1));
        curB.add(Ld);
      } else {
        curB.add(Ld);
        // addr get the ptr.val, store into res.ptr
        curB.add(res);
      }

    }
  }

  // [num] st -> ed tempvar
  HashMap<Integer, vector<Integer>> edge, num;

  private class pair {
    color st, ed;
    int num; // ID

    public pair() {
    }
  };

  private class costa {
    // in_num, nxt;
    public int in_id;
    public color st;
    public HashMap<color, Integer> nxt;
  }

  // if same/ ed null/ pass.
  public static int Imm(String imm) {
    if (imm.equals("null") || imm.equals("false"))
      return 0;
    if (imm.equals("true"))
      return 1;
    return Integer.parseInt(imm);
  }

  private boolean movImm(String imm, String ed) {
    int id = regColor.getReg(ed);
    int im = Imm(imm);
    if (id >= 0) {
      curB.add(pseudo.builder()
          .strs(new vector<String>("li", regColor.getRegName(id), Integer.toString(im)))
          .build());
      return true;
    }
    return false;
  }

  private boolean sameColor(String st, String ed) {
    var edc = regColor.getCol(ed);
    var stc = regColor.getCol(st);
    if (edc == null)
      return true;
    if (stc == null) {
      // direct store into ed
      return movImm(st, ed);
    }
    return stc.equals(edc);
  }

  private void addCurB(vector<irBinary> phi, vector<Integer> finalOrder) {
    for (var pr : finalOrder) {
      var ph = phi.get(pr);
      curB.add(pseudo.builder()
          .strs(new vector<String>("#phi_nonCircle"))
          .build());
      // same place don't move
      if (sameColor(ph.getOp2(), ph.getRes())) {
        continue;
      }
      String t0 = "t6";
      if (ph.getOp2() != null)
        t0 = mem2a(ph.getOp2(), 6, tBool(ph.getTp().equals("i1")));

      asmNode res = null;
      if (ph.getRes() != null) {
        res = regColor.getResult(ph.getRes(), t0, tBool(ph.getTp().equals("i1")), vrM);
        if (isImm(t0) && res != null) {
          if (res instanceof pseudo) {
            ((pseudo) res).getStrs().set(0, "li");
            ((pseudo) res).getStrs().set(2, t0);
          } else {
            curB.add(pseudo.builder()
                .strs(new vector<String>("li", "t6", t0))
                .build());
            ((riscS) res).setRs2("t6");
          }
          curB.add(res);
        }
      }
    }

  }

  private void nxtremove(costa co, color ed) {
    co.nxt.remove(ed);
  }

  private void shuPhi(vector<irBinary> phi) {
    // rearrange the order
    // mv use -> def col->col
    vector<pair> edge = new vector<pair>();

    // new vector immediate -> def
    vector<Integer> immDef = new vector<>();
    // pr.num should equal to edge.size
    for (var ph : phi) {
      pair pr = new pair();
      pr.st = regColor.getCol(ph.getOp2());
      pr.ed = regColor.getCol(ph.getRes());
      pr.num = edge.size();
      if (curB.getLabel().equals("cond.end1"))
        System.err.println("Debug");
      if (pr.ed == null) {
        edge.add(null);
        continue;
      }
      edge.add(pr);
      if (pr.st == null) {
        immDef.add(pr.num);
      }
    }
    HashMap<Integer, costa> coInfo = new HashMap<>();
    // build edge

    for (var pr : edge) {
      if (pr == null || pr.st == null) {
        continue;
      }
      var stp = regColor.col2i(pr.st);
      var edp = regColor.col2i(pr.ed);
      if (coInfo.get(stp) == null) {
        coInfo.put(stp, new costa());
        coInfo.get(stp).nxt = new HashMap<color, Integer>();
        coInfo.get(stp).in_id = -1;
      }
      if (coInfo.get(edp) == null) {
        coInfo.put(edp, new costa());
        coInfo.get(edp).nxt = new HashMap<color, Integer>();
        coInfo.get(edp).in_id = -1;
      }
      coInfo.get(stp).st = pr.st;
      coInfo.get(edp).in_id = pr.num;
      coInfo.get(stp).nxt.put(pr.ed, pr.num);
    }

    Queue<Integer> topo = new LinkedList<Integer>();
    // add phi
    for (var pr : coInfo.entrySet()) {
      if (pr.getValue().nxt.isEmpty())
        topo.add(pr.getKey());
    }

    // non circle
    vector<Integer> finalOrder = new vector<Integer>(),
        circle = new vector<Integer>();
    while (!topo.isEmpty()) {
      var fa = (coInfo.get(topo.poll()).in_id);
      if (fa == -1)
        continue;
      finalOrder.add(fa);
      var st = regColor.col2i(edge.get(fa).st);
      nxtremove(coInfo.get(st), edge.get(fa).ed);
      if (coInfo.get(st).nxt.isEmpty())
        topo.add(st);
    }

    addCurB(phi, finalOrder);

    for (var pr : coInfo.keySet())
      if (!coInfo.get(pr).nxt.isEmpty()) {
        // arrange the order in circle, in != 0
        // pr -> t6
        curB.add(pseudo.builder()
            .strs(new vector<String>("#phi_circle_found"))
            .build());
        // pr def <- use
        circle.clear();
        // findcircle to add edge
        int cur = pr;
        do {
          // in_id
          var st = regColor.col2i(edge.get(coInfo.get(cur).in_id).st);
          nxtremove(coInfo.get(st), coInfo.get(cur).st);
          if (st != pr) {
            circle.add(coInfo.get(cur).in_id);
            cur = st;
          } else {
            break;
          }
        } while (cur != pr);
        if (!circle.isEmpty()) {
          memCol2a(pr, 6, "lw");
          addCurB(phi, circle);
          // last t6 -> cur
          curB.add(regColor.getColResult(edge.get(coInfo.get(cur).in_id).ed, "t6", "sw", vrM));
        }
        coInfo.get(cur).nxt.clear();
      }
    // 先非环，再环, 最后immDef。

    for (var id : immDef) {
      //
      var pr = edge.get(id);
      curB.add(pseudo.builder()
          .strs(new vector<String>("#phi_immDef"))
          .build());
      // mem2a pr.st -> t6
      var phs = mem2a(phi.get(id).getOp2(), 6, tBool(phi.get(id).getTp().equals("i1")));
      // t6 -> pr.ed
      var res = regColor.getColResult(pr.ed, "t6", "sw", vrM);
      if (res == null)
        continue;
      if (res instanceof riscS) {
        if (isImm(phs)) {
          // phs is imm
          curB.add(pseudo.builder()
              .strs(new vector<String>("li", "t6", phs))
              .build());
        }
      } else {
        if (isImm(phs)) {
          var psu = ((pseudo) res);
          psu.getStrs().set(0, "li");
          psu.getStrs().set(2, phs);
        }
      }
      curB.add(res);
    }
  }

  @Override
  public void visit(irBlock node) throws error {

    asmBlock blck = asmBlock.builder()
        .label(node.getLabel())
        .nodes(new vector<asmNode>())
        .build();

    curB = blck;
    if (status == false) {
      vrM.setCurB(curB, curFunc.getParatypelist().size());
      if (node.getLabel().equals("entry")) {
        vrM.setSize(6 + 8);
        // entry storeT
        blck.setLabel(func.getName());
        vrM.storeDef(regColor.getReg(node.getLive().in)); // size currect
      }
    }
    if (node.getStmts() != null)
      for (irStmt ins : node.getStmts()) {
        ins.accept(this);
      }
    // delphi before jump
    if (status == false) {
      shuPhi(node.getPhiDel());
      if (node.getTerminalstmt() != null)
        node.getTerminalstmt().accept(this);
      else
        node.getEndTerm().accept(this);
      func.getNodes().add(blck);
    }
  }
}
