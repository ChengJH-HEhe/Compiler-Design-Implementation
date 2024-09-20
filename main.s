	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p1_m2p0_a2p1_c2p0"
	.file	"output.ll"
	.globl	Edge.Edge                       # -- Begin function Edge.Edge
	.p2align	1
	.type	Edge.Edge,@function
Edge.Edge:                              # @Edge.Edge
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 8(sp)
	j	.LBB0_1
.LBB0_1:                                # %return
	addi	sp, sp, 16
	ret
.Lfunc_end0:
	.size	Edge.Edge, .Lfunc_end0-Edge.Edge
	.cfi_endproc
                                        # -- End function
	.globl	EdgeList.EdgeList               # -- Begin function EdgeList.EdgeList
	.p2align	1
	.type	EdgeList.EdgeList,@function
EdgeList.EdgeList:                      # @EdgeList.EdgeList
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 8(sp)
	j	.LBB1_1
.LBB1_1:                                # %return
	addi	sp, sp, 16
	ret
.Lfunc_end1:
	.size	EdgeList.EdgeList, .Lfunc_end1-EdgeList.EdgeList
	.cfi_endproc
                                        # -- End function
	.globl	EdgeList.init                   # -- Begin function EdgeList.init
	.p2align	1
	.type	EdgeList.init,@function
EdgeList.init:                          # @EdgeList.init
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 24(sp)
	lw	a0, 24(sp)
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	sw	a1, 20(sp)
	sw	a2, 16(sp)
	lw	a0, 16(sp)
	call	_arr_init@plt
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	sw	a0, 0(a1)
	lw	a0, 16(sp)
	call	_arr_init@plt
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	sw	a0, 4(a1)
	lw	a0, 20(sp)
	call	_arr_init@plt
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	sw	a0, 8(a1)
	li	a0, 0
	sw	a0, 12(sp)
	j	.LBB2_1
.LBB2_1:                                # %for.cond2.0.0
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, 12(sp)
	lw	a1, 16(sp)
	bge	a0, a1, .LBB2_4
	j	.LBB2_2
.LBB2_2:                                # %for.body2.0.0
                                        #   in Loop: Header=BB2_1 Depth=1
	lw	a0, 8(sp)                       # 4-byte Folded Reload
	lw	a0, 4(a0)
	lw	a1, 12(sp)
	slli	a1, a1, 2
	add	a1, a1, a0
	li	a0, -1
	sw	a0, 0(a1)
	j	.LBB2_3
.LBB2_3:                                # %for.inc2.0.0
                                        #   in Loop: Header=BB2_1 Depth=1
	lw	a0, 12(sp)
	addi	a0, a0, 1
	sw	a0, 12(sp)
	j	.LBB2_1
.LBB2_4:                                # %for.end2.0.0
	li	a0, 0
	sw	a0, 12(sp)
	j	.LBB2_5
.LBB2_5:                                # %for.cond2.1.1
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, 12(sp)
	lw	a1, 20(sp)
	bge	a0, a1, .LBB2_8
	j	.LBB2_6
.LBB2_6:                                # %for.body2.1.1
                                        #   in Loop: Header=BB2_5 Depth=1
	lw	a0, 8(sp)                       # 4-byte Folded Reload
	lw	a0, 8(a0)
	lw	a1, 12(sp)
	slli	a1, a1, 2
	add	a1, a1, a0
	li	a0, -1
	sw	a0, 0(a1)
	j	.LBB2_7
.LBB2_7:                                # %for.inc2.1.1
                                        #   in Loop: Header=BB2_5 Depth=1
	lw	a0, 12(sp)
	addi	a0, a0, 1
	sw	a0, 12(sp)
	j	.LBB2_5
.LBB2_8:                                # %for.end2.1.1
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	li	a0, 0
	sw	a0, 12(a1)
	j	.LBB2_9
.LBB2_9:                                # %return
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end2:
	.size	EdgeList.init, .Lfunc_end2-EdgeList.init
	.cfi_endproc
                                        # -- End function
	.globl	EdgeList.addEdge                # -- Begin function EdgeList.addEdge
	.p2align	1
	.type	EdgeList.addEdge,@function
EdgeList.addEdge:                       # @EdgeList.addEdge
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 24(sp)
	lw	a0, 24(sp)
	sw	a0, 4(sp)                       # 4-byte Folded Spill
	sw	a1, 20(sp)
	sw	a2, 16(sp)
	sw	a3, 12(sp)
	li	a0, 3
	call	_malloc@plt
	sw	a0, 0(sp)                       # 4-byte Folded Spill
	call	Edge.Edge@plt
	lw	a0, 0(sp)                       # 4-byte Folded Reload
	lw	a1, 4(sp)                       # 4-byte Folded Reload
	sw	a0, 8(sp)
	lw	a0, 20(sp)
	lw	a2, 8(sp)
	sw	a0, 0(a2)
	lw	a0, 16(sp)
	lw	a2, 8(sp)
	sw	a0, 4(a2)
	lw	a0, 12(sp)
	lw	a2, 8(sp)
	sw	a0, 8(a2)
	lw	a0, 8(sp)
	lw	a2, 0(a1)
	lw	a3, 12(a1)
	slli	a3, a3, 2
	add	a2, a2, a3
	sw	a0, 0(a2)
	lw	a0, 8(a1)
	lw	a2, 20(sp)
	slli	a2, a2, 2
	add	a0, a0, a2
	lw	a0, 0(a0)
	lw	a2, 4(a1)
	lw	a3, 12(a1)
	slli	a3, a3, 2
	add	a2, a2, a3
	sw	a0, 0(a2)
	lw	a0, 12(a1)
	lw	a2, 8(a1)
	lw	a3, 20(sp)
	slli	a3, a3, 2
	add	a2, a2, a3
	sw	a0, 0(a2)
	lw	a0, 12(a1)
	addi	a0, a0, 1
	sw	a0, 12(a1)
	j	.LBB3_1
.LBB3_1:                                # %return
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end3:
	.size	EdgeList.addEdge, .Lfunc_end3-EdgeList.addEdge
	.cfi_endproc
                                        # -- End function
	.globl	EdgeList.nVertices              # -- Begin function EdgeList.nVertices
	.p2align	1
	.type	EdgeList.nVertices,@function
