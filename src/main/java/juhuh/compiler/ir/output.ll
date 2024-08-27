@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@c.0.0 = global i32 0
@x.0.0 = global i32 0
%class.string = type {  }
declare i32 @length(ptr %this)
declare ptr @substring(ptr %this, i32 %.int0, i32 %.int1)
declare i32 @parseInt(ptr %this)
declare i32 @ord(ptr %this, i32 %.int0)
declare i32 @_arr_size(ptr %array)
declare ptr @_malloc(i32 %size)
declare ptr @_arr_init(i32 %size)
declare ptr @_add(ptr %str1, ptr %str2)
declare i32 @_strcmp_(ptr %str1, ptr %str2)
declare ptr @toString(i32 %i)
declare ptr @print(ptr %str)
declare ptr @println(ptr %str)
declare ptr @printInt(i32 %i)
declare ptr @printlnInt(i32 %i)
declare ptr @getString()
declare i32 @getInt()
define i32 @lol(i32 %x) {
entry:
  %ret.val= alloca i32
  %x.1.0= alloca i32
  store i32 %x, ptr %x.1.0
  %0 = load i32, ptr %x.1.0
  %1 = add i32 %0, 1
  store i32 %1, ptr %ret.val
  br label %return


return:
  %2 = load i32, ptr %ret.val
  ret i32 %2

}

define void @__init__() {
entry:
  store i32 123, ptr @c.0.0
  %0 = load i32, ptr @c.0.0
  %1 = call i32 @lol(i32 %0)
  store i32 %1, ptr @x.0.0
  br label %return

entry:
  store i32 123, ptr @c.0.0
  %0 = load i32, ptr @c.0.0
  %1 = call i32 @lol(i32 %0)
  store i32 %1, ptr @x.0.0
  br label %return


return:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  %ret.val= alloca i32
  %0 = load i32, ptr @x.0.0
  store i32 %0, ptr %ret.val
  br label %return


return:
  %1 = load i32, ptr %ret.val
  ret i32 %1

}

