package nio;

import java.nio.CharBuffer;

public class BrokenCharBufferTestFromWeb
{

	public static void main(String[] args)
	{
		// Originally this was .allocate(5) which didn't work
		//
		CharBuffer cb1 = CharBuffer.allocate(6);
		cb1.put('j').put('a').put('v').put('a').put('2').put('s');

		cb1.rewind();

		String s = cb1.toString();
		System.out.println(s);

		byte[] bytes = stringToBytesASCII("Abc123. Thank AAA");

		System.out.print("Bytes: [");

		for (byte b : bytes)
		{
			System.out.printf("%d ", b);
		}
		System.out.println("]");
	}

	public static byte[] stringToBytesASCIISlower(String str)
	{
		char[] buffer = str.toCharArray();
		byte[] b = new byte[buffer.length];
		for (int i = 0; i < b.length; i++)
		{
			b[i] = (byte) buffer[i];
		}
		return b;
	}

	public static byte[] stringToBytesASCII(String str)
	{
		byte[] b = new byte[str.length()];
		for (int i = 0; i < b.length; i++)
		{
			b[i] = (byte) str.charAt(i);
		}
		return b;
	}

	public static byte[] stringToBytesUTFCustomFaster(String str)
	{
		byte[] b = new byte[str.length() << 1];
		for (int i = 0; i < str.length(); i++)
		{
			char strChar = str.charAt(i);
			int bpos = i << 1;
			b[bpos    ] = (byte) ((strChar & 0xFF00) >> 8);
			b[bpos + 1] = (byte) ( strChar & 0x00FF);
		}
		return b;
	}
	
	public static byte[] stringToBytesUTFCustom(String str)
	{
		char[] buffer = str.toCharArray();
		byte[] b = new byte[buffer.length << 1];
		for (int i = 0; i < buffer.length; i++)
		{
			int bpos = i << 1;
			b[bpos] = (byte) ((buffer[i] & 0xFF00) >> 8);
			b[bpos + 1] = (byte) (buffer[i] & 0x00FF);
		}
		return b;
	}
}