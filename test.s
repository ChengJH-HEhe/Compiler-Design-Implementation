  .text
  .globl square
square:
  sw ra, -4(sp)
  addi sp, sp, -48
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s1, t4
  rem t5, s0, s1
  mv  s2, t5
  rem t5, s0, s1
  mv  s0, t5
  mul t5, s2, s0
  mv  s0, t5
  j return1

_phi.return1:
  mv  a0, s0
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return1:
  j _phi.return1



  .globl gcd
gcd:
  sw ra, -4(sp)
  addi sp, sp, -48
  li  t6, 0
  xor t5, s1, t6
  seqz  t5, t5
  mv  s2, t5
  j _phi.entry

if.then2.0.0:
  j return2

if.else2.0.0:
  j if.end2.0.0

if.end2.0.0:
  slt t5, s0, s1
  mv  s2, t5
  j _phi.if.end2.0.0

if.then2.3.1:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %y
  # %x
  call  gcd
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j return2

if.else2.3.1:
  rem t5, s0, s1
  mv  s0, t5
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %y
  # %_10
  call  gcd
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j return2

_phi.entry:
  beqz  s2, .false0
  j if.then2.0.0
  .false0:
  j if.else2.0.0

_phi.if.end2.0.0:
  beqz  s2, .false1
  j if.then2.3.1
  .false1:
  j if.else2.3.1

_phi.return2:
  mv  a0, s0
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return2:
  j _phi.return2



  .globl lcm
lcm:
  sw ra, -4(sp)
  addi sp, sp, -48
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %a
  # %b
  call  gcd
  mv  s2, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  div t5, s0, s2
  mv  s0, t5
  mul t5, s0, s1
  mv  s0, t5
  j return3

_phi.return3:
  mv  a0, s0
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return3:
  j _phi.return3



  .globl Rand
Rand:
  sw ra, -4(sp)
  addi sp, sp, -48
  j for.cond2.0.1

for.cond2.0.1:
  li  t6, 3
  slt t5, s0, t6
  mv  s1, t5
  j _phi.for.cond2.0.1

for.body2.0.1:
  #Load  
  la  t4, no.0.21
  lw t4, 0(t4)
  mv  s1, t4
  #Load  
  la  t4, aa.0.18
  lw t4, 0(t4)
  mv  s2, t4
  mul t5, s1, s2
  mv  s1, t5
  #Load  
  la  t4, bb.0.19
  lw t4, 0(t4)
  mv  s2, t4
  add t5, s1, s2
  mv  s1, t5
  #Load  
  la  t4, MOD.0.20
  lw t4, 0(t4)
  mv  s2, t4
  rem t5, s1, s2
  mv  s1, t5
  #Store  
  la  t4, no.0.21
  sw s1, 0(t4)
  j for.inc2.0.1

for.inc2.0.1:
  li  t6, 1
  add t5, s0, t6
  mv  s0, t5
  j for.cond2.0.1

for.end2.0.1:
  #Load  
  la  t4, no.0.21
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 0
  slt t5, s1, t6
  mv  s1, t5
  j _phi.for.end2.0.1

if.then2.1.2:
  #Load  
  la  t4, no.0.21
  lw t4, 0(t4)
  mv  s1, t4
  li  t5, 0
  sub t5, t5, s1
  mv  s1, t5
  #Store  
  la  t4, no.0.21
  sw s1, 0(t4)
  j if.end2.1.2

if.else2.1.2:
  j if.end2.1.2

if.end2.1.2:
  #Load  
  la  t4, no.0.21
  lw t4, 0(t4)
  mv  s1, t4
  j return4

_phi.for.cond2.0.1:
  beqz  s1, .false2
  j for.body2.0.1
  .false2:
  j for.end2.0.1

_phi.for.end2.0.1:
  beqz  s1, .false3
  j if.then2.1.2
  .false3:
  j if.else2.1.2

_phi.return4:
  mv  a0, s1
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return4:
  j _phi.return4



  .globl RandRange
RandRange:
  sw ra, -4(sp)
  addi sp, sp, -48
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  call  Rand
  mv  s2, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  sub t5, s1, s0
  mv  s1, t5
  li  t6, 1
  add t5, s1, t6
  mv  s1, t5
  rem t5, s2, s1
  mv  s1, t5
  add t5, s0, s1
  mv  s0, t5
  li  t6, 1
  add t5, s0, t6
  mv  s0, t5
  j return5

_phi.return5:
  mv  a0, s0
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return5:
  j _phi.return5



  .globl init
init:
  sw ra, -4(sp)
  addi sp, sp, -48
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 140005
  li  a0, 140005
  call  _arr_init
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j for.cond2.0.2

for.cond2.0.2:
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s2, t4
  slt t5, s1, s2
  mv  s2, t5
  j _phi.for.cond2.0.2

for.body2.0.2:
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s1, 0(t4)
  j for.inc2.0.2

for.inc2.0.2:
  li  t6, 1
  add t5, s1, t6
  mv  s1, t5
  j for.cond2.0.2

for.end2.0.2:
  j for.cond2.1.3

for.cond2.1.3:
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s3, t4
  slt t5, s2, s3
  mv  s3, t5
  j _phi.for.cond2.1.3

