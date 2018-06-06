package xivvic.core.lifecycle

import java.util.concurrent.CountDownLatch

/**
 * Startable provides a mechanism to start a component with a lifecycle.
 * When first created and before @see start() is called, the predicate @see hasStarted
 * will return false.
 *
 * Upon calling start, the @see onStart() hook method will be called. Classes incorporating
 * Startable can implement this method to perform arbitrary startup activity.
 *
 * start() should only be called once, after which @see hasStarted will return true.
 * Subsequent calls to @see start() will return false and the @see onStart() method
 * will not be called.
 */
trait Startable
{
	private var latch = new CountDownLatch(1)

	def onStart(): Unit = {}

	def start(): Boolean =
	{
		if (latch.getCount == 0)
		{
			false
		}
		else
		{
			onStart()
			latch.countDown
			true
		}
	}

	def hasStarted = latch.getCount == 0
}
