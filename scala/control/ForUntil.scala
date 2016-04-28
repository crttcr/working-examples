package control

object ForUntil
{
	def main(args: Array[String])
	{
		var i = 0
		val capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

		for (i <- 0 until capitalLetters.length)
		{
			print(capitalLetters(i))
		}
	}
}
