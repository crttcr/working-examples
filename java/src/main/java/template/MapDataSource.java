package template;

import java.util.HashMap;
import java.util.Map;

public class MapDataSource
{
	
	public static Map<String, Object> getData()
	{
		Map<String, Object> data = new HashMap<>();

		data.put("model", "GT4");
		data.put("user", "Damien");
		
		Map<String, Object> child = new HashMap<>();
		child.put("says", "Boo hoo");
		
		data.put("child", child);
		
		return data;
	}
}