for.body2.1.3:
  j for.cond4.0.4

for.cond4.0.4:
  li  t6, 15
  slt t5, t6, s3
  xori t5, t5, 1
  mv  s4, t5
  j _phi.for.cond4.0.4

for.body4.0.4:
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_18
  call  square
  mv  s4, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s5, t4
  rem t5, s4, s5
  mv  s4, t5
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s5, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s4, 0(t4)
  j for.inc4.0.4

for.inc4.0.4:
  li  t6, 1
  add t5, s3, t6
  mv  s3, t5
  j for.cond4.0.4

for.end4.0.4:
  j for.inc2.1.3

for.inc2.1.3:
  li  t6, 1
  add t5, s2, t6
  mv  s2, t5
  j for.cond2.1.3

for.end2.1.3:
  j for.cond2.2.5

for.cond2.2.5:
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s5, t4
  slt t5, s4, s5
  mv  s5, t5
  j _phi.for.cond2.2.5

for.body2.2.5:
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s5, t6
  #Load  
  lw t4, 0(t4)
  mv  s5, t4
  j for.cond4.0.6

for.cond4.0.6:
  j _phi.for.cond4.0.6

for.body4.0.6:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %x.3.29.for.cond4.0.6
  call  square
  mv  s5, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s7, t4
  rem t5, s5, s7
  mv  s5, t5
  #Load  
  la  t4, b.0.0
  lw t4, 0(t4)
  mv  s7, t4
  #getElement
  slli s5, s5, 2
  add t6, t4, s5
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 1
  sw t5, 0(t4)
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  xor t5, s5, s7
  seqz  t5, t5
  mv  s7, t5
  j _phi.for.body4.0.6

if.then6.0.3:
  j for.end4.0.6

if.else6.0.3:
  j if.end6.0.3

if.end6.0.3:
  j for.inc4.0.6

for.inc4.0.6:
  li  t6, 1
  add t5, s6, t6
  mv  s6, t5
  j for.cond4.0.6

for.end4.0.6:
  #Load  
  la  t4, L.0.8
  lw t4, 0(t4)
  mv  s5, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_49
  # %j.3.28.for.cond4.0.6
  call  lcm
  mv  s5, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, L.0.8
  sw s5, 0(t4)
  j for.inc2.2.5

for.inc2.2.5:
  li  t6, 1
  add t5, s4, t6
  mv  s4, t5
  j for.cond2.2.5

for.end2.2.5:
  #Load  
  la  t4, b.0.0
  lw t4, 0(t4)
  mv  s5, t4
  #getElement
  li  t5, 0
  add t6, t4, t5
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 1
  sw t5, 0(t4)
  #getElement
  li  t5, 4
  add t6, t4, t5
  mv  s5, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 1
  sw t5, 0(t4)
  j return6

_phi.for.cond2.0.2:
  beqz  s2, .false4
  j for.body2.0.2
  .false4:
  j for.end2.0.2

_phi.for.cond2.1.3:
  beqz  s3, .false5
  j for.body2.1.3
  .false5:
  j for.end2.1.3

_phi.for.cond4.0.4:
  beqz  s4, .false6
  j for.body4.0.4
  .false6:
  j for.end4.0.4

_phi.for.cond2.2.5:
  beqz  s5, .false7
  j for.body2.2.5
  .false7:
  j for.end2.2.5

_phi.for.cond4.0.6:
  li  t5, 1
  beqz  t5, .false8
  j for.body4.0.6
  .false8:
  j for.end4.0.6

_phi.for.body4.0.6:
  beqz  s7, .false9
  j if.then6.0.3
  .false9:
  j if.else6.0.3

_phi.return6:
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return6:
  j _phi.return6



  .globl build
build:
  sw ra, -4(sp)
  addi sp, sp, -48
  xor t5, s1, s2
  seqz  t5, t5
  mv  s3, t5
  j _phi.entry

if.then2.0.4:
  #Load  
  la  t4, a.0.3
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #Load  
  la  t4, sum.0.11
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s3, 0(t4)
  j log.lhs5

log.lhs5:
  j log.lhs6

log.lhs6:
  #Load  
  la  t4, a.0.3
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s4, t4
  slt t5, s3, s4
  mv  s3, t5
  j _phi.log.lhs6

log.rhs6:
  #Load  
  la  t4, a.0.3
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  li  t6, 0
  slt t5, s4, t6
  xori t5, t5, 1
  mv  s4, t5
  j log.end6

log.end6:
  beqz  s3, .false10
  mv  s3, s4
  j  .end10
  .false10:
  mv  s3, s3
  j  .end10
  .end10:
  j _phi.log.end6

log.rhs5:
  #Load  
  la  t4, b.0.0
  lw t4, 0(t4)
  mv  s4, t4
  #Load  
  la  t4, a.0.3
  lw t4, 0(t4)
  mv  s5, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s5, t6
  #Load  
  lw t4, 0(t4)
  mv  s5, t4
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s6, t4
  rem t5, s5, s6
  mv  s5, t5
  #getElement
  slli s5, s5, 2
  add t6, t4, s5
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  li  t6, 0
  slt t5, t6, s4
  mv  s4, t5
  j log.end5

