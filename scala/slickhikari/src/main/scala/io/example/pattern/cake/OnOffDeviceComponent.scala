package io.example.pattern.cake

trait OnOffDeviceComponent 
{
	val onOff: OnOffDevice
	
	trait OnOffDevice
	{
		def  on: Unit
		def off: Unit
	}
  
}