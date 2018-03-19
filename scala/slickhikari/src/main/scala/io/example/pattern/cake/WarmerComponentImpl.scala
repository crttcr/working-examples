package io.example.pattern.cake

// Service declaring 2 dependencies that it wants injected
//
trait WarmerComponentImpl 
{
	this: SensorDeviceComponent with OnOffDeviceComponent =>
		
	class Warmer
	{
		def trigger = 
		{
			if (sensor.isCoffeePresent) onOff.on
			else onOff.off
		}
	}
  
}