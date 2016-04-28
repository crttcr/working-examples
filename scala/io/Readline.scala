import scala.io.StdIn.{readLine, readInt}

object Tutorial
{
	// Note: Other type specific read operations
	// readInt, readShort, readByte, readLong, readDouble
	//
	def main(args: Array[String])
	{
		val secretNumber = 49
		var numberGuess = 0

		do
		{
			print("Guess a number ")
			numberGuess = readLine.toInt
		}
		while (numberGuess != secretNumber)
	
		printf("You guessed the secret number %d\n", secretNumber)
	}

}

