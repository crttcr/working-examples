package j8.fpij;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.List;

/**
 * Note the static import of {@codeCollectors.joining}
 */
public class UpperCaseAndJoin
{
	public static void main(String[] args)
	{
		List<String> list = Arrays.asList("Really", "these", "are", "all", "homegrown");

		final String out =
			list.stream()
				.map(String::toUpperCase)
				.collect(joining(", "));

		System.out.println("UP: " + out);
	}
}
