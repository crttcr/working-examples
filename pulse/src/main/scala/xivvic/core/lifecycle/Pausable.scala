package xivvic.core.lifecycle

import java.util.concurrent.atomic.AtomicBoolean

trait Pausable
{
	private var isPaused = new AtomicBoolean()

	def pause(): Boolean =
	{
		if (isPaused.get)
		{
			false
		}
		else
		{
			isPaused.set(true)
			true
		}
	}

	def resume(): Boolean =
	{
		if (isPaused.get)
		{
			isPaused.set(true)
			true
		}
		else
		{
			false
		}
	}

}
