package j8.fpij;

/**
 * Note the static import of {@codeCollectors.joining}
 */
public class StringToChars
{
	public static void main(String[] args)
	{
		String s = "Excel";

		s.chars().forEach(System.out::println);
		s.chars().forEach(StringToChars::printAsChar);
		s.chars()
			.mapToObj(c -> Character.valueOf((char) c))     // NOTE: mapToObj()
			.forEach(System.out::println);

		s.chars()
		.filter(c -> Character.isUpperCase(c))
		.forEach(c -> printAsChar(c));
	}

	private static void printAsChar(int c)
	{
		System.out.println((char) c);
	}
}
