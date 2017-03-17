package spring.data.redis;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RedisConfiguration.class)
public class RedisAccountTest
{
	@Inject
	RedisConnectionFactory rcf;

	@Inject
	private RedisTemplate<String, Account> spitterTemplate;

	private ValueOperations<String, Account> vops;

	@Before
	public void setUp()
	{
		vops = spitterTemplate.opsForValue();
	}

	@Test
	public void testSave()
	{
		// Arrange
		//
		String id = "123";
		Account s = new Account(id, "B", "C");

		// Act
		//
		vops.set(id, s);
		Account t = vops.get(id);

		// Assert
		//
		assertEquals(s, t);
	}
}