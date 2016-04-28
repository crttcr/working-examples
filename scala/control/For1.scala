package control

object For1
{
	def main(args: Array[String])
	{
		var i = 0

		for (i <- 1 to 10)
		{
			// Index arrays with () not []
			//
			println(i)
		}

		println("")
	}
}
