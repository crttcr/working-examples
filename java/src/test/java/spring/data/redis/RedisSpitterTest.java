package spring.data.redis;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RedisConfiguration.class)
public class RedisSpitterTest
{
	@Inject
	RedisConnectionFactory rcf;

	@Inject
	private RedisTemplate<String, Account> spitterTemplate;

	@Test
	public void testSave()
	{
		String id = "123";
		Account s = new Account(id, "B", "C");

		spitterTemplate.opsForValue().set(id, s);

		Account t = spitterTemplate.opsForValue().get(id);

		assertEquals(s, t);
	}
}