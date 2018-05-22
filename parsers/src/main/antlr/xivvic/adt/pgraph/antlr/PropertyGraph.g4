grammar PropertyGraph;

@header {
package xivvic.adt.pgraph.antlr;
} 

graph: vertex+ edge* EOF ;


vertex: '(' vname ':' label (':' label)* properties? ')' ;
vname: NAME;

edge:   '(' vname ')' '-'+ '[' ':' ename properties? ']' '-'+ '>' '(' vname ')' ;
ename: NAME;

label: NAME;

properties: '{' prop (',' prop)+ '}' ;
prop      : pname EQUAL value ;
pname     : NAME;

value     : 
	  string
	| bool
	| integer
	| flt
	| dbl
	;
	
string : SQSTR | DQSTR ;
bool   : 'true' | 'TRUE' | 'True' | 'false' | 'FALSE' | 'False' ;
integer: INT;
flt    : FLOAT;
dbl    : DOUBLE;


// Lexical Items
//

// Names
//
NAME: [a-zA-Z_][a-zA-Z0-9._+-]* ;

// Strings (single and double quoted)
//
SQSTR : '\'' (~['"] | '\\' '\'' | DQSTR)* '\'';
DQSTR : '"'  (~['"] | '\\"' | SQSTR)* '"';

// Values
//
INT: '0' | '-'? [1-9][0-9]* ;
REAL: '-'? INT '.' [0-9]+ ;
FLOAT: REAL ('f' | 'F') ;
DOUBLE: REAL ('d' | 'D') ;

// Whitespace & Comments
//
LINE_COMMENT : ('#'|'//') .*? '\r'? '\n' -> skip ;
LINE_ESCAPE: '\\' '\r'? '\n'             -> skip ;
WS: [ \t]+                               -> skip ;
NL: '\r'? '\n'                           -> skip;
