package we.tinkerpop.basic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TinkerGraphExamples
{
    private  TinkerGraph subject;

    @Before
    public void before()
    {
	subject = TinkerGraph.open();
    }

    @After
    public void after()
    {
	// NOTE: The documentation states that this only has an effect when location is set
	// in which case it causes the current state of the graph to be flushed to the location.
	// It can be called multiple times.
	//
	subject.close();
    }

    @Test
    public void ExCreateAVertexWithOnlyALabel()
    {
	// Arrange
	//
	final String l ="A Label";

	// Act
	//
	final Vertex v = subject.addVertex(l);
	final Vertex u = subject.traversal().V().next();
	final String s = v.label();

	// Assert
	//
	assertEquals(v, u);
	assertEquals(s, l);
    }

    /*
     * NOTE: Properties are case sensitive!
     */

    @Test
    public void ExCreateAVertexWithProperties()
    {
	// Arrange
	//
	final String l ="A Label";
	final String n = "Name";
	final Map<String, Object> pmap = new HashMap<>();

	// Act
	//
	final Vertex v = subject.addVertex(T.label, l, n, "Aloysius", "ppg", 21.0);
	final Vertex u = subject.traversal().V().next();

	final VertexProperty<Object> np = u.property(n);
	final Iterator<VertexProperty<Object>> it = u.properties();

	while (it.hasNext())
	{
	    final VertexProperty<Object> p = it.next();
	    final String k = p.key();
	    final Object o = p.value();

	    // System.out.println(k + " :: " + o);
	    pmap.put(k, o);
	}

	// Assert
	//
	assertEquals(v, u);
	assertEquals(l, v.label());
	assertEquals("Aloysius", pmap.get(n));
	assertEquals("Aloysius", np.value());
    }

    @Test
    public void ExCreateAVertexWithRepeatedProperties()
    {
	// Arrange
	//
	final String l ="A Label";
	final String n = "Name";
	final Map<String, Object> pmap = new HashMap<>();

	// Act
	//
	final Vertex v = subject.addVertex(T.label, l, n, "Patrick", n, "Aloysius", n, "Ewing", "ppg", 21.0);
	final Vertex u = subject.traversal().V().next();

	final Iterator<VertexProperty<Object>> ps = u.properties(n);
	final Iterator<VertexProperty<Object>> it = u.properties();

	while (it.hasNext())
	{
	    final VertexProperty<Object> p = it.next();
	    final String k = p.key();
	    final Object o = p.value();

//	    System.out.println(k + " :: " + o);
//	    System.out.println(pmap);
	    pmap.put(k, o);
	}

	while (ps.hasNext())
	{
	    final VertexProperty<Object> p = ps.next();
	    p.key();
	    p.value();

//	    System.out.println(k + " :: " + o);
	}

	// Assert
	//
	assertEquals(v, u);
	assertEquals(l, v.label());
	assertEquals("Ewing", pmap.get(n));  // Properties seem to maintain order under a single key.
    }

    @Test
    public void ExAddPropertiesToAVertexAfterCreation()
    {
	// Arrange
	//
	final String l ="A Label";
	final String n = "Name";
	final Map<String, Object> pmap = new HashMap<>();

	// Act
	//
	final Vertex v = subject.addVertex(T.label, l, "ppg", 21.0);
	final Vertex u = subject.traversal().V().next();
	u.property(n, "Patrick");
	u.property(n, "Aloysius");
	u.property(n, "Ewing");

	u.properties(n);
	final Iterator<VertexProperty<Object>> it = u.properties();

	while (it.hasNext())
	{
	    final VertexProperty<Object> p = it.next();
	    final String k = p.key();
	    final Object o = p.value();
	    pmap.put(k, o);
	}

	/*
	while (ps.hasNext())
	{
	    final VertexProperty<Object> p = ps.next();
	    final String k = p.key();
	    final Object o = p.value();

	    System.out.println(k + " :: " + o);
	}
	*/

	// Assert
	//
	assertEquals(v, u);
	assertEquals(l, v.label());
	assertEquals("Ewing", pmap.get(n));  // Properties seem to maintain order under a single key.
    }


    ////////////////////////
    // Edges              //
    ////////////////////////

    @Test
    public void ExCreateAnEdgeWithOnlyARelationshipType()
    {
	// Arrange
	//
	final String l = "A Label";
	final String r = "KNOWS";

	// Act
	//
	final Vertex a = subject.addVertex(l);
	final Vertex b = subject.addVertex(l);
	final Edge   e = a.addEdge(r, b);
	final Edge   f = subject.traversal().E().next();

	// Assert
	//
	assertEquals(e, f);
	assertEquals(r, e.label());
    }

    /*
     * NOTE: Properties are case sensitive!
     */

    @Test
    public void ExCreateAnEdgeWithProperties()
    {
	// Arrange
	//
	final String l ="A Label";
	final String n = "Name";
	final String r = "KNOWS";

	final Vertex a = subject.addVertex(l);
	final Vertex b = subject.addVertex(l);

	final Map<String, Object> pmap = new HashMap<>();

	// Act
	//
	final Edge   e = a.addEdge(r, b, "weight", 0.5F, "color", "BLUE");
	final Edge   f = subject.traversal().E().next();

	f.property(n);
	final Iterator<Property<Object>> it = f.properties();

	while (it.hasNext())
	{
	    final Property<Object> p = it.next();
	    final String k = p.key();
	    final Object o = p.value();

	    // final String msg = String.format("%10s :: %s", k, o);
	    // System.out.println(msg);

	    pmap.put(k, o);
	}

	// Assert
	//
	assertEquals(e, f);
	assertEquals(r, e.label());
	assertEquals("BLUE", pmap.get("color"));
    }

    // NOTE: Edge properties are not repeatable. There is only a single property with a given name.
    //
    @Test
    public void ExCreateAnEdgeWithRepeatedProperties()
    {
	// Arrange
	//
	final String l ="A Label";
	final String r = "KNOWS";

	final Vertex a = subject.addVertex(l);
	final Vertex b = subject.addVertex(l);

	final Map<String, Object> pmap = new HashMap<>();
	final List<Object>       plist = new ArrayList<>();

	// Act
	//
	final Edge   e = a.addEdge(r, b, "weight", 0.5F, "used", "yesterday", "used", "today", "used", 10);
	final Edge   f = subject.traversal().E().next();

	final Iterator<Property<Object>> it = f.properties();

	while (it.hasNext())
	{
	    final Property<Object> p = it.next();
	    final String k = p.key();
	    final Object o = p.value();

//	    final String msg = String.format("%10s :: %s", k, o);
//	    System.out.println(msg);

	    plist.add(o);
	    pmap.put(k, o);
	}

	// Assert
	//
	assertEquals(e, f);
	assertEquals(2, plist.size());  // weight + used.  Label is not considered a property for an edge.
	assertEquals(r, e.label());
	assertEquals(10, pmap.get("used"));
    }

    @Test
    public void ExAddPropertiesToAnEdgeAfterCreation()
    {
	// Arrange
	//
	final String l ="A Label";
	final String r = "KNOWS";

	final Vertex a = subject.addVertex(l);
	final Vertex b = subject.addVertex(l);

	final Map<String, Object> pmap = new HashMap<>();
	final List<Object>       plist = new ArrayList<>();

	// Act
	//
	final Edge   e = a.addEdge(r, b);
	final Edge   f = subject.traversal().E().next();
	e.property("first", "Patrick");
	e.property("middle", "Aloysius");
	e.property("last", "Ewing");

	final Iterator<Property<Object>> it = e.properties();

	while (it.hasNext())
	{
	    final Property<Object> p = it.next();
	    final String k = p.key();
	    final Object o = p.value();
	    pmap.put(k, o);
	    plist.add(p);
	}

	// Assert
	//
	assertEquals(e, f);
	assertEquals(r, e.label());
	assertEquals("Patrick", pmap.get("first"));
	assertEquals("Ewing", pmap.get("last"));
    }

}
