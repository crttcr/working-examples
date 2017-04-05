package args.marshall;

import java.util.Iterator;

import args.ArgsException;

public class BooleanArgumentMarshaller extends ArgumentMarshallerBase<Boolean>
{
	private boolean value = false;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		value = true;
	}

	public static boolean getValue(ArgumentMarshaller am)
	{
		if (am != null && am instanceof BooleanArgumentMarshaller)
		{
			return ((BooleanArgumentMarshaller) am).value;
		}

		return false;
	}

	@Override
	public String toString()
	{
		return "Boolean[called = " + count() + ", value = " + value + "]";
	}
}
