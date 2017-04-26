package args.schema;

import java.util.function.Predicate;

public interface ItemPredicate<T>
extends Predicate<Item<T>>
{
}
