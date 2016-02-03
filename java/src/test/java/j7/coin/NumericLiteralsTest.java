package j7.coin;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/*
 * This is a pretty boring test, admittedly.
 * We're testing literals, so not much action.
 * 
 */

public class NumericLiteralsTest
{
	@Test
	public void testDecimalUnderscores()
	{
		assertEquals(10000000, NumericLiterals.DECIMAL);
	}

	@Test
	public void testHexUnderscores()
	{
		assertEquals(0xCAFEBABE, NumericLiterals.HEX);
	}

	@Test
	public void testBinary()
	{
		assertEquals(24, NumericLiterals.BINARY);
	}

}
