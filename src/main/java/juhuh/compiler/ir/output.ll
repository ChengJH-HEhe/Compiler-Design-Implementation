@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@.str.0 = private unnamed_addr constant [4 x i8] c"233\00"
@.str.1 = private unnamed_addr constant [3 x i8] c"( \00"
@.str.2 = private unnamed_addr constant [3 x i8] c", \00"
@.str.3 = private unnamed_addr constant [3 x i8] c" )\00"
@.str.4 = private unnamed_addr constant [11 x i8] c"vector x: \00"
@.str.5 = private unnamed_addr constant [9 x i8] c"excited!\00"
@.str.6 = private unnamed_addr constant [11 x i8] c"vector y: \00"
@.str.7 = private unnamed_addr constant [8 x i8] c"x + y: \00"
@.str.8 = private unnamed_addr constant [8 x i8] c"x * y: \00"
@.str.9 = private unnamed_addr constant [15 x i8] c"(1 << 3) * y: \00"
%class.string = type {  }
%class.vector = type { ptr }
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
define void @vector(ptr %this) {
entry:
  %this.addr= alloca ptr
  store ptr %this, ptr %this.addr
  %this.copy = load ptr, ptr %this.addr
  br label %return


return:
  ret void

}

define void @vector.init(ptr %this, ptr %vec) {
entry:
  %this.addr.1= alloca ptr
  store ptr %this, ptr %this.addr.1
  %this.copy = load ptr, ptr %this.addr.1
  %vec.1.0= alloca ptr
  store ptr %vec, ptr %vec.1.0
  call void @print(ptr @.str.0)
  %0 = load ptr, ptr %vec.1.0
  %1 = icmp eq ptr %0, null
  %i.1.1= alloca i32
  br i1 %1, label %if.then2.0.0, label %if.else2.0.0

if.then2.0.0:
  br label %return

if.else2.0.0:
  br label %if.end2.0.0

if.end2.0.0:
  %2 = load ptr, ptr %vec.1.0
  %3 = call i32 @_arr_size(ptr %2)
  %4 = call ptr @_arr_init(i32 %3)
  %5 = getelementptr %class.vector, ptr %this.copy, i32 0
  %6 = getelementptr ptr, ptr %5, i32 0
  %7 = load ptr, ptr %6
  store ptr %4, ptr %6
  %8 = load i32, ptr %i.1.1
  store i32 0, ptr %i.1.1
  br label %for.cond2.3.0

for.cond2.3.0:
  %9 = load i32, ptr %i.1.1
  %10 = load ptr, ptr %vec.1.0
  %11 = call i32 @_arr_size(ptr %10)
  %12 = icmp slt i32 %9, %11
  br i1 %12, label %for.body2.3.0, label %for.end2.3.0

for.body2.3.0:
  %13 = load ptr, ptr %vec.1.0
  %14 = load i32, ptr %i.1.1
  %15 = getelementptr i32, ptr %13, i32 %14
  %16 = load i32, ptr %15
  %17 = getelementptr %class.vector, ptr %this.copy, i32 0
  %18 = getelementptr ptr, ptr %17, i32 0
  %19 = load ptr, ptr %18
  %20 = load i32, ptr %i.1.1
  %21 = getelementptr i32, ptr %19, i32 %20
  %22 = load i32, ptr %21
  store i32 %16, ptr %21
  br label %for.inc2.3.0

for.inc2.3.0:
  %23 = load i32, ptr %i.1.1
  %24 = add i32 %23, 1
  store i32 %24, ptr %i.1.1
  br label %for.cond2.3.0

for.end2.3.0:
  br label %return


return:
  ret void

}

