package xivvic.adt.pgraph.util

import xivvic.proto.adt.pgraph.PGraph
import xivvic.proto.adt.pgraph.Vertex
import xivvic.proto.adt.pgraph.Edge
/**
 * PGProtoElf provides convenience methods for working with PGraph Protobuf classes
 */
object PGProtoElf
{
	def allVertices(g: PGraph): Array[Vertex] =
	{
		if (g == null) { return Array() }

		val it = g.getVList.iterator
		val rv = scala.collection.mutable.ArrayBuffer.empty[Vertex]

		while (it.hasNext)
		{
			rv += it.next
		}

		rv.toArray
	}

	def allEdges(g: PGraph): Array[Edge] =
	{
		if (g == null) { return Array() }

		val it = g.getEList.iterator
		val rv = scala.collection.mutable.ArrayBuffer.empty[Edge]

		while (it.hasNext)
		{
			rv += it.next
		}

		rv.toArray
	}

}
