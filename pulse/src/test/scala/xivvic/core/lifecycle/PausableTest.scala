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

	it should "start in correct state and not call onPause()/onResume()" in
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

	it should "call the onPause() hook and set isPaused to true" in
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

	it should "return false when called twice without 'resume'" in
	{
		// Arrange
		//
		val f = fixture

		// Act
		//
		val  ok = f.subject.pause()
		val not = f.subject.pause()

		// Assert
		//
		ok	 should be (true)
		not should be (false)
		f.subject.isPaused should be (true)
		f.subject. pauseCounter.get should be (1L)
		f.subject.resumeCounter.get should be (0L)
	}

	it should "call onPause() and set isPaused to true after 'resume'" in
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

	behavior of "resume             :"

	it should "return false and not call onResume() when not paused" in
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

	it should "return true when paused and call nResume() no longer be paused" in
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

	behavior of "pause/resume       :"

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
