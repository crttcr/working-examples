package xivvic.adt.pgraph.util

import org.apache.tinkerpop.gremlin.structure.{Graph => TGraph, Vertex => TVertex, Edge => TEdge}
import xivvic.proto.adt.pgraph.{PGraph, Vertex => PVertex, Edge => PEdge}
import org.apache.tinkerpop.gremlin.structure.T

/**
 *  Coverter provides functions that return converters for vertices and edges from
 *  TinkerGraph to a ProtoBuf Graph or vice versa.
 *
 */
object Converter
{
		// Note: It's possible to create a vertex prototype and reuse that
		//
		// 	    Vertex  proto = Vertex.newBuilder().addLabel("PERSON").build();
		//        xivvic.proto.adt.pgraph.Vertex pv = new xivvic.proto.adt.pgraph.Vertex.Builder().build();

	def tv2pv(b: PGraph.Builder): TVertex => Unit =
	{
		tv =>
		{
			val label = tv.label()
			val pv = PVertex.newBuilder().addLabel(label).build()
			b.addV(pv)
		}
	}

	def te2pe(b: PGraph.Builder): TEdge => Unit =
	{
		te =>
		{
			val label = te.label()
			val pe = PEdge.newBuilder().build()
			b.addE(pe)
		}
	}

	def pv2tv(g: TGraph): PVertex => Unit =
	{
		pv =>
		{
			val label = pv.getLabel(0)
			val    tv = g.addVertex(T.label, label)
		}
	}

	def pe2te(tg: TGraph): PEdge => Unit = ???
}

/*
		def cf(v: TVertex): Unit =
		{
			val label = v.label()
			val pv = PVertex.newBuilder().addLabel(label).build()
			builder.addV(pv)
		}
*/
