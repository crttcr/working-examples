package args;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class ArgsTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test(expected=ArgsException.class)
	public void testConstructWithNullSchema() throws Exception
	{
		String[] args = {"-x", "radio"};
		@SuppressWarnings("unused")
		Args arg = new Args(null, args);
	}

	@Test
	public void testConstructionWhenArgumentListIsEmpty() throws Exception
	{
		// Arrange & Act
		//
		String schema = "x";
		String[] args = {};
		Args arg = new Args(schema, args);

		// Assert
		//
		assertNotNull(arg);
	}

	@Test
	public void testGetBooleanSuccessWhenSet() throws Exception
	{
		// Arrange
		//
		String schema = "x";
		String[] args = {"-x", "radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		boolean isSet = arg.getBoolean('x');

		// Assert
		//
		assertTrue(isSet);
	}

	@Test
	public void testGetBooleanSuccessWhenNotSet() throws Exception
	{
		// Arrange
		//
		String schema = "x";
		String[] args = {"radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		boolean isSet = arg.getBoolean('x');

		// Assert
		//
		assertFalse(isSet);
	}

	@Test
	public void testGetString()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetInteger()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testHasWhenSingleBooleanNotProvided() throws Exception
	{
		// Arrange
		//
		String schema = "x";
		String[] args = {"radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		boolean isSet = arg.has('x');

		// Assert
		//
		assertFalse(isSet);
	}

	@Test
	public void testNextArgumentWhenNoneProvided() throws Exception
	{
		// Arrange
		//
		String schema = "x";
		String[] args = {};
		Args arg = new Args(schema, args);

		// Act
		//
		int na = arg.nextArgument();

		// Assert
		//
		assertEquals(0, na);
	}

}
