object Tutorial
{
	def main(args: Array[String])
	{
		val   name =  "Frank"
		val    age =      34
		val weight =     200.2

		println(s"Hello $name")
		println(f"You will be ${age + 10} in 10 years")
		println(f"You will weigh ${weight + 52.5}%.2f in 10 years")
	}
}

