package template;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
}
