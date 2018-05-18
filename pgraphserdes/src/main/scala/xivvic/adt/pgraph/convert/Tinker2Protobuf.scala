package xivvic.adt.pgraph.convert

import org.apache.tinkerpop.gremlin.structure.{Graph => TGraph, Vertex => TVertex, Edge => TEdge}
import xivvic.proto.adt.pgraph.{PGraph, Vertex => PVertex, Edge => PEdge}
import org.apache.tinkerpop.gremlin.structure.T
import xivvic.proto.adt.pgraph.Property
import org.apache.tinkerpop.gremlin.structure.VertexProperty

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
			val    vb = PVertex.newBuilder().addLabel(label).setId(id)

			val it = tv.properties()

			while (it.hasNext())
			{
				val p = it.next().asInstanceOf[VertexProperty[Object]]
				if (p.isPresent())
				{
					val key = p.key()
					val value = p.value()
					val ptype = Property.Type.STRING

					val newp = Property.newBuilder().setName(key).setType(ptype).setValue(value.toString).build()
					vb.addP(newp)

				}
				// println("Property: " + p)
			}

			val pv = vb.build()
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
				.setRelType(label)
				.setFrom(out)
				.setTo(in)
				.build()
			builder.addE(pe)
		}
	}

}
