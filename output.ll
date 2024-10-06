@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
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
  br label %return0

_phi.return0:
  ret void


return0:
  br label %_phi.return0

}

define i32 @main() {
entry:
  call void @__init__()
  %_0 = call ptr @_arr_init(i32 5)
  br label %for.cond.0

for.cond.0:
  %.0.for.cond.0 = phi i32 [0, %entry], [ %_5 , %for.inc.0]
  %_2 = icmp slt i32 %.0.for.cond.0, 5
  br label %_phi.for.cond.0

for.body.0:
  %_3 = call ptr @_arr_init(i32 5)
  %_4 = getelementptr ptr, ptr %_0, i32 %.0.for.cond.0
  store ptr %_3, ptr %_4
  br label %for.inc.0

for.inc.0:
  %_5 = add i32 %.0.for.cond.0, 1
  %.0.for.cond.0 = add null 0, %_5
  br label %for.cond.0

for.end.0:
  %_6 = getelementptr ptr, ptr %_0, i32 1
  %_7 = load ptr, ptr %_6
  %_8 = getelementptr i32, ptr %_7, i32 1
  %_9 = load i32, ptr %_8
  store i32 1, ptr %_8
  %_10 = getelementptr ptr, ptr %_0, i32 1
  %_11 = load ptr, ptr %_10
  %_12 = getelementptr i32, ptr %_11, i32 2
  %_13 = load i32, ptr %_12
  store i32 1, ptr %_12
  br label %return1

_phi.for.cond.0:
  br i1 %_2, label %for.body.0, label %for.end.0

_phi.return1:
  ret i32 0


return1:
  br label %_phi.return1

}

