package xivvic.adt.pgraph.simple;

import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.val;

public class Formatter
{
	public static String format(@NonNull Graph g)
	{
		val sb = new StringBuilder();

		sb.append("Graph:").append("\n");

		val vs = g.vertices();

		for (val v: vs)
		{
			val fmt = Formatter.format(v);
			sb.append("\t").append(fmt).append("\n");
		}

		val es = g.edges();

		for (val e: es)
		{
			val fmt = Formatter.format(e);
			sb.append("\t").append(fmt).append("\n");
		}

		return sb.toString();

	}

	public static String format(@NonNull Vertex v)
	{
		val sb = new StringBuilder();

		sb.append("Vertex:")
		.append("id=")
		.append(v.id())
		.append(", labels=")
		.append(v.labels())
		.append("\n");

		val props = v.properties();
		if (props.size() != 0)
		{
			val fmt = format(props);
			sb.append(fmt);
			sb.append("\n");
		}

		return sb.toString();
	}

	public static String format(@NonNull Edge e)
	{
		val sb = new StringBuilder();

		sb.append("Edge:")
		.append("(")
		.append(e.from().id())
		.append(")")
		.append("--[:")
		.append(e.relation())
		.append("]->(")
		.append(e.to().id())
		.append(")")
		.append("\n");

		val props = e.properties();
		if (props.size() != 0)
		{
			val fmt = format(props);
			sb.append(fmt);
			sb.append("\n");
		}

		return sb.toString();
	}

	public static String stringify(Property p)
	{
		return p.name() + " = " + p.value().toString();
	}

	public static String format(@NonNull List<Property> ps)
	{

		if (ps.size() == 0)
			return "";

		val x = ps.stream().map(Formatter::stringify).collect(Collectors.joining(", "));
		val sb = new StringBuilder();

		sb.append("\t\tProps: {");
		sb.append(x);
		sb.append("}");

		return sb.toString();
	}

}
