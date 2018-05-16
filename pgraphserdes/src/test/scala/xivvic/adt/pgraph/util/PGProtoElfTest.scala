package xivvic.adt.pgraph.util

import org.junit.runner.RunWith
import xivvic.adt.pgraph.util.serde.TinkerPopSerde
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.scalatest.junit.JUnitRunner
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.apache.tinkerpop.gremlin.structure.T
import xivvic.proto.adt.pgraph.PGraph
import xivvic.proto.adt.pgraph.Vertex
import xivvic.proto.adt.pgraph.Edge

@RunWith(classOf[JUnitRunner])
class PGProtoElfTest
	extends FlatSpecLike
		with Matchers
{
	import PGProtoElf._

	behavior of "AllVertices"

	it should "return an empty array when given a null graph" in
	{
		// Arrange
		//
		val g: PGraph = null;

		// Act
		//
		val vs = allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (0)
	}

	it should "return an empty array when given an empty graph" in
	{
		// Arrange
		//
		val g = PGraph.newBuilder().build()

		// Act
		//
		val vs = allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (0)
	}

	it should "return an array with one vertex when given a graph with one vertex" in
	{
		// Arrange
		//
		val v = Vertex.newBuilder().addLabel("SERVICE").build();

		val g = PGraph.newBuilder().addV(v).build()
//		val v2 = g.addVertex(T.label, "service",   T.id, 1.asInstanceOf[Object], "name", "vadas", "age", 27.asInstanceOf[Object]);

		// Act
		//
		val vs = allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (1)
	}

	behavior of "AllEdges"

	it should "return an empty array when given a null graph" in
	{
		// Arrange
		//
		val g: PGraph = null;

		// Act
		//
		val es = allEdges(g)

		// Assert
		//
		es should not be (null)
		es.length should be (0)
	}

	it should "return an empty array when given an empty graph" in
	{
		// Arrange
		//
		val g = PGraph.newBuilder().build()

		// Act
		//
		val es = allEdges(g)

		// Assert
		//
		es should not be (null)
		es.length should be (0)
	}

	it should "return an array with one edge when given a graph with one edge" in
	{
		// Arrange
		//
		val v = Vertex.newBuilder().addLabel("SERVICE").setId("svc").build();
		val e = Edge.newBuilder().setId("edge.1").setFrom("svc").setTo("svc").setRelType("REFLEXIVE").build();
		val g = PGraph.newBuilder().addV(v).addE(e).build()

		// Act
		//
		val vs = allEdges(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (1)
	}

}
