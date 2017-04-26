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

	@SuppressWarnings("unchecked")
	public static <T> OptEvaluator<T> getEvaluatorForType(OptionType type)
	{
		Objects.requireNonNull(type);

		switch (type)
		{
		case BOOLEAN: return (OptEvaluator<T>) new BooleanOptEvaluator();
		case STRING:  return (OptEvaluator<T>) new StringOptEvaluator();
		case INTEGER: return (OptEvaluator<T>) new IntegerOptEvaluator();
		default: throw new IllegalArgumentException("Not implemented: " + type);
		}
	}

}

