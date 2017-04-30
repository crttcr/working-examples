package args.schema;

import static args.error.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static args.error.ErrorCode.NO_SCHEMA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import args.error.ArgsException;
import args.error.CompositeException;
import args.error.ErrorCode;
import args.error.ErrorStrategy;
import args.marshall.OptEvaluator;
import args.marshall.OptEvaluatorBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SchemaBuilder
{
	public static final String			 DEFAULT_NAME					= "[Name not defined]";
	public static final String			 EXTENDED_FORMAT_SEPARATOR	= "=";

	private Map<String, Item<?>>		 opts								= new ConcurrentHashMap<>();
	private Map<String, Map<String, String>> itemdata				= new ConcurrentHashMap<>();

	private final String					 name;
	private String							 def;
	private ErrorStrategy             es = ErrorStrategy.FAIL_FAST;
	private List<ArgsException>       errors;

	public SchemaBuilder(String name)
	{
		this.name = name == null ? DEFAULT_NAME : name;
	}

	public SchemaBuilder errorStrategy(ErrorStrategy es)
	{
		this.es = es;
		return this;
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
		char firstChar = def.charAt(0);
		ErrorStrategy declaredStrategy = ErrorStrategy.strategyForCode(firstChar);

		String shortDefs = def;
		if (declaredStrategy != null)
		{
			es = declaredStrategy;
			shortDefs = def.substring(1);
		}

		String[] defs = shortDefs.split(",");
		for (String s : defs)
		{
			String t = s.trim();
			if (t.length() == 0)
			{
				log.warn("Schema definition {} contains an empty element.", def);
				continue;
			}

			Item<?> item = getObjectFromSimpleDefinition(s);
			if (item == null)
			{
				return;
			}

			String option = item.getName();

			opts.put(option, item);
		}

		if (errors != null)
		{
			throw new CompositeException(errors);
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
			LongFormData form = LongFormData.processDefinitionLine(line);
			String key = form.getKey();

			Map<String, String> data = itemdata.get(key);
			if (data == null)
			{
				data = new ConcurrentHashMap<>();
				itemdata.put(key, data);
			}

			String field = form.getField();
			String value = form.getValue();
			data.put(field, value);
		}

		for (Entry<String, Map<String, String>> e : itemdata.entrySet())
		{
			applyBuilder(e.getKey(), e.getValue());
		}
	}


	private void applyBuilder(String key, Map<String, String> data) throws ArgsException
	{
		String typename = data.get(Item.TYPE);
		if (typename == null)
		{
			String msg = "Cannot construct an option without specifying the type.";
			throw new ArgsException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
		}

		OptionType type = OptionType.valueOf(typename.toUpperCase());
		if (type == null)
		{
			String msg = String.format("The type name [%s] is not a valid type", typename);
			throw new ArgsException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
		}

		Item.Builder<?> builder = Item.builder(data);
		Item<?> item = builder.build();
		opts.put(key, item);
	}

	private Item<?> getObjectFromSimpleDefinition(String s) throws ArgsException
	{
		String t = s.trim();
		int length = t.length();
		if (length == 0)
		{
			return null;
		}

		String  opt = t.substring(0, 1);
		String rest = length > 1 ? t.substring(1) : null;
		Item<?>  rv = null;

		try
		{
			rv = processSimpleItem(opt, rest);
		}
		catch (ArgsException e)
		{
			handleException(e);
		}

		return rv;
	}

	private boolean isSimpleDefinition()
	{
		return ! def.contains(EXTENDED_FORMAT_SEPARATOR);
	}

	private Item<?> processSimpleItem(String opt, String modifier) throws ArgsException
	{
		if (modifier == null)
		{
			OptEvaluator<Boolean> ev = OptEvaluatorBase.getEvaluatorForType(OptionType.BOOLEAN);
			Item<Boolean> item = new Item<Boolean>(opt, OptionType.BOOLEAN, ev);
			return item;
		}

		switch (modifier)
		{
		case "*":
			OptEvaluator<String> stringEval = OptEvaluatorBase.getEvaluatorForType(OptionType.STRING);
			Item<String> stringItem = new Item<String>(opt, OptionType.STRING, stringEval);
			return stringItem;
		case "#":
			OptEvaluator<Integer> ev = OptEvaluatorBase.getEvaluatorForType(OptionType.INTEGER);
			Item<Integer> iItem = new Item<Integer>(opt, OptionType.INTEGER, ev);
			return iItem;
		case "##":
			OptEvaluator<Double> dv = OptEvaluatorBase.getEvaluatorForType(OptionType.DOUBLE);
			Item<Double> dub = new Item<Double>(opt, OptionType.DOUBLE, dv);
			return dub;
		case "[*]":
			OptEvaluator<List<String>> sleval = OptEvaluatorBase.getEvaluatorForType(OptionType.STRING_LIST);
			Item<List<String>> slitem = new Item<List<String>>(opt, OptionType.STRING_LIST, sleval);
			return slitem;
		default:
			throw new ArgsException(INVALID_ARGUMENT_FORMAT, opt, null);
		}

	}

	private void handleException(ArgsException e) throws ArgsException
	{
		switch (es)
		{
		case FAIL_FAST:
			throw e;
		case FAIL_SLOW:
			errors = errors == null ? new ArrayList<>() : errors;
			errors.add(e);
		case WARN_AND_IGNORE:
			String msg = String.format("Ignoring schema definition error:  %s", e.getMessage());
			log.warn(msg);
			break;
		default:
			String err = String.format("Program error: ErrorStrategy (%s) not supported. Continuing.", es);
			log.warn(err);
			break;
		}
	}
}
