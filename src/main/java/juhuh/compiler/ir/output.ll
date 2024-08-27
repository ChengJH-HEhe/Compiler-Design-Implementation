@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@n.0.0 = global i32 0
@a.0.0 = global ptr null
@i.0.0 = global i32 0
%class.string = type {  }
declare i32 @string.length(ptr %this)
declare ptr @string.substring(ptr %this, i32 %.int0, i32 %.int1)
declare i32 @string.parseInt(ptr %this)
declare i32 @string.ord(ptr %this, i32 %.int0)
declare i32 @_arr_size(ptr %array)
declare ptr @_arr_malloc(i32 %size)
declare ptr @_add(ptr %str1, ptr %str2)
declare i32 @_strcmp_(ptr %str1, ptr %str2)
declare ptr @toString(i32 %i)
declare ptr @print(ptr %str)
declare ptr @println(ptr %str)
declare ptr @printInt(i32 %i)
declare ptr @printlnInt(i32 %i)
declare ptr @getString()
declare i32 @getInt()
define i32 @jud(i32 %x) {
entry:
  %ret.val= alloca i32
  %x.1.0= alloca i32
  store i32 %x, ptr %x.1.0
  %i.1.0= alloca i32
  %j.1.0= alloca i32
  %0 = load i32, ptr %i.1.0
  store i32 0, ptr %i.1.0
  br label %for.cond2.0.0

for.cond2.0.0:
  %1 = load i32, ptr %i.1.0
  %2 = load i32, ptr @n.0.0
  %3 = load i32, ptr %x.1.0
  %4 = sdiv i32 %2, %3
  %5 = icmp slt i32 %1, %4
  br i1 %5, label %for.body2.0.0, label %for.end2.0.0

for.body2.0.0:
  %flag.2.0= alloca i1
  store i1 false, ptr %flag.2.0
  %6 = load i32, ptr %j.1.0
  store i32 0, ptr %j.1.0
  br label %for.cond3.0.1

for.cond3.0.1:
  %7 = load i32, ptr %j.1.0
  %8 = load i32, ptr %x.1.0
  %9 = sub i32 %8, 1
  %10 = icmp slt i32 %7, %9
  br i1 %10, label %for.body3.0.1, label %for.end3.0.1

for.body3.0.1:
  %11 = load ptr, ptr @a.0.0
  %12 = load i32, ptr %i.1.0
  %13 = load i32, ptr %x.1.0
  %14 = mul i32 %12, %13
  %15 = load i32, ptr %j.1.0
  %16 = add i32 %14, %15
  %17 = getelementptr i32, ptr %11, i32 %16
  %18 = load i32, ptr %17
  %19 = load ptr, ptr @a.0.0
  %20 = load i32, ptr %i.1.0
  %21 = load i32, ptr %x.1.0
  %22 = mul i32 %20, %21
  %23 = load i32, ptr %j.1.0
  %24 = add i32 %22, %23
  %25 = add i32 %24, 1
  %26 = getelementptr i32, ptr %19, i32 %25
  %27 = load i32, ptr %26
  %28 = icmp sgt i32 %18, %27
  br i1 %28, label %if.then4.0.0, label %if.else4.0.0

if.then4.0.0:
  %29 = load i1, ptr %flag.2.0
  store i1 true, ptr %flag.2.0
  br label %if.end4.0.0

if.else4.0.0:
  br label %if.end4.0.0

if.end4.0.0:
  br label %for.inc3.0.1

for.inc3.0.1:
  %30 = load i32, ptr %j.1.0
  %31 = add i32 %30, 1
  store i32 %31, ptr %j.1.0
  br label %for.cond3.0.1

for.end3.0.1:
  %32 = load i1, ptr %flag.2.0
  %33 = xor i1 1, %32
  br i1 %33, label %if.then3.1.1, label %if.else3.1.1

if.then3.1.1:
  store i32 1, ptr %ret.val
  br label %return

if.else3.1.1:
  br label %if.end3.1.1

if.end3.1.1:
  br label %for.inc2.0.0

for.inc2.0.0:
  %34 = load i32, ptr %i.1.0
  %35 = add i32 %34, 1
  store i32 %35, ptr %i.1.0
  br label %for.cond2.0.0

for.end2.0.0:
  store i32 0, ptr %ret.val
  br label %return


return:
  %36 = load i32, ptr %ret.val
  ret i32 %36

}

define void @__init__() {
entry:
  %0 = call ptr @_arr_malloc(i32 20)
  %1 = and i32 1, 1
  br label %for.cond.%1

for.cond.%1:
  %cmp.%1 = slt i32 %1, 20
  br i1 %cmp.%1, label %for.body.%1, label %for.end.%1

for.body.%1:
  %2= alloca ptr
  %3 = call ptr @_malloc(i32 1)
  %4 = getelementptr ptr, ptr %0, i32 %1
  store ptr %2, ptr %4
  br label %for.inc.%1

for.inc.%1:
  %1 = add i32 %1, 1
  br label %for.end.%1


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
  %2 = load i32, ptr @i.0.0
  store i32 0, ptr @i.0.0
  br label %for.cond2.0.2

for.cond2.0.2:
  %3 = load i32, ptr @i.0.0
  %4 = load i32, ptr @n.0.0
  %5 = icmp slt i32 %3, %4
  br i1 %5, label %for.body2.0.2, label %for.end2.0.2

for.body2.0.2:
  %6 = call i32 @getInt()
  %7 = load ptr, ptr @a.0.0
  %8 = load i32, ptr @i.0.0
  %9 = getelementptr i32, ptr %7, i32 %8
  %10 = load i32, ptr %9
  store i32 %6, ptr %9
  br label %for.inc2.0.2

for.inc2.0.2:
  %11 = load i32, ptr @i.0.0
  %12 = add i32 %11, 1
  store i32 %12, ptr @i.0.0
  br label %for.cond2.0.2

for.end2.0.2:
  %13 = load i32, ptr @n.0.0
  %14 = load i32, ptr @i.0.0
  store i32 %13, ptr @i.0.0
  br label %for.cond2.1.3

for.cond2.1.3:
  %15 = load i32, ptr @i.0.0
  %16 = icmp sgt i32 %15, 0
  br i1 %16, label %for.body2.1.3, label %for.end2.1.3

for.body2.1.3:
  %17 = load i32, ptr @i.0.0
  %18 = call i32 @jud(i32 %17)
  %19 = icmp sgt i32 %18, 0
  br i1 %19, label %if.then3.0.2, label %if.else3.0.2

if.then3.0.2:
  %20 = load i32, ptr @i.0.0
  %21 = call ptr @toString(i32 %20)
  call void @print(ptr %21)
  store i32 0, ptr %ret.val
  br label %return

if.else3.0.2:
  br label %if.end3.0.2

if.end3.0.2:
  br label %for.inc2.1.3

for.inc2.1.3:
  %22 = load i32, ptr @i.0.0
  %23 = sdiv i32 %22, 2
  %24 = load i32, ptr @i.0.0
  store i32 %23, ptr @i.0.0
  br label %for.cond2.1.3

for.end2.1.3:
  store i32 0, ptr %ret.val
  br label %return


return:
  %25 = load i32, ptr %ret.val
  ret i32 %25

}

