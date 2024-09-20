@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
%class.string = type {  }
%class.A = type { i32 }
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
define void @A.A(ptr %this) {
entry:
  br label %return1


return1:
  ret void

}

define i32 @A.f(ptr %this) {
entry:
  %_0 = getelementptr %class.A, ptr %this, i32 0
  %_1 = getelementptr i32, ptr %_0, i32 0
  %_2 = load i32, ptr %_1
  br label %return2


return2:
  ret i32 %_2

}

define ptr @A.copy(ptr %this) {
entry:
  br label %return3


return3:
  ret ptr %this

}

define void @__init__() {
entry:
  br label %return0


return0:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  %_0 = call ptr @_malloc(i32 1)
  call void @A.A(ptr %_0)
  %_1 = call i32 @A.f(ptr %_0)
  %_2 = icmp slt i32 %_1, 0
  br i1 %_2, label %cond.true0, label %cond.false0

cond.true0:
  br label %cond.end0

cond.false0:
  %_4 = call ptr @A.copy(ptr %_0)
  br label %cond.end0

cond.end0:
  %_5 = select i1 %_2, ptr null, ptr %_4
  %_6 = call i32 @A.f(ptr %_5)
  %_7 = icmp sgt i32 %_6, 0
  br i1 %_7, label %cond.true1, label %cond.false1

cond.true1:
  %_9 = call ptr @A.copy(ptr %_5)
  br label %cond.end1

cond.false1:
  br label %cond.end1

cond.end1:
  %_10 = select i1 %_7, ptr %_9, ptr null
  %_11 = call ptr @_arr_init(i32 10)
  %_12 = getelementptr i32, ptr %_11, i32 9
  %_13 = load i32, ptr %_12
  %_14 = getelementptr i32, ptr %_11, i32 1
  %_15 = load i32, ptr %_14
  %_16 = icmp sgt i32 %_13, %_15
  br i1 %_16, label %cond.true2, label %cond.false2

cond.true2:
  br label %cond.end2

cond.false2:
  br label %cond.end2

cond.end2:
  %_18 = select i1 %_16, ptr %_17, ptr null
  %_20 = call ptr @A.copy(ptr %_10)
  %_21 = call ptr @A.copy(ptr %_20)
  %_22 = call ptr @A.copy(ptr %_21)
  %_23 = call ptr @A.copy(ptr %_22)
  %_24 = call i32 @A.f(ptr %_23)
  %_25 = getelementptr i32, ptr %_18, i32 0
  %_26 = load i32, ptr %_25
  %_27 = add i32 %_24, %_26
  br label %return4


return4:
  ret i32 %_27

}

