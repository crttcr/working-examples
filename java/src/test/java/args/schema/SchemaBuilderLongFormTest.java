package args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

import org.junit.Before;
import org.junit.Test;

public class SchemaBuilderLongFormTest
{
	private SchemaBuilder subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new SchemaBuilder("TestSchemaBuidler");
	}

	@Test
	public void testSchemaBuilderBooleanRequiredNoDefault() throws Exception
	{
		// Arrange
		//
		String name = "x";
		String defs = createDefs(name, OptionType.BOOLEAN, true, null);

		// Act
		//
		Schema schema = subject.build(defs);
		Item<Boolean> item = schema.getItem(name);

		// Assert
		//
		assertItemForm(item, name, OptionType.BOOLEAN, true, null);
	}
	@Test
	public void testSchemaBuilderBooleanWithDefaultTrue() throws Exception
	{
		// Arrange
		//
		String defs = readFromTestResourceFile("b.rt");

		// Act
		//
		Schema schema = subject.build(defs);
		Item<Boolean> item = schema.getItem("b");

		// Assert
		//
		assertItemForm(item, "b", OptionType.BOOLEAN, true, null);
	}


	///////////////////////////////
	// Helper Methods //
	///////////////////////////////

	private void assertItemForm(Item<?> item, String name, OptionType type, boolean required, Object dv)
	{
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertNull(item.getRequired());
		assertNull(item.getDv());
		assertEquals(type, item.getType());
		assertNotNull(item.getEval());
	}

	private String createDefs(String name, OptionType type, boolean required, String dv)
	{
		StringJoiner sj = new StringJoiner("\n");

		sj.add(name + ".name=" + name);
		sj.add(name + ".type=" + type);

		return sj.toString();
	}

	private String readFromTestResourceFile(String file)
	{
		Path path = Paths.get("src/test/resources/args/defs", file);

		System.out.println(path.toAbsolutePath().toString());

		if (! Files.exists(path))
		{
			fail("Missing test resource file: " + path);
		}

		byte[] bytes = {};
		try
		{
			bytes = Files.readAllBytes(path);
		}
		catch (IOException e)
		{
			fail("Error reading test resource file: " + path + ". Error: " + e.getLocalizedMessage());
		}
		String content = new String(bytes);

		return content;
	}

}
