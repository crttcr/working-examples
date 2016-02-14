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
		Objects.nonNull(path_to_file);
		
		this.path_to_file = file;
	}
	
	public ScanResult scan()
	{
		ScanResult result = new ScanResult();
		
		long count = 0;
		result.start();
		
		String file = path_to_file.toString();
		
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
			System.exit(-1);
		}
		
		// DO SCAN
		//
		
		result.end();
		result.setCount(count);
		
		return result;
	}

}
