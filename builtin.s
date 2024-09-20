	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p1_m2p0_a2p1_c2p0"
	.file	"builtin.c"
	.globl	print                           # -- Begin function print
	.p2align	1
	.type	print,@function
print:                                  # @print
# %bb.0:
	mv	a1, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	tail	printf
.Lfunc_end0:
	.size	print, .Lfunc_end0-print
                                        # -- End function
	.globl	_strcmp                         # -- Begin function _strcmp
	.p2align	1
	.type	_strcmp,@function
_strcmp:                                # @_strcmp
# %bb.0:
	tail	strcmp
.Lfunc_end1:
	.size	_strcmp, .Lfunc_end1-_strcmp
                                        # -- End function
	.globl	println                         # -- Begin function println
	.p2align	1
	.type	println,@function
println:                                # @println
# %bb.0:
	mv	a1, a0
	lui	a0, %hi(.L.str.1)
	addi	a0, a0, %lo(.L.str.1)
	tail	printf
.Lfunc_end2:
	.size	println, .Lfunc_end2-println
                                        # -- End function
	.globl	printInt                        # -- Begin function printInt
	.p2align	1
	.type	printInt,@function
printInt:                               # @printInt
# %bb.0:
	mv	a1, a0
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	tail	printf
.Lfunc_end3:
	.size	printInt, .Lfunc_end3-printInt
                                        # -- End function
	.globl	printlnInt                      # -- Begin function printlnInt
	.p2align	1
	.type	printlnInt,@function
printlnInt:                             # @printlnInt
# %bb.0:
	mv	a1, a0
	lui	a0, %hi(.L.str.3)
	addi	a0, a0, %lo(.L.str.3)
	tail	printf
.Lfunc_end4:
	.size	printlnInt, .Lfunc_end4-printlnInt
                                        # -- End function
	.globl	getString                       # -- Begin function getString
	.p2align	1
	.type	getString,@function
getString:                              # @getString
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	li	a0, 256
	call	malloc
	mv	a1, a0
	sw	a1, 8(sp)                       # 4-byte Folded Spill
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	scanf
                                        # kill: def $x11 killed $x10
	lw	a0, 8(sp)                       # 4-byte Folded Reload
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end5:
	.size	getString, .Lfunc_end5-getString
                                        # -- End function
	.globl	getInt                          # -- Begin function getInt
	.p2align	1
	.type	getInt,@function
getInt:                                 # @getInt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, sp, 8
	call	scanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end6:
	.size	getInt, .Lfunc_end6-getInt
                                        # -- End function
	.globl	toString                        # -- Begin function toString
	.p2align	1
	.type	toString,@function
toString:                               # @toString
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	a0, 4(sp)                       # 4-byte Folded Spill
	li	a0, 256
	call	malloc
	lw	a2, 4(sp)                       # 4-byte Folded Reload
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	call	sprintf
                                        # kill: def $x11 killed $x10
	lw	a0, 8(sp)                       # 4-byte Folded Reload
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end7:
	.size	toString, .Lfunc_end7-toString
                                        # -- End function
	.globl	string.length                   # -- Begin function string.length
	.p2align	1
	.type	string.length,@function
string.length:                          # @string.length
# %bb.0:
	tail	strlen
.Lfunc_end8:
	.size	string.length, .Lfunc_end8-string.length
                                        # -- End function
	.globl	string.substring                # -- Begin function string.substring
	.p2align	1
	.type	string.substring,@function
string.substring:                       # @string.substring
# %bb.0:
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	a1, 12(sp)                      # 4-byte Folded Spill
	sw	a0, 16(sp)                      # 4-byte Folded Spill
	sub	a0, a2, a1
	sw	a0, 20(sp)                      # 4-byte Folded Spill
	addi	a0, a0, 1
	call	malloc
	lw	a3, 12(sp)                      # 4-byte Folded Reload
	lw	a1, 16(sp)                      # 4-byte Folded Reload
	lw	a2, 20(sp)                      # 4-byte Folded Reload
	sw	a0, 24(sp)                      # 4-byte Folded Spill
	add	a1, a1, a3
	call	memcpy
	lw	a1, 20(sp)                      # 4-byte Folded Reload
                                        # kill: def $x12 killed $x10
	lw	a0, 24(sp)                      # 4-byte Folded Reload
	add	a2, a0, a1
	li	a1, 0
	sb	a1, 0(a2)
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end9:
	.size	string.substring, .Lfunc_end9-string.substring
                                        # -- End function
	.globl	string.parseInt                 # -- Begin function string.parseInt
	.p2align	1
	.type	string.parseInt,@function
