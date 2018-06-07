package xivvic.core.lifecycle

import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.scalatest.junit.JUnitRunner
import java.util.concurrent.CountDownLatch

@RunWith(classOf[JUnitRunner])
class StartableTest
  extends FlatSpecLike
    with BeforeAndAfter
    with Matchers
{

	def fixture = new
	{
		val subject = getSubject
	}

	behavior of "initialization code:"

	it should "should start in correct state and not call onStart()" in
	{
		// Arrange
		//
		val f = fixture

		// Assert
		//
		f.subject.hasStarted should be (false)
		f.subject.latch.getCount should be (1L)
	}

	behavior of "Start()            :"

	it should "return true, call onStart() and hasStarted should be true" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val ok = f.subject.start()

		// Assert
		//
		ok should be (true)
		f.subject.hasStarted should be (true)
		f.subject.latch.getCount should be (0L)
	}

	it should "return false and not call onStarted() when called multiple times" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val a = f.subject.start()
		val b = f.subject.start()
		val c = f.subject.start()

		// Assert
		//
		a should be (true)
		b should be (false)
		c should be (false)
		f.subject.hasStarted should be (true)
		f.subject.latch.getCount should be (0L)
	}

	//////////////////////////////
	// Helpers                  //
	//////////////////////////////

	def getSubject =
	{
		new Startable()
		{
			val latch = new CountDownLatch(1)
			override def onStart() = {latch.countDown}
		}
	}
}
