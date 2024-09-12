current phase: debugging Mem2reg

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
  different pattern to solve DOMT:
  1. 数据流迭代法：n^2, get label->id & id->label, then use reverse_postorder to iterate until dom bacomes unchanged -> get domset
  1. 5-> get idom, domF 
  2. Tarjan

4. construct a Dom Tree (1st visit IR)
  - funcdef's block, irBlock add a struct<label, irnode, domFrontier> 
  - CFG: label ->  jump: 1-out; || branch: 2-out; use struct{label,irblocknode} as a map to construct CFG
  - build Dom's idom, domF
    - idom: 
5. place phi-Ins (2nd visit IR)
  construct a global stack to maintain the ptr's {ptr->name} pair
  - collect defName(%defName = alloca) problem is: nxt block's first use, shouldn't load, vector(first load) -> phi or ? ? 
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
  - TODO : modify asmBuilder (%2 = add %1 0 -> mv %2, %1)

p1: func args: in irBuilder, I copy a0~a7, and store them in the real pointer. when calling another subroutine, (first def) a0~a7 will be invalid. how can i store the args in a temporary space? no other ways, except saving a0~a7.

p2: why ret.val won't place phi in ret block?








