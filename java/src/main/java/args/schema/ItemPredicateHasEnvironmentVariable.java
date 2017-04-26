package args.schema;

import java.util.Objects;

public class ItemPredicateHasEnvironmentVariable<T>
implements ItemPredicate<T>
{

	@Override
	public boolean test(Item<T> item)
	{
		Objects.requireNonNull(item);
		String s = item.getEv();
		return s != null;
	}
}
