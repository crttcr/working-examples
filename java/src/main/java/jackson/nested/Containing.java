package jackson.nested;

import com.fasterxml.jackson.annotation.JsonProperty;

import jackson.AbstractClass;
import jackson.Interface;


public class Containing
{
	@JsonProperty
	private String name;
	
	private Interface iface;
	
	private AbstractClass aclass;
	
}
