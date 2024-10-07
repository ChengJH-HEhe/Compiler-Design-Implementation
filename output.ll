@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@n.0.28 = global i32 0
@m.0.29 = global i32 0
@g.0.30 = global ptr null
@INF.0.31 = global i32 0
@.str.0 = private unnamed_addr constant [3 x i8] c"-1\00"
@.str.1 = private unnamed_addr constant [2 x i8] c" \00"
@.str.2 = private unnamed_addr constant [1 x i8] c"\00"
%class.string = type {  }
%class.Edge = type { i32,i32,i32 }
%class.EdgeList = type { ptr,ptr,ptr,i32 }
%class.Array_Node = type { ptr,i32 }
%class.Heap_Node = type { ptr }
%class.Node = type { i32,i32 }
declare i32 @string.length(ptr %this)
declare ptr @string.substring(ptr %this, i32 %.int0, i32 %.int1)
declare i32 @string.parseInt(ptr %this)
declare i32 @string.ord(ptr %this, i32 %.int0)
declare i32 @_arr_size(ptr %array)
declare ptr @_malloc(i32 %size)
declare ptr @_arr_init(i32 %size)
declare ptr @_add(ptr %str1, ptr %str2)
declare i32 @_strcmp(ptr %str1, ptr %str2)
declare ptr @toString(i32 %i)
declare void @print(ptr %str)
declare void @println(ptr %str)
declare void @printInt(i32 %i)
declare void @printlnInt(i32 %i)
declare ptr @getString()
declare i32 @getInt()
define void @Edge.Edge(ptr %this) {
entry:
  br label %return1


return1:
  ret void

}

define void @EdgeList.EdgeList(ptr %this) {
entry:
  br label %return2


return2:
  ret void

}

define void @EdgeList.init(ptr %this, i32 %n, i32 %m) {
entry:
  %_0 = call ptr @_arr_init(i32 %m)
  %_1 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_2 = getelementptr ptr, ptr %_1, i32 0
  %_3 = load ptr, ptr %_2
  store ptr %_0, ptr %_2
  %_4 = call ptr @_arr_init(i32 %m)
  %_5 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_6 = getelementptr ptr, ptr %_5, i32 1
  %_7 = load ptr, ptr %_6
  store ptr %_4, ptr %_6
  %_8 = call ptr @_arr_init(i32 %n)
  %_9 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_10 = getelementptr ptr, ptr %_9, i32 2
  %_11 = load ptr, ptr %_10
  store ptr %_8, ptr %_10
  br label %for.cond2.0.0

for.cond2.0.0:
  %i.1.2.for.cond2.0.0 = phi i32 [ 0 , %entry], [ %_23 , %for.inc2.0.0]
  %_14 = icmp slt i32 %i.1.2.for.cond2.0.0, %m
  br i1 %_14, label %for.body2.0.0, label %for.end2.0.0

for.body2.0.0:
  %_15 = sub i32 0, 1
  %_16 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_17 = getelementptr ptr, ptr %_16, i32 1
  %_18 = load ptr, ptr %_17
  %_20 = getelementptr i32, ptr %_18, i32 %i.1.2.for.cond2.0.0
  %_21 = load i32, ptr %_20
  store i32 %_15, ptr %_20
  br label %for.inc2.0.0

for.inc2.0.0:
  %_23 = add i32 %i.1.2.for.cond2.0.0, 1
  br label %for.cond2.0.0

for.end2.0.0:
  br label %for.cond2.1.1

for.cond2.1.1:
  %i.1.2.for.cond2.1.1 = phi i32 [ 0 , %for.end2.0.0], [ %_35 , %for.inc2.1.1]
  %_26 = icmp slt i32 %i.1.2.for.cond2.1.1, %n
  br i1 %_26, label %for.body2.1.1, label %for.end2.1.1

for.body2.1.1:
  %_27 = sub i32 0, 1
  %_28 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_29 = getelementptr ptr, ptr %_28, i32 2
  %_30 = load ptr, ptr %_29
  %_32 = getelementptr i32, ptr %_30, i32 %i.1.2.for.cond2.1.1
  %_33 = load i32, ptr %_32
  store i32 %_27, ptr %_32
  br label %for.inc2.1.1

for.inc2.1.1:
  %_35 = add i32 %i.1.2.for.cond2.1.1, 1
  br label %for.cond2.1.1

for.end2.1.1:
  %_36 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_37 = getelementptr i32, ptr %_36, i32 3
  %_38 = load i32, ptr %_37
  store i32 0, ptr %_37
  br label %return3


return3:
  ret void

}

