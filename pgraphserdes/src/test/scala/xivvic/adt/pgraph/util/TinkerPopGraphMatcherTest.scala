package xivvic.adt.pgraph.util

import org.junit.runner.RunWith
import xivvic.adt.pgraph.util.serde.TinkerPopSerde
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.scalatest.junit.JUnitRunner
import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.apache.tinkerpop.gremlin.structure.T
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory

@RunWith(classOf[JUnitRunner])
class TinkerPopGraphMatcherTest
	extends FlatSpecLike
		with Matchers
{

	behavior of "vertexCardinalityMatch:"

	it should "return true when both graphs are null" in
	{
		// Arrange
		//
		val a: Graph = null;
		val b: Graph = null;
		val m = new TinkerPopGraphMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexCardinalityMatch()

		// Assert
		//
		ok should be (true)
		msg should include ("null")
	}

	it should "return false when one of the graphs is null" in
	{
		// Arrange
		//
		val a: Graph = null;
		val b: Graph = TinkerGraph.open
		val m = new TinkerPopGraphMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexCardinalityMatch()

		// Assert
		//
		ok should be (false)
		msg should include ("null")
	}

	it should "return true when given two copies of the same graph" in
	{
		// Arrange
		//
		val a: Graph = TinkerFactory.createModern()
		val b: Graph = TinkerFactory.createModern()
		val m = new TinkerPopGraphMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.vertexCardinalityMatch()

		// Assert
		//
		ok should be (true)
		msg should include ("cardinality")
	}

	behavior of "edgeCardinalityMatch  :"

	it should "return true when both graphs are null" in
	{
		// Arrange
		//
		val a: Graph = null;
		val b: Graph = null;
		val m = new TinkerPopGraphMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.edgeCardinalityMatch()

		// Assert
		//
		ok should be (true)
		msg should include ("null")
	}

	it should "return false when one of the graphs is null" in
	{
		// Arrange
		//
		val a: Graph = null;
		val b: Graph = TinkerGraph.open
		val m = new TinkerPopGraphMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.edgeCardinalityMatch()

		// Assert
		//
		ok should be (false)
		msg should include ("null")
	}

	it should "return true when given two copies of the same graph" in
	{
		// Arrange
		//
		val a: Graph = TinkerFactory.createModern()
		val b: Graph = TinkerFactory.createModern()
		val m = new TinkerPopGraphMatcher(a, b)

		// Act
		//
		val (ok, msg) = m.edgeCardinalityMatch()

		// Assert
		//
		ok should be (true)
		msg should include ("cardinality")
	}

}
