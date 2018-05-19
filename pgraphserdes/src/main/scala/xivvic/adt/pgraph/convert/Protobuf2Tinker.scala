package xivvic.adt.pgraph.convert

import org.apache.tinkerpop.gremlin.structure.{Graph => TGraph, Vertex => TVertex, Edge => TEdge, Property => TProperty}
import xivvic.proto.adt.pgraph.{PGraph, Vertex => PVertex, Edge => PEdge, Property => PProperty}
import org.apache.tinkerpop.gremlin.structure.T
import xivvic.adt.pgraph.util.PGProtoElf
import com.google.protobuf.ProtocolStringList

class Protobuf2Tinker(val graph: TGraph)
{
	val vmap = collection.mutable.Map.empty[String, TVertex]

	/** vcf returns a function that converts a Protobuf vertex to a TinkerPop vertex
	 *  by constructing it and adding it to this object's graph. It also keeps track
	 *  of the constructed vertices for use in building edges.
	 */
	def vcf(): PVertex => Unit =
	{
		pv =>
		{
			val     id = pv.getId.toString
			val labels = pv.getLabelList
			val  props = vertexProperties2array(pv)
			val   args = createVertexArgs(labels, props)
			val     tv = graph.addVertex(args:_*)

			vmap(id) = tv
		}
	}

	/** ecf returns a function that converts a Protobuf edge to a TinkerPop edge
	 *  by constructing it and adding it to this object's graph.
	 */
	def ecf(): PEdge => Unit =
	{
		pe =>
		{
			val  label = pe.getRelType
			val out_id = pe.getFrom
			val  in_id = pe.getTo
			val    out = vmap(out_id)
			val     in = vmap(in_id)
			val props  = edgeProperties2array(pe)

			out.addEdge(label, in, props:_*)
		}
	}

	/**
	 * createVertexArgs combines the vertex labels and properties into a single array
	 * that gets passed to the vertex's constructor.
	 */
	private def createVertexArgs(labels: ProtocolStringList, props: Array[Object]) =
	{
		val rv = Array.ofDim[Object](2 * labels.size + props.length)
		var index = 0

		labels.forEach
		{
			label =>
			{
				rv(index)     = T.label
				rv(index + 1) = label
				index += 2
			}
		}

		for (i <- 0 until props.length)
		{
			rv(i + index) = props(i)
		}

		rv
	}

	private def vertexProperties2array(pv: PVertex): Array[Object] =
	{
		val  props = pv.getPList
		val     rv = new scala.collection.mutable.ArrayBuffer[Object]

		props.forEach
		{
			p =>
			{
				val n = p.getName
				val v = p.getValue
				val t = p.getType
				val o = PGProtoElf.propertyString2Object(v, t)

				rv += n
				rv += v

				println(rv)
			}
		}

		rv.toArray
	}

	private def edgeProperties2array(pe: PEdge): Array[Object] =
	{
		val props = pe.getPList
		val    rv = new scala.collection.mutable.ArrayBuffer[Object]

		props.forEach
		{
			p =>
			{
				val n = p.getName
				val v = p.getValue
				val t = p.getType
				val o = PGProtoElf.propertyString2Object(v, t)

				rv += n
				rv += v

				println(rv)
			}
		}

		rv.toArray
	}

}
