package jackson;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(as=ConcreteClass.class)
public abstract class AbstractClass
{
	@JsonProperty("id")
	abstract String id();
	
	@JsonProperty("name")
	abstract String name();
	
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	abstract LocalDateTime birthday();
	
   public String toString()
   {
   	String msg = String.format("AbstractClass.toString() -> id = %s, name = %s", id(), name());
   	return msg;
   }
   
	public final String toJson()
	{
		StringWriter string_w = new StringWriter();
		ObjectMapper   mapper = new ObjectMapper();
		
		try
		{
			mapper.writeValue(string_w, this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		String s = string_w.toString();

		return s;
	}
	
	public final static AbstractClass fromJson(String json)
	{
		ObjectMapper    mapper = new ObjectMapper();
		ObjectReader    reader = mapper.reader(AbstractClass.class);
		AbstractClass hydrated = null;
		
		try
		{
			hydrated = reader.readValue(json);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		System.out.println(hydrated);

		return hydrated;
	}
}
