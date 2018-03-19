package io.example.pattern.cake

// Service Implementation
//
trait OnOffDeviceComponentImpl 
	extends OnOffDeviceComponent
{
	class Heater extends OnOffDevice
	{
		def  on = println("heater.on")
		def off= println("heater.off")
	}
}