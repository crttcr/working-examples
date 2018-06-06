package xivvic.core.lifecycle

import java.util.concurrent.atomic.AtomicBoolean

trait Pausable
{
	private var state = new AtomicBoolean()

	def onPause():  Unit = {}
	def onResume(): Unit = {}

	def pause(): Boolean =
	{
		if (state.get)
		{
			false
		}
		else
		{
			onPause()
			state.set(true)
			true
		}
	}

	def resume(): Boolean =
	{
		if (state.get)
		{
			onResume()
			state.set(false)
			true
		}
		else
		{
			false
		}
	}

	def isPaused = state.get
}
