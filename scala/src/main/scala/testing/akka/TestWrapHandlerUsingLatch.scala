package testing.akka

import akka.actor.{ActorRef, ActorContext, Props}
import akka.testkit.{TestKit, TestActorRef, TestLatch}
import scala.concurrent.Await
import scala.concurrent.duration._
import org.scalatest.Matchers._

class TestWrapHandlerUsingLatch
   extends AkkaTest("WrapHandlerUsingLatch")
{
   import Messages._
  
   class Helper
   {
      object ActivitySpy
      {
         val latch = TestLatch(1)
      }
  
      def s_ctor = new SubjectActor(ChildActor.create _)
      def c_ctor = new ChildActor 
      { 
         override def handleCommand() = 
         { 
            ActivitySpy.latch.countDown()
            super.handleCommand() 
         }
      }

      def subject(p: Props) =
      {
         val a = TestActorRef[SubjectActor](p)
         (a, a.underlyingActor)
      }
   }
   
   it should "prove that the message was sent to the child" in new Helper
   {
      // Arrange
      //
      def wrap_child_ctor(context: ActorContext, name: String): ActorRef =
      {
         val props = Props(c_ctor)
         val   ref = context.actorOf(props, name)
         ref
      }
      
      val       p = Props(new SubjectActor(wrap_child_ctor _))
      val (a, ua) = subject(p)
      
      // Act
      // 
      a ! Command
      
      // Assert
      //
      Await.ready(ActivitySpy.latch, 2.seconds)
      ActivitySpy.latch.isOpen should be (true)
   }
}