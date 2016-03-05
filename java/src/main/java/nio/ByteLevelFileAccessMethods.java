package nio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class ByteLevelFileAccessMethods
{

	public static long checksumFileInputStreamWithByteReads(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;
	
		try (FileInputStream f = new FileInputStream(name))
		{
			int b;
			while ( (b = f.read()) != -1 )
			    checksum += b;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
		return checksum;
	}

	public static long checksumFileInputStreamWithByteArrayAccess(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;
	
		try (FileInputStream f = new FileInputStream(name))
		{
			byte[] barray = new byte[array_size];
			int nRead;

			while ( (nRead = f.read( barray, 0, array_size )) != -1 )
			    for ( int i = 0; i<nRead; i++ )
			        checksum += barray[i];
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
		return checksum;
	}

	public static long checksumBufferedInputStreamWithByteReads(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;
	
		try (BufferedInputStream f = new BufferedInputStream(new FileInputStream(name)))
		{
			int b;
			while ( (b = f.read()) != -1 )
			    checksum += b;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
		return checksum;
	}

	public static long checksumBufferedInputStreamWithByteArrayAccess(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;
	
		try (BufferedInputStream f = new BufferedInputStream(new FileInputStream(name)))
		{
			byte[] barray = new byte[array_size];
			int nRead;
			while ( (nRead = f.read( barray, 0, array_size )) != -1 )
			    for ( int i=0; i<nRead; i++ )
			        checksum += barray[i];	
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
		return checksum;
	}

	public static long checksumRandomAccessFileWithByteReads(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;
	
		try ( RandomAccessFile f = new RandomAccessFile( name, "r" ))
		{
			int b;
			while ( (b = f.read()) != -1 )
			    checksum += b;		
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
		return checksum;
	}

	public static long checksumRandomAccessFileWithByteArrayAccess(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;
	
		try ( RandomAccessFile f = new RandomAccessFile( name, "r" ))
		{
			byte[] barray = new byte[array_size];
			int nRead;
			while ( (nRead = f.read( barray, 0, array_size )) != -1 )
			    for ( int i = 0; i < nRead; i++ )
			        checksum += barray[i];	
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	
		return checksum;
	}
	public static long checksumFileChannelWithByteBufferAndByteArrayAccess(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;

		try (FileInputStream f = new FileInputStream(name))
		{
			FileChannel ch = f.getChannel();
			ByteBuffer bb = ByteBuffer.allocate(buffer_size);
			byte[] barray = new byte[array_size];
			int nRead, nGet;

			while ((nRead = ch.read(bb)) != -1)
			{
				if (nRead == 0) continue;
				bb.position(0);
				bb.limit(nRead);
				while (bb.hasRemaining())
				{
					nGet = Math.min(bb.remaining(), array_size);
					bb.get(barray, 0, nGet);
					for (int i = 0; i < nGet; i++)
					{
						checksum += barray[i];
					}
				}
				bb.clear();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		return checksum;
	}

	public static long checksumTwoStrategiesInTandemToFindCalcIssue(String name, int buffer_size, int array_size)
	{
		long checksum_a = 0L;
		long checksum_b = 0L;
		long    count_a = 0L;
		long    count_b = 0L;

		try (FileInputStream f = new FileInputStream(name); BufferedInputStream f1 = new BufferedInputStream(new FileInputStream(name)))
		{
			FileChannel ch = f.getChannel();
			
			// 2nd Approach
			MappedByteBuffer mb = ch.map( MapMode.READ_ONLY, 0L, ch.size( ) );

			int b;
			while ( (b = f1.read()) != -1)
			{
				byte byte_b = mb.get();
				int       c = Byte.toUnsignedInt(byte_b);

				if (b !=  c)
				{
					String s1 = Integer.toBinaryString(b).replace(' ', '0');
					String s2 = Integer.toBinaryString(byte_b).replace(' ', '0');
					String msg = String.format("Bytes at position[%d] are not the same int[%16s] !=  byte[%16s]", count_a, s1, s2);
					System.err.println(msg);
					System.exit(0);
				}
						
				count_a++;
				count_b++;
				checksum_a += b;
				checksum_b += c;
			}

			if (mb.hasRemaining())
			{
				String msg = "Approach 2 is not finished, but approach 1 has cycled through input: " + count_a + ":" + count_b;
				System.err.println(msg);
				System.exit(0);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		if (checksum_a != checksum_b)
		{
			String msg = "Checksums are different: " + checksum_a + ":" + checksum_b;
			System.err.println(msg);
			System.exit(0);
		}

		return checksum_a;
	}

	// long checksumFileChannelWithMappedByteBufferAndByteGets(String name, int buffer_size, int array_size)

	public static long checksumFileChannelmithArrayByteBufferAndByteArrayAccess(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;

		try (FileInputStream f = new FileInputStream(name))
		{
			FileChannel ch = f.getChannel();
			byte[] barray = new byte[array_size];
			ByteBuffer bb = ByteBuffer.wrap(barray);

			int nRead;
			while ((nRead = ch.read(bb)) != -1)
			{
				for (int i = 0; i < nRead; i++)
					checksum += barray[i];
				bb.clear();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		return checksum;
	}

	public static long checksumFileChannelWithDirectBufferAndByteGets(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;

		try (FileInputStream f = new FileInputStream(name))
		{
			FileChannel ch = f.getChannel();
			ByteBuffer bb = ByteBuffer.allocateDirect(array_size);
			int nRead;
			while ((nRead = ch.read(bb)) != -1)
			{
				bb.position(0);
				bb.limit(nRead);

				while (bb.hasRemaining())
					checksum += bb.get();

				bb.clear();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		return checksum;
	}

	public static long checksumFileChannelWithDirectBufferAndByteArrayAccess(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;

		try (FileInputStream f = new FileInputStream(name))
		{
			FileChannel ch = f.getChannel();
			ByteBuffer bb = ByteBuffer.allocateDirect(buffer_size);
			byte[] barray = new byte[array_size];
			int nRead, nGet;
			while ((nRead = ch.read(bb)) != -1)
			{
				if (nRead == 0) continue;
				bb.position(0);
				bb.limit(nRead);
				while (bb.hasRemaining())
				{
					nGet = Math.min(bb.remaining(), array_size);
					bb.get(barray, 0, nGet);
					for (int i = 0; i < nGet; i++)
						checksum += barray[i];
				}
				bb.clear();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		return checksum;
	}

	public static long checksumFileChannelWithMappedByteBufferAndByteGets(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;

		try (FileInputStream f = new FileInputStream(name))
		{
			FileChannel      ch = f.getChannel();
			MappedByteBuffer mb = ch.map( MapMode.READ_ONLY, 0L, ch.size( ) );
			
			while ( mb.hasRemaining() )
				checksum += mb.get( );
	   }
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		return checksum;
	}

	public static long checksumFileChannelWithMappedByteBufferAndArrayAccess(String name, int buffer_size, int array_size)
	{
		long checksum = 0L;

		try (FileInputStream f = new FileInputStream(name))
		{
			FileChannel ch = f.getChannel();
			MappedByteBuffer mb = ch.map( MapMode.READ_ONLY, 0L, ch.size( ) );
			byte[] barray = new byte[array_size];
			int nGet;

			while( mb.hasRemaining() )
			{
				nGet = Math.min( mb.remaining( ), array_size );
				mb.get( barray, 0, nGet );

				for ( int i = 0; i < nGet; i++ )
					checksum += barray[i];
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}

		return checksum;
	}

}
