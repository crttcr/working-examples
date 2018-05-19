package xivvic.adt.pgraph.util.serde

import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.JUnitRunner
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.T
import xivvic.adt.pgraph.util.TinkerPopElf
import org.apache.tinkerpop.gremlin.structure.Vertex
import xivvic.adt.pgraph.util.TinkerPopGraphMatcher
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory
import xivvic.adt.pgraph.util.TinkerPopVertexMatcher

@RunWith(classOf[JUnitRunner])
class TinkerSerdeRoundTripTest
	extends FlatSpecLike
		with Matchers
{
	import TinkerPopSerde._

	behavior of "Deserialize(Serialize(g))"

	it should "produce a matching result for a graph consisting of a single vertex" in
	{
		// Arrange
		//
		val input: Graph = sampleGraph(1, 0)

		// Act
		//
		val ser = serialize(input)
		val result = deserialize(ser);

		// Assert
		//
		ser should not be (null)
		result should not be (null)
		assertGraphsMatch(input, result);
	}

	it should "produce a matching result for the modern example" in
	{
		// Arrange
		//
		val input: Graph = TinkerFactory.createModern()

		// Act
		//
		val ser = serialize(input)
		val result = deserialize(ser);

		// Assert
		//
		ser should not be (null)
		result should not be (null)
		assertGraphsMatch(input, result);
	}

	////////////////////////////
	// Helpers                //
	////////////////////////////

	def sampleGraph(v: Int, e: Int): Graph =
	{
		val rv = TinkerGraph.open
		for( i <- 0 until v)
		{
			val label = i.toString
			val     v = rv.addVertex(T.label, label)
		}

		rv
	}

	// FIXME: Need to test structure and content of graph, not just cardinality
	//
	def assertGraphsMatch(a: Graph, b: Graph): Unit =
	{
		val matcher = new TinkerPopGraphMatcher(a, b)

		val (ok_v, msg_v) = matcher.vertexCardinalityMatch()
		val (ok_e, msg_e) = matcher.edgeCardinalityMatch()

		if (! ok_v) fail(msg_v)
		if (! ok_e) fail(msg_e)

		val a_vertices = a.traversal().V().toList()
		val b_vertices = b.traversal().V().toList()

		val vmap = getVertexMap(b_vertices)

		val it = a_vertices.iterator()
		while (it.hasNext())
		{
			val v = it.next()
			val x = vmap.getOrElse(v.id().toString(), null)
			val m = new TinkerPopVertexMatcher(v, x)

			val (ok, msg) = m.vertexMatch()
			if (! ok) fail(msg)
		}

	}

	private def getVertexMap(vs: java.util.List[Vertex]): Map[String, Vertex] =
	{
		val rv = scala.collection.mutable.Map.empty[String, Vertex]
		val it = vs.iterator()
		while (it.hasNext())
		{
			val  v = it.next()
			val id = v.id().toString()

			rv(id) = v
		}

		rv.toMap
	}

}
