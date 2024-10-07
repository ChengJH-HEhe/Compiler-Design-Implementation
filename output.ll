@.str.true = private unnamed_addr constant [5 x i8] c"true\00"
@.str.false = private unnamed_addr constant [6 x i8] c"false\00"
@b.0.0 = global ptr null
@now.0.1 = global ptr null
@t.0.2 = global ptr null
@a.0.3 = global ptr null
@n.0.4 = global i32 0
@m.0.5 = global i32 0
@p.0.6 = global i32 0
@op.0.7 = global i32 0
@L.0.8 = global i32 0
@flag.0.9 = global ptr null
@s.0.10 = global ptr null
@sum.0.11 = global ptr null
@ans.0.12 = global i32 0
@aa.0.18 = global i32 0
@bb.0.19 = global i32 0
@MOD.0.20 = global i32 0
@no.0.21 = global i32 0
@pl.0.40 = global i32 0
@pr.0.41 = global i32 0
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
define i32 @square(i32 %x) {
entry:
  %_0 = load i32, ptr @p.0.6
  %_1 = srem i32 %x, %_0
  %_2 = srem i32 %x, %_0
  %_3 = mul i32 %_1, %_2
  br label %return1


return1:
  ret i32 %_3

}

define i32 @gcd(i32 %x, i32 %y) {
entry:
  %_0 = icmp eq i32 %y, 0
  br i1 %_0, label %if.then2.0.0, label %if.else2.0.0

if.then2.0.0:
  br label %return2

if.else2.0.0:
  br label %if.end2.0.0

if.end2.0.0:
  %_4 = icmp slt i32 %x, %y
  br i1 %_4, label %if.then2.3.1, label %if.else2.3.1

if.then2.3.1:
  %_7 = call i32 @gcd(i32 %y, i32 %x)
  br label %return2

if.else2.3.1:
  %_10 = srem i32 %x, %y
  %_11 = call i32 @gcd(i32 %y, i32 %_10)
  br label %return2


return2:
  %ret.val.return2 = phi i32 [ %x , %if.then2.0.0], [ %_7 , %if.then2.3.1], [ %_11 , %if.else2.3.1]
  ret i32 %ret.val.return2

}

define i32 @lcm(i32 %a, i32 %b) {
entry:
  %_0 = call i32 @gcd(i32 %a, i32 %b)
  %_1 = sdiv i32 %a, %_0
  %_2 = mul i32 %_1, %b
  br label %return3


return3:
  ret i32 %_2

}

define i32 @Rand() {
entry:
  br label %for.cond2.0.1

for.cond2.0.1:
  %i.1.22.for.cond2.0.1 = phi i32 [ 1 , %entry], [ %_10 , %for.inc2.0.1]
  %_1 = icmp slt i32 %i.1.22.for.cond2.0.1, 3
  br i1 %_1, label %for.body2.0.1, label %for.end2.0.1

for.body2.0.1:
  %_2 = load i32, ptr @no.0.21
  %_3 = load i32, ptr @aa.0.18
  %_4 = mul i32 %_2, %_3
  %_5 = load i32, ptr @bb.0.19
  %_6 = add i32 %_4, %_5
  %_7 = load i32, ptr @MOD.0.20
  %_8 = srem i32 %_6, %_7
  store i32 %_8, ptr @no.0.21
  br label %for.inc2.0.1

for.inc2.0.1:
  %_10 = add i32 %i.1.22.for.cond2.0.1, 1
  br label %for.cond2.0.1

for.end2.0.1:
  %_11 = load i32, ptr @no.0.21
  %_12 = icmp slt i32 %_11, 0
  br i1 %_12, label %if.then2.1.2, label %if.else2.1.2

if.then2.1.2:
  %_13 = load i32, ptr @no.0.21
  %_14 = sub i32 0, %_13
  store i32 %_14, ptr @no.0.21
  br label %if.end2.1.2

if.else2.1.2:
  br label %if.end2.1.2

if.end2.1.2:
  %_15 = load i32, ptr @no.0.21
  br label %return4


return4:
  ret i32 %_15

}

define i32 @RandRange(i32 %low, i32 %high) {
entry:
  %_0 = call i32 @Rand()
  %_1 = sub i32 %high, %low
  %_2 = add i32 %_1, 1
  %_3 = srem i32 %_0, %_2
  %_4 = add i32 %low, %_3
  %_5 = add i32 %_4, 1
  br label %return5


return5:
  ret i32 %_5

}

