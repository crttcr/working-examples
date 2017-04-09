package args.marshall;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;

import args.TestUtil;
import args.error.ArgsException;

public class IntegerOptEvaluatorTest
{
	private IntegerOptEvaluator subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new IntegerOptEvaluator();
	}

	@Test
	public void testToString()
	{
		System.out.println(subject.toString());
		assertTrue(subject.toString().contains("called = 0"));
	}

	@Test
	public void testSuccess() throws Exception
	{
		// Arrange
		//
		ListIterator<String> it = TestUtil.getListIterator("223", "y");
		subject.set(it);

		// Act
		//
		Integer i = subject.getValue();
		int count = subject.count();


		// Assert
		//
		assertNotNull(i);
		assertEquals(223, i.intValue());
		assertEquals(1, count);
	}

	@Test(expected=ArgsException.class)
	public void testSetNotAnInteger() throws Exception
	{
		ListIterator<String> it = TestUtil.getListIterator("two");
		subject.set(it);

		// Act
		//
		Integer i = subject.getValue();
		int count = subject.count();


		// Assert
		//
		assertNotNull(i);
		assertEquals(223, i.intValue());
		assertEquals(1, count);
	}


	//////////////////////////
	// Helper Methods       //
	//////////////////////////


}
