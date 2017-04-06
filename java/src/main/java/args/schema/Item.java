package args.schema;

import args.marshall.OptEvaluator;
import lombok.Data;

@Data
public class Item<T>
{
	final OptionType type;
	final Boolean required;
	final OptEvaluator<T> eval;
	final T dv;

	public Item(OptionType type, OptEvaluator<T> eval)
	{
		this.type = type;
		this.eval = eval;
		this.required = Boolean.FALSE;
		this.dv = null;
	}

	public Item(OptionType type, OptEvaluator<T> eval, T defaultValue)
	{
		this.type = type;
		this.eval = eval;
		this.required = Boolean.FALSE;
		this.dv = defaultValue;
	}

	public Item(OptionType type, OptEvaluator<T> eval, boolean required)
	{
		this.type = type;
		this.eval = eval;
		this.required = required;
		this.dv = null;
	}


}
