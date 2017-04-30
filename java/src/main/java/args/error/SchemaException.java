package args.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class SchemaException
extends ArgsException
{
	public SchemaException(ErrorCode code)
	{
		super(code);
	}

	public SchemaException(ErrorCode code, String parameter)
	{
		super(code, parameter);
	}

	public SchemaException(ErrorCode code, String option, String param)
	{
		super(code, option, param);
	}

	@Override
	public String errorMessage()
	{
		String rv = "SchemaException: " + super.toString();
		return rv;
	}

}