define void @EdgeList.addEdge(ptr %this, i32 %u, i32 %v, i32 %w) {
entry:
  %_0 = call ptr @_malloc(i32 3)
  call void @Edge.Edge(ptr %_0)
  %_1 = getelementptr %class.Edge, ptr %_0, i32 0
  %_2 = getelementptr i32, ptr %_1, i32 0
  %_3 = load i32, ptr %_2
  store i32 %u, ptr %_2
  %_4 = getelementptr %class.Edge, ptr %_0, i32 0
  %_5 = getelementptr i32, ptr %_4, i32 1
  %_6 = load i32, ptr %_5
  store i32 %v, ptr %_5
  %_7 = getelementptr %class.Edge, ptr %_0, i32 0
  %_8 = getelementptr i32, ptr %_7, i32 2
  %_9 = load i32, ptr %_8
  store i32 %w, ptr %_8
  %_10 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_11 = getelementptr ptr, ptr %_10, i32 0
  %_12 = load ptr, ptr %_11
  %_13 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_14 = getelementptr i32, ptr %_13, i32 3
  %_15 = load i32, ptr %_14
  %_16 = getelementptr ptr, ptr %_12, i32 %_15
  %_17 = load ptr, ptr %_16
  store ptr %_0, ptr %_16
  %_18 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_19 = getelementptr ptr, ptr %_18, i32 2
  %_20 = load ptr, ptr %_19
  %_21 = getelementptr i32, ptr %_20, i32 %u
  %_22 = load i32, ptr %_21
  %_23 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_24 = getelementptr ptr, ptr %_23, i32 1
  %_25 = load ptr, ptr %_24
  %_26 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_27 = getelementptr i32, ptr %_26, i32 3
  %_28 = load i32, ptr %_27
  %_29 = getelementptr i32, ptr %_25, i32 %_28
  %_30 = load i32, ptr %_29
  store i32 %_22, ptr %_29
  %_31 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_32 = getelementptr i32, ptr %_31, i32 3
  %_33 = load i32, ptr %_32
  %_34 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_35 = getelementptr ptr, ptr %_34, i32 2
  %_36 = load ptr, ptr %_35
  %_37 = getelementptr i32, ptr %_36, i32 %u
  %_38 = load i32, ptr %_37
  store i32 %_33, ptr %_37
  %_39 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_40 = getelementptr i32, ptr %_39, i32 3
  %_41 = load i32, ptr %_40
  %_42 = add i32 %_41, 1
  store i32 %_42, ptr %_40
  br label %return4


return4:
  ret void

}

define i32 @EdgeList.nVertices(ptr %this) {
entry:
  %_0 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 2
  %_2 = load ptr, ptr %_1
  %_3 = call i32 @_arr_size(ptr %_2)
  br label %return5


return5:
  ret i32 %_3

}

define i32 @EdgeList.nEdges(ptr %this) {
entry:
  %_0 = getelementptr %class.EdgeList, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = call i32 @_arr_size(ptr %_2)
  br label %return6


return6:
  ret i32 %_3

}

define void @Array_Node.Array_Node(ptr %this) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr i32, ptr %_0, i32 1
  %_2 = load i32, ptr %_1
  store i32 0, ptr %_1
  %_3 = call ptr @_arr_init(i32 16)
  %_4 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_5 = getelementptr ptr, ptr %_4, i32 0
  %_6 = load ptr, ptr %_5
  store ptr %_3, ptr %_5
  br label %return7


return7:
  ret void

}

define void @Array_Node.push_back(ptr %this, ptr %v) {
entry:
  %_0 = call i32 @Array_Node.size(ptr %this)
  %_1 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_2 = getelementptr ptr, ptr %_1, i32 0
  %_3 = load ptr, ptr %_2
  %_4 = call i32 @_arr_size(ptr %_3)
  %_5 = icmp eq i32 %_0, %_4
  br i1 %_5, label %if.then2.0.0, label %if.else2.0.0

if.then2.0.0:
  call void @Array_Node.doubleStorage(ptr %this)
  br label %if.end2.0.0

if.else2.0.0:
  br label %if.end2.0.0

if.end2.0.0:
  %_7 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_8 = getelementptr ptr, ptr %_7, i32 0
  %_9 = load ptr, ptr %_8
  %_10 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_11 = getelementptr i32, ptr %_10, i32 1
  %_12 = load i32, ptr %_11
  %_13 = getelementptr ptr, ptr %_9, i32 %_12
  %_14 = load ptr, ptr %_13
  store ptr %v, ptr %_13
  %_15 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_16 = getelementptr i32, ptr %_15, i32 1
  %_17 = load i32, ptr %_16
  %_18 = add i32 %_17, 1
  store i32 %_18, ptr %_16
  br label %return8


return8:
  ret void

}

