package xivvic.adt.pgraph.util.serde

import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.{Vertex => TVertex}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import xivvic.adt.pgraph.util.TinkerPopElf
import org.apache.tinkerpop.gremlin.structure.{Vertex => TVertex}
import xivvic.proto.adt.pgraph.PGraph.Builder
import xivvic.proto.adt.pgraph.PGraph
import xivvic.proto.adt.pgraph.{Vertex => PVertex}
import xivvic.proto.adt.pgraph.{Edge => PEdge}
import org.apache.tinkerpop.gremlin.structure.T
import xivvic.adt.pgraph.convert.Tinker2Protobuf
import xivvic.adt.pgraph.convert.Protobuf2Tinker
import xivvic.adt.pgraph.util.PGProtoElf

/**
 * TinkerPopSerde converts a TinkerGraph instance to a serialized form
 * and a serialized graph back to a TinkerGraph.
 */
object TinkerPopSerde
{
	def serialize(g: Graph): Array[Byte] =
	{
		val vs = TinkerPopElf.allVertices(g)
		val es = TinkerPopElf.allEdges(g)

		val   builder = PGraph.newBuilder()
		val converter = new Tinker2Protobuf(builder)

		def vcf = converter.vcf()
		def ecf = converter.ecf()

		vs.foreach(vcf)
		es.foreach(ecf)

		val  pg = builder.build()
		val ser = pg.toByteArray()

//		println("SERIALIALIZED FORM---------------------------------------------------")
//		println(pg)
//		println("SERIALIALIZED FORM COMPLETE------------------------------------------")

		ser
	}

	def deserialize(bs: Array[Byte]): Graph =
	{
		val rv = TinkerGraph.open()
		if (bs == null || bs.length == 0) return rv

	   val pg = PGraph.parseFrom(bs);
		val vs = PGProtoElf.allVertices(pg)
		val es = PGProtoElf.allEdges(pg)

		val converter = new Protobuf2Tinker(rv)
		def       vcf = converter.vcf()
		def       ecf = converter.ecf()

		vs.foreach(vcf)
		es.foreach(ecf)

		rv
	}
}
