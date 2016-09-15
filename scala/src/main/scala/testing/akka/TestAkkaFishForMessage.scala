package testing.akka

import org.scalatest.Matchers._

import akka.actor.Props
import akka.actor.Props
import akka.testkit.{TestKit, TestActorRef, TestLatch}
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.testkit.ImplicitSender
import akka.actor.ActorRef
import akka.actor.ActorContext


class TestAkkaFishForMessage 
   extends AkkaTest("AkkaFishForMessage")
{
   import Messages._
  
   class Helper
   {
      def s_ctor = new SubjectActor(ChildActor.create _)

      def subject(p: Props) =
      {
         val a = TestActorRef[SubjectActor](p)
         (a, a.underlyingActor)
      }
   }
   
   it should "create subject, send a message, and fish for response" in new Helper
   {
      // Arrange
      //
      val p = Props(s_ctor)
      val (a, real) = subject(p)

      val s = "A String"
      
      // Act
      // 
      a ! Command
      
      // Assert
      //
      // For some reason, no message comes back to be identified here.
      // As a result, this test fails. ImplicitSender is mixed into parent class.
      //
      fishForMessage()
      {
         case Ack    => true
         case x: Any => println(s"Received $x"); false
      }
   }

}