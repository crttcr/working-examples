package args;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArgsException
extends Exception
{


	private char errorId = '\0';
	private String errorParameter = null;
	private ErrorCode code = ErrorCode.OK;

	public ArgsException() { }

	public ArgsException(String message)
	{
		super(message);
	}

	public ArgsException(ErrorCode code)
	{
		this.code = code;
	}

	public ArgsException(ErrorCode code, String parameter)
	{
		this.code = code;
		this.errorParameter = parameter;
	}

	public ArgsException(ErrorCode code, char elementId, String param)
	{
		this.code = code;
		this.errorId = elementId;
		this.errorParameter = param;
	}

	public void setErrorArgumentId(char c)
	{
		errorId = c;
	}

	public Object errorMessage()
	{
		switch(code)
		{
		case OK:
			return code.format();
		case MISSING_STRING:
		case MISSING_INTEGER:
		case MISSING_DOUBLE:
		case INVALID_ARGUMENT_NAME:
		case UNEXPECTED_ARGUMENT:
			return code.format(errorId);
		case INVALID_INTEGER:
		case INVALID_DOUBLE:
			return code.format(errorId, errorParameter);
		case INVALID_ARGUMENT_FORMAT:
			return code.format(errorParameter);
		default:
			return "Somehow you reached an error code that is not accounted for: " + code;
		}
	}

}
