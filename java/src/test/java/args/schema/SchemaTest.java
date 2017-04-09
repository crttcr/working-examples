package args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import args.TestUtil;
import args.marshall.IntegerOptEvaluator;
import args.marshall.OptEvaluator;
import args.marshall.StringOptEvaluator;

public class SchemaTest
{

	private Schema subject;

	@Before
	public void setUp() throws Exception
	{
		Map<String, Item<?>> args = new HashMap<>();
		String name = "x";
		Item<?> item = createItem(name, OptionType.STRING);
		args.put(name, item);
		subject = new Schema("TestSchema", args);
	}

	@Test
	public void testGetOptionsBaseCase()
	{
		// Act
		//
		List<String> opts = subject.getOptions();

		// Assert
		//
		assertNotNull(opts);
		assertEquals(1, opts.size());
		assertEquals("x", opts.get(0));
	}

	@Test
	public void testGetItemBaseCaseNotSet()
	{
		// Act
		//
		Item<String> item = subject.getItem("x");
		String value = item.getEval().getValue();

		// Assert
		//
		assertNotNull(item);
		assertNull(value);
	}

	@Test
	public void testAllRequiredArgsHaveValuesSingleIntegerSuccess() throws Exception
	{
		// Arrange
		//
		Map<String, Item<?>> args = new HashMap<>();
		String name = "port";
		Item<Integer> theItem = createRequiredIntegerItem(name);
		args.put(name, theItem);
		ListIterator<String> it = TestUtil.getListIterator("49");
		theItem.getEval().set(it);
		subject = new Schema("TestSchema", args);


		// Act
		//
		boolean ok = subject.allRequiredOptionsHaveValues();

		// Assert
		//
		assertTrue(ok);
	}


	@Test
	public void testAllRequiredArgsHaveValuesSingleIntegerFailure() throws Exception
	{
		// Arrange
		//
		Map<String, Item<?>> args = new HashMap<>();
		String name = "port";
		Item<Integer> theItem = createRequiredIntegerItem(name);
		args.put(name, theItem);
		subject = new Schema("TestSchema", args);


		// Act
		//
		boolean ok = subject.allRequiredOptionsHaveValues();

		// Assert
		//
		assertFalse(ok);
	}

	///////////////////////////////
	// Helper Methods            //
	///////////////////////////////

	@Test
	public void testAllRequiredOptionsHaveValuesOneOptionFalse()
	{
		// Act
		//
		boolean ok = subject.allRequiredOptionsHaveValues();
	
		// Assert
		//
		assertTrue(ok);
	}

	private Item<?> createItem(String name, OptionType type)
	{
		OptEvaluator<String> eval = new StringOptEvaluator();
		Item<String> item = new Item<>(name, type, eval);
		return item;
	}

	private Item<Integer> createRequiredIntegerItem(String name)
	{
		OptEvaluator<Integer> eval = new IntegerOptEvaluator();
		Item<Integer> item = new Item<>(name, OptionType.INTEGER, eval, true);
		return item;
	}


}
