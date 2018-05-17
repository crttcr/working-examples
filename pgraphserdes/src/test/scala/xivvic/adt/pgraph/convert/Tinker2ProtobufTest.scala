package xivvic.adt.pgraph.convert

import org.junit.runner.RunWith
import xivvic.adt.pgraph.util.serde.TinkerPopSerde
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.scalatest.junit.JUnitRunner
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import xivvic.proto.adt.pgraph.PGraph
import org.apache.tinkerpop.gremlin.structure.T

@RunWith(classOf[JUnitRunner])
class Tinker2ProtobufTest
	extends FlatSpecLike
		with Matchers
{

	def fixture = new
	{
		val  tinker = TinkerGraph.open()
		val builder = PGraph.newBuilder()
		val subject = new Tinker2Protobuf(builder)
	}

	behavior of "vcf:"

	it should "produce a vertex in the builder when converting a single vertex" in
	{
		// Arrange
		//
		val  f = fixture
		val cf = f.subject.vcf
		val  v = f.tinker.addVertex(T.id, "1")

		// Act
		//
		cf(v)
		val pg = f.builder.build()

		// Assert
		//
		pg.getVCount should be (1)
	}

	behavior of "ecf:"

	it should "produce an edge in the builder when converting a single edge" in
	{
		// Arrange
		//
		val  f = fixture
		val cf = f.subject.ecf
		val  v = f.tinker.addVertex(T.id, "1")
		val  e = v.addEdge("self", v, T.id, 11.asInstanceOf[Object], "weight", 0.5f.asInstanceOf[Object]);

		// Act
		//
		cf(e)
		val pg = f.builder.build()

		// Assert
		//
		pg.getECount should be (1)
	}

}
