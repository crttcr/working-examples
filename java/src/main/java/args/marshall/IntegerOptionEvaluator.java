package args.marshall;

import static args.ErrorCode.INVALID_INTEGER;
import static args.ErrorCode.MISSING_INTEGER;

import java.util.Iterator;
import java.util.NoSuchElementException;

import args.ArgsException;

public class IntegerOptionEvaluator extends OptionEvaluatorBase<Integer>
{
	private int value = 0;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		String parameter = null;

		try
		{
			parameter = currentArgument.next();
			value = Integer.parseInt(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_INTEGER);
		}
		catch (NumberFormatException e)
		{
			throw new ArgsException(INVALID_INTEGER);
		}
	}

	public static Integer getValue(OptionEvaluator am)
	{
		if (am != null && am instanceof IntegerOptionEvaluator)
		{
			return ((IntegerOptionEvaluator) am).value;
		}

		return 0;
	}

	@Override
	public String toString()
	{
		return "Integer[called = " + count() + ", value = " + value + "]";
	}

}
