package args.marshall;

import java.util.Iterator;

import args.error.ArgsException;
import lombok.Getter;

public class BooleanOptEvaluator extends OptEvaluatorBase<Boolean>
{
	@Getter
	private Boolean value = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		value = true;
	}

	@Override
	public String toString()
	{
		return "Boolean[called = " + count() + ", value = " + value + "]";
	}
}
