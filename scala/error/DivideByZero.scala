
/*
import scala.io.StdIn.{readLine, readInt}
import scala.math._
import scala.collection.mutable.ArrayBuffer
import java.io.PrintWriter
import scala.io.Source
*/

object Tutorial
{
	def main(args: Array[String])
	{
		def divideNums(num1 : Int, num2 : Int) = try
		{
			(num1 / num2)
		}
		catch
		{
			case ex : java.lang.ArithmeticException => "Div by 0 is verbotten"
		}
		finally
		{
			// Do some sort of cleanup here
			//
		}

		println("3 / 0  = " + divideNums(3, 0))
	}
}

