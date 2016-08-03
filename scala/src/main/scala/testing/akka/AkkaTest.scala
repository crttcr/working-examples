package testing.akka

import akka.testkit.TestKit
import akka.actor.{Actor, ActorSystem, ActorRef, Props}
import akka.testkit.TestLatch
import akka.testkit.TestActorRef
import akka.testkit.ImplicitSender
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FlatSpecLike
import org.scalatest.Matchers._


class AkkaTest(name: String) 
   extends TestKit(ActorSystem(name))
   with ImplicitSender
   with FlatSpecLike
   with BeforeAndAfterAll
{
   override def afterAll() 
   { 
      system.terminate() 
   }
}