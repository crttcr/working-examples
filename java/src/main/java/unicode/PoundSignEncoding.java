package unicode;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class PoundSignEncoding
{
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
		};


		Charset charset = Charset.defaultCharset();
		System.out.println("Default encoding: " + charset + " (Aliases: " + charset.aliases() + ")");

		String poundSign = "\u00A3";
		for (String encoding : encodings)
		{
			System.out.format("%10s%3s  ", encoding, poundSign);

			byte[] encoded = poundSign.getBytes(encoding);
			for (byte b : encoded)
			{
				System.out.format("%02x ", b);
			}

			System.out.println();
		}
	}

}
