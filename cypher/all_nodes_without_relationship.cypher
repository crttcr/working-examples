// Find all nodes that do not have a specific relationship
//
MATCH (a:Foo) WHERE not ((a)-[:has]->(:Bar)) RETURN a;

