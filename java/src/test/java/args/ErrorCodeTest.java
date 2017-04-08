package args;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import args.error.ErrorCode;

public class ErrorCodeTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testAllValuesProduceErrorStrings()
	{
		// Arrange
		//
		String option = "x";
		String param = "brick";
		ErrorCode[] codes = ErrorCode.values();

		// Act & Assert
		//
		for (ErrorCode code: codes)
		{
			String err = code.errorText(option, param);
			assertNotNull(err);
		}
	}

	@Test
	public void testErrorStringsAcceptNull()
	{
		// Arrange
		//
		String option = "x";
		String param = null;
		ErrorCode[] codes = ErrorCode.values();

		// Act & Assert
		//
		for (ErrorCode code: codes)
		{
			String err = code.errorText(option, param);
			assertNotNull(err);
		}
	}

	@Test
	public void testErrorStringsAcceptUnicode()
	{
		// Arrange
		//
		String option = Character.toChars(0x2202).toString();
		String param = "blimp";
		ErrorCode[] codes = ErrorCode.values();

		// Act & Assert
		//
		for (ErrorCode code: codes)
		{
			String err = code.errorText(option, param);
			assertNotNull(err);
		}
	}


}
