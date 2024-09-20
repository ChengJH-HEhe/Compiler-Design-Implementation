; ModuleID = './output.ll'
source_filename = "./output.ll"

@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@n.0.0 = global i32 0
@a.0.1 = global ptr null
@i.0.6 = global i32 0

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

define i32 @jud(i32 %x) {
entry:
  br label %for.cond2.0.0

for.cond2.0.0:                                    ; preds = %for.inc2.0.0, %entry
  %flag.3.4.for.cond2.0.0 = phi i1 [ false, %entry ], [ %flag.3.4.for.cond4.0.1, %for.inc2.0.0 ]
  %j.4.5.for.cond2.0.0 = phi i32 [ 0, %entry ], [ %j.4.5.for.cond4.0.1, %for.inc2.0.0 ]
  %i.2.3.for.cond2.0.0 = phi i32 [ 0, %entry ], [ %_28, %for.inc2.0.0 ]
  %_1 = load i32, ptr @n.0.0, align 4
  %_3 = sdiv i32 %_1, %x
  %_4 = icmp slt i32 %i.2.3.for.cond2.0.0, %_3
  br i1 %_4, label %for.body2.0.0, label %for.end2.0.0

for.body2.0.0:                                    ; preds = %for.cond2.0.0
  br label %for.cond4.0.1

for.cond4.0.1:                                    ; preds = %for.inc4.0.1, %for.body2.0.0
  %flag.3.4.for.cond4.0.1 = phi i1 [ false, %for.body2.0.0 ], [ %flag.3.4.if.end6.0.0, %for.inc4.0.1 ]
  %j.4.5.for.cond4.0.1 = phi i32 [ 0, %for.body2.0.0 ], [ %_24, %for.inc4.0.1 ]
  %_7 = sub i32 %x, 1
  %_8 = icmp slt i32 %j.4.5.for.cond4.0.1, %_7
  br i1 %_8, label %for.body4.0.1, label %for.end4.0.1

for.body4.0.1:                                    ; preds = %for.cond4.0.1
  %_9 = load ptr, ptr @a.0.1, align 8
  %_12 = mul i32 %i.2.3.for.cond2.0.0, %x
  %_14 = add i32 %_12, %j.4.5.for.cond4.0.1
  %_15 = getelementptr i32, ptr %_9, i32 %_14
  %_16 = load i32, ptr %_15, align 4
  %_17 = mul i32 %i.2.3.for.cond2.0.0, %x
  %_18 = add i32 %_17, %j.4.5.for.cond4.0.1
  %_19 = add i32 %_18, 1
  %_20 = getelementptr i32, ptr %_9, i32 %_19
  %_21 = load i32, ptr %_20, align 4
  %_22 = icmp sgt i32 %_16, %_21
  br i1 %_22, label %if.then6.0.0, label %if.else6.0.0

if.then6.0.0:                                     ; preds = %for.body4.0.1
  br label %if.end6.0.0

if.else6.0.0:                                     ; preds = %for.body4.0.1
  br label %if.end6.0.0

if.end6.0.0:                                      ; preds = %if.else6.0.0, %if.then6.0.0
  %flag.3.4.if.end6.0.0 = phi i1 [ %flag.3.4.for.cond4.0.1, %if.else6.0.0 ], [ true, %if.then6.0.0 ]
  br label %for.inc4.0.1

for.inc4.0.1:                                     ; preds = %if.end6.0.0
  %_24 = add i32 %j.4.5.for.cond4.0.1, 1
  br label %for.cond4.0.1

for.end4.0.1:                                     ; preds = %for.cond4.0.1
  %_26 = xor i1 true, %flag.3.4.for.cond4.0.1
  br i1 %_26, label %if.then4.1.1, label %if.else4.1.1

if.then4.1.1:                                     ; preds = %for.end4.0.1
  br label %return1

if.else4.1.1:                                     ; preds = %for.end4.0.1
  br label %if.end4.1.1

if.end4.1.1:                                      ; preds = %if.else4.1.1
  br label %for.inc2.0.0

for.inc2.0.0:                                     ; preds = %if.end4.1.1
  %_28 = add i32 %i.2.3.for.cond2.0.0, 1
  br label %for.cond2.0.0

for.end2.0.0:                                     ; preds = %for.cond2.0.0
  br label %return1

return1:                                          ; preds = %for.end2.0.0, %if.then4.1.1
  %flag.3.4.return1 = phi i1 [ %flag.3.4.for.cond2.0.0, %for.end2.0.0 ], [ %flag.3.4.for.cond4.0.1, %if.then4.1.1 ]
  %ret.val.return1 = phi i32 [ 0, %for.end2.0.0 ], [ 1, %if.then4.1.1 ]
  %j.4.5.return1 = phi i32 [ %j.4.5.for.cond2.0.0, %for.end2.0.0 ], [ %j.4.5.for.cond4.0.1, %if.then4.1.1 ]
  ret i32 %ret.val.return1
}

