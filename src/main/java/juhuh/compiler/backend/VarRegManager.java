package juhuh.compiler.backend;

import juhuh.compiler.backend.asm.asmBlock;
import juhuh.compiler.backend.asm.ins.*;

public class VarRegManager {
  // alloc %.(ptr) or %num = (val) all to sp + id * 4
  // manaager for a
  // todo init for spilled vars
  // private HashMap<String, Integer> Var2Id = new HashMap<String, Integer>();

  private int size = 0, maxArgs = 0; // t0~t4
  private int aNum = 0;
  private asmBlock curB;

  // local start with num = : ; start with alpha, alloca, first scan, add all
  // first add return -1 for func sp member check
  public void setCurB(asmBlock B, int aNum) {
    curB = B;
    this.aNum = aNum;
  }

  // (sp) | callargs(maxArgs) | s0~11 | spillcount | t0~6 | a0~7 | ra  (selfargs(N,N-1,..,8): spillcount
  // 0~argsId)
  public int getOffset(int id) {
    assert (id < 0);
    if ((-id - 1) < aNum - 8)
      return (getSize() - (-id - 1) - 1) * 4;
    else
      return ((-id - 1 - (aNum - 8)) + maxArgs + 12) * 4;
    // spill count;
  }

  public int getCallArgs(int id) {
    return aNum - id;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getSize() {
    maxArgs += ((size + maxArgs) % 4 == 0 ? 0 : (4 - (size + maxArgs) % 4));
    return size + maxArgs + 12;
  }

  // 16 multiply

  public int getMaxargs() {
    return maxArgs;
  }

  // private vector<Integer> t;
  // difference? call: caller; define: callee
  // before call, caller store value to the args place
  // when calling, callee store args to the localptr
  void storeCall() {
    // curT[i] = size + i
    for (int i = 0; i < 5; ++i) {
      curB.add(riscS.builder()
          .op("sw")
          .rs2("t" + i)
          .imm((-(i + 1)) * 4)
          .rs1("sp")
          .build());
    }
    // caller ti ra ai
    // a0~amin(num-1,7) 也需要
    for (int i = 0; i < Math.min(aNum, 8); ++i) {
      curB.add(riscS.builder()
          .op("sw")
          .rs2("a" + i)
          .imm(-(i + 6) * 4)
          .rs1("sp")
          .build());
    }
    curB.add(riscS.builder().op("sw")
        .rs2("ra")
        .imm((-1) * 4)
        .rs1("sp")
        .build());
    size += 7 + aNum;

    // 8~maxargs - i
  }

  void restoreCall() {
    for (int i = 0; i < 5; ++i) {
      curB.add(riscS.builder()
          .op("lw")
          .rs2("t" + i)
          .imm((-(i + 1)) * 4)
          .rs1("sp")
          .build());
    }
    for (int i = 0; i < Math.min(aNum, 8); ++i) {
      curB.add(riscS.builder()
          .op("lw")
          .rs2("a" + i)
          .imm(-(i + 6) * 4)
          .rs1("sp")
          .build());
    }
    curB.add(riscS.builder().op("lw")
        .rs2("ra")
        .imm((-1) * 4)
        .rs1("sp")
        .build());
  }

  void storeDef() {
    // store s0~s11
    for (int i = 0; i < 12; ++i) {
      curB.add(riscS.builder()
          .op("sw")
          .rs2("s" + i)
          .imm(maxArgs + i)
          .rs1("sp")
          .build());
    }
  }

  void restoreDef() {
    // restore s0~s11
    for (int i = 0; i < 12; ++i) {
      curB.add(riscS.builder()
          .op("lw")
          .rs2("s" + i)
          .imm(maxArgs + i)
          .rs1("sp")
          .build());
    }
  }

  void argsInc(int sz) {
    if (sz > 8)
      maxArgs = Math.max(maxArgs, sz - 8);
  }

  // t0-6 loop %7 int is the reg id
  // call add args, add before call

  // callee do nothing knows the args & the sp+i

}