define ptr @Array_Node.pop_back(ptr %this) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr i32, ptr %_0, i32 1
  %_2 = load i32, ptr %_1
  %_3 = sub i32 %_2, 1
  store i32 %_3, ptr %_1
  %_4 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_5 = getelementptr ptr, ptr %_4, i32 0
  %_6 = load ptr, ptr %_5
  %_7 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_8 = getelementptr i32, ptr %_7, i32 1
  %_9 = load i32, ptr %_8
  %_10 = getelementptr ptr, ptr %_6, i32 %_9
  %_11 = load ptr, ptr %_10
  br label %return9


return9:
  ret ptr %_11

}

define ptr @Array_Node.back(ptr %this) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_4 = getelementptr i32, ptr %_3, i32 1
  %_5 = load i32, ptr %_4
  %_6 = sub i32 %_5, 1
  %_7 = getelementptr ptr, ptr %_2, i32 %_6
  %_8 = load ptr, ptr %_7
  br label %return10


return10:
  ret ptr %_8

}

define ptr @Array_Node.front(ptr %this) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = getelementptr ptr, ptr %_2, i32 0
  %_4 = load ptr, ptr %_3
  br label %return11


return11:
  ret ptr %_4

}

define i32 @Array_Node.size(ptr %this) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr i32, ptr %_0, i32 1
  %_2 = load i32, ptr %_1
  br label %return12


return12:
  ret i32 %_2

}

define void @Array_Node.resize(ptr %this, i32 %newSize) {
entry:
  br label %while.cond2.0.2

while.cond2.0.2:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = call i32 @_arr_size(ptr %_2)
  %_5 = icmp slt i32 %_3, %newSize
  br i1 %_5, label %while.body2.0.2, label %while.end2.0.2

while.body2.0.2:
  call void @Array_Node.doubleStorage(ptr %this)
  br label %while.cond2.0.2

while.end2.0.2:
  %_7 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_8 = getelementptr i32, ptr %_7, i32 1
  %_9 = load i32, ptr %_8
  store i32 %newSize, ptr %_8
  br label %return13


return13:
  ret void

}

define ptr @Array_Node.get(ptr %this, i32 %i) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = getelementptr ptr, ptr %_2, i32 %i
  %_4 = load ptr, ptr %_3
  br label %return14


return14:
  ret ptr %_4

}

define void @Array_Node.set(ptr %this, i32 %i, ptr %v) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = getelementptr ptr, ptr %_2, i32 %i
  %_4 = load ptr, ptr %_3
  store ptr %v, ptr %_3
  br label %return15


return15:
  ret void

}

define void @Array_Node.swap(ptr %this, i32 %i, i32 %j) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = getelementptr ptr, ptr %_2, i32 %i
  %_4 = load ptr, ptr %_3
  %_5 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_6 = getelementptr ptr, ptr %_5, i32 0
  %_7 = load ptr, ptr %_6
  %_8 = getelementptr ptr, ptr %_7, i32 %j
  %_9 = load ptr, ptr %_8
  %_10 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_11 = getelementptr ptr, ptr %_10, i32 0
  %_12 = load ptr, ptr %_11
  %_13 = getelementptr ptr, ptr %_12, i32 %i
  %_14 = load ptr, ptr %_13
  store ptr %_9, ptr %_13
  %_15 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_16 = getelementptr ptr, ptr %_15, i32 0
  %_17 = load ptr, ptr %_16
  %_18 = getelementptr ptr, ptr %_17, i32 %j
  %_19 = load ptr, ptr %_18
  store ptr %_4, ptr %_18
  br label %return16


return16:
  ret void

}

define void @Array_Node.doubleStorage(ptr %this) {
entry:
  %_0 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_1 = getelementptr ptr, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_4 = getelementptr i32, ptr %_3, i32 1
  %_5 = load i32, ptr %_4
  %_6 = call i32 @_arr_size(ptr %_2)
  %_7 = mul i32 %_6, 2
  %_8 = call ptr @_arr_init(i32 %_7)
  %_9 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_10 = getelementptr ptr, ptr %_9, i32 0
  %_11 = load ptr, ptr %_10
  store ptr %_8, ptr %_10
  %_12 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_13 = getelementptr i32, ptr %_12, i32 1
  %_14 = load i32, ptr %_13
  store i32 0, ptr %_13
  br label %for.cond2.0.3

for.cond2.0.3:
  %_15 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_16 = getelementptr i32, ptr %_15, i32 1
  %_17 = load i32, ptr %_16
  %_19 = icmp ne i32 %_17, %_5
  br i1 %_19, label %for.body2.0.3, label %for.end2.0.3

for.body2.0.3:
  %_21 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_22 = getelementptr i32, ptr %_21, i32 1
  %_23 = load i32, ptr %_22
  %_24 = getelementptr ptr, ptr %_2, i32 %_23
  %_25 = load ptr, ptr %_24
  %_26 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_27 = getelementptr ptr, ptr %_26, i32 0
  %_28 = load ptr, ptr %_27
  %_29 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_30 = getelementptr i32, ptr %_29, i32 1
  %_31 = load i32, ptr %_30
  %_32 = getelementptr ptr, ptr %_28, i32 %_31
  %_33 = load ptr, ptr %_32
  store ptr %_25, ptr %_32
  br label %for.inc2.0.3

for.inc2.0.3:
  %_34 = getelementptr %class.Array_Node, ptr %this, i32 0
  %_35 = getelementptr i32, ptr %_34, i32 1
  %_36 = load i32, ptr %_35
  %_37 = add i32 %_36, 1
  store i32 %_37, ptr %_35
  br label %for.cond2.0.3

for.end2.0.3:
  br label %return17


return17:
  ret void

}

