package xivvic.adt.pgraph.util

import org.junit.runner.RunWith
import xivvic.adt.pgraph.util.serde.TinkerPopSerde
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

	behavior of "AllVertices: "

	it should "return an empty array when given a null graph" in
	{
		// Arrange
		//
		val g: Graph = null;

		// Act
		//
		val vs = TinkerPopElf.allVertices(g)

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
		val vs = TinkerPopElf.allVertices(g)

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
		val vs = TinkerPopElf.allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (1)
	}

	it should "return an array with three vertices when given a graph with three vertices" in
	{
		// Arrange
		//
		val  g = TinkerGraph.open
		val v1 = g.addVertex(T.label, "service",      T.id, 1.asInstanceOf[Object], "name", "RDC", "topic", "strategic_data_in");
		val v2 = g.addVertex(T.label, "server",       T.id, 2.asInstanceOf[Object], "IP", "10.0.1.100");
		val v3 = g.addVertex(T.label, "kafka_broker", T.id, 3.asInstanceOf[Object], "url", "kafka.dev.uptake.com");

		// Act
		//
		val vs = TinkerPopElf.allVertices(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (3)
	}

	behavior of "AllEdges   :"

	it should "return an empty array when given a null graph" in
	{
		// Arrange
		//
		val g: Graph = null;

		// Act
		//
		val es = TinkerPopElf.allEdges(g)

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
		val es = TinkerPopElf.allEdges(g)

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
		val vs = TinkerPopElf.allEdges(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (1)
	}

	it should "return an array with three edge when given a graph with three edges" in
	{
		// Arrange
		//
		val  g = TinkerGraph.open
		val v1 = g.addVertex(T.label, "service",      T.id, 1.asInstanceOf[Object], "name", "RDC", "topic", "strategic_data_in");
		val v2 = g.addVertex(T.label, "server",       T.id, 2.asInstanceOf[Object], "IP", "10.0.1.100");
		v1.addEdge("self",     v1, T.id, 11.asInstanceOf[Object], "weight", 0.5f.asInstanceOf[Object]);
		v1.addEdge("RUNS_ON",  v2, T.id, 12.asInstanceOf[Object]);
		v2.addEdge("HOST_FOR", v1, T.id, 13.asInstanceOf[Object]);

		// Act
		//
		val vs = TinkerPopElf.allEdges(g)

		// Assert
		//
		vs should not be (null)
		vs.length should be (3)
	}

}