string.parseInt:                        # @string.parseInt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	addi	a2, sp, 8
	call	sscanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end10:
	.size	string.parseInt, .Lfunc_end10-string.parseInt
                                        # -- End function
	.globl	string.ord                      # -- Begin function string.ord
	.p2align	1
	.type	string.ord,@function
string.ord:                             # @string.ord
# %bb.0:
	add	a0, a0, a1
	lbu	a0, 0(a0)
	ret
.Lfunc_end11:
	.size	string.ord, .Lfunc_end11-string.ord
                                        # -- End function
	.globl	_add                            # -- Begin function _add
	.p2align	1
	.type	_add,@function
_add:                                   # @_add
# %bb.0:
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	a1, 20(sp)                      # 4-byte Folded Spill
	sw	a0, 16(sp)                      # 4-byte Folded Spill
	call	strlen
	mv	a1, a0
	lw	a0, 20(sp)                      # 4-byte Folded Reload
	sw	a1, 12(sp)                      # 4-byte Folded Spill
	call	strlen
	mv	a1, a0
	lw	a0, 12(sp)                      # 4-byte Folded Reload
	add	a0, a0, a1
	addi	a0, a0, 1
	call	malloc
	lw	a2, 16(sp)                      # 4-byte Folded Reload
	lw	a3, 20(sp)                      # 4-byte Folded Reload
	sw	a0, 24(sp)                      # 4-byte Folded Spill
	lui	a1, %hi(.L.str.4)
	addi	a1, a1, %lo(.L.str.4)
	call	sprintf
                                        # kill: def $x11 killed $x10
	lw	a0, 24(sp)                      # 4-byte Folded Reload
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end12:
	.size	_add, .Lfunc_end12-_add
                                        # -- End function
	.globl	_arr_size                       # -- Begin function _arr_size
	.p2align	1
	.type	_arr_size,@function
_arr_size:                              # @_arr_size
# %bb.0:
	lw	a0, -4(a0)
	ret
.Lfunc_end13:
	.size	_arr_size, .Lfunc_end13-_arr_size
                                        # -- End function
	.globl	_arr_init                       # -- Begin function _arr_init
	.p2align	1
	.type	_arr_init,@function
_arr_init:                              # @_arr_init
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	slli	a0, a0, 2
	addi	a0, a0, 4
	call	malloc
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	sw	a1, 0(a0)
	addi	a0, a0, 4
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end14:
	.size	_arr_init, .Lfunc_end14-_arr_init
                                        # -- End function
	.globl	_malloc                         # -- Begin function _malloc
	.p2align	1
	.type	_malloc,@function
_malloc:                                # @_malloc
# %bb.0:
	slli	a0, a0, 2
	tail	malloc
.Lfunc_end15:
	.size	_malloc, .Lfunc_end15-_malloc
                                        # -- End function
	.type	.L.str,@object                  # @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.1,@object                # @.str.1
.L.str.1:
	.asciz	"%s\n"
	.size	.L.str.1, 4

	.type	.L.str.2,@object                # @.str.2
.L.str.2:
	.asciz	"%d"
	.size	.L.str.2, 3

	.type	.L.str.3,@object                # @.str.3
.L.str.3:
	.asciz	"%d\n"
	.size	.L.str.3, 4

	.type	.L.str.4,@object                # @.str.4
.L.str.4:
	.asciz	"%s%s"
	.size	.L.str.4, 5

	.ident	"Ubuntu clang version 17.0.6 (++20231209124227+6009708b4367-1~exp1~20231209124336.77)"
	.section	".note.GNU-stack","",@progbits
	.addrsig
