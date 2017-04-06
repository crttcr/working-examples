package args.marshall;

import java.util.Iterator;

import args.error.ArgsException;

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
}

