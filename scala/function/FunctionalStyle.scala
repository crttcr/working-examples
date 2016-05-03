
val list = List(1, 3, 5, 7, 9)

// Imperative Style
//
// list is a val -- equivalent to final
// 
// In scala, lists are immutable, unless you ask for mutable list
//
def totalI(list : List[Int]) =
{
	var sum = 0
	
	// FYI: i is a val, not a var
	//
	for (i <- list)
	{
		sum += i   // Constantly modifying variable
	}

	sum
}


println("totalI  = " + totalI(list))

// Functional Style
//
def totalf1(list: List[Int]) =
{
	list.foldLeft(0) { (carryOver, e) => carryOver + e }
}

println("totalf1 = " + totalf1(list))


// Functions can define functions
//
def totalf2(list: List[Int]) =
{
	def add(a : Int, b : Int) = a + b

	list.foldLeft(0) { add }
}

println("totalf2 = " + totalf2(list))


// Functions can define functions
//
def totalf3(list: List[Int]) =
{
	var sum = 0
   list.foreach { e => sum += e }
	sum
}

println("totalf3 = " + totalf3(list))
