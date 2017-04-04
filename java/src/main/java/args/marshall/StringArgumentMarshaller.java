package args.marshall;

import static args.ErrorCode.MISSING_STRING;

import java.util.Iterator;
import java.util.NoSuchElementException;

import args.ArgsException;

public class StringArgumentMarshaller implements ArgumentMarshaller
{
	private String value = "";

	@Override
	public void set(Iterator<String> currentArgument) throws ArgsException
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

	public static String getValue(ArgumentMarshaller am)
	{
		if (am != null && am instanceof StringArgumentMarshaller)
		{
			return ((StringArgumentMarshaller) am).value;
		}

		return "";
	}
}
