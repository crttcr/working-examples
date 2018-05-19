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

@RunWith(classOf[JUnitRunner])
class TinkerPopVertexMatcherTest
	extends FlatSpecLike
		with Matchers
{
	import TinkerPopVertexMatcher._

	behavior of "vertexMatch:"

	it should "return true when both vertices are null" in
	{
		val m = new TinkerPopVertexMatcher(null, null)
		assertTrueWhenBothVerticesAreNull(m.vertexMatch _)
	}

	it should "return false when one of the vertices is null" in
	{
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = new TinkerPopVertexMatcher(v, null)

		assertFalseWhenOneVertexIsNull(m.vertexMatch _)
	}

	behavior of "vertexLabelMatch:"

	it should "return true when both vertices are null" in
	{
		val m = new TinkerPopVertexMatcher(null, null)
		assertTrueWhenBothVerticesAreNull(m.vertexLabelMatch _)
	}

	it should "return false when one of the vertices is null" in
	{
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = new TinkerPopVertexMatcher(v, null)

		assertFalseWhenOneVertexIsNull(m.vertexLabelMatch _)
	}

	it should "return [true] when the vertices labels match" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = new TinkerPopVertexMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexLabelMatch

		// Assert
		//
		ok should be (true)
		msg should include ("match")
	}

	it should "return [false]] when the vertices labels do match" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "ROBOT")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = new TinkerPopVertexMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexLabelMatch

		// Assert
		//
		ok should be (false)
		msg should include ("not")
	}

	/////////////////////////
	// Helpers             //
	/////////////////////////

	import TinkerPopVertexMatcher.TPVMFuntion

	def assertTrueWhenBothVerticesAreNull(f: TPVMFuntion): Unit =
	{
		// Act
		//
		val (ok, msg) = f()

		// Assert
		//
		ok should be (true)
		msg should include ("null")
	}

	def assertFalseWhenOneVertexIsNull(f: TPVMFuntion): Unit =
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
