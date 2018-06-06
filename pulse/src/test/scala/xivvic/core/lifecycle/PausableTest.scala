package xivvic.core.lifecycle

import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.Matchers
import org.scalatest.FlatSpecLike
import org.scalatest.junit.JUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

@RunWith(classOf[JUnitRunner])
class PausableTest
  extends FlatSpecLike
    with BeforeAndAfter
    with Matchers
{

	def fixture = new
	{
		val subject = getSubject
	}

	behavior of "initialization code:"

	it should "not be paused immediately after construction and onPause()/onResume() should not have been called" in
	{
		// Arrange
		//
		val f = fixture

		// Assert
		//
		f.subject.isPaused should be (false)
		f.subject. pauseCounter.get should be (0L)
		f.subject.resumeCounter.get should be (0L)
	}

	behavior of "pause              :"

	it should "return true when first called, call the onPause() hook, and have the correct state" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val ok = f.subject.pause()

		// Assert
		//
		ok should be (true)
		f.subject.isPaused should be (true)
		f.subject. pauseCounter.get should be (1L)
		f.subject.resumeCounter.get should be (0L)
	}

	it should "return true when called after resume, call the onPause() hook, and have the correct state" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val a = f.subject.pause()
		val b = f.subject.resume()
		val c = f.subject.pause()

		// Assert
		//
		a should be (true)
		c should be (true)
		f.subject.isPaused should be (true)
		f.subject. pauseCounter.get should be (2L)
		f.subject.resumeCounter.get should be (1L)
	}

	behavior of "resume            :"

	it should "return false when first called, not call either hook, and have the correct state" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val ok = f.subject.resume()

		// Assert
		//
		ok should be (false)
		f.subject.isPaused should be (false)
		f.subject. pauseCounter.get should be (0L)
		f.subject.resumeCounter.get should be (0L)
	}

	it should "return true when called after pause, call the onResume() hook, and have the correct state" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val a = f.subject.pause()
		val b = f.subject.resume()

		// Assert
		//
		a should be (true)
		b should be (true)
		f.subject.isPaused should be (false)
		f.subject. pauseCounter.get should be (1L)
		f.subject.resumeCounter.get should be (1L)
	}

	behavior of "resume/resume     :"

	it should "support repeated calls to pause and resume" in
	{
		// Arrange
		//
		val f = fixture

		// Act & Assert
		//
		for (i <- 1 to 10)
		{
			val a = f.subject.pause()
			a should be (true)
			f.subject.isPaused should be (true)

			val b = f.subject.resume()
			b should be (true)
			f.subject.isPaused should be (false)

			f.subject. pauseCounter.get should be (i)
			f.subject.resumeCounter.get should be (i)
		}
	}

	//////////////////////////////
	// Helpers                  //
	//////////////////////////////

	def getSubject =
	{
		new Pausable()
		{
			val  pauseCounter = new AtomicInteger
			val resumeCounter = new AtomicInteger

			override def  onPause() = { pauseCounter.incrementAndGet()}
			override def onResume() = {resumeCounter.incrementAndGet()}
		}
	}
}
