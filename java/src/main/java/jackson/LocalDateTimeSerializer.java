package jackson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/** 
 * Convert LocalDateTime JSON representation to a single long representing the number of seconds
 * into the epoch. This has second resolution. Does not take into account sub-second time units.
 * See the companion {@link LocalDateTimeDeserialiser}
 * 
 * Note: 
 * This class uses UTC by default if a Null ZoneOffset is provided
 * Nano seconds are ignored.
 * 
 * @author reid.dev
 *
 */
public class LocalDateTimeSerializer
	extends JsonSerializer<LocalDateTime>
{
	private final ZoneOffset offset;

	public LocalDateTimeSerializer()
	{
		this(ZoneOffset.UTC);
	}
	
	public LocalDateTimeSerializer(ZoneOffset offset)
	{
		if (offset == null)
			this.offset = ZoneOffset.UTC;
		else
			this.offset = ZoneOffset.UTC;
	}
	
	
	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
	throws IOException, JsonProcessingException
	{
		if (value == null)
		{
			gen.writeNull();
			return;
		}

		long seconds = value.toEpochSecond(offset);
		gen.writeNumber(seconds);
	}

}
