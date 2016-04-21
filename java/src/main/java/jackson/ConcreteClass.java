package jackson;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trivial class implementing abstract class and featuring a factory method
 * that returns the AbstractClass type for a concrete implementation.
 * @author reid.dev
 *
 */
public class ConcreteClass
	extends AbstractClass
{
	
	private final String              id;
	private final String            name;
	private final LocalDateTime birthday;

	private ConcreteClass(String id, String name, LocalDateTime birthday)
	{
		this.id       = id;
		this.name     = name;
		this.birthday = birthday;
	}

	// IMPORTANT!
	// 
	// A factory method identified with the @JsonCreator annotation, must
	// return the declaring class type (or apparently a subtype, but I haven't
	// tried that situation. Although, it is tempting from good information
	// hiding aspect to return the abstract parent type, this will cause Jackson to
	// fail and throw an error about not being able to find the first property,
	// even though it is specifically identified with @JsonProperty annotation.
	//
	// Also, each parameter for the factory method must be annotated with @JsonProperty,
	// and of course, Jackson is case sensitive, so "ID" is not the same as "id."
	//
	// If a property is not found in the JSON being rehydrated, either null or a
	// default primitive value is used.
	//
	@JsonCreator
	public static ConcreteClass factoryMethod(
			@JsonProperty("id")       String id, 
			@JsonProperty("name")     String name, 
			@JsonProperty("birthday") LocalDateTime birthday
			)
	{
		ConcreteClass result = new ConcreteClass(id, name, birthday);
		
		return result;
	}


	@Override
	@JsonProperty("id")
	String id() { return id; }

	@Override
	String name() { return name; }

	@Override
	LocalDateTime birthday() { return birthday; }

	@Override
   public String toString()
   {
   	String msg = String.format("%s.toString() -> id = %s, name = %s", this.getClass().getName(), id(), name());
   	return msg;
   }
   

}
