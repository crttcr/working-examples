package args;

import static args.error.ErrorCode.NO_SCHEMA;
import static args.error.ErrorCode.NULL_ARGUMENT_ARRAY;
import static args.error.ErrorCode.UNEXPECTED_OPTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import args.error.ArgsException;
import args.error.ErrorCode;
import args.marshall.OptEvaluator;
import args.schema.Item;
import args.schema.Schema;

/**
 * Argument and options command line processor inspired by
 * Robert C. Martin's Clean Code, chapter 14.
 *
 * However, this class no longer is responsible for parsing
 * the definition of the program options and processing the command line.
 *
 * Option definition is handled by the classes in the schema package.
 */
public class Args
{
	private Set<String> optionsFound = new HashSet<String>();
	private ListIterator<String> argumentIterator;
	private final List<String> arguments = new ArrayList<>();
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

		for (argumentIterator = argList.listIterator(); argumentIterator.hasNext(); )
		{
			String argString = argumentIterator.next();
			if (argString.startsWith("--"))
			{
				String rest = argString.substring(2);
				parseLongFormOption(rest, argumentIterator);
			}
			else if (argString.startsWith("-"))
			{
				String rest = argString.substring(1);
				handleShortFormOption(rest, argumentIterator);
			}
			else
			{
				arguments.add(argString);
			}
		}
	}

	private void parseLongFormOption(String option, ListIterator<String> args)
	{
		// TODO Auto-generated method stub

	}


	private void handleShortFormOption(String stripped, ListIterator<String> args)
		throws ArgsException
	{
		if (stripped.length() < 1)
		{
			throw new ArgsException(ErrorCode.MISSING_OPTION_NAME);
		}

		String option = stripped.substring(0, 1);

		Item<?> item = schema.getItem(option);

		if (item == null)
		{
			throw new ArgsException(UNEXPECTED_OPTION, option, null);
		}

		OptEvaluator<?> eval = item.getEval();
		optionsFound.add(option);
		try
		{
			eval.set(argumentIterator);
		}
		catch (ArgsException e)
		{
			e.setOption(option);
			throw e;
		}
	}

	public <T> T getValue(String option) {
		Item<T> item = schema.getItem(option);
		OptEvaluator<T> eval = item.getEval();
		T rv = eval.getValue();
		return rv;
	}

	public boolean has(String opt)
	{
		if (opt == null)
		{
			return false;
		}

		return optionsFound.contains(opt);
	}

	public int argumentCount()
	{
		return argumentIterator.nextIndex();
	}

	public String getArgument(int i)
	{
		if (i < 0 || i >= arguments.size())
		{
			return null;
		}

		return arguments.get(i);
	}
}
