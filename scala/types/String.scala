object String
{
	def main(args: Array[String])
	{
		var   s =  "I ate a dragon-flavored Pop Tart"

		println("15th char     " + s(15))
		println("String length " + s.length)
		println("String concat " + s.concat(" yesterday"))
		println("dragon index  " + s.indexOf("dragon"))

		val sArray = s.toArray
		for (v <- sArray)
			print(v + " ")

		println("")
	}
}