EdgeList.nVertices:                     # @EdgeList.nVertices
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 0(sp)
	lw	a0, 0(sp)
	lw	a0, 8(a0)
	call	_arr_size@plt
	sw	a0, 8(sp)
	j	.LBB4_1
.LBB4_1:                                # %return
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end4:
	.size	EdgeList.nVertices, .Lfunc_end4-EdgeList.nVertices
	.cfi_endproc
                                        # -- End function
	.globl	EdgeList.nEdges                 # -- Begin function EdgeList.nEdges
	.p2align	1
	.type	EdgeList.nEdges,@function
EdgeList.nEdges:                        # @EdgeList.nEdges
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 0(sp)
	lw	a0, 0(sp)
	lw	a0, 0(a0)
	call	_arr_size@plt
	sw	a0, 8(sp)
	j	.LBB5_1
.LBB5_1:                                # %return
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end5:
	.size	EdgeList.nEdges, .Lfunc_end5-EdgeList.nEdges
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.Array_Node           # -- Begin function Array_Node.Array_Node
	.p2align	1
	.type	Array_Node.Array_Node,@function
Array_Node.Array_Node:                  # @Array_Node.Array_Node
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 8(sp)
	lw	a1, 8(sp)
	sw	a1, 4(sp)                       # 4-byte Folded Spill
	li	a0, 0
	sw	a0, 4(a1)
	li	a0, 16
	call	_arr_init@plt
	lw	a1, 4(sp)                       # 4-byte Folded Reload
	sw	a0, 0(a1)
	j	.LBB6_1
.LBB6_1:                                # %return
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end6:
	.size	Array_Node.Array_Node, .Lfunc_end6-Array_Node.Array_Node
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.push_back            # -- Begin function Array_Node.push_back
	.p2align	1
	.type	Array_Node.push_back,@function
Array_Node.push_back:                   # @Array_Node.push_back
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 24(sp)
	lw	a0, 24(sp)
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	sw	a1, 16(sp)
	call	Array_Node.size@plt
	mv	a1, a0
	lw	a0, 8(sp)                       # 4-byte Folded Reload
	sw	a1, 12(sp)                      # 4-byte Folded Spill
	lw	a0, 0(a0)
	call	_arr_size@plt
	mv	a1, a0
	lw	a0, 12(sp)                      # 4-byte Folded Reload
	bne	a0, a1, .LBB7_2
	j	.LBB7_1
.LBB7_1:                                # %if.then2.0.0
	lw	a0, 8(sp)                       # 4-byte Folded Reload
	call	Array_Node.doubleStorage@plt
	j	.LBB7_3
.LBB7_2:                                # %if.else2.0.0
	j	.LBB7_3
.LBB7_3:                                # %if.end2.0.0
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	lw	a0, 16(sp)
	lw	a2, 0(a1)
	lw	a3, 4(a1)
	slli	a3, a3, 2
	add	a2, a2, a3
	sw	a0, 0(a2)
	lw	a0, 4(a1)
	addi	a0, a0, 1
	sw	a0, 4(a1)
	j	.LBB7_4
.LBB7_4:                                # %return
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end7:
	.size	Array_Node.push_back, .Lfunc_end7-Array_Node.push_back
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.pop_back             # -- Begin function Array_Node.pop_back
	.p2align	1
	.type	Array_Node.pop_back,@function
Array_Node.pop_back:                    # @Array_Node.pop_back
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 0(sp)
	lw	a1, 0(sp)
	lw	a0, 4(a1)
	addi	a0, a0, -1
	sw	a0, 4(a1)
	lw	a0, 0(a1)
	lw	a1, 4(a1)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	sw	a0, 8(sp)
	j	.LBB8_1
.LBB8_1:                                # %return
	lw	a0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end8:
	.size	Array_Node.pop_back, .Lfunc_end8-Array_Node.pop_back
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.back                 # -- Begin function Array_Node.back
	.p2align	1
	.type	Array_Node.back,@function
Array_Node.back:                        # @Array_Node.back
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 0(sp)
	lw	a0, 0(sp)
	lw	a1, 0(a0)
	lw	a0, 4(a0)
	slli	a0, a0, 2
	add	a0, a0, a1
	lw	a0, -4(a0)
	sw	a0, 8(sp)
	j	.LBB9_1
.LBB9_1:                                # %return
	lw	a0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end9:
	.size	Array_Node.back, .Lfunc_end9-Array_Node.back
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.front                # -- Begin function Array_Node.front
	.p2align	1
	.type	Array_Node.front,@function
Array_Node.front:                       # @Array_Node.front
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 0(sp)
	lw	a0, 0(sp)
	lw	a0, 0(a0)
	lw	a0, 0(a0)
	sw	a0, 8(sp)
	j	.LBB10_1
