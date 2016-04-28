import java.io.PrintWriter
import scala.io.Source

object Tutorial
{
	def main(args: Array[String])
	{
		val      f = "text.txt"
		val writer = new PrintWriter(f)

		writer.write("Here is some cool text.\nAnd some more.\n")
		writer.write("Did you see that?\nI can do this all day.")
		writer.close()

		val text     = Source.fromFile(f, "UTF-8")
		val iterator = text.getLines
		
		for (line <- iterator)
			println(line)
	}

}

