package filescan;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Objects;

/** 
 * This class does nothing more than read a file byte-by-byte (using NIO style ByteBuffer)
 * count the number of bytes encountered. It is an example of low-level file access which
 * can be compared to {@link FileScanner}
 * 
 * NOTE: This is massively faster that the {@link FileScanner} method.
 * 1.5 seconds vs 57 seconds for an 86MB file.
 * 
 * @author reid.dev
 *
 */
public class NioStyleScanner
{
	private Path path_to_file;
	
	NioStyleScanner(Path file)
	{
		this.path_to_file = Objects.requireNonNull(file);
	}
	
	public ScanResult scan()
	{
		ScanResult result = new ScanResult();
		
		long count = 0;
		
		String file = path_to_file.toString();
		ByteBuffer buffer = ByteBuffer.allocate(48);
		
		result.start();
		try (RandomAccessFile raf = new RandomAccessFile(file, "r"))
		{
			FileChannel inChannel = raf.getChannel();
			
			int bytesRead = inChannel.read(buffer);

			while (bytesRead != -1)
			{
				buffer.flip();
				
				while (buffer.hasRemaining())
				{
					@SuppressWarnings("unused")
					byte b = buffer.get();
					count++;
				}
				
				buffer.clear();
				bytesRead = inChannel.read(buffer);
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
