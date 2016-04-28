trait Friend 
{
	val name : String	
	def listen = println("I am " + name + " your friend.")
}

def seekHelp(friend : Friend) = friend.listen

class Human(val name : String)
extends Friend
{
}


class Animal(val name : String)
class Dog(override val name : String) 
extends Animal(name)
with Friend

class Cat(override val name : String) 
extends Animal(name)


val peter = new Human("Peter")
peter.listen
seekHelp(peter)

val rover = new Dog("Rover")
rover.listen
seekHelp(rover)

// Objects have traits!
//
// Decorator pattern without ceremony!
//
val snow = new Cat("Snow") with Friend
snow.listen
seekHelp(snow)
