package xivvic.adt.pgraph.antlr;

import java.util.Iterator;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import lombok.NonNull;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.LabelContext;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.PropContext;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.PropertiesContext;

public class EchoGraphListener
	extends PropertyGraphBaseListener
{
	private final StringBuilder sb = new StringBuilder();
	private final boolean quiet = true;

	public String result() { return sb.toString(); }

	@Override public void exitPragma(PropertyGraphParser.PragmaContext ctx)
	{
		writeProperty("##", "", ctx.prop());
	}

	@Override public void exitVertex(PropertyGraphParser.VertexContext ctx)
	{
		writeName("VERTEX", ctx.NAME());
		writeVertexLabels(ctx.label());
		writeProperties(ctx.properties());
	}

	@Override public void exitEdge(PropertyGraphParser.EdgeContext ctx)
	{
		writeName("EDGE", ctx.NAME(1));
		writeProperties(ctx.properties());
	}

	private void writeProperties(PropertiesContext c)
	{
		if (c == null) return;

		final List<PropContext> lpc = c.prop();

		if (lpc == null) return;

		final Iterator<PropContext> pit = lpc.iterator();
		while (pit.hasNext())
		{
			final PropContext pc = pit.next();
			writeProperty("\t\t", "PROP", pc);
		}
	}

	private void writeProperty(String prefix, String label, PropContext c)
	{
		if (c == null) return;

		final String pname = c.NAME().getText();
		final String value = c.value().getText();
		final String  text = String.format("%s%s %s = %s\n", prefix, label, pname, value);
		sb.append(text);

		if (! quiet)
		{
			System.out.print(text);
		}

	}

	private void writeName(@NonNull String context, @NonNull TerminalNode n)
	{
		final String name = n.getText();
		final String text = context + ": " + name + "\n";

		sb.append(text);

		if (! quiet)
		{
			System.out.print(text);
		}

	}

	private void writeVertexLabels(List<LabelContext> cs)
	{
		if (cs == null) return;

		final Iterator<LabelContext> it = cs.iterator();

		while (it.hasNext())
		{
			final LabelContext c = it.next();
			final String label = c.getText();
			final String text = "\tLABEL: " + label + "\n";

			sb.append(text);

         if (! quiet)
         {
            System.out.print(text);
         }
		}

	}

}
