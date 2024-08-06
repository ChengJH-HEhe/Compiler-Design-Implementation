grammar Mx;
@header {package parser;}

program: (funcDef | classDef | (varDef ';'))* EOF;

classDef: Class Identifier '{' (varDef ';'| funcDef | constr)* '}' ';';

constr: Identifier '(' ')' block;

funcDef: returnType Identifier '(' arglist? ')' block;
arglist: type Identifier(Comma type Identifier)*;

varDef: type varDefn (Comma varDefn)*;
varDefn: Identifier(Assign expr)?;

returnType: Void | type;
type: typename ('[' ']')*;
typename : baseType | Identifier;
baseType : Bool | Int | String;

block: '{' stmt* '}';

// 不允许无限递归自身
arrayConst:
    ( '{' (((literal Comma)* literal) | ()) '}');

//


literal : True | False | IntegerConst | StringConst | Null | arrayConst | formatStr;

primary
    : Identifier
    | literal
    | This
    ;
stmt
    : block
    | varDef ';'
    | If '(' expr ')' thenStmt = stmt
        (Else elseStmt = stmt)?
    | While '(' expr ')' stmt
    | For '(' initStmt = stmt  condExpr = expr? ';' stepexpr = expr ')'
    | Return  expr? ';'
    | Break ';'
    | Continue ';'
    | expr ';'
    | ';'
;

expr
   : New typename ('[' expr? ']') + (arrayConst?)
   | New typename ('(' ')')?
   | expr '(' (expr (Comma expr)*)? ')'
   | expr '[' expr ']'
   | expr op=Member Identifier
   | expr op= (Increment | Decrement)
   | <assoc = right> op= (Not | LogicNot | Minus | Plus) expr
   | <assoc = right> op= (Increment | Decrement) expr
   | expr op= (Mul | Div | Mod) expr
   | expr op= (Plus | Minus) expr
   | expr op= (LeftShift | RightShift) expr
   | expr op= (Greater | GreaterEqual | Less | LessEqual) expr
   | expr op= (Equal | UnEqual) expr
   | expr op= And expr
   | expr op= Xor expr
   | expr op= Or expr
   | expr op= LogicAnd expr
   | expr op= LogicOr expr
   | <assoc = right> expr '?' expr ':' expr
   | <assoc = right> expr op=Assign expr
   | '(' expr ')'
   | primary
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


WhiteSpace: [\t\r\n ]+ -> skip;
LineComment: '//' ~[\r\n]* -> skip;
ParaComment: '/*' .*? '*/' -> skip;
Non_s : ~[$] | Escape;

formatStr: // to the first $
     (Fstring_l expr (Fstring_m expr )*  Fstring_lst) | FstringConst | ()
;