define void @Heap_Node.Heap_Node(ptr %this) {
entry:
  %_0 = call ptr @_malloc(i32 2)
  call void @Array_Node.Array_Node(ptr %_0)
  %_1 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_2 = getelementptr %class.Array_Node, ptr %_1, i32 0
  %_3 = load ptr, ptr %_2
  store ptr %_0, ptr %_2
  br label %return18


return18:
  ret void

}

define void @Heap_Node.push(ptr %this, ptr %v) {
entry:
  %_0 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_1 = getelementptr %class.Array_Node, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  call void @Array_Node.push_back(ptr %_2, ptr %v)
  %_3 = call i32 @Heap_Node.size(ptr %this)
  %_4 = sub i32 %_3, 1
  br label %while.cond2.0.4

while.cond2.0.4:
  %p.3.19.while.cond2.0.4 = phi i32 [0, %entry], [ %_8 , %if.end4.0.1]
  %x.1.18.while.cond2.0.4 = phi i32 [ %_4 , %entry], [ %_8 , %if.end4.0.1]
  %_6 = icmp sgt i32 %x.1.18.while.cond2.0.4, 0
  br i1 %_6, label %while.body2.0.4, label %while.end2.0.4

while.body2.0.4:
  %_8 = call i32 @Heap_Node.pnt(ptr %this, i32 %x.1.18.while.cond2.0.4)
  %_9 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_10 = getelementptr %class.Array_Node, ptr %_9, i32 0
  %_11 = load ptr, ptr %_10
  %_12 = call ptr @Array_Node.get(ptr %_11, i32 %_8)
  %_13 = call i32 @Node.key_(ptr %_12)
  %_14 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_15 = getelementptr %class.Array_Node, ptr %_14, i32 0
  %_16 = load ptr, ptr %_15
  %_17 = call ptr @Array_Node.get(ptr %_16, i32 %x.1.18.while.cond2.0.4)
  %_18 = call i32 @Node.key_(ptr %_17)
  %_19 = icmp sge i32 %_13, %_18
  br i1 %_19, label %if.then4.0.1, label %if.else4.0.1

if.then4.0.1:
  br label %while.end2.0.4

if.else4.0.1:
  br label %if.end4.0.1

if.end4.0.1:
  %_20 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_21 = getelementptr %class.Array_Node, ptr %_20, i32 0
  %_22 = load ptr, ptr %_21
  call void @Array_Node.swap(ptr %_22, i32 %_8, i32 %x.1.18.while.cond2.0.4)
  br label %while.cond2.0.4

while.end2.0.4:
  %p.3.19.while.end2.0.4 = phi i32 [ %_8 , %if.then4.0.1], [ %p.3.19.while.cond2.0.4 , %while.cond2.0.4]
  br label %return19


return19:
  ret void

}

define ptr @Heap_Node.pop(ptr %this) {
entry:
  %_0 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_1 = getelementptr %class.Array_Node, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = call ptr @Array_Node.front(ptr %_2)
  %_4 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_5 = getelementptr %class.Array_Node, ptr %_4, i32 0
  %_6 = load ptr, ptr %_5
  %_7 = call i32 @Heap_Node.size(ptr %this)
  %_8 = sub i32 %_7, 1
  call void @Array_Node.swap(ptr %_6, i32 0, i32 %_8)
  %_9 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_10 = getelementptr %class.Array_Node, ptr %_9, i32 0
  %_11 = load ptr, ptr %_10
  %_12 = call ptr @Array_Node.pop_back(ptr %_11)
  call void @Heap_Node.maxHeapify(ptr %this, i32 0)
  br label %return20


return20:
  ret ptr %_3

}

define ptr @Heap_Node.top(ptr %this) {
entry:
  %_0 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_1 = getelementptr %class.Array_Node, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = call ptr @Array_Node.front(ptr %_2)
  br label %return21


return21:
  ret ptr %_3

}

