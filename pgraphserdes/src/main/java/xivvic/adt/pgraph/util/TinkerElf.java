package xivvic.adt.pgraph.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class TinkerElf
{
    public static Vertex[] allVertices(Graph g)
    {
	if (g == null) { return new Vertex[0]; }
	
	Object[] nothing = {};
	Iterator<Vertex> it = g.vertices(nothing);
	
	List<Vertex> rv = new ArrayList<>(10);
	
	while (it.hasNext())
	{
	    Vertex v = it.next();
	    rv.add(v);
	}
	
	return rv.toArray(new Vertex[0]);
    }

    public static Edge[] allEdges(Graph g)
    {
	if (g == null) { return new Edge[0]; }
	
	Object[] nothing = {};
	Iterator<Edge> it = g.edges(nothing);
	
	List<Edge> rv = new ArrayList<>(10);
	
	while (it.hasNext())
	{
	    Edge e = it.next();
	    rv.add(e);
	}
	
	return rv.toArray(new Edge[0]);
    }

}
