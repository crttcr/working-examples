package unicode;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ByteOrderMark
{
	private final static String bomCharacter = "\uFEFF";
	
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		String[] encodings = 
		{ 
				"Cp1252",        // Windows-1252
				"US-ASCII",      
				"ISO-8859-1",    
				"UTF-8",         
				"UTF-16",        
				"UTF-16BE",      
				"UTF-16LE",
				"UTF-32",        
				"UTF-32BE",        
				"UTF-32LE",        
		};


		for (String encoding : encodings)
		{
			Charset charset = Charset.forName(encoding);
			
			byte[] byteOrderMark = bomCharacter.getBytes(charset);
			
			System.out.format("%16s BOM: ", charset.toString());

			for (byte b : byteOrderMark)
			{
				System.out.format("%02x ", b);
			}

			System.out.println();
		}
	}

}
