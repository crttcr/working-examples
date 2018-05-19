package xivvic.adt.pgraph.util

import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.Vertex

object TinkerPopVertexMatcher
{
	type TPVMFuntion = () => (Boolean, String)
}

class TinkerPopVertexMatcher(a: Vertex, b: Vertex)
{

	def vertexMatch(): (Boolean, String) =
	{
		if (a == null && b == null) return (true, "Both vertices are null");
		if (a == null || b == null) return (false, "One vertex is null, the other is not");

		val (label_match, reason)      = vertexLabelMatch
		val (property_match, reasonxx) = vertexPropertyMatch
		val (edge_match, reasonyy)     = vertexEdgeMatch

		if (! label_match)    return (false, reason)
//		if (! property_match) return (false, reasonxx)
//		if (! edge_match)     return (false, reasonyy)

		(true, "labels, properties, and edges match");
	}

	def vertexLabelMatch: (Boolean, String) =
	{
		if (a == null && b == null) return (true, "Both vertices are null");
		if (a == null || b == null) return (false, "One vertex is null, the other is not");

		val a_label = a.label()
		val b_label = b.label()

		if (a_label == null && b_label == null) return (true, "Both labels are null");
		if (a_label == null || b_label == null) return (false, "One label is null, the other is not");

		if (a_label == b_label)
		{
			(true, "Labels match");
		}
		else
		{
			(false, s"Label [$a_label] is not the same as [$b_label]");
		}
	}

	private def vertexPropertyMatch: (Boolean, String) =
	{
		(false, "Not yet implemented");
	}

	private def vertexEdgeMatch: (Boolean, String) =
	{
		(false, "Not yet implemented");
	}

}
