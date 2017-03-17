package spring.data.redis;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RedisConfiguration.class)
public class RedisConfigurationTest
{
	@Inject
	RedisConnectionFactory rcf;

	@Inject
	private RedisTemplate<String, String> template;

	@Test
	public void testConfiguration()
	{
		assertNotNull(rcf);
		assertNotNull(template);
	}
}