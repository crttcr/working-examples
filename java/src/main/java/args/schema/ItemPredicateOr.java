package args.schema;

import java.util.Objects;

public class ItemPredicateOr<T>
implements ItemPredicate<T>
{
	private final ItemPredicate<T> a;
	private final ItemPredicate<T> b;

	public ItemPredicateOr(ItemPredicate<T> a, ItemPredicate<T> b)
	{
		this.a = Objects.requireNonNull(a);
		this.b = Objects.requireNonNull(b);
	}


	@Override
	public boolean test(Item<T> item)
	{
		Objects.requireNonNull(item);

		return a.test(item) || b.test(item);
	}
}