define void @init() {
entry:
  %_0 = call ptr @_arr_init(i32 140005)
  br label %for.cond2.0.2

for.cond2.0.2:
  %i.1.26.for.cond2.0.2 = phi i32 [ 2 , %entry], [ %_9 , %for.inc2.0.2]
  %_2 = load i32, ptr @p.0.6
  %_3 = icmp slt i32 %i.1.26.for.cond2.0.2, %_2
  br i1 %_3, label %for.body2.0.2, label %for.end2.0.2

for.body2.0.2:
  %_6 = getelementptr i32, ptr %_0, i32 %i.1.26.for.cond2.0.2
  %_7 = load i32, ptr %_6
  store i32 %i.1.26.for.cond2.0.2, ptr %_6
  br label %for.inc2.0.2

for.inc2.0.2:
  %_9 = add i32 %i.1.26.for.cond2.0.2, 1
  br label %for.cond2.0.2

for.end2.0.2:
  br label %for.cond2.1.3

for.cond2.1.3:
  %i.1.26.for.cond2.1.3 = phi i32 [ %_27 , %for.inc2.1.3], [ 2 , %for.end2.0.2]
  %j.1.27.for.cond2.1.3 = phi i32 [ %j.1.27.for.cond4.0.4 , %for.inc2.1.3], [ 0 , %for.end2.0.2]
  %_11 = load i32, ptr @p.0.6
  %_12 = icmp slt i32 %i.1.26.for.cond2.1.3, %_11
  br i1 %_12, label %for.body2.1.3, label %for.end2.1.3

for.body2.1.3:
  br label %for.cond4.0.4

for.cond4.0.4:
  %j.1.27.for.cond4.0.4 = phi i32 [ 1 , %for.body2.1.3], [ %_25 , %for.inc4.0.4]
  %_14 = icmp sle i32 %j.1.27.for.cond4.0.4, 15
  br i1 %_14, label %for.body4.0.4, label %for.end4.0.4

for.body4.0.4:
  %_17 = getelementptr i32, ptr %_0, i32 %i.1.26.for.cond2.1.3
  %_18 = load i32, ptr %_17
  %_19 = call i32 @square(i32 %_18)
  %_20 = load i32, ptr @p.0.6
  %_21 = srem i32 %_19, %_20
  %_22 = getelementptr i32, ptr %_0, i32 %i.1.26.for.cond2.1.3
  %_23 = load i32, ptr %_22
  store i32 %_21, ptr %_22
  br label %for.inc4.0.4

for.inc4.0.4:
  %_25 = add i32 %j.1.27.for.cond4.0.4, 1
  br label %for.cond4.0.4

for.end4.0.4:
  br label %for.inc2.1.3

for.inc2.1.3:
  %_27 = add i32 %i.1.26.for.cond2.1.3, 1
  br label %for.cond2.1.3

for.end2.1.3:
  br label %for.cond2.2.5

for.cond2.2.5:
  %x.3.29.for.cond2.2.5 = phi i32 [0, %for.end2.1.3], [ %x.3.29.for.end4.0.6 , %for.inc2.2.5]
  %j.3.28.for.cond2.2.5 = phi i32 [0, %for.end2.1.3], [ %j.3.28.for.cond4.0.6 , %for.inc2.2.5]
  %i.1.26.for.cond2.2.5 = phi i32 [ 2 , %for.end2.1.3], [ %_53 , %for.inc2.2.5]
  %_29 = load i32, ptr @p.0.6
  %_30 = icmp slt i32 %i.1.26.for.cond2.2.5, %_29
  br i1 %_30, label %for.body2.2.5, label %for.end2.2.5

for.body2.2.5:
  %_33 = getelementptr i32, ptr %_0, i32 %i.1.26.for.cond2.2.5
  %_34 = load i32, ptr %_33
  br label %for.cond4.0.6

for.cond4.0.6:
  %x.3.29.for.cond4.0.6 = phi i32 [ %_38 , %for.inc4.0.6], [ %_34 , %for.body2.2.5]
  %j.3.28.for.cond4.0.6 = phi i32 [ %_48 , %for.inc4.0.6], [ 1 , %for.body2.2.5]
  br i1 true, label %for.body4.0.6, label %for.end4.0.6

for.body4.0.6:
  %_36 = call i32 @square(i32 %x.3.29.for.cond4.0.6)
  %_37 = load i32, ptr @p.0.6
  %_38 = srem i32 %_36, %_37
  %_39 = load ptr, ptr @b.0.0
  %_40 = getelementptr i32, ptr %_39, i32 %_38
  %_41 = load i32, ptr %_40
  store i32 1, ptr %_40
  %_44 = getelementptr i32, ptr %_0, i32 %i.1.26.for.cond2.2.5
  %_45 = load i32, ptr %_44
  %_46 = icmp eq i32 %_38, %_45
  br i1 %_46, label %if.then6.0.3, label %if.else6.0.3

if.then6.0.3:
  br label %for.end4.0.6

if.else6.0.3:
  br label %if.end6.0.3

if.end6.0.3:
  br label %for.inc4.0.6

for.inc4.0.6:
  %_48 = add i32 %j.3.28.for.cond4.0.6, 1
  br label %for.cond4.0.6

for.end4.0.6:
  %x.3.29.for.end4.0.6 = phi i32 [ %x.3.29.for.cond4.0.6 , %for.cond4.0.6], [ %_38 , %if.then6.0.3]
  %_49 = load i32, ptr @L.0.8
  %_51 = call i32 @lcm(i32 %_49, i32 %j.3.28.for.cond4.0.6)
  store i32 %_51, ptr @L.0.8
  br label %for.inc2.2.5

for.inc2.2.5:
  %_53 = add i32 %i.1.26.for.cond2.2.5, 1
  br label %for.cond2.2.5

for.end2.2.5:
  %_54 = load ptr, ptr @b.0.0
  %_55 = getelementptr i32, ptr %_54, i32 0
  %_56 = load i32, ptr %_55
  store i32 1, ptr %_55
  %_57 = getelementptr i32, ptr %_54, i32 1
  %_58 = load i32, ptr %_57
  store i32 1, ptr %_57
  br label %return6


return6:
  ret void

}

