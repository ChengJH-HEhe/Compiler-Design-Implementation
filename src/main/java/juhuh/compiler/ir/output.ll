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
  %this.addr= alloca ptr
  store ptr %this, ptr %this.addr
  %this.copy = load ptr, ptr %this.addr
  br label %return


return:
  ret void

}

define void @EdgeList.EdgeList(ptr %this) {
entry:
  %this.addr= alloca ptr
  store ptr %this, ptr %this.addr
  %this.copy = load ptr, ptr %this.addr
  br label %return


return:
  ret void

}

define void @EdgeList.init(ptr %this, i32 %n, i32 %m) {
entry:
  %this.addr.1= alloca ptr
  store ptr %this, ptr %this.addr.1
  %this.copy = load ptr, ptr %this.addr.1
  %n.1.0= alloca i32
  store i32 %n, ptr %n.1.0
  %m.1.1= alloca i32
  store i32 %m, ptr %m.1.1
  %0 = load i32, ptr %m.1.1
  %1 = call ptr @_arr_init(i32 %0)
  %2 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %3 = getelementptr ptr, ptr %2, i32 0
  %4 = load ptr, ptr %3
  store ptr %1, ptr %3
  %5 = load i32, ptr %m.1.1
  %6 = call ptr @_arr_init(i32 %5)
  %7 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %8 = getelementptr ptr, ptr %7, i32 1
  %9 = load ptr, ptr %8
  store ptr %6, ptr %8
  %10 = load i32, ptr %n.1.0
  %11 = call ptr @_arr_init(i32 %10)
  %12 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %13 = getelementptr ptr, ptr %12, i32 2
  %14 = load ptr, ptr %13
  store ptr %11, ptr %13
  %i.1.2= alloca i32
  %15 = load i32, ptr %i.1.2
  store i32 0, ptr %i.1.2
  br label %for.cond2.0.0

for.cond2.0.0:
  %16 = load i32, ptr %i.1.2
  %17 = load i32, ptr %m.1.1
  %18 = icmp slt i32 %16, %17
  br i1 %18, label %for.body2.0.0, label %for.end2.0.0

for.body2.0.0:
  %19 = sub i32 0, 1
  %20 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %21 = getelementptr ptr, ptr %20, i32 1
  %22 = load ptr, ptr %21
  %23 = load i32, ptr %i.1.2
  %24 = getelementptr i32, ptr %22, i32 %23
  %25 = load i32, ptr %24
  store i32 %19, ptr %24
  br label %for.inc2.0.0

for.inc2.0.0:
  %26 = load i32, ptr %i.1.2
  %27 = add i32 %26, 1
  store i32 %27, ptr %i.1.2
  br label %for.cond2.0.0

for.end2.0.0:
  %28 = load i32, ptr %i.1.2
  store i32 0, ptr %i.1.2
  br label %for.cond2.1.1

for.cond2.1.1:
  %29 = load i32, ptr %i.1.2
  %30 = load i32, ptr %n.1.0
  %31 = icmp slt i32 %29, %30
  br i1 %31, label %for.body2.1.1, label %for.end2.1.1

for.body2.1.1:
  %32 = sub i32 0, 1
  %33 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %34 = getelementptr ptr, ptr %33, i32 2
  %35 = load ptr, ptr %34
  %36 = load i32, ptr %i.1.2
  %37 = getelementptr i32, ptr %35, i32 %36
  %38 = load i32, ptr %37
  store i32 %32, ptr %37
  br label %for.inc2.1.1

for.inc2.1.1:
  %39 = load i32, ptr %i.1.2
  %40 = add i32 %39, 1
  store i32 %40, ptr %i.1.2
  br label %for.cond2.1.1

for.end2.1.1:
  %41 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %42 = getelementptr i32, ptr %41, i32 3
  %43 = load i32, ptr %42
  store i32 0, ptr %42
  br label %return


return:
  ret void

}

define void @EdgeList.addEdge(ptr %this, i32 %u, i32 %v, i32 %w) {
entry:
  %this.addr.2= alloca ptr
  store ptr %this, ptr %this.addr.2
  %this.copy = load ptr, ptr %this.addr.2
  %u.1.3= alloca i32
  store i32 %u, ptr %u.1.3
  %v.1.4= alloca i32
  store i32 %v, ptr %v.1.4
  %w.1.5= alloca i32
  store i32 %w, ptr %w.1.5
  %e.1.6= alloca ptr
  %0 = call ptr @_malloc(i32 3)
  call void @Edge.Edge(ptr %0)
  store ptr %0, ptr %e.1.6
  %1 = load i32, ptr %u.1.3
  %2 = load ptr, ptr %e.1.6
  %3 = getelementptr %class.Edge, ptr %2, i32 0
  %4 = getelementptr i32, ptr %3, i32 0
  %5 = load i32, ptr %4
  store i32 %1, ptr %4
  %6 = load i32, ptr %v.1.4
  %7 = load ptr, ptr %e.1.6
  %8 = getelementptr %class.Edge, ptr %7, i32 0
  %9 = getelementptr i32, ptr %8, i32 1
  %10 = load i32, ptr %9
  store i32 %6, ptr %9
  %11 = load i32, ptr %w.1.5
  %12 = load ptr, ptr %e.1.6
  %13 = getelementptr %class.Edge, ptr %12, i32 0
  %14 = getelementptr i32, ptr %13, i32 2
  %15 = load i32, ptr %14
  store i32 %11, ptr %14
  %16 = load ptr, ptr %e.1.6
  %17 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %18 = getelementptr ptr, ptr %17, i32 0
  %19 = load ptr, ptr %18
  %20 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %21 = getelementptr i32, ptr %20, i32 3
  %22 = load i32, ptr %21
  %23 = getelementptr ptr, ptr %19, i32 %22
  %24 = load ptr, ptr %23
  store ptr %16, ptr %23
  %25 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %26 = getelementptr ptr, ptr %25, i32 2
  %27 = load ptr, ptr %26
  %28 = load i32, ptr %u.1.3
  %29 = getelementptr i32, ptr %27, i32 %28
  %30 = load i32, ptr %29
  %31 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %32 = getelementptr ptr, ptr %31, i32 1
  %33 = load ptr, ptr %32
  %34 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %35 = getelementptr i32, ptr %34, i32 3
  %36 = load i32, ptr %35
  %37 = getelementptr i32, ptr %33, i32 %36
  %38 = load i32, ptr %37
  store i32 %30, ptr %37
  %39 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %40 = getelementptr i32, ptr %39, i32 3
  %41 = load i32, ptr %40
  %42 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %43 = getelementptr ptr, ptr %42, i32 2
  %44 = load ptr, ptr %43
  %45 = load i32, ptr %u.1.3
  %46 = getelementptr i32, ptr %44, i32 %45
  %47 = load i32, ptr %46
  store i32 %41, ptr %46
  %48 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %49 = getelementptr i32, ptr %48, i32 3
  %50 = load i32, ptr %49
  %51 = add i32 %50, 1
  store i32 %51, ptr %49
  br label %return


return:
  ret void

}

