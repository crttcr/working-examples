package j7.nio2;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilesManipulatorTest
{
	FilesManipulator fm = null;

	@Before
	public void setUp() throws Exception
	{
		fm = new FilesManipulator();
	}

	@After
	public void tearDown() throws Exception
	{
		fm = null;
	}

	@Test
	public void testShowFileAttributes()
	{
		boolean ok = fm.showFileAttributes();
		
		assertTrue(ok);
	}
	@Test
	public void testFileCreate()
	{
		String name = "Electric_boogie_3924";
		String posix_perms = "rw-rw----";
		
		Path p = fm.createTempFileWithPermissions(name, posix_perms);
		
		assertNotNull(p);
		assertTrue(Files.exists(p));
		
		try
		{
			Files.delete(p);
		}
		catch (IOException e)
		{
			System.err.println("Failed to delete temporary file: " + e.getMessage());
		}
	}

}
