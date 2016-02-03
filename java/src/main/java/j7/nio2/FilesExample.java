package j7.nio2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FilesExample
{
	public FilesExample()
	{

	}
	
	private static class ExampleVisitor
	extends SimpleFileVisitor<Path>
	{
		private Map<Path, Long> sizes = new ConcurrentHashMap<>();
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
		{
			Long size = attrs.size();
			sizes.put(file, size);
			
			// Could also be TERMINATE | SKIP_SIBLINGS | SKIP_SUBTREE
			//
			return FileVisitResult.CONTINUE;
		}
		
		public Map<Path, Long> getResult()
		{
			return sizes;
		}
		
	}

	/*
	 * NOTE: Returning a Map with Path as key means that you pretty much need to iterate
	 * over the result to get any use from this example method. You won't be able to look
	 * up a particular file without iterating and interrogating the Path keys.
	 */
	public Map<Path, Long> subtreeFileSizes(String start)
	{
		Path              path = Paths.get(start);
		ExampleVisitor visitor = new ExampleVisitor();
		
		try
		{
			Files.walkFileTree(path, visitor);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return visitor.getResult();
	}

	public Map<String, Path> buildDirectoryMap(String start)
	{
		if (start == null)
			return new HashMap<String, Path>();
		
		Map<String, Path> result = new HashMap<>();
		
		Path root = Paths.get(start);
		
		Filter<Path> filter = new DirectoryStream.Filter<Path>()
		{
			@Override
			public boolean accept(Path entry) throws IOException
			{
				boolean exec = Files.isExecutable(entry);
				
				return exec;
			}
		};

		// Note, Files.newDirectoryStream(Path) can be called without a filter
		// to retrieve all files.  You can also pass in a string representing 
		// a glob pattern, e.g. "*.java"
		//
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(root, filter))
		{
			for (Path entry : stream)
			{
				String    name = entry.getFileName().toString();
				Path  abs_path = entry.toRealPath();
				
				result.put(name, abs_path);
			}
		}
		catch (IOException e)
		{
			String msg = "Failed to find all binary files. Returning empty result.";
			System.err.println(msg);
			System.err.println(e.getMessage());
			return new HashMap<String, Path>();
		}
		
		return result;
	}

	public Path resourceLocationForTest()
	{
		Path cwd = Paths.get("src/test/resources", "Sample.File.txt");
		
		Path absolute = cwd.toAbsolutePath();
		
		return absolute;
	}

	public String[] getZipDetails()
	{
	   Path p = Paths.get("/usr/bin/zip");
	   
	   Path   name = p.getFileName();
	   int   count = p.getNameCount();
	   Path parent = p.getParent();
	   Path   root = p.getRoot();
	   Path    sub = p.subpath(0, 2);
	   Path   real = null;
	   
	   try
		{
			real = p.toRealPath();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	   String[] result = new String[6];
	   
	   result[0] = String.format("File Name                  : [%s]", name);
	   result[1] = String.format("# of Name Elements in Path : [%d]", count);
	   result[2] = String.format("Parent Path                : [%s]", parent);
	   result[3] = String.format("Path Root                  : [%s]", root);
	   result[4] = String.format("Subpath, 2 elements deep   : [%s]", sub);
	   result[5] = String.format("Real path, nomalized       : [%s]", real == null ? "I/O FAIL" : real);
	   
	   return result;
	}

	public String readAllBytesOfAFile()
	{
		Path wiki_path = Paths.get("src/test/resources", "Sample.File.txt");
		String  result = null;

		try
		{
			byte[]   content = Files.readAllBytes(wiki_path);

			Charset charset = Charset.forName("UTF-8");
			
			// result = new String(content, "ISO-8859-1");
			result = new String(content, charset);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}

		return result;
	}

}
