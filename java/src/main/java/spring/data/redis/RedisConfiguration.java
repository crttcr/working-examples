package spring.data.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableRedisRepositories
public class RedisConfiguration
{

	@Value("${spring.redis.host}")
	private String host;

	//	@Value("${spring.redis.port}")
	//	private Integer port;

	@Value("${spring.redis.password}")
	private String password;

	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory cf)
	{
		return new StringRedisTemplate(cf);
	}

	@Bean
	public RedisTemplate<String, Account> redisTemplate(RedisConnectionFactory cf)
	{
		RedisTemplate<String, Account> rv = new RedisTemplate<>();
		rv.setConnectionFactory(cf);
		return rv;
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate() {

		RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
		return template;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory()
	{
		JedisConnectionFactory cf = new JedisConnectionFactory();

		cf.setHostName(host);
		//		cf.setPort(port);
		cf.setPassword(password);

		return cf;
	}

}
