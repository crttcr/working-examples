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
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.ThreadSafe;



@ThreadSafe
public class WatchExample
	implements Callable<Integer>
{
	private final Path dir;
	private volatile boolean cancelled = false;
	
	public WatchExample(Path directory)
	{
		this.dir = directory;
	}
	
	public void cancel()
	{
		cancelled = true;
	}

	@Override
	public Integer call()
	{
		int events_detected = 0;
		WatchService watcher = null;
		
		try
		{
			FileSystem        fs = FileSystems.getDefault();
			watcher              = fs.newWatchService();
			WatchKey         key = dir.register(watcher, ENTRY_MODIFY);
			
			while (! cancelled)
			{
				// NOTE: It's also possible to use this
				//
				// key = watcher.take(); 
				//
				// which will block until an event happens. For this application, polling allows
				// the ability to check for a shutdown signal. Since Java only offers cooperative
				// mechanisms to cancel a task, we timeout to recheck the loop condition.
				//
				key = watcher.poll(50L, TimeUnit.MILLISECONDS);
				System.out.println("Return from poll. Key is " + key);
				if (key == null)
					continue;

				System.out.println("Valid key" + key.toString());
				for (WatchEvent<?> event : key.pollEvents())
				{
					System.out.println("Process event: " + event.toString());
					if (event.kind() == ENTRY_MODIFY)
					{
						System.out.println("directory modified");
						events_detected++;
					}
				}
				
				key.reset();
			}
		}
		catch (IOException | InterruptedException e)
		{
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