define i32 @Heap_Node.size(ptr %this) {
entry:
  %_0 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_1 = getelementptr %class.Array_Node, ptr %_0, i32 0
  %_2 = load ptr, ptr %_1
  %_3 = call i32 @Array_Node.size(ptr %_2)
  br label %return22


return22:
  ret i32 %_3

}

define i32 @Heap_Node.lchild(ptr %this, i32 %x) {
entry:
  %_0 = mul i32 %x, 2
  %_1 = add i32 %_0, 1
  br label %return23


return23:
  ret i32 %_1

}

define i32 @Heap_Node.rchild(ptr %this, i32 %x) {
entry:
  %_0 = mul i32 %x, 2
  %_1 = add i32 %_0, 2
  br label %return24


return24:
  ret i32 %_1

}

define i32 @Heap_Node.pnt(ptr %this, i32 %x) {
entry:
  %_0 = sub i32 %x, 1
  %_1 = sdiv i32 %_0, 2
  br label %return25


return25:
  ret i32 %_1

}

define void @Heap_Node.maxHeapify(ptr %this, i32 %x) {
entry:
  %_0 = call i32 @Heap_Node.lchild(ptr %this, i32 %x)
  %_1 = call i32 @Heap_Node.rchild(ptr %this, i32 %x)
  br label %log.lhs2

log.lhs2:
  %_3 = call i32 @Heap_Node.size(ptr %this)
  %_4 = icmp slt i32 %_0, %_3
  br i1 %_4, label %log.rhs2, label %log.end2

log.rhs2:
  %_5 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_6 = getelementptr %class.Array_Node, ptr %_5, i32 0
  %_7 = load ptr, ptr %_6
  %_9 = call ptr @Array_Node.get(ptr %_7, i32 %_0)
  %_10 = call i32 @Node.key_(ptr %_9)
  %_11 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_12 = getelementptr %class.Array_Node, ptr %_11, i32 0
  %_13 = load ptr, ptr %_12
  %_15 = call ptr @Array_Node.get(ptr %_13, i32 %x)
  %_16 = call i32 @Node.key_(ptr %_15)
  %_17 = icmp sgt i32 %_10, %_16
  br label %log.end2

log.end2:
  %_18 = select i1 %_4, i1 %_17, i1 %_4
  br i1 %_18, label %if.then2.0.3, label %if.else2.0.3

if.then2.0.3:
  br label %if.end2.0.3

if.else2.0.3:
  br label %if.end2.0.3

if.end2.0.3:
  %largest.1.27.if.end2.0.3 = phi i32 [ %_0 , %if.then2.0.3], [ %x , %if.else2.0.3]
  br label %log.lhs4

log.lhs4:
  %_21 = call i32 @Heap_Node.size(ptr %this)
  %_22 = icmp slt i32 %_1, %_21
  br i1 %_22, label %log.rhs4, label %log.end4

log.rhs4:
  %_23 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_24 = getelementptr %class.Array_Node, ptr %_23, i32 0
  %_25 = load ptr, ptr %_24
  %_27 = call ptr @Array_Node.get(ptr %_25, i32 %_1)
  %_28 = call i32 @Node.key_(ptr %_27)
  %_29 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_30 = getelementptr %class.Array_Node, ptr %_29, i32 0
  %_31 = load ptr, ptr %_30
  %_33 = call ptr @Array_Node.get(ptr %_31, i32 %largest.1.27.if.end2.0.3)
  %_34 = call i32 @Node.key_(ptr %_33)
  %_35 = icmp sgt i32 %_28, %_34
  br label %log.end4

log.end4:
  %_36 = select i1 %_22, i1 %_35, i1 %_22
  br i1 %_36, label %if.then2.3.5, label %if.else2.3.5

if.then2.3.5:
  br label %if.end2.3.5

if.else2.3.5:
  br label %if.end2.3.5

if.end2.3.5:
  %largest.1.27.if.end2.3.5 = phi i32 [ %_1 , %if.then2.3.5], [ %largest.1.27.if.end2.0.3 , %if.else2.3.5]
  %_40 = icmp eq i32 %largest.1.27.if.end2.3.5, %x
  br i1 %_40, label %if.then2.6.6, label %if.else2.6.6

if.then2.6.6:
  br label %return26

if.else2.6.6:
  br label %if.end2.6.6

if.end2.6.6:
  %_41 = getelementptr %class.Heap_Node, ptr %this, i32 0
  %_42 = getelementptr %class.Array_Node, ptr %_41, i32 0
  %_43 = load ptr, ptr %_42
  call void @Array_Node.swap(ptr %_43, i32 %x, i32 %largest.1.27.if.end2.3.5)
  call void @Heap_Node.maxHeapify(ptr %this, i32 %largest.1.27.if.end2.3.5)
  br label %return26


return26:
  ret void

}

