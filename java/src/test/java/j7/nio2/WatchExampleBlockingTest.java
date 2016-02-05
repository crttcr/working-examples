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
import org.junit.Test;

public class WatchExampleBlockingTest
{
	Path         watched_dir = null;
	WatchExampleBlocking web = null;

	@Before
	public void setUp() throws Exception
	{
		watched_dir = Files.createTempDirectory("wxxx");
		
		System.out.println("Watch DIR:  " + watched_dir);
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

	@Test
	public void testWatchWithNewFileCreated() throws Exception
	{
		final ExecutorService exec_svc = Executors.newSingleThreadExecutor();
		Future<Integer>         future = exec_svc.submit(web);
		
		Path   tempFile = Files.createTempFile(watched_dir, "fj", ".tmp");
		List<String> lines = Arrays.asList("Line1", "Line2", "Line3");
		Files.write(tempFile, lines, Charset.defaultCharset(), StandardOpenOption.WRITE);
		System.out.printf("Wrote to : %s\n", tempFile.toString());
		
		
		// Output file delete command
		//
		System.out.println("rm " + tempFile.toAbsolutePath());

		// Wait for blocking Watcher to return
		//
		Integer changes = future.get();
		
		Files.delete(tempFile);

		assertEquals(1, changes.intValue());
	}
	
	
}
