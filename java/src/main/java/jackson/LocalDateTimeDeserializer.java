package jackson;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/** 
 * Convert JSON representation as a single long representing the number of seconds
 * into the epoch into a LocalDateTime. This has second resolution. Does not take into 
 * account sub-second time units. See the companion {@link LocalDateTimeSerialiser}
 * 
 * Note: 
 * This class uses UTC by default if a Null ZoneOffset is provided
 * Nanoseconds are ignored.
 * 
 * @author reid.dev
 *
 */
public class LocalDateTimeDeserializer
	extends JsonDeserializer<LocalDateTime>
{
	private final ZoneOffset offset;
	
	public LocalDateTimeDeserializer()
	{
		this(ZoneOffset.UTC);
	}

	public LocalDateTimeDeserializer(ZoneOffset offset)
	{
		if (offset == null)
			this.offset = ZoneOffset.UTC;
		else
			this.offset = ZoneOffset.UTC;
	}

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
	throws IOException, JsonProcessingException
	{
		String text = p.getText();
		
		if (text == null)
			return null;
		
		LocalDateTime result = null;
		try
		{
			long seconds = Long.parseLong(text, 10);
			result       = LocalDateTime.ofEpochSecond(seconds, 0, offset);
			
		}
		catch (NumberFormatException | DateTimeException e)
		{
			return null;
		}

		return result;
	}

}
