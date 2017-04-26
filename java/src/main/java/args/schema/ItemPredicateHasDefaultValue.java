package args.schema;

import java.util.Objects;

public class ItemPredicateHasDefaultValue<T>
implements ItemPredicate<T>
{

	@Override
	public boolean test(Item<T> item)
	{
		Objects.requireNonNull(item);
		T t = item.getDv();

		return t == null;
	}
}
