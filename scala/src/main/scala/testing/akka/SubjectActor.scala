package testing.akka

import akka.actor.Props
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorContext

object SubjectActor 
{
}

class SubjectActor(val cf: (ActorContext, String) => ActorRef)
extends Actor
{
   import Messages._
   import SubjectActor._

   var child: ActorRef = null

   override def preStart()
   {
      child = cf(context, "child")
   }

   def receive = 
   {
      case Command => handleCommand()
      case Ack     => handleAck()
   }

   def handleCommand() =
   {
      println(s"Subject: Received command. Send to child. Sender=$sender")
      child ! Command
   }

   def handleAck() =
   {
      println(s"Subject: Thanks for the Ack!. Sender=$sender")
   }
}
