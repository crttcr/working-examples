package testing.akka

import akka.actor.Props
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorContext

object ChildActor 
{
   // Actor creation function
   //
   def create(context: ActorContext, name: String): ActorRef = 
   {
      val ref = context.actorOf(Props[ChildActor], name)
      ref
   }
}

class ChildActor
   extends Actor
{
   import Messages._
   import ChildActor._

   def receive = 
   {
      case Command => handleCommand()
      case Ack     => handleAck()
   }

   def handleCommand() =
   {
      println(s"Child: Received command. Responding w/ Ack. Sender=$sender")
      sender ! Ack
   }

   def handleAck() =
   {
      println(s"Child: Don't know what to do with this Ack! Sender=$sender")
   }
}
   