package args.marshall;

import java.util.Iterator;

import args.ArgsException;

public abstract class ArgumentMarshallerBase<T>
implements ArgumentMarshaller
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

