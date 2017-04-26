package args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import args.marshall.BooleanOptEvaluator;
import args.marshall.OptEvaluator;

public class ItemTest
{
	@Test
	public void testBasicConstructor()
	{
		//Arrange
		//
		String name = "fred";
		OptionType type = OptionType.BOOLEAN;
		OptEvaluator<Boolean> eval = new BooleanOptEvaluator();

		// Act
		//
		Item<Boolean> item = new Item<Boolean>(name, type, eval);

		// Assert
		//
		assertSimpleItem(item, name, type);
	}

	@Test
	public void testConstructorWithDefault()
	{
		//Arrange
		//
		String name = "fred";
		OptionType type = OptionType.BOOLEAN;
		OptEvaluator<Boolean> eval = new BooleanOptEvaluator();

		// Act
		//
		Item<Boolean> itemTrue = new Item<Boolean>(name, type, eval, Boolean.TRUE, null, null);
		Item<Boolean> itemFalse = new Item<Boolean>(name, type, eval, Boolean.FALSE, null, null);

		// Assert
		//
		assertTrue(itemTrue.getDv());
		assertFalse(itemFalse.getDv());
	}


	@Test
	public void testConstructorWithRequired()
	{
		//Arrange
		//
		String name = "fred";
		OptionType type = OptionType.BOOLEAN;
		OptEvaluator<Boolean> eval = new BooleanOptEvaluator();

		// Act
		//
		Item<Boolean> itemTrue = new Item<Boolean>(name, type, eval, true, null, null);
		Item<Boolean> itemFalse = new Item<Boolean>(name, type, eval, false, null, null);

		// Assert
		//
		assertTrue(itemTrue.getRequired());
		assertFalse(itemFalse.getRequired());
	}


	///////////////////////////////
	// Helper Methods            //
	///////////////////////////////

	private void assertSimpleItem(Item<?> item, String name, OptionType type)
	{
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertEquals(type, item.getType());
		assertNotNull(item.getEval());
		assertFalse(item.getRequired());
		assertNull(item.getDescription());
		assertNull(item.getEv());
		assertNull(item.getDv());
	}

}