log.end5:
  beqz  s3, .false11
  mv  s3, s4
  j  .end11
  .false11:
  mv  s3, s3
  j  .end11
  .end11:
  j _phi.log.end5

if.then4.0.7:
  #Load  
  la  t4, flag.0.9
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 1
  sw t5, 0(t4)
  #Load  
  la  t4, a.0.3
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s1, t6
  #Load  
  lw t4, 0(t4)
  mv  s1, t4
  #Load  
  la  t4, s.0.10
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  li  t5, 0
  add t6, t4, t5
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s1, 0(t4)
  j for.cond6.0.7

for.cond6.0.7:
  #Load  
  la  t4, L.0.8
  lw t4, 0(t4)
  mv  s3, t4
  slt t5, s1, s3
  mv  s3, t5
  j _phi.for.cond6.0.7

for.body6.0.7:
  #Load  
  la  t4, s.0.10
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  li  t6, 1
  sub t5, s1, t6
  mv  s5, t5
  #getElement
  slli s5, s5, 2
  add t6, t4, s5
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_55
  call  square
  mv  s4, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s5, t4
  rem t5, s4, s5
  mv  s4, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s4, 0(t4)
  j for.inc6.0.7

for.inc6.0.7:
  li  t6, 1
  add t5, s1, t6
  mv  s1, t5
  j for.cond6.0.7

for.end6.0.7:
  j if.end4.0.7

if.else4.0.7:
  j if.end4.0.7

if.end4.0.7:
  #Load  
  la  t4, now.0.1
  lw t4, 0(t4)
  mv  s1, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s0, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 0
  sw t5, 0(t4)
  j if.end2.0.4

if.else2.0.4:
  li  t6, 2
  mul t5, s0, t6
  mv  s3, t5
  li  t6, 2
  mul t5, s0, t6
  mv  s4, t5
  li  t6, 1
  add t5, s4, t6
  mv  s4, t5
  add t5, s1, s2
  mv  s5, t5
  li  t6, 2
  div t5, s5, t6
  mv  s5, t5
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_70
  # %l
  # %_76
  call  build
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  li  t6, 1
  add t5, s5, t6
  mv  s1, t5
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_72
  # %_77
  # %r
  call  build
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Load  
  la  t4, sum.0.11
  lw t4, 0(t4)
  mv  s1, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  add t5, s2, s6
  mv  s2, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s1, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s2, 0(t4)
  #Load  
  la  t4, flag.0.9
  lw t4, 0(t4)
  mv  s1, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  and t5, s2, s6
  mv  s2, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s2, 0(t4)
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s1, t6
  #Load  
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 0
  slt t5, t6, s1
  mv  s1, t5
  j _phi.if.else2.0.4

if.then4.0.8:
  j for.cond6.0.8

for.cond6.0.8:
  #Load  
  la  t4, L.0.8
  lw t4, 0(t4)
  mv  s2, t4
  slt t5, s1, s2
  mv  s2, t5
  j _phi.for.cond6.0.8

for.body6.0.8:
  #Load  
  la  t4, s.0.10
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  add t5, s6, s7
  mv  s6, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s6, 0(t4)
  j for.inc6.0.8

for.inc6.0.8:
  li  t6, 1
  add t5, s1, t6
  mv  s1, t5
  j for.cond6.0.8

for.end6.0.8:
  #Load  
  la  t4, now.0.1
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  li  t5, 0
  add t6, t4, t5
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 0
  sw t5, 0(t4)
  j if.end4.0.8

if.else4.0.8:
  j if.end4.0.8

if.end4.0.8:
  j if.end2.0.4

if.end2.0.4:
  j return7

_phi.entry:
  beqz  s3, .false12
  j if.then2.0.4
  .false12:
  j if.else2.0.4

_phi.log.lhs6:
  beqz  s3, .false13
  j log.rhs6
  .false13:
  j log.end6

_phi.log.end6:
  beqz  s3, .false14
  j log.rhs5
  .false14:
  j log.end5

_phi.log.end5:
  beqz  s3, .false15
  j if.then4.0.7
  .false15:
  j if.else4.0.7

_phi.for.cond6.0.7:
  beqz  s3, .false16
  j for.body6.0.7
  .false16:
  j for.end6.0.7

_phi.if.else2.0.4:
  beqz  s1, .false17
  j if.then4.0.8
  .false17:
  j if.else4.0.8

_phi.for.cond6.0.8:
  beqz  s2, .false18
  j for.body6.0.8
  .false18:
  j for.end6.0.8

_phi.return7:
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return7:
  j _phi.return7



  .globl pushdown
pushdown:
  sw ra, -4(sp)
  addi sp, sp, -48
  #Load  
  la  t4, t.0.2
  lw t4, 0(t4)
  mv  s1, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s1, t6
  #Load  
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 0
  slt t5, t6, s1
  mv  s1, t5
  j _phi.entry

