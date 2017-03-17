package spring.data.redis;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account
implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String username;
	private String email;

	public static Account copyFrom(String id, Account prototype)
	{
		String u = prototype.getUsername();
		String e = prototype.getEmail();

		Account rv = new Account(id, u, e);
		return rv;
	}
}
