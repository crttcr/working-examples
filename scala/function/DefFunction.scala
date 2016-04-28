
import scala.io.StdIn.{readLine, readInt}
import scala.math._
import scala.collection.mutable.ArrayBuffer
import java.io.PrintWriter
import scala.io.Source

object DefFunction
{
	def main(args: Array[String])
	{
	
		// def functionName (param1:type, param2:type) : returnType
		// body
		// [return returnValue]
		//
		def getSum(num1 : Int = 1, num2 : Int = 1) : Int = 
		{
			return num1 + num2
		}

      def getDiff(num1 : Int = 1, num2 : Int = 1) : Int = 
      {
         num1 - num2
      }

		println("5 + 4 = " + getSum(5, 4))
		println("5 - 4 = " + getDiff(5, 4))
		println("4 - 5 = " + getDiff(num2 = 5, num1 = 4))
	}
}


