package args.marshall;

import java.util.Iterator;

import args.ArgsException;

public class BooleanArgumentMarshaller implements ArgumentMarshaller
{
	private boolean value = false;

	@Override
	public void set(Iterator<String> currentArgument) throws ArgsException
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
}
