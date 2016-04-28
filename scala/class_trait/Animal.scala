
import scala.io.StdIn.{readLine, readInt}
import scala.math._
import scala.collection.mutable.ArrayBuffer
import java.io.PrintWriter
import scala.io.Source

object ScalaTutorial
{
	def main(args: Array[String])
	{
		println("Start of main")
	
		val rover = new Animal
		rover.setName("Rover")
		rover.setSound("Woof")

		printf("%s says %s\n", rover.getName, rover.getSound)

		val whiskers = new Animal("Whiskers", "Meow")
		printf(s"${whiskers.getName} with id ${whiskers.id} says ${whiskers.getSound}\n")

		val mr_fuzzy = new Animal
		println(mr_fuzzy.toString)

		val fido = new Dog("Fido", "Burb", "Snuffle")
		println(fido.toString)
	} // END OF MAIN

	// Defining the default Constructor
	// right after your decl class
	//
	class Animal(var name: String, var sound: String)
	{
		this.setName(name)
		
		val id = Animal.newIdNumber

		def getName()  : String = name
		def getSound() : String = sound

		def setName(name : String)
		{
			if (!(name.matches(".*\\d+.*")))
				this.name = name
			else
				this.name = "No name"
		}

      def setSound(sound : String)  
      {
      	this.sound = sound
      }

      def this()
      {
         this("No name", "No sound")
      }  

		def this(name : String)
		{	
			this("No name", "No sound")
			this.setName(name)
		}

		override def toString() : String = 
		{
			return "%s with id %d says %s".format(this.name, this.id, this.sound)
		}

		
	}

	// Companion Object for the Animal class
	//
	// This is how you can create STATIC fields and methods
	//
	object Animal
	{
		private var idNumber = 0
		private def newIdNumber = { idNumber += 1; idNumber }
	}

	class Dog (name: String, sound: String, growl: String)
	extends Animal(name, sound)
	{
      def this(name: String, sound: String)
      {
         this("Temp", sound, "No growl")
         this.setName(name)
      }

		def this(name: String)
		{
			this("Temp", "No sound", "No growl")
			this.setName(name)
		}

		def this()
		{
			this("No name", "No sound", "No growl")
		}

		override def toString() : String = 
		{
			return "%s with id %d says %s or %s".format(this.name, this.id, this.sound, this.growl)
		}
	}

} // END OF ScalaTurorial

