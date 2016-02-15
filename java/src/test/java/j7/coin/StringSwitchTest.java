package j7.coin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringSwitchTest
{
	StringSwitch switcher;

	@Before
	public void setUp() throws Exception
	{
		switcher = new StringSwitch();
	}

	@After
	public void tearDown() throws Exception
	{
		switcher = null;
	}

	@Test
	public void testConvertMonday()
	{
		String day = "Monday";
		
		int i = switcher.convertDayToInt(day);
		
		assertEquals(1, i);
	}

   @Test(expected = NullPointerException.class)
	public void testConvertNull()
	{
		int i = switcher.convertDayToInt(null);
		
		fail("should have thrown exception. Received result: " + i);
	}

   @Test(expected = IllegalArgumentException.class)
	public void testConvertEmptyString()
	{
		int i = switcher.convertDayToInt("");
		
		fail("should have thrown exception. Received result: " + i);
	}


	@Test
	public void testConvertBogusDay()
	{
		String day = "Montag";
		
		int i = switcher.convertDayToInt(day);
		
		assertEquals(-1, i);
	}


}