define i32 @vector.getDim(ptr %this) {
entry:
  %ret.val= alloca i32
  %this.addr.2= alloca ptr
  store ptr %this, ptr %this.addr.2
  %this.copy = load ptr, ptr %this.addr.2
  %0 = getelementptr %class.vector, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = icmp eq ptr %2, null
  br i1 %3, label %if.then2.0.1, label %if.else2.0.1

if.then2.0.1:
  store i32 0, ptr %ret.val
  br label %return

if.else2.0.1:
  br label %if.end2.0.1

if.end2.0.1:
  %4 = getelementptr %class.vector, ptr %this.copy, i32 0
  %5 = getelementptr ptr, ptr %4, i32 0
  %6 = load ptr, ptr %5
  %7 = call i32 @_arr_size(ptr %6)
  store i32 %7, ptr %ret.val
  br label %return


return:
  %8 = load i32, ptr %ret.val
  ret i32 %8

}

define i32 @vector.dot(ptr %this, ptr %rhs) {
entry:
  %ret.val= alloca i32
  %this.addr.3= alloca ptr
  store ptr %this, ptr %this.addr.3
  %this.copy = load ptr, ptr %this.addr.3
  %rhs.1.2= alloca ptr
  store ptr %rhs, ptr %rhs.1.2
  %i.1.3= alloca i32
  store i32 0, ptr %i.1.3
  %result.1.4= alloca i32
  store i32 0, ptr %result.1.4
  br label %while.cond2.0.1

while.cond2.0.1:
  %0 = load i32, ptr %i.1.3
  %1 = call i32 @vector.getDim(ptr %this.copy)
  %2 = icmp slt i32 %0, %1
  br i1 %2, label %while.body2.0.1, label %while.end2.0.1

while.body2.0.1:
  %3 = getelementptr %class.vector, ptr %this.copy, i32 0
  %4 = getelementptr ptr, ptr %3, i32 0
  %5 = load ptr, ptr %4
  %6 = load i32, ptr %i.1.3
  %7 = getelementptr i32, ptr %5, i32 %6
  %8 = load i32, ptr %7
  %9 = load ptr, ptr %rhs.1.2
  %10 = getelementptr %class.vector, ptr %9, i32 0
  %11 = getelementptr ptr, ptr %10, i32 0
  %12 = load ptr, ptr %11
  %13 = load i32, ptr %i.1.3
  %14 = getelementptr i32, ptr %12, i32 %13
  %15 = load i32, ptr %14
  %16 = mul i32 %8, %15
  %17 = load i32, ptr %result.1.4
  store i32 %16, ptr %result.1.4
  %18 = load i32, ptr %i.1.3
  %19 = add i32 %18, 1
  store i32 %19, ptr %i.1.3
  br label %while.cond2.0.1

while.end2.0.1:
  %20 = load i32, ptr %result.1.4
  store i32 %20, ptr %ret.val
  br label %return


return:
  %21 = load i32, ptr %ret.val
  ret i32 %21

}

define ptr @vector.scalarInPlaceMultiply(ptr %this, i32 %c) {
entry:
  %ret.val= alloca ptr
  %this.addr.4= alloca ptr
  store ptr %this, ptr %this.addr.4
  %this.copy = load ptr, ptr %this.addr.4
  %c.1.5= alloca i32
  store i32 %c, ptr %c.1.5
  %0 = getelementptr %class.vector, ptr %this.copy, i32 0
  %1 = getelementptr ptr, ptr %0, i32 0
  %2 = load ptr, ptr %1
  %3 = icmp eq ptr %2, null
  %i.1.6= alloca i32
  br i1 %3, label %if.then2.0.2, label %if.else2.0.2

if.then2.0.2:
  store ptr null, ptr %ret.val
  br label %return

if.else2.0.2:
  br label %if.end2.0.2

if.end2.0.2:
  %4 = load i32, ptr %i.1.6
  store i32 0, ptr %i.1.6
  br label %for.cond2.3.2

for.cond2.3.2:
  %5 = load i32, ptr %i.1.6
  %6 = call i32 @vector.getDim(ptr %this.copy)
  %7 = icmp slt i32 %5, %6
  br i1 %7, label %for.body2.3.2, label %for.end2.3.2

for.body2.3.2:
  %8 = load i32, ptr %c.1.5
  %9 = getelementptr %class.vector, ptr %this.copy, i32 0
  %10 = getelementptr ptr, ptr %9, i32 0
  %11 = load ptr, ptr %10
  %12 = load i32, ptr %i.1.6
  %13 = getelementptr i32, ptr %11, i32 %12
  %14 = load i32, ptr %13
  %15 = mul i32 %8, %14
  %16 = getelementptr %class.vector, ptr %this.copy, i32 0
  %17 = getelementptr ptr, ptr %16, i32 0
  %18 = load ptr, ptr %17
  %19 = load i32, ptr %i.1.6
  %20 = getelementptr i32, ptr %18, i32 %19
  %21 = load i32, ptr %20
  store i32 %15, ptr %20
  br label %for.inc2.3.2

for.inc2.3.2:
  %22 = load i32, ptr %i.1.6
  %23 = add i32 %22, 1
  store i32 %23, ptr %i.1.6
  br label %for.cond2.3.2

for.end2.3.2:
  store ptr %this.copy, ptr %ret.val
  br label %return


return:
  %24 = load ptr, ptr %ret.val
  ret ptr %24

}

