package jackson;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jackson.serialize.LocalDateTimeDeserializer;
import jackson.serialize.LocalDateTimeSerializer;

@JsonDeserialize(as=InterfaceImplementation.class)
public interface Interface
{
	@JsonProperty("id")
	String id();
	
	// PAY Attention to case here.  By using a different case such as @JsonProperty("Name")
	// you will change how the serialized JSON looks. This would need to be respected in
	// the deserialization.
	//
	@JsonProperty("name")
	String name();
	
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	LocalDateTime birthday();
	
	public default String toJson()
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
	
	public static Interface fromJson(String json)
	{
		ObjectMapper    mapper = new ObjectMapper();
		ObjectReader    reader = mapper.reader(Interface.class);
		Interface hydrated = null;
		
		try
		{
			hydrated = reader.readValue(json);
		}
		catch (IOException e)
		{
			System.err.println("Failed to read value:  " + e.getMessage());
			e.printStackTrace();
		}
		
		
		System.out.println(hydrated);

		return hydrated;
	}
}