define void @build(i32 %o, i32 %l, i32 %r) {
entry:
  %_0 = icmp eq i32 %l, %r
  br i1 %_0, label %if.then2.0.4, label %if.else2.0.4

if.then2.0.4:
  %_1 = load ptr, ptr @a.0.3
  %_3 = getelementptr i32, ptr %_1, i32 %l
  %_4 = load i32, ptr %_3
  %_5 = load ptr, ptr @sum.0.11
  %_7 = getelementptr i32, ptr %_5, i32 %o
  %_8 = load i32, ptr %_7
  store i32 %_4, ptr %_7
  br label %log.lhs5

log.lhs5:
  br label %log.lhs6

log.lhs6:
  %_9 = load ptr, ptr @a.0.3
  %_11 = getelementptr i32, ptr %_9, i32 %l
  %_12 = load i32, ptr %_11
  %_13 = load i32, ptr @p.0.6
  %_14 = icmp slt i32 %_12, %_13
  br i1 %_14, label %log.rhs6, label %log.end6

log.rhs6:
  %_15 = load ptr, ptr @a.0.3
  %_17 = getelementptr i32, ptr %_15, i32 %l
  %_18 = load i32, ptr %_17
  %_19 = icmp sge i32 %_18, 0
  br label %log.end6

log.end6:
  %_20 = select i1 %_14, i1 %_19, i1 %_14
  br i1 %_20, label %log.rhs5, label %log.end5

log.rhs5:
  %_21 = load ptr, ptr @b.0.0
  %_22 = load ptr, ptr @a.0.3
  %_24 = getelementptr i32, ptr %_22, i32 %l
  %_25 = load i32, ptr %_24
  %_26 = load i32, ptr @p.0.6
  %_27 = srem i32 %_25, %_26
  %_28 = getelementptr i32, ptr %_21, i32 %_27
  %_29 = load i32, ptr %_28
  %_30 = icmp sgt i32 %_29, 0
  br label %log.end5

log.end5:
  %_31 = select i1 %_20, i1 %_30, i1 %_20
  br i1 %_31, label %if.then4.0.7, label %if.else4.0.7

if.then4.0.7:
  %_32 = load ptr, ptr @flag.0.9
  %_34 = getelementptr i32, ptr %_32, i32 %o
  %_35 = load i32, ptr %_34
  store i32 1, ptr %_34
  %_36 = load ptr, ptr @a.0.3
  %_38 = getelementptr i32, ptr %_36, i32 %l
  %_39 = load i32, ptr %_38
  %_40 = load ptr, ptr @s.0.10
  %_41 = getelementptr ptr, ptr %_40, i32 %o
  %_42 = load ptr, ptr %_41
  %_43 = getelementptr i32, ptr %_42, i32 0
  %_44 = load i32, ptr %_43
  store i32 %_39, ptr %_43
  br label %for.cond6.0.7

for.cond6.0.7:
  %i.1.33.for.cond6.0.7 = phi i32 [ %_64 , %for.inc6.0.7], [ 1 , %if.then4.0.7]
  %_46 = load i32, ptr @L.0.8
  %_47 = icmp slt i32 %i.1.33.for.cond6.0.7, %_46
  br i1 %_47, label %for.body6.0.7, label %for.end6.0.7

for.body6.0.7:
  %_48 = load ptr, ptr @s.0.10
  %_50 = getelementptr ptr, ptr %_48, i32 %o
  %_51 = load ptr, ptr %_50
  %_53 = sub i32 %i.1.33.for.cond6.0.7, 1
  %_54 = getelementptr i32, ptr %_51, i32 %_53
  %_55 = load i32, ptr %_54
  %_56 = call i32 @square(i32 %_55)
  %_57 = load i32, ptr @p.0.6
  %_58 = srem i32 %_56, %_57
  %_59 = getelementptr ptr, ptr %_48, i32 %o
  %_60 = load ptr, ptr %_59
  %_61 = getelementptr i32, ptr %_60, i32 %i.1.33.for.cond6.0.7
  %_62 = load i32, ptr %_61
  store i32 %_58, ptr %_61
  br label %for.inc6.0.7

for.inc6.0.7:
  %_64 = add i32 %i.1.33.for.cond6.0.7, 1
  br label %for.cond6.0.7

for.end6.0.7:
  br label %if.end4.0.7

if.else4.0.7:
  br label %if.end4.0.7

if.end4.0.7:
  %i.1.33.if.end4.0.7 = phi i32 [ 0 , %if.else4.0.7], [ %i.1.33.for.cond6.0.7 , %for.end6.0.7]
  %_65 = load ptr, ptr @now.0.1
  %_67 = getelementptr i32, ptr %_65, i32 %o
  %_68 = load i32, ptr %_67
  store i32 0, ptr %_67
  br label %if.end2.0.4

if.else2.0.4:
  %_70 = mul i32 %o, 2
  %_71 = mul i32 %o, 2
  %_72 = add i32 %_71, 1
  %_75 = add i32 %l, %r
  %_76 = sdiv i32 %_75, 2
  call void @build(i32 %_70, i32 %l, i32 %_76)
  %_77 = add i32 %_76, 1
  call void @build(i32 %_72, i32 %_77, i32 %r)
  %_78 = load ptr, ptr @sum.0.11
  %_79 = getelementptr i32, ptr %_78, i32 %_70
  %_80 = load i32, ptr %_79
  %_81 = getelementptr i32, ptr %_78, i32 %_72
  %_82 = load i32, ptr %_81
  %_83 = add i32 %_80, %_82
  %_84 = getelementptr i32, ptr %_78, i32 %o
  %_85 = load i32, ptr %_84
  store i32 %_83, ptr %_84
  %_86 = load ptr, ptr @flag.0.9
  %_87 = getelementptr i32, ptr %_86, i32 %_70
  %_88 = load i32, ptr %_87
  %_89 = getelementptr i32, ptr %_86, i32 %_72
  %_90 = load i32, ptr %_89
  %_91 = and i32 %_88, %_90
  %_92 = getelementptr i32, ptr %_86, i32 %o
  %_93 = load i32, ptr %_92
  store i32 %_91, ptr %_92
  %_94 = getelementptr i32, ptr %_86, i32 %o
  %_95 = load i32, ptr %_94
  %_96 = icmp sgt i32 %_95, 0
  br i1 %_96, label %if.then4.0.8, label %if.else4.0.8

if.then4.0.8:
  br label %for.cond6.0.8

for.cond6.0.8:
  %i.1.33.for.cond6.0.8 = phi i32 [ %_119 , %for.inc6.0.8], [ 0 , %if.then4.0.8]
  %_98 = load i32, ptr @L.0.8
  %_99 = icmp slt i32 %i.1.33.for.cond6.0.8, %_98
  br i1 %_99, label %for.body6.0.8, label %for.end6.0.8

for.body6.0.8:
  %_100 = load ptr, ptr @s.0.10
  %_102 = getelementptr ptr, ptr %_100, i32 %_70
  %_103 = load ptr, ptr %_102
  %_105 = getelementptr i32, ptr %_103, i32 %i.1.33.for.cond6.0.8
  %_106 = load i32, ptr %_105
  %_108 = getelementptr ptr, ptr %_100, i32 %_72
  %_109 = load ptr, ptr %_108
  %_110 = getelementptr i32, ptr %_109, i32 %i.1.33.for.cond6.0.8
  %_111 = load i32, ptr %_110
  %_112 = add i32 %_106, %_111
  %_114 = getelementptr ptr, ptr %_100, i32 %o
  %_115 = load ptr, ptr %_114
  %_116 = getelementptr i32, ptr %_115, i32 %i.1.33.for.cond6.0.8
  %_117 = load i32, ptr %_116
  store i32 %_112, ptr %_116
  br label %for.inc6.0.8

for.inc6.0.8:
  %_119 = add i32 %i.1.33.for.cond6.0.8, 1
  br label %for.cond6.0.8

for.end6.0.8:
  %_120 = load ptr, ptr @now.0.1
  %_121 = getelementptr i32, ptr %_120, i32 0
  %_122 = load i32, ptr %_121
  store i32 0, ptr %_121
  br label %if.end4.0.8

if.else4.0.8:
  br label %if.end4.0.8

if.end4.0.8:
  %i.1.33.if.end4.0.8 = phi i32 [ 0 , %if.else4.0.8], [ %i.1.33.for.cond6.0.8 , %for.end6.0.8]
  br label %if.end2.0.4

if.end2.0.4:
  %lc.3.34.if.end2.0.4 = phi i32 [ %_70 , %if.end4.0.8], [0, %if.end4.0.7]
  %rc.3.35.if.end2.0.4 = phi i32 [ %_72 , %if.end4.0.8], [0, %if.end4.0.7]
  %i.1.33.if.end2.0.4 = phi i32 [ %i.1.33.if.end4.0.8 , %if.end4.0.8], [ %i.1.33.if.end4.0.7 , %if.end4.0.7]
  %mid.3.36.if.end2.0.4 = phi i32 [ %_76 , %if.end4.0.8], [0, %if.end4.0.7]
  br label %return7


return7:
  ret void

}

