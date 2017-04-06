package args;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import args.error.ArgsException;
import args.schema.Schema;
import args.schema.SchemaBuilder;

public class ArgsTest
{
	@Test(expected=ArgsException.class)
	public void testConstructWithNullSchema() throws Exception
	{
		String[] args = {"-x", "radio"};
		@SuppressWarnings("unused")
		Args arg = new Args(null, args);
	}

	@Test(expected=ArgsException.class)
	public void testConstructWithEmptySchema() throws Exception
	{
		Schema schema = SchemaBuilder.parseSchema("");
		String[] args = {"-x", "radio"};
		@SuppressWarnings("unused")
		Args arg = new Args(schema, args);
	}

	@Test(expected=ArgsException.class)
	public void testConstructWithNullArgList() throws Exception
	{
		Schema schema = SchemaBuilder.parseSchema("x");
		@SuppressWarnings("unused")
		Args arg = new Args(schema, null);
	}

	@Test
	public void testConstructionWhenArgumentListIsEmpty() throws Exception
	{
		// Arrange & Act
		//
		Schema schema = SchemaBuilder.parseSchema("x");
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
		Schema schema = SchemaBuilder.parseSchema("x");
		String[] args = {"-x", "radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		Boolean isSet = arg.getValue('x');

		// Assert
		//
		assertTrue(isSet);
	}

	@Test
	public void testGetBooleanSuccessWhenNotSet() throws Exception
	{
		// Arrange
		//
		Schema schema = SchemaBuilder.parseSchema("x");
		String[] args = {"radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		Boolean isSet = arg.getValue('x');

		// Assert
		//
		assertFalse(isSet);
	}

	@Test
	public void testGetStringSuccess() throws Exception
	{
		// Arrange
		//
		Schema schema = SchemaBuilder.parseSchema("x*");
		String[] args = {"-x", "radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		String s = arg.getValue('x');

		// Assert
		//
		assertNotNull(s);
		assertEquals("radio", s);
	}

	@Test
	public void testGetIntegerSuccess() throws Exception
	{
		// Arrange
		//
		Schema schema = SchemaBuilder.parseSchema("x*,y#");
		String[] args = {"-y", "239"};
		Args arg = new Args(schema, args);

		// Act
		//
		Integer i = arg.getValue('y');

		// Assert
		//
		assertNotNull(i);
		assertEquals(239, i.intValue());
	}

	@Test
	public void testHasWhenSingleBooleanNotProvided() throws Exception
	{
		// Arrange
		//
		Schema schema = SchemaBuilder.parseSchema("x");
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
		Schema schema = SchemaBuilder.parseSchema("x*,y#");
		String[] args = {};
		Args arg = new Args(schema, args);

		// Act
		//
		int na = arg.nextArgument();

		// Assert
		//
		assertEquals(0, na);
	}

	@Test
	public void testNextArgumentWithOneArgumentNoOptions() throws Exception
	{
		// Arrange
		//
		Schema schema = SchemaBuilder.parseSchema("x*,y#");
		String[] args = {"file.a"};
		Args arg = new Args(schema, args);

		// Act
		//
		int na = arg.nextArgument();
		String s = arg.getArgument(na);

		// Assert
		//
		assertNotNull(s);
		assertEquals(args[0], s);
	}


}