define i32 @EdgeList.nVertices(ptr %this) {
entry:
  %ret.val= alloca i32
  %this.addr.3= alloca ptr
  store ptr %this, ptr %this.addr.3
  %this.copy = load ptr, ptr %this.addr.3
  %0 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 2
  %2 = load ptr, ptr %1
  %3 = call i32 @_arr_size(ptr %2)
  store i32 %3, ptr %ret.val
  br label %return


return:
  %4 = load i32, ptr %ret.val
  ret i32 %4

}

define i32 @EdgeList.nEdges(ptr %this) {
entry:
  %ret.val= alloca i32
  %this.addr.4= alloca ptr
  store ptr %this, ptr %this.addr.4
  %this.copy = load ptr, ptr %this.addr.4
  %0 = getelementptr %class.EdgeList, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = call i32 @_arr_size(ptr %2)
  store i32 %3, ptr %ret.val
  br label %return


return:
  %4 = load i32, ptr %ret.val
  ret i32 %4

}

define void @Array_Node.Array_Node(ptr %this) {
entry:
  %this.addr= alloca ptr
  store ptr %this, ptr %this.addr
  %this.copy = load ptr, ptr %this.addr
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr i32, ptr %0, i32 1
  %2 = load i32, ptr %1
  store i32 0, ptr %1
  %3 = call ptr @_arr_init(i32 16)
  %4 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %5 = getelementptr ptr, ptr %4, i32 0
  %6 = load ptr, ptr %5
  store ptr %3, ptr %5
  br label %return


return:
  ret void

}

define void @Array_Node.push_back(ptr %this, ptr %v) {
entry:
  %this.addr.0= alloca ptr
  store ptr %this, ptr %this.addr.0
  %this.copy = load ptr, ptr %this.addr.0
  %v.1.7= alloca ptr
  store ptr %v, ptr %v.1.7
  %0 = call i32 @Array_Node.size(ptr %this.copy)
  %1 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %2 = getelementptr ptr, ptr %1, i32 0
  %3 = load ptr, ptr %2
  %4 = call i32 @_arr_size(ptr %3)
  %5 = icmp eq i32 %0, %4
  br i1 %5, label %if.then2.0.0, label %if.else2.0.0

if.then2.0.0:
  call void @Array_Node.doubleStorage(ptr %this.copy)
  br label %if.end2.0.0

if.else2.0.0:
  br label %if.end2.0.0

if.end2.0.0:
  %6 = load ptr, ptr %v.1.7
  %7 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %8 = getelementptr ptr, ptr %7, i32 0
  %9 = load ptr, ptr %8
  %10 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %11 = getelementptr i32, ptr %10, i32 1
  %12 = load i32, ptr %11
  %13 = getelementptr ptr, ptr %9, i32 %12
  %14 = load ptr, ptr %13
  store ptr %6, ptr %13
  %15 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %16 = getelementptr i32, ptr %15, i32 1
  %17 = load i32, ptr %16
  %18 = add i32 %17, 1
  store i32 %18, ptr %16
  br label %return


return:
  ret void

}

define ptr @Array_Node.pop_back(ptr %this) {
entry:
  %ret.val= alloca ptr
  %this.addr.1= alloca ptr
  store ptr %this, ptr %this.addr.1
  %this.copy = load ptr, ptr %this.addr.1
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr i32, ptr %0, i32 1
  %2 = load i32, ptr %1
  %3 = sub i32 %2, 1
  store i32 %3, ptr %1
  %4 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %5 = getelementptr ptr, ptr %4, i32 0
  %6 = load ptr, ptr %5
  %7 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %8 = getelementptr i32, ptr %7, i32 1
  %9 = load i32, ptr %8
  %10 = getelementptr ptr, ptr %6, i32 %9
  %11 = load ptr, ptr %10
  store ptr %11, ptr %ret.val
  br label %return


return:
  %12 = load ptr, ptr %ret.val
  ret ptr %12

}

define ptr @Array_Node.back(ptr %this) {
entry:
  %ret.val= alloca ptr
  %this.addr.2= alloca ptr
  store ptr %this, ptr %this.addr.2
  %this.copy = load ptr, ptr %this.addr.2
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %4 = getelementptr i32, ptr %3, i32 1
  %5 = load i32, ptr %4
  %6 = sub i32 %5, 1
  %7 = getelementptr ptr, ptr %2, i32 %6
  %8 = load ptr, ptr %7
  store ptr %8, ptr %ret.val
  br label %return


return:
  %9 = load ptr, ptr %ret.val
  ret ptr %9

}

