package spring.data.redis;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Accounts") // Required for RedisRepository
public class Account
implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id // Required for RedisRepository
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
