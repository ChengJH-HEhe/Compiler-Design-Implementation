@.str.true = private unnamed_addr constant [4 x i8] c"true\00"
@.str.false = private unnamed_addr constant [5 x i8] c"false\00"
%class.string = type { [] }
declare i32 @string_length(ptr %this, )
declare ptr @string_substring(ptr %this, i32 int, i32 int, )
declare i32 @string_parseInt(ptr %this, )
declare i32 @string_ord(ptr %this, i32 int, )
declare i32 @_arr_size(ptr %array, )
declare ptr @_arr_malloc(i32 %size, )
declare ptr @_add(ptr str1, ptr str2, )
declare i32 @_strcmp(ptr str1, ptr str2, )
declare ptr @toString(i32 i, )
declare ptr @print(ptr str, )
declare ptr @println(ptr str, )
declare ptr @printInt(i32 i, )
declare ptr @printlnInt(i32 i, )
declare ptr @getString()
declare i32 @getInt()
define void @__init() {
entry:
  br label return


return:
  ret void

}

define i32 @main() {
entry:
  %ret.val= alloca i32
  %a.1.0= alloca ptr
  %0 = call ptr @_arr_malloc(i32 5)
  %1 = and i32 1 1
for.cond.%1:
  %cmp.%1 = slt i32 %1 5
  br i1%cmp.%1, label %for.body.%1, label %for.end.%1
for.body.%1:
  %2 = call ptr @_arr_malloc(i32 5)
  %3 = and i32 1 1
for.cond.%3:
  %cmp.%3 = slt i32 %3 5
  br i1%cmp.%3, label %for.body.%3, label %for.end.%3
for.body.%3:
  %4= alloca ptr
  %5 = call ptr @_malloc(i32 1)
  %6 = getelementptr ptr, ptr %2, i32 %3
  store ptr %4, ptr %6
  br label for.inc.%3
for.inc.%3:
  %3 = add i32 %3 1
  br label for.cond.%3
for.end.%3:

  %7 = getelementptr ptr, ptr %0, i32 %1
  store ptr %2, ptr %7
  br label for.inc.%1
for.inc.%1:
  %1 = add i32 %1 1
  br label for.cond.%1
for.end.%1:

  store ptr %0, ptr %a.1.0
  %8 = load ptr, ptr %a.1.0
  %10 = load ptr, ptr %9
  %9 = getelementptr ptr, ptr %8, i32 1
  %12 = load i32, ptr %11
  %11 = getelementptr i32, ptr %10, i32 1
  store i32 1, ptr %11
  %13 = add i32 1 0
  %14 = load ptr, ptr %a.1.0
  %16 = load ptr, ptr %15
  %15 = getelementptr ptr, ptr %14, i32 1
  %18 = load i32, ptr %17
  %17 = getelementptr i32, ptr %16, i32 2
  store i32 1, ptr %17
  %19 = add i32 1 0
  call void @__init()
  br label null


return:
  ret i32 %ret.val

}

