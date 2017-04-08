package args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import args.error.ArgsException;

public class SchemaBuilderTest
{
	private SchemaBuilder subject;

	@Test
	public void testSchemaBuilderNullName() throws Exception
	{
		// Arrange
		//
		subject = new SchemaBuilder(null);

		// Act
		//
		Schema schema = subject.build("x");

		// Assert
		//
		assertNotNull(schema);
		assertNotNull(schema.getName());
	}

	@Test
	public void testSchemaBuilderSpecifiedName() throws Exception
	{
		// Arrange
		//
		String name = "Jorge";
		subject = new SchemaBuilder(name);

		// Act
		//
		Schema schema = subject.build("x");

		// Assert
		//
		assertNotNull(schema);
		assertEquals(name, schema.getName());
	}


	@Test
	public void testSchemaBuilderEmptyName() throws Exception
	{
		// Arrange
		//
		subject = new SchemaBuilder("");

		// Act
		//
		Schema schema = subject.build("x");

		// Assert
		//
		assertNotNull(schema);
		assertEquals("", schema.getName());
	}

	@Test(expected=ArgsException.class)
	public void testBuildNullThrows() throws Exception
	{
		SchemaBuilder sb = new SchemaBuilder("TestSchema");
		sb.build(null);
	}
}