define void @pushdown(i32 %o) {
entry:
  %_0 = load ptr, ptr @t.0.2
  %_1 = getelementptr i32, ptr %_0, i32 %o
  %_2 = load i32, ptr %_1
  %_3 = icmp sgt i32 %_2, 0
  br i1 %_3, label %if.then2.0.9, label %if.else2.0.9

if.then2.0.9:
  %_5 = mul i32 %o, 2
  %_6 = mul i32 %o, 2
  %_7 = add i32 %_6, 1
  %_8 = load ptr, ptr @now.0.1
  %_9 = getelementptr i32, ptr %_8, i32 %_5
  %_10 = load i32, ptr %_9
  %_11 = load ptr, ptr @t.0.2
  %_12 = getelementptr i32, ptr %_11, i32 %o
  %_13 = load i32, ptr %_12
  %_14 = add i32 %_10, %_13
  %_15 = load i32, ptr @L.0.8
  %_16 = srem i32 %_14, %_15
  %_17 = getelementptr i32, ptr %_8, i32 %_5
  %_18 = load i32, ptr %_17
  store i32 %_16, ptr %_17
  %_19 = load ptr, ptr @s.0.10
  %_20 = getelementptr ptr, ptr %_19, i32 %_5
  %_21 = load ptr, ptr %_20
  %_22 = getelementptr i32, ptr %_8, i32 %_5
  %_23 = load i32, ptr %_22
  %_24 = getelementptr i32, ptr %_21, i32 %_23
  %_25 = load i32, ptr %_24
  %_26 = load ptr, ptr @sum.0.11
  %_27 = getelementptr i32, ptr %_26, i32 %_5
  %_28 = load i32, ptr %_27
  store i32 %_25, ptr %_27
  %_29 = getelementptr i32, ptr %_11, i32 %_5
  %_30 = load i32, ptr %_29
  %_31 = getelementptr i32, ptr %_11, i32 %o
  %_32 = load i32, ptr %_31
  %_33 = add i32 %_30, %_32
  %_34 = srem i32 %_33, %_15
  %_35 = getelementptr i32, ptr %_11, i32 %_5
  %_36 = load i32, ptr %_35
  store i32 %_34, ptr %_35
  %_37 = getelementptr i32, ptr %_8, i32 %_7
  %_38 = load i32, ptr %_37
  %_39 = getelementptr i32, ptr %_11, i32 %o
  %_40 = load i32, ptr %_39
  %_41 = add i32 %_38, %_40
  %_42 = srem i32 %_41, %_15
  %_43 = getelementptr i32, ptr %_8, i32 %_7
  %_44 = load i32, ptr %_43
  store i32 %_42, ptr %_43
  %_45 = getelementptr ptr, ptr %_19, i32 %_7
  %_46 = load ptr, ptr %_45
  %_47 = getelementptr i32, ptr %_8, i32 %_7
  %_48 = load i32, ptr %_47
  %_49 = getelementptr i32, ptr %_46, i32 %_48
  %_50 = load i32, ptr %_49
  %_51 = getelementptr i32, ptr %_26, i32 %_7
  %_52 = load i32, ptr %_51
  store i32 %_50, ptr %_51
  %_53 = getelementptr i32, ptr %_11, i32 %_7
  %_54 = load i32, ptr %_53
  %_55 = getelementptr i32, ptr %_11, i32 %o
  %_56 = load i32, ptr %_55
  %_57 = add i32 %_54, %_56
  %_58 = srem i32 %_57, %_15
  %_59 = getelementptr i32, ptr %_11, i32 %_7
  %_60 = load i32, ptr %_59
  store i32 %_58, ptr %_59
  %_61 = getelementptr i32, ptr %_11, i32 %o
  %_62 = load i32, ptr %_61
  store i32 0, ptr %_61
  br label %if.end2.0.9

if.else2.0.9:
  br label %if.end2.0.9

if.end2.0.9:
  %rc.3.39.if.end2.0.9 = phi i32 [ %_7 , %if.then2.0.9], [0, %if.else2.0.9]
  %lc.3.38.if.end2.0.9 = phi i32 [ %_5 , %if.then2.0.9], [0, %if.else2.0.9]
  br label %return8


return8:
  ret void

}

