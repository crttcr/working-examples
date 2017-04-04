package args;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

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
		char errorId = 'x';
		String param = "brick";
		ErrorCode[] codes = ErrorCode.values();

		// Act & Assert
		//
		for (ErrorCode code: codes)
		{
			String err = code.errorText(errorId, param);
			assertNotNull(err);
		}
	}

	@Test
	public void testErrorStringsAcceptNull()
	{
		// Arrange
		//
		char errorId = 'x';
		String param = null;
		ErrorCode[] codes = ErrorCode.values();

		// Act & Assert
		//
		for (ErrorCode code: codes)
		{
			String err = code.errorText(errorId, param);
			assertNotNull(err);
		}
	}

	@Test
	public void testErrorStringsAcceptUnicode()
	{
		// Arrange
		//
		char errorId = Character.toChars(0x2202)[0];
		String param = "blimp";
		ErrorCode[] codes = ErrorCode.values();

		// Act & Assert
		//
		for (ErrorCode code: codes)
		{
			String err = code.errorText(errorId, param);
			assertNotNull(err);
		}
	}


}
