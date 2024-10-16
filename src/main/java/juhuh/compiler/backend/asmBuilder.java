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
  // if is spilled then direct store into the val;
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
      return regColor.getRegName(res);
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
          curB.add(pseudo.builder()
              .strs(new vector<String>("mv", num, regColor.getRegName(res)))
              .build());
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

  // res = valPtr
  @Override
  public void visit(irBinary node) throws error {
    // binary
    if(status == true)
      return;
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
    // maxargs , maxargs + 7
    vrM.storeCall();

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
        // store tmpvar to sp + (curId - 8) * 4
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
    if (!node.getRes().equals("") && !node.getRes().equals("void")) {
      // not void call
      var tp = tBool(node.getFunc().getRetType().equals(SemanticChecker.boolType));
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

    vrM.restoreCall();

  }

  @Override
  public void visit(irGetElement node) throws error {
    // should change name -> id
    if(status == true)
      return;
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
          .rd("t5")
          .rs1(offset)
          .imm(2)
          .build());
      offset = "t5";
    }
    // tmp += t0
    var res = regColor.getResult(node.getRes(), "t6", "w", vrM);
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
    curB.adS("sp", "sp", vrM.getSize() * 4);
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
            res = vrM.getOffset(regColor.getSpillReg(node.getRes()));
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
    if(res != null) {
      curB.add(riscL.builder()
      .op("l" + tp)
        .rd("t5")
        .imm(0)
        .rs1(addr)
        .build());
      // addr get the ptr.val, store into res.ptr
      curB.add(res);
    }
    // curB.add(riscS.builder()
    // .op("s" + tp)
    // .rs2(addr)
    // .imm(offset * 4)
    // .rs1("sp").build());
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
    public HashMap<color, Integer> nxt;
  }

  private void addCurB(vector<irBinary> phi, vector<Integer> finalOrder) {
    for (var pr : finalOrder) {
      var ph = phi.get(pr);
      curB.add(pseudo.builder()
        .strs(new vector<String>("#phi_nonCircle"))
        .build());
      String t0 = "t6";
      if (ph.getOp2() != null)
        t0 = mem2a(ph.getOp2(), 6, tBool(ph.getTp().equals("i1")));
      
      asmNode res = null;
      if (ph.getRes() != null) {
        res = regColor.getResult(ph.getRes(), t0, tBool(ph.getTp().equals("i1")), vrM);
        curB.add(res);
      }
    }

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
      if(curB.getLabel().equals("cond.end1"))
        System.err.println("Debug");
      if(pr.ed == null) {
        edge.add(null);
        continue;
      }
      edge.add(pr);
      if(pr.st == null) {
        immDef.add(pr.num);
      } 
    }
    HashMap<color, costa> coInfo = new HashMap<>();
    // build edge
    
    for (var pr : edge) {
      if(pr == null || pr.st == null) {
        continue;
      } 
      if (coInfo.get(pr.st) == null) {
        coInfo.put(pr.st, new costa());
        coInfo.get(pr.st).nxt = new HashMap<color, Integer>();
        coInfo.get(pr.st).in_id = -1;
      }
      if (coInfo.get(pr.ed) == null) {
        coInfo.put(pr.ed, new costa());
        coInfo.get(pr.ed).nxt = new HashMap<color, Integer>();
        coInfo.get(pr.ed).in_id = -1;
      }
      coInfo.get(pr.ed).in_id = pr.num;
      coInfo.get(pr.st).nxt.put(pr.ed, pr.num);
    }
    
    Queue<color> topo = new LinkedList<color>();
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
      if(fa == -1)
        continue;
      finalOrder.add(fa);
      var st = edge.get(fa).st;
      if (st == null)
        continue;
      coInfo.get(st).nxt.remove(edge.get(fa).ed);
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
        memCol2a(regColor.col2i(pr), 6, "w");
        // pr def <- use
        circle.clear();
        // findcircle to add edge
        color cur = pr;
        do {
          // in_id
          var st = edge.get(coInfo.get(cur).in_id).st;
          coInfo.get(st).nxt.remove(cur);
          if (st != pr) {
            cur = st;
            circle.add(coInfo.get(cur).in_id);
          } else {
            break;
          }
        } while (cur != pr);
        addCurB(phi, circle);
        // last t6 -> cur
        curB.add(regColor.getColResult(cur, "t6", "sw", vrM));
      }
    // 先非环，再环, 最后immDef。
    
    for(var id : immDef) {
      //
      var pr = edge.get(id);
      curB.add(pseudo.builder()
          .strs(new vector<String>("#phi_immDef"))
          .build());
      // mem2a pr.st -> t6
      mem2a(phi.get(id).getOp2(), 6, tBool(phi.get(id).getTp().equals("i1")));
      // t6 -> pr.ed
      curB.add(regColor.getColResult(pr.ed, "t6", "sw", vrM));
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
        vrM.setSize(6 + Math.min(curFunc.getParatypelist().size(), 8));
        // entry storeT
        blck.setLabel(func.getName());
        vrM.storeDef(); // size currect
        curB.adS("sp", "sp", -(vrM.getSize() / 4 * 16));
      }
    }
    if (node.getStmts() != null)
      for (irStmt ins : node.getStmts()) {
        ins.accept(this);
      }
      // delphi before jump
    if (status == false){
      shuPhi(node.getPhiDel());
      if (node.getTerminalstmt() != null)
        node.getTerminalstmt().accept(this);
      else
        node.getEndTerm().accept(this);
      func.getNodes().add(blck);
    }
  }
}
