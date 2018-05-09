package we.tinkerpop.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestBasicOperations
{
    private BasicOperations subject;

    @Before
    public void before()
    {
	subject = new BasicOperations();
    }

    @Test
    public void onCreate_BasicOperations_doesNotBlowUp()
    {
	assertNotNull(subject);
    }
    
    @Test
    public void onLoad_withRDC_returnsTrue()
    {
	boolean ok = subject.loadGraph("RDC");
	assertTrue(ok);
    }
    
    @Test
    public void onVertices_withRDC_thenReturnAllVertices() throws Exception
    {
	// Arrange
	//
	subject.loadGraph("RDC");
	
	// Act
	//
	Traversal<Vertex, Vertex> t = subject.vertices();
	List<Vertex> vs = t.toList();
	
	// Assert
	//
	assertNotNull(vs);
	assertEquals(5, vs.size());
	
	// Cleanup
	//
	t.close();
    }

    @Test
    public void onVertices_withKafka_thenReadGraphMLFile()
    {
	// Arrange
	//
	boolean ok = subject.loadGraph("KAFKA");
	
	// Assert
	//
	assertTrue(ok);
    }

    @Ignore("Unable to read any valid Graphson file")
    public void onVertices_withDeployment_thenReadGraphsonFile()
    {
	// Arrange
	//
	boolean ok = subject.loadGraph("DEPLOYMENT");
	
	// Assert
	//
	assertTrue(ok);
    }

}
