package args;

import static args.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static args.ErrorCode.INVALID_ARGUMENT_NAME;
import static args.ErrorCode.UNEXPECTED_ARGUMENT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import args.marshall.ArgumentMarshaller;
import args.marshall.BooleanArgumentMarshaller;
import args.marshall.IntegerArgumentMarshaller;
import args.marshall.StringArgumentMarshaller;

public class Args
{
	private Map<Character, ArgumentMarshaller> marshallers = new ConcurrentHashMap<>();
	private Set<Character> argsFound = new HashSet<Character>();
	private ListIterator<String> currentArgument;

	public Args(String schema, String[] args)
		throws ArgsException
	{
		parseSchema(schema);
		parseCommandLine(Arrays.asList(args));
	}

	private void parseSchema(String schema)
		throws ArgsException
	{
		for (String s: schema.split(","))
		{
			if (s.length() > 0)
			{
				String t = s.trim();
				parseSchemaElement(t);
			}
		}
	}

	private void parseCommandLine(List<String> args)
		throws ArgsException
	{
		for (currentArgument = args.listIterator(); currentArgument.hasNext(); )
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
		ArgumentMarshaller m = marshallers.get(c);
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
			e.setErrorArgumentId(c);
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
			marshallers.put(elementId, new BooleanArgumentMarshaller());
			return;
		}

		switch (tail)
		{
		case "*":
			marshallers.put(elementId, new StringArgumentMarshaller());
			break;
		case "#":
			marshallers.put(elementId, new IntegerArgumentMarshaller());
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
		ArgumentMarshaller m = marshallers.get(arg);
		return BooleanArgumentMarshaller.getValue(m);
	}

	public String getString(char arg)
	{
		ArgumentMarshaller m = marshallers.get(arg);
		return StringArgumentMarshaller.getValue(m);
	}

	public int getInteger(char arg)
	{
		ArgumentMarshaller m = marshallers.get(arg);
		return IntegerArgumentMarshaller.getValue(m);
	}

	public boolean has(char arg)
	{
		return argsFound.contains(arg);
	}

	public int nextArgument()
	{
		return currentArgument.nextIndex();
	}
}
