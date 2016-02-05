package j7.nio2;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Callable;

import net.jcip.annotations.ThreadSafe;



@ThreadSafe
public class WatchExampleBlocking
	implements Callable<Integer>
{
	private final Path dir;
	private       WatchService watcher;
	private       WatchKey key;
	
	public WatchExampleBlocking(Path directory)
	{
		this.dir = directory;

		FileSystem        fs = FileSystems.getDefault();
		try
		{
			watcher = fs.newWatchService();
			key     = dir.register(watcher, ENTRY_MODIFY);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	@Override
	public Integer call()
	{
		int events_detected = 0;
		
		try
		{
			key = watcher.take();

			System.out.println("Return from take(). Key is " + key);

			for (WatchEvent<?> event : key.pollEvents())
			{
				System.out.println("Process event: " + event.toString());
				if (event.kind() == ENTRY_MODIFY)
				{
					System.out.println("directory modified");
					events_detected++;
				}
			}
		}
		catch (InterruptedException e)
		{
			System.err.println("Watching directory interruped: " + e.getMessage());
			return -1;
		}
		finally
		{
			try
			{
				if (watcher != null) 
					watcher.close();
			}
			catch (IOException e)
			{
				String msg = "Failed to close directory watcher.";
				System.out.println(msg + ": " + e.getMessage());
			}
			
		}
		
		return events_detected;
	}
}
