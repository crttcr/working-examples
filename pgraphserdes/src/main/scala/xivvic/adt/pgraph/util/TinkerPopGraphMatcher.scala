package xivvic.adt.pgraph.util

import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.Vertex

class TinkerPopGraphMatcher(a: Graph, b: Graph)
{
	def vertexCardinalityMatch(): (Boolean, String) =
	{
		if (a == null && b == null) return (true, "Both graphs are null");
		if (a == null || b == null) return (false, "One graph is null, the other is not");

		val va = TinkerPopElf.allVertices(a)
		val vb = TinkerPopElf.allVertices(b)

		if (va.length != vb.length)
		{
			val msg = f"Graph: mismatch vertex cardinality: (|a| = ${va.length}%d, |b| = ${vb.length}%d)";
			return (false, msg)
		}

		val msg = f"Graph: vertex cardinality matches: (|a| = ${va.length}%d, |b| = ${vb.length}%d)";
		return (true, msg)
	}

	def edgeCardinalityMatch(): (Boolean, String) =
	{
		if (a == null && b == null) return (true,  "Both graphs are null");
		if (a == null || b == null) return (false, "One graph is null, the other is not");

		val ea = TinkerPopElf.allEdges(a)
		val eb = TinkerPopElf.allEdges(b)

		if (ea.length != eb.length)
		{
			val msg = f"Graph: mismatch edge cardinality: (|a| = ${ea.length}%d, |b| = ${eb.length}%d)";
			return (false, msg)
		}

		val msg = f"Graph: edge cardinality matches: (|a| = ${ea.length}%d, |b| = ${eb.length}%d)";
		return (true, msg)
	}

}