define ptr @Array_Node.front(ptr %this) {
entry:
  %ret.val= alloca ptr
  %this.addr.3= alloca ptr
  store ptr %this, ptr %this.addr.3
  %this.copy = load ptr, ptr %this.addr.3
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = getelementptr ptr, ptr %2, i32 0
  %4 = load ptr, ptr %3
  store ptr %4, ptr %ret.val
  br label %return


return:
  %5 = load ptr, ptr %ret.val
  ret ptr %5

}

define i32 @Array_Node.size(ptr %this) {
entry:
  %ret.val= alloca i32
  %this.addr.4= alloca ptr
  store ptr %this, ptr %this.addr.4
  %this.copy = load ptr, ptr %this.addr.4
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr i32, ptr %0, i32 1
  %2 = load i32, ptr %1
  store i32 %2, ptr %ret.val
  br label %return


return:
  %3 = load i32, ptr %ret.val
  ret i32 %3

}

define void @Array_Node.resize(ptr %this, i32 %newSize) {
entry:
  %this.addr.5= alloca ptr
  store ptr %this, ptr %this.addr.5
  %this.copy = load ptr, ptr %this.addr.5
  %newSize.1.8= alloca i32
  store i32 %newSize, ptr %newSize.1.8
  br label %while.cond2.0.2

while.cond2.0.2:
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = call i32 @_arr_size(ptr %2)
  %4 = load i32, ptr %newSize.1.8
  %5 = icmp slt i32 %3, %4
  br i1 %5, label %while.body2.0.2, label %while.end2.0.2

while.body2.0.2:
  call void @Array_Node.doubleStorage(ptr %this.copy)
  br label %while.cond2.0.2

while.end2.0.2:
  %6 = load i32, ptr %newSize.1.8
  %7 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %8 = getelementptr i32, ptr %7, i32 1
  %9 = load i32, ptr %8
  store i32 %6, ptr %8
  br label %return


return:
  ret void

}

define ptr @Array_Node.get(ptr %this, i32 %i) {
entry:
  %ret.val= alloca ptr
  %this.addr.6= alloca ptr
  store ptr %this, ptr %this.addr.6
  %this.copy = load ptr, ptr %this.addr.6
  %i.1.9= alloca i32
  store i32 %i, ptr %i.1.9
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = load i32, ptr %i.1.9
  %4 = getelementptr ptr, ptr %2, i32 %3
  %5 = load ptr, ptr %4
  store ptr %5, ptr %ret.val
  br label %return


return:
  %6 = load ptr, ptr %ret.val
  ret ptr %6

}

define void @Array_Node.set(ptr %this, i32 %i, ptr %v) {
entry:
  %this.addr.7= alloca ptr
  store ptr %this, ptr %this.addr.7
  %this.copy = load ptr, ptr %this.addr.7
  %i.1.10= alloca i32
  store i32 %i, ptr %i.1.10
  %v.1.11= alloca ptr
  store ptr %v, ptr %v.1.11
  %0 = load ptr, ptr %v.1.11
  %1 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %2 = getelementptr ptr, ptr %1, i32 0
  %3 = load ptr, ptr %2
  %4 = load i32, ptr %i.1.10
  %5 = getelementptr ptr, ptr %3, i32 %4
  %6 = load ptr, ptr %5
  store ptr %0, ptr %5
  br label %return


return:
  ret void

}

define void @Array_Node.swap(ptr %this, i32 %i, i32 %j) {
entry:
  %this.addr.8= alloca ptr
  store ptr %this, ptr %this.addr.8
  %this.copy = load ptr, ptr %this.addr.8
  %i.1.12= alloca i32
  store i32 %i, ptr %i.1.12
  %j.1.13= alloca i32
  store i32 %j, ptr %j.1.13
  %t.1.14= alloca ptr
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = load i32, ptr %i.1.12
  %4 = getelementptr ptr, ptr %2, i32 %3
  %5 = load ptr, ptr %4
  store ptr %5, ptr %t.1.14
  %6 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %7 = getelementptr ptr, ptr %6, i32 0
  %8 = load ptr, ptr %7
  %9 = load i32, ptr %j.1.13
  %10 = getelementptr ptr, ptr %8, i32 %9
  %11 = load ptr, ptr %10
  %12 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %13 = getelementptr ptr, ptr %12, i32 0
  %14 = load ptr, ptr %13
  %15 = load i32, ptr %i.1.12
  %16 = getelementptr ptr, ptr %14, i32 %15
  %17 = load ptr, ptr %16
  store ptr %11, ptr %16
  %18 = load ptr, ptr %t.1.14
  %19 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %20 = getelementptr ptr, ptr %19, i32 0
  %21 = load ptr, ptr %20
  %22 = load i32, ptr %j.1.13
  %23 = getelementptr ptr, ptr %21, i32 %22
  %24 = load ptr, ptr %23
  store ptr %18, ptr %23
  br label %return


return:
  ret void

}

