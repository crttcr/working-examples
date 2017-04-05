package args.marshall;

import static args.ErrorCode.MISSING_STRING;

import java.util.Iterator;
import java.util.NoSuchElementException;

import args.ArgsException;

public class StringOptionEvaluator extends OptionEvaluatorBase<String>
{
	private String value = "";

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			value = currentArgument.next();
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_STRING);

		}
	}

	public static String getValue(OptionEvaluator am)
	{
		if (am != null && am instanceof StringOptionEvaluator)
		{
			return ((StringOptionEvaluator) am).value;
		}

		return "";
	}

	@Override
	public String toString()
	{
		return "String[called = " + count() + ", value = " + value + "]";
	}
}
