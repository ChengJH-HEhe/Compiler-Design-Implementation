@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@f.0.0 = global ptr null
@.str.0 = private unnamed_addr constant [1 x i8] c"\00"
@.str.1 = private unnamed_addr constant [1 x i8] c"\00"
@.str.4 = private unnamed_addr constant [2 x i8] c"\0A\00"
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
  %_0 = call ptr @_arr_init(i32 2801)
  store ptr %_0, ptr @f.0.0
  br label %return0

phi.return0:
  ret void


return0:
  br label %phi.return0

}

define i32 @main() {
entry:
  call void @__init__()
  br label %for.cond2.0.0

for.cond2.0.0:
  %b.1.2.for.cond2.0.0 = phi i32 [ 0 , %entry], [ %_8 , %for.inc2.0.0]
  %_2 = sub i32 %b.1.2.for.cond2.0.0, 2800
  %_3 = icmp ne i32 %_2, 0
  br label %phi.for.cond2.0.0

for.body2.0.0:
  %_5 = sdiv i32 10000, 5
  %_6 = load ptr, ptr @f.0.0
  %_8 = add i32 %b.1.2.for.cond2.0.0, 1
  %_9 = getelementptr i32, ptr %_6, i32 %b.1.2.for.cond2.0.0
  %_10 = load i32, ptr %_9
  store i32 %_5, ptr %_9
  br label %for.inc2.0.0

for.inc2.0.0:
  br label %for.cond2.0.0

for.end2.0.0:
  br label %for.cond2.1.1

for.cond2.1.1:
  %d.1.4.for.cond2.1.1 = phi i32 [ 0 , %for.end2.0.0], [ %d.1.4.for.end4.3.2 , %for.inc2.1.1]
  %e.1.5.for.cond2.1.1 = phi i32 [ 0 , %for.end2.0.0], [ %_45 , %for.inc2.1.1]
  %g.1.6.for.cond2.1.1 = phi i32 [ 0 , %for.end2.0.0], [ %g.1.6.for.end4.3.2 , %for.inc2.1.1]
  %b.1.2.for.cond2.1.1 = phi i32 [ %b.1.2.for.cond2.0.0 , %for.end2.0.0], [ %b.1.2.for.end4.3.2 , %for.inc2.1.1]
  %c.1.3.for.cond2.1.1 = phi i32 [ 2800 , %for.end2.0.0], [ %_36 , %for.inc2.1.1]
  br label %phi.for.cond2.1.1

for.body2.1.1:
  %_12 = mul i32 %c.1.3.for.cond2.1.1, 2
  %_13 = icmp eq i32 %_12, 0
  br label %phi.for.body2.1.1

if.then4.0.0:
  br label %for.end2.1.1

if.else4.0.0:
  br label %if.end4.0.0

if.end4.0.0:
  br label %for.cond4.3.2

for.cond4.3.2:
  %d.1.4.for.cond4.3.2 = phi i32 [ %_34 , %for.inc4.3.2], [ 0 , %if.end4.0.0]
  %b.1.2.for.cond4.3.2 = phi i32 [ %_30 , %for.inc4.3.2], [ %c.1.3.for.cond2.1.1 , %if.end4.0.0]
  %g.1.6.for.cond4.3.2 = phi i32 [ %_28 , %for.inc4.3.2], [ %_12 , %if.end4.0.0]
  br label %phi.for.cond4.3.2

for.body4.3.2:
  %_16 = load ptr, ptr @f.0.0
  %_18 = getelementptr i32, ptr %_16, i32 %b.1.2.for.cond4.3.2
  %_19 = load i32, ptr %_18
  %_21 = mul i32 %_19, 10000
  %_22 = add i32 %d.1.4.for.cond4.3.2, %_21
  %_24 = sub i32 %g.1.6.for.cond4.3.2, 1
  %_25 = srem i32 %_22, %_24
  %_26 = getelementptr i32, ptr %_16, i32 %b.1.2.for.cond4.3.2
  %_27 = load i32, ptr %_26
  store i32 %_25, ptr %_26
  %_28 = sub i32 %_24, 1
  %_29 = sdiv i32 %_22, %_24
  %_30 = sub i32 %b.1.2.for.cond4.3.2, 1
  %_31 = icmp eq i32 %_30, 0
  br label %phi.for.body4.3.2

if.then6.0.1:
  br label %for.end4.3.2

if.else6.0.1:
  br label %if.end6.0.1

if.end6.0.1:
  br label %for.inc4.3.2

for.inc4.3.2:
  %_34 = mul i32 %_29, %_30
  br label %for.cond4.3.2

for.end4.3.2:
  %d.1.4.for.end4.3.2 = phi i32 [ %d.1.4.for.cond4.3.2 , %for.cond4.3.2], [ %_29 , %if.then6.0.1]
  %b.1.2.for.end4.3.2 = phi i32 [ %b.1.2.for.cond4.3.2 , %for.cond4.3.2], [ %_30 , %if.then6.0.1]
  %g.1.6.for.end4.3.2 = phi i32 [ %g.1.6.for.cond4.3.2 , %for.cond4.3.2], [ %_28 , %if.then6.0.1]
  %_36 = sub i32 %c.1.3.for.cond2.1.1, 14
  %_40 = sdiv i32 %d.1.4.for.end4.3.2, 10000
  %_41 = add i32 %e.1.5.for.cond2.1.1, %_40
  %_42 = call ptr @toString(i32 %_41)
  %.str.2 = call ptr @_add(ptr @.str.0, ptr %_42)
  %.str.3 = call ptr @_add(ptr %.str.2, ptr @.str.1)
  call void @print(ptr %.str.3)
  br label %for.inc2.1.1

for.inc2.1.1:
  %_45 = srem i32 %d.1.4.for.end4.3.2, 10000
  br label %for.cond2.1.1

for.end2.1.1:
  %d.1.4.for.end2.1.1 = phi i32 [ %d.1.4.for.cond2.1.1 , %for.cond2.1.1], [ 0 , %if.then4.0.0]
  %g.1.6.for.end2.1.1 = phi i32 [ %g.1.6.for.cond2.1.1 , %for.cond2.1.1], [ %_12 , %if.then4.0.0]
  call void @print(ptr @.str.4)
  br label %return1

phi.for.cond2.0.0:
  br i1 %_3, label %for.body2.0.0, label %for.end2.0.0

phi.for.cond2.1.1:
  br i1 true, label %for.body2.1.1, label %for.end2.1.1

phi.for.body2.1.1:
  br i1 %_13, label %if.then4.0.0, label %if.else4.0.0

phi.for.cond4.3.2:
  br i1 true, label %for.body4.3.2, label %for.end4.3.2

phi.for.body4.3.2:
  br i1 %_31, label %if.then6.0.1, label %if.else6.0.1

phi.return1:
  ret i32 0


return1:
  br label %phi.return1

}

