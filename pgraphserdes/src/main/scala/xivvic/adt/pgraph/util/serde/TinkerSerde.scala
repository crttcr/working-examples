package xivvic.adt.pgraph.util.serde

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.{Vertex => TVertex}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

object TinkerSerde
{
	def serialize(g: Graph): Array[Byte] = Array()

	def deserialize(bs: Array[Byte]): Graph = TinkerGraph.open()

	/*
	def serialize(Graph g): java.lang.byte[] =
	{
		new java.lang.byte[0]
	}
*/
}

//import src.main.proto.Vertex;
//import xivvic.adt.pgraph.util.TinkerElf;

/**
 * Serde provides serialization and deserialization of property graphs from and
 * into a TinkerGraph.
public class Serde
{
    public static byte[] serialize(Graph g) throws SerdeException
    {

	Vertex[] vs = TinkerElf.allVertices(g);

	for (Vertex v : vs)
	{
	    Vertex  proto = Vertex.newBuilder().addLabel("PERSON").build();

	    src.main.proto.Vertex pv = new src.main.proto.Vertex.Builder().build();

	}
	return new byte[0];
    }

    public static TinkerGraph deserialize(byte[] bs) throws SerdeException
    {
	return TinkerGraph.open();
    }
}
 */
