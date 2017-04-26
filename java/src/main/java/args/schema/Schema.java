package args.schema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.Getter;

public class Schema
{
	@Getter
	private final String name;

	private SortedMap<String, Item<?>> opts = new TreeMap<>();

	Schema(String name, Map<String, Item<?>> defs )
	{
		this.name = Objects.requireNonNull(name);
		Objects.requireNonNull(defs);

		Set<Entry<String, Item<?>>> entries = defs.entrySet();

		for (Entry<String, Item<?>> e : entries)
		{
			String k = e.getKey();
			Item<?> v = e.getValue();
			opts.put(k, v);
		}

	}

	public boolean allRequiredOptionsHaveValues() {

		for (Item<?> item:  opts.values()) {
			if (! item.getRequired())
			{
				continue;
			}

			if (item.getEval().getValue() == null)
			{
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public <T> Item<T> getItem(String option)
	{
		return (Item<T>) opts.get(option);
	}

	public List<String> getOptions()
	{
		List<String> rv = new ArrayList<>();
		Set<String> keys = opts.keySet();
		Iterator<String> it = keys.iterator();

		while (it.hasNext())
		{
			rv.add(it.next());
		}

		return rv;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Item> requiredWithEnvironments()
	{
		ItemPredicate<?> r = new ItemPredicateRequired<>();
		ItemPredicate<?> e = new ItemPredicateHasEnvironmentVariable<>();
		ItemPredicate<?> p = new ItemPredicateAnd(r, e);

		List<Item> rv = find(p);

		return rv;
	}

	@Override
	public String toString()
	{
		return "Schema [opts=" + opts + "]";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Item> find(ItemPredicate<?> predicate) {

		List<Item> list = new ArrayList<>();

		for (Item item:  opts.values())
		{
			if (predicate == null || predicate.test(item))
			{
				list.add(item);
			}
		}

		return list;
	}
}