package args;

import args.error.ArgsException;
import args.schema.Schema;
import args.schema.SchemaBuilder;

/**
 * Example application to run the Args command line processor
 * inspired by Robert C. Martin's Clean Code, chapter 14.
 */
public class ApplicationLongForm
{
	public static void main(String[] args)
	{
		try
		{
			String[] ersatzArgs = {"--path", "/tmp", "--file" , "out.txt", "--server", "localhost", "--port", "8080"};
			args = args.length == 0 ? ersatzArgs : args;
			String defs = getOptionDefinitions();
			Schema schema = new SchemaBuilder("ApplicationLongForm").build(defs);
			Args arg = new Args(schema, args);

			String path = arg.getValue("path");
			String file = arg.getValue("file");
			Integer port = arg.getValue("port");
			String server = arg.getValue("server");

			run(path, file, server, port);
		}
		catch (ArgsException e)
		{
			System.out.printf("Argument error: %s\n", e.errorMessage());
		}
	}

	// Reads resource file containing option definitions
	//
	private static String getOptionDefinitions()
	{
		String path = "src/main/resources/argdefs";
		String file = "Option.definitions.example.txt";
		String defs = ArgUtil.readFromResourceFile(path, file);

		return defs;
	}

	private static void run(String directory, String file, String server, int port)
	{
		String fmt = "ApplicationLongForm running with dir=%s/%s, %s:%d\n";
		String msg = String.format(fmt, directory, file, server, port);
		System.out.printf(msg);
	}

}
