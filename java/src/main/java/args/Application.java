package args;

import args.error.ArgsException;
import args.schema.Schema;
import args.schema.SchemaBuilder;

/**
 * Example application to run the Args command line processor
 * inspired by Robert C. Martin's Clean Code, chapter 14.
 */
public class Application
{
	public static void main(String[] args)
	{
		try
		{
			String[] defs = { "-d", "/tmp/foo", "-l", "-p", "8080" };
			Schema schema = new SchemaBuilder("Application").build("l,p#,d*");
			Args arg = new Args(schema, args.length == 0 ? defs : args);

			String path = arg.getValue("d");
			Integer port = arg.getValue("p");
			Boolean logging = arg.getValue("l");

			run(path, port, logging);
		}
		catch (ArgsException e)
		{
			System.out.printf("Argument error: %s\n", e.errorMessage());
		}
	}

	private static void run(String directory, int port, boolean logging)
	{
		String fmt = "Application running with dir=%s, port=%d, logging=%s\n";
		String msg = String.format(fmt, directory, port, logging ? "ENABLED" : "DISABLED");
		System.out.printf(msg);
	}

}
