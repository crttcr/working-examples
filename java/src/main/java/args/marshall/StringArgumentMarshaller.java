package args.marshall;

import static args.ErrorCode.MISSING_STRING;

import java.util.Iterator;
import java.util.NoSuchElementException;

import args.ArgsException;

public class StringArgumentMarshaller extends ArgumentMarshallerBase<String>
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

	public static String getValue(ArgumentMarshaller am)
	{
		if (am != null && am instanceof StringArgumentMarshaller)
		{
			return ((StringArgumentMarshaller) am).value;
		}

		return "";
	}

	@Override
	public String toString()
	{
		return "String[called = " + count() + ", value = " + value + "]";
	}
}
