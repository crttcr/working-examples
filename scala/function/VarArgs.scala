object VarArgs
{
	def main(args: Array[String])
	{
	
		// def functionName (param1:type*) : returnType
		// body
		// [return returnValue]
		//
		def getSum(args : Int*) : Int = 
		{
			var sum : Int = 0
			for (num <- args)
			{
				sum += num
			}

			sum
		}

		println("5 + 4 + 3 + 2 + 1= " + getSum(5, 4, 3, 2, 1))
	}
}


