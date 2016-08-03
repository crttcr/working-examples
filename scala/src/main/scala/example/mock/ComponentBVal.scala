package example.mock

import org.scalamock.proxy.ProxyMockFactory
import org.scalamock.scalatest.MockFactory

class ComponentBVal
   extends UnitTest("Component")
   with MockFactory
{
   it should "return true when isDone is called." in
   {
      val m = stub[Mesh]
      val a = Set.empty[Artifact]
      val t = Set.empty[Task]

      (m.isDone _).when(a, t).returns(true)
      
      val d = m.isDone(a, t)
      
      d shouldBe true
   }

   it should "return a set of tasks when startableTasks is called" in
   {
      val m = stub[Mesh]
      val a = Set.empty[Artifact]
      val completed = Set.empty[Task]
      val t = stub[Task]
      val rv: Set[Task] = Set(t)

      (m.startableTasks _).when(a, completed) returns(rv)
      
      val  tasks = m.startableTasks(a, completed)
      
      tasks should equal (rv)
   }

   it should "provide mesh functionality for director BVal" in
   {
      val m = stub[Mesh]
      val a = Set.empty[Artifact]
      val t = Set.empty[Task]

      (m.isDone _).when(a, t).returns(true)
      (m.startableTasks _).when(a, t) returns(t)
      
      val d = m.isDone(a, t)
      val  tasks = m.startableTasks(a, t)
      
      d shouldBe true
      tasks should equal (t)
//      m.verify(name)
   }
}