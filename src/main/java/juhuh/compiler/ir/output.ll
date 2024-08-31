@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@n.0.0 = global i32 0
@p.0.1 = global i32 0
@k.0.2 = global i32 0
@i.0.3 = global i32 0
@.str.0 = private unnamed_addr constant [4 x i8] c"<< \00"
@.str.1 = private unnamed_addr constant [2 x i8] c"(\00"
@.str.2 = private unnamed_addr constant [3 x i8] c") \00"
@.str.3 = private unnamed_addr constant [2 x i8] c" \00"
@.str.4 = private unnamed_addr constant [4 x i8] c">> \00"
%class.string = type {  }
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
  %0 = call i32 @getInt()
  %1 = load i32, ptr @n.0.0
  store i32 %0, ptr @n.0.0
  %2 = call i32 @getInt()
  %3 = load i32, ptr @p.0.1
  store i32 %2, ptr @p.0.1
  %4 = call i32 @getInt()
  %5 = load i32, ptr @k.0.2
  store i32 %4, ptr @k.0.2
  %6 = load i32, ptr @p.0.1
  %7 = load i32, ptr @k.0.2
  %8 = sub i32 %6, %7
  %9 = icmp sgt i32 %8, 1
  br i1 %9, label %if.then2.0.0, label %if.else2.0.0

if.then2.0.0:
  call void @print(ptr @.str.0)
  br label %if.end2.0.0

if.else2.0.0:
  br label %if.end2.0.0

if.end2.0.0:
  %10 = load i32, ptr @p.0.1
  %11 = load i32, ptr @k.0.2
  %12 = sub i32 %10, %11
  %13 = load i32, ptr @i.0.3
  store i32 %12, ptr @i.0.3
  br label %for.cond2.3.0

for.cond2.3.0:
  %14 = load i32, ptr @i.0.3
  %15 = load i32, ptr @p.0.1
  %16 = load i32, ptr @k.0.2
  %17 = add i32 %15, %16
  %18 = icmp sle i32 %14, %17
  br i1 %18, label %for.body2.3.0, label %for.end2.3.0

for.body2.3.0:
  br label %log.lhs1

log.lhs1:
  %19 = load i32, ptr @i.0.3
  %20 = icmp sle i32 1, %19
  br i1 %20, label %log.rhs1, label %log.end1

log.rhs1:
  %21 = load i32, ptr @i.0.3
  %22 = load i32, ptr @n.0.0
  %23 = icmp sle i32 %21, %22
  br label %log.end1

log.end1:
  %24 = select i1 %20, i1 %23, i1 %20
  br i1 %24, label %if.then4.0.2, label %if.else4.0.2

if.then4.0.2:
  %25 = load i32, ptr @i.0.3
  %26 = load i32, ptr @p.0.1
  %27 = icmp eq i32 %25, %26
  br i1 %27, label %if.then6.0.3, label %if.else6.0.3

if.then6.0.3:
  call void @print(ptr @.str.1)
  %28 = load i32, ptr @i.0.3
  %29 = call ptr @toString(i32 %28)
  call void @print(ptr %29)
  call void @print(ptr @.str.2)
  br label %if.end6.0.3

if.else6.0.3:
  %30 = load i32, ptr @i.0.3
  call void @printInt(i32 %30)
  call void @print(ptr @.str.3)
  br label %if.end6.0.3

if.end6.0.3:
  br label %if.end4.0.2

if.else4.0.2:
  br label %if.end4.0.2

if.end4.0.2:
  br label %for.inc2.3.0

for.inc2.3.0:
  %31 = load i32, ptr @i.0.3
  %32 = add i32 %31, 0
  %33 = add i32 %31, 1
  store i32 %33, ptr @i.0.3
  br label %for.cond2.3.0

for.end2.3.0:
  %34 = load i32, ptr @p.0.1
  %35 = load i32, ptr @k.0.2
  %36 = add i32 %34, %35
  %37 = load i32, ptr @n.0.0
  %38 = icmp slt i32 %36, %37
  br i1 %38, label %if.then2.4.4, label %if.else2.4.4

if.then2.4.4:
  call void @print(ptr @.str.4)
  br label %if.end2.4.4

if.else2.4.4:
  br label %if.end2.4.4

if.end2.4.4:
  store i32 0, ptr %ret.val
  br label %return


return:
  %39 = load i32, ptr %ret.val
  ret i32 %39

}

