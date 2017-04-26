package args.schema;

import args.error.ArgsException;
import args.error.ErrorCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class LongFormData
{
	private final String key;
	private final String field;
	private final String value;


	public static LongFormData processDefinitionLine(String line) throws ArgsException
	{
		String[] parts = splitString(line);
		int pos = parts[0].indexOf('.');
		if (pos < 1)
		{
			log.error("Schema definition line {} key part does not have 'a.b' form.", parts[0]);
			throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT);
		}


		String key = parts[0].substring(0, pos);
		String field = parts[0].substring(pos + 1);
		String value = parts[1];

		LongFormData rv = new LongFormData(key, field, value);
		return rv;
	}

	private static String[] splitString(String line) throws ArgsException
	{
		String[] parts = line.split(SchemaBuilder.EXTENDED_FORMAT_SEPARATOR, 2);
		if (parts.length != 2 || parts[0] == null || parts[1] == null)
		{
			log.error("Schema definition line {} did not split into 2 parts.", line);
			throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT);
		}

		if (parts[0] == null || parts[1] == null)
		{
			log.error("Schema definition line {} split into 2 parts with null content.", line);
			throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT);
		}

		if (parts[0].length() == 0 || parts[1].length() == 0)
		{
			log.error("Schema definition line {} split into 2 parts with empty content.", line);
			throw new ArgsException(ErrorCode.INVALID_ARGUMENT_FORMAT);
		}



		return parts;
	}

}
