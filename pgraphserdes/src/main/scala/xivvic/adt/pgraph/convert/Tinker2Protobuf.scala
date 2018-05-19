package xivvic.adt.pgraph.convert

import org.apache.tinkerpop.gremlin.structure.{Graph => TGraph, Vertex => TVertex, Edge => TEdge, Property => TProperty}
import xivvic.proto.adt.pgraph.{PGraph, Vertex => PVertex, Edge => PEdge}
import org.apache.tinkerpop.gremlin.structure.T
import xivvic.proto.adt.pgraph.{Property => PProperty}
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
			val    it = tv.properties()

			val    vb = PVertex.newBuilder().addLabel(label).setId(id)

			addPropertiesToVertex(vb, it)

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
			val label = te.label()
			val   out = te.outVertex().id().toString()
			val    in = te.inVertex().id().toString()
			val    it = te.properties()

			val    eb = PEdge.newBuilder()
				.setRelType(label)
				.setFrom(out)
				.setTo(in)

			addPropertiesToEdge(eb, it)

			val pe = eb.build()
			builder.addE(pe)
		}
	}

	private def addPropertiesToEdge(eb: PEdge.Builder, it: java.util.Iterator[TProperty[Nothing]]): Unit =
	{
		while (it.hasNext())
		{
			val p = it.next().asInstanceOf[TProperty[Object]]
			if (p.isPresent())
			{
				val key = p.key()
				val value = p.value()
				val ptype = PProperty.Type.STRING

				val newp = PProperty.newBuilder().setName(key).setType(ptype).setValue(value.toString).build()
				eb.addP(newp)

			}
			// println("Property: " + p)
		}

	}

	private def addPropertiesToVertex(vb: PVertex.Builder, it: java.util.Iterator[VertexProperty[Nothing]]): Unit =
	{

		while (it.hasNext())
		{
			val p = it.next().asInstanceOf[VertexProperty[Object]]
			if (p.isPresent())
			{
				val key = p.key()
				val value = p.value()
				val ptype = PProperty.Type.STRING

				val newp = PProperty.newBuilder().setName(key).setType(ptype).setValue(value.toString).build()
				vb.addP(newp)

			}
			// println("Property: " + p)
		}

	}

}
