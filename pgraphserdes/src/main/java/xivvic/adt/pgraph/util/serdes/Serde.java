package xivvic.adt.pgraph.util.serdes;

import org.apache.tinkerpop.gremlin.structure.Graph;
//import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

//import src.main.proto.Vertex;
//import xivvic.adt.pgraph.util.TinkerElf;

/**
 * Serde provides serialization and deserialization of property graphs from and
 * into a TinkerGraph.
 */
public class Serde
{
    public static byte[] serialize(Graph g) throws SerdeException
    {
/*	
	Vertex[] vs = TinkerElf.allVertices(g);
	
	for (Vertex v : vs)
	{
	    Vertex  proto = Vertex.newBuilder().addLabel("PERSON").build();

	    src.main.proto.Vertex pv = new src.main.proto.Vertex.Builder().build();
		 
	    
	}
*/
	return new byte[0];
    }

    public static TinkerGraph deserialize(byte[] bs) throws SerdeException
    {
	return TinkerGraph.open();
    }
}
