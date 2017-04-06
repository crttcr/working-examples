package args;

import static args.error.ErrorCode.NO_SCHEMA;
import static args.error.ErrorCode.NULL_ARGUMENT_ARRAY;
import static args.error.ErrorCode.UNEXPECTED_ARGUMENT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import args.error.ArgsException;
import args.marshall.OptEvaluator;
import args.schema.Item;
import args.schema.Schema;

/**
 * Example application to run the Args command line processor
 * inspired by Robert C. Martin's Clean Code, chapter 14.
 */
public class Args
{
	private Set<Character> argsFound = new HashSet<Character>();
	private ListIterator<String> currentArgument;
	private final Schema schema;

	public Args(Schema schema, String[] args)
		throws ArgsException
	{
		if (schema == null)
		{
			throw new ArgsException(NO_SCHEMA);
		}
		this.schema = schema;
		parseCommandLine(args);
		validateCommandLine();
	}


	private void validateCommandLine()
	{
		// TODO Auto-generated method stub
	}

	private void parseCommandLine(String[] args)
		throws ArgsException
	{
		if (args == null)
		{
			throw new ArgsException(NULL_ARGUMENT_ARRAY);
		}

		List<String> argList = Arrays.asList(args);

		for (currentArgument = argList.listIterator(); currentArgument.hasNext(); )
		{
			String argString = currentArgument.next();
			if (argString.startsWith("-"))
			{
				String rest = argString.substring(1);
				parseArgumentCharacters(rest);
			}
			else
			{
				// Current implementation forces options to appear before arguments.
				// As soon as the first non option appears, the processing is done.
				//
				currentArgument.previous();
				break;
			}
		}
	}

	private void parseArgumentCharacters(String chars)
		throws ArgsException
	{
		for (int i = 0; i < chars.length(); i++)
		{
			char c = chars.charAt(i);
			parseArgumentCharacter(c);
		}
	}

	private void parseArgumentCharacter(char c)
		throws ArgsException
	{
		Item<?> item = schema.getItem(c);

		if (item == null)
		{
			throw new ArgsException(UNEXPECTED_ARGUMENT, c, null);
		}

		OptEvaluator<?> eval = item.getOe();
		argsFound.add(c);
		try
		{
			eval.set(currentArgument);
		}
		catch (ArgsException e)
		{
			e.setErrorId(c);
			throw e;
		}
	}

	public <T> T getValue(char c) {
		Item<T> item = schema.getItem(c);
		OptEvaluator<T> eval = item.getOe();
		T rv = eval.getValue();
		return rv;
	}

	public boolean has(char c)
	{
		return argsFound.contains(c);
	}

	public int nextArgument()
	{
		return currentArgument.nextIndex();
	}

	public String getArgument(int i)
	{
		// FIXME: Need to support positional arguments in addition to command line options
		//
		return null;
	}
}
