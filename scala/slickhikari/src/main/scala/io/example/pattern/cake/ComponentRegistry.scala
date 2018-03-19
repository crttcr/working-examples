package io.example.pattern.cake

object ComponentRegistry 
	extends OnOffDeviceComponentImpl
		with SensorDeviceComponentImpl
		with WarmerComponentImpl
{
	val  onOff = new Heater
	val sensor = new PotSensor
	val warmer = new Warmer
}

// A program using the wired up components
//
object Program extends App
{
	val warmer = ComponentRegistry.warmer
	warmer.trigger
}