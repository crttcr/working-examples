package xivvic.core.lifecycle

import java.util.concurrent.CountDownLatch

trait Stoppable
{
	private var latch = new CountDownLatch(1)

	def onStop(): Unit = {}

	def stop(): Boolean =
	{
		if (latch.getCount == 0)
		{
			false
		}
		else
		{
			onStop()
			latch.countDown
			true
		}
	}

	def hasStopped = latch.getCount == 0
}
