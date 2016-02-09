package template.velocity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HtmlTableTemplate
{
	public static String getTemplateText()
	{
		String result = null;
		Path path = Paths.get("src/main/resources/templates/html_table.vts");
		
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
