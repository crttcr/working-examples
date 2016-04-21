package jackson.basics;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SingleFieldAnnotationTest
{
	private SingleFieldAnnotation subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new SingleFieldAnnotation();
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testSerializeEmptyObject()
		throws Exception
	{
		// Arrange
		//
		StringWriter     sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		// Act
		//
		mapper.writeValue(sw, subject);
		String json = sw.toString();
		int     pos = json.indexOf("name");
		
//		System.out.println(subject);
//		System.out.println(json);
		
		// Assert
		//
		assertNotNull(json);
		assertTrue(-1 < pos);
	}

	@Test
	public void testDeserializeEmptyObject()
		throws Exception
	{
		// Arrange
		//
		ObjectMapper mapper = new ObjectMapper();
		String         json = "{\"name\":null}";
		
		// Act
		//
		SingleFieldAnnotation hydrated = mapper.readValue(json, SingleFieldAnnotation.class); 
		
//		System.out.println(hydrated);
		
		// Assert
		//
		assertNotNull(hydrated);
	}
	
}
