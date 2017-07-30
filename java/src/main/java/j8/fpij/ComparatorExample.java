package j8.fpij;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Note the static import of {@codeCollectors.joining}
 */
public class ComparatorExample
{
	public static void main(String[] args)
	{
		List<Point>          points = randomPoints();
		Comparator<Point> ascending = (a, b) -> a.lengthCompare(b);

		List<Point> short2long = points.stream()
			.sorted(ascending)
			.collect(Collectors.toList());

		// In this instance, we let the Java compiler handle the mundane parameter routing
		// details. The first parameter becomes the object for the lengthCompare() call, and
		// all the rest of the parameters are fed to that method.
		//
		// In this particular method reference example, there can only be one other argument
		// because this is a binary comparison, an it becomes the "other" point.
		//
		List<Point> short2long2 = points.stream()
		.sorted(ComparatorExample.Point::lengthCompare)
		.collect(Collectors.toList());

		Point.printPoints("Sorted short to long using comparator variable", short2long);
		Point.printPoints("Sorted short to long using compararison function", short2long2);
		Point.printPoints("Sorted x, y using comparator chaining ",
			points
				.stream()
				.sorted(comparing(Point.byX).thenComparing(Point.byY))
				.collect(toList())
				);
	}


	@Data
	@Accessors(fluent = true)
	public static class Point
	{
		public static Function<Point, Double> byX = p -> p.x();
		public static Function<Point, Double> byY = p -> p.y();

		private final double x;
		private final double y;

		public double length()
		{
			double a = x * x + y * y;
			return Math.sqrt(a);
		}

		public int lengthCompare(final Point other)
		{
			double mine = length();
			double theirs = other.length();
			double delta = mine - theirs;

			return delta < 0 ? -1: delta > 0 ? 1 : 0;
		}

		public static void printPoints(String msg, List<Point> points)
		{
			Objects.requireNonNull(msg);
			Objects.requireNonNull(points);

			System.out.println(msg);
			points.forEach(System.out::println);
		}
	}

	public static List<Point> randomPoints()
	{
		Random r = new Random();
		List<Point> rv = Arrays.asList(
         new Point(r.nextDouble(), r.nextDouble()),
         new Point(r.nextDouble(), r.nextDouble()),
         new Point(r.nextDouble(), r.nextDouble()),
         new Point(r.nextDouble(), r.nextDouble())
			);

		return rv;
	}
}
