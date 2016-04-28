object MapExample
{
	def main(args: Array[String])
	{
		// Immutable Map
		// 
		val emps = Map("Manager" -> "Sue Smith", "Admin" -> "Ian Blue")

		if (emps.contains("Manager"))
		{
			printf("Manager : %s\n", emps("Manager"))
		}

		// Mutable version
		//
		val customers = collection.mutable.Map(100 -> "Pauly Jones", 102 -> "Sally Rider")

		customers(101) = "Dave Turnip"
		customers(103) = "Rhino Bedop"

		for ((k, v) <- customers)
		{
			printf("%d : %s\n", k, v)
		}
	}
}

