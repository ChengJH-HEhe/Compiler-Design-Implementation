grammar Mx;
@header {package parser;}

program: (funcDef | classDef | (vardefstmt))* EOF;

classDef: Class Identifier '{' ( vardefstmt| funcDef | constr)* '}' ';';

constr: Identifier '(' ')' block;

funcDef: returnType Identifier '(' arglist? ')' block;
arglist: type Identifier(Comma type Identifier)*;

vardefstmt: type varDefn (Comma varDefn)* ';' ;
varDefn: Identifier(Assign expr)?;

returnType: Void | type;
type: typename ('[' ']')*;
typename : baseType | Identifier;
baseType : Bool | Int | String;

block: '{' stmt* '}';

// 不允许无限递归自身
arrayConst:
    ( '{' ((((literal) Comma)* literal) | ()) '}');

//


literal : True | False | IntegerConst | StringConst | Null | arrayConst;

primary
    : Identifier
    | literal
    | This
    ;
stmt
    : block #blockStmt
    | vardefstmt #vardefStmt
    | If '(' expr ')' thenStmt = stmt 
        (Else elseStmt = stmt)? #ifStmt
    | While '(' expr ')' stmt #whileStmt
    | For '(' initStmt = stmt  condExpr = expr? ';' stepexpr = expr ')' bodystmt = stmt #forStmt
    | Return  expr? ';' #returnStmt
    | Break ';' #breakStmt
    | Continue ';' #continueStmt
    | expr ';' #exprStmt
    | ';' #emptyStmt
;


expr
   : New typename ('[' expr? ']') + (arrayConst?) #newArrayExpr
   | New typename ('(' ')')? #newVarExpr
   | expr '(' (expr (Comma expr)*)? ')' #callExpr
   | expr '[' expr ']' #arrayExpr
   | expr op=Member Identifier #memberExpr
   | expr op= (Increment | Decrement) #unaryExpr
   | <assoc = right> op= (Not | LogicNot | Minus | Plus) expr #unaryExpr
   | <assoc = right> op= (Increment | Decrement) expr #preSelfExpr
   | expr op= (Mul | Div | Mod) expr #binaryExpr
   | expr op= (Plus | Minus) expr #binaryExpr
   | expr op= (LeftShift | RightShift) expr #binaryExpr
   | expr op= (Greater | GreaterEqual | Less | LessEqual) expr #binaryExpr
   | expr op= (Equal | UnEqual) expr #binaryExpr
   | expr op= And expr #binaryExpr
   | expr op= Xor expr #binaryExpr
   | expr op= Or expr #binaryExpr
   | expr op= LogicAnd expr #binaryExpr
   | expr op= LogicOr expr #binaryExpr
   | <assoc = right> expr '?' expr ':' expr #conditionalExpr
   | <assoc = right> expr op=Assign expr #assignExpr
   | '(' expr ')' #parenExpr
   | primary #atomExpr
   | formatStr #fStrExpr
;

Void : 'void';
Bool : 'bool';
Int : 'int';
String : 'string';
New : 'new';
Class : 'class';
Null : 'null';
True : 'true';
False : 'false';
This : 'this';
If : 'if';
Else : 'else';
For : 'for';
While : 'while';
Break : 'break';
Continue : 'continue';
Return : 'return';

Plus: '+';
Minus: '-';
Mul: '*';
Div: '/';
Mod: '%';

Greater: '>';
Less: '<';
GreaterEqual: '>=';
LessEqual: '<=';
UnEqual: '!=';
Equal: '==';

LogicAnd: '&&';
LogicOr: '||';
LogicNot: '!';

RightShift: '>>';
LeftShift: '<<';
And: '&';
Or: '|';
Xor: '^';
Not: '~';

Assign: '=';

Increment: '++';
Decrement: '--';

Member: '.';

Identifier: [a-zA-Z][a-zA-Z0-9_]*;

LParen: '(';
RParen: ')';
LBracket: '[';
RBracket: ']';
LBrace: '{';
RBrace: '}';

Question: '?';
Colon: ':';
Semi : ';';
Comma : ',';

Quote: '"';
Escape: '\\\\' | '\\n' | '\\"';
IntegerConst: '0' | [1-9][0-9]*;

FstringConst: 'f'(Quote (~[$"] | '$$')*? Quote);
Fstring_l : 'f' Quote ((~[$"]|('$$'))*) '$';
Fstring_m : '$' (~[$"]|('$$'))* '$';
StringConst: (Quote (Escape | .)*? Quote);
Fstring_lst : '$' ((~[$"]|('$$'))*) Quote;
formatStr: // to the first $
     (Fstring_l expr (Fstring_m expr )*  Fstring_lst) | FstringConst
;

WhiteSpace: [\t\r\n ]+ -> skip;
LineComment: '//' ~[\r\n]* -> skip;
ParaComment: '/*' .*? '*/' -> skip;
Non_s : ~[$] | Escape;



