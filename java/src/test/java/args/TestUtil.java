package args;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class TestUtil
{
	public static ListIterator<String> getListIterator(String...strings)
	{
		List<String> list = Arrays.asList(strings);
		ListIterator<String> it = list.listIterator();
		return it;
	}

	public static String readFromTestResourceFile(String file)
	{
		Path path = Paths.get("src/test/resources/args/defs", file);

		System.out.println(path.toAbsolutePath().toString());

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
