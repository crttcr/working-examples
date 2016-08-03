package example.mock

import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import org.scalatest.Matchers

class FunctionMockSpec 
   extends FlatSpec 
   with MockFactory
   with Matchers
{
   it should "mock a simple function call" in 
   {
      val m = mockFunction[Int, String]
      m expects (43) returning "forty three"
      val result = m(43)
      result shouldBe "forty three"
   }
   
   it should "allow multiple calls to the function" in
   {
      val m = mockFunction[Int, String]

      inAnyOrder
      {
         m.expects(1).returns("one")
         m.expects(2).returns("two").repeat(2)
      }
      
      val r1 = m(2)
      val r2 = m(1)
      val r3 = m(2)
      
      r1 shouldBe "two"
      r2 shouldBe "one"
      r3 shouldBe "two"
   }

   /** 
    *  This test does indeed fail
    *  
    */
//   it should "fail with no expectations" in 
//   {
//      val m = mockFunction[Int, String]
//      val result = m(43)
//      result shouldBe null
//   }
}