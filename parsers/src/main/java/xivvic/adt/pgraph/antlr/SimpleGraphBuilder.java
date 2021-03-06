package xivvic.adt.pgraph.antlr;

 import static xivvic.adt.pgraph.antlr.error.GraphDefinitionError.FMT_SYMBOL_NOT_RESOLVED;
import static xivvic.adt.pgraph.antlr.error.GraphDefinitionError.FMT_SYMBOL_REUSE;

import java.util.ArrayList;
import java.util.HashMap;
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
import xivvic.adt.pgraph.antlr.error.GraphDefinitionError;
import xivvic.adt.pgraph.antlr.error.GraphDefinitionError.Severity;
import xivvic.adt.pgraph.antlr.error.GraphDefinitionResult.GraphDefinitionResultBuilder;
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

	private final GraphDefinitionResultBuilder result_builder;

	public SimpleGraphBuilder(@NonNull GraphDefinitionResultBuilder result_builder)
	{
		this. result_builder = result_builder;
	}

	@Override public void exitGraph(@NonNull PropertyGraphParser.GraphContext ctx)
	{
		graph  = graph_builder.build();
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

		val existing = vmap.get(name);
		if (existing != null && name.equals("_") == false)
		{
			val err = GraphDefinitionError.getSymbolError(c.NAME(), c, Severity.WARNING, FMT_SYMBOL_REUSE);
			result_builder.error(err);
		}

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

		boolean failed = false;

		if (out == null)
		{
			val err = GraphDefinitionError.getSymbolError(c.NAME(0), c, Severity.FAILURE, FMT_SYMBOL_NOT_RESOLVED);
			result_builder.error(err);
			failed = true;
		}

		if (in == null)
		{
			val err = GraphDefinitionError.getSymbolError(c.NAME(2), c, Severity.FAILURE, FMT_SYMBOL_NOT_RESOLVED);
			result_builder.error(err);
			failed = true;
		}

		if (failed)
			return;

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

		val it = lpc.iterator();
		while (it.hasNext())
		{
			val pc = it.next();
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
			case PropertyGraphLexer.NAME:   return text;

			case PropertyGraphLexer.STRING:
			case PropertyGraphLexer.SQSTR:
			case PropertyGraphLexer.DQSTR:
				val clean = text.substring(1, text.length() - 1);
				return clean;
		}

		val fmt = "Option unknown: lexer token (from first child of context) not handled in switch statement.\n\tType [%s]\n\tText [%s]\n\tContext [%s]\n";
		val msg = String.format(fmt, option, text, c.getText());

		throw new IllegalArgumentException(msg);
	}

	private List<String> labels(@NonNull List<LabelContext> cs)
	{
		val rv = new ArrayList<String>();
		val it = cs.iterator();

		while (it.hasNext())
		{
			val label = it.next().getText();
			rv.add(label);
		}

		return rv;
	}

}
