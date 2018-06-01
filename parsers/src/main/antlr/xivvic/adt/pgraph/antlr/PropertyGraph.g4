grammar PropertyGraph;

@header {
package xivvic.adt.pgraph.antlr;
}

// A Property Graph consists of up to 3 different types of records in the following order:
//
// pragma  e.g.  ## edge.auto.id="GENERATE"
// vertex  e.g.  (c:COMPONENT:SINK { color = "red" })
// edge    e.g.  (c)--[:RUNS_ON {cpu = 2.0}]->(s)
//

graph: pragma* vertex+ edge* EOF ;

pragma: '##' prop ;

vertex: '(' NAME ':' label (':' label)* properties? ')' ;
label: NAME;

edge:   '(' NAME ')' '-'+ '[' ':' NAME properties? ']' '-'+ '>' '(' NAME ')' ;


// If the properties rule is matched, i.e. the input text has an opening '{'
// then there must be at least one property.
//
// An empty set of properties can simply be indicated in the source as omitting
// the opening and closing curly braces, {}.
// 
// Also, the list of properties can end in a comma, for convenience
//
properties: '{' prop (',' prop)* ','? '}' ;
prop      : NAME '=' value ;

value
    : STRING
    | NAME      // Treated like a string.
	| BOOL
	| INT
	| FLOAT
	| DOUBLE
	;

// Lexical Items
//


// Strings (single and double quoted)
//
STRING : SQSTR | DQSTR ;
SQSTR : '\'' (~['"] | '\\' '\'' | DQSTR)* '\'';
DQSTR : '"'  (~['"] | '\\"' | SQSTR)* '"';

// Boolean
//
BOOL   : TRUE | FALSE ;
TRUE   : 'true'  ;
FALSE  : 'false' ;


// Numerics
//
INT: '0' | '-'? [1-9][0-9]* ;
REAL: '-'? INT '.' [0-9]+ ;
FLOAT: REAL ('f' | 'F') ;
DOUBLE: REAL ('d' | 'D') ;

// Names 
// (Since names can match 'true' and 'false' and the like, they need to come at the end of the lexer rules)
//
NAME: [a-zA-Z_][a-zA-Z0-9._+-]* ;


// Whitespace & Comments
//
LINE_COMMENT : '//'.*? '\r'? '\n' -> skip ;
LINE_ESCAPE: '\\' '\r'? '\n'             -> skip ;
WS: [ \t]+                               -> skip ;
NL: '\r'? '\n'                           -> skip;



