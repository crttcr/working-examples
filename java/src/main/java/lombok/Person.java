package lombok;

import java.time.LocalDate;
import lombok.Data;

/**
 * Person example class.
 * 
 * Uses @Data annotation from Lombok.
 * @Data = @ToString + @EqualsAndHashCode, @Getter, @Setter
 * 
 * 
 * @author reid.dev
 */
@Data
public class Person
{
	private final String      firstName;
	private final String       lastName;
	private final LocalDate dateOfBirth;
	
	public static void main(String[] args)
	{
		LocalDate dob = LocalDate.of(2000, 1, 1);
		Person    joe = new Person("Joe", "Smith", dob);
		
		// Prints
		//
		// Person(firstName=Joe, lastName=Smith, dateOfBirth=2000-01-01)
		//
		System.out.println(joe);
	}
}
