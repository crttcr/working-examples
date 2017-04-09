package args.marshall;

import java.util.Iterator;
import java.util.Objects;

import args.error.ArgsException;
import args.schema.OptionType;

public abstract class OptEvaluatorBase<T>
implements OptEvaluator<T>
{
	private int count = 0;

	protected abstract void doSet(Iterator<String> currentArgument) throws ArgsException;

	@Override
	public final void set(Iterator<String> currentArgument) throws ArgsException
	{
		count++;
		doSet(currentArgument);
	}

	public int count() { return count; }

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

