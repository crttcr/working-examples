package args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class SchemaBuilderShortFormTest
{
	private SchemaBuilder subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new SchemaBuilder("TestSchemaBuidler");
	}

	@Test
	public void testSchemaBuilderSingleBooleanTest() throws Exception
	{
		// Arrange
		//
		String opt = "b";

		// Act
		//
		Schema schema = subject.build(opt);
		Item<Boolean> item = schema.getItem(opt);

		// Assert
		//
		assertSimpleItemForm(item, opt, OptionType.BOOLEAN);
	}

	@Test
	public void testSchemaBuilderSingleStringTest() throws Exception
	{
		// Arrange
		//
		String opt = "s";
		String fmt = "s*";

		// Act
		//
		Schema schema = subject.build(fmt);
		Item<Boolean> item = schema.getItem(opt);

		// Assert
		//
		assertSimpleItemForm(item, opt, OptionType.STRING);
	}

	@Test
	public void testSchemaBuilderSingleIntegerTest() throws Exception
	{
		// Arrange
		//
		String opt = "i";
		String fmt = "i#";

		// Act
		//
		Schema schema = subject.build(fmt);
		Item<Boolean> item = schema.getItem(opt);

		// Assert
		//
		assertSimpleItemForm(item, opt, OptionType.INTEGER);
	}

	@Test
	public void testSchemaBuilderBSITest() throws Exception
	{
		// Arrange
		//
		String fmt = "b,s*,i#";

		// Act
		//
		Schema schema = subject.build(fmt);
		Item<Boolean> i1 = schema.getItem("b");
		Item<String>  i2 = schema.getItem("s");
		Item<Integer> i3 = schema.getItem("i");

		// Assert
		//
		assertSimpleItemForm(i1, "b", OptionType.BOOLEAN);
		assertSimpleItemForm(i2, "s", OptionType.STRING);
		assertSimpleItemForm(i3, "i", OptionType.INTEGER);
	}

	@Test
	public void testSchemaBuilderBIBWithSpaces() throws Exception
	{
		// Arrange
		//
		String fmt = "b, i# , x";

		// Act
		//
		Schema schema = subject.build(fmt);
		Item<Boolean> i1 = schema.getItem("b");
		Item<Boolean> i2 = schema.getItem("i");
		Item<Boolean> i3 = schema.getItem("x");

		// Assert
		//
		assertSimpleItemForm(i1, "b", OptionType.BOOLEAN);
		assertSimpleItemForm(i2, "i", OptionType.INTEGER);
		assertSimpleItemForm(i3, "x", OptionType.BOOLEAN);
	}

	///////////////////////////////
	// Helper Methods            //
	///////////////////////////////

	private void assertSimpleItemForm(Item<?> item, String name, OptionType type)
	{
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertFalse(item.getRequired());
		assertNull(item.getDv());
		assertEquals(type, item.getType());
		assertNotNull(item.getEval());
	}
}
