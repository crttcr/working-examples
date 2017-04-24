package args.schema;

import args.error.ArgsException;
import args.marshall.BooleanOptEvaluator;
import args.marshall.IntegerOptEvaluator;
import args.marshall.OptEvaluator;
import args.marshall.StringOptEvaluator;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Item<T>
{
	private String name;
	private OptionType type;
	private Boolean required;
	private OptEvaluator<T> eval;
	private T dv;

	private Item()
	{
	}

	public Item(String name, OptionType type, OptEvaluator<T> eval)
	{
		this.name = name;
		this.type = type;
		this.eval = eval;
		this.required = Boolean.FALSE;
		this.dv = null;
	}

	public Item(String name, OptionType type, OptEvaluator<T> eval, T defaultValue)
	{
		this.name = name;
		this.type = type;
		this.eval = eval;
		this.required = Boolean.FALSE;
		this.dv = defaultValue;
	}

	public Item(String name, OptionType type, OptEvaluator<T> eval, boolean required)
	{
		this.name = name;
		this.type = type;
		this.eval = eval;
		this.required = required;
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

	public <U> Builder<U> builder()
	{
		Builder<U> rv = new Builder<>();
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

		public Builder<T> required(boolean b)
		{
			this.instance.required = b;
			return this;
		}

		@SuppressWarnings("unchecked")
		public Item<T> build() throws ArgsException
		{
			assertValid();

			switch(instance.type)
			{
			case BOOLEAN:
				instance.eval = (OptEvaluator<T>) new BooleanOptEvaluator();
				break;
			case INTEGER:
				instance.eval = (OptEvaluator<T>) new IntegerOptEvaluator();
				break;
			case STRING:
				instance.eval = (OptEvaluator<T>) new StringOptEvaluator();
				break;

			default:
				throw new RuntimeException("Barf");
			}

			Item<T> result = instance;
			return result;
		}

		private void assertValid() throws ArgsException
		{
			if (instance.name == null)
			{
				String msg = String.format("Items require a valid name: [%s]", instance);
				throw new RuntimeException(msg);
			}
			if (instance.type == null)
			{
				String msg = String.format("Items require a valid type: [%s]", instance);
				throw new RuntimeException(msg);
			}
			if (instance.eval == null)
			{
				String msg = String.format("Items require a valid evaluator: [%s]", instance);
				throw new RuntimeException(msg);
			}
		}
	}
}
