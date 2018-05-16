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
import xivvic.proto.adt.pgraph.Property

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
		val t = Property.Type.STRING
		val p = Property.newBuilder().setName("age").setType(t).setValue("27").build()
		val v = Vertex.newBuilder().addLabel("SERVICE").setId("001").addP(p).build();
		val g = PGraph.newBuilder().addV(v).build()

		// Act
		//
		val vs = allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (1)
	}

	it should "return an array with multiple vertices when given a graph with three vertices" in
	{
		// Arrange
		//
		val  t = Property.Type.STRING
		val  p = Property.newBuilder().setName("age").setType(t).setValue("27").build()
		val v1 = Vertex.newBuilder().addLabel("SERVICE").setId("001").addP(p).build();
		val v2 = Vertex.newBuilder().addLabel("SERVICE").setId("002").build();
		val v3 = Vertex.newBuilder().addLabel("SERVICE").setId("003").build();
		val  g = PGraph.newBuilder().addV(v1).addV(v2).addV(v3).build()

		// Act
		//
		val vs = allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (3)
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
		val t = Property.Type.FLOAT
		val p = Property.newBuilder().setName("weight").setType(t).setValue("0.5").build()
		val v = Vertex.newBuilder().addLabel("SERVICE").setId("svc").build();
		val e = Edge.newBuilder().setId("edge.1").setFrom("svc").setTo("svc").setRelType("REFLEXIVE").addP(p).build();
		val g = PGraph.newBuilder().addV(v).addE(e).build()

		// Act
		//
		val vs = allEdges(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (1)
	}

	it should "return an array with three edges when given a graph with three edges" in
	{
		// Arrange
		//
		val  t = Property.Type.FLOAT
		val  p = Property.newBuilder().setName("weight").setType(t).setValue("0.5").build()
		val v1 = Vertex.newBuilder().addLabel("SERVICE").setId("svc").build();
		val v2 = Vertex.newBuilder().addLabel("SERVICE").setId("server").build();
		val e1 = Edge.newBuilder().setId("edge.1").setFrom("svc").setTo("svc").setRelType("REFLEXIVE").addP(p).build();
		val e2 = Edge.newBuilder().setId("edge.2").setFrom("svc").setTo("server").setRelType("RUNS_ON").build();
		val e3 = Edge.newBuilder().setId("edge.3").setFrom("server").setTo("svc").setRelType("HOST_FOR").build();
		val  g = PGraph.newBuilder().addV(v1).addV(v2).addE(e1).addE(e2).addE(e3).build()

		// Act
		//
		val vs = allEdges(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (3)
	}

}
