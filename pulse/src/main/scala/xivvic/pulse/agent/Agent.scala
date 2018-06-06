package xivvic.pulse.agent

import xivvic.core.lifecycle.Stoppable
import xivvic.core.lifecycle.Startable
import xivvic.core.lifecycle.Pausable

trait Agent
	extends Startable
		with Stoppable
		with Pausable
{
}
