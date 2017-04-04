package args;

public enum ErrorCode
{
	OK("Error code says 'OK' but errorMessage called."),
	UNEXPECTED_ARGUMENT("Argument -%c unexpected. Not in schema definition."),
	MISSING_STRING("Could not find string parameter for -%c."),
	MISSING_INTEGER("Could not find integer parameter for -%c"),
	MISSING_DOUBLE("Could not find double parameter for -%c"),
	INVALID_ARGUMENT_FORMAT("'%s' is not a valid argument format."),
	INVALID_ARGUMENT_NAME("'%c' is not a valid argument name."),
	INVALID_INTEGER("Argument -%c expects an integer, but was '%s'."),
	INVALID_DOUBLE("Argument -%c expects a double, but was '%s'."),
	;

	private String fmt;
	ErrorCode(String fmt) {
		this.fmt = fmt;
	}

	public String format()
	{
		return fmt;
	}

	public String format(char c)
	{
		return String.format(fmt, c);
	}

	public String format(String s)
	{
		return String.format(fmt, s);
	}

	public String format(char c, String s)
	{
		return String.format(fmt, c, s);
	}
}
