package lombok;

import java.util.Set;

@ToString
@Builder
public class PersonWithBuilder
{
	@Getter private final String firstName;
	@Getter private final String lastName;

	private final @Singular Set<String> nicknames;

	
	// Runs a simple example and produces this output:
	//
	// PersonWithBuilder(firstName=Joe, lastName=Smith, nicknames=[J, Jackson, Einstein])
	//
	//
	public static void main(String[] args)
	{
		PersonWithBuilderBuilder b = new PersonWithBuilderBuilder();
		
		b.firstName("Joe").lastName("Smith").nickname("J").nickname("Jackson").nickname("Einstein");
		
		PersonWithBuilder joe = b.build();

		System.out.println(joe);
	}
}
