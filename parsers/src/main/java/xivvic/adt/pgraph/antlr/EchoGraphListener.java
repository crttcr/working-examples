package xivvic.adt.pgraph.antlr;

import java.util.Iterator;
import java.util.List;

import xivvic.adt.pgraph.antlr.PropertyGraphParser.LabelContext;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.PropContext;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.PropertiesContext;
import xivvic.adt.pgraph.antlr.PropertyGraphParser.VnameContext;

public class EchoGraphListener
	extends PropertyGraphBaseListener
{

	// FIXME: Add edge listeners
	// FIXME: Need to synthesize ID if it is not provided
	//

	private final StringBuilder sb = new StringBuilder();
	private final boolean quiet = false;

	public String result() { return sb.toString(); }

	@Override public void exitVertex(PropertyGraphParser.VertexContext ctx)
	{
		writeVertexName(ctx.vname());
		writeLabels(ctx.label());
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
			final String pname = pc.pname().getText();
			final String value = pc.value().getText();
			final String  text = String.format("\t\tPROP %s -> %s\n", pname, value);
			sb.append(text);

			if (! quiet)
			{
				System.out.print(text);
			}
		}
	}

	private void writeVertexName(VnameContext c)
	{
		if (c == null) return;

		final String name = c.getText();
		final String text = "VERTEX: " + name + "\n";

		sb.append(text);

		if (! quiet)
		{
			System.out.print(text);
		}

	}

	private void writeLabels(List<LabelContext> cs)
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
