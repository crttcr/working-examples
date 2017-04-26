package args.schema;

import java.util.Objects;

public class ItemPredicateAnd<T>
implements ItemPredicate<T>
{
	private final ItemPredicate<T> a;
	private final ItemPredicate<T> b;

	public ItemPredicateAnd(ItemPredicate<T> a, ItemPredicate<T> b)
	{
		this.a = Objects.requireNonNull(a);
		this.b = Objects.requireNonNull(b);
	}


	@Override
	public boolean test(Item<T> item)
	{
		Objects.requireNonNull(item);

		return a.test(item) && b.test(item);
	}
}
