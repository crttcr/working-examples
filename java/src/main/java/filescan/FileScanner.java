package filescan;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/** 
 * This class does nothing more than read a file byte-by-byte and
 * count the number of bytes encountered. It is an example of low-level
 * file access.
 * 
 * @author reid.dev
 *
 */
public class FileScanner
{
	private Path path_to_file;
	
	FileScanner(Path file)
	{
		path_to_file = Objects.requireNonNull(file);
	}
	
	public ScanResult scan()
	{
		ScanResult result = new ScanResult();
		
		long count = 0;
		
		String file = path_to_file.toString();
		
		result.start();
		try (FileInputStream in = new FileInputStream(file))
		{
			// In this example method, nothing is done with the byte read
			// in from the file.
			//
			@SuppressWarnings("unused")
			int c;
			while ((c = in.read()) != -1)
			{
				count++;
			}
		}
		catch (IOException e)
		{
			String msg = String.format("Scan Failure: [%s]", e.getMessage());
			System.err.println(msg);
			return null;
		}
		
		// DO SCAN
		//
		
		result.end();
		result.setCount(count);
		
		return result;
	}

}
