package xivvic.core.lifecycle

import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.scalatest.junit.JUnitRunner
import java.util.concurrent.CountDownLatch

@RunWith(classOf[JUnitRunner])
class StoppableTest
  extends FlatSpecLike
    with BeforeAndAfter
    with Matchers
{

	def fixture = new
	{
		val subject = getSubject
	}

	behavior of "hasStopped:"

	it should "not be stopped immediately after construction and onStop should not have been called" in
	{
		// Arrange
		//
		val f = fixture

		// Assert
		//
		f.subject.hasStopped should be (false)
		f.subject.latch.getCount should be (1L)
	}

	behavior of "Stop     :"

	it should "return true and have correct state when called immediately after construction" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val ok = f.subject.stop()

		// Assert
		//
		ok should be (true)
		f.subject.hasStopped should be (true)
		f.subject.latch.getCount should be (0L)
	}

	it should "return false and still be stopped when start called multiple times" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val a = f.subject.stop()
		val b = f.subject.stop()
		val c = f.subject.stop()

		// Assert
		//
		a should be (true)
		b should be (false)
		c should be (false)
		f.subject.hasStopped should be (true)
		f.subject.latch.getCount should be (0L)
	}

	//////////////////////////////
	// Helpers                  //
	//////////////////////////////

	def getSubject =
	{
		new Stoppable()
		{
			val latch = new CountDownLatch(1)
			override def onStop() = {latch.countDown}
		}
	}
}
