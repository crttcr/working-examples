package j7.nio2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Objects;
import java.util.Set;

public class FilesManipulator
{

	/*
	 * NOTE: posix_perms looks something like this "rw-rw----")
	 * for a chmod 660 file.
	 * 
	 */
	public Path createTempFileWithPermissions(String name, String posix_perms)
	{
		Objects.requireNonNull(name);
		Objects.requireNonNull(posix_perms);
		
		Path target = Paths.get("/tmp", name);
		
		Set<PosixFilePermission>               perms = PosixFilePermissions.fromString(posix_perms);
		FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);

		Path result = null;
		try
		{
			result = Files.createFile(target, attr);
		}
		catch (IOException e)
		{
			return null;
		}
		
		return result;
	}
	
	public boolean showFileAttributes()
	{
		try 
		{
			Path zip = Paths.get("/usr/bin/zip");

			System.out.println("Last modification :  " + Files.getLastModifiedTime(zip));
			System.out.println("File size         :  " + Files.size(zip));
			System.out.println("Is symbolic link  :  " + Files.isSymbolicLink(zip));
			System.out.println("Is directory      :  " + Files.isDirectory(zip));
			System.out.println("BulkAttributes    :  " + Files.readAttributes(zip, "*"));
		}
		catch (IOException e)
		{
			System.err.println("Encountered problem: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	// There's no need to have a method for this, but this is here
	// as a reminder of how to use Files to move a file and what type
	// of options are available.
	//
	public void move()
	{
		Path source = null; 
		Path target = null;
		try
		{
			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.ATOMIC_MOVE);
		}
		catch (IOException e)
		{
			// TODO Handle Exception
			//
			e.printStackTrace();
		}
	}
}