if.then2.0.9:
  li  t6, 2
  mul t5, s0, t6
  mv  s1, t5
  li  t6, 2
  mul t5, s0, t6
  mv  s2, t5
  li  t6, 1
  add t5, s2, t6
  mv  s2, t5
  #Load  
  la  t4, now.0.1
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  #Load  
  la  t4, t.0.2
  lw t4, 0(t4)
  mv  s5, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  add t5, s4, s6
  mv  s4, t5
  #Load  
  la  t4, L.0.8
  lw t4, 0(t4)
  mv  s6, t4
  rem t5, s4, s6
  mv  s4, t5
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s4, 0(t4)
  #Load  
  la  t4, s.0.10
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s8, t6
  #Load  
  lw t4, 0(t4)
  mv  s8, t4
  #getElement
  slli s8, s8, 2
  add t6, t4, s8
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  #Load  
  la  t4, sum.0.11
  lw t4, 0(t4)
  mv  s8, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s9, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s7, 0(t4)
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s9, t6
  #Load  
  lw t4, 0(t4)
  mv  s9, t4
  add t5, s7, s9
  mv  s7, t5
  rem t5, s7, s6
  mv  s7, t5
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s9, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s7, 0(t4)
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s9, t6
  #Load  
  lw t4, 0(t4)
  mv  s9, t4
  add t5, s7, s9
  mv  s7, t5
  rem t5, s7, s6
  mv  s7, t5
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s9, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s7, 0(t4)
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s3, 0(t4)
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  add t5, s3, s4
  mv  s3, t5
  rem t5, s3, s6
  mv  s3, t5
  #getElement
  slli s2, s2, 2
  add t6, t4, s2
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s3, 0(t4)
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s0, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 0
  sw t5, 0(t4)
  j if.end2.0.9

if.else2.0.9:
  j if.end2.0.9

if.end2.0.9:
  j return8

_phi.entry:
  beqz  s1, .false19
  j if.then2.0.9
  .false19:
  j if.else2.0.9

_phi.return8:
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return8:
  j _phi.return8



  .globl update
update:
  sw ra, -4(sp)
  addi sp, sp, -48
  j log.lhs10

log.lhs10:
  j log.lhs11

log.lhs11:
  #Load  
  la  t4, pl.0.40
  lw t4, 0(t4)
  mv  s3, t4
  slt t5, s1, s3
  xori t5, t5, 1
  mv  s3, t5
  j _phi.log.lhs11

log.rhs11:
  #Load  
  la  t4, pr.0.41
  lw t4, 0(t4)
  mv  s4, t4
  slt t5, s4, s2
  xori t5, t5, 1
  mv  s4, t5
  j log.end11

log.end11:
  beqz  s3, .false20
  mv  s3, s4
  j  .end20
  .false20:
  mv  s3, s3
  j  .end20
  .end20:
  j _phi.log.end11

log.rhs10:
  #Load  
  la  t4, flag.0.9
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  li  t6, 0
  slt t5, t6, s4
  mv  s4, t5
  j log.end10

log.end10:
  beqz  s3, .false21
  mv  s3, s4
  j  .end21
  .false21:
  mv  s3, s3
  j  .end21
  .end21:
  j _phi.log.end10

if.then2.0.12:
  #Load  
  la  t4, now.0.1
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  li  t6, 1
  add t5, s4, t6
  mv  s4, t5
  #Load  
  la  t4, L.0.8
  lw t4, 0(t4)
  mv  s5, t4
  rem t5, s4, s5
  mv  s4, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s4, 0(t4)
  #Load  
  la  t4, s.0.10
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #Load  
  la  t4, sum.0.11
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s3, 0(t4)
  #Load  
  la  t4, t.0.2
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  li  t6, 1
  add t5, s4, t6
  mv  s4, t5
  rem t5, s4, s5
  mv  s4, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s0, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s4, 0(t4)
  j return9

if.else2.0.12:
  j if.end2.0.12

if.end2.0.12:
  xor t5, s1, s2
  seqz  t5, t5
  mv  s3, t5
  j _phi.if.end2.0.12

if.then2.3.13:
  #Load  
  la  t4, sum.0.11
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_45
  call  square
  mv  s4, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s5, t4
  rem t5, s4, s5
  mv  s4, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s5, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s4, 0(t4)
  #Load  
  la  t4, b.0.0
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  li  t6, 0
  slt t5, t6, s3
  mv  s3, t5
  j _phi.if.then2.3.13

if.then4.0.14:
  #Load  
  la  t4, flag.0.9
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 1
  sw t5, 0(t4)
  #Load  
  la  t4, sum.0.11
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  #Load  
  la  t4, s.0.10
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  li  t5, 0
  add t6, t4, t5
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s3, 0(t4)
  j for.cond6.0.9

for.cond6.0.9:
  #Load  
  la  t4, L.0.8
  lw t4, 0(t4)
  mv  s4, t4
  slt t5, s3, s4
  mv  s4, t5
  j _phi.for.cond6.0.9

for.body6.0.9:
  #Load  
  la  t4, s.0.10
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s5, t6
  #Load  
  lw t4, 0(t4)
  mv  s5, t4
  li  t6, 1
  sub t5, s3, t6
  mv  s6, t5
  #getElement
  slli s6, s6, 2
  add t6, t4, s6
  mv  s5, t6
  #Load  
  lw t4, 0(t4)
  mv  s5, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_79
  call  square
  mv  s5, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s6, t4
  rem t5, s5, s6
  mv  s5, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  mv  s4, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s4, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s5, 0(t4)
  j for.inc6.0.9

for.inc6.0.9:
  li  t6, 1
  add t5, s3, t6
  mv  s3, t5
  j for.cond6.0.9

