package args.marshall;

import java.util.Iterator;

import args.ArgsException;

public class BooleanOptionEvaluator extends OptionEvaluatorBase<Boolean>
{
	private boolean value = false;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		value = true;
	}

	public static boolean getValue(OptionEvaluator am)
	{
		if (am != null && am instanceof BooleanOptionEvaluator)
		{
			return ((BooleanOptionEvaluator) am).value;
		}

		return false;
	}

	@Override
	public String toString()
	{
		return "Boolean[called = " + count() + ", value = " + value + "]";
	}
}
