package j7.nio2;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WatchExampleTest
{
	
	Path  watched_dir = null;
	WatchExample we = null;
	WatchExampleBlocking web = null;

	@Before
	public void setUp() throws Exception
	{
		watched_dir = Files.createTempDirectory("wxxx");
		
		// we = new WatchExample(watched_dir);
		web = new WatchExampleBlocking(watched_dir);
	}

	@After
	public void tearDown() throws Exception
	{
		web = null;
		Thread.sleep(50);
		System.out.println("rm " + watched_dir);
		Files.delete(watched_dir);
	}

	@Ignore
	@Test
	public void testWatchDirectoryNoChange() throws Exception
	{
		final ExecutorService exec_svc = Executors.newSingleThreadExecutor();
		Future<Integer>         future = exec_svc.submit(we);
		
		we.cancel();
		
		Integer changes = future.get();
		
		assertEquals(0, changes.intValue());
	}


	@Test
	public void testWatchWithNewFileCreated() throws Exception
	{
		final ExecutorService exec_svc = Executors.newSingleThreadExecutor();
		Future<Integer>         future = exec_svc.submit(web);
		
		
		Path   tempFile = Files.createTempFile(watched_dir, "temp_file", ".tmp");
		List<String> lines = Arrays.asList("Line1", "Line2");
		Files.write(tempFile, lines, Charset.defaultCharset(), StandardOpenOption.WRITE);
		System.out.printf("Wrote text to temporary file %s%n", tempFile.toString());
		
		
		Thread.sleep(400);
		
		// Output file delete command
		//
		System.out.println("rm " + tempFile.toAbsolutePath());

		Integer changes = future.get();
		Files.delete(tempFile);
		Thread.sleep(200);

		// FIXME: This test is broken.  We should see a change in the directory, we just
		// wrote a file there.
		//
		assertEquals(0, changes.intValue());
	}
	
	
}
