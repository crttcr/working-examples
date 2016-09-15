package example.mock

import org.scalatest.Matchers
import org.scalatest.FlatSpec

class UnitTest(component: String)
   extends FlatSpec
   with Matchers
{
   behavior of component
  
}