define void @Array_Node.doubleStorage(ptr %this) {
entry:
  %this.addr.9= alloca ptr
  store ptr %this, ptr %this.addr.9
  %this.copy = load ptr, ptr %this.addr.9
  %copy.1.15= alloca ptr
  %0 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  store ptr %2, ptr %copy.1.15
  %szCopy.1.16= alloca i32
  %3 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %4 = getelementptr i32, ptr %3, i32 1
  %5 = load i32, ptr %4
  store i32 %5, ptr %szCopy.1.16
  %6 = load ptr, ptr %copy.1.15
  %7 = call i32 @_arr_size(ptr %6)
  %8 = mul i32 %7, 2
  %9 = call ptr @_arr_init(i32 %8)
  %10 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %11 = getelementptr ptr, ptr %10, i32 0
  %12 = load ptr, ptr %11
  store ptr %9, ptr %11
  %13 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %14 = getelementptr i32, ptr %13, i32 1
  %15 = load i32, ptr %14
  store i32 0, ptr %14
  br label %for.cond2.0.3

for.cond2.0.3:
  %16 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %17 = getelementptr i32, ptr %16, i32 1
  %18 = load i32, ptr %17
  %19 = load i32, ptr %szCopy.1.16
  %20 = icmp ne i32 %18, %19
  br i1 %20, label %for.body2.0.3, label %for.end2.0.3

for.body2.0.3:
  %21 = load ptr, ptr %copy.1.15
  %22 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %23 = getelementptr i32, ptr %22, i32 1
  %24 = load i32, ptr %23
  %25 = getelementptr ptr, ptr %21, i32 %24
  %26 = load ptr, ptr %25
  %27 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %28 = getelementptr ptr, ptr %27, i32 0
  %29 = load ptr, ptr %28
  %30 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %31 = getelementptr i32, ptr %30, i32 1
  %32 = load i32, ptr %31
  %33 = getelementptr ptr, ptr %29, i32 %32
  %34 = load ptr, ptr %33
  store ptr %26, ptr %33
  br label %for.inc2.0.3

for.inc2.0.3:
  %35 = getelementptr %class.Array_Node, ptr %this.copy, i32 0
  %36 = getelementptr i32, ptr %35, i32 1
  %37 = load i32, ptr %36
  %38 = add i32 %37, 1
  store i32 %38, ptr %36
  br label %for.cond2.0.3

for.end2.0.3:
  br label %return


return:
  ret void

}

define void @Heap_Node.Heap_Node(ptr %this) {
entry:
  %this.addr= alloca ptr
  store ptr %this, ptr %this.addr
  %this.copy = load ptr, ptr %this.addr
  %0 = call ptr @_malloc(i32 2)
  call void @Array_Node.Array_Node(ptr %0)
  %1 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %2 = getelementptr %class.Array_Node, ptr %1, i32 0
  %3 = load ptr, ptr %2
  store ptr %0, ptr %2
  br label %return


return:
  ret void

}

define void @Heap_Node.push(ptr %this, ptr %v) {
entry:
  %this.addr.0= alloca ptr
  store ptr %this, ptr %this.addr.0
  %this.copy = load ptr, ptr %this.addr.0
  %v.1.17= alloca ptr
  store ptr %v, ptr %v.1.17
  %0 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %1 = getelementptr %class.Array_Node, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = load ptr, ptr %v.1.17
  call void @Array_Node.push_back(ptr %2, ptr %3)
  %x.1.18= alloca i32
  %4 = call i32 @Heap_Node.size(ptr %this.copy)
  %5 = sub i32 %4, 1
  store i32 %5, ptr %x.1.18
  %p.3.19= alloca i32
  br label %while.cond2.0.4

while.cond2.0.4:
  %6 = load i32, ptr %x.1.18
  %7 = icmp sgt i32 %6, 0
  br i1 %7, label %while.body2.0.4, label %while.end2.0.4

while.body2.0.4:
  %8 = load i32, ptr %x.1.18
  %9 = call i32 @Heap_Node.pnt(ptr %this.copy, i32 %8)
  store i32 %9, ptr %p.3.19
  %10 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %11 = getelementptr %class.Array_Node, ptr %10, i32 0
  %12 = load ptr, ptr %11
  %13 = load i32, ptr %p.3.19
  %14 = call ptr @Array_Node.get(ptr %12, i32 %13)
  %15 = call i32 @Node.key_(ptr %14)
  %16 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %17 = getelementptr %class.Array_Node, ptr %16, i32 0
  %18 = load ptr, ptr %17
  %19 = load i32, ptr %x.1.18
  %20 = call ptr @Array_Node.get(ptr %18, i32 %19)
  %21 = call i32 @Node.key_(ptr %20)
  %22 = icmp sge i32 %15, %21
  br i1 %22, label %if.then4.0.1, label %if.else4.0.1

if.then4.0.1:
  br label %while.end2.0.4

if.else4.0.1:
  br label %if.end4.0.1

if.end4.0.1:
  %23 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %24 = getelementptr %class.Array_Node, ptr %23, i32 0
  %25 = load ptr, ptr %24
  %26 = load i32, ptr %p.3.19
  %27 = load i32, ptr %x.1.18
  call void @Array_Node.swap(ptr %25, i32 %26, i32 %27)
  %28 = load i32, ptr %p.3.19
  %29 = load i32, ptr %x.1.18
  store i32 %28, ptr %x.1.18
  br label %while.cond2.0.4

while.end2.0.4:
  br label %return


return:
  ret void

}

define ptr @Heap_Node.pop(ptr %this) {
entry:
  %ret.val= alloca ptr
  %this.addr.1= alloca ptr
  store ptr %this, ptr %this.addr.1
  %this.copy = load ptr, ptr %this.addr.1
  %res.1.20= alloca ptr
  %0 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %1 = getelementptr %class.Array_Node, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = call ptr @Array_Node.front(ptr %2)
  store ptr %3, ptr %res.1.20
  %4 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %5 = getelementptr %class.Array_Node, ptr %4, i32 0
  %6 = load ptr, ptr %5
  %7 = call i32 @Heap_Node.size(ptr %this.copy)
  %8 = sub i32 %7, 1
  call void @Array_Node.swap(ptr %6, i32 0, i32 %8)
  %9 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %10 = getelementptr %class.Array_Node, ptr %9, i32 0
  %11 = load ptr, ptr %10
  %12 = call ptr @Array_Node.pop_back(ptr %11)
  call void @Heap_Node.maxHeapify(ptr %this.copy, i32 0)
  %13 = load ptr, ptr %res.1.20
  store ptr %13, ptr %ret.val
  br label %return


return:
  %14 = load ptr, ptr %ret.val
  ret ptr %14

}

