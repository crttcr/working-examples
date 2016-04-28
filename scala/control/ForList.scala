package control

object ForList
{
	def main(args: Array[String])
	{
		var     i = 0
		val aList = List(1,2,3,5,100)

		for (i <- aList)
		{
			println("List item " + i)
		}
	}
}
