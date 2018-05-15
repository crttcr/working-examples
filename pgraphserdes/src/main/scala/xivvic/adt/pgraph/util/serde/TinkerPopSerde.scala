package xivvic.adt.pgraph.util.serde

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.{Vertex => TVertex}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import xivvic.adt.pgraph.util.TinkerPopElf
import org.apache.tinkerpop.gremlin.structure.{Vertex => TVertex}
import src.main.proto.PGraph.Builder
import src.main.proto.PGraph
import src.main.proto.{Vertex => PVertex}
import src.main.proto.{Edge => PEdge}
import org.apache.tinkerpop.gremlin.structure.T

/**
 * TinkerPopSerde converts a TinkerGraph instance to a serialized form
 * and a serialized graph back to a TinkerGraph.
 */
object TinkerPopSerde
{
	def serialize(g: Graph): Array[Byte] =
	{
		val builder: Builder = PGraph.newBuilder()

		// Note: It's possible to create a vertex prototype and reuse that
		//
		// 	    Vertex  proto = Vertex.newBuilder().addLabel("PERSON").build();
		//        src.main.proto.Vertex pv = new src.main.proto.Vertex.Builder().build();

		def cf(v: TVertex): Unit =
		{
			val label = v.label()
			val pv = PVertex.newBuilder().addLabel(label).build()
			builder.addV(pv)
		}

		val vs = TinkerPopElf.allVertices(g)

		vs.foreach(cf)

		val  pg = builder.build()
		val ser = pg.toByteArray()

		ser
	}

	def deserialize(bs: Array[Byte]): Graph =
	{
		val rv = TinkerGraph.open()

		if (bs == null || bs.length == 0) return rv

	   val pg = PGraph.parseFrom(bs);
		val vs = pg.getVList

		vs.forEach {
			pv =>
			val label = pv.getLabel(0)
			val    tv = rv.addVertex(T.label, label)
		}

		rv
	}

}
