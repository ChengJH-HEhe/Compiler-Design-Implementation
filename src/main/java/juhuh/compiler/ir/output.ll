@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@hashsize.0.0 = global i32 0
@table.0.0 = global ptr null
@.str.0 = private unnamed_addr constant [2 x i8] c" \00"
%class.string = type {  }
%class.node = type { i32,i32,ptr }
declare i32 @length(ptr %this)
declare ptr @substring(ptr %this, i32 %.int0, i32 %.int1)
declare i32 @parseInt(ptr %this)
declare i32 @ord(ptr %this, i32 %.int0)
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
define void @node(ptr %this) {
entry:
  %this.addr= alloca ptr
  store ptr %this, ptr %this.addr
  %this.copy = load ptr, ptr %this.addr
  br label %return


return:
  ret void

}

define i32 @getHash(i32 %n) {
entry:
  %ret.val= alloca i32
  %n.1.1= alloca i32
  store i32 %n, ptr %n.1.1
  %0 = load i32, ptr %n.1.1
  %1 = mul i32 %0, 237
  %2 = load i32, ptr @hashsize.0.0
  %3 = srem i32 %1, %2
  store i32 %3, ptr %ret.val
  br label %return


return:
  %4 = load i32, ptr %ret.val
  ret i32 %4

}

