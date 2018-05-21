package xivvic.adt.pgraph.util

import org.junit.runner.RunWith
import xivvic.adt.pgraph.util.serde.TinkerPopSerde
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.scalatest.junit.JUnitRunner
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.apache.tinkerpop.gremlin.structure.T
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory
import org.apache.tinkerpop.gremlin.structure.Element

@RunWith(classOf[JUnitRunner])
class TinkerPopElementMatcherTest
	extends FlatSpecLike
		with Matchers
{
	behavior of "atLeastOneElementIsNull:"
	it should "return [true ] when both vertices are null" in
	{
		val m = createElementMatcher(null, null)
		m.atLeastOneElementIsNull should be (true)
	}

	it should "return [true ] when first element is null" in
	{
		val m = createElementMatcher(null, TinkerGraph.open.addVertex(T.label, "PERSON"))
		m.atLeastOneElementIsNull should be (true)
	}

	it should "return [true ] when seconde element is null" in
	{
		val m = createElementMatcher(TinkerGraph.open.addVertex(T.label, "PERSON"), null)
		m.atLeastOneElementIsNull should be (true)
	}

	it should "return [false] when both vertices are not null" in
	{
		val v = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = createElementMatcher(v, v)
		m.atLeastOneElementIsNull should be (false)
	}

	behavior of "matchGivenNullVertex   :"

	it should "return [true ] when both vertices are null" in
	{
		val m = createElementMatcher(null, null)
		val (b, s) = m.matchGivenNullElement()
		b should be (true)
	}

	it should "return [false] when only one element is null" in
	{
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m1 = createElementMatcher(v, null)
		val m2 = createElementMatcher(null, v)

		assertFalseWhenOneElementIsNull(m1.matchGivenNullElement _)
		assertFalseWhenOneElementIsNull(m2.matchGivenNullElement _)
	}

	it should "throw an exception when neither element is null (v1)" in
	{
		val v = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = createElementMatcher(v, v)

		val thrown = intercept[Exception]
		{
			m.matchGivenNullElement()
		}

		assert(thrown.getMessage.matches("Neither.*is null"))
	}

	it should "throw an exception when neither element is null (v2)" in
	{
		val v = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = createElementMatcher(v, v)

		try
		{
			val (b, s) = m.matchGivenNullElement()
			fail("Should have thrown an exception because both vertices are defined.")
		}
		catch
		{
			case e: Exception => // Expected. Do nothing.
		}
	}

	behavior of "elementLabelMatch      :"

	it should "return [true ] when both vertices are null" in
	{
		val m = createElementMatcher(null, null)
		assertTrueWhenBothElementsAreNull(m.elementLabelMatch _)
	}

	it should "return [false] when one of the vertices is null" in
	{
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m1 = new TinkerPopVertexMatcher(v, null)
		val m2 = new TinkerPopVertexMatcher(null, v)

		assertFalseWhenOneElementIsNull(m1.elementLabelMatch _)
		assertFalseWhenOneElementIsNull(m2.elementLabelMatch _)
	}

	it should "return [true ] when the vertices labels match" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = createElementMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.elementLabelMatch

		// Assert
		//
		ok should be (true)
		msg should include ("match")
	}

	it should "return [false] when the vertices labels do match" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "ROBOT")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = createElementMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.elementLabelMatch

		// Assert
		//
		ok should be (false)
		msg should include ("not")
	}

	/////////////////////////
	// Helpers             //
	/////////////////////////

	def createElementMatcher(left: Element, right: Element): TinkerPopElementMatcher = new TinkerPopElementMatcher
	{
		val a = left
		val b = right
	}

	import TinkerPopVertexMatcher.TPVMFuntion

	def assertTrueWhenBothElementsAreNull(f: TPVMFuntion): Unit =
	{
		// Act
		//
		val (ok, msg) = f()

		// Assert
		//
		ok should be (true)
		msg should include ("null")
	}

	def assertFalseWhenOneElementIsNull(f: TPVMFuntion): Unit =
	{
		// Act
		//
		val (ok, msg) = f()

		// Assert
		//
		ok should be (false)
		msg should include ("null")
	}

}