define ptr @vector.add(ptr %this, ptr %rhs) {
entry:
  %ret.val= alloca ptr
  %this.addr.5= alloca ptr
  store ptr %this, ptr %this.addr.5
  %this.copy = load ptr, ptr %this.addr.5
  %rhs.1.7= alloca ptr
  store ptr %rhs, ptr %rhs.1.7
  %temp.1.8= alloca ptr
  %i.1.9= alloca i32
  br label %log.lhs3

log.lhs3:
  %0 = call i32 @vector.getDim(ptr %this.copy)
  %1 = load ptr, ptr %rhs.1.7
  %2 = call i32 @vector.getDim(ptr %1)
  %3 = icmp ne i32 %0, %2
  br i1 %3, label %log.end3, label %log.rhs3

log.rhs3:
  %4 = call i32 @vector.getDim(ptr %this.copy)
  %5 = icmp eq i32 %4, 0
  br label %log.end3

log.end3:
  %6 = select i1 %3, i1 %3, i1 %5
  br i1 %6, label %if.then2.0.4, label %if.else2.0.4

if.then2.0.4:
  store ptr null, ptr %ret.val
  br label %return

if.else2.0.4:
  br label %if.end2.0.4

if.end2.0.4:
  store ptr null, ptr %temp.1.8
  %7 = call i32 @vector.getDim(ptr %this.copy)
  %8 = call ptr @_arr_init(i32 %7)
  %9 = load ptr, ptr %temp.1.8
  %10 = getelementptr %class.vector, ptr %9, i32 0
  %11 = getelementptr ptr, ptr %10, i32 0
  %12 = load ptr, ptr %11
  store ptr %8, ptr %11
  %13 = load i32, ptr %i.1.9
  store i32 0, ptr %i.1.9
  br label %for.cond2.3.3

for.cond2.3.3:
  %14 = load i32, ptr %i.1.9
  %15 = call i32 @vector.getDim(ptr %this.copy)
  %16 = icmp slt i32 %14, %15
  br i1 %16, label %for.body2.3.3, label %for.end2.3.3

for.body2.3.3:
  %17 = getelementptr %class.vector, ptr %this.copy, i32 0
  %18 = getelementptr ptr, ptr %17, i32 0
  %19 = load ptr, ptr %18
  %20 = load i32, ptr %i.1.9
  %21 = getelementptr i32, ptr %19, i32 %20
  %22 = load i32, ptr %21
  %23 = load ptr, ptr %rhs.1.7
  %24 = getelementptr %class.vector, ptr %23, i32 0
  %25 = getelementptr ptr, ptr %24, i32 0
  %26 = load ptr, ptr %25
  %27 = load i32, ptr %i.1.9
  %28 = getelementptr i32, ptr %26, i32 %27
  %29 = load i32, ptr %28
  %30 = add i32 %22, %29
  %31 = load ptr, ptr %temp.1.8
  %32 = getelementptr %class.vector, ptr %31, i32 0
  %33 = getelementptr ptr, ptr %32, i32 0
  %34 = load ptr, ptr %33
  %35 = load i32, ptr %i.1.9
  %36 = getelementptr i32, ptr %34, i32 %35
  %37 = load i32, ptr %36
  store i32 %30, ptr %36
  br label %for.inc2.3.3

for.inc2.3.3:
  %38 = load i32, ptr %i.1.9
  %39 = add i32 %38, 1
  store i32 %39, ptr %i.1.9
  br label %for.cond2.3.3

for.end2.3.3:
  %40 = load ptr, ptr %temp.1.8
  store ptr %40, ptr %ret.val
  br label %return


return:
  %41 = load ptr, ptr %ret.val
  ret ptr %41

}

