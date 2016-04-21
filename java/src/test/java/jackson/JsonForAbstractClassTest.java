package jackson;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class JsonForAbstractClassTest
{
	private static final String              id = "39549213";
	private static final String            name = "Col. Skinner";
	private static final LocalDateTime birthday = LocalDateTime.of(1924, 2, 16, 5, 43);
	
	AbstractClass subject;
	
	@Before
	public void setUp() throws Exception
	{
		subject = ConcreteClass.factoryMethod(id, name, birthday);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Ignore
	@Test
	public void testId()
	{
		// Arrange
		//
		
		// Act
		// 
		String returned_id = subject.id();
		
		// Assert
		//
		assertNotNull(returned_id);
		assertTrue(id.equals(returned_id));
	}


	@Ignore
	@Test
	public void testToString()
	{
		// Arrange
		//
		
		// Act
		// 
		String to_string = subject.toString();
		
		// Assert
		//
		assertNotNull(to_string);
		System.out.println(to_string);
	}

	@Ignore
	@Test
	public void testToJsonCreatesText()
	{
		// Arrange
		//
		
		// Act
		// 
		String json = subject.toJson();
		
		// Assert
		//
		assertNotNull(json);
		System.out.println(json);
	}

	@Test
	public void testFromJsonResultIsNotNull()
	{
		// Arrange
		//
		String json = "{\"id\":\"39549213\",\"birthday\":-1447697820,\"name\":\"Col. Skinner\"}";

		
		// Act
		// 
		AbstractClass hydrated = AbstractClass.fromJson(json);
		
		// Assert
		//
		assertNotNull(hydrated);
		System.out.println(hydrated);
	}

}
