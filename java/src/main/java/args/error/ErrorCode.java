package args.error;

// NOTE: When adding a new ENUM, add an appropriate case to the switch
// statement in the @{link errorText()} method.
//
public enum ErrorCode
{
	OK("Error code says 'OK' but errorMessage called."),
	EMPTY_SCHEMA("Argument processing requires a schema, however an empty one was provided."),
	NO_SCHEMA("Argument processing requires a schema, but none was provided."),
	NULL_ARGUMENT_ARRAY("Argument processing failed because a null value was provided as the argument list instead of an empty array."),
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

	public String errorText(char errorId, String param)
	{
		switch(this)
		{
		case OK:
		case EMPTY_SCHEMA:
		case NO_SCHEMA:
		case NULL_ARGUMENT_ARRAY:
			return format();
		case MISSING_STRING:
		case MISSING_INTEGER:
		case MISSING_DOUBLE:
		case INVALID_ARGUMENT_NAME:
		case UNEXPECTED_ARGUMENT:
			return format(errorId);
		case INVALID_INTEGER:
		case INVALID_DOUBLE:
			return format(errorId, param);
		case INVALID_ARGUMENT_FORMAT:
			return format(param);
		}

		String fmt = "Programmer error. The format routine does not handle error code %s";
		String msg = String.format(fmt, this);
		return msg;
	}

	private String format()
	{
		return fmt;
	}

	private String format(char c)
	{
		return String.format(fmt, c);
	}

	private String format(String s)
	{
		return String.format(fmt, s);
	}

	private String format(char c, String s)
	{
		return String.format(fmt, c, s);
	}
}
