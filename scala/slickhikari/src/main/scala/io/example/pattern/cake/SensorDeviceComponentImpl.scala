package io.example.pattern.cake

trait SensorDeviceComponentImpl 
	extends SensorDeviceComponent
{
	class PotSensor extends SensorDevice
	{
		def isCoffeePresent = true
	}
  
}