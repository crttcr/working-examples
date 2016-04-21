package jackson.nested;

import com.fasterxml.jackson.annotation.JsonProperty;

import jackson.AbstractClass;
import jackson.Interface;

/**
 * This class is for exploring Jackson's ability to contain nested classes within
 * a class that is being serialized. Note that Jackson respects the serialization
 * details of the nested classes, for example, they're using the custom LocalDateTime
 * serialiazation that is wired into their class annotations.
 * 
 * @author reid.dev
 */

public class Containing
{
	@JsonProperty
	private String name;
	
	@JsonProperty
	private Interface iface;
	
	@JsonProperty
	private AbstractClass aclass;

	public Containing()
	{
		this.name = "CONTAINER_OF_NULL";
	}

	public Containing(String name, Interface iface, AbstractClass aclass)
	{
		this.name   = name;
		this.iface  = iface;
		this.aclass = aclass;
	}

	@Override
   public String toString()
   {
		String cname = this.getClass().getName();
   	String   fmt = "%s.toString() -> \n\tname = %s, \n\tiface = %s \n\taclass = %s\n";
   	String   msg = String.format(fmt, cname, name, iface, aclass);
   	
   	return msg;
   }
	
}
