object Factorial
{
	def main(args: Array[String])
	{
	
		def factorial(number : BigInt) : BigInt = 
		{
			if (number <= 1)
			{
				return 1
			}
			else
			{
				number * factorial(number - 1)
			}
		}

		println("Factorial of 5 = " + factorial(5))
	}
}


