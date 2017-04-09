package args.schema;

import static args.error.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static args.error.ErrorCode.NO_SCHEMA;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import args.error.ArgsException;
import args.error.ErrorCode;
import args.marshall.BooleanOptEvaluator;
import args.marshall.IntegerOptEvaluator;
import args.marshall.OptEvaluator;
import args.marshall.StringOptEvaluator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SchemaBuilder
{
	public static final String			 DEFAULT_NAME					= "[Name not defined]";
	public static final String			 EXTENDED_FORMAT_SEPARATOR	= "=";

	private Map<String, Item<?>>		 opts								= new ConcurrentHashMap<>();
	private Map<String, Item.Builder<?>> builders					= new ConcurrentHashMap<>();

	private final String					 name;
	private String							 def;

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
		createDefinitions();

		return new Schema(name, opts);
	}

	private void createDefinitions() throws ArgsException
	{
		if (isSimpleDefinition())
		{
			buildItemsFromShortDefinition();
		}
		else
		{
			buildItemsFromExtendedDefinition();
		}
	}

	private void buildItemsFromShortDefinition() throws ArgsException
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

	private void buildItemsFromExtendedDefinition() throws ArgsException
	{
		String[] lines = def.split("\n");
		for (String line : lines)
		{
			line = line.trim();
			if (line.length() == 0 || line.startsWith("#"))
			{
				continue;
			}

			if (!line.contains(EXTENDED_FORMAT_SEPARATOR))
			{
				log.warn("Schema definition line {} missing separator {}.", line, EXTENDED_FORMAT_SEPARATOR);
				continue;
			}
			LongFormData data = LongFormData.processDefinitionLine(line);
			String key = data.getKey();

			Item.Builder<?> builder = builders.get(key);
			if (builder == null)
			{
				builder = new Item.Builder<>();
				builders.put(key, builder);
			}

			callBuilderMethod(builder, data);
		}

		for (Item.Builder<?> builder : builders.values())
		{
			Item<?> item = builder.build();
			String key = item.getName();
			opts.put(key, item);
		}
	}

	private void callBuilderMethod(Item.Builder<?> builder, LongFormData data) throws ArgsException
	{
		String field = data.getField();
		String value = data.getValue();

		switch (field)
		{
		case "name":
			builder.name(value);
			break;
		case "type":
			OptionType type = OptionType.valueOf(value.toUpperCase());
			builder.type(type);
			break;
		case "dv":
			Object dv = null;
			// FIXME: builder.dv(dv);
			break;
		case "required":
			boolean required = Boolean.valueOf(value);
			builder.required(required);
			break;
		default:
			throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT);
		}

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
		return ! def.contains(EXTENDED_FORMAT_SEPARATOR);
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
