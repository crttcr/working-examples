package args.error;

// NOTE: When adding a new ENUM, add an appropriate case to the switch // statement in the @{link errorText()} method.
//
public enum ErrorCode
{
	// Common Codes
	//
	OK("Error code says 'OK' but errorMessage called."),
	COMPOSITE_ERROR("Composite errors"),

	// Schema Codes
	//
	NO_SCHEMA("Argument processing requires a schema, but none was provided."),
	EMPTY_SCHEMA("Argument processing requires a schema, however an empty one was provided."),
	INVALID_SCHEMA_ELEMENT("Schema element not valid %s"),
	MISSING_OPTION_NAME("Options staring with - or -- must be followed by a character, not blank"),


	// Argument Processing Codes
	//
	NULL_ARGUMENT_ARRAY("Argument processing failed because a null value was provided as the argument list instead of an empty array."),
	UNEXPECTED_OPTION("Argument -%s unexpected. Not in schema definition."),
	MISSING_STRING("Could not find string parameter for option %s."),
	MISSING_STRING_LIST("Could not find string parameters for option %s."),
	MISSING_INTEGER("Could not find integer parameter for option %s"),
	MISSING_DOUBLE("Could not find double parameter for option %s"),
	MISSING_ENVIRONMENT_VARIABLE("Environment variable [%s] does not have an established value to define option [%s]."),

	INVALID_ARGUMENT_FORMAT("'%s' is not a valid argument format."),
	INVALID_ARGUMENT_NAME("'%s' is not a valid argument name."),
	INVALID_INTEGER("Argument -%s expects an integer, but was '%s'."),
	INVALID_DOUBLE("Argument -%s expects a double, but was '%s'."),
	;

	private String fmt;
	ErrorCode(String fmt) {
		this.fmt = fmt;
	}

	public String errorText(String option, String param)
	{
		switch(this)
		{
		case OK:
		case COMPOSITE_ERROR:
		case EMPTY_SCHEMA:
		case NO_SCHEMA:
		case NULL_ARGUMENT_ARRAY:
		case MISSING_OPTION_NAME:
			return format();
		case MISSING_STRING:
		case MISSING_STRING_LIST:
		case MISSING_INTEGER:
		case MISSING_DOUBLE:
		case INVALID_ARGUMENT_NAME:
		case INVALID_SCHEMA_ELEMENT:
		case UNEXPECTED_OPTION:
			return format(option);
		case INVALID_INTEGER:
		case INVALID_DOUBLE:
			return format(option, param);
		case INVALID_ARGUMENT_FORMAT:
			return format(param);
		case MISSING_ENVIRONMENT_VARIABLE:
			return format(param, option);
		}

		String fmt = "Programmer error. The format routine does not handle error code %s";
		String msg = String.format(fmt, this);
		return msg;
	}

	private String format()
	{
		return fmt;
	}

	private String format(String s)
	{
		return String.format(fmt, s);
	}

	private String format(String option, String param)
	{
		return String.format(fmt, option, param);
	}
}
