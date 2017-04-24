package args.marshall;

import static args.error.ErrorCode.MISSING_STRING;

import java.util.Iterator;
import java.util.NoSuchElementException;

import args.error.ArgsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper=false)
public class StringOptEvaluator extends OptEvaluatorBase<String>
{
	@Getter
	private String value = null;

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

	@Override
	public String toString()
	{
		return "String[called = " + count() + ", value = " + value + "]";
	}
}
