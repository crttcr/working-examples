// ArrayBuffer requires an import
//
import scala.collection.mutable.ArrayBuffer

// Use an array when the number of elements is known.
// When it's unknown, use an ArrayBuffer
//

object ArrayMulti
{
	def main(args: Array[String])
	{
		val mult_table = Array.ofDim[Int](10,10)
		
		// Populate with the 2d for loop
		//
		for (i <- 0 to 9; j <- 0 to 9)
		{
			mult_table(i)(j) = i * j
		}

      for (i <- 0 to 9)
		{
			for (j <- 0 to 9)
      	{
				printf("(%d, %d) -> %d\n", i, j, mult_table(i)(j)) 
      	}
		}

		println("Sum : " + mult_table.sum)
		println("Min : " + mult_table.min)
		println("Max : " + mult_table.max)
	}
}


