@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@a.0.0 = global ptr null
@m.0.0 = global i32 0
@k.0.0 = global i32 0
@i.0.0 = global i32 0
%class.string = type {  }
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
declare ptr @print(ptr %str)
declare ptr @println(ptr %str)
declare ptr @printInt(i32 %i)
declare ptr @printlnInt(i32 %i)
declare ptr @getString()
declare i32 @getInt()
define void @__init__() {
entry:
  %0 = call ptr @_malloc(i32 50)
  %.0= alloca i32
  store i32 0, ptr %.0
  br label %for.cond.0

for.cond.0:
  %1 = load i32, ptr %.0
  %2 = icmp slt i32 %1, 50
  br i1 %2, label %for.body.0, label %for.end.0

for.body.0:
  %3 = getelementptr ptr, ptr %0, i32 %1
  store ptr UNUSED, ptr %3
  br label %for.inc.0

for.inc.0:
  %4 = add i32 %1, 1
  store i32 %4, ptr %.0
  br label %for.cond.0

for.end.0:
  store ptr %0, ptr @a.0.0
  br label %return


return:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  br label %for.cond2.0.1

for.cond2.0.1:
  %5 = load i32, ptr @i.0.0
  %6 = load i32, ptr @m.0.0
  %7 = icmp slt i32 %5, %6
  br i1 %7, label %for.body2.0.1, label %for.end2.0.1

for.inc2.0.1:
  %8 = load i32, ptr @i.0.0
  %9 = add i32 %8, 0
  %10 = add i32 %8, 1
  store i32 %10, ptr @i.0.0
  br label %for.cond2.0.1

for.body2.0.1:
  %11 = call i32 @getInt()
  %12 = load ptr, ptr @a.0.0
  %13 = load i32, ptr @i.0.0
  %14 = getelementptr i32, ptr %12, i32 %13
  %15 = load i32, ptr %14
  store i32 %11, ptr %14
  br label %for.inc2.0.1

for.end2.0.1:
  %16 = load i32, ptr @i.0.0
  store i32 0, ptr @i.0.0
  br label %for.cond2.1.2

for.cond2.1.2:
  br i1 %36, label %for.body2.1.2, label %for.end2.1.2

log.lhs0:
  br label %log.lhs1

log.lhs1:
  %17 = load ptr, ptr @a.0.0
  %18 = load i32, ptr @i.0.0
  %19 = getelementptr i32, ptr %17, i32 %18
  %20 = load i32, ptr %19
  %21 = load ptr, ptr @a.0.0
  %22 = load i32, ptr @k.0.0
  %23 = sub i32 %22, 1
  %24 = getelementptr i32, ptr %21, i32 %23
  %25 = load i32, ptr %24
  %26 = icmp sge i32 %20, %25
  br i1 %26, label %log.rhs1, label %log.end1

log.rhs1:
  %27 = load ptr, ptr @a.0.0
  %28 = load i32, ptr @i.0.0
  %29 = getelementptr i32, ptr %27, i32 %28
  %30 = load i32, ptr %29
  %31 = icmp sgt i32 %30, 0
  br label %log.end1

log.end1:
  %32 = select i1 %26, i1 %31, i1 %26
  br i1 %32, label %log.rhs0, label %log.end0

log.rhs0:
  %33 = load i32, ptr @i.0.0
  %34 = load i32, ptr @m.0.0
  %35 = icmp slt i32 %33, %34
  br label %log.end0

log.end0:
  %36 = select i1 %32, i1 %35, i1 %32
  br label %for.inc2.1.2

for.inc2.1.2:
  %37 = load i32, ptr @i.0.0
  %38 = add i32 %37, 0
  %39 = add i32 %37, 1
  store i32 %39, ptr @i.0.0
  br label %for.cond2.1.2

for.body2.1.2:
  br label %for.inc2.1.2

for.end2.1.2:
  %40 = load i32, ptr @i.0.0
  call void @printInt(i32 %40)
  store i32 0, ptr %ret.val
  br label %return


return:
  %41 = load i32, ptr %ret.val
  ret i32 %41

}

