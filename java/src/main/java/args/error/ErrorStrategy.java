package args.error;

// Error strategy determines how argument processing should handle errors.
//
//
public enum ErrorStrategy
{
	FAIL_FAST('!'),
	FAIL_SLOW('~'),
	WARN_AND_IGNORE('&'),
	;

	private final char code;

	ErrorStrategy(char code) {
		this.code = code;
	}

	public static ErrorStrategy strategyForCode(char code)
	{
		switch (code)
		{
		case '!': return FAIL_FAST;
		case '~': return FAIL_SLOW;
		case '&': return WARN_AND_IGNORE;
		default: return null;
		}
	}

	public char getCode()
	{
		return code;
	}

}