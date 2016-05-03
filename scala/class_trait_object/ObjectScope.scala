object One
{
   def mess_inside(instance: Two): Unit =
   {
      println("Before messing: " + instance)
      instance.id = -1
      println("After  messing: " + instance)
   }
}

object Two
{
	val two = new Two

	def mess_inside(instance: Two): Unit =
	{
		println("Before messing: " + instance)
		instance.id = -1
		println("After  messing: " + instance)
	}
}

class One
{
}

class Two(var id:Int = 7)
{
	private var extra = "Pikachu"

	override def toString = id + ":" + extra
}


var x = new Two(33)
println(x)

// Interestingly, any companion object in the same class file
// seems to be able to look inside any other class
//
One.mess_inside(x)

// This causes a stack overflow. Some sort of initialization loop
//
// Two.mess_inside(x)

println(x)


