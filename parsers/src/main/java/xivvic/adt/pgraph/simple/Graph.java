package xivvic.adt.pgraph.simple;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent=true)
@Builder
public class Graph
{
	@Singular private final List<Vertex> vertices;
	@Singular private final List<Edge> edges;
}
