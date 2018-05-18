package xivvic.adt.pgraph.convert

import org.apache.tinkerpop.gremlin.structure.{Graph => TGraph, Vertex => TVertex, Edge => TEdge}
import xivvic.proto.adt.pgraph.{PGraph, Vertex => PVertex, Edge => PEdge}
import org.apache.tinkerpop.gremlin.structure.T

class Protobuf2Tinker(val graph: TGraph)
{
	val vmap = collection.mutable.Map.empty[String, TVertex]

	/** vcf returns a function that converts a Protobuf vertex
	 *  to a TinkerPop vertex by constructing it and adding it
	 *  to this object's graph. It also keeps track of what is
	 *  constructed for edge references.
	 */
	def vcf(): PVertex => Unit =
	{
		pv =>
		{
			// Fixme: Need to handle labels correctly.
			//
			val label = pv.getLabel(0)
			val    id = pv.getId.toString
			val props = pv.getPList

			// FIXME: Set Properties
			//
			val    tv = graph.addVertex(T.label, label)
			vmap(id) = tv
		}
	}

	def ecf(): PEdge => Unit =
	{
		pe =>
		{
			val  label = pe.getRelType
			val out_id = pe.getFrom
			val  in_id = pe.getTo
			val    out = vmap(out_id)
			val     in = vmap(in_id)
			val  props = pe.getPList

			// FIXME: Set Propeties
			//

			println(s"Adding label [$label] to edge")
			out.addEdge(label, in)

		}
	}

}
