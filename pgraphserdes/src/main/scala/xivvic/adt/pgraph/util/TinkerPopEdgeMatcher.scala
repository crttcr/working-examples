package xivvic.adt.pgraph.util

import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.Edge
import org.apache.tinkerpop.gremlin.structure.Property

object TinkerPopEdgeMatcher
{
	type TPEMFuntion = () => (Boolean, String)
}

class TinkerPopEdgeMatcher(val a: Edge, val b: Edge)
	extends TinkerPopElementMatcher
{

	def edgeMatch(): (Boolean, String) =
	{
		if (atLeastOneElementIsNull) return matchGivenNullElement()

		val (label_match, reason)      = elementLabelMatch
		val (property_match, reasonxx) = edgePropertyMatch
		if (! label_match)    return (false, reason)
		if (! property_match) return (false, reasonxx)

		(true, "labels and properties match");
	}

	def edgePropertyMatch: (Boolean, String) =
	{
		if (atLeastOneElementIsNull) return matchGivenNullElement()

		val ma = getPropertyMap(a)
		val mb = getPropertyMap(b)

		if (ma.size != mb.size)
		{
			return (false, s"Edge property mismatch: [$ma.size] vs [$mb.size]")
		}

		val ka = ma.keySet
		val kb = mb.keySet

		if (ka != kb)
		{
			Console.err.println("Edge property key mismatch")
			Console.err.println("a: " + ka)
			Console.err.println("b: " + kb)

			return (false, s"Edge property mismatch: keys are not the same")
		}

		ka.foreach
		{
			k =>
			{
				val list_a = ma.getOrElse(k, List())
				val list_b = mb.getOrElse(k, List())

				if (list_a.size != list_b.size)
					return (false, s"Edge property mismatch: key [$k] has different number of values")

				val zip = list_a zip list_b

				zip.foreach
				{
					pair =>
					{
						if (pair._1.toString != pair._2.toString)
							return (false, s"Edge property mismatch: key [$k] values [$pair._1, $pair._2]")
					}
				}

			}
		}

		(true, "Properties match");
	}

	/////////////////////////
	// Helpers             //
	/////////////////////////

	private def getPropertyMap(v: Edge): Map[String, List[Object]] =
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
