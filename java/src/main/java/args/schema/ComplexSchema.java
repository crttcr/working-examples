package args.schema;

import static args.error.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static args.error.ErrorCode.INVALID_ARGUMENT_NAME;
import static args.error.ErrorCode.NO_SCHEMA;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import args.error.ArgsException;
import args.marshall.BooleanOptEvaluator;
import args.marshall.IntegerOptEvaluator;
import args.marshall.OptEvaluator;
import args.marshall.StringOptEvaluator;

// FIXME: This does not do any required / defaults / description / help stuff yet.
//
public class ComplexSchema
implements Schema
{
	private Map<Character, Item<?>> opts = new ConcurrentHashMap<>();

	public ComplexSchema(String def) throws ArgsException
	{
		parse(def);
	}

	private void parse(String schema)
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

	private void parseSchemaElement(String element) throws ArgsException
	{
		char elementId = element.charAt(0);
		String tail = element.substring(1);
		validateSchemaElementId(elementId);

		if (tail.length() == 0)
		{
			OptEvaluator<Boolean> ev = new BooleanOptEvaluator();
			Item<Boolean> item = new Item<Boolean>(OptionType.BOOLEAN, ev);
			opts.put(elementId, item);
			return;
		}

		switch (tail)
		{
		case "*":
			OptEvaluator<String> stringEv = new StringOptEvaluator();
			Item<String> stringItem = new Item<String>(OptionType.STRING, stringEv);
			opts.put(elementId, stringItem);
			break;
		case "#":
			OptEvaluator<Integer> ev = new IntegerOptEvaluator();
			Item<Integer> item = new Item<Integer>(OptionType.INTEGER, ev);
			opts.put(elementId, item);
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> Item<T> getItem(char c)
	{
		return (Item<T>) opts.get(c);
	}
}
