package args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import args.error.ArgsException;
import args.marshall.OptEvaluator;
import args.marshall.StringOptEvaluator;

public class ItemBuilderTest
{
	@Test(expected=ArgsException.class)
	public void testBuildWithNoValuesEstablished() throws ArgsException
	{
		Item.builder().build();
	}

	@Test(expected=ArgsException.class)
	public void testBuildWithoutName() throws ArgsException
	{
		// Arrange
		//
		OptionType type = OptionType.STRING;
		OptEvaluator<String> eval = new StringOptEvaluator();
		String dv = "default value";
		Item.Builder<String> builder = Item.builder();
		builder.type(type).eval(eval).dv(dv);

		// Act
		//
		builder.build();
	}

	@Test(expected=ArgsException.class)
	public void testBuildWithoutType() throws ArgsException
	{
		// Arrange
		//
		String name = "message";
		OptEvaluator<String> eval = new StringOptEvaluator();
		String dv = "default value";
		Item.Builder<String> builder = Item.builder();
		builder.name(name).eval(eval).dv(dv);

		// Act
		//
		builder.build();
	}

	@Test(expected=ArgsException.class)
	public void testBuildWithoutEval() throws ArgsException
	{
		// Arrange
		//
		String name = "message";
		OptionType type = OptionType.STRING;
		String dv = "default value";
		Item.Builder<String> builder = Item.builder();
		builder.name(name).type(type).dv(dv);

		// Act
		//
		builder.build();
	}

	@Test
	public void testBuildWithMinimalInformation() throws ArgsException
	{
		// Arrange
		//
		String name = "message";
		OptionType type = OptionType.STRING;
		OptEvaluator<String> eval = new StringOptEvaluator();
		Item.Builder<String> builder = Item.builder();
		builder.name(name).type(type).eval(eval);

		// Act
		//
		Item<String> item = builder.build();

		// Assert
		//
		assertComplexItem(item, name, type, eval, null);
	}



	///////////////////////////////
	// Helper Methods            //
	///////////////////////////////

	private <T> void assertComplexItem(Item<T> item, String name, OptionType type, OptEvaluator<T> eval, T dv)
	{
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertEquals(type, item.getType());
		//  assertEquals(eval, item.getEval());
		//		assertFalse(item.getRequired());
		assertEquals(dv, item.getDv());
	}

}
