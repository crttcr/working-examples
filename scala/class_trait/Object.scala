// Traditional class + Companion Class
//
// Companion MUST be defined in the same class.
// Why? Because companion has access to class' internal details.
// 
// Handles the details for the instances of Car
//
class Car
{
	def turn(direction : String) =
	{
		println("turning " + direction)
	}
}

// "object" Represents the Car class
//
// Singleton handled by the language / class loader
// Called the "companion object"
// Handles the details for the class
//
// One instance of the class that is a mirror class
//
object Car
{
	// Acts like a static method
	//
	def countOfInstances() = 
	{
	}
}

// Using the class outside of main
//
val car = new Car
car.turn("left")

// Looks like data!
//
// Internal DSL in a lightweight syntax
//
car turn "right"
