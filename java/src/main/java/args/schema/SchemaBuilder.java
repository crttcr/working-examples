package args.schema;

import static args.error.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static args.error.ErrorCode.NO_SCHEMA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import args.error.ArgsException;
import args.marshall.BooleanOptEvaluator;
import args.marshall.IntegerOptEvaluator;
import args.marshall.OptEvaluator;
import args.marshall.StringOptEvaluator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SchemaBuilder
{
	public static final String	  DEFAULT_NAME					 = "[Name not defined]";
	private static final String  EXTENDED_FORMAT_SEPARATOR = "=";

	private Map<String, Item<?>> opts							 = new ConcurrentHashMap<>();

	private final String			  name;
	private String					  def;

	public SchemaBuilder(String name) throws ArgsException
	{
		this.name = name == null ? DEFAULT_NAME : name;
	}

	public Schema build(String def) throws ArgsException
	{
		if (def == null || def.trim().length() == 0)
		{
			throw new ArgsException(NO_SCHEMA);
		}

		opts.clear();
		this.def = def.trim();
		createDefinitions(this.def);

		return new Schema(name, opts);
	}

	private void createDefinitions(String def) throws ArgsException
	{
		if (isSimpleDefinition())
		{
			String[] defs = def.split(",");
			for (String s : defs)
			{
				String t = s.trim();
				if (t.length() == 0)
				{
					log.warn("Schema definition {} contains an empty element.", def);
					continue;
				}

				Item<?> item = getObjectFromSimpleDefinition(s);
				String option = item.getName();

				opts.put(option, item);
			}
		}
		else
		{
			getItemsFromExtendedDefinition();
		}
	}

	private List<Item<?>> getItemsFromExtendedDefinition()
	{
		List<Item<?>> rv = new ArrayList<>();

		String[] lines = def.split("\n");
		for (String line : lines)
		{
			line = line.trim();
			if (!line.contains(EXTENDED_FORMAT_SEPARATOR))
			{
				log.warn("Schema definition line {} missing separator {}.", line, EXTENDED_FORMAT_SEPARATOR);
				continue;
			}
			String[] parts = def.split(EXTENDED_FORMAT_SEPARATOR, 2);
			if (parts.length != 2)
			{
				continue;
			}

			Item<?> item = null;
			rv.add(item);
		}

		return rv;
	}

	private Item<?> getObjectFromSimpleDefinition(String s) throws ArgsException
	{
		String t = s.trim();
		int length = t.length();
		if (length == 0)
		{
			return null;
		}

		String opt = t.substring(0, 1);
		String rest = length > 1 ? t.substring(1) : null;
		return processSimpleItem(opt, rest);
	}

	private boolean isSimpleDefinition()
	{
		return !def.contains(EXTENDED_FORMAT_SEPARATOR);
	}

	private void parseSchemaElement(String element) throws ArgsException
	{
		String option = element.substring(0, 1);
		String tail = element.substring(1);
		validateSchemaElementId(option);

		if (tail.length() == 0)
		{
			OptEvaluator<Boolean> ev = new BooleanOptEvaluator();
			Item<Boolean> item = new Item<Boolean>(option, OptionType.BOOLEAN, ev);
			opts.put(option, item);
			return;
		}

		switch (tail)
		{
		case "*":
			OptEvaluator<String> stringEv = new StringOptEvaluator();
			Item<String> stringItem = new Item<String>(option, OptionType.STRING, stringEv);
			opts.put(option, stringItem);
			break;
		case "#":
			OptEvaluator<Integer> ev = new IntegerOptEvaluator();
			Item<Integer> item = new Item<Integer>(option, OptionType.INTEGER, ev);
			opts.put(option, item);
			break;
		case "##":
			break;
		case "[*]":
			break;
		default:
			throw new ArgsException(INVALID_ARGUMENT_FORMAT, option, null);
		}

	}

	private Item<?> processSimpleItem(String opt, String modifier) throws ArgsException
	{
		if (modifier == null)
		{
			OptEvaluator<Boolean> ev = new BooleanOptEvaluator();
			Item<Boolean> item = new Item<Boolean>(opt, OptionType.BOOLEAN, ev);
			return item;
		}

		switch (modifier)
		{
		case "*":
			OptEvaluator<String> stringEv = new StringOptEvaluator();
			Item<String> stringItem = new Item<String>(opt, OptionType.STRING, stringEv);
			return stringItem;
		case "#":
			OptEvaluator<Integer> ev = new IntegerOptEvaluator();
			Item<Integer> item = new Item<Integer>(opt, OptionType.INTEGER, ev);
			return item;
		case "##":
			return null;
		case "[*]":
			return null;
		default:
			throw new ArgsException(INVALID_ARGUMENT_FORMAT, opt, null);
		}

	}

	private void validateSchemaElementId(String option) throws ArgsException
	{
		// if (!Character.isLetter(option))
		// {
		// throw new ArgsException(INVALID_ARGUMENT_NAME, option, null);
		// }
	}

}
