package lucene;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

public class ProgramUtil
{
	public final static String ARG_CREATE     = "arg.create";
	public final static String ARG_DOC_PATH   = "arg.path.doc";
	public final static String ARG_INDEX_PATH = "arg.path.index";
	
	public static Map<String, Object> args2Map(String[] args)
	{
		Map<String, Object> result = new HashMap<>();
		
		result.put(ARG_CREATE,                        Boolean.TRUE);
		result.put(ARG_DOC_PATH,    "src/main/resources/books.txt");
		result.put(ARG_INDEX_PATH,                  "lucene_index");

		if (args == null || args.length == 0)
			return result;
		
		for (int i = 0; i < args.length; i++)
		{
			String arg = args[i].toLowerCase();

			switch (arg)
			{
			case "-index":
				result.put(ARG_INDEX_PATH, args[++i]);
				break;
			case "-docs":
				result.put(ARG_DOC_PATH, args[++i]);
				break;
			case "-update":
				result.put(ARG_CREATE, Boolean.FALSE);
				break;
			default:
				String msg = String.format("Unhandled program argument: [%s]", arg);
				System.err.println(msg);
				System.exit(-1);
				break;
			}
		}

		return result;
	}

	public static Path getIndexPath(Map<String, Object> argmap)
	{
		String s = (String) argmap.get(ARG_INDEX_PATH);

		if (s == null)
			s = "lucene_index";
		
		final Path path = Paths.get(s);
		
		return path;
	}
	
	
	public static Path getDocumentPath(Map<String, Object> argmap)
	{
		String s = (String) argmap.get(ARG_DOC_PATH);

		Validate.notNull(s, "Need to have the detail of which document to index");

		final Path path = Paths.get(s);
		
		if (! Files.isReadable(path))
		{
			String msg = String.format("Document [%s] does not exist or is not readable", path.toAbsolutePath());
			System.err.println(msg);
			System.exit(-1);
		}
		
		return path;
	}
	
	// Overwrite is true by default
	// (Since this is simple demonstrating indexing, we want to blow away existing
	// and re-index most of the time)
	//
	public static boolean getOverwriteFlag(Map<String, Object> argmap)
	{
		Boolean b = (Boolean) argmap.get(ARG_CREATE);
		
		if (b == null)
			return true;
		
		return b.booleanValue();
	}

}