define void @__init__() {
entry:
  %_0 = call ptr @_arr_init(i32 20)
  store ptr %_0, ptr @a.0.1, align 8
  br label %return0

return0:                                          ; preds = %entry
  ret void
}

define i32 @main() {
entry:
  call void @__init__()
  %_0 = call i32 @getInt()
  store i32 %_0, ptr @n.0.0, align 4
  store i32 0, ptr @i.0.6, align 4
  br label %for.cond2.0.2

for.cond2.0.2:                                    ; preds = %for.inc2.0.2, %entry
  %_1 = load i32, ptr @i.0.6, align 4
  %_2 = load i32, ptr @n.0.0, align 4
  %_3 = icmp slt i32 %_1, %_2
  br i1 %_3, label %for.body2.0.2, label %for.end2.0.2

for.body2.0.2:                                    ; preds = %for.cond2.0.2
  %_4 = call i32 @getInt()
  %_5 = load ptr, ptr @a.0.1, align 8
  %_6 = load i32, ptr @i.0.6, align 4
  %_7 = getelementptr i32, ptr %_5, i32 %_6
  %_8 = load i32, ptr %_7, align 4
  store i32 %_4, ptr %_7, align 4
  br label %for.inc2.0.2

for.inc2.0.2:                                     ; preds = %for.body2.0.2
  %_9 = load i32, ptr @i.0.6, align 4
  %_10 = add i32 %_9, 1
  store i32 %_10, ptr @i.0.6, align 4
  br label %for.cond2.0.2

for.end2.0.2:                                     ; preds = %for.cond2.0.2
  %_11 = load i32, ptr @n.0.0, align 4
  store i32 %_11, ptr @i.0.6, align 4
  br label %for.cond2.1.3

for.cond2.1.3:                                    ; preds = %for.inc2.1.3, %for.end2.0.2
  %_12 = load i32, ptr @i.0.6, align 4
  %_13 = icmp sgt i32 %_12, 0
  br i1 %_13, label %for.body2.1.3, label %for.end2.1.3

for.body2.1.3:                                    ; preds = %for.cond2.1.3
  %_14 = load i32, ptr @i.0.6, align 4
  %_15 = call i32 @jud(i32 %_14)
  %_16 = icmp sgt i32 %_15, 0
  br i1 %_16, label %if.then4.0.2, label %if.else4.0.2

if.then4.0.2:                                     ; preds = %for.body2.1.3
  %_17 = load i32, ptr @i.0.6, align 4
  %_18 = call ptr @toString(i32 %_17)
  call void @print(ptr %_18)
  br label %return2

if.else4.0.2:                                     ; preds = %for.body2.1.3
  br label %if.end4.0.2

if.end4.0.2:                                      ; preds = %if.else4.0.2
  br label %for.inc2.1.3

for.inc2.1.3:                                     ; preds = %if.end4.0.2
  %_19 = load i32, ptr @i.0.6, align 4
  %_20 = sdiv i32 %_19, 2
  store i32 %_20, ptr @i.0.6, align 4
  br label %for.cond2.1.3

for.end2.1.3:                                     ; preds = %for.cond2.1.3
  br label %return2

return2:                                          ; preds = %for.end2.1.3, %if.then4.0.2
  %ret.val.return2 = phi i32 [ 0, %for.end2.1.3 ], [ 0, %if.then4.0.2 ]
  ret i32 %ret.val.return2
}