for.end6.0.9:
  j if.end4.0.14

if.else4.0.14:
  j if.end4.0.14

if.end4.0.14:
  j return9

if.else2.3.13:
  j if.end2.3.13

if.end2.3.13:
  #Load  
  la  t4, t.0.2
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s3, t6
  #Load  
  lw t4, 0(t4)
  mv  s3, t4
  li  t6, 0
  slt t5, t6, s3
  mv  s3, t5
  j _phi.if.end2.3.13

if.then2.6.15:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %o
  call  pushdown
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j if.end2.6.15

if.else2.6.15:
  j if.end2.6.15

if.end2.6.15:
  li  t6, 2
  mul t5, s0, t6
  mv  s3, t5
  li  t6, 2
  mul t5, s0, t6
  mv  s4, t5
  li  t6, 1
  add t5, s4, t6
  mv  s4, t5
  add t5, s1, s2
  mv  s5, t5
  li  t6, 2
  div t5, s5, t6
  mv  s5, t5
  #Load  
  la  t4, pl.0.40
  lw t4, 0(t4)
  mv  s6, t4
  slt t5, s5, s6
  xori t5, t5, 1
  mv  s6, t5
  j _phi.if.end2.6.15

if.then2.9.16:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_96
  # %l
  # %_102
  call  update
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j if.end2.9.16

if.else2.9.16:
  j if.end2.9.16

if.end2.9.16:
  #Load  
  la  t4, pr.0.41
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 1
  add t5, s5, t6
  mv  s6, t5
  slt t5, s1, s6
  xori t5, t5, 1
  mv  s1, t5
  j _phi.if.end2.9.16

if.then2.12.17:
  li  t6, 1
  add t5, s5, t6
  mv  s1, t5
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_98
  # %_114
  # %r
  call  update
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j if.end2.12.17

if.else2.12.17:
  j if.end2.12.17

if.end2.12.17:
  #Load  
  la  t4, sum.0.11
  lw t4, 0(t4)
  mv  s1, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  add t5, s2, s6
  mv  s2, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s1, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s2, 0(t4)
  #Load  
  la  t4, flag.0.9
  lw t4, 0(t4)
  mv  s1, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  and t5, s2, s6
  mv  s2, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s2, 0(t4)
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s1, t6
  #Load  
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 0
  slt t5, t6, s1
  mv  s1, t5
  j _phi.if.end2.12.17

if.then2.15.18:
  j for.cond4.0.10

for.cond4.0.10:
  #Load  
  la  t4, L.0.8
  lw t4, 0(t4)
  mv  s2, t4
  slt t5, s1, s2
  mv  s2, t5
  j _phi.for.cond4.0.10

for.body4.0.10:
  #Load  
  la  t4, s.0.10
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  #Load  
  la  t4, now.0.1
  lw t4, 0(t4)
  mv  s7, t4
  #getElement
  slli s3, s3, 2
  add t6, t4, s3
  mv  s8, t6
  #Load  
  lw t4, 0(t4)
  mv  s8, t4
  add t5, s1, s8
  mv  s8, t5
  #Load  
  la  t4, L.0.8
  lw t4, 0(t4)
  mv  s9, t4
  rem t5, s8, s9
  mv  s8, t5
  #getElement
  slli s8, s8, 2
  add t6, t4, s8
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s8, t6
  #Load  
  lw t4, 0(t4)
  mv  s8, t4
  #getElement
  slli s4, s4, 2
  add t6, t4, s4
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  add t5, s1, s7
  mv  s7, t5
  rem t5, s7, s9
  mv  s7, t5
  #getElement
  slli s7, s7, 2
  add t6, t4, s7
  mv  s7, t6
  #Load  
  lw t4, 0(t4)
  mv  s7, t4
  add t5, s6, s7
  mv  s6, t5
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s6, 0(t4)
  j for.inc4.0.10

for.inc4.0.10:
  li  t6, 1
  add t5, s1, t6
  mv  s1, t5
  j for.cond4.0.10

for.end4.0.10:
  #Load  
  la  t4, now.0.1
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s0, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  li  t5, 0
  sw t5, 0(t4)
  j if.end2.15.18

if.else2.15.18:
  j if.end2.15.18

if.end2.15.18:
  j return9

_phi.log.lhs11:
  beqz  s3, .false22
  j log.rhs11
  .false22:
  j log.end11

_phi.log.end11:
  beqz  s3, .false23
  j log.rhs10
  .false23:
  j log.end10

_phi.log.end10:
  beqz  s3, .false24
  j if.then2.0.12
  .false24:
  j if.else2.0.12

_phi.if.end2.0.12:
  beqz  s3, .false25
  j if.then2.3.13
  .false25:
  j if.else2.3.13

_phi.if.then2.3.13:
  beqz  s3, .false26
  j if.then4.0.14
  .false26:
  j if.else4.0.14

_phi.for.cond6.0.9:
  beqz  s4, .false27
  j for.body6.0.9
  .false27:
  j for.end6.0.9

_phi.if.end2.3.13:
  beqz  s3, .false28
  j if.then2.6.15
  .false28:
  j if.else2.6.15

