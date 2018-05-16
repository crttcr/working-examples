package xivvic.adt.pgraph.util

import scala.collection.mutable.ArrayBuffer
import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.{Vertex, Edge}

/**
 * TinkerPopElf provides convenience methods for working with TinkerPop
 */
object TinkerPopElf
{
	def allVertices(g: Graph): Array[Vertex] =
	{
		if (g == null) { return Array() }

		val it = g.vertices()
		val rv = ArrayBuffer.empty[Vertex]

		it.forEachRemaining(rv += _)

		rv.toArray
	}

	def allEdges(g: Graph): Array[Edge] =
	{
		if (g == null) { return Array() }

		val it = g.edges()
		val rv = ArrayBuffer.empty[Edge]

		it.forEachRemaining(rv += _)
		rv.toArray
	}

}
