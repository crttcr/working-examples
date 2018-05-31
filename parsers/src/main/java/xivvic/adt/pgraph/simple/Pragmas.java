package xivvic.adt.pgraph.simple;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.val;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@Builder
public class Pragmas
{
	public static final String VERTEX_GENERATOR_CLASS = "vertex.auto.id.generator.class";

	@Singular private List<Property> properties;

	// Convenience methods to simplify client code
	//
	public boolean exists(String name)
	{
		val f = nameFilter(name);
		return properties.stream().anyMatch(f);
	}

	public Optional<Object> firstValueFor(String name)
	{
		val f = nameFilter(name);

		return properties.stream().filter(f).findFirst().map(Property::value);
	}

	private Predicate<Property> nameFilter(String name)
	{
		return p -> p.name().equals(name);
	}
}