define void @put(i32 %key, i32 %data) {
entry:
  %key.1.2= alloca i32
  store i32 %key, ptr %key.1.2
  %data.1.2= alloca i32
  store i32 %data, ptr %data.1.2
  %p.1.2= alloca i32
  %ptr.1.2= alloca ptr
  store ptr null, ptr %ptr.1.2
  %0 = load i32, ptr %key.1.2
  %1 = call i32 @getHash(i32 %0)
  %2 = load i32, ptr %p.1.2
  store i32 %1, ptr %p.1.2
  %3 = load ptr, ptr @table.0.0
  %4 = load i32, ptr %p.1.2
  %5 = getelementptr ptr, ptr %3, i32 %4
  %6 = load ptr, ptr %5
  %7 = icmp eq ptr %6, null
  br i1 %7, label %if.then2.0.0, label %if.else2.0.0

if.then2.0.0:
  %8 = call ptr @_malloc(i32 3)
  call void @node(ptr %8)
  %9 = load ptr, ptr @table.0.0
  %10 = load i32, ptr %p.1.2
  %11 = getelementptr ptr, ptr %9, i32 %10
  %12 = load ptr, ptr %11
  store ptr %8, ptr %11
  %13 = load i32, ptr %key.1.2
  %14 = load ptr, ptr @table.0.0
  %15 = load i32, ptr %p.1.2
  %16 = getelementptr ptr, ptr %14, i32 %15
  %17 = load ptr, ptr %16
  %18 = getelementptr %class.node, ptr %17, i32 0
  %19 = getelementptr i32, ptr %18, i32 0
  %20 = load i32, ptr %19
  store i32 %13, ptr %19
  %21 = load i32, ptr %data.1.2
  %22 = load ptr, ptr @table.0.0
  %23 = load i32, ptr %p.1.2
  %24 = getelementptr ptr, ptr %22, i32 %23
  %25 = load ptr, ptr %24
  %26 = getelementptr %class.node, ptr %25, i32 0
  %27 = getelementptr i32, ptr %26, i32 1
  %28 = load i32, ptr %27
  store i32 %21, ptr %27
  %29 = load ptr, ptr @table.0.0
  %30 = load i32, ptr %p.1.2
  %31 = getelementptr ptr, ptr %29, i32 %30
  %32 = load ptr, ptr %31
  %33 = getelementptr %class.node, ptr %32, i32 0
  %34 = getelementptr ptr, ptr %33, i32 2
  %35 = load ptr, ptr %34
  store ptr null, ptr %34
  br label %return

if.else2.0.0:
  br label %if.end2.0.0

if.end2.0.0:
  %36 = load ptr, ptr @table.0.0
  %37 = load i32, ptr %p.1.2
  %38 = getelementptr ptr, ptr %36, i32 %37
  %39 = load ptr, ptr %38
  %40 = load ptr, ptr %ptr.1.2
  store ptr %39, ptr %ptr.1.2
  br label %while.cond2.3.0

while.cond2.3.0:
  %41 = load ptr, ptr %ptr.1.2
  %42 = getelementptr %class.node, ptr %41, i32 0
  %43 = getelementptr i32, ptr %42, i32 0
  %44 = load i32, ptr %43
  %45 = load i32, ptr %key.1.2
  %46 = icmp ne i32 %44, %45
  br i1 %46, label %while.body2.3.0, label %while.end2.3.0

while.body2.3.0:
  %47 = load ptr, ptr %ptr.1.2
  %48 = getelementptr %class.node, ptr %47, i32 0
  %49 = getelementptr ptr, ptr %48, i32 2
  %50 = load ptr, ptr %49
  %51 = icmp eq ptr %50, null
  br i1 %51, label %if.then4.0.1, label %if.else4.0.1

if.then4.0.1:
  %52 = call ptr @_malloc(i32 3)
  call void @node(ptr %52)
  %53 = load ptr, ptr %ptr.1.2
  %54 = getelementptr %class.node, ptr %53, i32 0
  %55 = getelementptr ptr, ptr %54, i32 2
  %56 = load ptr, ptr %55
  store ptr %52, ptr %55
  %57 = load i32, ptr %key.1.2
  %58 = load ptr, ptr %ptr.1.2
  %59 = getelementptr %class.node, ptr %58, i32 0
  %60 = getelementptr ptr, ptr %59, i32 2
  %61 = load ptr, ptr %60
  %62 = getelementptr %class.node, ptr %61, i32 0
  %63 = getelementptr i32, ptr %62, i32 0
  %64 = load i32, ptr %63
  store i32 %57, ptr %63
  %65 = load ptr, ptr %ptr.1.2
  %66 = getelementptr %class.node, ptr %65, i32 0
  %67 = getelementptr ptr, ptr %66, i32 2
  %68 = load ptr, ptr %67
  %69 = getelementptr %class.node, ptr %68, i32 0
  %70 = getelementptr ptr, ptr %69, i32 2
  %71 = load ptr, ptr %70
  store ptr null, ptr %70
  br label %if.end4.0.1

if.else4.0.1:
  br label %if.end4.0.1

if.end4.0.1:
  %72 = load ptr, ptr %ptr.1.2
  %73 = getelementptr %class.node, ptr %72, i32 0
  %74 = getelementptr ptr, ptr %73, i32 2
  %75 = load ptr, ptr %74
  %76 = load ptr, ptr %ptr.1.2
  store ptr %75, ptr %ptr.1.2
  br label %while.cond2.3.0

while.end2.3.0:
  %77 = load i32, ptr %data.1.2
  %78 = load ptr, ptr %ptr.1.2
  %79 = getelementptr %class.node, ptr %78, i32 0
  %80 = getelementptr i32, ptr %79, i32 1
  %81 = load i32, ptr %80
  store i32 %77, ptr %80
  br label %return


return:
  ret void

}

define i32 @get(i32 %key) {
entry:
  %ret.val= alloca i32
  %key.1.3= alloca i32
  store i32 %key, ptr %key.1.3
  %ptr.1.3= alloca ptr
  store ptr null, ptr %ptr.1.3
  %0 = load ptr, ptr @table.0.0
  %1 = load i32, ptr %key.1.3
  %2 = call i32 @getHash(i32 %1)
  %3 = getelementptr ptr, ptr %0, i32 %2
  %4 = load ptr, ptr %3
  %5 = load ptr, ptr %ptr.1.3
  store ptr %4, ptr %ptr.1.3
  br label %while.cond2.0.1

while.cond2.0.1:
  %6 = load ptr, ptr %ptr.1.3
  %7 = getelementptr %class.node, ptr %6, i32 0
  %8 = getelementptr i32, ptr %7, i32 0
  %9 = load i32, ptr %8
  %10 = load i32, ptr %key.1.3
  %11 = icmp ne i32 %9, %10
  br i1 %11, label %while.body2.0.1, label %while.end2.0.1

while.body2.0.1:
  %12 = load ptr, ptr %ptr.1.3
  %13 = getelementptr %class.node, ptr %12, i32 0
  %14 = getelementptr ptr, ptr %13, i32 2
  %15 = load ptr, ptr %14
  %16 = load ptr, ptr %ptr.1.3
  store ptr %15, ptr %ptr.1.3
  br label %while.cond2.0.1

while.end2.0.1:
  %17 = load ptr, ptr %ptr.1.3
  %18 = getelementptr %class.node, ptr %17, i32 0
  %19 = getelementptr i32, ptr %18, i32 1
  %20 = load i32, ptr %19
  store i32 %20, ptr %ret.val
  br label %return


return:
  %21 = load i32, ptr %ret.val
  ret i32 %21

}

