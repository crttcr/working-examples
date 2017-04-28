package args.marshall;

import static args.error.ErrorCode.MISSING_STRING_LIST;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import args.error.ArgsException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper=false)
public class StringListOptEvaluator extends OptEvaluatorBase<List<String>>
{
	@Getter
	private List<String> value = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			String s = currentArgument.next();
			if (value == null)
			{
				value = new ArrayList<String>();
			}

			value.add(s);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_STRING_LIST);
		}
	}

	@Override
	public String toString()
	{
		return "StringListEval[called = " + count() + ", value = " + value + "]";
	}
}