_phi.if.end2.6.15:
  beqz  s6, .false29
  j if.then2.9.16
  .false29:
  j if.else2.9.16

_phi.if.end2.9.16:
  beqz  s1, .false30
  j if.then2.12.17
  .false30:
  j if.else2.12.17

_phi.if.end2.12.17:
  beqz  s1, .false31
  j if.then2.15.18
  .false31:
  j if.else2.15.18

_phi.for.cond4.0.10:
  beqz  s2, .false32
  j for.body4.0.10
  .false32:
  j for.end4.0.10

_phi.return9:
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return9:
  j _phi.return9



  .globl query
query:
  sw ra, -4(sp)
  addi sp, sp, -48
  j log.lhs19

log.lhs19:
  #Load  
  la  t4, pl.0.40
  lw t4, 0(t4)
  mv  s3, t4
  slt t5, s1, s3
  xori t5, t5, 1
  mv  s3, t5
  j _phi.log.lhs19

log.rhs19:
  #Load  
  la  t4, pr.0.41
  lw t4, 0(t4)
  mv  s4, t4
  slt t5, s4, s2
  xori t5, t5, 1
  mv  s4, t5
  j log.end19

log.end19:
  beqz  s3, .false33
  mv  s3, s4
  j  .end33
  .false33:
  mv  s3, s3
  j  .end33
  .end33:
  j _phi.log.end19

if.then2.0.20:
  #Load  
  la  t4, sum.0.11
  lw t4, 0(t4)
  mv  s3, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s0, t6
  #Load  
  lw t4, 0(t4)
  mv  s0, t4
  j return10

if.else2.0.20:
  j if.end2.0.20

if.end2.0.20:
  li  t6, 2
  mul t5, s0, t6
  mv  s3, t5
  li  t6, 2
  mul t5, s0, t6
  mv  s4, t5
  li  t6, 1
  add t5, s4, t6
  mv  s4, t5
  add t5, s1, s2
  mv  s5, t5
  li  t6, 2
  div t5, s5, t6
  mv  s5, t5
  #Load  
  la  t4, t.0.2
  lw t4, 0(t4)
  mv  s6, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s6, t6
  #Load  
  lw t4, 0(t4)
  mv  s6, t4
  li  t6, 0
  slt t5, t6, s6
  mv  s6, t5
  j _phi.if.end2.0.20

if.then2.3.21:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %o
  call  pushdown
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j if.end2.3.21

if.else2.3.21:
  j if.end2.3.21

if.end2.3.21:
  #Load  
  la  t4, pl.0.40
  lw t4, 0(t4)
  mv  s0, t4
  slt t5, s5, s0
  xori t5, t5, 1
  mv  s0, t5
  j _phi.if.end2.3.21

if.then2.6.22:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_12
  # %l
  # %_18
  call  query
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  li  t5, 0
  add t5, t5, s0
  mv  s0, t5
  #Load  
  la  t4, MOD.0.20
  lw t4, 0(t4)
  mv  s1, t4
  rem t5, s0, s1
  mv  s0, t5
  j if.end2.6.22

if.else2.6.22:
  j if.end2.6.22

if.end2.6.22:
  #Load  
  la  t4, pr.0.41
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 1
  add t5, s5, t6
  mv  s6, t5
  slt t5, s1, s6
  xori t5, t5, 1
  mv  s1, t5
  j _phi.if.end2.6.22

if.then2.9.23:
  li  t6, 1
  add t5, s5, t6
  mv  s1, t5
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_14
  # %_42
  # %r
  call  query
  mv  s1, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  add t5, s0, s1
  mv  s0, t5
  #Load  
  la  t4, MOD.0.20
  lw t4, 0(t4)
  mv  s1, t4
  rem t5, s0, s1
  mv  s0, t5
  j if.end2.9.23

if.else2.9.23:
  j if.end2.9.23

if.end2.9.23:
  j return10

_phi.log.lhs19:
  beqz  s3, .false34
  j log.rhs19
  .false34:
  j log.end19

_phi.log.end19:
  beqz  s3, .false35
  j if.then2.0.20
  .false35:
  j if.else2.0.20

_phi.if.end2.0.20:
  beqz  s6, .false36
  j if.then2.3.21
  .false36:
  j if.else2.3.21

_phi.if.end2.3.21:
  beqz  s0, .false37
  j if.then2.6.22
  .false37:
  j if.else2.6.22

_phi.if.end2.6.22:
  beqz  s1, .false38
  j if.then2.9.23
  .false38:
  j if.else2.9.23

_phi.return10:
  mv  a0, s0
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return10:
  j _phi.return10



  .globl __init__
__init__:
  sw ra, -4(sp)
  addi sp, sp, -48
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 500005
  li  a0, 500005
  call  _arr_init
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, b.0.0
  sw s0, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 500005
  li  a0, 500005
  call  _arr_init
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, now.0.1
  sw s0, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 500005
  li  a0, 500005
  call  _arr_init
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, t.0.2
  sw s0, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 200005
  li  a0, 200005
  call  _arr_init
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, a.0.3
  sw s0, 0(t4)
  #Store  
  la  t4, L.0.8
  li  t5, 1
  sw t5, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 500005
  li  a0, 500005
  call  _arr_init
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, flag.0.9
  sw s0, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 500005
  li  a0, 500005
  call  _arr_init
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j for.cond.0

