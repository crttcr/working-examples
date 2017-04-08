package args.schema;

import args.marshall.OptEvaluator;
import lombok.Data;

@Data
public class Item<T>
{
	private final String name;
	private final OptionType type;
	private final Boolean required;
	private final OptEvaluator<T> eval;
	private final T dv;

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


}