define void @update(i32 %o, i32 %l, i32 %r) {
entry:
  br label %log.lhs10

log.lhs10:
  br label %log.lhs11

log.lhs11:
  %_0 = load i32, ptr @pl.0.40
  %_2 = icmp sle i32 %_0, %l
  br i1 %_2, label %log.rhs11, label %log.end11

log.rhs11:
  %_3 = load i32, ptr @pr.0.41
  %_5 = icmp sge i32 %_3, %r
  br label %log.end11

log.end11:
  %_6 = select i1 %_2, i1 %_5, i1 %_2
  br i1 %_6, label %log.rhs10, label %log.end10

log.rhs10:
  %_7 = load ptr, ptr @flag.0.9
  %_9 = getelementptr i32, ptr %_7, i32 %o
  %_10 = load i32, ptr %_9
  %_11 = icmp sgt i32 %_10, 0
  br label %log.end10

log.end10:
  %_12 = select i1 %_6, i1 %_11, i1 %_6
  br i1 %_12, label %if.then2.0.12, label %if.else2.0.12

if.then2.0.12:
  %_13 = load ptr, ptr @now.0.1
  %_15 = getelementptr i32, ptr %_13, i32 %o
  %_16 = load i32, ptr %_15
  %_17 = add i32 %_16, 1
  %_18 = load i32, ptr @L.0.8
  %_19 = srem i32 %_17, %_18
  %_20 = getelementptr i32, ptr %_13, i32 %o
  %_21 = load i32, ptr %_20
  store i32 %_19, ptr %_20
  %_22 = load ptr, ptr @s.0.10
  %_23 = getelementptr ptr, ptr %_22, i32 %o
  %_24 = load ptr, ptr %_23
  %_25 = getelementptr i32, ptr %_13, i32 %o
  %_26 = load i32, ptr %_25
  %_27 = getelementptr i32, ptr %_24, i32 %_26
  %_28 = load i32, ptr %_27
  %_29 = load ptr, ptr @sum.0.11
  %_30 = getelementptr i32, ptr %_29, i32 %o
  %_31 = load i32, ptr %_30
  store i32 %_28, ptr %_30
  %_32 = load ptr, ptr @t.0.2
  %_33 = getelementptr i32, ptr %_32, i32 %o
  %_34 = load i32, ptr %_33
  %_35 = add i32 %_34, 1
  %_36 = srem i32 %_35, %_18
  %_37 = getelementptr i32, ptr %_32, i32 %o
  %_38 = load i32, ptr %_37
  store i32 %_36, ptr %_37
  br label %return9

if.else2.0.12:
  br label %if.end2.0.12

if.end2.0.12:
  %_41 = icmp eq i32 %l, %r
  br i1 %_41, label %if.then2.3.13, label %if.else2.3.13

if.then2.3.13:
  %_42 = load ptr, ptr @sum.0.11
  %_44 = getelementptr i32, ptr %_42, i32 %o
  %_45 = load i32, ptr %_44
  %_46 = call i32 @square(i32 %_45)
  %_47 = load i32, ptr @p.0.6
  %_48 = srem i32 %_46, %_47
  %_49 = getelementptr i32, ptr %_42, i32 %o
  %_50 = load i32, ptr %_49
  store i32 %_48, ptr %_49
  %_51 = load ptr, ptr @b.0.0
  %_52 = getelementptr i32, ptr %_42, i32 %o
  %_53 = load i32, ptr %_52
  %_54 = getelementptr i32, ptr %_51, i32 %_53
  %_55 = load i32, ptr %_54
  %_56 = icmp sgt i32 %_55, 0
  br i1 %_56, label %if.then4.0.14, label %if.else4.0.14

if.then4.0.14:
  %_57 = load ptr, ptr @flag.0.9
  %_59 = getelementptr i32, ptr %_57, i32 %o
  %_60 = load i32, ptr %_59
  store i32 1, ptr %_59
  %_61 = load ptr, ptr @sum.0.11
  %_62 = getelementptr i32, ptr %_61, i32 %o
  %_63 = load i32, ptr %_62
  %_64 = load ptr, ptr @s.0.10
  %_65 = getelementptr ptr, ptr %_64, i32 %o
  %_66 = load ptr, ptr %_65
  %_67 = getelementptr i32, ptr %_66, i32 0
  %_68 = load i32, ptr %_67
  store i32 %_63, ptr %_67
  br label %for.cond6.0.9

for.cond6.0.9:
  %i.5.45.for.cond6.0.9 = phi i32 [ %_88 , %for.inc6.0.9], [ 1 , %if.then4.0.14]
  %_70 = load i32, ptr @L.0.8
  %_71 = icmp slt i32 %i.5.45.for.cond6.0.9, %_70
  br i1 %_71, label %for.body6.0.9, label %for.end6.0.9

for.body6.0.9:
  %_72 = load ptr, ptr @s.0.10
  %_74 = getelementptr ptr, ptr %_72, i32 %o
  %_75 = load ptr, ptr %_74
  %_77 = sub i32 %i.5.45.for.cond6.0.9, 1
  %_78 = getelementptr i32, ptr %_75, i32 %_77
  %_79 = load i32, ptr %_78
  %_80 = call i32 @square(i32 %_79)
  %_81 = load i32, ptr @p.0.6
  %_82 = srem i32 %_80, %_81
  %_83 = getelementptr ptr, ptr %_72, i32 %o
  %_84 = load ptr, ptr %_83
  %_85 = getelementptr i32, ptr %_84, i32 %i.5.45.for.cond6.0.9
  %_86 = load i32, ptr %_85
  store i32 %_82, ptr %_85
  br label %for.inc6.0.9

for.inc6.0.9:
  %_88 = add i32 %i.5.45.for.cond6.0.9, 1
  br label %for.cond6.0.9

for.end6.0.9:
  br label %if.end4.0.14

if.else4.0.14:
  br label %if.end4.0.14

if.end4.0.14:
  %i.5.45.if.end4.0.14 = phi i32 [ %i.5.45.for.cond6.0.9 , %for.end6.0.9], [0, %if.else4.0.14]
  br label %return9

if.else2.3.13:
  br label %if.end2.3.13

if.end2.3.13:
  %_89 = load ptr, ptr @t.0.2
  %_91 = getelementptr i32, ptr %_89, i32 %o
  %_92 = load i32, ptr %_91
  %_93 = icmp sgt i32 %_92, 0
  br i1 %_93, label %if.then2.6.15, label %if.else2.6.15

if.then2.6.15:
  call void @pushdown(i32 %o)
  br label %if.end2.6.15

if.else2.6.15:
  br label %if.end2.6.15

if.end2.6.15:
  %_96 = mul i32 %o, 2
  %_97 = mul i32 %o, 2
  %_98 = add i32 %_97, 1
  %_101 = add i32 %l, %r
  %_102 = sdiv i32 %_101, 2
  %_103 = load i32, ptr @pl.0.40
  %_104 = icmp sle i32 %_103, %_102
  br i1 %_104, label %if.then2.9.16, label %if.else2.9.16

if.then2.9.16:
  call void @update(i32 %_96, i32 %l, i32 %_102)
  br label %if.end2.9.16

if.else2.9.16:
  br label %if.end2.9.16

if.end2.9.16:
  %_108 = load i32, ptr @pr.0.41
  %_110 = add i32 %_102, 1
  %_111 = icmp sge i32 %_108, %_110
  br i1 %_111, label %if.then2.12.17, label %if.else2.12.17

if.then2.12.17:
  %_114 = add i32 %_102, 1
  call void @update(i32 %_98, i32 %_114, i32 %r)
  br label %if.end2.12.17

if.else2.12.17:
  br label %if.end2.12.17

if.end2.12.17:
  %_116 = load ptr, ptr @sum.0.11
  %_118 = getelementptr i32, ptr %_116, i32 %_96
  %_119 = load i32, ptr %_118
  %_121 = getelementptr i32, ptr %_116, i32 %_98
  %_122 = load i32, ptr %_121
  %_123 = add i32 %_119, %_122
  %_125 = getelementptr i32, ptr %_116, i32 %o
  %_126 = load i32, ptr %_125
  store i32 %_123, ptr %_125
  %_127 = load ptr, ptr @flag.0.9
  %_128 = getelementptr i32, ptr %_127, i32 %_96
  %_129 = load i32, ptr %_128
  %_130 = getelementptr i32, ptr %_127, i32 %_98
  %_131 = load i32, ptr %_130
  %_132 = and i32 %_129, %_131
  %_133 = getelementptr i32, ptr %_127, i32 %o
  %_134 = load i32, ptr %_133
  store i32 %_132, ptr %_133
  %_135 = getelementptr i32, ptr %_127, i32 %o
  %_136 = load i32, ptr %_135
  %_137 = icmp sgt i32 %_136, 0
  br i1 %_137, label %if.then2.15.18, label %if.else2.15.18

if.then2.15.18:
  br label %for.cond4.0.10

for.cond4.0.10:
  %i.3.49.for.cond4.0.10 = phi i32 [ 0 , %if.then2.15.18], [ %_170 , %for.inc4.0.10]
  %_139 = load i32, ptr @L.0.8
  %_140 = icmp slt i32 %i.3.49.for.cond4.0.10, %_139
  br i1 %_140, label %for.body4.0.10, label %for.end4.0.10

for.body4.0.10:
  %_141 = load ptr, ptr @s.0.10
  %_143 = getelementptr ptr, ptr %_141, i32 %_96
  %_144 = load ptr, ptr %_143
  %_146 = load ptr, ptr @now.0.1
  %_147 = getelementptr i32, ptr %_146, i32 %_96
  %_148 = load i32, ptr %_147
  %_149 = add i32 %i.3.49.for.cond4.0.10, %_148
  %_150 = load i32, ptr @L.0.8
  %_151 = srem i32 %_149, %_150
  %_152 = getelementptr i32, ptr %_144, i32 %_151
  %_153 = load i32, ptr %_152
  %_155 = getelementptr ptr, ptr %_141, i32 %_98
  %_156 = load ptr, ptr %_155
  %_157 = getelementptr i32, ptr %_146, i32 %_98
  %_158 = load i32, ptr %_157
  %_159 = add i32 %i.3.49.for.cond4.0.10, %_158
  %_160 = srem i32 %_159, %_150
  %_161 = getelementptr i32, ptr %_156, i32 %_160
  %_162 = load i32, ptr %_161
  %_163 = add i32 %_153, %_162
  %_165 = getelementptr ptr, ptr %_141, i32 %o
  %_166 = load ptr, ptr %_165
  %_167 = getelementptr i32, ptr %_166, i32 %i.3.49.for.cond4.0.10
  %_168 = load i32, ptr %_167
  store i32 %_163, ptr %_167
  br label %for.inc4.0.10

for.inc4.0.10:
  %_170 = add i32 %i.3.49.for.cond4.0.10, 1
  br label %for.cond4.0.10

for.end4.0.10:
  %_171 = load ptr, ptr @now.0.1
  %_173 = getelementptr i32, ptr %_171, i32 %o
  %_174 = load i32, ptr %_173
  store i32 0, ptr %_173
  br label %if.end2.15.18

if.else2.15.18:
  br label %if.end2.15.18

if.end2.15.18:
  %i.3.49.if.end2.15.18 = phi i32 [ %i.3.49.for.cond4.0.10 , %for.end4.0.10], [0, %if.else2.15.18]
  br label %return9


return9:
  %i.5.45.return9 = phi i32 [ %i.5.45.if.end4.0.14 , %if.end4.0.14], [0, %if.end2.15.18], [0, %if.then2.0.12]
  %rc.1.47.return9 = phi i32 [0, %if.end4.0.14], [ %_98 , %if.end2.15.18], [0, %if.then2.0.12]
  %i.3.49.return9 = phi i32 [0, %if.end4.0.14], [ %i.3.49.if.end2.15.18 , %if.end2.15.18], [0, %if.then2.0.12]
  %lc.1.46.return9 = phi i32 [0, %if.end4.0.14], [ %_96 , %if.end2.15.18], [0, %if.then2.0.12]
  %mid.1.48.return9 = phi i32 [0, %if.end4.0.14], [ %_102 , %if.end2.15.18], [0, %if.then2.0.12]
  ret void

}

