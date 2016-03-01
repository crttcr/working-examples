package autovalue;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookInfoTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test(expected=NullPointerException.class)
	public void testNullTitleInBuilderCreation()
	{
		// Arrange
		//
		
		// Act
		//
		@SuppressWarnings("unused")
		BookInfo.Builder builder = BookInfo.builder(null);
		
		// Assert
		//
		fail("Should have thrown exception. Instead execution continued to this point");
	}

	@Test(expected=IllegalStateException.class)
	public void testCallingBuildWithNoTitle()
	{
		// Arrange
		//
		BookInfo.Builder builder = BookInfo.builder();
		
		// Act
		//
		@SuppressWarnings("unused")
		BookInfo info = builder.build();
		
		// Assert
		//
		fail("Should have thrown exception. Instead execution continued to this point");
	}

	@Test
	public void testCallingBuildWithNoIsbn()
	{
		// Arrange
		//
		BookInfo.Builder builder = BookInfo.builder();
		builder.title("Some Title");
		builder.authors("John LeCarre");
		
		// Act
		//
		BookInfo info = builder.build();
		
		// Assert
		//
		assertNotNull(info);
		assertNull(info.isbn());
	}

	@Test
	public void testCallingBuildAfterIsbn()
	{
		// Arrange
		//
		String ISBN = "9780553049886";
		BookInfo.Builder builder = BookInfo.builder();
		builder.title("Some Title");
		builder.authors("John LeCarre");
		builder.isbn(ISBN);
		
		// Act
		//
		BookInfo info = builder.build();
		
		// Assert
		//
		assertNotNull(info);
		assertNotNull(info.isbn());
		assertTrue(ISBN.equals(info.isbn()));
	}


}
