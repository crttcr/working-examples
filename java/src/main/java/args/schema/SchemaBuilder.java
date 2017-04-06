package args.schema;

import args.error.ArgsException;

public class SchemaBuilder
{
	public static Schema parseSchema(String def) throws ArgsException
	{
		boolean isSimple = determineIfSimpleDefinition(def);

		if (isSimple)
		{
			return new SimpleSchema(def);
		}
		else
		{
			// FIXME Not implemented.
			return new ComplexSchema(def);
		}
	}

	private static boolean determineIfSimpleDefinition(String def)
	{
		// TODO Auto-generated method stub
		return true;
	}



}
