	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p1_m2p0_a2p1_c2p0"
	.file	"output.ll"
	.globl	__init__                        # -- Begin function __init__
	.p2align	1
	.type	__init__,@function
__init__:                               # @__init__
	.cfi_startproc
# %bb.0:                                # %entry
	j	.LBB0_1
.LBB0_1:                                # %return0
	ret
.Lfunc_end0:
	.size	__init__, .Lfunc_end0-__init__
	.cfi_endproc
                                        # -- End function
	.globl	main                            # -- Begin function main
	.p2align	1
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	call	__init__@plt
	li	a0, 1
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	j	.LBB1_1
.LBB1_1:                                # %for.cond2.0.0
                                        # =>This Inner Loop Header: Depth=1
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	sw	a1, 4(sp)                       # 4-byte Folded Spill
	li	a0, 9
	blt	a0, a1, .LBB1_4
	j	.LBB1_2
.LBB1_2:                                # %for.body2.0.0
                                        #   in Loop: Header=BB1_1 Depth=1
	j	.LBB1_3
.LBB1_3:                                # %for.inc2.0.0
                                        #   in Loop: Header=BB1_1 Depth=1
	lw	a0, 4(sp)                       # 4-byte Folded Reload
	addi	a0, a0, 1
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	j	.LBB1_1
.LBB1_4:                                # %for.end2.0.0
	j	.LBB1_5
.LBB1_5:                                # %return1
	li	a0, 0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end1:
	.size	main, .Lfunc_end1-main
	.cfi_endproc
                                        # -- End function
	.type	.L.str.true,@object             # @.str.true
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str.true:
	.asciz	"true"
	.size	.L.str.true, 5

	.type	.L.str.false,@object            # @.str.false
.L.str.false:
	.asciz	"false"
	.size	.L.str.false, 6

	.section	".note.GNU-stack","",@progbits
	.addrsig
	.addrsig_sym __init__
