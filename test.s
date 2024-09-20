  .text
  .globl vector.vector
vector.vector:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -80
  addi t0, sp, 48
  sw t0, 44(sp)
  #Store  
  lw t4, 44(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 44(sp)
  lw t4, 0(t4)
  sw t4, 52(sp)
  j return1

return1:
  lw ra, 76(sp)
  lw t1, 72(sp)
  lw t2, 68(sp)
  lw t3, 64(sp)
  lw t4, 60(sp)
  lw t5, 56(sp)
  addi sp, sp, 80
  ret   



  .globl vector.init
vector.init:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -192
  addi t0, sp, 44
  sw t0, 40(sp)
  #Store  
  lw t4, 40(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 40(sp)
  lw t4, 0(t4)
  sw t4, 48(sp)
  addi t0, sp, 56
  sw t0, 52(sp)
  #Store  
  lw t4, 52(sp)
  sw a1, 0(t4)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 60(sp)
  lw t0, 60(sp)
  li  t1, 0
  xor t2, t0, t1
  seqz  t2, t2
  sb t2, 64(sp)
  addi t0, sp, 72
  sw t0, 68(sp)
  lb t0, 64(sp)
  beqz  t0, .false0
  j if.then2.0.0
  .false0:
  j if.else2.0.0

if.then2.0.0:
  j return2

if.else2.0.0:
  j if.end2.0.0

if.end2.0.0:
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 76(sp)
  #Call  
  # %2
  lw a0, 76(sp)
  call  _arr_size
  sw a0, 80(sp)
  #Call  
  # %3
  lw a0, 80(sp)
  call  _arr_init
  sw a0, 84(sp)
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 88(sp)
  #getElement
  lw t4, 88(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 92(sp)
  #Load  
  lw t4, 92(sp)
  lw t4, 0(t4)
  sw t4, 96(sp)
  #Store  
  lw t4, 92(sp)
  lw t3, 84(sp)
  sw t3, 0(t4)
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 100(sp)
  #Store  
  lw t4, 68(sp)
  li  t0, 0
  sw t0, 0(t4)
  j for.cond2.3.0

for.cond2.3.0:
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 104(sp)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 108(sp)
  #Call  
  # %10
  lw a0, 108(sp)
  call  _arr_size
  sw a0, 112(sp)
  lw t0, 104(sp)
  lw t1, 112(sp)
  slt t2, t0, t1
  sb t2, 116(sp)
  lb t0, 116(sp)
  beqz  t0, .false1
  j for.body2.3.0
  .false1:
  j for.end2.3.0

for.body2.3.0:
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 120(sp)
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 124(sp)
  #getElement
  lw t4, 120(sp)
  lw t0, 124(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 128(sp)
  #Load  
  lw t4, 128(sp)
  lw t4, 0(t4)
  sw t4, 132(sp)
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 136(sp)
  #getElement
  lw t4, 136(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 140(sp)
  #Load  
  lw t4, 140(sp)
  lw t4, 0(t4)
  sw t4, 144(sp)
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 148(sp)
  #getElement
  lw t4, 144(sp)
  lw t0, 148(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 152(sp)
  #Load  
  lw t4, 152(sp)
  lw t4, 0(t4)
  sw t4, 156(sp)
  #Store  
  lw t4, 152(sp)
  lw t3, 132(sp)
  sw t3, 0(t4)
  j for.inc2.3.0

for.inc2.3.0:
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 160(sp)
  lw t0, 160(sp)
  li  t1, 1
  add t2, t0, t1
  sw t2, 164(sp)
  #Store  
  lw t4, 68(sp)
  lw t3, 164(sp)
  sw t3, 0(t4)
  j for.cond2.3.0

for.end2.3.0:
  j return2

return2:
  lw ra, 188(sp)
  lw t1, 184(sp)
  lw t2, 180(sp)
  lw t3, 176(sp)
  lw t4, 172(sp)
  lw t5, 168(sp)
  addi sp, sp, 192
  ret   



  .globl vector.getDim
vector.getDim:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -112
  addi t0, sp, 36
  sw t0, 32(sp)
  addi t0, sp, 44
  sw t0, 40(sp)
  #Store  
  lw t4, 40(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 40(sp)
  lw t4, 0(t4)
  sw t4, 48(sp)
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 52(sp)
  #getElement
  lw t4, 52(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 56(sp)
  #Load  
  lw t4, 56(sp)
  lw t4, 0(t4)
  sw t4, 60(sp)
  lw t0, 60(sp)
  li  t1, 0
  xor t2, t0, t1
  seqz  t2, t2
  sb t2, 64(sp)
  lb t0, 64(sp)
  beqz  t0, .false2
  j if.then2.0.1
  .false2:
  j if.else2.0.1

if.then2.0.1:
  #Store  
  lw t4, 32(sp)
  li  t0, 0
  sw t0, 0(t4)
  j return3

if.else2.0.1:
  j if.end2.0.1

if.end2.0.1:
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 68(sp)
  #getElement
  lw t4, 68(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 72(sp)
  #Load  
  lw t4, 72(sp)
  lw t4, 0(t4)
  sw t4, 76(sp)
  #Call  
  # %6
  lw a0, 76(sp)
  call  _arr_size
  sw a0, 80(sp)
  #Store  
  lw t4, 32(sp)
  lw t3, 80(sp)
  sw t3, 0(t4)
  j return3

return3:
  #Load  
  lw t4, 32(sp)
  lw t4, 0(t4)
  sw t4, 84(sp)
  lw a0, 84(sp)
  lw ra, 108(sp)
  lw t1, 104(sp)
  lw t2, 100(sp)
  lw t3, 96(sp)
  lw t4, 92(sp)
  lw t5, 88(sp)
  addi sp, sp, 112
  ret   



  .globl vector.dot
vector.dot:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -192
  addi t0, sp, 40
  sw t0, 36(sp)
  addi t0, sp, 48
  sw t0, 44(sp)
  #Store  
  lw t4, 44(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 44(sp)
  lw t4, 0(t4)
  sw t4, 52(sp)
  addi t0, sp, 60
  sw t0, 56(sp)
  #Store  
  lw t4, 56(sp)
  sw a1, 0(t4)
  addi t0, sp, 68
  sw t0, 64(sp)
  #Store  
  lw t4, 64(sp)
  li  t0, 0
  sw t0, 0(t4)
  addi t0, sp, 76
  sw t0, 72(sp)
  #Store  
  lw t4, 72(sp)
  li  t0, 0
  sw t0, 0(t4)
  j while.cond2.0.1

while.cond2.0.1:
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 80(sp)
  #Call  
  # %this.copy
  lw a0, 52(sp)
  call  vector.getDim
  sw a0, 84(sp)
  lw t0, 80(sp)
  lw t1, 84(sp)
  slt t2, t0, t1
  sb t2, 88(sp)
  lb t0, 88(sp)
  beqz  t0, .false3
  j while.body2.0.1
  .false3:
  j while.end2.0.1

while.body2.0.1:
  #getElement
  lw t4, 52(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 92(sp)
  #getElement
  lw t4, 92(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 96(sp)
  #Load  
  lw t4, 96(sp)
  lw t4, 0(t4)
  sw t4, 100(sp)
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 104(sp)
  #getElement
  lw t4, 100(sp)
  lw t0, 104(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 108(sp)
  #Load  
  lw t4, 108(sp)
  lw t4, 0(t4)
  sw t4, 112(sp)
  #Load  
  lw t4, 56(sp)
  lw t4, 0(t4)
  sw t4, 116(sp)
  #getElement
  lw t4, 116(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 120(sp)
  #getElement
  lw t4, 120(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 124(sp)
  #Load  
  lw t4, 124(sp)
  lw t4, 0(t4)
  sw t4, 128(sp)
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 132(sp)
  #getElement
  lw t4, 128(sp)
  lw t0, 132(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 136(sp)
  #Load  
  lw t4, 136(sp)
  lw t4, 0(t4)
  sw t4, 140(sp)
  lw t0, 112(sp)
  lw t1, 140(sp)
  mul t2, t0, t1
  sw t2, 144(sp)
  #Load  
  lw t4, 72(sp)
  lw t4, 0(t4)
  sw t4, 148(sp)
  #Store  
  lw t4, 72(sp)
  lw t3, 144(sp)
  sw t3, 0(t4)
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 152(sp)
  lw t0, 152(sp)
  li  t1, 1
  add t2, t0, t1
  sw t2, 156(sp)
  #Store  
  lw t4, 64(sp)
  lw t3, 156(sp)
  sw t3, 0(t4)
  j while.cond2.0.1

while.end2.0.1:
  #Load  
  lw t4, 72(sp)
  lw t4, 0(t4)
  sw t4, 160(sp)
  #Store  
  lw t4, 36(sp)
  lw t3, 160(sp)
  sw t3, 0(t4)
  j return4

return4:
  #Load  
  lw t4, 36(sp)
  lw t4, 0(t4)
  sw t4, 164(sp)
  lw a0, 164(sp)
  lw ra, 188(sp)
  lw t1, 184(sp)
  lw t2, 180(sp)
  lw t3, 176(sp)
  lw t4, 172(sp)
  lw t5, 168(sp)
  addi sp, sp, 192
  ret   



  .globl vector.scalarInPlaceMultiply
vector.scalarInPlaceMultiply:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -192
  addi t0, sp, 36
  sw t0, 32(sp)
  addi t0, sp, 44
  sw t0, 40(sp)
  #Store  
  lw t4, 40(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 40(sp)
  lw t4, 0(t4)
  sw t4, 48(sp)
  addi t0, sp, 56
  sw t0, 52(sp)
  #Store  
  lw t4, 52(sp)
  sw a1, 0(t4)
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 60(sp)
  #getElement
  lw t4, 60(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 64(sp)
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 68(sp)
  lw t0, 68(sp)
  li  t1, 0
  xor t2, t0, t1
  seqz  t2, t2
  sb t2, 72(sp)
  addi t0, sp, 80
  sw t0, 76(sp)
  lb t0, 72(sp)
  beqz  t0, .false4
  j if.then2.0.2
  .false4:
  j if.else2.0.2

if.then2.0.2:
  #Store  
  lw t4, 32(sp)
  li  t0, 0
  sw t0, 0(t4)
  j return5

if.else2.0.2:
  j if.end2.0.2

if.end2.0.2:
  #Load  
  lw t4, 76(sp)
  lw t4, 0(t4)
  sw t4, 84(sp)
  #Store  
  lw t4, 76(sp)
  li  t0, 0
  sw t0, 0(t4)
  j for.cond2.3.2

for.cond2.3.2:
  #Load  
  lw t4, 76(sp)
  lw t4, 0(t4)
  sw t4, 88(sp)
  #Call  
  # %this.copy
  lw a0, 48(sp)
  call  vector.getDim
  sw a0, 92(sp)
  lw t0, 88(sp)
  lw t1, 92(sp)
  slt t2, t0, t1
  sb t2, 96(sp)
  lb t0, 96(sp)
  beqz  t0, .false5
  j for.body2.3.2
  .false5:
  j for.end2.3.2

for.body2.3.2:
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 100(sp)
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 104(sp)
  #getElement
  lw t4, 104(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 108(sp)
  #Load  
  lw t4, 108(sp)
  lw t4, 0(t4)
  sw t4, 112(sp)
  #Load  
  lw t4, 76(sp)
  lw t4, 0(t4)
  sw t4, 116(sp)
  #getElement
  lw t4, 112(sp)
  lw t0, 116(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 120(sp)
  #Load  
  lw t4, 120(sp)
  lw t4, 0(t4)
  sw t4, 124(sp)
  lw t0, 100(sp)
  lw t1, 124(sp)
  mul t2, t0, t1
  sw t2, 128(sp)
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 132(sp)
  #getElement
  lw t4, 132(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 136(sp)
  #Load  
  lw t4, 136(sp)
  lw t4, 0(t4)
  sw t4, 140(sp)
  #Load  
  lw t4, 76(sp)
  lw t4, 0(t4)
  sw t4, 144(sp)
  #getElement
  lw t4, 140(sp)
  lw t0, 144(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 148(sp)
  #Load  
  lw t4, 148(sp)
  lw t4, 0(t4)
  sw t4, 152(sp)
  #Store  
  lw t4, 148(sp)
  lw t3, 128(sp)
  sw t3, 0(t4)
  j for.inc2.3.2

for.inc2.3.2:
  #Load  
  lw t4, 76(sp)
  lw t4, 0(t4)
  sw t4, 156(sp)
  lw t0, 156(sp)
  li  t1, 1
  add t2, t0, t1
  sw t2, 160(sp)
  #Store  
  lw t4, 76(sp)
  lw t3, 160(sp)
  sw t3, 0(t4)
  j for.cond2.3.2

for.end2.3.2:
  #Store  
  lw t4, 32(sp)
  lw t3, 48(sp)
  sw t3, 0(t4)
  j return5

return5:
  #Load  
  lw t4, 32(sp)
  lw t4, 0(t4)
  sw t4, 164(sp)
  lw a0, 164(sp)
  lw ra, 188(sp)
  lw t1, 184(sp)
  lw t2, 180(sp)
  lw t3, 176(sp)
  lw t4, 172(sp)
  lw t5, 168(sp)
  addi sp, sp, 192
  ret   



  .globl vector.add
vector.add:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -272
  addi t0, sp, 36
  sw t0, 32(sp)
  addi t0, sp, 44
  sw t0, 40(sp)
  #Store  
  lw t4, 40(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 40(sp)
  lw t4, 0(t4)
  sw t4, 48(sp)
  addi t0, sp, 56
  sw t0, 52(sp)
  #Store  
  lw t4, 52(sp)
  sw a1, 0(t4)
  addi t0, sp, 64
  sw t0, 60(sp)
  addi t0, sp, 72
  sw t0, 68(sp)
  j log.lhs3

log.lhs3:
  #Call  
  # %this.copy
  lw a0, 48(sp)
  call  vector.getDim
  sw a0, 76(sp)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 80(sp)
  #Call  
  # %1
  lw a0, 80(sp)
  call  vector.getDim
  sw a0, 84(sp)
  lw t0, 76(sp)
  lw t1, 84(sp)
  xor t2, t0, t1
  snez  t2, t2
  sb t2, 88(sp)
  lb t0, 88(sp)
  beqz  t0, .false6
  j log.end3
  .false6:
  j log.rhs3

log.rhs3:
  #Call  
  # %this.copy
  lw a0, 48(sp)
  call  vector.getDim
  sw a0, 92(sp)
  lw t0, 92(sp)
  li  t1, 0
  xor t2, t0, t1
  seqz  t2, t2
  sb t2, 96(sp)
  j log.end3

log.end3:
  lb t0, 88(sp)
  beqz  t0, .false7
  lb t1, 88(sp)
  sb t1, 100(sp)
  j  .end7
  .false7:
  lb t1, 96(sp)
  sb t1, 100(sp)
  j  .end7
  .end7:
  lb t0, 100(sp)
  beqz  t0, .false8
  j if.then2.0.4
  .false8:
  j if.else2.0.4

if.then2.0.4:
  #Store  
  lw t4, 32(sp)
  li  t0, 0
  sw t0, 0(t4)
  j return6

if.else2.0.4:
  j if.end2.0.4

if.end2.0.4:
  #Call  
  # 1
  li  a0, 1
  call  _malloc
  sw a0, 104(sp)
  #Call  
  # %7
  lw a0, 104(sp)
  call  vector.vector
  #Store  
  lw t4, 60(sp)
  lw t3, 104(sp)
  sw t3, 0(t4)
  #Call  
  # %this.copy
  lw a0, 48(sp)
  call  vector.getDim
  sw a0, 108(sp)
  #Call  
  # %8
  lw a0, 108(sp)
  call  _arr_init
  sw a0, 112(sp)
  #Load  
  lw t4, 60(sp)
  lw t4, 0(t4)
  sw t4, 116(sp)
  #getElement
  lw t4, 116(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 120(sp)
  #getElement
  lw t4, 120(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 124(sp)
  #Load  
  lw t4, 124(sp)
  lw t4, 0(t4)
  sw t4, 128(sp)
  #Store  
  lw t4, 124(sp)
  lw t3, 112(sp)
  sw t3, 0(t4)
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 132(sp)
  #Store  
  lw t4, 68(sp)
  li  t0, 0
  sw t0, 0(t4)
  j for.cond2.3.3

for.cond2.3.3:
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 136(sp)
  #Call  
  # %this.copy
  lw a0, 48(sp)
  call  vector.getDim
  sw a0, 140(sp)
  lw t0, 136(sp)
  lw t1, 140(sp)
  slt t2, t0, t1
  sb t2, 144(sp)
  lb t0, 144(sp)
  beqz  t0, .false9
  j for.body2.3.3
  .false9:
  j for.end2.3.3

for.body2.3.3:
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 148(sp)
  #getElement
  lw t4, 148(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 152(sp)
  #Load  
  lw t4, 152(sp)
  lw t4, 0(t4)
  sw t4, 156(sp)
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 160(sp)
  #getElement
  lw t4, 156(sp)
  lw t0, 160(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 164(sp)
  #Load  
  lw t4, 164(sp)
  lw t4, 0(t4)
  sw t4, 168(sp)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 172(sp)
  #getElement
  lw t4, 172(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 176(sp)
  #getElement
  lw t4, 176(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 180(sp)
  #Load  
  lw t4, 180(sp)
  lw t4, 0(t4)
  sw t4, 184(sp)
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 188(sp)
  #getElement
  lw t4, 184(sp)
  lw t0, 188(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 192(sp)
  #Load  
  lw t4, 192(sp)
  lw t4, 0(t4)
  sw t4, 196(sp)
  lw t0, 168(sp)
  lw t1, 196(sp)
  add t2, t0, t1
  sw t2, 200(sp)
  #Load  
  lw t4, 60(sp)
  lw t4, 0(t4)
  sw t4, 204(sp)
  #getElement
  lw t4, 204(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 208(sp)
  #getElement
  lw t4, 208(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 212(sp)
  #Load  
  lw t4, 212(sp)
  lw t4, 0(t4)
  sw t4, 216(sp)
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 220(sp)
  #getElement
  lw t4, 216(sp)
  lw t0, 220(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 224(sp)
  #Load  
  lw t4, 224(sp)
  lw t4, 0(t4)
  sw t4, 228(sp)
  #Store  
  lw t4, 224(sp)
  lw t3, 200(sp)
  sw t3, 0(t4)
  j for.inc2.3.3

for.inc2.3.3:
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 232(sp)
  lw t0, 232(sp)
  li  t1, 1
  add t2, t0, t1
  sw t2, 236(sp)
  #Store  
  lw t4, 68(sp)
  lw t3, 236(sp)
  sw t3, 0(t4)
  j for.cond2.3.3

for.end2.3.3:
  #Load  
  lw t4, 60(sp)
  lw t4, 0(t4)
  sw t4, 240(sp)
  #Store  
  lw t4, 32(sp)
  lw t3, 240(sp)
  sw t3, 0(t4)
  j return6

return6:
  #Load  
  lw t4, 32(sp)
  lw t4, 0(t4)
  sw t4, 244(sp)
  lw a0, 244(sp)
  lw ra, 268(sp)
  lw t1, 264(sp)
  lw t2, 260(sp)
  lw t3, 256(sp)
  lw t4, 252(sp)
  lw t5, 248(sp)
  addi sp, sp, 272
  ret   



  .globl vector.set
vector.set:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -144
  addi t0, sp, 44
  sw t0, 40(sp)
  addi t0, sp, 52
  sw t0, 48(sp)
  #Store  
  lw t4, 48(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 48(sp)
  lw t4, 0(t4)
  sw t4, 56(sp)
  addi t0, sp, 64
  sw t0, 60(sp)
  #Store  
  lw t4, 60(sp)
  sw a1, 0(t4)
  addi t0, sp, 72
  sw t0, 68(sp)
  #Store  
  lw t4, 68(sp)
  sw a2, 0(t4)
  #Call  
  # %this.copy
  lw a0, 56(sp)
  call  vector.getDim
  sw a0, 76(sp)
  #Load  
  lw t4, 60(sp)
  lw t4, 0(t4)
  sw t4, 80(sp)
  lw t0, 76(sp)
  lw t1, 80(sp)
  slt t2, t0, t1
  sb t2, 84(sp)
  lb t0, 84(sp)
  beqz  t0, .false10
  j if.then2.0.5
  .false10:
  j if.else2.0.5

if.then2.0.5:
  #Store  
  lw t4, 40(sp)
  li  t0, 0
  sb t0, 0(t4)
  j return7

if.else2.0.5:
  j if.end2.0.5

if.end2.0.5:
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 88(sp)
  #getElement
  lw t4, 56(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 92(sp)
  #getElement
  lw t4, 92(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 96(sp)
  #Load  
  lw t4, 96(sp)
  lw t4, 0(t4)
  sw t4, 100(sp)
  #Load  
  lw t4, 60(sp)
  lw t4, 0(t4)
  sw t4, 104(sp)
  #getElement
  lw t4, 100(sp)
  lw t0, 104(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 108(sp)
  #Load  
  lw t4, 108(sp)
  lw t4, 0(t4)
  sw t4, 112(sp)
  #Store  
  lw t4, 108(sp)
  lw t3, 88(sp)
  sw t3, 0(t4)
  #Store  
  lw t4, 40(sp)
  li  t0, 1
  sb t0, 0(t4)
  j return7

return7:
  #Load  
  lw t4, 40(sp)
  lb t4, 0(t4)
  sb t4, 116(sp)
  lb a0, 116(sp)
  lw ra, 140(sp)
  lw t1, 136(sp)
  lw t2, 132(sp)
  lw t3, 128(sp)
  lw t4, 124(sp)
  lw t5, 120(sp)
  addi sp, sp, 144
  ret   



  .globl vector.tostring
vector.tostring:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -224
  addi t0, sp, 36
  sw t0, 32(sp)
  addi t0, sp, 44
  sw t0, 40(sp)
  #Store  
  lw t4, 40(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 40(sp)
  lw t4, 0(t4)
  sw t4, 48(sp)
  addi t0, sp, 56
  sw t0, 52(sp)
  #Store  
  lw t4, 52(sp)
  la  t0, .str.0
  sw t0, 0(t4)
  #Call  
  # %this.copy
  lw a0, 48(sp)
  call  vector.getDim
  sw a0, 60(sp)
  lw t0, 60(sp)
  li  t1, 0
  slt t2, t1, t0
  sb t2, 64(sp)
  addi t0, sp, 72
  sw t0, 68(sp)
  lb t0, 64(sp)
  beqz  t0, .false11
  j if.then2.0.6
  .false11:
  j if.else2.0.6

if.then2.0.6:
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 76(sp)
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 80(sp)
  #getElement
  lw t4, 80(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 84(sp)
  #Load  
  lw t4, 84(sp)
  lw t4, 0(t4)
  sw t4, 88(sp)
  #getElement
  lw t4, 88(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 92(sp)
  #Load  
  lw t4, 92(sp)
  lw t4, 0(t4)
  sw t4, 96(sp)
  #Call  
  # %7
  lw a0, 96(sp)
  call  toString
  sw a0, 100(sp)
  #Call  
  # %2
  lw a0, 76(sp)
  # %8
  lw a1, 100(sp)
  call  _add
  sw a0, 104(sp)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 108(sp)
  #Store  
  lw t4, 52(sp)
  lw t3, 104(sp)
  sw t3, 0(t4)
  j if.end2.0.6

if.else2.0.6:
  j if.end2.0.6

if.end2.0.6:
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 112(sp)
  #Store  
  lw t4, 68(sp)
  li  t0, 1
  sw t0, 0(t4)
  j for.cond2.3.4

for.cond2.3.4:
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 116(sp)
  #Call  
  # %this.copy
  lw a0, 48(sp)
  call  vector.getDim
  sw a0, 120(sp)
  lw t0, 116(sp)
  lw t1, 120(sp)
  slt t2, t0, t1
  sb t2, 124(sp)
  lb t0, 124(sp)
  beqz  t0, .false12
  j for.body2.3.4
  .false12:
  j for.end2.3.4

for.body2.3.4:
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 128(sp)
  #Call  
  # %15
  lw a0, 128(sp)
  # @.str.1
  la  a1, .str.1
  call  _add
  sw a0, 132(sp)
  #getElement
  lw t4, 48(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 136(sp)
  #getElement
  lw t4, 136(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 140(sp)
  #Load  
  lw t4, 140(sp)
  lw t4, 0(t4)
  sw t4, 144(sp)
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 148(sp)
  #getElement
  lw t4, 144(sp)
  lw t0, 148(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 152(sp)
  #Load  
  lw t4, 152(sp)
  lw t4, 0(t4)
  sw t4, 156(sp)
  #Call  
  # %22
  lw a0, 156(sp)
  call  toString
  sw a0, 160(sp)
  #Call  
  # %16
  lw a0, 132(sp)
  # %23
  lw a1, 160(sp)
  call  _add
  sw a0, 164(sp)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 168(sp)
  #Store  
  lw t4, 52(sp)
  lw t3, 164(sp)
  sw t3, 0(t4)
  j for.inc2.3.4

for.inc2.3.4:
  #Load  
  lw t4, 68(sp)
  lw t4, 0(t4)
  sw t4, 172(sp)
  lw t0, 172(sp)
  li  t1, 1
  add t2, t0, t1
  sw t2, 176(sp)
  #Store  
  lw t4, 68(sp)
  lw t3, 176(sp)
  sw t3, 0(t4)
  j for.cond2.3.4

for.end2.3.4:
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 180(sp)
  #Call  
  # %28
  lw a0, 180(sp)
  # @.str.2
  la  a1, .str.2
  call  _add
  sw a0, 184(sp)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 188(sp)
  #Store  
  lw t4, 52(sp)
  lw t3, 184(sp)
  sw t3, 0(t4)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 192(sp)
  #Store  
  lw t4, 32(sp)
  lw t3, 192(sp)
  sw t3, 0(t4)
  j return8

return8:
  #Load  
  lw t4, 32(sp)
  lw t4, 0(t4)
  sw t4, 196(sp)
  lw a0, 196(sp)
  lw ra, 220(sp)
  lw t1, 216(sp)
  lw t2, 212(sp)
  lw t3, 208(sp)
  lw t4, 204(sp)
  lw t5, 200(sp)
  addi sp, sp, 224
  ret   



  .globl vector.copy
vector.copy:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -240
  addi t0, sp, 48
  sw t0, 44(sp)
  addi t0, sp, 56
  sw t0, 52(sp)
  #Store  
  lw t4, 52(sp)
  sw a0, 0(t4)
  #Load  
  lw t4, 52(sp)
  lw t4, 0(t4)
  sw t4, 60(sp)
  addi t0, sp, 68
  sw t0, 64(sp)
  #Store  
  lw t4, 64(sp)
  sw a1, 0(t4)
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 72(sp)
  lw t0, 72(sp)
  li  t1, 0
  xor t2, t0, t1
  seqz  t2, t2
  sb t2, 76(sp)
  addi t0, sp, 84
  sw t0, 80(sp)
  lb t0, 76(sp)
  beqz  t0, .false13
  j if.then2.0.7
  .false13:
  j if.else2.0.7

if.then2.0.7:
  #Store  
  lw t4, 44(sp)
  li  t0, 0
  sb t0, 0(t4)
  j return9

if.else2.0.7:
  j if.end2.0.7

if.end2.0.7:
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 88(sp)
  #Call  
  # %2
  lw a0, 88(sp)
  call  vector.getDim
  sw a0, 92(sp)
  lw t0, 92(sp)
  li  t1, 0
  xor t2, t0, t1
  seqz  t2, t2
  sb t2, 96(sp)
  lb t0, 96(sp)
  beqz  t0, .false14
  j if.then2.3.8
  .false14:
  j if.else2.3.8

if.then2.3.8:
  #getElement
  lw t4, 60(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 100(sp)
  #getElement
  lw t4, 100(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 104(sp)
  #Load  
  lw t4, 104(sp)
  lw t4, 0(t4)
  sw t4, 108(sp)
  #Store  
  lw t4, 104(sp)
  li  t0, 0
  sw t0, 0(t4)
  j if.end2.3.8

if.else2.3.8:
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 112(sp)
  #Call  
  # %8
  lw a0, 112(sp)
  call  vector.getDim
  sw a0, 116(sp)
  #Call  
  # %9
  lw a0, 116(sp)
  call  _arr_init
  sw a0, 120(sp)
  #getElement
  lw t4, 60(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 124(sp)
  #getElement
  lw t4, 124(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 128(sp)
  #Load  
  lw t4, 128(sp)
  lw t4, 0(t4)
  sw t4, 132(sp)
  #Store  
  lw t4, 128(sp)
  lw t3, 120(sp)
  sw t3, 0(t4)
  #Load  
  lw t4, 80(sp)
  lw t4, 0(t4)
  sw t4, 136(sp)
  #Store  
  lw t4, 80(sp)
  li  t0, 0
  sw t0, 0(t4)
  j for.cond4.0.5

for.cond4.0.5:
  #Load  
  lw t4, 80(sp)
  lw t4, 0(t4)
  sw t4, 140(sp)
  #Call  
  # %this.copy
  lw a0, 60(sp)
  call  vector.getDim
  sw a0, 144(sp)
  lw t0, 140(sp)
  lw t1, 144(sp)
  slt t2, t0, t1
  sb t2, 148(sp)
  lb t0, 148(sp)
  beqz  t0, .false15
  j for.body4.0.5
  .false15:
  j for.end4.0.5

for.body4.0.5:
  #Load  
  lw t4, 64(sp)
  lw t4, 0(t4)
  sw t4, 152(sp)
  #getElement
  lw t4, 152(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 156(sp)
  #getElement
  lw t4, 156(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 160(sp)
  #Load  
  lw t4, 160(sp)
  lw t4, 0(t4)
  sw t4, 164(sp)
  #Load  
  lw t4, 80(sp)
  lw t4, 0(t4)
  sw t4, 168(sp)
  #getElement
  lw t4, 164(sp)
  lw t0, 168(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 172(sp)
  #Load  
  lw t4, 172(sp)
  lw t4, 0(t4)
  sw t4, 176(sp)
  #getElement
  lw t4, 60(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 180(sp)
  #getElement
  lw t4, 180(sp)
  li  t0, 0
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 184(sp)
  #Load  
  lw t4, 184(sp)
  lw t4, 0(t4)
  sw t4, 188(sp)
  #Load  
  lw t4, 80(sp)
  lw t4, 0(t4)
  sw t4, 192(sp)
  #getElement
  lw t4, 188(sp)
  lw t0, 192(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 196(sp)
  #Load  
  lw t4, 196(sp)
  lw t4, 0(t4)
  sw t4, 200(sp)
  #Store  
  lw t4, 196(sp)
  lw t3, 176(sp)
  sw t3, 0(t4)
  j for.inc4.0.5

for.inc4.0.5:
  #Load  
  lw t4, 80(sp)
  lw t4, 0(t4)
  sw t4, 204(sp)
  lw t0, 204(sp)
  li  t1, 1
  add t2, t0, t1
  sw t2, 208(sp)
  #Store  
  lw t4, 80(sp)
  lw t3, 208(sp)
  sw t3, 0(t4)
  j for.cond4.0.5

for.end4.0.5:
  j if.end2.3.8

if.end2.3.8:
  #Store  
  lw t4, 44(sp)
  li  t0, 1
  sb t0, 0(t4)
  j return9

return9:
  #Load  
  lw t4, 44(sp)
  lb t4, 0(t4)
  sb t4, 212(sp)
  lb a0, 212(sp)
  lw ra, 236(sp)
  lw t1, 232(sp)
  lw t2, 228(sp)
  lw t3, 224(sp)
  lw t4, 220(sp)
  lw t5, 216(sp)
  addi sp, sp, 240
  ret   



  .globl __init__
__init__:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -64
  j return0

return0:
  lw ra, 60(sp)
  lw t1, 56(sp)
  lw t2, 52(sp)
  lw t3, 48(sp)
  lw t4, 44(sp)
  lw t5, 40(sp)
  addi sp, sp, 64
  ret   



  .globl main
main:
  sw t1, -8(sp)
  sw t2, -12(sp)
  sw t3, -16(sp)
  sw t4, -20(sp)
  sw t5, -24(sp)
  sw ra, -4(sp)
  addi sp, sp, -256
  #Call  
  call  __init__
  addi t0, sp, 44
  sw t0, 40(sp)
  addi t0, sp, 52
  sw t0, 48(sp)
  #Call  
  # 1
  li  a0, 1
  call  _malloc
  sw a0, 56(sp)
  #Call  
  # %0
  lw a0, 56(sp)
  call  vector.vector
  #Store  
  lw t4, 48(sp)
  lw t3, 56(sp)
  sw t3, 0(t4)
  addi t0, sp, 64
  sw t0, 60(sp)
  #Call  
  # 10
  li  a0, 10
  call  _arr_init
  sw a0, 68(sp)
  #Store  
  lw t4, 60(sp)
  lw t3, 68(sp)
  sw t3, 0(t4)
  addi t0, sp, 76
  sw t0, 72(sp)
  #Load  
  lw t4, 72(sp)
  lw t4, 0(t4)
  sw t4, 80(sp)
  #Store  
  lw t4, 72(sp)
  li  t0, 0
  sw t0, 0(t4)
  addi t0, sp, 88
  sw t0, 84(sp)
  j for.cond2.0.6

for.cond2.0.6:
  #Load  
  lw t4, 72(sp)
  lw t4, 0(t4)
  sw t4, 92(sp)
  lw t0, 92(sp)
  li  t1, 10
  slt t2, t0, t1
  sb t2, 96(sp)
  lb t0, 96(sp)
  beqz  t0, .false16
  j for.body2.0.6
  .false16:
  j for.end2.0.6

for.body2.0.6:
  #Load  
  lw t4, 72(sp)
  lw t4, 0(t4)
  sw t4, 100(sp)
  li  t0, 9
  lw t1, 100(sp)
  sub t2, t0, t1
  sw t2, 104(sp)
  #Load  
  lw t4, 60(sp)
  lw t4, 0(t4)
  sw t4, 108(sp)
  #Load  
  lw t4, 72(sp)
  lw t4, 0(t4)
  sw t4, 112(sp)
  #getElement
  lw t4, 108(sp)
  lw t0, 112(sp)
  slli t0, t0, 2
  add t3, t4, t0
  sw t3, 116(sp)
  #Load  
  lw t4, 116(sp)
  lw t4, 0(t4)
  sw t4, 120(sp)
  #Store  
  lw t4, 116(sp)
  lw t3, 104(sp)
  sw t3, 0(t4)
  j for.inc2.0.6

for.inc2.0.6:
  #Load  
  lw t4, 72(sp)
  lw t4, 0(t4)
  sw t4, 124(sp)
  lw t0, 124(sp)
  li  t1, 1
  add t2, t0, t1
  sw t2, 128(sp)
  #Store  
  lw t4, 72(sp)
  lw t3, 128(sp)
  sw t3, 0(t4)
  j for.cond2.0.6

for.end2.0.6:
  #Load  
  lw t4, 48(sp)
  lw t4, 0(t4)
  sw t4, 132(sp)
  #Load  
  lw t4, 60(sp)
  lw t4, 0(t4)
  sw t4, 136(sp)
  #Call  
  # %13
  lw a0, 132(sp)
  # %14
  lw a1, 136(sp)
  call  vector.init
  #Call  
  # @.str.3
  la  a0, .str.3
  call  print
  #Load  
  lw t4, 48(sp)
  lw t4, 0(t4)
  sw t4, 140(sp)
  #Call  
  # %15
  lw a0, 140(sp)
  call  vector.tostring
  sw a0, 144(sp)
  #Call  
  # %16
  lw a0, 144(sp)
  call  println
  #Call  
  # 1
  li  a0, 1
  call  _malloc
  sw a0, 148(sp)
  #Call  
  # %17
  lw a0, 148(sp)
  call  vector.vector
  #Store  
  lw t4, 84(sp)
  lw t3, 148(sp)
  sw t3, 0(t4)
  #Load  
  lw t4, 84(sp)
  lw t4, 0(t4)
  sw t4, 152(sp)
  #Load  
  lw t4, 48(sp)
  lw t4, 0(t4)
  sw t4, 156(sp)
  #Call  
  # %18
  lw a0, 152(sp)
  # %19
  lw a1, 156(sp)
  call  vector.copy
  sb a0, 160(sp)
  #Load  
  lw t4, 84(sp)
  lw t4, 0(t4)
  sw t4, 164(sp)
  #Call  
  # %21
  lw a0, 164(sp)
  # 3
  li  a1, 3
  # 817
  li  a2, 817
  call  vector.set
  sb a0, 168(sp)
  lb t0, 168(sp)
  beqz  t0, .false17
  j if.then2.1.9
  .false17:
  j if.else2.1.9

if.then2.1.9:
  #Call  
  # @.str.4
  la  a0, .str.4
  call  println
  j if.end2.1.9

if.else2.1.9:
  j if.end2.1.9

if.end2.1.9:
  #Call  
  # @.str.5
  la  a0, .str.5
  call  print
  #Load  
  lw t4, 84(sp)
  lw t4, 0(t4)
  sw t4, 172(sp)
  #Call  
  # %23
  lw a0, 172(sp)
  call  vector.tostring
  sw a0, 176(sp)
  #Call  
  # %24
  lw a0, 176(sp)
  call  println
  #Call  
  # @.str.6
  la  a0, .str.6
  call  print
  #Load  
  lw t4, 48(sp)
  lw t4, 0(t4)
  sw t4, 180(sp)
  #Load  
  lw t4, 84(sp)
  lw t4, 0(t4)
  sw t4, 184(sp)
  #Call  
  # %25
  lw a0, 180(sp)
  # %26
  lw a1, 184(sp)
  call  vector.add
  sw a0, 188(sp)
  #Call  
  # %27
  lw a0, 188(sp)
  call  vector.tostring
  sw a0, 192(sp)
  #Call  
  # %28
  lw a0, 192(sp)
  call  println
  #Call  
  # @.str.7
  la  a0, .str.7
  call  print
  #Load  
  lw t4, 48(sp)
  lw t4, 0(t4)
  sw t4, 196(sp)
  #Load  
  lw t4, 84(sp)
  lw t4, 0(t4)
  sw t4, 200(sp)
  #Call  
  # %29
  lw a0, 196(sp)
  # %30
  lw a1, 200(sp)
  call  vector.dot
  sw a0, 204(sp)
  #Call  
  # %31
  lw a0, 204(sp)
  call  toString
  sw a0, 208(sp)
  #Call  
  # %32
  lw a0, 208(sp)
  call  println
  #Call  
  # @.str.8
  la  a0, .str.8
  call  print
  #Load  
  lw t4, 84(sp)
  lw t4, 0(t4)
  sw t4, 212(sp)
  li  t0, 1
  li  t1, 3
  sll t2, t0, t1
  sw t2, 216(sp)
  #Call  
  # %33
  lw a0, 212(sp)
  # %34
  lw a1, 216(sp)
  call  vector.scalarInPlaceMultiply
  sw a0, 220(sp)
  #Call  
  # %35
  lw a0, 220(sp)
  call  vector.tostring
  sw a0, 224(sp)
  #Call  
  # %36
  lw a0, 224(sp)
  call  println
  #Store  
  lw t4, 40(sp)
  li  t0, 0
  sw t0, 0(t4)
  j return10

return10:
  #Load  
  lw t4, 40(sp)
  lw t4, 0(t4)
  sw t4, 228(sp)
  lw a0, 228(sp)
  lw ra, 252(sp)
  lw t1, 248(sp)
  lw t2, 244(sp)
  lw t3, 240(sp)
  lw t4, 236(sp)
  lw t5, 232(sp)
  addi sp, sp, 256
  ret   



 .section .data
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
  .asciz "( "
  .size .str.0, 3

.p2align 2
.str.1:
  .asciz ", "
  .size .str.1, 3

.p2align 2
.str.2:
  .asciz " )"
  .size .str.2, 3

.p2align 2
.str.3:
  .asciz "vector x: "
  .size .str.3, 11

.p2align 2
.str.4:
  .asciz "excited!"
  .size .str.4, 9

.p2align 2
.str.5:
  .asciz "vector y: "
  .size .str.5, 11

.p2align 2
.str.6:
  .asciz "x + y: "
  .size .str.6, 8

.p2align 2
.str.7:
  .asciz "x * y: "
  .size .str.7, 8

.p2align 2
.str.8:
  .asciz "(1 << 3) * y: "
  .size .str.8, 15

