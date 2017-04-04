package args;

/**
 * Command line processor highly inspired by Robert C. Martin's Clean Code, chapter 14.
 *
 */

public class Application
{
	public static void main(String[] args)
	{
		try
		{
			String[] defs = { "-d", "/tmp/foo", "-l", "-p", "8080" };
			Args arg = new Args("l,p#,d*", args.length == 0 ? defs : args);

			boolean logging = arg.getBoolean('l');
			int port = arg.getInteger('p');
			String directory = arg.getString('d');

			run(directory, port, logging);
		}
		catch (ArgsException e)
		{
			System.out.printf("Argument error: %s\n", e.errorMessage());
		}
	}

	private static void run(String directory, int port, boolean logging)
	{
		System.out.printf("Application running with dir=%s, port=%d, %s\n", directory, port, logging ? "LOGGING" : "NOLOG");
	}

}
