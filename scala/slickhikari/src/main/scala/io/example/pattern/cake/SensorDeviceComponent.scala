package io.example.pattern.cake

trait SensorDeviceComponent 
{
	val sensor: SensorDevice
	
	trait SensorDevice
	{
		def isCoffeePresent: Boolean
	}
  
}