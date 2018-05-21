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
import org.apache.tinkerpop.gremlin.structure.Edge

@RunWith(classOf[JUnitRunner])
class TinkerPopEdgeMatcherTest
	extends FlatSpecLike
		with Matchers
{
	import TinkerPopEdgeMatcher._

	behavior of "edgeMatch           :"

	it should "return [true ] when both edges are null" in
	{
		val m = new TinkerPopEdgeMatcher(null, null)
		assertTrueWhenBothEdgesAreNull(m.edgeMatch _)
	}

	it should "return [false] when one of the edges is null" in
	{
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val e:   Edge = v.addEdge("REFLEX", v)
		val m = new TinkerPopEdgeMatcher(e, null)

		assertFalseWhenOneEdgeIsNull(m.edgeMatch _)
	}

	behavior of "edgePropertyMatch   :"

	it should "return [true ] when both edges are null" in
	{
		val m = new TinkerPopEdgeMatcher(null, null)
		assertTrueWhenBothEdgesAreNull(m.edgePropertyMatch _)
	}

	it should "return [false] when one of the edges is null" in
	{
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val e:   Edge = v.addEdge("REFLEX", v)
		val m1 = new TinkerPopEdgeMatcher(e, null)
		val m2 = new TinkerPopEdgeMatcher(null, e)

		assertFalseWhenOneEdgeIsNull(m1.edgePropertyMatch _)
		assertFalseWhenOneEdgeIsNull(m2.edgePropertyMatch _)
	}

	it should "return [true ] when neither of the edges have properties" in
	{
		// Arrange
		//
		val v: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val e1:   Edge = v.addEdge("REFLEX", v)
		val e2:   Edge = v.addEdge("XELFER", v)
		val m = new TinkerPopEdgeMatcher(e1, e2)

		// Act
		//
		val (ok, msg) = m.edgePropertyMatch

		// Assert
		//
		ok should be (true)
	}

	it should "return [false] when only one of the edges has properties" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Motegi")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Suzuki")
		val e1:   Edge = a.addEdge("A_TO_B", b, "EPROP", "EVALUE")
		val e2:   Edge = a.addEdge("B_TO_A", a)
		val m = new TinkerPopEdgeMatcher(e1, e2)

		// Act
		//
		val (ok, msg) = m.edgePropertyMatch

		// Assert
		//
		ok should be (false)
	}

	it should "return [false] when properties have different keys" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON")
		val e1:   Edge = a.addEdge("A_TO_B", b, "weight", 0.5F.asInstanceOf[Object])
		val e2:   Edge = a.addEdge("B_TO_A", a, "height", 0.5F.asInstanceOf[Object])
		val m = new TinkerPopEdgeMatcher(e1, e2)

		// Act
		//
		val (ok, msg) = m.edgePropertyMatch

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
		val e1:   Edge = a.addEdge("A_TO_B", b, "weight", 1.0F.asInstanceOf[Object])
		val e2:   Edge = a.addEdge("B_TO_A", a, "weight", 0.5F.asInstanceOf[Object])
		val m = new TinkerPopEdgeMatcher(e1, e2)

		// Act
		//
		val (ok, msg) = m.edgePropertyMatch

		// Assert
		//
		ok should be (false)
	}

	it should "return [false] when properties have different number of values" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Leo")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Larry")
		val e1:   Edge = a.addEdge("A_TO_B", b, "weight", 1.0F.asInstanceOf[Object])
		val e2:   Edge = a.addEdge("B_TO_A", a, "weight", 0.5F.asInstanceOf[Object], "color", "BLUE")
		val m = new TinkerPopEdgeMatcher(e1, e2)

		// Act
		//
		val (ok, msg) = m.edgePropertyMatch

		// Assert
		//
		ok should be (false)
	}

	it should "return [true ] when all properties are the same" in
	{
		// Arrange
		//
		val a: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Leo")
		val b: Vertex = TinkerGraph.open.addVertex(T.label, "PERSON", "name", "Larry")
		val e1:   Edge = a.addEdge("A_TO_B", b, "color", "BLUE", "weight", 1.0F.asInstanceOf[Object])
		val e2:   Edge = a.addEdge("B_TO_A", a, "weight", 1.0F.asInstanceOf[Object], "color", "BLUE")
		val m = new TinkerPopEdgeMatcher(e1, e2)

		// Act
		//
		val (ok, msg) = m.edgePropertyMatch

		// Assert
		//
		ok should be (true)
	}

	/////////////////////////
	// Helpers             //
	/////////////////////////

	import TinkerPopEdgeMatcher.TPEMFuntion

	def assertTrueWhenBothEdgesAreNull(f: TPEMFuntion): Unit =
	{
		// Act
		//
		val (ok, msg) = f()

		// Assert
		//
		ok should be (true)
	}

	def assertFalseWhenOneEdgeIsNull(f: TPEMFuntion): Unit =
	{
		// Act
		//
		val (ok, msg) = f()

		// Assert
		//
		ok should be (false)
	}

}
