package xivvic.adt.pgraph.util.serde

import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.JUnitRunner
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.T

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

	////////////////////////////
	// Helpers //
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

	def assertGraphsMatch(a: Graph, b: Graph): Unit =
	{
		if (a == null && b == null) return;
		if (a == null || b == null) fail("One graph is null, the other is not");
	}
	/*

	Vertex[] va = TinkerElf.allVertices(a);
	Vertex[] vb = TinkerElf.allVertices(b);
	Edge[]   ea = TinkerElf.allEdges(a);
	Edge[]   eb = TinkerElf.allEdges(b);

	assertVertexCardinality(va, vb);
	assertEdgeCardinality(ea, eb);

	// FIXME: Need to test structure and content of graph, not just cardinality
	//
	}

	private void assertVertexCardinality(Vertex[] a, Vertex[] b)
	{
	if (a == null && b == null) return;
	if (a == null || b == null) fail("One array is null, the other is not");

	if (a.length != b.length)
	{
		String fmt = "The graphs have different number of vertices: (a = %d, b = %d)";
		String msg = String.format(fmt, a.length, b.length);
		fail(msg);
	}
	}

	private void assertEdgeCardinality(Edge[] a, Edge[] b)
	{
	if (a == null && b == null) return;
	if (a == null || b == null) fail("One array is null, the other is not");

	if (a.length != b.length)
	{
		String fmt = "The graphs have different number of edges: (a = %d, b = %d)";
		String msg = String.format(fmt, a.length, b.length);
		fail(msg);
	}
	}

	public Graph sampleGraph(int v, int e)
	{

	Graph rv = TinkerGraph.open();

	for (int i = 0; i < v; i++)
	{
		String label = Integer.toString(i);
		rv.addVertex(label);
	}

	return rv;
	}
*/
}