.LBB10_1:                               # %return
	lw	a0, 8(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end10:
	.size	Array_Node.front, .Lfunc_end10-Array_Node.front
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.size                 # -- Begin function Array_Node.size
	.p2align	1
	.type	Array_Node.size,@function
Array_Node.size:                        # @Array_Node.size
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 8(sp)
	lw	a0, 8(sp)
	lw	a0, 4(a0)
	sw	a0, 12(sp)
	j	.LBB11_1
.LBB11_1:                               # %return
	lw	a0, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end11:
	.size	Array_Node.size, .Lfunc_end11-Array_Node.size
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.resize               # -- Begin function Array_Node.resize
	.p2align	1
	.type	Array_Node.resize,@function
Array_Node.resize:                      # @Array_Node.resize
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 8(sp)
	lw	a0, 8(sp)
	sw	a0, 0(sp)                       # 4-byte Folded Spill
	sw	a1, 4(sp)
	j	.LBB12_1
.LBB12_1:                               # %while.cond2.0.2
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, 0(sp)                       # 4-byte Folded Reload
	lw	a0, 0(a0)
	call	_arr_size@plt
	lw	a1, 4(sp)
	bge	a0, a1, .LBB12_3
	j	.LBB12_2
.LBB12_2:                               # %while.body2.0.2
                                        #   in Loop: Header=BB12_1 Depth=1
	lw	a0, 0(sp)                       # 4-byte Folded Reload
	call	Array_Node.doubleStorage@plt
	j	.LBB12_1
.LBB12_3:                               # %while.end2.0.2
	lw	a1, 0(sp)                       # 4-byte Folded Reload
	lw	a0, 4(sp)
	sw	a0, 4(a1)
	j	.LBB12_4
.LBB12_4:                               # %return
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end12:
	.size	Array_Node.resize, .Lfunc_end12-Array_Node.resize
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.get                  # -- Begin function Array_Node.get
	.p2align	1
	.type	Array_Node.get,@function
Array_Node.get:                         # @Array_Node.get
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	a0, 16(sp)
	lw	a0, 16(sp)
	sw	a1, 12(sp)
	lw	a0, 0(a0)
	lw	a1, 12(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	sw	a0, 24(sp)
	j	.LBB13_1
.LBB13_1:                               # %return
	lw	a0, 24(sp)
	addi	sp, sp, 32
	ret
.Lfunc_end13:
	.size	Array_Node.get, .Lfunc_end13-Array_Node.get
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.set                  # -- Begin function Array_Node.set
	.p2align	1
	.type	Array_Node.set,@function
Array_Node.set:                         # @Array_Node.set
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	a1, 12(sp)                      # 4-byte Folded Spill
	mv	a1, a0
	lw	a0, 12(sp)                      # 4-byte Folded Reload
	sw	a1, 24(sp)
	lw	a1, 24(sp)
	sw	a0, 20(sp)
	sw	a2, 16(sp)
	lw	a0, 16(sp)
	lw	a1, 0(a1)
	lw	a2, 20(sp)
	slli	a2, a2, 2
	add	a1, a1, a2
	sw	a0, 0(a1)
	j	.LBB14_1
.LBB14_1:                               # %return
	addi	sp, sp, 32
	ret
.Lfunc_end14:
	.size	Array_Node.set, .Lfunc_end14-Array_Node.set
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.swap                 # -- Begin function Array_Node.swap
	.p2align	1
	.type	Array_Node.swap,@function
Array_Node.swap:                        # @Array_Node.swap
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	a1, 4(sp)                       # 4-byte Folded Spill
	mv	a1, a0
	lw	a0, 4(sp)                       # 4-byte Folded Reload
	sw	a1, 24(sp)
	lw	a1, 24(sp)
	sw	a0, 20(sp)
	sw	a2, 16(sp)
	lw	a0, 0(a1)
	lw	a2, 20(sp)
	slli	a2, a2, 2
	add	a0, a0, a2
	lw	a0, 0(a0)
	sw	a0, 8(sp)
	lw	a2, 0(a1)
	lw	a0, 16(sp)
	slli	a0, a0, 2
	add	a0, a0, a2
	lw	a0, 0(a0)
	lw	a3, 20(sp)
	slli	a3, a3, 2
	add	a2, a2, a3
	sw	a0, 0(a2)
	lw	a0, 8(sp)
	lw	a1, 0(a1)
	lw	a2, 16(sp)
	slli	a2, a2, 2
	add	a1, a1, a2
	sw	a0, 0(a1)
	j	.LBB15_1
.LBB15_1:                               # %return
	addi	sp, sp, 32
	ret
.Lfunc_end15:
	.size	Array_Node.swap, .Lfunc_end15-Array_Node.swap
	.cfi_endproc
                                        # -- End function
	.globl	Array_Node.doubleStorage        # -- Begin function Array_Node.doubleStorage
	.p2align	1
	.type	Array_Node.doubleStorage,@function
Array_Node.doubleStorage:               # @Array_Node.doubleStorage
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 24(sp)
	lw	a0, 24(sp)
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	lw	a1, 0(a0)
	sw	a1, 16(sp)
	lw	a0, 4(a0)
	sw	a0, 12(sp)
	lw	a0, 16(sp)
	call	_arr_size@plt
	slli	a0, a0, 1
	call	_arr_init@plt
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	sw	a0, 0(a1)
	li	a0, 0
	sw	a0, 4(a1)
	j	.LBB16_1
.LBB16_1:                               # %for.cond2.0.3
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, 8(sp)                       # 4-byte Folded Reload
	lw	a0, 4(a0)
	lw	a1, 12(sp)
	beq	a0, a1, .LBB16_4
	j	.LBB16_2
.LBB16_2:                               # %for.body2.0.3
                                        #   in Loop: Header=BB16_1 Depth=1
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	lw	a0, 16(sp)
	lw	a2, 4(a1)
	slli	a2, a2, 2
	add	a0, a0, a2
	lw	a0, 0(a0)
	lw	a1, 0(a1)
	add	a1, a1, a2
	sw	a0, 0(a1)
	j	.LBB16_3
.LBB16_3:                               # %for.inc2.0.3
                                        #   in Loop: Header=BB16_1 Depth=1
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	lw	a0, 4(a1)
	addi	a0, a0, 1
	sw	a0, 4(a1)
	j	.LBB16_1
.LBB16_4:                               # %for.end2.0.3
	j	.LBB16_5
.LBB16_5:                               # %return
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end16:
	.size	Array_Node.doubleStorage, .Lfunc_end16-Array_Node.doubleStorage
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.Heap_Node             # -- Begin function Heap_Node.Heap_Node
	.p2align	1
	.type	Heap_Node.Heap_Node,@function
Heap_Node.Heap_Node:                    # @Heap_Node.Heap_Node
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 8(sp)
	lw	a0, 8(sp)
	sw	a0, 0(sp)                       # 4-byte Folded Spill
	li	a0, 2
	call	_malloc@plt
	sw	a0, 4(sp)                       # 4-byte Folded Spill
	call	Array_Node.Array_Node@plt
	lw	a1, 0(sp)                       # 4-byte Folded Reload
	lw	a0, 4(sp)                       # 4-byte Folded Reload
	sw	a0, 0(a1)
	j	.LBB17_1
.LBB17_1:                               # %return
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end17:
	.size	Heap_Node.Heap_Node, .Lfunc_end17-Heap_Node.Heap_Node
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.push                  # -- Begin function Heap_Node.push
	.p2align	1
	.type	Heap_Node.push,@function
Heap_Node.push:                         # @Heap_Node.push
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 24(sp)
	lw	a0, 24(sp)
	sw	a0, 4(sp)                       # 4-byte Folded Spill
	sw	a1, 16(sp)
	lw	a0, 0(a0)
	lw	a1, 16(sp)
	call	Array_Node.push_back@plt
	lw	a0, 4(sp)                       # 4-byte Folded Reload
	call	Heap_Node.size@plt
	addi	a0, a0, -1
	sw	a0, 12(sp)
	j	.LBB18_1
.LBB18_1:                               # %while.cond2.0.4
                                        # =>This Inner Loop Header: Depth=1
	lw	a1, 12(sp)
	li	a0, 0
	bge	a0, a1, .LBB18_6
	j	.LBB18_2
.LBB18_2:                               # %while.body2.0.4
                                        #   in Loop: Header=BB18_1 Depth=1
	lw	a0, 4(sp)                       # 4-byte Folded Reload
	lw	a1, 12(sp)
	call	Heap_Node.pnt@plt
	mv	a1, a0
	lw	a0, 4(sp)                       # 4-byte Folded Reload
	sw	a1, 8(sp)
	lw	a0, 0(a0)
	lw	a1, 8(sp)
	call	Array_Node.get@plt
	call	Node.key_@plt
	mv	a1, a0
	lw	a0, 4(sp)                       # 4-byte Folded Reload
	sw	a1, 0(sp)                       # 4-byte Folded Spill
	lw	a0, 0(a0)
	lw	a1, 12(sp)
	call	Array_Node.get@plt
	call	Node.key_@plt
	mv	a1, a0
	lw	a0, 0(sp)                       # 4-byte Folded Reload
	blt	a0, a1, .LBB18_4
	j	.LBB18_3
.LBB18_3:                               # %if.then4.0.1
	j	.LBB18_6
.LBB18_4:                               # %if.else4.0.1
                                        #   in Loop: Header=BB18_1 Depth=1
	j	.LBB18_5
.LBB18_5:                               # %if.end4.0.1
                                        #   in Loop: Header=BB18_1 Depth=1
	lw	a0, 4(sp)                       # 4-byte Folded Reload
	lw	a0, 0(a0)
	lw	a1, 8(sp)
	lw	a2, 12(sp)
	call	Array_Node.swap@plt
	lw	a0, 8(sp)
	sw	a0, 12(sp)
	j	.LBB18_1
.LBB18_6:                               # %while.end2.0.4
	j	.LBB18_7
.LBB18_7:                               # %return
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end18:
	.size	Heap_Node.push, .Lfunc_end18-Heap_Node.push
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.pop                   # -- Begin function Heap_Node.pop
	.p2align	1
	.type	Heap_Node.pop,@function
Heap_Node.pop:                          # @Heap_Node.pop
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -48
	.cfi_def_cfa_offset 48
	sw	ra, 44(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 32(sp)
	lw	a0, 32(sp)
	sw	a0, 20(sp)                      # 4-byte Folded Spill
	lw	a0, 0(a0)
	call	Array_Node.front@plt
	mv	a1, a0
	lw	a0, 20(sp)                      # 4-byte Folded Reload
	sw	a1, 24(sp)
	lw	a1, 0(a0)
	sw	a1, 12(sp)                      # 4-byte Folded Spill
	call	Heap_Node.size@plt
	mv	a1, a0
	lw	a0, 12(sp)                      # 4-byte Folded Reload
	addi	a2, a1, -1
	li	a1, 0
	sw	a1, 16(sp)                      # 4-byte Folded Spill
	call	Array_Node.swap@plt
	lw	a0, 20(sp)                      # 4-byte Folded Reload
	lw	a0, 0(a0)
	call	Array_Node.pop_back@plt
	lw	a1, 16(sp)                      # 4-byte Folded Reload
                                        # kill: def $x12 killed $x10
	lw	a0, 20(sp)                      # 4-byte Folded Reload
	call	Heap_Node.maxHeapify@plt
	lw	a0, 24(sp)
	sw	a0, 40(sp)
	j	.LBB19_1
.LBB19_1:                               # %return
	lw	a0, 40(sp)
	lw	ra, 44(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 48
	ret
.Lfunc_end19:
	.size	Heap_Node.pop, .Lfunc_end19-Heap_Node.pop
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.top                   # -- Begin function Heap_Node.top
	.p2align	1
	.type	Heap_Node.top,@function
Heap_Node.top:                          # @Heap_Node.top
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 0(sp)
	lw	a0, 0(sp)
	lw	a0, 0(a0)
	call	Array_Node.front@plt
	sw	a0, 8(sp)
	j	.LBB20_1
.LBB20_1:                               # %return
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end20:
	.size	Heap_Node.top, .Lfunc_end20-Heap_Node.top
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.size                  # -- Begin function Heap_Node.size
	.p2align	1
	.type	Heap_Node.size,@function
Heap_Node.size:                         # @Heap_Node.size
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 0(sp)
	lw	a0, 0(sp)
	lw	a0, 0(a0)
	call	Array_Node.size@plt
	sw	a0, 8(sp)
	j	.LBB21_1
.LBB21_1:                               # %return
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end21:
	.size	Heap_Node.size, .Lfunc_end21-Heap_Node.size
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.lchild                # -- Begin function Heap_Node.lchild
	.p2align	1
	.type	Heap_Node.lchild,@function
Heap_Node.lchild:                       # @Heap_Node.lchild
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 8(sp)
	sw	a1, 4(sp)
	lw	a0, 4(sp)
	slli	a0, a0, 1
	addi	a0, a0, 1
	sw	a0, 12(sp)
	j	.LBB22_1
.LBB22_1:                               # %return
	lw	a0, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end22:
	.size	Heap_Node.lchild, .Lfunc_end22-Heap_Node.lchild
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.rchild                # -- Begin function Heap_Node.rchild
	.p2align	1
	.type	Heap_Node.rchild,@function
Heap_Node.rchild:                       # @Heap_Node.rchild
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 8(sp)
	sw	a1, 4(sp)
	lw	a0, 4(sp)
	slli	a0, a0, 1
	addi	a0, a0, 2
	sw	a0, 12(sp)
	j	.LBB23_1
.LBB23_1:                               # %return
	lw	a0, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end23:
	.size	Heap_Node.rchild, .Lfunc_end23-Heap_Node.rchild
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.pnt                   # -- Begin function Heap_Node.pnt
	.p2align	1
	.type	Heap_Node.pnt,@function
Heap_Node.pnt:                          # @Heap_Node.pnt
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 8(sp)
	sw	a1, 4(sp)
	lw	a0, 4(sp)
	addi	a0, a0, -1
	srli	a1, a0, 31
	add	a0, a0, a1
	srai	a0, a0, 1
	sw	a0, 12(sp)
	j	.LBB24_1
.LBB24_1:                               # %return
	lw	a0, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end24:
	.size	Heap_Node.pnt, .Lfunc_end24-Heap_Node.pnt
	.cfi_endproc
                                        # -- End function
	.globl	Heap_Node.maxHeapify            # -- Begin function Heap_Node.maxHeapify
	.p2align	1
	.type	Heap_Node.maxHeapify,@function
Heap_Node.maxHeapify:                   # @Heap_Node.maxHeapify
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -64
	.cfi_def_cfa_offset 64
	sw	ra, 60(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 56(sp)
	lw	a0, 56(sp)
	sw	a0, 36(sp)                      # 4-byte Folded Spill
	sw	a1, 52(sp)
	lw	a1, 52(sp)
	call	Heap_Node.lchild@plt
	mv	a1, a0
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	sw	a1, 48(sp)
	lw	a1, 52(sp)
	call	Heap_Node.rchild@plt
	sw	a0, 44(sp)
	lw	a0, 52(sp)
	sw	a0, 40(sp)
	j	.LBB25_1
.LBB25_1:                               # %log.lhs2
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	lw	a1, 48(sp)
	sw	a1, 28(sp)                      # 4-byte Folded Spill
	call	Heap_Node.size@plt
	mv	a1, a0
	lw	a0, 28(sp)                      # 4-byte Folded Reload
	slt	a2, a0, a1
	sw	a2, 32(sp)                      # 4-byte Folded Spill
	bge	a0, a1, .LBB25_3
	j	.LBB25_2
.LBB25_2:                               # %log.rhs2
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	lw	a0, 0(a0)
	lw	a1, 48(sp)
	call	Array_Node.get@plt
	call	Node.key_@plt
	mv	a1, a0
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	sw	a1, 20(sp)                      # 4-byte Folded Spill
	lw	a0, 0(a0)
	lw	a1, 40(sp)
	call	Array_Node.get@plt
	call	Node.key_@plt
	lw	a1, 20(sp)                      # 4-byte Folded Reload
	slt	a0, a0, a1
	sw	a0, 24(sp)                      # 4-byte Folded Spill
	j	.LBB25_3
.LBB25_3:                               # %log.end2
	lw	a0, 32(sp)                      # 4-byte Folded Reload
	lw	a1, 24(sp)                      # 4-byte Folded Reload
	and	a0, a0, a1
	andi	a0, a0, 1
	beqz	a0, .LBB25_5
	j	.LBB25_4
.LBB25_4:                               # %if.then2.0.3
	lw	a0, 48(sp)
	sw	a0, 40(sp)
	j	.LBB25_6
.LBB25_5:                               # %if.else2.0.3
	j	.LBB25_6
.LBB25_6:                               # %if.end2.0.3
	j	.LBB25_7
.LBB25_7:                               # %log.lhs4
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	lw	a1, 44(sp)
	sw	a1, 12(sp)                      # 4-byte Folded Spill
	call	Heap_Node.size@plt
	mv	a1, a0
	lw	a0, 12(sp)                      # 4-byte Folded Reload
	slt	a2, a0, a1
	sw	a2, 16(sp)                      # 4-byte Folded Spill
	bge	a0, a1, .LBB25_9
	j	.LBB25_8
.LBB25_8:                               # %log.rhs4
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	lw	a0, 0(a0)
	lw	a1, 44(sp)
	call	Array_Node.get@plt
	call	Node.key_@plt
	mv	a1, a0
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	sw	a1, 4(sp)                       # 4-byte Folded Spill
	lw	a0, 0(a0)
	lw	a1, 40(sp)
	call	Array_Node.get@plt
	call	Node.key_@plt
	lw	a1, 4(sp)                       # 4-byte Folded Reload
	slt	a0, a0, a1
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	j	.LBB25_9
.LBB25_9:                               # %log.end4
	lw	a0, 16(sp)                      # 4-byte Folded Reload
	lw	a1, 8(sp)                       # 4-byte Folded Reload
	and	a0, a0, a1
	andi	a0, a0, 1
	beqz	a0, .LBB25_11
	j	.LBB25_10
.LBB25_10:                              # %if.then2.3.5
	lw	a0, 44(sp)
	sw	a0, 40(sp)
	j	.LBB25_12
.LBB25_11:                              # %if.else2.3.5
	j	.LBB25_12
.LBB25_12:                              # %if.end2.3.5
	lw	a0, 40(sp)
	lw	a1, 52(sp)
	bne	a0, a1, .LBB25_14
	j	.LBB25_13
.LBB25_13:                              # %if.then2.6.6
	j	.LBB25_16
.LBB25_14:                              # %if.else2.6.6
	j	.LBB25_15
.LBB25_15:                              # %if.end2.6.6
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	lw	a0, 0(a0)
	lw	a1, 52(sp)
	lw	a2, 40(sp)
	call	Array_Node.swap@plt
	lw	a0, 36(sp)                      # 4-byte Folded Reload
	lw	a1, 40(sp)
	call	Heap_Node.maxHeapify@plt
	j	.LBB25_16
.LBB25_16:                              # %return
	lw	ra, 60(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 64
	ret
.Lfunc_end25:
	.size	Heap_Node.maxHeapify, .Lfunc_end25-Heap_Node.maxHeapify
	.cfi_endproc
                                        # -- End function
	.globl	init                            # -- Begin function init
	.p2align	1
	.type	init,@function
init:                                   # @init
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	call	getInt@plt
	lui	a1, %hi(n.0.28)
	sw	a1, 4(sp)                       # 4-byte Folded Spill
	sw	a0, %lo(n.0.28)(a1)
	call	getInt@plt
	lui	a1, %hi(m.0.29)
	sw	a1, 8(sp)                       # 4-byte Folded Spill
	sw	a0, %lo(m.0.29)(a1)
	li	a0, 4
	call	_malloc@plt
	sw	a0, 0(sp)                       # 4-byte Folded Spill
	call	EdgeList.EdgeList@plt
	lw	a3, 0(sp)                       # 4-byte Folded Reload
	lw	a1, 4(sp)                       # 4-byte Folded Reload
	lw	a2, 8(sp)                       # 4-byte Folded Reload
	lui	a0, %hi(g.0.30)
	sw	a3, %lo(g.0.30)(a0)
	lw	a0, %lo(g.0.30)(a0)
	lw	a1, %lo(n.0.28)(a1)
	lw	a2, %lo(m.0.29)(a2)
	call	EdgeList.init@plt
	li	a0, 0
	sw	a0, 24(sp)
	j	.LBB26_1
.LBB26_1:                               # %for.cond2.0.5
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, 24(sp)
	lui	a1, %hi(m.0.29)
	lw	a1, %lo(m.0.29)(a1)
	bge	a0, a1, .LBB26_4
	j	.LBB26_2
.LBB26_2:                               # %for.body2.0.5
                                        #   in Loop: Header=BB26_1 Depth=1
	call	getInt@plt
	sw	a0, 20(sp)
	call	getInt@plt
	sw	a0, 16(sp)
	call	getInt@plt
	sw	a0, 12(sp)
	lui	a0, %hi(g.0.30)
	lw	a0, %lo(g.0.30)(a0)
	lw	a1, 20(sp)
	lw	a2, 16(sp)
	lw	a3, 12(sp)
	call	EdgeList.addEdge@plt
	j	.LBB26_3
.LBB26_3:                               # %for.inc2.0.5
                                        #   in Loop: Header=BB26_1 Depth=1
	lw	a0, 24(sp)
	addi	a0, a0, 1
	sw	a0, 24(sp)
	j	.LBB26_1
.LBB26_4:                               # %for.end2.0.5
	j	.LBB26_5
.LBB26_5:                               # %return
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end26:
	.size	init, .Lfunc_end26-init
	.cfi_endproc
                                        # -- End function
	.globl	Node.Node                       # -- Begin function Node.Node
	.p2align	1
	.type	Node.Node,@function
Node.Node:                              # @Node.Node
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 8(sp)
	j	.LBB27_1
.LBB27_1:                               # %return
	addi	sp, sp, 16
	ret
.Lfunc_end27:
	.size	Node.Node, .Lfunc_end27-Node.Node
	.cfi_endproc
                                        # -- End function
	.globl	Node.key_                       # -- Begin function Node.key_
	.p2align	1
	.type	Node.key_,@function
Node.key_:                              # @Node.key_
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	a0, 8(sp)
	lw	a0, 8(sp)
	lw	a1, 4(a0)
	li	a0, 0
	sub	a0, a0, a1
	sw	a0, 12(sp)
	j	.LBB28_1
.LBB28_1:                               # %return
	lw	a0, 12(sp)
	addi	sp, sp, 16
	ret
.Lfunc_end28:
	.size	Node.key_, .Lfunc_end28-Node.key_
	.cfi_endproc
                                        # -- End function
	.globl	dijkstra                        # -- Begin function dijkstra
	.p2align	1
	.type	dijkstra,@function
dijkstra:                               # @dijkstra
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -96
	.cfi_def_cfa_offset 96
	sw	ra, 92(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	sw	a0, 84(sp)
	lui	a0, %hi(n.0.28)
	sw	a0, 24(sp)                      # 4-byte Folded Spill
	lw	a0, %lo(n.0.28)(a0)
	call	_arr_init@plt
	mv	a1, a0
	lw	a0, 24(sp)                      # 4-byte Folded Reload
	sw	a1, 80(sp)
	lw	a0, %lo(n.0.28)(a0)
	call	_arr_init@plt
	sw	a0, 72(sp)
	li	a0, 0
	sw	a0, 68(sp)
	j	.LBB29_1
.LBB29_1:                               # %for.cond2.0.6
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, 68(sp)
	lui	a1, %hi(n.0.28)
	lw	a1, %lo(n.0.28)(a1)
	bge	a0, a1, .LBB29_4
	j	.LBB29_2
.LBB29_2:                               # %for.body2.0.6
                                        #   in Loop: Header=BB29_1 Depth=1
	lui	a0, %hi(INF.0.31)
	lw	a0, %lo(INF.0.31)(a0)
	lw	a1, 72(sp)
	lw	a2, 68(sp)
	slli	a2, a2, 2
	add	a1, a1, a2
	sw	a0, 0(a1)
	lw	a0, 80(sp)
	lw	a1, 68(sp)
	slli	a1, a1, 2
	add	a1, a1, a0
	li	a0, 0
	sw	a0, 0(a1)
	j	.LBB29_3
.LBB29_3:                               # %for.inc2.0.6
                                        #   in Loop: Header=BB29_1 Depth=1
	lw	a0, 68(sp)
	addi	a0, a0, 1
	sw	a0, 68(sp)
	j	.LBB29_1
.LBB29_4:                               # %for.end2.0.6
	lw	a0, 72(sp)
	lw	a1, 84(sp)
	slli	a1, a1, 2
	add	a1, a1, a0
	li	a0, 0
	sw	a0, 20(sp)                      # 4-byte Folded Spill
	sw	a0, 0(a1)
	li	a0, 1
	call	_malloc@plt
	sw	a0, 12(sp)                      # 4-byte Folded Spill
	call	Heap_Node.Heap_Node@plt
	lw	a0, 12(sp)                      # 4-byte Folded Reload
	sw	a0, 64(sp)
	li	a0, 2
	call	_malloc@plt
	sw	a0, 16(sp)                      # 4-byte Folded Spill
	call	Node.Node@plt
	lw	a1, 16(sp)                      # 4-byte Folded Reload
	lw	a0, 20(sp)                      # 4-byte Folded Reload
	sw	a1, 56(sp)
	lw	a1, 56(sp)
	sw	a0, 4(a1)
	lw	a0, 84(sp)
	lw	a1, 56(sp)
	sw	a0, 0(a1)
	lw	a0, 64(sp)
	lw	a1, 56(sp)
	call	Heap_Node.push@plt
	j	.LBB29_5
.LBB29_5:                               # %while.cond2.1.7
                                        # =>This Loop Header: Depth=1
                                        #     Child Loop BB29_10 Depth 2
	lw	a0, 64(sp)
	call	Heap_Node.size@plt
	beqz	a0, .LBB29_17
	j	.LBB29_6
.LBB29_6:                               # %while.body2.1.7
                                        #   in Loop: Header=BB29_5 Depth=1
	lw	a0, 64(sp)
	call	Heap_Node.pop@plt
	sw	a0, 48(sp)
	lw	a0, 48(sp)
	lw	a0, 0(a0)
	sw	a0, 44(sp)
	lw	a0, 80(sp)
	lw	a1, 44(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	li	a1, 1
	bne	a0, a1, .LBB29_8
	j	.LBB29_7
.LBB29_7:                               # %if.then4.0.7
                                        #   in Loop: Header=BB29_5 Depth=1
	j	.LBB29_5
.LBB29_8:                               # %if.else4.0.7
                                        #   in Loop: Header=BB29_5 Depth=1
	j	.LBB29_9
.LBB29_9:                               # %if.end4.0.7
                                        #   in Loop: Header=BB29_5 Depth=1
	lw	a0, 80(sp)
	lw	a1, 44(sp)
	slli	a1, a1, 2
	add	a1, a1, a0
	li	a0, 1
	sw	a0, 0(a1)
	lui	a0, %hi(g.0.30)
	lw	a0, %lo(g.0.30)(a0)
	lw	a0, 8(a0)
	lw	a1, 44(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	sw	a0, 40(sp)
	j	.LBB29_10
.LBB29_10:                              # %for.cond4.3.8
                                        #   Parent Loop BB29_5 Depth=1
                                        # =>  This Inner Loop Header: Depth=2
	lw	a0, 40(sp)
	li	a1, -1
	beq	a0, a1, .LBB29_16
	j	.LBB29_11
.LBB29_11:                              # %for.body4.3.8
                                        #   in Loop: Header=BB29_10 Depth=2
	lui	a0, %hi(g.0.30)
	lw	a1, %lo(g.0.30)(a0)
	lw	a1, 0(a1)
	lw	a2, 40(sp)
	slli	a2, a2, 2
	add	a1, a1, a2
	lw	a1, 0(a1)
	lw	a1, 4(a1)
	sw	a1, 36(sp)
	lw	a0, %lo(g.0.30)(a0)
	lw	a0, 0(a0)
	lw	a1, 40(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	lw	a0, 8(a0)
	sw	a0, 32(sp)
	lw	a0, 72(sp)
	lw	a1, 44(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	lw	a1, 32(sp)
	add	a0, a0, a1
	sw	a0, 28(sp)
	lw	a0, 28(sp)
	lw	a1, 72(sp)
	lw	a2, 36(sp)
	slli	a2, a2, 2
	add	a1, a1, a2
	lw	a1, 0(a1)
	blt	a0, a1, .LBB29_13
	j	.LBB29_12
.LBB29_12:                              # %if.then6.0.8
                                        #   in Loop: Header=BB29_10 Depth=2
	j	.LBB29_15
.LBB29_13:                              # %if.else6.0.8
                                        #   in Loop: Header=BB29_10 Depth=2
	j	.LBB29_14
.LBB29_14:                              # %if.end6.0.8
                                        #   in Loop: Header=BB29_10 Depth=2
	lw	a0, 28(sp)
	lw	a1, 72(sp)
	lw	a2, 36(sp)
	slli	a2, a2, 2
	add	a1, a1, a2
	sw	a0, 0(a1)
	li	a0, 2
	call	_malloc@plt
	sw	a0, 8(sp)                       # 4-byte Folded Spill
	call	Node.Node@plt
	lw	a0, 8(sp)                       # 4-byte Folded Reload
	sw	a0, 48(sp)
	lw	a0, 36(sp)
	lw	a1, 48(sp)
	sw	a0, 0(a1)
	lw	a0, 72(sp)
	lw	a1, 36(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	lw	a1, 48(sp)
	sw	a0, 4(a1)
	lw	a0, 64(sp)
	lw	a1, 48(sp)
	call	Heap_Node.push@plt
	j	.LBB29_15
.LBB29_15:                              # %for.inc4.3.8
                                        #   in Loop: Header=BB29_10 Depth=2
	lui	a0, %hi(g.0.30)
	lw	a0, %lo(g.0.30)(a0)
	lw	a0, 4(a0)
	lw	a1, 40(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	sw	a0, 40(sp)
	j	.LBB29_10
.LBB29_16:                              # %for.end4.3.8
                                        #   in Loop: Header=BB29_5 Depth=1
	j	.LBB29_5
.LBB29_17:                              # %while.end2.1.7
	lw	a0, 72(sp)
	sw	a0, 88(sp)
	j	.LBB29_18
.LBB29_18:                              # %return
	lw	a0, 88(sp)
	lw	ra, 92(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 96
	ret
.Lfunc_end29:
	.size	dijkstra, .Lfunc_end29-dijkstra
	.cfi_endproc
                                        # -- End function
	.globl	__init__                        # -- Begin function __init__
	.p2align	1
	.type	__init__,@function
__init__:                               # @__init__
	.cfi_startproc
# %bb.0:                                # %entry
	lui	a1, %hi(INF.0.31)
	lui	a0, 2441
	addi	a0, a0, 1664
	sw	a0, %lo(INF.0.31)(a1)
	j	.LBB30_1
.LBB30_1:                               # %return
	ret
.Lfunc_end30:
	.size	__init__, .Lfunc_end30-__init__
	.cfi_endproc
                                        # -- End function
	.globl	main                            # -- Begin function main
	.p2align	1
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:                                # %entry
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	.cfi_offset ra, -4
	call	__init__@plt
	call	init@plt
	li	a0, 0
	sw	a0, 20(sp)
	j	.LBB31_1
.LBB31_1:                               # %for.cond2.0.9
                                        # =>This Loop Header: Depth=1
                                        #     Child Loop BB31_3 Depth 2
	lw	a0, 20(sp)
	lui	a1, %hi(n.0.28)
	lw	a1, %lo(n.0.28)(a1)
	bge	a0, a1, .LBB31_11
	j	.LBB31_2
.LBB31_2:                               # %for.body2.0.9
                                        #   in Loop: Header=BB31_1 Depth=1
	lw	a0, 20(sp)
	call	dijkstra@plt
	sw	a0, 8(sp)
	li	a0, 0
	sw	a0, 16(sp)
	j	.LBB31_3
.LBB31_3:                               # %for.cond4.0.10
                                        #   Parent Loop BB31_1 Depth=1
                                        # =>  This Inner Loop Header: Depth=2
	lw	a0, 16(sp)
	lui	a1, %hi(n.0.28)
	lw	a1, %lo(n.0.28)(a1)
	bge	a0, a1, .LBB31_9
	j	.LBB31_4
.LBB31_4:                               # %for.body4.0.10
                                        #   in Loop: Header=BB31_3 Depth=2
	lw	a0, 8(sp)
	lw	a1, 16(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	lui	a1, %hi(INF.0.31)
	lw	a1, %lo(INF.0.31)(a1)
	bne	a0, a1, .LBB31_6
	j	.LBB31_5
.LBB31_5:                               # %if.then6.0.9
                                        #   in Loop: Header=BB31_3 Depth=2
	lui	a0, %hi(.L.str.0)
	addi	a0, a0, %lo(.L.str.0)
	call	print@plt
	j	.LBB31_7
.LBB31_6:                               # %if.else6.0.9
                                        #   in Loop: Header=BB31_3 Depth=2
	lw	a0, 8(sp)
	lw	a1, 16(sp)
	slli	a1, a1, 2
	add	a0, a0, a1
	lw	a0, 0(a0)
	call	toString@plt
	call	print@plt
	j	.LBB31_7
.LBB31_7:                               # %if.end6.0.9
                                        #   in Loop: Header=BB31_3 Depth=2
	lui	a0, %hi(.L.str.1)
	addi	a0, a0, %lo(.L.str.1)
	call	print@plt
	j	.LBB31_8
.LBB31_8:                               # %for.inc4.0.10
                                        #   in Loop: Header=BB31_3 Depth=2
	lw	a0, 16(sp)
	addi	a0, a0, 1
	sw	a0, 16(sp)
	j	.LBB31_3
.LBB31_9:                               # %for.end4.0.10
                                        #   in Loop: Header=BB31_1 Depth=1
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	call	println@plt
	j	.LBB31_10
.LBB31_10:                              # %for.inc2.0.9
                                        #   in Loop: Header=BB31_1 Depth=1
	lw	a0, 20(sp)
	addi	a0, a0, 1
	sw	a0, 20(sp)
	j	.LBB31_1
.LBB31_11:                              # %for.end2.0.9
	li	a0, 0
	sw	a0, 24(sp)
	j	.LBB31_12
.LBB31_12:                              # %return
	lw	a0, 24(sp)
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end31:
	.size	main, .Lfunc_end31-main
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

	.type	n.0.28,@object                  # @n.0.28
	.section	.sbss,"aw",@nobits
	.globl	n.0.28
	.p2align	2
n.0.28:
	.word	0                               # 0x0
	.size	n.0.28, 4

	.type	m.0.29,@object                  # @m.0.29
	.globl	m.0.29
	.p2align	2
m.0.29:
	.word	0                               # 0x0
	.size	m.0.29, 4

	.type	g.0.30,@object                  # @g.0.30
	.globl	g.0.30
	.p2align	2
g.0.30:
	.word	0
	.size	g.0.30, 4

	.type	INF.0.31,@object                # @INF.0.31
	.globl	INF.0.31
	.p2align	2
INF.0.31:
	.word	0                               # 0x0
	.size	INF.0.31, 4

	.type	.L.str.0,@object                # @.str.0
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str.0:
	.asciz	"-1"
	.size	.L.str.0, 3

	.type	.L.str.1,@object                # @.str.1
.L.str.1:
	.asciz	" "
	.size	.L.str.1, 2

	.type	.L.str.2,@object                # @.str.2
.L.str.2:
	.zero	1
	.size	.L.str.2, 1

	.section	".note.GNU-stack","",@progbits
	.addrsig
	.addrsig_sym _arr_size
	.addrsig_sym _malloc
	.addrsig_sym _arr_init
	.addrsig_sym toString
	.addrsig_sym print
	.addrsig_sym println
	.addrsig_sym getInt
	.addrsig_sym Edge.Edge
	.addrsig_sym EdgeList.EdgeList
	.addrsig_sym EdgeList.init
	.addrsig_sym EdgeList.addEdge
	.addrsig_sym Array_Node.Array_Node
	.addrsig_sym Array_Node.push_back
	.addrsig_sym Array_Node.pop_back
	.addrsig_sym Array_Node.front
	.addrsig_sym Array_Node.size
	.addrsig_sym Array_Node.get
	.addrsig_sym Array_Node.swap
	.addrsig_sym Array_Node.doubleStorage
	.addrsig_sym Heap_Node.Heap_Node
	.addrsig_sym Heap_Node.push
	.addrsig_sym Heap_Node.pop
	.addrsig_sym Heap_Node.size
	.addrsig_sym Heap_Node.lchild
	.addrsig_sym Heap_Node.rchild
	.addrsig_sym Heap_Node.pnt
	.addrsig_sym Heap_Node.maxHeapify
	.addrsig_sym init
	.addrsig_sym Node.Node
	.addrsig_sym Node.key_
	.addrsig_sym dijkstra
	.addrsig_sym __init__
	.addrsig_sym n.0.28
	.addrsig_sym m.0.29
	.addrsig_sym g.0.30
	.addrsig_sym INF.0.31