define i32 @query(i32 %o, i32 %l, i32 %r) {
entry:
  br label %log.lhs19

log.lhs19:
  %_0 = load i32, ptr @pl.0.40
  %_2 = icmp sle i32 %_0, %l
  br i1 %_2, label %log.rhs19, label %log.end19

log.rhs19:
  %_3 = load i32, ptr @pr.0.41
  %_5 = icmp sge i32 %_3, %r
  br label %log.end19

log.end19:
  %_6 = select i1 %_2, i1 %_5, i1 %_2
  br i1 %_6, label %if.then2.0.20, label %if.else2.0.20

if.then2.0.20:
  %_7 = load ptr, ptr @sum.0.11
  %_9 = getelementptr i32, ptr %_7, i32 %o
  %_10 = load i32, ptr %_9
  br label %return10

if.else2.0.20:
  br label %if.end2.0.20

if.end2.0.20:
  %_12 = mul i32 %o, 2
  %_13 = mul i32 %o, 2
  %_14 = add i32 %_13, 1
  %_17 = add i32 %l, %r
  %_18 = sdiv i32 %_17, 2
  %_19 = load ptr, ptr @t.0.2
  %_20 = getelementptr i32, ptr %_19, i32 %o
  %_21 = load i32, ptr %_20
  %_22 = icmp sgt i32 %_21, 0
  br i1 %_22, label %if.then2.3.21, label %if.else2.3.21

if.then2.3.21:
  call void @pushdown(i32 %o)
  br label %if.end2.3.21

if.else2.3.21:
  br label %if.end2.3.21

if.end2.3.21:
  %_24 = load i32, ptr @pl.0.40
  %_26 = icmp sle i32 %_24, %_18
  br i1 %_26, label %if.then2.6.22, label %if.else2.6.22

if.then2.6.22:
  %_31 = call i32 @query(i32 %_12, i32 %l, i32 %_18)
  %_32 = add i32 0, %_31
  %_33 = load i32, ptr @MOD.0.20
  %_34 = srem i32 %_32, %_33
  br label %if.end2.6.22

if.else2.6.22:
  br label %if.end2.6.22

if.end2.6.22:
  %ss.1.56.if.end2.6.22 = phi i32 [ 0 , %if.else2.6.22], [ %_34 , %if.then2.6.22]
  %_35 = load i32, ptr @pr.0.41
  %_37 = add i32 %_18, 1
  %_38 = icmp sge i32 %_35, %_37
  br i1 %_38, label %if.then2.9.23, label %if.else2.9.23

if.then2.9.23:
  %_42 = add i32 %_18, 1
  %_44 = call i32 @query(i32 %_14, i32 %_42, i32 %r)
  %_45 = add i32 %ss.1.56.if.end2.6.22, %_44
  %_46 = load i32, ptr @MOD.0.20
  %_47 = srem i32 %_45, %_46
  br label %if.end2.9.23

if.else2.9.23:
  br label %if.end2.9.23

if.end2.9.23:
  %ss.1.56.if.end2.9.23 = phi i32 [ %_47 , %if.then2.9.23], [ %ss.1.56.if.end2.6.22 , %if.else2.9.23]
  br label %return10


return10:
  %ss.1.56.return10 = phi i32 [0, %if.then2.0.20], [ %ss.1.56.if.end2.9.23 , %if.end2.9.23]
  %rc.1.54.return10 = phi i32 [0, %if.then2.0.20], [ %_14 , %if.end2.9.23]
  %lc.1.53.return10 = phi i32 [0, %if.then2.0.20], [ %_12 , %if.end2.9.23]
  %ret.val.return10 = phi i32 [ %_10 , %if.then2.0.20], [ %ss.1.56.if.end2.9.23 , %if.end2.9.23]
  %mid.1.55.return10 = phi i32 [0, %if.then2.0.20], [ %_18 , %if.end2.9.23]
  ret i32 %ret.val.return10

}

