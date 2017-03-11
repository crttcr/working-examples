package spring.data.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity                         // Annotation required for Spring + Hibernate
public class Spitter
{
	// Annotations required for Spring + Hibernate
	//
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;
	private String fullname;

	public static Spitter copyFrom(Long id, Spitter prototype)
	{
		String u = prototype.getUsername();
		String p = prototype.getPassword();
		String f = prototype.getFullname();

		Spitter rv = new Spitter(id, u, p, f);
		return rv;
	}
}
