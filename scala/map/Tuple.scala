object Tuple
{
	def main(args: Array[String])
	{
		var tupleMarge = (104, "Marge Simpson", 10.24)

		printf("%s owes us $%.2f\n", tupleMarge._2, tupleMarge._3)

		tupleMarge.productIterator.foreach{ i => println(i) }

		println(tupleMarge.toString())
	}
}

