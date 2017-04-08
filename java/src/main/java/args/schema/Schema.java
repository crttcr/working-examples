package args.schema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.Getter;

public class Schema
{
	@Getter
	private final String name;

	private SortedMap<String, Item<?>> opts = new TreeMap<>();

	Schema(String name, Map<String, Item<?>> args )
	{
		this.name = name;
		Set<Entry<String, Item<?>>> entries = args.entrySet();

		entries.forEach( e -> {
			opts.put(e.getKey(), e.getValue());
		});
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

	@Override
	public String toString()
	{
		return "Schema [opts=" + opts + "]";
	}

}
