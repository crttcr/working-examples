grammar PropertyGraph;

@header {
package xivvic.adt.pgraph.antlr;
}

// A Property Graph consists of up to 3 different types of records in the following order:
//
// pragma  e.g.  ## edge.auto.id=GENERATE
// vertex  e.g.  (c:COMPONENT:SINK { color = "red" })
// edge    e.g.  (c)--[:RUNS_ON {cpu = 2.0}]->(s)
//

graph: pragma* vertex+ edge* EOF ;

pragma: '##' prop ;

vertex: '(' vname ':' label (':' label)* properties? ')' ;
vname: NAME;

edge:   '(' vname ')' '-'+ '[' ':' ename properties? ']' '-'+ '>' '(' vname ')' ;
ename: NAME;

label: NAME;

// If the properties rule is matched, i.e. the input text has an opening '{'
// then there must be at least one property.
//
// An empty set of properties can simply be indicated in the source as omitting
// the opening and closing curly braces, {}.
//
properties: '{' prop (',' prop)* '}' ;
prop      : pname '=' value ;
pname     : NAME;

value
    : STRING
	| BOOL
	| INT
	| FLOAT
	| DOUBLE
	;

// Lexical Items
//

// Names
//
NAME: [a-zA-Z_][a-zA-Z0-9._+-]* ;

// Strings (single and double quoted)
//
STRING : SQSTR | DQSTR ;
SQSTR : '\'' (~['"] | '\\' '\'' | DQSTR)* '\'';
DQSTR : '"'  (~['"] | '\\"' | SQSTR)* '"';

// Boolean
//
BOOL   : 'true' | 'TRUE' | 'True' | 'false' | 'FALSE' | 'False' ;

// Numerics
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
