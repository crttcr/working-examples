package jackson.nested;

import static org.junit.Assert.assertNotNull;

import java.io.StringWriter;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import jackson.AbstractClass;
import jackson.ConcreteClass;
import jackson.Interface;
import jackson.InterfaceImplementation;

public class ContainingJsonTest
{
	private Containing subject;

	@Before
	public void setUp() throws Exception
	{
		subject = populatedContaining();
	}

	@After
	public void tearDown() throws Exception
	{
	}

	// Helper method for convenience
	//
	private Containing emptyContaining()
	{
		return new Containing();
	}

	// Helper method for convenience
	//
	private Containing populatedContaining()
	{
		LocalDateTime     now = LocalDateTime.now();
		LocalDateTime    then = LocalDateTime.of(2014, 3, 14, 0, 0);
		AbstractClass  aclass = ConcreteClass.factoryMethod("cc.id", "cc.name", then);
		Interface       iface = InterfaceImplementation.factoryMethod("if.id", "if.name", now);
		
		return new Containing(" *** FULL_CONTAINER ***", iface, aclass);
	}

	@Test
	public void testToStringEmpty()
	{
		// Arrange
		//
		subject = emptyContaining();
		
		// Act
		//
		String s = subject.toString();
		
		
		// Assert
		//
		assertNotNull(s);
		System.out.println(s);
	}

	@Test
	public void testSerializeEmptyContainer()
		throws Exception
	{
		// Arrange
		//
		subject             = emptyContaining();
		StringWriter     sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		// Act
		//
		mapper.writeValue(sw, subject);
		String json = sw.toString();
		
		System.out.println("Empty Container JSON:");
		System.out.println(json);
		System.out.println("");
		
		// Assert
		//
		assertNotNull(json);
	}

	@Test
	public void testSerializeFullContainer()
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
		
		System.out.println("Full container JSON:");
		System.out.println(json);
		System.out.println("");
		
		// Assert
		//
		assertNotNull(json);
	}

	@Test
	public void testDeserializeFullContainer()
		throws Exception
	{
		// Arrange
		//
		ObjectMapper mapper = new ObjectMapper();
		String         json = "{\"name\":\"HEAVY\",\"iface\":{\"id\":\"if.id\",\"name\":\"if.name\",\"birthday\":1461233316},\"aclass\":{\"id\":\"cc.id\",\"name\":\"cc.name\",\"birthday\":1394755200}}";
		
		// Act
		//
		Containing hydrated = mapper.readValue(json, Containing.class); 
		
		System.out.println("Rehydrated full container");
		System.out.println(hydrated);
		System.out.println("");
		
		// Assert
		//
		assertNotNull(hydrated);
	}
	
	@Test
	public void testDeserializeEmptyContainer()
		throws Exception
	{
		// Arrange
		//
		ObjectMapper mapper = new ObjectMapper();
		String         json = "{\"name\":\"C_OF_NULL\",\"iface\":null,\"aclass\":null}";		

		// Act
		//
		Containing hydrated = mapper.readValue(json, Containing.class); 
		
		System.out.println("Rehydrated EMPTY container");
		System.out.println(hydrated);
		System.out.println("");
		
		// Assert
		//
		assertNotNull(hydrated);
	}

}
