package xivvic.adt.pgraph.antlr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.antlr.v4.runtime.tree.TerminalNode;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import lombok.experimental.Accessors;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.LabelContext;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.PropContext;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.PropertiesContext;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.ValueContext;
import xivvic.adt.pgraph.simple.Edge;
import xivvic.adt.pgraph.simple.Graph;
import xivvic.adt.pgraph.simple.Pragmas;
import xivvic.adt.pgraph.simple.Property;
import xivvic.adt.pgraph.simple.Vertex;
import xivvic.adt.pgraph.simple.util.SequenceGenerator;


/**
 * SimpleGraphBuilder provides an example listener that builds a graph from the parsed content.
 * It uses simple graph objects from the {@link xivvic.adt.pgraph.simple} package to contain
 * the in memory representation of the graph.
 *
 */

@Accessors(fluent = true)
public class SimpleGraphBuilder
	extends PropertyGraphBaseListener
{
	private final Pragmas.PragmasBuilder prag_builder = Pragmas.builder();
	private final Graph.GraphBuilder graph_builder = Graph.builder();
	private final Map<String, Vertex> vmap = new HashMap<>();

	@Getter
	private Pragmas pragmas = null;

	@Getter
	private Graph graph = null;


	@Override public void exitGraph(@NonNull PropertyGraphParser.GraphContext ctx)
	{
		graph = graph_builder.build();
	}

	@Override public void exitPragma(@NonNull PropertyGraphParser.PragmaContext ctx)
	{
		val pc = ctx.prop();
		val  p = createProperty(pc);

		prag_builder.property(p);
	}

	// Entering the first vertex occurs after all the Pragmas are complete, so use
	// this hook to build out the Pragma.
	//
	@Override public void enterVertex(@NonNull PropertyGraphParser.VertexContext c)
	{
		if (pragmas == null)
		{
			pragmas = prag_builder.build();
		}
	}

	@Override public void exitVertex(PropertyGraphParser.VertexContext c)
	{
		val    name = c.NAME().getText();
		val  labels = labels(c.label());
		val   props = createProperties(c.properties());
		val      id = getOrCreateId(props);
		val builder = Vertex.builder();

		val v = builder.id(id).labels(labels).properties(props).build();

		vmap.put(name, v);
		graph_builder.vertex(v);
	}

	private String getOrCreateId(List<Property> props)
	{

		val opt = props.stream().filter(p -> p.name().equals("id")).findFirst();

		if (opt.isPresent())
		{
			val p = opt.get();
			return p.value().toString();
		}


		val id = SequenceGenerator.next_id_string();

		return id;
	}

	@Override public void exitEdge(PropertyGraphParser.EdgeContext c)
	{
		val     rel = c.NAME(1).getText();
		val   props = createProperties(c.properties());
		val out_ref = c.NAME(0).getText();
		val  in_ref = c.NAME(2).getText();
		val     out = vmap.get(out_ref);
		val      in = vmap.get( in_ref);

		val e = new Edge(rel, out, in, props);

		graph_builder.edge(e);
	}

	private List<Property> createProperties(PropertiesContext c)
	{
		val rv = new ArrayList<Property>();
		if (c == null)
			return rv;

		final List<PropContext> lpc = c.prop();
		Objects.requireNonNull(lpc);

		final Iterator<PropContext> pit = lpc.iterator();
		while (pit.hasNext())
		{
			val pc = pit.next();
			val  p = createProperty(pc);
			rv.add(p);
		}

		return rv;
	}

	private Property createProperty(@NonNull PropContext c)
	{
		val pname = c.NAME().getText();
		val value = valueFromContext(c.value());
		val    rv = new Property(pname, value);

		return rv;
	}

	private Object valueFromContext(@NonNull ValueContext c)
	{
		val option = (TerminalNode) c.getChild(0);
		val   text = option.getText();

		switch (option.getSymbol().getType())
		{
			case PropertyGraphLexer.TRUE:   return Boolean.TRUE;
			case PropertyGraphLexer.FALSE:  return Boolean.FALSE;
			case PropertyGraphLexer.BOOL:   return text.startsWith("t") ? Boolean.TRUE : Boolean.FALSE;
			case PropertyGraphLexer.INT:    return new Integer(text);
			case PropertyGraphLexer.FLOAT:  return new Float(text);
			case PropertyGraphLexer.DOUBLE: return new Double(text);

			case PropertyGraphLexer.STRING:
			case PropertyGraphLexer.SQSTR:
			case PropertyGraphLexer.DQSTR:
				val clean = text.substring(1, text.length() - 1);
				return clean;
		}

		throw new IllegalArgumentException("Unable to handle option type " + option +  " with text: " + text);
	}

	private List<String> labels(@NonNull List<LabelContext> cs)
	{
		val rv = new ArrayList<String>();
		final Iterator<LabelContext> it = cs.iterator();

		while (it.hasNext())
		{
			final LabelContext c = it.next();
			final String label = c.getText();
			rv.add(label);
		}

		return rv;
	}

}
