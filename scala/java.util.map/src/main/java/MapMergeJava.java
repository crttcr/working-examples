import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapMergeJava
{
	public static void main(String[] args)
	{
		MapMergeJava x = new MapMergeJava();

		x.merge_value_exists__fn_returns_int();
		x.merge_value_exists__replacement_value_null();
		x.merge_value_exists__fn_returns_null();
		x.merge_value_exists__fn_throws_exception();
		x.merge_value_missing_fn_returns_int();
		x.merge_value_missing_fn_returns_null();
		x.merge_value_missing_fn_throws_exception();
	}

	public static void show(String prefix, Map<String, Integer> map)
	{
		System.out.println(prefix +  map);
	}
	public static void before(Map<String, Integer> map)
	{
		show("Before merge: ", map);
	}
	public static void after (Map<String, Integer> map)
	{
		show("After  merge: ", map);
	}

	private void merge_value_exists__fn_returns_int()
	{
		String key = "Apple";
		Map<String, Integer> map = new ConcurrentHashMap<>();

		map.put(key, 10);

		System.out.println("merge_value_exists__fn_returns_int");
		before(map);
		map.merge(key, 2, (a, b) -> a + b );
		after(map);
		System.out.println("");
	}

	private void merge_value_exists__replacement_value_null()
	{
		String key = "Apple";
		Map<String, Integer> map = new ConcurrentHashMap<>();

		map.put(key, 10);

		System.out.println("merge_value_exists__replacement_value_null");
		before(map);
		try
		{
			map.merge(key, null, (a, b) -> a + b );
		}
		catch(Exception e)
		{
			System.out.println("Caught Exception: " + e.getLocalizedMessage());
			System.out.println("NOTE: None of the parameters to merge() can be null!");
		}
		after(map);
		System.out.println("");	}

	private void merge_value_exists__fn_returns_null()
	{
		String key = "Apple";
		Map<String, Integer> map = new ConcurrentHashMap<>();

		map.put(key, 10);

		System.out.println("merge_value_exists__fn_returns_null");
		before(map);
		map.merge(key, 2, (a, b) -> null );
		after(map);
		System.out.println("");	}

	private void merge_value_exists__fn_throws_exception()
	{
	}

	private void merge_value_missing_fn_returns_int()
	{
	}

	private void merge_value_missing_fn_returns_null()
	{
	}

	private void merge_value_missing_fn_throws_exception()
	{
	}


}
