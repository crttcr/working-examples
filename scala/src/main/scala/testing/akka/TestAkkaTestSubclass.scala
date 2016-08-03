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

object TestAkkaTestSubclass
{
}


class TestAkkaTestSubclass 
   extends AkkaTest("AkkaTestSubclass")
{
   class Helper
   {
      def subject(p: Props) =
      {
         val a = TestActorRef[SubjectActor](p)
         (a, a.underlyingActor)
      }
   }
   
   
   it should "do something basic to prove that subclassing AkkaTest works" in new Helper
   {
      // Arrange
      //
      val s = "A String"
      
      // Act
      // 
      val r = s.reverse
      
      // Assert
      //
      r.reverse should equal (s)
   }
}