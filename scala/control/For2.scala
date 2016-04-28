package control

object For1
{
	def main(args: Array[String])
	{
		for (i <- 1 to 5; j <- 6 to 10)
		{
			println("(" + i + ", " + j + ")")
		}
	}
}