define void @__init__() {
entry:
  %_0 = call ptr @_arr_init(i32 500005)
  store ptr %_0, ptr @b.0.0
  %_1 = call ptr @_arr_init(i32 500005)
  store ptr %_1, ptr @now.0.1
  %_2 = call ptr @_arr_init(i32 500005)
  store ptr %_2, ptr @t.0.2
  %_3 = call ptr @_arr_init(i32 200005)
  store ptr %_3, ptr @a.0.3
  store i32 1, ptr @L.0.8
  %_4 = call ptr @_arr_init(i32 500005)
  store ptr %_4, ptr @flag.0.9
  %_5 = call ptr @_arr_init(i32 500005)
  br label %for.cond.0

for.cond.0:
  %.0.for.cond.0 = phi i32 [0, %entry], [ %_10 , %for.inc.0]
  %_7 = icmp slt i32 %.0.for.cond.0, 500005
  br i1 %_7, label %for.body.0, label %for.end.0

for.body.0:
  %_8 = call ptr @_arr_init(i32 80)
  %_9 = getelementptr ptr, ptr %_5, i32 %.0.for.cond.0
  store ptr %_8, ptr %_9
  br label %for.inc.0

for.inc.0:
  %_10 = add i32 %.0.for.cond.0, 1
  br label %for.cond.0

for.end.0:
  store ptr %_5, ptr @s.0.10
  %_11 = call ptr @_arr_init(i32 500005)
  store ptr %_11, ptr @sum.0.11
  store i32 0, ptr @ans.0.12
  store i32 13131, ptr @aa.0.18
  store i32 5353, ptr @bb.0.19
  %_12 = shl i32 2, 14
  %_13 = sub i32 %_12, 7
  store i32 %_13, ptr @MOD.0.20
  store i32 1, ptr @no.0.21
  store i32 0, ptr @pl.0.40
  store i32 0, ptr @pr.0.41
  br label %return0


return0:
  ret void

}

