  .text
  .globl __init__
__init__:
  sw ra, -4(sp)
  addi sp, sp, -64
  j return0

return0:
  lw ra, 60(sp)
  addi sp, sp, 64
  ret   



  .globl main
main:
  sw ra, -4(sp)
  addi sp, sp, -144
  #Call  
  call  __init__
  addi t0, sp, 44
  sw t0, 40(sp)
  #Call  
  call  getInt
  sw a0, 48(sp)
  #Store  
  la  t4, n.0.0
  lw t3, 48(sp)
  sw t3, 0(t4)
  #Call  
  call  getInt
  sw a0, 52(sp)
  #Store  
  la  t4, p.0.1
  lw t3, 52(sp)
  sw t3, 0(t4)
  #Call  
  call  getInt
  sw a0, 56(sp)
  #Store  
  la  t4, k.0.2
  lw t3, 56(sp)
  sw t3, 0(t4)
  lw t0, 52(sp)
  lw t1, 56(sp)
  sub t2, t0, t1
  sw t2, 60(sp)
  lw t0, 60(sp)
  li  t1, 1
  slt t2, t1, t0
  sb t2, 64(sp)
  lb t0, 64(sp)
  beqz  t0, .false0
  j if.then2.0.0
  .false0:
  j if.else2.0.0

if.then2.0.0:
  #Call  
  # @.str.0
  la  a0, .str.0
  call  print
  j if.end2.0.0

if.else2.0.0:
  j if.end2.0.0

if.end2.0.0:
  lw t0, 52(sp)
  lw t1, 56(sp)
  sub t2, t0, t1
  sw t2, 68(sp)
  #Store  
  la  t4, i.0.3
  lw t3, 68(sp)
  sw t3, 0(t4)
  j for.cond2.3.0

for.cond2.3.0:
  lw t0, 52(sp)
  lw t1, 56(sp)
  add t2, t0, t1
  sw t2, 72(sp)
  lw t0, 68(sp)
  lw t1, 72(sp)
  slt t2, t1, t0
  xori t2, t2, 1
  sb t2, 76(sp)
  lb t0, 76(sp)
  beqz  t0, .false1
  j for.body2.3.0
  .false1:
  j for.end2.3.0

for.body2.3.0:
  j log.lhs1

log.lhs1:
  li  t0, 1
  lw t1, 68(sp)
  slt t2, t1, t0
  xori t2, t2, 1
  sb t2, 80(sp)
  lb t0, 80(sp)
  beqz  t0, .false2
  j log.rhs1
  .false2:
  j log.end1

log.rhs1:
  lw t0, 68(sp)
  lw t1, 48(sp)
  slt t2, t1, t0
  xori t2, t2, 1
  sb t2, 84(sp)
  j log.end1

log.end1:
  lb t0, 80(sp)
  beqz  t0, .false3
  lb t1, 84(sp)
  sb t1, 88(sp)
  j  .end3
  .false3:
  lb t1, 80(sp)
  sb t1, 88(sp)
  j  .end3
  .end3:
  lb t0, 88(sp)
  beqz  t0, .false4
  j if.then4.0.2
  .false4:
  j if.else4.0.2

if.then4.0.2:
  lw t0, 68(sp)
  lw t1, 52(sp)
  xor t2, t0, t1
  seqz  t2, t2
  sb t2, 92(sp)
  lb t0, 92(sp)
  beqz  t0, .false5
  j if.then6.0.3
  .false5:
  j if.else6.0.3

if.then6.0.3:
  #Call  
  # @.str.1
  la  a0, .str.1
  call  print
  #Call  
  # %5
  lw a0, 68(sp)
  call  toString
  sw a0, 96(sp)
  #Call  
  # %12
  lw a0, 96(sp)
  call  print
  #Call  
  # @.str.2
  la  a0, .str.2
  call  print
  j if.end6.0.3

if.else6.0.3:
  #Call  
  # %5
  lw a0, 68(sp)
  call  printInt
  #Call  
  # @.str.3
  la  a0, .str.3
  call  print
  j if.end6.0.3

if.end6.0.3:
  j if.end4.0.2

if.else4.0.2:
  j if.end4.0.2

if.end4.0.2:
  j for.inc2.3.0

for.inc2.3.0:
  lw t0, 68(sp)
  li  t1, 0
  add t2, t0, t1
  sw t2, 100(sp)
  lw t0, 68(sp)
  li  t1, 1
  add t2, t0, t1
  sw t2, 104(sp)
  #Store  
  la  t4, i.0.3
  lw t3, 104(sp)
  sw t3, 0(t4)
  j for.cond2.3.0

for.end2.3.0:
  lw t0, 52(sp)
  lw t1, 56(sp)
  add t2, t0, t1
  sw t2, 108(sp)
  lw t0, 108(sp)
  lw t1, 48(sp)
  slt t2, t0, t1
  sb t2, 112(sp)
  lb t0, 112(sp)
  beqz  t0, .false6
  j if.then2.4.4
  .false6:
  j if.else2.4.4

if.then2.4.4:
  #Call  
  # @.str.4
  la  a0, .str.4
  call  print
  j if.end2.4.4

if.else2.4.4:
  j if.end2.4.4

if.end2.4.4:
  #Store  
  lw t4, 40(sp)
  li  t0, 0
  sw t0, 0(t4)
  j return1

return1:
  #Load  
  lw t4, 40(sp)
  lw t4, 0(t4)
  sw t4, 116(sp)
  lw a0, 116(sp)
  lw ra, 140(sp)
  addi sp, sp, 144
  ret   



 .section .data
  .globl n.0.0
  .p2align 2
n.0.0:
  .word 0
.size @n.0.0, 4

  .globl p.0.1
  .p2align 2
p.0.1:
  .word 0
.size @p.0.1, 4

  .globl k.0.2
  .p2align 2
k.0.2:
  .word 0
.size @k.0.2, 4

  .globl i.0.3
  .p2align 2
i.0.3:
  .word 0
.size @i.0.3, 4

 .section .rodata
.p2align 2
.str.true:
  .asciz "true"
  .size .str.true, 5

.p2align 2
.str.false:
  .asciz "false"
  .size .str.false, 6

.p2align 2
.str.0:
  .asciz "<< "
  .size .str.0, 4

.p2align 2
.str.1:
  .asciz "("
  .size .str.1, 2

.p2align 2
.str.2:
  .asciz ") "
  .size .str.2, 3

.p2align 2
.str.3:
  .asciz " "
  .size .str.3, 2

.p2align 2
.str.4:
  .asciz ">> "
  .size .str.4, 4

