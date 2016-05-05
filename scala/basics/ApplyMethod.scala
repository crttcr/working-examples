// The apply() method is essentially a shortcut,
//
// It provides a method that can be invoked with just ()
// and no name
//
// One potential disadvantage to making a method be the default one is if 
// it makes the code look odd. Accessing the default method should be natural, 
// like the accessor method for lists. Try to only use the apply method 
// where it makes sense, like an accessor method for a list.
//
class Multiplier(factor: Int) 
{
	def apply(input: Int) = input * factor
}

val doubler = new Multiplier(2)
val tripler = new Multiplier(3)


val a = doubler.apply(2200)
val b = tripler(   9)

println(a)
println(b)


// Another example
val list = List('a', 'b', 'c')
val item = list(2)

println("The item with index 2 is " + item)

