package juhuh.compiler.opt;

import juhuh.compiler.backend.asmBuilder;
import juhuh.compiler.ir.*;
import juhuh.compiler.opt.mem2reg.domBuilder;
import juhuh.compiler.util.vector;

public class optBuilder {
  vector<domBuilder> domFunc = new vector<domBuilder>();
  asmBuilder asmRt;
  irRoot rt;
  public optBuilder(asmBuilder asm) {
    asmRt = asm;
    
  }
  public void optimize(irRoot irt) {
    rt = irt;
    //
    
    for(int i = 0; i < irt.getFDef().size(); ++i) {
      // mem2reg.
      var cfg = new cfgBuilder();
      domFunc.add(new domBuilder(asmRt, cfg, irt.getFDef().get(i)));
    }

    // output the final asm Code; ir2Asm;

    // jump elim;

    asmRt.visit(irt);
    for(int i = 0; i < irt.getFDef().size(); ++i) {
      domFunc.get(i).visit(irt.getFDef().get(i));
    }
  }
}
