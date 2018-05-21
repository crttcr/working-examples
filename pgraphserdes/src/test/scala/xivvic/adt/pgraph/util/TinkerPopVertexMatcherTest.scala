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

	behavior of "vertexMatch           :"

	it should "return [true ] when both vertices are null" in
	{
		val m = new TinkerPopVertexMatcher(null, null)
		assertTrueWhenBothVerticesAreNull(m.vertexMatch _)
	}

	it should "return [false] when one of the vertices is null" in
	{
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = new TinkerPopVertexMatcher(v, null)

		assertFalseWhenOneVertexIsNull(m.vertexMatch _)
	}

	behavior of "vertexPropertyMatch   :"

	it should "return [true ] when both vertices are null" in
	{
		val m = new TinkerPopVertexMatcher(null, null)
		assertTrueWhenBothVerticesAreNull(m.vertexPropertyMatch _)
	}

	it should "return [false] when one of the vertices is null" in
	{
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m1 = new TinkerPopVertexMatcher(v, null)
		val m2 = new TinkerPopVertexMatcher(null, v)

		assertFalseWhenOneVertexIsNull(m1.vertexPropertyMatch _)
		assertFalseWhenOneVertexIsNull(m2.vertexPropertyMatch _)
	}

	it should "return [true ] when neither of the vertices have properties" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = new TinkerPopVertexMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexPropertyMatch

		// Assert
		//
		ok should be (true)
	}

	it should "return [false] when only one of the vertices has properties" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Motegi")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val m = new TinkerPopVertexMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexPropertyMatch

		// Assert
		//
		ok should be (false)
	}

	it should "return [false] when properties have different keys" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Leo")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "sign", "Leo")
		val m = new TinkerPopVertexMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexPropertyMatch

		// Assert
		//
		ok should be (false)
	}

	it should "return [false] when properties have different values" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Leo")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Larry")
		val m = new TinkerPopVertexMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexPropertyMatch

		// Assert
		//
		ok should be (false)
	}

	it should "return [false] when properties have different number of values" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Leo", "name", "Leonard")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Leo")
		val m = new TinkerPopVertexMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexPropertyMatch

		// Assert
		//
		ok should be (false)
	}

	it should "return [true ] when properties (including repeated) are the same" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "first", "Leo", "last", "Leonard", "pet", "Alfie", "pet", "Barky")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "first", "Leo", "pet", "Alfie", "pet", "Barky", "last", "Leonard")
		val m = new TinkerPopVertexMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexPropertyMatch

		// Assert
		//
		ok should be (true)
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
