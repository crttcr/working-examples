package args.marshall;

import static args.ErrorCode.INVALID_INTEGER;
import static args.ErrorCode.MISSING_INTEGER;

import java.util.Iterator;
import java.util.NoSuchElementException;

import args.ArgsException;

public class IntegerArgumentMarshaller implements ArgumentMarshaller
{
	private int value = 0;

	@Override
	public void set(Iterator<String> currentArgument) throws ArgsException
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

	public static Integer getValue(ArgumentMarshaller am)
	{
		if (am != null && am instanceof IntegerArgumentMarshaller)
		{
			return ((IntegerArgumentMarshaller) am).value;
		}

		return 0;
	}
}
