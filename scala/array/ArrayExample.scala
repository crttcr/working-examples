
// ArrayBuffer requires an import
//
import scala.collection.mutable.ArrayBuffer

// Use an array when the number of elements is known.
// When it's unknown, use an ArrayBuffer
//

object ArrayExample
{
	def main(args: Array[String])
	{

		// With Array, the length is fixed and required
		//
		val favorites = new Array[Int](20)
		val friends   = Array("Bob", "Tom")

		// Set a value in the array
		//
		friends(0) = "Sue"

		println("Best friend = " + friends(0))
	
		// With ArrayBuffer, the length is optional
		//
		val others = ArrayBuffer[String]()
		others.insert(0, "Rastus")
	
		// Add to the next available slot
		//
		others  += "Demeter"

		// Add multiple items with '++=' 
		//
		others ++= Array("Hera", "Juno")

		// Or with insert
		//
		others.insert(2, "Sally", "Slappy")

		// Insert then remove 2 items
		//
		others.insert(0, "Dang", "Fool")
		others.remove(0, 2)

		println("Others = " + others)

		var o : String = ""
		for (o <- others)
			println(o)

		// Fill an array 
		//
		for (i <- 0 to (favorites.length -1))
		{
			favorites(i) = i
			print(favorites(i) + " ")
		}

		println("")

		// Quick and easy map function
		//
		val double_favs = for (n <- favorites) yield 2 * n
		double_favs.foreach(print)
		println("")
		
		val fav_fours = for (n <- favorites if n % 4 == 0) yield n
		fav_fours.foreach(println)

      println("Sum : " + favorites.sum)
      println("Min : " + favorites.min)
      println("Max : " + favorites.max)

		val sorted = favorites.sortWith(_>_)
	
		// Join-like operation
		//
		println(sorted.deep.mkString(", "))
	}
}


