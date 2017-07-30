package j8.fpij;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReduceStringListToLongestEntry
{

	public static void main(String[] args)
	{
		List<String> list = Arrays.asList("The", "people", "of", "Venezuela", "continue", "to", "suffer", ".");

		final Optional<String> longest =
			list.stream()
				.filter(s -> s != null)
				.reduce((s, ax) ->
			  s.length() >= ax.length() ? s : ax);


		longest.ifPresent(s ->
		{
         final String msg = String.format("The longest string: %s", s);
         System.out.println(msg);
		}
		);
	}
}