define void @init() {
entry:
  %_0 = call i32 @getInt()
  store i32 %_0, ptr @n.0.28
  %_1 = call i32 @getInt()
  store i32 %_1, ptr @m.0.29
  %_2 = call ptr @_malloc(i32 4)
  call void @EdgeList.EdgeList(ptr %_2)
  store ptr %_2, ptr @g.0.30
  call void @EdgeList.init(ptr %_2, i32 %_0, i32 %_1)
  br label %for.cond2.0.5

for.cond2.0.5:
  %u.3.33.for.cond2.0.5 = phi i32 [0, %entry], [ %_6 , %for.inc2.0.5]
  %i.1.32.for.cond2.0.5 = phi i32 [ 0 , %entry], [ %_11 , %for.inc2.0.5]
  %v.3.34.for.cond2.0.5 = phi i32 [0, %entry], [ %_7 , %for.inc2.0.5]
  %w.3.35.for.cond2.0.5 = phi i32 [0, %entry], [ %_8 , %for.inc2.0.5]
  %_4 = load i32, ptr @m.0.29
  %_5 = icmp slt i32 %i.1.32.for.cond2.0.5, %_4
  br i1 %_5, label %for.body2.0.5, label %for.end2.0.5

for.body2.0.5:
  %_6 = call i32 @getInt()
  %_7 = call i32 @getInt()
  %_8 = call i32 @getInt()
  %_9 = load ptr, ptr @g.0.30
  call void @EdgeList.addEdge(ptr %_9, i32 %_6, i32 %_7, i32 %_8)
  br label %for.inc2.0.5

for.inc2.0.5:
  %_11 = add i32 %i.1.32.for.cond2.0.5, 1
  br label %for.cond2.0.5

for.end2.0.5:
  br label %return27


return27:
  ret void

}

define void @Node.Node(ptr %this) {
entry:
  br label %return28


return28:
  ret void

}

define i32 @Node.key_(ptr %this) {
entry:
  %_0 = getelementptr %class.Node, ptr %this, i32 0
  %_1 = getelementptr i32, ptr %_0, i32 1
  %_2 = load i32, ptr %_1
  %_3 = sub i32 0, %_2
  br label %return29


return29:
  ret i32 %_3

}

