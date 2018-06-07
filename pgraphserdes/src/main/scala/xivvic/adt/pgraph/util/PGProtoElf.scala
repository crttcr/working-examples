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

		def convertBoolean(v: String): Object =
		{
			if (v.equalsIgnoreCase("true"))
				java.lang.Boolean.TRUE
			else
				java.lang.Boolean.FALSE
		}

		def convertInteger(v: String): Object =
		{
			try
			{
				new java.lang.Integer(v)
			}
			catch
			{
     			case e: NumberFormatException =>
     				val msg = e.getLocalizedMessage
     				println(s"Unable to convert [$v] to integer: $msg")
     				v
	   	}
		}

		def convertFloat(v: String): Object =
		{
			try
			{
				new java.lang.Float(v)
			}
			catch
			{
     			case e: NumberFormatException =>
     				val msg = e.getLocalizedMessage
     				println(s"Unable to convert [$v] to float: $msg")
     				v
	   	}
		}

		def convertDouble(v: String): Object =
		{
			try
			{
				new java.lang.Double(v)
			}
			catch
			{
     			case e: NumberFormatException =>
     				val msg = e.getLocalizedMessage
     				println(s"Unable to convert [$v] to double: $msg")
     				v
	   	}
		}

		t match {
			case x if x == Property.Type.BOOLEAN   => convertBoolean(v)
			case x if x == Property.Type.FLOAT     => convertFloat(v)
			case x if x == Property.Type.DOUBLE    => convertDouble(v)
			case x if x == Property.Type.INTEGER   => convertInteger(v)
			case _                                 => v
		}
	}

}
