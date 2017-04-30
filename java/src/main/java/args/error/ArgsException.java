package args.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class ArgsException
extends Exception
{
	private String option = "";
	private String errorParameter = null;
	private ErrorCode code = ErrorCode.OK;

	public ArgsException(ErrorCode code)
	{
		this.code = code;
	}

	public ArgsException(ErrorCode code, String parameter)
	{
		this.code = code;
		this.errorParameter = parameter;
	}

	public ArgsException(ErrorCode code, String option, String param)
	{
		this.code = code;
		this.option = option;
		this.errorParameter = param;
	}

	public String errorMessage()
	{
		return code.errorText(option, errorParameter);
	}

}