define ptr @Heap_Node.top(ptr %this) {
entry:
  %ret.val= alloca ptr
  %this.addr.2= alloca ptr
  store ptr %this, ptr %this.addr.2
  %this.copy = load ptr, ptr %this.addr.2
  %0 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %1 = getelementptr %class.Array_Node, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = call ptr @Array_Node.front(ptr %2)
  store ptr %3, ptr %ret.val
  br label %return


return:
  %4 = load ptr, ptr %ret.val
  ret ptr %4

}

define i32 @Heap_Node.size(ptr %this) {
entry:
  %ret.val= alloca i32
  %this.addr.3= alloca ptr
  store ptr %this, ptr %this.addr.3
  %this.copy = load ptr, ptr %this.addr.3
  %0 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %1 = getelementptr %class.Array_Node, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = call i32 @Array_Node.size(ptr %2)
  store i32 %3, ptr %ret.val
  br label %return


return:
  %4 = load i32, ptr %ret.val
  ret i32 %4

}

define i32 @Heap_Node.lchild(ptr %this, i32 %x) {
entry:
  %ret.val= alloca i32
  %this.addr.4= alloca ptr
  store ptr %this, ptr %this.addr.4
  %this.copy = load ptr, ptr %this.addr.4
  %x.1.21= alloca i32
  store i32 %x, ptr %x.1.21
  %0 = load i32, ptr %x.1.21
  %1 = mul i32 %0, 2
  %2 = add i32 %1, 1
  store i32 %2, ptr %ret.val
  br label %return


return:
  %3 = load i32, ptr %ret.val
  ret i32 %3

}

define i32 @Heap_Node.rchild(ptr %this, i32 %x) {
entry:
  %ret.val= alloca i32
  %this.addr.5= alloca ptr
  store ptr %this, ptr %this.addr.5
  %this.copy = load ptr, ptr %this.addr.5
  %x.1.22= alloca i32
  store i32 %x, ptr %x.1.22
  %0 = load i32, ptr %x.1.22
  %1 = mul i32 %0, 2
  %2 = add i32 %1, 2
  store i32 %2, ptr %ret.val
  br label %return


return:
  %3 = load i32, ptr %ret.val
  ret i32 %3

}

define i32 @Heap_Node.pnt(ptr %this, i32 %x) {
entry:
  %ret.val= alloca i32
  %this.addr.6= alloca ptr
  store ptr %this, ptr %this.addr.6
  %this.copy = load ptr, ptr %this.addr.6
  %x.1.23= alloca i32
  store i32 %x, ptr %x.1.23
  %0 = load i32, ptr %x.1.23
  %1 = sub i32 %0, 1
  %2 = sdiv i32 %1, 2
  store i32 %2, ptr %ret.val
  br label %return


return:
  %3 = load i32, ptr %ret.val
  ret i32 %3

}

define void @Heap_Node.maxHeapify(ptr %this, i32 %x) {
entry:
  %this.addr.7= alloca ptr
  store ptr %this, ptr %this.addr.7
  %this.copy = load ptr, ptr %this.addr.7
  %x.1.24= alloca i32
  store i32 %x, ptr %x.1.24
  %l.1.25= alloca i32
  %0 = load i32, ptr %x.1.24
  %1 = call i32 @Heap_Node.lchild(ptr %this.copy, i32 %0)
  store i32 %1, ptr %l.1.25
  %r.1.26= alloca i32
  %2 = load i32, ptr %x.1.24
  %3 = call i32 @Heap_Node.rchild(ptr %this.copy, i32 %2)
  store i32 %3, ptr %r.1.26
  %largest.1.27= alloca i32
  %4 = load i32, ptr %x.1.24
  store i32 %4, ptr %largest.1.27
  br label %log.lhs2

log.lhs2:
  %5 = load i32, ptr %l.1.25
  %6 = call i32 @Heap_Node.size(ptr %this.copy)
  %7 = icmp slt i32 %5, %6
  br i1 %7, label %log.rhs2, label %log.end2

log.rhs2:
  %8 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %9 = getelementptr %class.Array_Node, ptr %8, i32 0
  %10 = load ptr, ptr %9
  %11 = load i32, ptr %l.1.25
  %12 = call ptr @Array_Node.get(ptr %10, i32 %11)
  %13 = call i32 @Node.key_(ptr %12)
  %14 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %15 = getelementptr %class.Array_Node, ptr %14, i32 0
  %16 = load ptr, ptr %15
  %17 = load i32, ptr %largest.1.27
  %18 = call ptr @Array_Node.get(ptr %16, i32 %17)
  %19 = call i32 @Node.key_(ptr %18)
  %20 = icmp sgt i32 %13, %19
  br label %log.end2

log.end2:
  %21 = select i1 %7, i1 %20, i1 %7
  br i1 %21, label %if.then2.0.3, label %if.else2.0.3

if.then2.0.3:
  %22 = load i32, ptr %l.1.25
  %23 = load i32, ptr %largest.1.27
  store i32 %22, ptr %largest.1.27
  br label %if.end2.0.3

if.else2.0.3:
  br label %if.end2.0.3

if.end2.0.3:
  br label %log.lhs4

log.lhs4:
  %24 = load i32, ptr %r.1.26
  %25 = call i32 @Heap_Node.size(ptr %this.copy)
  %26 = icmp slt i32 %24, %25
  br i1 %26, label %log.rhs4, label %log.end4

log.rhs4:
  %27 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %28 = getelementptr %class.Array_Node, ptr %27, i32 0
  %29 = load ptr, ptr %28
  %30 = load i32, ptr %r.1.26
  %31 = call ptr @Array_Node.get(ptr %29, i32 %30)
  %32 = call i32 @Node.key_(ptr %31)
  %33 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %34 = getelementptr %class.Array_Node, ptr %33, i32 0
  %35 = load ptr, ptr %34
  %36 = load i32, ptr %largest.1.27
  %37 = call ptr @Array_Node.get(ptr %35, i32 %36)
  %38 = call i32 @Node.key_(ptr %37)
  %39 = icmp sgt i32 %32, %38
  br label %log.end4

log.end4:
  %40 = select i1 %26, i1 %39, i1 %26
  br i1 %40, label %if.then2.3.5, label %if.else2.3.5

if.then2.3.5:
  %41 = load i32, ptr %r.1.26
  %42 = load i32, ptr %largest.1.27
  store i32 %41, ptr %largest.1.27
  br label %if.end2.3.5

if.else2.3.5:
  br label %if.end2.3.5

if.end2.3.5:
  %43 = load i32, ptr %largest.1.27
  %44 = load i32, ptr %x.1.24
  %45 = icmp eq i32 %43, %44
  br i1 %45, label %if.then2.6.6, label %if.else2.6.6

if.then2.6.6:
  br label %return

if.else2.6.6:
  br label %if.end2.6.6

if.end2.6.6:
  %46 = getelementptr %class.Heap_Node, ptr %this.copy, i32 0
  %47 = getelementptr %class.Array_Node, ptr %46, i32 0
  %48 = load ptr, ptr %47
  %49 = load i32, ptr %x.1.24
  %50 = load i32, ptr %largest.1.27
  call void @Array_Node.swap(ptr %48, i32 %49, i32 %50)
  %51 = load i32, ptr %largest.1.27
  call void @Heap_Node.maxHeapify(ptr %this.copy, i32 %51)
  br label %return


return:
  ret void

}

