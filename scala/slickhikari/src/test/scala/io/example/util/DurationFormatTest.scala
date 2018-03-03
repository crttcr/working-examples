package io.example.util

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpecLike
import org.scalatest.Matchers
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit._

@RunWith(classOf[JUnitRunner])
class FooTest
  extends FlatSpecLike
    with Matchers
{
  behavior of "format"
  
  it should "Produce a decent format" in 
  {
    // Act
    //
    val d = Duration(400, MILLISECONDS)
    val f = DurationFormat.format(d)
    
    println(f)
  }

}