for.cond.0:
  li  t6, 500005
  slt t5, s1, t6
  mv  s2, t5
  j _phi.for.cond.0

for.body.0:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 80
  li  a0, 80
  call  _arr_init
  mv  s2, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #getElement
  slli s1, s1, 2
  add t6, t4, s1
  mv  s3, t6
  #Store  
  sw s2, 0(t4)
  j for.inc.0

for.inc.0:
  li  t6, 1
  add t5, s1, t6
  mv  s1, t5
  j for.cond.0

for.end.0:
  #Store  
  la  t4, s.0.10
  sw s0, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 500005
  li  a0, 500005
  call  _arr_init
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, sum.0.11
  sw s0, 0(t4)
  #Store  
  la  t4, ans.0.12
  li  t5, 0
  sw t5, 0(t4)
  #Store  
  la  t4, aa.0.18
  li  t5, 13131
  sw t5, 0(t4)
  #Store  
  la  t4, bb.0.19
  li  t5, 5353
  sw t5, 0(t4)
  li  t5, 2
  li  t6, 14
  sll t5, t5, t6
  mv  s0, t5
  li  t6, 7
  sub t5, s0, t6
  mv  s0, t5
  #Store  
  la  t4, MOD.0.20
  sw s0, 0(t4)
  #Store  
  la  t4, no.0.21
  li  t5, 1
  sw t5, 0(t4)
  #Store  
  la  t4, pl.0.40
  li  t5, 0
  sw t5, 0(t4)
  #Store  
  la  t4, pr.0.41
  li  t5, 0
  sw t5, 0(t4)
  j return0

_phi.for.cond.0:
  beqz  s2, .false39
  j for.body.0
  .false39:
  j for.end.0

_phi.return0:
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return0:
  j _phi.return0



  .globl main
main:
  sw ra, -4(sp)
  addi sp, sp, -48
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  call  __init__
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  call  getInt
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, n.0.4
  sw s0, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  call  getInt
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, m.0.5
  sw s0, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  call  getInt
  mv  s0, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, p.0.6
  sw s0, 0(t4)
  j for.cond2.0.11

for.cond2.0.11:
  #Load  
  la  t4, n.0.4
  lw t4, 0(t4)
  mv  s1, t4
  slt t5, s1, s0
  xori t5, t5, 1
  mv  s1, t5
  j _phi.for.cond2.0.11

for.body2.0.11:
  #Load  
  la  t4, p.0.6
  lw t4, 0(t4)
  mv  s1, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 0
  li  a0, 0
  # %_6
  call  RandRange
  mv  s1, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Load  
  la  t4, a.0.3
  lw t4, 0(t4)
  mv  s2, t4
  #getElement
  slli s0, s0, 2
  add t6, t4, s0
  mv  s2, t6
  #Load  
  lw t4, 0(t4)
  #Store  
  sw s1, 0(t4)
  j for.inc2.0.11

for.inc2.0.11:
  li  t6, 1
  add t5, s0, t6
  mv  s0, t5
  j for.cond2.0.11

for.end2.0.11:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  call  init
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Load  
  la  t4, n.0.4
  lw t4, 0(t4)
  mv  s1, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 1
  li  a0, 1
  # 1
  li  a1, 1
  # %_14
  call  build
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j while.cond2.1.12

while.cond2.1.12:
  #Load  
  la  t4, m.0.5
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 0
  slt t5, t6, s1
  mv  s1, t5
  j _phi.while.cond2.1.12

while.body2.1.12:
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  call  Rand
  mv  s1, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  li  t6, 2
  rem t5, s1, t6
  mv  s1, t5
  #Store  
  la  t4, op.0.7
  sw s1, 0(t4)
  #Load  
  la  t4, n.0.4
  lw t4, 0(t4)
  mv  s1, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 1
  li  a0, 1
  # %_19
  call  RandRange
  mv  s2, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, pl.0.40
  sw s2, 0(t4)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 1
  li  a0, 1
  # %_19
  call  RandRange
  mv  s1, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, pr.0.41
  sw s1, 0(t4)
  j while.cond4.0.13

while.cond4.0.13:
  #Load  
  la  t4, pr.0.41
  lw t4, 0(t4)
  mv  s1, t4
  #Load  
  la  t4, pl.0.40
  lw t4, 0(t4)
  mv  s2, t4
  slt t5, s2, s1
  xori t5, t5, 1
  mv  s1, t5
  j _phi.while.cond4.0.13

while.body4.0.13:
  #Load  
  la  t4, n.0.4
  lw t4, 0(t4)
  mv  s1, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 1
  li  a0, 1
  # %_25
  call  RandRange
  mv  s1, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Store  
  la  t4, pr.0.41
  sw s1, 0(t4)
  j while.cond4.0.13

while.end4.0.13:
  #Load  
  la  t4, op.0.7
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 0
  xor t5, s1, t6
  seqz  t5, t5
  mv  s1, t5
  j _phi.while.end4.0.13

if.then4.1.24:
  #Load  
  la  t4, n.0.4
  lw t4, 0(t4)
  mv  s1, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 1
  li  a0, 1
  # 1
  li  a1, 1
  # %_29
  call  update
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j if.end4.1.24

