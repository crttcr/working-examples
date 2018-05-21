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
				val oa = ma.get(k)
				val ob = mb.get(k)

				(oa, ob) match
				{
					case (Some(va), Some(vb)) if va.equals(vb) => // match, do nothing
					case (None, None)                          => // match, do nothing
					case _                                     => return (false, "Properties do not match")
				}

			}
		}

		(true, "Properties match");
	}

	/////////////////////////
	// Helpers             //
	/////////////////////////

	private def getPropertyMap(v: Edge): Map[String, Object] =
	{
		val rv = scala.collection.mutable.Map.empty[String, Object]
		val it = v.properties();

		while (it.hasNext())
		{
			val p = it.next()
			val k = p.key()
			val v = p.value().asInstanceOf[Object]

			rv(k) = v
		}

		rv.toMap
	}

}
