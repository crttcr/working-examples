package control

object ForYield
{
	def main(args: Array[String])
	{
		var     i = 0
		val aList = for 
			{ 
				i <- 1 to 20
				if (i % 2) == 0
			} yield i
	
		for (i <- aList)
		{
			println("Item " + i)
		}
	}
}
