package lombok;

import java.util.Set;

@ToString
@Builder(toBuilder = true)
public class PersonWithToBuilder
{
	@Getter private final String firstName;
	@Getter private final String lastName;

	private final @Singular Set<String> nicknames;

	
	// Runs a simple example and produces this output:
	//
	// PersonWithToBuilder(firstName=Joe, lastName=Smith, nicknames=[Jake, Slick])
	// PersonWithToBuilder(firstName=Sue, lastName=Smith, nicknames=[SusieQ])
	//
	//
	public static void main(String[] args)
	{
		PersonWithToBuilderBuilder b = new PersonWithToBuilderBuilder();
		PersonWithToBuilder    smith = b.lastName("Smith").build();
		PersonWithToBuilder      joe = smith.toBuilder().firstName("Joe").nickname("Jake").nickname("Slick").build();
		PersonWithToBuilder      sue = smith.toBuilder().firstName("Sue").nickname("SusieQ").build();
		

		System.out.println(joe);
		System.out.println(sue);
	}
}
