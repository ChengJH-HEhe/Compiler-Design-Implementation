  .section text
  .globl null
null:
  sw t0, 0(sp)
  sw t1, -4(sp)
  sw t2, -8(sp)
  sw t3, -12(sp)
  sw t4, -16(sp)
  null sp sp -8
  lw t3, 0(sp)
  la t4 x.0.3 

  sw t3, 0(t4)
  lw t3, 0(sp)
  la t4 y.0.4 

  sw t3, 0(t4)
  lw t3, 0(sp)
  la t4 z.0.5 

  sw t3, 0(t4)

return:



  .globl null
null:
  sw t0, 0(sp)
  sw t1, -4(sp)
  sw t2, -8(sp)
  sw t3, -12(sp)
  sw t4, -16(sp)
  null sp sp -24
  call __init__ 

  call getInt 

  sw a0, 16(sp)
  lw t3, 4(sp)
  la t4 n.0.6 

  sw t3, 0(t4)
  lw t3, 0(sp)
  la t4 i.0.7 

  sw t3, 0(t4)

for.cond2.0.0:
  lw t0, 104(sp)
  lw t1, 108(sp)
  slt t2, t0, t1
  sw t2, 20(sp)

for.body2.0.0:
  call getInt 

  sw a0, 24(sp)
  lw t3, 6(sp)
  la t4 p.0.0 

  sw t3, 0(t4)
  call getInt 

  sw a0, 28(sp)
  lw t3, 7(sp)
  la t4 q.0.1 

  sw t3, 0(t4)
  call getInt 

  sw a0, 32(sp)
  lw t3, 8(sp)
  la t4 r.0.2 

  sw t3, 0(t4)
  lw t0, 124(sp)
  lw t1, 128(sp)
  add t2, t0, t1
  sw t2, 36(sp)
  lw t3, 9(sp)
  la t4 x.0.3 

  sw t3, 0(t4)
  lw t0, 136(sp)
  lw t1, 140(sp)
  add t2, t0, t1
  sw t2, 40(sp)
  lw t3, 10(sp)
  la t4 y.0.4 

  sw t3, 0(t4)
  lw t0, 148(sp)
  lw t1, 152(sp)
  add t2, t0, t1
  sw t2, 44(sp)
  lw t3, 11(sp)
  la t4 z.0.5 

  sw t3, 0(t4)

for.inc2.0.0:
  lw t0, 160(sp)
  li t1 0 

  add t2, t0, t1
  sw t2, 48(sp)
  lw t0, 160(sp)
  li t1 1 

  add t2, t0, t1
  sw t2, 52(sp)
  lw t3, 13(sp)
  la t4 i.0.7 

  sw t3, 0(t4)

for.end2.0.0:

log.lhs0:

log.lhs1:
  lw t0, 164(sp)
  li t1 0 

  xor t2, t0, t1
  seqz t2 t2 0
  sw t2, 56(sp)

log.rhs1:
  lw t0, 168(sp)
  li t1 0 

  xor t2, t0, t1
  seqz t2 t2 0
  sw t2, 60(sp)

log.end1:
  lb t0, 56(sp)
  beqz t0 bran.false0 

  lb t1, 60(sp)
  sb t1, 64(sp)
  j bran.end0 

  bran.false0: 

  lb t1, 56(sp)
  sb t1, 64(sp)
  j bran.end0 

  bran.end0: 


log.rhs0:
  lw t0, 172(sp)
  li t1 0 

  xor t2, t0, t1
  seqz t2 t2 0
  sw t2, 68(sp)

log.end0:
  lb t0, 64(sp)
  beqz t0 bran.false1 

  lb t1, 68(sp)
  sb t1, 72(sp)
  j bran.end1 

  bran.false1: 

  lb t1, 64(sp)
  sb t1, 72(sp)
  j bran.end1 

  bran.end1: 


if.then2.1.2:
  la t4 .str.0 

  lw a0, 0(t4)
  call print 


if.else2.1.2:
  la t4 .str.1 

  lw a0, 0(t4)
  call print 


if.end2.1.2:
  lw t3, 0(sp)
  addi t4, 12(sp)
  sw t3, 0(t4)

return:



 .section data
  .globl @p.0.0
@p.0.0:
  .word 4

  .globl @q.0.1
@q.0.1:
  .word 4

  .globl @r.0.2
@r.0.2:
  .word 4

  .globl @x.0.3
@x.0.3:
  .word 4

  .globl @y.0.4
@y.0.4:
  .word 4

  .globl @z.0.5
@z.0.5:
  .word 4

  .globl @n.0.6
@n.0.6:
  .word 4

  .globl @i.0.7
@i.0.7:
  .word 4

 .section rodata
@.str.true:
  .asciz "true"
  .size @.str.true, 5

@.str.false:
  .asciz "false"
  .size @.str.false, 6

@.str.0:
  .asciz "YES"
  .size @.str.0, 4

@.str.1:
  .asciz "NO"
  .size @.str.1, 3