define i1 @vector.set(ptr %this, i32 %idx, i32 %value) {
entry:
  %ret.val= alloca i1
  %this.addr.6= alloca ptr
  store ptr %this, ptr %this.addr.6
  %this.copy = load ptr, ptr %this.addr.6
  %idx.1.10= alloca i32
  store i32 %idx, ptr %idx.1.10
  %value.1.11= alloca i32
  store i32 %value, ptr %value.1.11
  %0 = call i32 @vector.getDim(ptr %this.copy)
  %1 = load i32, ptr %idx.1.10
  %2 = icmp slt i32 %0, %1
  br i1 %2, label %if.then2.0.5, label %if.else2.0.5

if.then2.0.5:
  store i1 false, ptr %ret.val
  br label %return

if.else2.0.5:
  br label %if.end2.0.5

if.end2.0.5:
  %3 = load i32, ptr %value.1.11
  %4 = getelementptr %class.vector, ptr %this.copy, i32 0
  %5 = getelementptr ptr, ptr %4, i32 0
  %6 = load ptr, ptr %5
  %7 = load i32, ptr %idx.1.10
  %8 = getelementptr i32, ptr %6, i32 %7
  %9 = load i32, ptr %8
  store i32 %3, ptr %8
  store i1 true, ptr %ret.val
  br label %return


return:
  %10 = load i1, ptr %ret.val
  ret i1 %10

}

define ptr @vector.tostring(ptr %this) {
entry:
  %ret.val= alloca ptr
  %this.addr.7= alloca ptr
  store ptr %this, ptr %this.addr.7
  %this.copy = load ptr, ptr %this.addr.7
  %temp.1.12= alloca ptr
  store ptr @.str.1, ptr %temp.1.12
  %0 = call i32 @vector.getDim(ptr %this.copy)
  %1 = icmp sgt i32 %0, 0
  %i.1.13= alloca i32
  br i1 %1, label %if.then2.0.6, label %if.else2.0.6

if.then2.0.6:
  %2 = load ptr, ptr %temp.1.12
  %3 = getelementptr %class.vector, ptr %this.copy, i32 0
  %4 = getelementptr ptr, ptr %3, i32 0
  %5 = load ptr, ptr %4
  %6 = getelementptr i32, ptr %5, i32 0
  %7 = load i32, ptr %6
  %8 = call ptr @toString(i32 %7)
  %9 = call ptr @_add(ptr %2, ptr %8)
  %10 = load ptr, ptr %temp.1.12
  store ptr %9, ptr %temp.1.12
  br label %if.end2.0.6

if.else2.0.6:
  br label %if.end2.0.6

if.end2.0.6:
  %11 = load i32, ptr %i.1.13
  store i32 1, ptr %i.1.13
  br label %for.cond2.3.4

for.cond2.3.4:
  %12 = load i32, ptr %i.1.13
  %13 = call i32 @vector.getDim(ptr %this.copy)
  %14 = icmp slt i32 %12, %13
  br i1 %14, label %for.body2.3.4, label %for.end2.3.4

for.body2.3.4:
  %15 = load ptr, ptr %temp.1.12
  %16 = call ptr @_add(ptr %15, ptr @.str.2)
  %17 = getelementptr %class.vector, ptr %this.copy, i32 0
  %18 = getelementptr ptr, ptr %17, i32 0
  %19 = load ptr, ptr %18
  %20 = load i32, ptr %i.1.13
  %21 = getelementptr i32, ptr %19, i32 %20
  %22 = load i32, ptr %21
  %23 = call ptr @toString(i32 %22)
  %24 = call ptr @_add(ptr %16, ptr %23)
  %25 = load ptr, ptr %temp.1.12
  store ptr %24, ptr %temp.1.12
  br label %for.inc2.3.4

for.inc2.3.4:
  %26 = load i32, ptr %i.1.13
  %27 = add i32 %26, 1
  store i32 %27, ptr %i.1.13
  br label %for.cond2.3.4

for.end2.3.4:
  %28 = load ptr, ptr %temp.1.12
  %29 = call ptr @_add(ptr %28, ptr @.str.3)
  %30 = load ptr, ptr %temp.1.12
  store ptr %29, ptr %temp.1.12
  %31 = load ptr, ptr %temp.1.12
  store ptr %31, ptr %ret.val
  br label %return


return:
  %32 = load ptr, ptr %ret.val
  ret ptr %32

}

