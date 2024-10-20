package juhuh.compiler.backend;

import java.util.HashSet;

import juhuh.compiler.backend.asm.asmBlock;
import juhuh.compiler.backend.asm.ins.*;
import juhuh.compiler.util.vector;

public class VarRegManager {
  // alloc %.(ptr) or %num = (val) all to sp + id * 4
  // manaager for a
  // todo init for spilled vars
  // private HashMap<String, Integer> Var2Id = new HashMap<String, Integer>();

  private int size = 0, maxArgs = 0; // t0~t4
  private int aNum = 0, callNum = 0;
  private asmBlock curB;

  // local start with num = : ; start with alpha, alloca, first scan, add all
  // first add return -1 for func sp member check
  public void setMxS(HashSet<String> s) {
    mxS = 0;
    for(var str : s) {
      if(str.charAt(0) == 's')
        ++mxS;
    }
  }
  public void setCurB(asmBlock B, int aNum) {
    curB = B;
    this.aNum = aNum;
  }
  public void setCall(int callNum) {
    this.callNum = callNum;
  }
  // (sp(after)) | callargs(maxArgs)(storeCall(selfargs, t0~t4, ra), ) |
  // spillcount | (s0~s11 sp when loadDef/storeDef)
  public int getOffset(int id) {
    // args -id-1 < aNum - 8 selfArgs
    int id22 = getSize() - mxS - (-id - 1 - Math.max(0, aNum - 8)); // >=
    if ((-id - 1) < aNum - 8) {
      id22 = getSize() + (aNum - 8) - (-id - 1);
    } // > 8 selfArgs
    // if(id22 <= maxArgs + 5 + Math.min(aNum, 8) )
    // System.err.println("bug!");
    // spillCount
    return id22 * 4;
    // spill count;
  }

  public int getCallArgs(int id) {
    return callNum - 1 - id;
  }

  // CALL ONLY TWICE!
  public void setSize(int size) {
    this.size += size;
  }

  public int getSize() {
    int res = mxA + size + mxS;
    return res + (4 - res&3) % 4;
  }

  // 16 multiply

  public int getMaxargs() {
    return maxArgs;
  }

  // private vector<Integer> t;
  // difference? call: caller; define: callee
  // before call, caller store value to the args place
  // when calling, callee store args to the localptr
  HashSet<String> in;
  vector<String> regSt;
  String[] imReg;
  int mxS = 0, mxA = 1;
  String[] storeCall(String def, HashSet<String> in, int argSize) {
    // curT[i] = size + i
    this.in = in;

    regSt = new vector<String>();
    for (int i = 0; i < 12; ++i)
      if (!in.contains("s" + i) && (def == null || !def.equals("s" + i)))
        regSt.add("s" + i);
    imReg = new String[13];
    int cur = 0, cur1 = maxArgs+1;
    for (int i = 0; i < 5; ++i) {
      if (in.contains("t" + i))
        if (cur != regSt.size()) {
          imReg[i] = regSt.get(cur);
          ++cur;
          curB.add(pseudo.builder()
              .strs(new vector<String>("mv", imReg[i], "t" + i))
              .build());
        } else{
          imReg[i] = Integer.toString((cur1)*4);
          curB.add(riscS.builder()
              .op("sw")
              .rs2("t" + i)
              .imm((cur1) * 4)
              .rs1("sp")
              .build());
          ++cur1;
        }
    }
    // caller ti ra ai
    // a0~amin(num-1,7) 也需要
    for (int i = 0; i < 8; ++i) {
      if (i == 0 || in.contains("a" + i))
        if (cur != regSt.size()) {
          imReg[5 + i] = regSt.get(cur);
          ++cur;
          curB.add(pseudo.builder()
              .strs(new vector<String>("mv", imReg[5 + i], "a" + i))
              .build());
        } else {
          imReg[5 + i] = Integer.toString((cur1)*4);
          cur1++;
          curB.add(riscS.builder()
          .op("sw")
          .rs2("a" + i)
          .imm(Integer.parseInt(imReg[5+i]))
          .rs1("sp")
          .build());
        }
      }
      mxA = Math.max(mxA, cur1);
    return imReg;
    // 8~maxargs - i
  }

  String result;
  void restoreCall(boolean ret) {
    if (ret == false) {
      result = null;
    }
    for (int i = 0; i < 8; ++i) {
      if ((i == 0 || in.contains("a" + i)) && (result == null || !result.equals("a" + i)))
        if (imReg[5 + i].charAt(0) == 's') {
          curB.add(pseudo.builder()
              .strs(new vector<String>("mv", "a" + i, imReg[5 + i]))
              .build());
        } else
          curB.add(riscS.builder()
              .op("lw")
              .rs2("a" + i)
              .imm(Integer.parseInt(imReg[5+i]))
              .rs1("sp")
              .build());
    }
    for (int i = 0; i < 5; ++i) {
      if (in.contains("t" + i) && (result == null || !result.equals("t" + i)))
        if (imReg[i].charAt(0) == 's') {
          curB.add(pseudo.builder()
              .strs(new vector<String>("mv", "t" + i, imReg[i]))
              .build());
        } else
          curB.add(riscS.builder()
              .op("lw")
              .rs2("t" + i)
              .imm(Integer.parseInt(imReg[i]))
              .rs1("sp")
              .build());
    }
  }

  private HashSet<String> out;
  private int[] sReg;
  void storeDef(HashSet<String> out) {
    // store s0~s11
    this.out = out;
    sReg = new int[12];
    int cur = 0;
    for (int i = 0; i < 12; ++i) {
      if (out.contains("s" + i))
        curB.add(riscS.builder()
            .op("sw")
            .rs2("s" + i)
            .imm(-(sReg[i] = ++cur) * 4)
            .rs1("sp")
            .build());
    }
    mxS = cur;
    curB.adS("sp", "sp", -(getSize() / 4 * 16));
    curB.add(riscS.builder().op("sw")
        .rs2("ra")
        .imm(maxArgs * 4)
        .rs1("sp")
        .build());
  }

  void restoreDef() {
    // restore s0~s11
    // opt#1 ra store;
    curB.add(riscL.builder().op("lw")
        .rd("ra")
        .imm(maxArgs * 4)
        .rs1("sp")
        .build());
    curB.adS("sp", "sp", getSize() * 4);
    for (int i = 0; i < 12; ++i) {
      if (out.contains("s" + i))
        curB.add(riscS.builder()
            .op("lw")
            .rs2("s" + i)
            .imm(-(sReg[i]) * 4)
            .rs1("sp")
            .build());
    }

  }

  void argsInc(int sz) {
    if (sz > 8)
      maxArgs = Math.max(maxArgs, sz - 8 + 1);
  }
  // t0-6 loop %7 int is the reg id
  // call add args, add before call

  // callee do nothing knows the args & the sp+i

}
