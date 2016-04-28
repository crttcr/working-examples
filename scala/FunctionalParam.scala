val list = List(1, 2, 4, 5, 7, 8, 9, 10, 11)

def totalSelectValues(list: List[Int], selector : Int => Boolean) =
{
	var sum = 0

   list.foreach 
	{ 
		e =>
		if (selector(e))
		{
			sum += e 
		}
	}
	sum
}

// val f_true = e => true

// println("totalSelectValues  = " + totalSelectValues(list, f_true))
println("total true  = " + totalSelectValues(list, { e => true }))
// println("totalSelectValues  = " + totalSelectValues(list, { _ => > 4 }))
