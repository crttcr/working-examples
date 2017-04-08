package args.marshall;

import java.util.Objects;

import args.schema.OptionType;

public class OptEvaluatorUtil
{
	public static OptEvaluator<?> getEvaluatorForType(OptionType type)
	{
		Objects.requireNonNull(type);

		switch (type)
		{
		case BOOLEAN: return new BooleanOptEvaluator();
		case STRING: return new StringOptEvaluator();
		case INTEGER: return new IntegerOptEvaluator();
		default: throw new IllegalArgumentException("Not implemented: " + type);

		}
	}

}
