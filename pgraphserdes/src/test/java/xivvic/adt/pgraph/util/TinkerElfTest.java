package xivvic.adt.pgraph.util;

import static org.junit.Assert.*;

import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.Test;

public class TinkerElfTest
{
    @Test
    public void onAllVertices_withNullGraph_thenReturnEmptyArray()
    {
	// Arrange
	//
	Graph g = null;
	
	// Act
	//
	Vertex[] vs = TinkerElf.allVertices(g);
	
	// Assert
	//
	assertNotNull(vs);
	assertEquals(0, vs.length);
    }

    @Test
    public void onAllEdges_withNullGraph_thenReturnEmptyArray()
    {
	// Arrange
	//
	Graph g = null;
	
	// Act
	//
	Edge[] vs = TinkerElf.allEdges(g);
	
	// Assert
	//
	assertNotNull(vs);
	assertEquals(0, vs.length);
    }

}
