package control

object BreakContinue
{
	def main(args: Array[String])
	{
		def printPrimes()
		{
			val primeList = List(1, 2, 3, 5, 7, 11, 13, 17, 19)
			for (i <- primeList)
			{
				// This is not equivalent to a break, because it
				// terminates the function, not just the loop.
				//
				if (i == 13)
				{
					return
				}

				// Somewhat equivalent to continue, but requires doing all
				// work in side the body of the if statement
				//
				if (i != 1)
				{
					println(i)
				}
			}

			println("This line is never printed.")
		}

		printPrimes
	}
}
