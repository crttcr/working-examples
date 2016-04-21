package jackson;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trivial class implementing the Interface interface.
 * Also features a factory method that returns the Interface a concrete implementation.
 * 
 * @author reid.dev
 *
 */
public class InterfaceImplementation
	implements Interface
{
	private final String              id;
	private final String            name;
	private final LocalDateTime birthday;

	private InterfaceImplementation(String id, String name, LocalDateTime birthday)
	{
		this.id       = id;
		this.name     = name;
		this.birthday = birthday;
	}
	
	// IMPORTANT!
	// 
	// A factory method identified with the @JsonCreator annotation, must
	// return the defining type. Although, it is tempting from good information
	// hiding aspect to return the interface type, this will cause Jackson to
	// fail and throw an error about not being able to find the first property,
	// even though it is specifically identified with @JsonProperty annotation.
	//
	// Also, each parameter for the factory method must be annotated with @JsonProperty,
	// and of course, Jackson is case sensitive, so "ID" is not the same as "id."
	//
	@JsonCreator
	public static InterfaceImplementation factoryMethod(
			@JsonProperty("id")       String id, 
			@JsonProperty("name")     String name, 
			@JsonProperty("birthday") LocalDateTime birthday
			)
	{
		InterfaceImplementation result = new InterfaceImplementation(id, name, birthday);
		
		return result;
	}


	@Override
	@JsonProperty("id")
	public String id() { return id; }

	@Override
	public String name() { return name; }

	@Override
	public LocalDateTime birthday() { return birthday; }

	@Override
   public String toString()
   {
   	String msg = String.format("%s.toString() -> id = %s, name = %s", this.getClass().getName(), id(), name());
   	return msg;
   }
   

}
