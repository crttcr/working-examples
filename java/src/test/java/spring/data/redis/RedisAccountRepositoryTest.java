package spring.data.redis;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RedisConfiguration.class)
public class RedisAccountRepositoryTest
{
	@Inject
	AccountRepository repo;

	@Before
	public void setUp()
	{
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
		repo.save(s);
		Account t = repo.findOne(id);

		// Assert
		//
		assertEquals(s, t);
	}
}