define void @init() {
entry:
  %0 = call i32 @getInt()
  %1 = load i32, ptr @n.0.28
  store i32 %0, ptr @n.0.28
  %2 = call i32 @getInt()
  %3 = load i32, ptr @m.0.29
  store i32 %2, ptr @m.0.29
  %4 = call ptr @_malloc(i32 4)
  call void @EdgeList.EdgeList(ptr %4)
  %5 = load ptr, ptr @g.0.30
  store ptr %4, ptr @g.0.30
  %6 = load ptr, ptr @g.0.30
  %7 = load i32, ptr @n.0.28
  %8 = load i32, ptr @m.0.29
  call void @EdgeList.init(ptr %6, i32 %7, i32 %8)
  %i.1.32= alloca i32
  %9 = load i32, ptr %i.1.32
  store i32 0, ptr %i.1.32
  %u.3.33= alloca i32
  %v.3.34= alloca i32
  %w.3.35= alloca i32
  br label %for.cond2.0.5

for.cond2.0.5:
  %10 = load i32, ptr %i.1.32
  %11 = load i32, ptr @m.0.29
  %12 = icmp slt i32 %10, %11
  br i1 %12, label %for.body2.0.5, label %for.end2.0.5

for.body2.0.5:
  %13 = call i32 @getInt()
  store i32 %13, ptr %u.3.33
  %14 = call i32 @getInt()
  store i32 %14, ptr %v.3.34
  %15 = call i32 @getInt()
  store i32 %15, ptr %w.3.35
  %16 = load ptr, ptr @g.0.30
  %17 = load i32, ptr %u.3.33
  %18 = load i32, ptr %v.3.34
  %19 = load i32, ptr %w.3.35
  call void @EdgeList.addEdge(ptr %16, i32 %17, i32 %18, i32 %19)
  br label %for.inc2.0.5

for.inc2.0.5:
  %20 = load i32, ptr %i.1.32
  %21 = add i32 %20, 1
  store i32 %21, ptr %i.1.32
  br label %for.cond2.0.5

for.end2.0.5:
  br label %return


return:
  ret void

}

define void @Node.Node(ptr %this) {
entry:
  %this.addr= alloca ptr
  store ptr %this, ptr %this.addr
  %this.copy = load ptr, ptr %this.addr
  br label %return


return:
  ret void

}

define i32 @Node.key_(ptr %this) {
entry:
  %ret.val= alloca i32
  %this.addr.1= alloca ptr
  store ptr %this, ptr %this.addr.1
  %this.copy = load ptr, ptr %this.addr.1
  %0 = getelementptr %class.Node, ptr %this.copy, i32 0
  %1 = getelementptr i32, ptr %0, i32 1
  %2 = load i32, ptr %1
  %3 = sub i32 0, %2
  store i32 %3, ptr %ret.val
  br label %return


return:
  %4 = load i32, ptr %ret.val
  ret i32 %4

}