if.else4.1.24:
  j if.end4.1.24

if.end4.1.24:
  #Load  
  la  t4, op.0.7
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 1
  xor t5, s1, t6
  seqz  t5, t5
  mv  s1, t5
  j _phi.if.end4.1.24

if.then4.4.25:
  #Load  
  la  t4, ans.0.12
  lw t4, 0(t4)
  mv  s1, t4
  #Load  
  la  t4, n.0.4
  lw t4, 0(t4)
  mv  s2, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # 1
  li  a0, 1
  # 1
  li  a1, 1
  # %_33
  call  query
  mv  s2, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  add t5, s1, s2
  mv  s1, t5
  #Load  
  la  t4, MOD.0.20
  lw t4, 0(t4)
  mv  s2, t4
  rem t5, s1, s2
  mv  s1, t5
  #Store  
  la  t4, ans.0.12
  sw s1, 0(t4)
  j if.end4.4.25

if.else4.4.25:
  j if.end4.4.25

if.end4.4.25:
  #Load  
  la  t4, m.0.5
  lw t4, 0(t4)
  mv  s1, t4
  li  t6, 1
  sub t5, s1, t6
  mv  s1, t5
  #Store  
  la  t4, m.0.5
  sw s1, 0(t4)
  j while.cond2.1.12

while.end2.1.12:
  #Load  
  la  t4, ans.0.12
  lw t4, 0(t4)
  mv  s1, t4
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_40
  call  toString
  mv  s1, a0
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  #Call  
  sw a0, 12(sp)
  sw a1, 16(sp)
  sw a2, 20(sp)
  sw a3, 24(sp)
  sw a4, 28(sp)
  sw a5, 32(sp)
  sw a6, 36(sp)
  sw a7, 40(sp)
  # %_41
  call  print
  lw a0, 12(sp)
  lw a1, 16(sp)
  lw a2, 20(sp)
  lw a3, 24(sp)
  lw a4, 28(sp)
  lw a5, 32(sp)
  lw a6, 36(sp)
  lw a7, 40(sp)
  j return11

_phi.for.cond2.0.11:
  beqz  s1, .false40
  j for.body2.0.11
  .false40:
  j for.end2.0.11

_phi.while.cond2.1.12:
  beqz  s1, .false41
  j while.body2.1.12
  .false41:
  j while.end2.1.12

_phi.while.cond4.0.13:
  beqz  s1, .false42
  j while.body4.0.13
  .false42:
  j while.end4.0.13

_phi.while.end4.0.13:
  beqz  s1, .false43
  j if.then4.1.24
  .false43:
  j if.else4.1.24

_phi.if.end4.1.24:
  beqz  s1, .false44
  j if.then4.4.25
  .false44:
  j if.else4.4.25

_phi.return11:
  li  t5, 0
  mv  a0, t5
  lw ra, 44(sp)
  addi sp, sp, 48
  ret   

return11:
  j _phi.return11



 .section .data
  .globl b.0.0
  .p2align 2
b.0.0:
  .word 0
.size @b.0.0, 4

  .globl now.0.1
  .p2align 2
now.0.1:
  .word 0
.size @now.0.1, 4

  .globl t.0.2
  .p2align 2
t.0.2:
  .word 0
.size @t.0.2, 4

  .globl a.0.3
  .p2align 2
a.0.3:
  .word 0
.size @a.0.3, 4

  .globl n.0.4
  .p2align 2
n.0.4:
  .word 0
.size @n.0.4, 4

  .globl m.0.5
  .p2align 2
m.0.5:
  .word 0
.size @m.0.5, 4

  .globl p.0.6
  .p2align 2
p.0.6:
  .word 0
.size @p.0.6, 4

  .globl op.0.7
  .p2align 2
op.0.7:
  .word 0
.size @op.0.7, 4

  .globl L.0.8
  .p2align 2
L.0.8:
  .word 0
.size @L.0.8, 4

  .globl flag.0.9
  .p2align 2
flag.0.9:
  .word 0
.size @flag.0.9, 4

  .globl s.0.10
  .p2align 2
s.0.10:
  .word 0
.size @s.0.10, 4

  .globl sum.0.11
  .p2align 2
sum.0.11:
  .word 0
.size @sum.0.11, 4

  .globl ans.0.12
  .p2align 2
ans.0.12:
  .word 0
.size @ans.0.12, 4

  .globl aa.0.18
  .p2align 2
aa.0.18:
  .word 0
.size @aa.0.18, 4

  .globl bb.0.19
  .p2align 2
bb.0.19:
  .word 0
.size @bb.0.19, 4

  .globl MOD.0.20
  .p2align 2
MOD.0.20:
  .word 0
.size @MOD.0.20, 4

  .globl no.0.21
  .p2align 2
no.0.21:
  .word 0
.size @no.0.21, 4

  .globl pl.0.40
  .p2align 2
pl.0.40:
  .word 0
.size @pl.0.40, 4

  .globl pr.0.41
  .p2align 2
pr.0.41:
  .word 0
.size @pr.0.41, 4

 .section .rodata
.p2align 2
.str.true:
  .asciz "true"
  .size .str.true, 0

.p2align 2
.str.false:
  .asciz "false"
  .size .str.false, 0

