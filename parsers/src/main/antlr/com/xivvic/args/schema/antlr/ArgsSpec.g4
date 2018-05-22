grammar ArgsSpec;

@header {
package com.xivvic.args.schema.antlr;
} 

start: item+ EOF ;
item: item_header name_value* ;
item_header: LBRACK name RBRACK ;
name: NAME ;
name_value: name (COLON | EQUAL)  value? ;
value: NAME | STRING ;


// Lexical Items
//
NAME: [a-zA-Z0-9._+-]+ ;
STRING: '"' ('\\"' | '\\\\' | ~[\\"])* '"' ;
LINE_COMMENT : ('#'|'//') .*? '\r'? '\n' -> skip ;
LINE_ESCAPE: '\\' '\r'? '\n' -> skip ;
WS: [ \t]+ -> skip ;
NL: '\r'? '\n' -> skip;

LBRACK: '[' ;
RBRACK: ']' ;
COLON: ':' ;
EQUAL: '=' ;
