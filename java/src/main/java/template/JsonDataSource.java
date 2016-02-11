package template;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// The JSON consists of an array of property objects, each of
// which has attributes like 'Name', 'Type', 'IsRequired', ...
//

public class JsonDataSource
{
	// Returns JSON string as the data source
	//
	public static String getData()
	{
		String result = null;
		Path path = Paths.get("src/main/resources/json/properties.json");
		
		System.out.println(path.normalize().toAbsolutePath());
		try
		{
			byte[] bytes = Files.readAllBytes(path);
			result = new String(bytes, StandardCharsets.UTF_8);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		return result;
	}

	public static JsonNode[] convertJsonData(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode root = null;
		try
		{
			root = mapper.readTree(json);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			fail("Could not process json");
		}
		
		JsonNode props = root.get("properties");
		
		if (! props.isArray())
			fail("Expected an array from JSON data, received: " + props.toString());
		
		
		List<JsonNode> list = new ArrayList<>();
		
		for (final JsonNode item : props)
		{
			list.add(item);
//			System.out.println(item.toString());
		}
		
		return list.toArray(new JsonNode[0]);
	}
	
}