define i1 @vector.copy(ptr %this, ptr %rhs) {
entry:
  %ret.val= alloca i1
  %this.addr.8= alloca ptr
  store ptr %this, ptr %this.addr.8
  %this.copy = load ptr, ptr %this.addr.8
  %rhs.1.14= alloca ptr
  store ptr %rhs, ptr %rhs.1.14
  %0 = load ptr, ptr %rhs.1.14
  %1 = icmp eq ptr %0, null
  %i.3.15= alloca i32
  br i1 %1, label %if.then2.0.7, label %if.else2.0.7

if.then2.0.7:
  store i1 false, ptr %ret.val
  br label %return

if.else2.0.7:
  br label %if.end2.0.7

if.end2.0.7:
  %2 = load ptr, ptr %rhs.1.14
  %3 = call i32 @vector.getDim(ptr %2)
  %4 = icmp eq i32 %3, 0
  br i1 %4, label %if.then2.3.8, label %if.else2.3.8

if.then2.3.8:
  %5 = getelementptr %class.vector, ptr %this.copy, i32 0
  %6 = getelementptr ptr, ptr %5, i32 0
  %7 = load ptr, ptr %6
  store ptr null, ptr %6
  br label %if.end2.3.8

if.else2.3.8:
  %8 = load ptr, ptr %rhs.1.14
  %9 = call i32 @vector.getDim(ptr %8)
  %10 = call ptr @_arr_init(i32 %9)
  %11 = getelementptr %class.vector, ptr %this.copy, i32 0
  %12 = getelementptr ptr, ptr %11, i32 0
  %13 = load ptr, ptr %12
  store ptr %10, ptr %12
  %14 = load i32, ptr %i.3.15
  store i32 0, ptr %i.3.15
  br label %for.cond4.0.5

for.cond4.0.5:
  %15 = load i32, ptr %i.3.15
  %16 = call i32 @vector.getDim(ptr %this.copy)
  %17 = icmp slt i32 %15, %16
  br i1 %17, label %for.body4.0.5, label %for.end4.0.5

for.body4.0.5:
  %18 = load ptr, ptr %rhs.1.14
  %19 = getelementptr %class.vector, ptr %18, i32 0
  %20 = getelementptr ptr, ptr %19, i32 0
  %21 = load ptr, ptr %20
  %22 = load i32, ptr %i.3.15
  %23 = getelementptr i32, ptr %21, i32 %22
  %24 = load i32, ptr %23
  %25 = getelementptr %class.vector, ptr %this.copy, i32 0
  %26 = getelementptr ptr, ptr %25, i32 0
  %27 = load ptr, ptr %26
  %28 = load i32, ptr %i.3.15
  %29 = getelementptr i32, ptr %27, i32 %28
  %30 = load i32, ptr %29
  store i32 %24, ptr %29
  br label %for.inc4.0.5

for.inc4.0.5:
  %31 = load i32, ptr %i.3.15
  %32 = add i32 %31, 1
  store i32 %32, ptr %i.3.15
  br label %for.cond4.0.5

for.end4.0.5:
  br label %if.end2.3.8

if.end2.3.8:
  store i1 true, ptr %ret.val
  br label %return


return:
  %33 = load i1, ptr %ret.val
  ret i1 %33

}

