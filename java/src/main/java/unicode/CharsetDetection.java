package unicode;

import java.io.UnsupportedEncodingException;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class CharsetDetection
{

	public static void main(String[] args) 
		throws UnsupportedEncodingException
	{
		byte[] thisAppCanBreak = "this app can break".getBytes("ISO-8859-1");

		CharsetDetector detector = new CharsetDetector();

		detector.setText(thisAppCanBreak);

		String fmt = "%10s %10s %8s%n";

		System.out.format(fmt, "CONFIDENCE", "CHARSET", "LANGUAGE");

		for (CharsetMatch match : detector.detectAll())
		{
			int confidence = match.getConfidence();
			String    name = match.getName();
			String    lang = match.getLanguage();
			
			System.out.format(fmt, confidence, name, lang);
		}
	}

}
