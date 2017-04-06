package args.schema;

import args.marshall.OptEvaluator;
import lombok.Data;

@Data
public class Item<T>
{
	final OptionType type;
	final Boolean required;
	final OptEvaluator<T> oe;

	public Item(OptionType type, OptEvaluator<T> eval)
	{
		this.type = type;
		this.required = Boolean.FALSE;
		this.oe = eval;
	}

}
