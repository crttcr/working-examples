package filescan;

import java.time.Duration;
import java.time.LocalDateTime;

public class ScanResult
{
	private LocalDateTime start;
	private LocalDateTime end;
	private long count;
	
	public void start()
	{
		start = LocalDateTime.now();
	}

	public void end()
	{
		end = LocalDateTime.now();
	}
	
	public Duration duration()
	{
		Duration d = Duration.between(start, end);
		
		return d;
	}

	public void setCount(long count)
	{
		this.count = count;
	}
	
	public String toString()
	{
		String msg = String.format("Scan [%012d] bytes in [%012d] milliseconds.", count, duration().toMillis());
		return msg;
	}

}
