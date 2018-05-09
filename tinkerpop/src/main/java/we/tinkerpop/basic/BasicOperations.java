package we.tinkerpop.basic;

import java.io.IOException;

import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
public class BasicOperations
{
    private GraphTraversalSource g;
    
    private Graph graph;
    
    public String name()
    {
	return "foo";
    }
    
    public Traversal<Vertex,Vertex> vertices()
    {
	return g.V();
    }
    
    public String[] components()
    {
	String[] rv = {"a", "b", "c"};
	
	return rv;
    }


    public boolean loadGraph(String name)
    {
	boolean ok = false;
	switch (name)
	{
	case "RDC":
	    graph = TinkerGraph.open(); //1
	    Vertex     svc = graph.addVertex(T.label, "service",   T.id, 1, "name", "vadas", "age", 27);
	    Vertex     src = graph.addVertex(T.label, "topic",     T.id, 2, "label", "source");
	    Vertex  events = graph.addVertex(T.label, "topic",     T.id, 3, "label", "events");
	    Vertex   retry = graph.addVertex(T.label, "topic",     T.id, 4, "label", "retry");
	    Vertex failure = graph.addVertex(T.label, "topic",     T.id, 5, "label", "failure");
	    
	    svc.addEdge("reads",       src, T.id, 11, "weight", 0.5f); //3
	    svc.addEdge("writes",   events, T.id, 12, "weight", 0.5f); //3
	    svc.addEdge("retry",     retry, T.id, 13, "weight", 0.5f); //3
	    svc.addEdge("failure", failure, T.id, 14, "weight", 0.5f); //3
	    
	    g = graph.traversal();
	    ok = true;
	    break;
	case "Modern":
	    graph = TinkerFactory.createModern();
	    /*
	    Vertex marko = graph.addVertex(T.label, "person", T.id, 1, "name", "marko", "age", 29); //2
	    Vertex vadas = graph.addVertex(T.label, "person", T.id, 2, "name", "vadas", "age", 27);
	    Vertex lop = graph.addVertex(T.label, "software", T.id, 3, "name", "lop", "lang", "java");
	    Vertex josh = graph.addVertex(T.label, "person", T.id, 4, "name", "josh", "age", 32);
	    Vertex ripple = graph.addVertex(T.label, "software", T.id, 5, "name", "ripple", "lang", "java");
	    Vertex peter = graph.addVertex(T.label, "person", T.id, 6, "name", "peter", "age", 35);
	    marko.addEdge("knows", vadas, T.id, 7, "weight", 0.5f); //3
	    marko.addEdge("knows", josh, T.id, 8, "weight", 1.0f);
	    marko.addEdge("created", lop, T.id, 9, "weight", 0.4f);
	    josh.addEdge("created", ripple, T.id, 10, "weight", 1.0f);
	    josh.addEdge("created", lop, T.id, 11, "weight", 0.4f);
	    peter.addEdge("created", lop, T.id, 12, "weight", 0.2f);
	    */
	    
	    g = graph.traversal();
	    ok = true;
	    break;
	case "KAFKA":
	    graph = readGraphML("src/test/resources/kafka.graphml");
	    g     = graph.traversal();
	    ok    = true;
	    break;
	case "DEPLOYMENT":
	    graph = readGraphson("src/test/resources/modern.graphson");
	    g     = graph.traversal();
	    ok    = true;
	    break;
	default:
	    System.out.println("Unable to load: " + name);
	}
	
	return ok;
    }

    private Graph readGraphson(String file)
    {
	TinkerGraph g = TinkerGraph.open();

	try
	{
	    g.io(IoCore.graphson()).readGraph(file);
	} catch (IOException e)
	{
	    System.out.println("File not found: " + file);
	    System.exit(1);
	}
	
	return g;
    }
    
    private Graph readGraphML(String file)
    {
	TinkerGraph g = TinkerGraph.open();

	try
	{
	    g.io(IoCore.graphml()).readGraph(file);
	} catch (IOException e)
	{
	    System.out.println("File not found: " + file);
	    System.exit(1);
	}
	
	return g;
    }
    
}
