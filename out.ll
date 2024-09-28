; ModuleID = './output.ll'
source_filename = "./output.ll"

@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"

declare i32 @string.length(ptr)

declare ptr @string.substring(ptr, i32, i32)

declare i32 @string.parseInt(ptr)

declare i32 @string.ord(ptr, i32)

declare i32 @_arr_size(ptr)

declare ptr @_malloc(i32)

declare ptr @_arr_init(i32)

declare ptr @_add(ptr, ptr)

declare i32 @_strcmp(ptr, ptr)

declare ptr @toString(i32)

declare void @print(ptr)

declare void @println(ptr)

declare void @printInt(i32)

declare void @printlnInt(i32)

declare ptr @getString()

declare i32 @getInt()

define void @__init__() {
entry:
  br label %return0

return0:                                          ; preds = %entry
  ret void
}

define i32 @main() {
entry:
  call void @__init__()
  br label %for.cond2.0.0

for.cond2.0.0:                                    ; preds = %for.inc2.0.0, %entry
  %i.2.0.for.cond2.0.0 = phi i32 [ 1, %entry ], [ %_1, %for.inc2.0.0 ]
  %flag.3.1.for.cond2.0.0 = phi i1 [ false, %entry ], [ false, %for.inc2.0.0 ]
  br i1 true, label %for.body2.0.0, label %for.end2.0.0

for.body2.0.0:                                    ; preds = %for.cond2.0.0
  br label %for.inc2.0.0

for.inc2.0.0:                                     ; preds = %for.body2.0.0
  %_1 = add i32 %i.2.0.for.cond2.0.0, 1
  br label %for.cond2.0.0

for.end2.0.0:                                     ; preds = %for.cond2.0.0
  br label %return1

return1:                                          ; preds = %for.end2.0.0
  ret i32 0
}
