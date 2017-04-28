package args.schema;

import java.util.Map;
import java.util.Map.Entry;

import args.error.ArgsException;
import args.error.ErrorCode;
import args.marshall.BooleanOptEvaluator;
import args.marshall.DoubleOptEvaluator;
import args.marshall.IntegerOptEvaluator;
import args.marshall.OptEvaluator;
import args.marshall.OptEvaluatorBase;
import args.marshall.StringListOptEvaluator;
import args.marshall.StringOptEvaluator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Item<T>
{
	public final static String	NAME			= "name";
	public final static String	TYPE			= "type";
	public final static String	ENV_VAR		= "ev";
	public final static String	DESCRIPTION	= "description";
	public final static String	DEFAULT		= "dv";
	public final static String	REQUIRED		= "required";

	private String					name;
	private OptionType			type;
	private OptEvaluator<T>		eval;
	private Boolean				required;
	private String					description;
	private String					ev;
	private T						dv;

	private Item()
	{
	}

	public Item(String name, OptionType type, OptEvaluator<T> eval)
	{
		this.name = name;
		this.type = type;
		this.eval = eval;
		this.required = Boolean.FALSE;
		this.description = null;
		this.ev = null;
		this.dv = null;
	}

	public Item(String name, OptionType type, OptEvaluator<T> eval, T defaultValue, String description, String ev)
	{
		this.name = name;
		this.type = type;
		this.eval = eval;
		this.required = Boolean.FALSE;
		this.description = description;
		this.ev = ev;
		this.dv = defaultValue;
	}

	public Item(String name, OptionType type, OptEvaluator<T> eval, boolean required, String description, String ev)
	{
		this.name = name;
		this.type = type;
		this.eval = eval;
		this.required = required;
		this.description = description;
		this.ev = ev;
		this.dv = null;
	}

	public Item(String name, OptionType type, boolean required, OptEvaluator<T> eval, T dv)
	{
		this.name = name;
		this.type = type;
		this.eval = eval;
		this.required = required;
		this.dv = null;
	}

	public static <U> Builder<U> builder()
	{
		Builder<U> rv = new Builder<>();
		return rv;
	}

	public static <U> Builder<U> builder(Map<String, String> args) throws ArgsException
	{
		Builder<U> rv = new Builder<>();

		if (args == null)
		{
			return rv;
		}

		for (Entry<String, String> e: args.entrySet())
		{
			String k = e.getKey();
			String v = e.getValue();
			Builder.callBuilderMethod(rv, k, v);
		}

		OptionType type = rv.type();
		if (type != null)
		{
			rv.eval(OptEvaluatorBase.getEvaluatorForType(type));
		}

		return rv;
	}

	public static class Builder<T>
	{
		// If you were to make this field final, you can't reuse the builder with a new object.
		// That would permit you to potentially leak changes to the object if the caller holds on
		// to your builder.
		//
		private final Item<T> instance;

		public Builder()
		{
			instance = new Item<T>();
		}

		public OptionType type()
		{
			return this.instance.getType();
		}

		public Builder<T> name(String name)
		{
			this.instance.name = name;
			return this;
		}

		public Builder<T> type(OptionType type)
		{
			this.instance.type = type;
			return this;
		}

		public Builder<T> eval(OptEvaluator<T> eval)
		{
			this.instance.eval = eval;
			return this;
		}

		public Builder<T> description(String description)
		{
			this.instance.description = description;
			return this;
		}

		public Builder<T> ev(String ev)
		{
			this.instance.ev = ev;
			return this;
		}

		public Builder<T> dv(T dv)
		{
			this.instance.dv = dv;
			return this;
		}

		public Builder<T> required(boolean b)
		{
			this.instance.required = b;
			return this;
		}

		@SuppressWarnings("unchecked")
		public Item<T> build() throws ArgsException
		{
			assertValid();

			switch (instance.type)
			{
			case BOOLEAN:
				instance.eval = (OptEvaluator<T>) new BooleanOptEvaluator();
				break;
			case INTEGER:
				instance.eval = (OptEvaluator<T>) new IntegerOptEvaluator();
				break;
			case DOUBLE:
				instance.eval = (OptEvaluator<T>) new DoubleOptEvaluator();
				break;
			case STRING:
				instance.eval = (OptEvaluator<T>) new StringOptEvaluator();
				break;
			case STRING_LIST:
				instance.eval = (OptEvaluator<T>) new StringListOptEvaluator();
				break;

			default:
				String msg = String.format("Unable to handle OptionType [%s] in builder", instance.type);
				throw new ArgsException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
			}

			Item<T> result = instance;
			return result;
		}

		private void assertValid() throws ArgsException
		{
			if (instance.name == null)
			{
				String msg = String.format("Items require a valid name: [%s]", instance);
				throw new ArgsException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
			}
			if (instance.type == null)
			{
				String msg = String.format("Items require a valid type: [%s]", instance);
				throw new ArgsException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
			}
			if (instance.eval == null)
			{
				String msg = String.format("Items require a valid evaluator: [%s]", instance);
				throw new ArgsException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
			}
		}

		public static void callBuilderMethod(Item.Builder<?> builder, String field, String value) throws ArgsException
		{
			switch (field)
			{
			case NAME:
				builder.name(value);
				break;
			case TYPE:
				OptionType type = OptionType.valueOf(value.toUpperCase());
				builder.type(type);
				break;
			case ENV_VAR:
				builder.ev(value);
				break;
			case DESCRIPTION:
				builder.description(value);
				break;
			case DEFAULT:
				Object dv = null;
				// FIXME: builder.dv(dv);
				break;
			case REQUIRED:
				boolean required = Boolean.valueOf(value);
				builder.required(required);
				break;
			default:
				throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT);
			}
		}
	}
}