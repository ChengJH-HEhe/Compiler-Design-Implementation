current phase: preparing for Mem2reg

1.basic block (BB)
get out[] , in[] 

use[]: atomexpr
def[]: entry block vardef

2. mem2reg

- 1. use in entry block: -> replace use -> def
- 2. use in other block: use phi -> (dominator tree)

3. domination pattern analysis:
  - Dom: visit v -> visit u => u dom v
  - Idom: nearest u && u dom v && u != v
  - Dom Frontier: y is dom frontier of x <=> x dom y's pred & ! x dom y

3.5 construct a Dom Tree

4. CFG: label ->  jump: 1-out; || branch: 2-out; use struct{label,irblocknode} as a map to construct CFG
  construct a global stack to maintain the ptr's {label->name} pair
5. place phi-Ins (2nd visit IR)
  - collect defName(%defName = alloca)
  - search for the defName's defBlock (assignExpr) (add . to pure num reg)
  - place phi in the defBlock's domFrontier -> place phi into (defBlock's domFrontier)'s domFrontier... until domFrontier has been visited or no domFrontier, also redefine the "ptr->regname" pair
6. rename atomExpr usage of ptr -> (3rd visit IR) (alloca's ptr starts with '%' + 'alpha')
  - only funcdef, block, program node
  - other nodes need to  implement replace Function
  - step1: enter block: st.push(status), replace "ptr = phi i32"  regs with "ptr + blockname"
  - step2: x = x.entry.1 + 1, change x -> x.entry.2
  - step3: rename phi rhs
  - step4: dfs cfg.nxt
  - step5: when return exit(stack)
7. del phi Ins
  - critical edge: s->t more than one in&out then insert a new block, change the j ins ->s & t->
  - normal: put in the end of new block






