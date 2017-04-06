package args.error;

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

	//	public ArgsException() { }
	//
	//	public ArgsException(String message)
	//	{
	//		super(message);
	//	}

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

	public String errorMessage()
	{
		return code.errorText(errorId, errorParameter);
	}

}
