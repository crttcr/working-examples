package xivvic.adt.pgraph.util

import scala.collection.mutable.ArrayBuffer
import xivvic.proto.adt.pgraph.PGraph
import xivvic.proto.adt.pgraph.Vertex
import xivvic.proto.adt.pgraph.Edge
import xivvic.proto.adt.pgraph.Property

/**
 * PGProtoElf provides convenience methods for working with PGraph Protobuf classes
 */
object PGProtoElf
{
	def allVertices(g: PGraph): Array[Vertex] =
	{
		if (g == null) { return Array() }

		val it = g.getVList.iterator
		val rv = ArrayBuffer.empty[Vertex]

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
		val rv = ArrayBuffer.empty[Edge]

		while (it.hasNext)
		{
			rv += it.next
		}

		rv.toArray
	}

	def propertyString2Object(v: String, t: Property.Type): Object =
	{
		val str = Property.Type.STRING

		t match {
			case x if x == Property.Type.STRING    => v
			case x if x == Property.Type.BOOLEAN   => if (v.equalsIgnoreCase("true")) java.lang.Boolean.TRUE else java.lang.Boolean.FALSE
			case x if x == Property.Type.FLOAT     => new java.lang.Float(v)
			case x if x == Property.Type.INTEGER   => new java.lang.Integer(v)
			case _                                 => v
		}
	}

}
