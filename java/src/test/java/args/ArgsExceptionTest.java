package args;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import args.error.ArgsException;
import args.error.ErrorCode;

public class ArgsExceptionTest
{
	@Test
	public void testArgsExceptionErrorCode()
	{
		// Arrange & Act
		//
		ArgsException ex = new ArgsException(ErrorCode.OK);

		// Assert
		//
		assertNotNull(ex.getCode());
		assertNotNull(ex.errorMessage());
	}

	@Test
	public void testArgsExceptionErrorCodeString()
	{
		// Arrange & Act
		//
		ArgsException ex = new ArgsException(ErrorCode.OK, "Banana");

		// Assert
		//
		assertNotNull(ex.getCode());
		assertNotNull(ex.errorMessage());
	}

	@Test
	public void testArgsExceptionErrorCodeCharString()
	{
		// Arrange & Act
		//
		ArgsException ex = new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT, "^", "Banana");

		// Assert
		//
		assertNotNull(ex.getCode());
		assertNotNull(ex.errorMessage());
	}
}
