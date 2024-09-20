## file structure
```
Root
|
--def
  |--globaldef i = 1/(_init)
  |--funcdef
  |--classdef
  |--vardef
|
```

## task description
need to build a IR code transformer,then use toString method to get the whole IRcode

break the whole task into several unique tasks
### 1. tool design: 
 1) instruction taker (in folder ir/ins) for   taking some args and output the ir ins code.
  2) inst node corresponding astExprNode ->
  3) stmt node corresponding astStmtNode
  5) type node : -> right before irbuilder
   - i32 int
   - i8 bool ?
   - ptr 4 
   - string -> ptr to the char array
   - stringconst -> anonymous global variable "@.str_%d"
   - array -> ptr to the addr real val array, length = first 4byte, -> length*typeaddr
   create a new array we must know its length before actually running the code
  4) global def node name manager:
  - consensus
    1. base type : 
      - @ global variable + init value
    2. class:
      - %class.a = {i32, ptr}
      - method (this, type)
      - constr: (assignexpr) ?
  6) func def node
  7) class def node 
  - 命名规则
    1. method -> classname.method 
    2. variable -> class ? 
     :var + (.) string -> count
     ptr name + ".depth+.selfN"
     this.a.b  ptr 
     
### 2. IR build roadmap
 1) entity -> type : constant or register
 2) ins
 3) stmt
 
 4) builder : only consider int, bool, class
  - int a = 1 + 2; // 2const / 0const 2place _init() initialplace promise : register contain the final name , visit binary node -> return res is enough
  - member (a.b).c ()
  - array  array[sub] array class member? 0 : 1
  - atom check (this.)? -> ptr 
 5) string 
  const ptr
  variable string.class : char* getelement 0, 0
 6) array
  - 一维？ int bool ptr
  - arr[-1] = size, size = 4*n
  
 7) toString 
 最终只需要汇集到root中 例如遇到classdef -> funcdef 需要加回 root->funcdef，不妨碍，可以先进classdef函数

 8.25 need to fix irSelect, cond : reg 2 times =

