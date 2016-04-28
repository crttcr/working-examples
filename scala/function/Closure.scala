import scala.math._

object Tutorial
{
	def main(args: Array[String])
	{
		// Closure -- function that depends on a variable declared
		// outside of that function's lexical scope
		//

		val divisorVal = 5
	
		val divisor5 = (num : Double) => num / divisorVal

		println("35 / 5 = " + divisor5(35.0))

	}
}


