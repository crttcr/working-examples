package j8.fpij;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * In this example, the scope of the prefix is set by the lexical location of the definition
 * of the function. The variable pgen is a function that given a string returns a predicate
 * that uses that string as the prefix in the String.startsWith(prefix) method.
 *
 * The lexical scope is the second filter's parameter to the apply method, and the definition
 * of pgen closes over this scope to use the (effectively final) argument value within the function body.
 *
 */
public class LexicalScopingExample
{
	public static void main(String[] args)
	{
		List<String> list = Arrays.asList("President", "Pinnochio", "went", "off", "the", "reservation", ".");

		/*
		 Here are 2 more verbose versions of the the same Lambda function being assigned to a variable

		 final Function<String, Predicate<String>> pgen =
		 	(String prefix) ->
		 	{
		 		Predicate<String> check = (String s) -> s.startsWith(prefix);
		 		return check;
		 	};

		 final Function<String, Predicate<String>> pgen = (String prefix) -> (String s) -> s.startsWith(prefix);
		*/

		final Function<String, Predicate<String>> pgen = prefix -> s -> s.startsWith(prefix);

		final List<String> out =
			list.stream()
				.filter(s -> s != null)
				.filter(pgen.apply("P"))
				.collect(Collectors.toList());

		System.out.println("Pbot: " + out);
	}
}