define void @__init__() {
entry:
  store i32 100, ptr @hashsize.0.0
  br label %return


return:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  %ret.val= alloca i32
  %i.1.4= alloca i32
  %0 = call ptr @_arr_init(i32 100)
  %1 = load ptr, ptr @table.0.0
  store ptr %0, ptr @table.0.0
  %2 = load i32, ptr %i.1.4
  store i32 0, ptr %i.1.4
  br label %for.cond2.0.2

for.cond2.0.2:
  %3 = load i32, ptr %i.1.4
  %4 = load i32, ptr @hashsize.0.0
  %5 = icmp slt i32 %3, %4
  br i1 %5, label %for.body2.0.2, label %for.end2.0.2

for.body2.0.2:
  %6 = load ptr, ptr @table.0.0
  %7 = load i32, ptr %i.1.4
  %8 = getelementptr ptr, ptr %6, i32 %7
  %9 = load ptr, ptr %8
  store ptr null, ptr %8
  br label %for.inc2.0.2

for.inc2.0.2:
  %10 = load i32, ptr %i.1.4
  %11 = add i32 %10, 0
  %12 = add i32 %10, 1
  store i32 %12, ptr %i.1.4
  br label %for.cond2.0.2

for.end2.0.2:
  %13 = load i32, ptr %i.1.4
  store i32 0, ptr %i.1.4
  br label %for.cond2.1.3

for.cond2.1.3:
  %14 = load i32, ptr %i.1.4
  %15 = icmp slt i32 %14, 1000
  br i1 %15, label %for.body2.1.3, label %for.end2.1.3

for.body2.1.3:
  %16 = load i32, ptr %i.1.4
  %17 = load i32, ptr %i.1.4
  call void @put(i32 %16, i32 %17)
  br label %for.inc2.1.3

for.inc2.1.3:
  %18 = load i32, ptr %i.1.4
  %19 = add i32 %18, 0
  %20 = add i32 %18, 1
  store i32 %20, ptr %i.1.4
  br label %for.cond2.1.3

for.end2.1.3:
  %21 = load i32, ptr %i.1.4
  store i32 0, ptr %i.1.4
  br label %for.cond2.2.4

for.cond2.2.4:
  %22 = load i32, ptr %i.1.4
  %23 = icmp slt i32 %22, 1000
  br i1 %23, label %for.body2.2.4, label %for.end2.2.4

for.body2.2.4:
  %24 = load i32, ptr %i.1.4
  %25 = call ptr @toString(i32 %24)
  %26 = call ptr @_add(ptr %25, ptr @.str.0)
  %27 = load i32, ptr %i.1.4
  %28 = call i32 @get(i32 %27)
  %29 = call ptr @toString(i32 %28)
  %30 = call ptr @_add(ptr %26, ptr %29)
  call void @println(ptr %30)
  br label %for.inc2.2.4

for.inc2.2.4:
  %31 = load i32, ptr %i.1.4
  %32 = add i32 %31, 0
  %33 = add i32 %31, 1
  store i32 %33, ptr %i.1.4
  br label %for.cond2.2.4

for.end2.2.4:
  store i32 0, ptr %ret.val
  br label %return


return:
  %34 = load i32, ptr %ret.val
  ret i32 %34

}