define ptr @dijkstra(i32 %s) {
entry:
  %_0 = load i32, ptr @n.0.28
  %_1 = call ptr @_arr_init(i32 %_0)
  %_2 = call ptr @_arr_init(i32 %_0)
  br label %for.cond2.0.6

for.cond2.0.6:
  %i.1.39.for.cond2.0.6 = phi i32 [ 0 , %entry], [ %_15 , %for.inc2.0.6]
  %_4 = load i32, ptr @n.0.28
  %_5 = icmp slt i32 %i.1.39.for.cond2.0.6, %_4
  br i1 %_5, label %for.body2.0.6, label %for.end2.0.6

for.body2.0.6:
  %_6 = load i32, ptr @INF.0.31
  %_9 = getelementptr i32, ptr %_2, i32 %i.1.39.for.cond2.0.6
  %_10 = load i32, ptr %_9
  store i32 %_6, ptr %_9
  %_12 = getelementptr i32, ptr %_1, i32 %i.1.39.for.cond2.0.6
  %_13 = load i32, ptr %_12
  store i32 0, ptr %_12
  br label %for.inc2.0.6

for.inc2.0.6:
  %_15 = add i32 %i.1.39.for.cond2.0.6, 1
  br label %for.cond2.0.6

for.end2.0.6:
  %_18 = getelementptr i32, ptr %_2, i32 %s
  %_19 = load i32, ptr %_18
  store i32 0, ptr %_18
  %_20 = call ptr @_malloc(i32 1)
  call void @Heap_Node.Heap_Node(ptr %_20)
  %_21 = call ptr @_malloc(i32 2)
  call void @Node.Node(ptr %_21)
  %_22 = getelementptr %class.Node, ptr %_21, i32 0
  %_23 = getelementptr i32, ptr %_22, i32 1
  %_24 = load i32, ptr %_23
  store i32 0, ptr %_23
  %_25 = getelementptr %class.Node, ptr %_21, i32 0
  %_26 = getelementptr i32, ptr %_25, i32 0
  %_27 = load i32, ptr %_26
  store i32 %s, ptr %_26
  call void @Heap_Node.push(ptr %_20, ptr %_21)
  br label %while.cond2.1.7

while.cond2.1.7:
  %node.3.42.while.cond2.1.7 = phi ptr [ %node.3.42.for.cond4.3.8 , %for.end4.3.8], [null, %for.end2.0.6], [ %_32 , %if.then4.0.7]
  %v.5.45.while.cond2.1.7 = phi i32 [ %v.5.45.for.cond4.3.8 , %for.end4.3.8], [0, %for.end2.0.6], [ %v.5.45.while.cond2.1.7 , %if.then4.0.7]
  %u.3.43.while.cond2.1.7 = phi i32 [ %_35 , %for.end4.3.8], [0, %for.end2.0.6], [ %_35 , %if.then4.0.7]
  %alt.5.47.while.cond2.1.7 = phi i32 [ %alt.5.47.for.cond4.3.8 , %for.end4.3.8], [0, %for.end2.0.6], [ %alt.5.47.while.cond2.1.7 , %if.then4.0.7]
  %k.3.44.while.cond2.1.7 = phi i32 [ %k.3.44.for.cond4.3.8 , %for.end4.3.8], [0, %for.end2.0.6], [ %k.3.44.while.cond2.1.7 , %if.then4.0.7]
  %w.5.46.while.cond2.1.7 = phi i32 [ %w.5.46.for.cond4.3.8 , %for.end4.3.8], [0, %for.end2.0.6], [ %w.5.46.while.cond2.1.7 , %if.then4.0.7]
  %_29 = call i32 @Heap_Node.size(ptr %_20)
  %_30 = icmp ne i32 %_29, 0
  br i1 %_30, label %while.body2.1.7, label %while.end2.1.7

while.body2.1.7:
  %_32 = call ptr @Heap_Node.pop(ptr %_20)
  %_33 = getelementptr %class.Node, ptr %_32, i32 0
  %_34 = getelementptr i32, ptr %_33, i32 0
  %_35 = load i32, ptr %_34
  %_37 = getelementptr i32, ptr %_1, i32 %_35
  %_38 = load i32, ptr %_37
  %_39 = icmp eq i32 %_38, 1
  br i1 %_39, label %if.then4.0.7, label %if.else4.0.7

if.then4.0.7:
  br label %while.cond2.1.7

if.else4.0.7:
  br label %if.end4.0.7

if.end4.0.7:
  %_42 = getelementptr i32, ptr %_1, i32 %_35
  %_43 = load i32, ptr %_42
  store i32 1, ptr %_42
  %_44 = load ptr, ptr @g.0.30
  %_45 = getelementptr %class.EdgeList, ptr %_44, i32 0
  %_46 = getelementptr ptr, ptr %_45, i32 2
  %_47 = load ptr, ptr %_46
  %_48 = getelementptr i32, ptr %_47, i32 %_35
  %_49 = load i32, ptr %_48
  br label %for.cond4.3.8

for.cond4.3.8:
  %node.3.42.for.cond4.3.8 = phi ptr [ %_32 , %if.end4.0.7], [ %node.3.42.for.inc4.3.8 , %for.inc4.3.8]
  %v.5.45.for.cond4.3.8 = phi i32 [ %v.5.45.while.cond2.1.7 , %if.end4.0.7], [ %_62 , %for.inc4.3.8]
  %alt.5.47.for.cond4.3.8 = phi i32 [ %alt.5.47.while.cond2.1.7 , %if.end4.0.7], [ %_75 , %for.inc4.3.8]
  %k.3.44.for.cond4.3.8 = phi i32 [ %_49 , %if.end4.0.7], [ %_100 , %for.inc4.3.8]
  %w.5.46.for.cond4.3.8 = phi i32 [ %w.5.46.while.cond2.1.7 , %if.end4.0.7], [ %_70 , %for.inc4.3.8]
  %_51 = sub i32 0, 1
  %_52 = icmp ne i32 %k.3.44.for.cond4.3.8, %_51
  br i1 %_52, label %for.body4.3.8, label %for.end4.3.8

for.body4.3.8:
  %_53 = load ptr, ptr @g.0.30
  %_54 = getelementptr %class.EdgeList, ptr %_53, i32 0
  %_55 = getelementptr ptr, ptr %_54, i32 0
  %_56 = load ptr, ptr %_55
  %_58 = getelementptr ptr, ptr %_56, i32 %k.3.44.for.cond4.3.8
  %_59 = load ptr, ptr %_58
  %_60 = getelementptr %class.Edge, ptr %_59, i32 0
  %_61 = getelementptr i32, ptr %_60, i32 1
  %_62 = load i32, ptr %_61
  %_63 = getelementptr %class.EdgeList, ptr %_53, i32 0
  %_64 = getelementptr ptr, ptr %_63, i32 0
  %_65 = load ptr, ptr %_64
  %_66 = getelementptr ptr, ptr %_65, i32 %k.3.44.for.cond4.3.8
  %_67 = load ptr, ptr %_66
  %_68 = getelementptr %class.Edge, ptr %_67, i32 0
  %_69 = getelementptr i32, ptr %_68, i32 2
  %_70 = load i32, ptr %_69
  %_73 = getelementptr i32, ptr %_2, i32 %_35
  %_74 = load i32, ptr %_73
  %_75 = add i32 %_74, %_70
  %_76 = getelementptr i32, ptr %_2, i32 %_62
  %_77 = load i32, ptr %_76
  %_78 = icmp sge i32 %_75, %_77
  br i1 %_78, label %if.then6.0.8, label %if.else6.0.8

if.then6.0.8:
  br label %for.inc4.3.8

if.else6.0.8:
  br label %if.end6.0.8

if.end6.0.8:
  %_82 = getelementptr i32, ptr %_2, i32 %_62
  %_83 = load i32, ptr %_82
  store i32 %_75, ptr %_82
  %_84 = call ptr @_malloc(i32 2)
  call void @Node.Node(ptr %_84)
  %_85 = getelementptr %class.Node, ptr %_84, i32 0
  %_86 = getelementptr i32, ptr %_85, i32 0
  %_87 = load i32, ptr %_86
  store i32 %_62, ptr %_86
  %_88 = getelementptr i32, ptr %_2, i32 %_62
  %_89 = load i32, ptr %_88
  %_90 = getelementptr %class.Node, ptr %_84, i32 0
  %_91 = getelementptr i32, ptr %_90, i32 1
  %_92 = load i32, ptr %_91
  store i32 %_89, ptr %_91
  call void @Heap_Node.push(ptr %_20, ptr %_84)
  br label %for.inc4.3.8

for.inc4.3.8:
  %node.3.42.for.inc4.3.8 = phi ptr [ %node.3.42.for.cond4.3.8 , %if.then6.0.8], [ %_84 , %if.end6.0.8]
  %_94 = load ptr, ptr @g.0.30
  %_95 = getelementptr %class.EdgeList, ptr %_94, i32 0
  %_96 = getelementptr ptr, ptr %_95, i32 1
  %_97 = load ptr, ptr %_96
  %_99 = getelementptr i32, ptr %_97, i32 %k.3.44.for.cond4.3.8
  %_100 = load i32, ptr %_99
  br label %for.cond4.3.8

for.end4.3.8:
  br label %while.cond2.1.7

while.end2.1.7:
  br label %return30


return30:
  ret ptr %_2

}

