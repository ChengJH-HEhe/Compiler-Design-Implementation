@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@MAXN.0.0 = global i32 0
@MAXM.0.1 = global i32 0
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
  store i32 10005, ptr @MAXN.0.0
  store i32 10005, ptr @MAXM.0.1
  br label %return0


return0:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  %_0 = call i32 @getInt()
  %_1 = call i32 @getInt()
  %_2 = load i32, ptr @MAXN.0.0
  %_3 = call ptr @_arr_init(i32 %_2)
  %_4 = call ptr @_arr_init(i32 %_2)
  %_5 = call ptr @_arr_init(i32 %_2)
  br label %for.cond2.0.0

for.cond2.0.0:
  %i.1.7.for.cond2.0.0 = phi i32 [ 0 , %entry], [ %_19 , %for.inc2.0.0]
  %_8 = icmp slt i32 %i.1.7.for.cond2.0.0, %_1
  br i1 %_8, label %for.body2.0.0, label %for.end2.0.0

for.body2.0.0:
  %_9 = call i32 @getInt()
  %_12 = getelementptr i32, ptr %_3, i32 %i.1.7.for.cond2.0.0
  %_13 = load i32, ptr %_12
  store i32 %_9, ptr %_12
  %_14 = call i32 @getInt()
  %_16 = getelementptr i32, ptr %_4, i32 %i.1.7.for.cond2.0.0
  %_17 = load i32, ptr %_16
  store i32 %_14, ptr %_16
  br label %for.inc2.0.0

for.inc2.0.0:
  %_19 = add i32 %i.1.7.for.cond2.0.0, 1
  br label %for.cond2.0.0

for.end2.0.0:
  br label %for.cond2.1.1

for.cond2.1.1:
  %i.1.7.for.cond2.1.1 = phi i32 [ 0 , %for.end2.0.0], [ %_64 , %for.inc2.1.1]
  %j.1.8.for.cond2.1.1 = phi i32 [0, %for.end2.0.0], [ %j.1.8.for.cond4.0.2 , %for.inc2.1.1]
  %_22 = icmp slt i32 %i.1.7.for.cond2.1.1, %_1
  br i1 %_22, label %for.body2.1.1, label %for.end2.1.1

for.body2.1.1:
  %_25 = getelementptr i32, ptr %_4, i32 %i.1.7.for.cond2.1.1
  %_26 = load i32, ptr %_25
  br label %for.cond4.0.2

for.cond4.0.2:
  %j.1.8.for.cond4.0.2 = phi i32 [ %_26 , %for.body2.1.1], [ %_62 , %for.inc4.0.2]
  %_29 = icmp sle i32 %j.1.8.for.cond4.0.2, %_0
  br i1 %_29, label %for.body4.0.2, label %for.end4.0.2

for.body4.0.2:
  %_32 = getelementptr i32, ptr %_5, i32 %j.1.8.for.cond4.0.2
  %_33 = load i32, ptr %_32
  %_36 = getelementptr i32, ptr %_4, i32 %i.1.7.for.cond2.1.1
  %_37 = load i32, ptr %_36
  %_38 = sub i32 %j.1.8.for.cond4.0.2, %_37
  %_39 = getelementptr i32, ptr %_5, i32 %_38
  %_40 = load i32, ptr %_39
  %_42 = getelementptr i32, ptr %_3, i32 %i.1.7.for.cond2.1.1
  %_43 = load i32, ptr %_42
  %_44 = add i32 %_40, %_43
  %_45 = icmp sle i32 %_33, %_44
  br i1 %_45, label %if.then6.0.0, label %if.else6.0.0

if.then6.0.0:
  %_50 = getelementptr i32, ptr %_4, i32 %i.1.7.for.cond2.1.1
  %_51 = load i32, ptr %_50
  %_52 = sub i32 %j.1.8.for.cond4.0.2, %_51
  %_53 = getelementptr i32, ptr %_5, i32 %_52
  %_54 = load i32, ptr %_53
  %_56 = getelementptr i32, ptr %_3, i32 %i.1.7.for.cond2.1.1
  %_57 = load i32, ptr %_56
  %_58 = add i32 %_54, %_57
  %_59 = getelementptr i32, ptr %_5, i32 %j.1.8.for.cond4.0.2
  %_60 = load i32, ptr %_59
  store i32 %_58, ptr %_59
  br label %if.end6.0.0

if.else6.0.0:
  br label %if.end6.0.0

if.end6.0.0:
  br label %for.inc4.0.2

for.inc4.0.2:
  %_62 = add i32 %j.1.8.for.cond4.0.2, 1
  br label %for.cond4.0.2

for.end4.0.2:
  br label %for.inc2.1.1

for.inc2.1.1:
  %_64 = add i32 %i.1.7.for.cond2.1.1, 1
  br label %for.cond2.1.1

for.end2.1.1:
  %_67 = getelementptr i32, ptr %_5, i32 %_0
  %_68 = load i32, ptr %_67
  %_69 = call ptr @toString(i32 %_68)
  call void @print(ptr %_69)
  br label %return1


return1:
  ret i32 0

}