define ptr @dijkstra(i32 %s) {
entry:
  %ret.val= alloca ptr
  %s.1.36= alloca i32
  store i32 %s, ptr %s.1.36
  %visited.1.37= alloca ptr
  %0 = load i32, ptr @n.0.28
  %1 = call ptr @_arr_init(i32 %0)
  store ptr %1, ptr %visited.1.37
  %d.1.38= alloca ptr
  %2 = load i32, ptr @n.0.28
  %3 = call ptr @_arr_init(i32 %2)
  store ptr %3, ptr %d.1.38
  %i.1.39= alloca i32
  %4 = load i32, ptr %i.1.39
  store i32 0, ptr %i.1.39
  %q.1.40= alloca ptr
  %src.1.41= alloca ptr
  %node.3.42= alloca ptr
  %u.3.43= alloca i32
  %k.3.44= alloca i32
  %v.5.45= alloca i32
  %w.5.46= alloca i32
  %alt.5.47= alloca i32
  br label %for.cond2.0.6

for.cond2.0.6:
  %5 = load i32, ptr %i.1.39
  %6 = load i32, ptr @n.0.28
  %7 = icmp slt i32 %5, %6
  br i1 %7, label %for.body2.0.6, label %for.end2.0.6

for.body2.0.6:
  %8 = load i32, ptr @INF.0.31
  %9 = load ptr, ptr %d.1.38
  %10 = load i32, ptr %i.1.39
  %11 = getelementptr i32, ptr %9, i32 %10
  %12 = load i32, ptr %11
  store i32 %8, ptr %11
  %13 = load ptr, ptr %visited.1.37
  %14 = load i32, ptr %i.1.39
  %15 = getelementptr i32, ptr %13, i32 %14
  %16 = load i32, ptr %15
  store i32 0, ptr %15
  br label %for.inc2.0.6

for.inc2.0.6:
  %17 = load i32, ptr %i.1.39
  %18 = add i32 %17, 1
  store i32 %18, ptr %i.1.39
  br label %for.cond2.0.6

for.end2.0.6:
  %19 = load ptr, ptr %d.1.38
  %20 = load i32, ptr %s.1.36
  %21 = getelementptr i32, ptr %19, i32 %20
  %22 = load i32, ptr %21
  store i32 0, ptr %21
  %23 = call ptr @_malloc(i32 1)
  call void @Heap_Node.Heap_Node(ptr %23)
  store ptr %23, ptr %q.1.40
  %24 = call ptr @_malloc(i32 2)
  call void @Node.Node(ptr %24)
  store ptr %24, ptr %src.1.41
  %25 = load ptr, ptr %src.1.41
  %26 = getelementptr %class.Node, ptr %25, i32 0
  %27 = getelementptr i32, ptr %26, i32 1
  %28 = load i32, ptr %27
  store i32 0, ptr %27
  %29 = load i32, ptr %s.1.36
  %30 = load ptr, ptr %src.1.41
  %31 = getelementptr %class.Node, ptr %30, i32 0
  %32 = getelementptr i32, ptr %31, i32 0
  %33 = load i32, ptr %32
  store i32 %29, ptr %32
  %34 = load ptr, ptr %q.1.40
  %35 = load ptr, ptr %src.1.41
  call void @Heap_Node.push(ptr %34, ptr %35)
  br label %while.cond2.1.7

while.cond2.1.7:
  %36 = load ptr, ptr %q.1.40
  %37 = call i32 @Heap_Node.size(ptr %36)
  %38 = icmp ne i32 %37, 0
  br i1 %38, label %while.body2.1.7, label %while.end2.1.7

while.body2.1.7:
  %39 = load ptr, ptr %q.1.40
  %40 = call ptr @Heap_Node.pop(ptr %39)
  store ptr %40, ptr %node.3.42
  %41 = load ptr, ptr %node.3.42
  %42 = getelementptr %class.Node, ptr %41, i32 0
  %43 = getelementptr i32, ptr %42, i32 0
  %44 = load i32, ptr %43
  store i32 %44, ptr %u.3.43
  %45 = load ptr, ptr %visited.1.37
  %46 = load i32, ptr %u.3.43
  %47 = getelementptr i32, ptr %45, i32 %46
  %48 = load i32, ptr %47
  %49 = icmp eq i32 %48, 1
  br i1 %49, label %if.then4.0.7, label %if.else4.0.7

if.then4.0.7:
  br label %while.cond2.1.7

if.else4.0.7:
  br label %if.end4.0.7

if.end4.0.7:
  %50 = load ptr, ptr %visited.1.37
  %51 = load i32, ptr %u.3.43
  %52 = getelementptr i32, ptr %50, i32 %51
  %53 = load i32, ptr %52
  store i32 1, ptr %52
  %54 = load ptr, ptr @g.0.30
  %55 = getelementptr %class.EdgeList, ptr %54, i32 0
  %56 = getelementptr ptr, ptr %55, i32 2
  %57 = load ptr, ptr %56
  %58 = load i32, ptr %u.3.43
  %59 = getelementptr i32, ptr %57, i32 %58
  %60 = load i32, ptr %59
  %61 = load i32, ptr %k.3.44
  store i32 %60, ptr %k.3.44
  br label %for.cond4.3.8

for.cond4.3.8:
  %62 = load i32, ptr %k.3.44
  %63 = sub i32 0, 1
  %64 = icmp ne i32 %62, %63
  br i1 %64, label %for.body4.3.8, label %for.end4.3.8

for.body4.3.8:
  %65 = load ptr, ptr @g.0.30
  %66 = getelementptr %class.EdgeList, ptr %65, i32 0
  %67 = getelementptr ptr, ptr %66, i32 0
  %68 = load ptr, ptr %67
  %69 = load i32, ptr %k.3.44
  %70 = getelementptr ptr, ptr %68, i32 %69
  %71 = load ptr, ptr %70
  %72 = getelementptr %class.Edge, ptr %71, i32 0
  %73 = getelementptr i32, ptr %72, i32 1
  %74 = load i32, ptr %73
  store i32 %74, ptr %v.5.45
  %75 = load ptr, ptr @g.0.30
  %76 = getelementptr %class.EdgeList, ptr %75, i32 0
  %77 = getelementptr ptr, ptr %76, i32 0
  %78 = load ptr, ptr %77
  %79 = load i32, ptr %k.3.44
  %80 = getelementptr ptr, ptr %78, i32 %79
  %81 = load ptr, ptr %80
  %82 = getelementptr %class.Edge, ptr %81, i32 0
  %83 = getelementptr i32, ptr %82, i32 2
  %84 = load i32, ptr %83
  store i32 %84, ptr %w.5.46
  %85 = load ptr, ptr %d.1.38
  %86 = load i32, ptr %u.3.43
  %87 = getelementptr i32, ptr %85, i32 %86
  %88 = load i32, ptr %87
  %89 = load i32, ptr %w.5.46
  %90 = add i32 %88, %89
  store i32 %90, ptr %alt.5.47
  %91 = load i32, ptr %alt.5.47
  %92 = load ptr, ptr %d.1.38
  %93 = load i32, ptr %v.5.45
  %94 = getelementptr i32, ptr %92, i32 %93
  %95 = load i32, ptr %94
  %96 = icmp sge i32 %91, %95
  br i1 %96, label %if.then6.0.8, label %if.else6.0.8

if.then6.0.8:
  br label %for.inc4.3.8

if.else6.0.8:
  br label %if.end6.0.8

if.end6.0.8:
  %97 = load i32, ptr %alt.5.47
  %98 = load ptr, ptr %d.1.38
  %99 = load i32, ptr %v.5.45
  %100 = getelementptr i32, ptr %98, i32 %99
  %101 = load i32, ptr %100
  store i32 %97, ptr %100
  %102 = call ptr @_malloc(i32 2)
  call void @Node.Node(ptr %102)
  %103 = load ptr, ptr %node.3.42
  store ptr %102, ptr %node.3.42
  %104 = load i32, ptr %v.5.45
  %105 = load ptr, ptr %node.3.42
  %106 = getelementptr %class.Node, ptr %105, i32 0
  %107 = getelementptr i32, ptr %106, i32 0
  %108 = load i32, ptr %107
  store i32 %104, ptr %107
  %109 = load ptr, ptr %d.1.38
  %110 = load i32, ptr %v.5.45
  %111 = getelementptr i32, ptr %109, i32 %110
  %112 = load i32, ptr %111
  %113 = load ptr, ptr %node.3.42
  %114 = getelementptr %class.Node, ptr %113, i32 0
  %115 = getelementptr i32, ptr %114, i32 1
  %116 = load i32, ptr %115
  store i32 %112, ptr %115
  %117 = load ptr, ptr %q.1.40
  %118 = load ptr, ptr %node.3.42
  call void @Heap_Node.push(ptr %117, ptr %118)
  br label %for.inc4.3.8

for.inc4.3.8:
  %119 = load ptr, ptr @g.0.30
  %120 = getelementptr %class.EdgeList, ptr %119, i32 0
  %121 = getelementptr ptr, ptr %120, i32 1
  %122 = load ptr, ptr %121
  %123 = load i32, ptr %k.3.44
  %124 = getelementptr i32, ptr %122, i32 %123
  %125 = load i32, ptr %124
  %126 = load i32, ptr %k.3.44
  store i32 %125, ptr %k.3.44
  br label %for.cond4.3.8

for.end4.3.8:
  br label %while.cond2.1.7

while.end2.1.7:
  %127 = load ptr, ptr %d.1.38
  store ptr %127, ptr %ret.val
  br label %return


return:
  %128 = load ptr, ptr %ret.val
  ret ptr %128

}

