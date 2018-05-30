package xivvic.adt.pgraph.simple;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent=true)
public class Property
{
	private final String name;
	private final Object value;
}
