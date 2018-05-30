package xivvic.adt.pgraph.simple;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent=true)
public class Edge
{
	private final String relation;
	private final Vertex from;
	private final Vertex to;
	private final List<Property> properties;
}