define void @__init__() {
entry:
  br label %return


return:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  %ret.val= alloca i32
  %x.1.16= alloca ptr
  store ptr null, ptr %x.1.16
  %a.1.17= alloca ptr
  %0 = call ptr @_arr_init(i32 10)
  store ptr %0, ptr %a.1.17
  %i.1.18= alloca i32
  %1 = load i32, ptr %i.1.18
  store i32 0, ptr %i.1.18
  %y.1.19= alloca ptr
  br label %for.cond2.0.6

for.cond2.0.6:
  %2 = load i32, ptr %i.1.18
  %3 = icmp slt i32 %2, 10
  br i1 %3, label %for.body2.0.6, label %for.end2.0.6

for.body2.0.6:
  %4 = load i32, ptr %i.1.18
  %5 = sub i32 9, %4
  %6 = load ptr, ptr %a.1.17
  %7 = load i32, ptr %i.1.18
  %8 = getelementptr i32, ptr %6, i32 %7
  %9 = load i32, ptr %8
  store i32 %5, ptr %8
  br label %for.inc2.0.6

for.inc2.0.6:
  %10 = load i32, ptr %i.1.18
  %11 = add i32 %10, 1
  store i32 %11, ptr %i.1.18
  br label %for.cond2.0.6

for.end2.0.6:
  %12 = load ptr, ptr %x.1.16
  %13 = load ptr, ptr %a.1.17
  call void @vector.init(ptr %12, ptr %13)
  call void @print(ptr @.str.4)
  %14 = load ptr, ptr %x.1.16
  %15 = call ptr @vector.tostring(ptr %14)
  call void @println(ptr %15)
  store ptr null, ptr %y.1.19
  %16 = load ptr, ptr %y.1.19
  %17 = load ptr, ptr %x.1.16
  %18 = call i1 @vector.copy(ptr %16, ptr %17)
  %19 = load ptr, ptr %y.1.19
  %20 = call i1 @vector.set(ptr %19, i32 3, i32 817)
  br i1 %20, label %if.then2.1.9, label %if.else2.1.9

if.then2.1.9:
  call void @println(ptr @.str.5)
  br label %if.end2.1.9

if.else2.1.9:
  br label %if.end2.1.9

if.end2.1.9:
  call void @print(ptr @.str.6)
  %21 = load ptr, ptr %y.1.19
  %22 = call ptr @vector.tostring(ptr %21)
  call void @println(ptr %22)
  call void @print(ptr @.str.7)
  %23 = load ptr, ptr %x.1.16
  %24 = load ptr, ptr %y.1.19
  %25 = call ptr @vector.add(ptr %23, ptr %24)
  %26 = call ptr @vector.tostring(ptr %25)
  call void @println(ptr %26)
  call void @print(ptr @.str.8)
  %27 = load ptr, ptr %x.1.16
  %28 = load ptr, ptr %y.1.19
  %29 = call i32 @vector.dot(ptr %27, ptr %28)
  %30 = call ptr @toString(i32 %29)
  call void @println(ptr %30)
  call void @print(ptr @.str.9)
  %31 = load ptr, ptr %y.1.19
  %32 = shl i32 1, 3
  %33 = call ptr @vector.scalarInPlaceMultiply(ptr %31, i32 %32)
  %34 = call ptr @vector.tostring(ptr %33)
  call void @println(ptr %34)
  store i32 0, ptr %ret.val
  br label %return


return:
  %35 = load i32, ptr %ret.val
  ret i32 %35

}

