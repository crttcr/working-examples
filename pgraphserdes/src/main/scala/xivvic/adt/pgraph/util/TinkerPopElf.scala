package xivvic.adt.pgraph.util

import org.apache.tinkerpop.gremlin.structure.Graph
import org.apache.tinkerpop.gremlin.structure.{Vertex, Edge}
import scala.collection.JavaConversions._

object TinkerPopElf
{
	val no_ids: Array[Object] = Array()

	def allVertices(g: Graph): Array[Vertex] =
	{

		if (g == null) { return Array() }

		val it = g.vertices(no_ids);
		val rv = scala.collection.mutable.ArrayBuffer.empty[Vertex]

		it.forEachRemaining(rv.+= _)
		rv.toArray
	}

	def allEdges(g: Graph): Array[Edge] =
	{
		if (g == null) { return Array() }

		val it = g.edges(no_ids);
		val rv = scala.collection.mutable.ArrayBuffer.empty[Edge]

		it.forEachRemaining(rv.+= _)
		rv.toArray
	}

}
