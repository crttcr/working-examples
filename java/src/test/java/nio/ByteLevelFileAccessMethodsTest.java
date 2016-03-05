package nio;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class exists to test different methods of reading a file, byte-by-byte.
 * It was inspired by a Nadeau software consulting page:
 * 
 * http://nadeausoftware.com/articles/2008/02/java_tip_how_read_files_quickly
 * 
 * which benchmarks 13 ways to read bytes from a file. The methods in the class
 * {@link ByteLevelFileAccessMethods} were slightly modified from Nadeau's code.
 * 
 * NOTE: The checksums computed return two different results, depending on the
 * method used to read bytes from the file. After digging into the difference,
 * there are a number of useful observations to keep in mind:
 * 
 *     A Java byte is always signed, giving it the range -128 to 127
 *     There is no language-level facility for using unsigned bytes
 *     Java 8 has a library method: int i = Byte.toUnsignedInt(some_byte) to facilitate
 *        the conversion of a byte to an unsigned byte's equivalent number stored as an int
 *     File reading methods either return an int or a byte
 *     Those that return an int return the equivalent of an unsigned byte value
 *     Those that return a byte return the same bit pattern, but Java treats it as signed
 *     Thus the checksum is different depending on whether you're adding int or byte
 *     This only matters when reading files that use the leftmost bit of the byte
 *     Thus binary files will have a different checksum depending on the read method
 *     Text files that stick with US-ASCII subset of UTF-8 will produce the same checksum
 * 
 * @author reid.dev
 *
 */
public class ByteLevelFileAccessMethodsTest
{

	private int         array_size = 1024;
	private int        buffer_size = 1024 << 4;
	private long     byte_checksum = -454615L;
	private long      int_checksum = 11013827369L;
	
	private String file_name = Paths.get("/Users/reid.dev/Desktop/GraphClass/neo4j-community_windows-x64_2_2_3.exe").toAbsolutePath().toString();
//	private String file_name = Paths.get("src/test/resources/FL_insurance_sample.csv").toAbsolutePath().toString();
	
	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	// Test is typically ignored because results are really slow!
	//
	@Ignore
	@Test
	public void testChecksumFileInputStreamWithByteReads()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumFileInputStreamWithByteReads(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumFileInputStreamWithByteReads", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(int_checksum, cs);
	}

	@Test
	public void testChecksumFileInputStreamWithByteArrayAccess()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumFileInputStreamWithByteArrayAccess(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumFileInputStreamWithByteArrayAccess", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	@Test
	public void testChecksumBufferedInputStreamWithByteReads()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumBufferedInputStreamWithByteReads(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumBufferedInputStreamWithByteReads", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(int_checksum, cs);
	}

	@Test
	public void testChecksumBufferedInputStreamWithByteArrayAccess()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumBufferedInputStreamWithByteArrayAccess(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumBufferedInputStreamWithByteArrayAccess", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	// Test is typically ignored because results are really slow!
	//
	@Ignore
	@Test
	public void testChecksumRandomAccessFileWithByteReads()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumRandomAccessFileWithByteReads(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumRandomAccessFileWithByteReads", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(int_checksum, cs);
	}

	@Test
	public void testChecksumRandomAccessFileWithByteArrayAccess()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumRandomAccessFileWithByteArrayAccess(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumRandomAccessFileWithByteArrayAccess", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	@Test
	public void testChecksumFileChannelWithByteBufferAndByteArrayAccess()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumFileChannelWithByteBufferAndByteArrayAccess(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumFileChannelWithByteBufferAndByteArrayAccess", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	@Test
	public void testChecksumFileChannelWithArrayByteBufferAndByteArrayAccess()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumFileChannelWithByteBufferAndByteArrayAccess(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumFileChannelWithArrayByteBufferAndByteArrayAccess", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	@Test
	public void testChecksumFileChannelWithDirectBufferAndByteGets()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumFileChannelWithDirectBufferAndByteGets(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumFileChannelWithDirectBufferAndByteGets", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	@Test
	public void testChecksumFileChannelWithDirectBufferAndByteArrayAccess()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumFileChannelWithDirectBufferAndByteArrayAccess(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumFileChannelWithDirectBufferAndByteArrayAccess", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	@Test
	public void testChecksumFileChannelWithMappedByteBufferAndByteGets()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumFileChannelWithMappedByteBufferAndByteGets(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumFileChannelWithMappedByteBufferAndByteGets", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	@Test
	public void testChecksumFileChannelWithMappedByteBufferAndArrayAccess()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumFileChannelWithMappedByteBufferAndArrayAccess(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "testChecksumFileChannelWithMappedByteBufferAndArrayAccess", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(byte_checksum, cs);
	}

	@Test
	public void debugChecksumTwoStrategiesInTandemToFindCalcIssue()
	{
		// Arrange
		//
		long start = System.nanoTime();
		
		
		// Act
		//
		long    cs = ByteLevelFileAccessMethods.checksumTwoStrategiesInTandemToFindCalcIssue(file_name, buffer_size, array_size);
		long   end = System.nanoTime();
		long   dur = (end - start) / 1000 / 1000;
		String msg = String.format("Method=[%60s] Milliseconds=[%8d] Result=[%12d]", "checksumTwoStrategiesInTandemToFindCalcIssue", dur, cs);
		System.out.println(msg);
		
		// Assert
		//
		assertEquals(int_checksum, cs);
	}

}
