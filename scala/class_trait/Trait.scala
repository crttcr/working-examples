import scala.io.StdIn.{readLine, readInt}
import scala.math._
import scala.collection.mutable.ArrayBuffer
import java.io.PrintWriter
import scala.io.Source

// Traits are like Java interfaces, except they
// can have default behavior (like Java 8)
//
object ScalaTutorial
{
	def main(args: Array[String])
	{
		val superman = new Superhero("Superman")
		
		
		println(superman.fly)
		println(superman.hitByBullet)
		println(superman.ricochet(240))

	} // END OF MAIN

	trait Flyable 
	{
		def      fly : String
	}

	trait Bulletproof 
	{
		def hitByBullet : String

		def ricochet(startSpeed : Double) : String = 
		{
			"Bullet ricochets at a speed of %.1f ft/sec".format(startSpeed * .75)
		}
	}

	// The 2nd trait being added requires the "with" keyword.
	//
	class Superhero(val name : String)
	extends Flyable with Bulletproof
	{
		def fly = "%s flies through the air".format(this.name)

		def hitByBullet = "The bullet bounces off of %s".format(this.name)
	}

} // END OF ScalaTurorial