define void @__init__() {
entry:
  store i32 10000000, ptr @INF.0.31
  br label %return0


return0:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  call void @init()
  br label %for.cond2.0.9

for.cond2.0.9:
  %i.1.48.for.cond2.0.9 = phi i32 [ 0 , %entry], [ %_22 , %for.inc2.0.9]
  %j.1.49.for.cond2.0.9 = phi i32 [0, %entry], [ %j.1.49.for.cond4.0.10 , %for.inc2.0.9]
  %d.3.50.for.cond2.0.9 = phi ptr [null, %entry], [ %_4 , %for.inc2.0.9]
  %_1 = load i32, ptr @n.0.28
  %_2 = icmp slt i32 %i.1.48.for.cond2.0.9, %_1
  br i1 %_2, label %for.body2.0.9, label %for.end2.0.9

for.body2.0.9:
  %_4 = call ptr @dijkstra(i32 %i.1.48.for.cond2.0.9)
  br label %for.cond4.0.10

for.cond4.0.10:
  %j.1.49.for.cond4.0.10 = phi i32 [ 0 , %for.body2.0.9], [ %_20 , %for.inc4.0.10]
  %_6 = load i32, ptr @n.0.28
  %_7 = icmp slt i32 %j.1.49.for.cond4.0.10, %_6
  br i1 %_7, label %for.body4.0.10, label %for.end4.0.10

for.body4.0.10:
  %_10 = getelementptr i32, ptr %_4, i32 %j.1.49.for.cond4.0.10
  %_11 = load i32, ptr %_10
  %_12 = load i32, ptr @INF.0.31
  %_13 = icmp eq i32 %_11, %_12
  br i1 %_13, label %if.then6.0.9, label %if.else6.0.9

if.then6.0.9:
  call void @print(ptr @.str.0)
  br label %if.end6.0.9

if.else6.0.9:
  %_16 = getelementptr i32, ptr %_4, i32 %j.1.49.for.cond4.0.10
  %_17 = load i32, ptr %_16
  %_18 = call ptr @toString(i32 %_17)
  call void @print(ptr %_18)
  br label %if.end6.0.9

if.end6.0.9:
  call void @print(ptr @.str.1)
  br label %for.inc4.0.10

for.inc4.0.10:
  %_20 = add i32 %j.1.49.for.cond4.0.10, 1
  br label %for.cond4.0.10

for.end4.0.10:
  call void @println(ptr @.str.2)
  br label %for.inc2.0.9

for.inc2.0.9:
  %_22 = add i32 %i.1.48.for.cond2.0.9, 1
  br label %for.cond2.0.9

for.end2.0.9:
  br label %return31


return31:
  ret i32 0

}

