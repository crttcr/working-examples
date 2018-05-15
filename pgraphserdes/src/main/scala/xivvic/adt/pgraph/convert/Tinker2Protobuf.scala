package xivvic.adt.pgraph.convert

import org.apache.tinkerpop.gremlin.structure.{Graph => TGraph, Vertex => TVertex, Edge => TEdge}
import xivvic.proto.adt.pgraph.{PGraph, Vertex => PVertex, Edge => PEdge}
import org.apache.tinkerpop.gremlin.structure.T

class Tinker2Protobuf(val builder: PGraph.Builder)
{
	val vmap = collection.mutable.Map.empty[String, PVertex]

	/** vcf returns a function that converts a TinkerPop vertex
	 *  to a Protobuf vertex by constructing it and adding it
	 *  to this object's builder.
	 */
	def vcf(): TVertex => Unit =
	{
		tv =>
		{
			val label = tv.label()
			val    id = tv.id().toString()
			val pv = PVertex.newBuilder().addLabel(label).build()

			// FIXME: Add properties
			//

			vmap(id) = pv
			builder.addV(pv)
		}
	}

	/** ecf returns a function that converts a TinkerPop edge to
	 *  a Protobuf edge by building it with the appropriate properties
	 *  and adding it to the PGraph Builder.
	 */
	def ecf(): TEdge => Unit =
	{
		te =>
		{

			// FIXME: Add properties
			//

			val label = te.label()
			val   out = te.outVertex().id().toString()
			val    in = te.inVertex().id().toString()
			val    pe = PEdge.newBuilder()
				.setFrom(out)
				.setTo(in)
				.build()
			builder.addE(pe)
		}
	}

}
