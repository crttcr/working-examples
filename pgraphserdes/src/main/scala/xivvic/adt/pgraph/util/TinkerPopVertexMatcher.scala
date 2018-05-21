package xivvic.adt.pgraph.util

import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty

object TinkerPopVertexMatcher
{
	type TPVMFuntion = () => (Boolean, String)
}

class TinkerPopVertexMatcher(a: Vertex, b: Vertex)
{
	def atLeastOneVertexIsNull = a == null || b == null

	def matchGivenNullVertex(): (Boolean, String) =
	{
		if (a == null && b == null) return (true, "Both vertices are null");
		if (a == null || b == null) return (false, "One vertex is null, the other is not");

		throw new IllegalArgumentException(s"Neither of the two vertices ($a, $b) is null");
	}

	def vertexMatch(): (Boolean, String) =
	{
		if (atLeastOneVertexIsNull) return matchGivenNullVertex()
		
		val (label_match, reason)      = vertexLabelMatch
		val (property_match, reasonxx) = vertexPropertyMatch
		if (! label_match)    return (false, reason)
		if (! property_match) return (false, reasonxx)

		(true, "labels and properties match");
	}

	def vertexLabelMatch: (Boolean, String) =
	{
		if (atLeastOneVertexIsNull) return matchGivenNullVertex()

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

	def vertexPropertyMatch: (Boolean, String) =
	{
		if (atLeastOneVertexIsNull) return matchGivenNullVertex()

		val ma = getPropertyMap(a)
		val mb = getPropertyMap(b)

		if (ma.size != mb.size)
		{
			return (false, s"Vertex property mismatch: [$ma.size] vs [$mb.size]")
		}

		val ka = ma.keySet
		val kb = mb.keySet

		if (ka != kb)
		{
			Console.err.println("Vertex property key mismatch")
			Console.err.println("a: " + ka)
			Console.err.println("b: " + kb)

			return (false, s"Vertex property mismatch: keys are not the same")
		}

		ka.foreach
		{
			k =>
			{
				val list_a = ma.getOrElse(k, List())
				val list_b = mb.getOrElse(k, List())

				if (list_a.size != list_b.size)
					return (false, s"Vertex property mismatch: key [$k] has different number of values")

				val zip = list_a zip list_b

				zip.foreach
				{
					pair =>
					{
						if (pair._1.toString != pair._2.toString)
							return (false, s"Vertex property mismatch: key [$k] values [$pair._1, $pair._2]")
					}
				}

			}
		}

		(true, "Properties match");
	}

	/////////////////////////
	// Helpers             //
	/////////////////////////

	private def getPropertyMap(v: Vertex): Map[String, List[Object]] =
	{
		val rv = scala.collection.mutable.Map.empty[String, List[Object]]
		val it = v.properties();

		while (it.hasNext())
		{
			val p = it.next()
			val k = p.key()
			val v = p.value().asInstanceOf[Object]
			val l = rv.getOrElse(k, List.empty[Object])

			rv(k) = v :: l
		}

		rv.toMap
	}

}
