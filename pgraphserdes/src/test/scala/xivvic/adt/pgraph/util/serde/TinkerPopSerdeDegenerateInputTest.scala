package xivvic.adt.pgraph.util.serde

import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitSuite
import org.scalatest.junit.JUnitRunner
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

@RunWith(classOf[JUnitRunner])
class TinkerPopSerdeDegenerateInputTest
	extends FlatSpecLike
		with Matchers
{
	import TinkerPopSerde._

	behavior of "Serialize"

	it should "return a zero length array when provided a null graph" in
	{
		// Act
		//
		val have = serialize(null)

		// Assert
		//
		have should not be (null)
		have.length should be (0)
	}

	it should "return a zero length array when provided an empty graph" in
	{
		// Arrange
		//
		val g = TinkerGraph.open()

		// Act
		//
		val have = serialize(g)

		// Assert
		//
		have should not be (null)
		have.length should be (0)
	}

	behavior of "Deserialize"

	it should "return an empty graph when provided a null byte array" in
	{
		// Arrange
		//
		val nothing = Array()

		// Act
		//
		val g = deserialize(null)

		// Assert
		//
		g should not be (null)
		g.vertices(nothing).hasNext() should be (false)
	}

	it should "return an empty graph when provided an empty byte array" in
	{
		// Arrange
		//
		val empty: Array[Byte] = Array()
		val nothing = Array()

		// Act
		//
		// Act
		//
		val g = deserialize(empty)

		// Assert
		//
		g should not be (null)
		g.vertices(nothing).hasNext() should be (false)
	}

}
