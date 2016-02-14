package filescan;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NioStyleScannerTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testBigScan()
	{
		Path path = Paths.get("/Users/reid.dev/Desktop/GraphClass/neo4j-community_windows-x64_2_2_3.exe");
		if (! Files.exists(path))
		{
			String msg = "This simple-minded test looks for a large, hard-coded file does not exist in the current environment.";
			System.out.println(msg);
			return;
		}
		
		NioStyleScanner scanner = new NioStyleScanner(path);
		ScanResult         scan = scanner.scan();
		
		System.out.println(scan.toString());
		
		assertTrue(scan.duration().getNano() > 0);
	}

	@Test
	public void testMediumScan()
	{
		Path path = Paths.get("/Users/reid.dev/Desktop/Neo/neo4j-community-2.2.3/LICENSES.txt");
		if (! Files.exists(path))
		{
			String msg = "This simple-minded test looks for a medium-sized, hard-coded file does not exist in the current environment.";
			System.out.println(msg);
			return;
		}
		
		NioStyleScanner scanner = new NioStyleScanner(path);
		ScanResult         scan = scanner.scan();
		
		System.out.println(scan.toString());
		
		assertTrue(scan.duration().getNano() > 0);
	}

	@Test
	public void testBogusPath()
	{
		Path path = Paths.get("/Users/phantom/Horsefeathers/chimera.txt");
		
		NioStyleScanner scanner = new NioStyleScanner(path);
		ScanResult         scan = scanner.scan();
		
		assertNull(scan);
	}

}
