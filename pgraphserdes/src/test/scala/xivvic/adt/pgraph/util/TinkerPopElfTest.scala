package xivvic.adt.pgraph.util

import org.junit.runner.RunWith
import xivvic.adt.pgraph.util.serde.TinkerSerde
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.scalatest.junit.JUnitRunner
import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.apache.tinkerpop.gremlin.structure.T

@RunWith(classOf[JUnitRunner])
class TinkerPopElfTest
	extends FlatSpecLike
		with Matchers
{
	import TinkerPopElf._

	behavior of "AllVertices"

	it should "return an empty array when given a null graph" in
	{
		// Arrange
		//
		val g: Graph = null;

		// Act
		//
		val vs = TinkerElf.allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (0)
	}

	it should "return an empty array when given an empty graph" in
	{
		// Arrange
		//
		val g = TinkerGraph.open

		// Act
		//
		val vs = TinkerElf.allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (0)
	}

	it should "return an array with one vertex when given a graph with one vertex" in
	{
		// Arrange
		//
		val g = TinkerGraph.open
		val v = g.addVertex(T.label, "service",   T.id, 1.asInstanceOf[Object], "name", "vadas", "age", 27.asInstanceOf[Object]);

		// Act
		//
		val vs = TinkerElf.allVertices(g)

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
		val g: Graph = null;

		// Act
		//
		val es = TinkerElf.allEdges(g)

		// Assert
		//
		es should not be (null)
		es.length should be (0)
	}

	it should "return an empty array when given an empty graph" in
	{
		// Arrange
		//
		val g = TinkerGraph.open

		// Act
		//
		val es = TinkerElf.allEdges(g)

		// Assert
		//
		es should not be (null)
		es.length should be (0)
	}

	it should "return an array with one edge when given a graph with one edge" in
	{
		// Arrange
		//
		val g = TinkerGraph.open
		val v = g.addVertex(T.label, "service",   T.id, 1.asInstanceOf[Object], "name", "vadas", "age", 27.asInstanceOf[Object])
		v.addEdge("reads", v, T.id, 11.asInstanceOf[Object], "weight", 0.5f.asInstanceOf[Object]);

		// Act
		//
		val vs = TinkerElf.allEdges(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (1)
	}

}
