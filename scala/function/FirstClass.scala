import scala.math._

object Tutorial
{
	def main(args: Array[String])
	{

		// The _ at the end of this line proves that we meant the function
		// and didn't just make a variable name mistake
		//
		val f = log10 _ 

		println(f)
		println(f(1000))

		// Two Map Examples
		//
		List(10.0, 100.0, 1000.0, 4000.0).map(f).foreach(println)
		List(1, 2, 3, 4, 8).map((x : Int) => x * 40).foreach(println)

		List(1, 2, 3, 4, 8).filter(_ % 2 == 0).foreach(println)

		// Two functions that we can pass around
		//
		def times3(num : Int) = num * 3
		def times4(num : Int) = num * 4

		def multIt(f : (Int) => Double, num : Int) =
		{
			f(num)
		}

		val result = multIt(times3, 100)
		printf("3 * 100 = %.1f\n", result)
	}
}


