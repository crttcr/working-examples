package j7.nio2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PathExampleTest
{
	PathExample pe = null;

	@Before
	public void setUp() throws Exception
	{
		pe = new PathExample();
	}

	@After
	public void tearDown() throws Exception
	{
		pe = null;
	}

	@Test
	public void testBuildDirectoryMap()
	{
		Map<String, Path> map = pe.buildDirectoryMap("/usr/bin");
		
		
		assertNotNull(map);

		assertTrue(map.containsKey("touch"));
		
		// No need to see this verbose output every time. Uncomment if you want to see where 
		// executable files are really located.
		//
//		for (Entry<String, Path> entry : map.entrySet())
//		{
//			String   key = entry.getKey();
//			String value = entry.getValue().toString();
//			String msg = String.format("Executable [%s] located [%s]",  key, value);
//			System.out.println(msg);
//		}
	}

	@Test
	public void testTestFileExists()
	{
		Path path = pe.resourceLocationForTest();
		
		assertNotNull(path);
		assertTrue(Files.exists(path));
		
		// System.out.println("Current location is: " + path.toString());
	}

	@Test
	public void testFileTreeWalk()
	{
		// Arrange
		//
		
		// Act
		//
		Map<Path, Long> map = pe.subtreeFileSizes("/usr/local/bin");
		
		// Assert
		//
		assertNotNull(map);
		
		// System.out.println(map.toString());
	}

	@Test
	public void testReadAllBytesOfAFile()
	{
		String s = pe.readAllBytesOfAFile();
		
		assertNotNull(s);
		System.out.println(s);
	}

	@Test
	public void testReadAllLinesOfAFile()
	{
		boolean ok = pe.readAllLines();
		
		assertTrue(ok);
	}

	@Test
	public void testZipDetails()
	{
		String[] array = pe.getZipDetails();
		
		assertNotNull(array);
		
		for (String s : array)
		{
			assertNotNull(s);
			System.out.println(s);
		}
	}


}
