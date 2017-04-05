package args;

import static args.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static args.ErrorCode.INVALID_ARGUMENT_NAME;
import static args.ErrorCode.NO_SCHEMA;
import static args.ErrorCode.NULL_ARGUMENT_ARRAY;
import static args.ErrorCode.UNEXPECTED_ARGUMENT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import args.marshall.OptionEvaluator;
import args.marshall.BooleanOptionEvaluator;
import args.marshall.IntegerOptionEvaluator;
import args.marshall.StringOptionEvaluator;

public class Args
{
	private Map<Character, OptionEvaluator> evaluators = new ConcurrentHashMap<>();
	private Set<Character> argsFound = new HashSet<Character>();
	private ListIterator<String> currentArgument;

	public Args(String schema, String[] args)
		throws ArgsException
	{
		parseSchema(schema);
		parseCommandLine(args);
	}

	private void parseSchema(String schema)
		throws ArgsException
	{
		if (schema == null)
		{
			throw new ArgsException(NO_SCHEMA);
		}

		for (String s: schema.split(","))
		{
			if (s.length() > 0)
			{
				String t = s.trim();
				parseSchemaElement(t);
			}
		}
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
		OptionEvaluator m = evaluators.get(c);
		if (m == null)
		{
			throw new ArgsException(UNEXPECTED_ARGUMENT, c, null);
		}

		argsFound.add(c);
		try
		{
			m.set(currentArgument);
		}
		catch (ArgsException e)
		{
			e.setErrorId(c);
			throw e;
		}
	}

	private void parseSchemaElement(String element) throws ArgsException
	{
		char elementId = element.charAt(0);
		String tail = element.substring(1);
		validateSchemaElementId(elementId);

		if (tail.length() == 0)
		{
			evaluators.put(elementId, new BooleanOptionEvaluator());
			return;
		}

		switch (tail)
		{
		case "*":
			evaluators.put(elementId, new StringOptionEvaluator());
			break;
		case "#":
			evaluators.put(elementId, new IntegerOptionEvaluator());
			break;
		case "##":
			break;
		case "[*]":
			break;
		default:
			throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, null);
		}

	}

	private void validateSchemaElementId(char elementId) throws ArgsException
	{
		if (! Character.isLetter(elementId))
		{
			throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
		}
	}

	public boolean getBoolean(char arg)
	{
		OptionEvaluator m = evaluators.get(arg);
		return BooleanOptionEvaluator.getValue(m);
	}

	public String getString(char arg)
	{
		OptionEvaluator m = evaluators.get(arg);
		return StringOptionEvaluator.getValue(m);
	}

	public int getInteger(char arg)
	{
		OptionEvaluator m = evaluators.get(arg);
		return IntegerOptionEvaluator.getValue(m);
	}

	public boolean has(char arg)
	{
		return argsFound.contains(arg);
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
