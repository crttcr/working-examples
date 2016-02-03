package j7.coin;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TryWithResourcesTest
{
	private TwrCloseable alias = null;

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
		if (alias != null)
		{
			assertTrue(alias.wasClosed());
			alias = null;
		}
	}

	@Test
	public void testTwr()
	{
		try (TwrCloseable resource = new TwrCloseable())
		{
			alias = resource;
			assertFalse(resource.wasClosed());
		}
		finally
		{
			assertTrue(alias.wasClosed());
		}
	}

   @Test(expected = RuntimeException.class)
	public void testTwrWithException()
	{
		try (TwrCloseable resource = new TwrCloseable())
		{
			alias = resource;
			assertFalse(resource.wasClosed());
			throw new RuntimeException("Will it be closed even with an exception?");
		}
		finally
		{
			assertTrue(alias.wasClosed());
		}
	}


}
