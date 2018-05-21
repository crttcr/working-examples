package xivvic.adt.pgraph.util

import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.Element
import org.apache.tinkerpop.gremlin.structure.Property

trait TinkerPopElementMatcher
{
	val a: Element
	val b: Element

	def atLeastOneElementIsNull = a == null || b == null

	def matchGivenNullElement(): (Boolean, String) =
	{
		if (a == null && b == null) return (true, "Both elements	 are null");
		if (a == null || b == null) return (false, "One vertex is null, the other is not");

		throw new IllegalArgumentException(s"Neither of the two edges ($a, $b) is null");
	}

	def elementLabelMatch: (Boolean, String) =
	{
		if (atLeastOneElementIsNull) return matchGivenNullElement()

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

}
