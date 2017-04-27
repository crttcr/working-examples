package args;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArgUtil
{
	public static String readFromResourceFile(String dir, String file)
	{
		Path path = Paths.get(dir, file);


		if (! Files.exists(path))
		{
			fail("Missing test resource file: " + path);
		}

		byte[] bytes = {};
		try
		{
			bytes = Files.readAllBytes(path);
		}
		catch (IOException e)
		{
			fail("Error reading test resource file: " + path + ". Error: " + e.getLocalizedMessage());
		}
		String content = new String(bytes);

		return content;
	}

}