define i32 @main() {
entry:
  call void @__init__()
  %_0 = call i32 @getInt()
  store i32 %_0, ptr @n.0.4
  %_1 = call i32 @getInt()
  store i32 %_1, ptr @m.0.5
  %_2 = call i32 @getInt()
  store i32 %_2, ptr @p.0.6
  br label %for.cond2.0.11

for.cond2.0.11:
  %i.1.57.for.cond2.0.11 = phi i32 [ 1 , %entry], [ %_13 , %for.inc2.0.11]
  %_4 = load i32, ptr @n.0.4
  %_5 = icmp sle i32 %i.1.57.for.cond2.0.11, %_4
  br i1 %_5, label %for.body2.0.11, label %for.end2.0.11

for.body2.0.11:
  %_6 = load i32, ptr @p.0.6
  %_7 = call i32 @RandRange(i32 0, i32 %_6)
  %_8 = load ptr, ptr @a.0.3
  %_10 = getelementptr i32, ptr %_8, i32 %i.1.57.for.cond2.0.11
  %_11 = load i32, ptr %_10
  store i32 %_7, ptr %_10
  br label %for.inc2.0.11

for.inc2.0.11:
  %_13 = add i32 %i.1.57.for.cond2.0.11, 1
  br label %for.cond2.0.11

for.end2.0.11:
  call void @init()
  %_14 = load i32, ptr @n.0.4
  call void @build(i32 1, i32 1, i32 %_14)
  br label %while.cond2.1.12

while.cond2.1.12:
  %_15 = load i32, ptr @m.0.5
  %_16 = icmp sgt i32 %_15, 0
  br i1 %_16, label %while.body2.1.12, label %while.end2.1.12

while.body2.1.12:
  %_17 = call i32 @Rand()
  %_18 = srem i32 %_17, 2
  store i32 %_18, ptr @op.0.7
  %_19 = load i32, ptr @n.0.4
  %_20 = call i32 @RandRange(i32 1, i32 %_19)
  store i32 %_20, ptr @pl.0.40
  %_21 = call i32 @RandRange(i32 1, i32 %_19)
  store i32 %_21, ptr @pr.0.41
  br label %while.cond4.0.13

while.cond4.0.13:
  %_22 = load i32, ptr @pr.0.41
  %_23 = load i32, ptr @pl.0.40
  %_24 = icmp sle i32 %_22, %_23
  br i1 %_24, label %while.body4.0.13, label %while.end4.0.13

while.body4.0.13:
  %_25 = load i32, ptr @n.0.4
  %_26 = call i32 @RandRange(i32 1, i32 %_25)
  store i32 %_26, ptr @pr.0.41
  br label %while.cond4.0.13

while.end4.0.13:
  %_27 = load i32, ptr @op.0.7
  %_28 = icmp eq i32 %_27, 0
  br i1 %_28, label %if.then4.1.24, label %if.else4.1.24

if.then4.1.24:
  %_29 = load i32, ptr @n.0.4
  call void @update(i32 1, i32 1, i32 %_29)
  br label %if.end4.1.24

if.else4.1.24:
  br label %if.end4.1.24

if.end4.1.24:
  %_30 = load i32, ptr @op.0.7
  %_31 = icmp eq i32 %_30, 1
  br i1 %_31, label %if.then4.4.25, label %if.else4.4.25

if.then4.4.25:
  %_32 = load i32, ptr @ans.0.12
  %_33 = load i32, ptr @n.0.4
  %_34 = call i32 @query(i32 1, i32 1, i32 %_33)
  %_35 = add i32 %_32, %_34
  %_36 = load i32, ptr @MOD.0.20
  %_37 = srem i32 %_35, %_36
  store i32 %_37, ptr @ans.0.12
  br label %if.end4.4.25

if.else4.4.25:
  br label %if.end4.4.25

if.end4.4.25:
  %_38 = load i32, ptr @m.0.5
  %_39 = sub i32 %_38, 1
  store i32 %_39, ptr @m.0.5
  br label %while.cond2.1.12

while.end2.1.12:
  %_40 = load i32, ptr @ans.0.12
  %_41 = call ptr @toString(i32 %_40)
  call void @print(ptr %_41)
  br label %return11


return11:
  ret i32 0

}

