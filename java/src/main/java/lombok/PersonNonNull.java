package lombok;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PersonNonNull
{
	private final String firstName;
	private final String lastName;

	@NonNull
	private final LocalDate dateOfBirth;

	
	// Runs a simple example and produces this output:
	//
	// As expected, an exception was thrown because non-null field (dob) was null.
	// PersonNonNull(firstName=Joe, lastName=null, dateOfBirth=2000-01-01)
	//
	//
	public static void main(String[] args)
	{
		try
		{
			PersonNonNull joe = new PersonNonNull("Joe", "Smith", null);
			if (joe != null)
			{
				System.err.println("FAIL:  Null check failed to throw exception. Abort.");
				System.exit(-1);
			}
		}
		catch(NullPointerException npe)
		{
			// Expected to have an exception to catch
			//
			System.out.println("As expected, an exception was thrown because non-null field (dob) was null.");
		}

		LocalDate     dob = LocalDate.of(2000, 1, 1);
		PersonNonNull joe = new PersonNonNull("Joe", null, dob);

		System.out.println(joe);
	}
}
