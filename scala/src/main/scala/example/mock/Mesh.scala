package example.mock

/** 
 *  This is the interface to mock
 *  
 */

trait Mesh
{
   def isDone(a: Set[Artifact], t: Set[Task]): Boolean
   def startableTasks(a: Set[Artifact], t: Set[Task]): Set[Task]
   
}