define void @__init__() {
entry:
  store i32 10000000, ptr @INF.0.31
  br label %return


return:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  %ret.val= alloca i32
  call void @init()
  %i.1.48= alloca i32
  %j.1.49= alloca i32
  %0 = load i32, ptr %i.1.48
  store i32 0, ptr %i.1.48
  %d.3.50= alloca ptr
  br label %for.cond2.0.9

for.cond2.0.9:
  %1 = load i32, ptr %i.1.48
  %2 = load i32, ptr @n.0.28
  %3 = icmp slt i32 %1, %2
  br i1 %3, label %for.body2.0.9, label %for.end2.0.9

for.body2.0.9:
  %4 = load i32, ptr %i.1.48
  %5 = call ptr @dijkstra(i32 %4)
  store ptr %5, ptr %d.3.50
  %6 = load i32, ptr %j.1.49
  store i32 0, ptr %j.1.49
  br label %for.cond4.0.10

for.cond4.0.10:
  %7 = load i32, ptr %j.1.49
  %8 = load i32, ptr @n.0.28
  %9 = icmp slt i32 %7, %8
  br i1 %9, label %for.body4.0.10, label %for.end4.0.10

for.body4.0.10:
  %10 = load ptr, ptr %d.3.50
  %11 = load i32, ptr %j.1.49
  %12 = getelementptr i32, ptr %10, i32 %11
  %13 = load i32, ptr %12
  %14 = load i32, ptr @INF.0.31
  %15 = icmp eq i32 %13, %14
  br i1 %15, label %if.then6.0.9, label %if.else6.0.9

if.then6.0.9:
  call void @print(ptr @.str.0)
  br label %if.end6.0.9

if.else6.0.9:
  %16 = load ptr, ptr %d.3.50
  %17 = load i32, ptr %j.1.49
  %18 = getelementptr i32, ptr %16, i32 %17
  %19 = load i32, ptr %18
  %20 = call ptr @toString(i32 %19)
  call void @print(ptr %20)
  br label %if.end6.0.9

if.end6.0.9:
  call void @print(ptr @.str.1)
  br label %for.inc4.0.10

for.inc4.0.10:
  %21 = load i32, ptr %j.1.49
  %22 = add i32 %21, 1
  store i32 %22, ptr %j.1.49
  br label %for.cond4.0.10

for.end4.0.10:
  call void @println(ptr @.str.2)
  br label %for.inc2.0.9

for.inc2.0.9:
  %23 = load i32, ptr %i.1.48
  %24 = add i32 %23, 1
  store i32 %24, ptr %i.1.48
  br label %for.cond2.0.9

for.end2.0.9:
  store i32 0, ptr %ret.val
  br label %return


return:
  %25 = load i32, ptr %ret.val
  ret i32 %25

}

