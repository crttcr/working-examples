package spring.data.jpa;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JpaConfiguration.class)
public class JpaConfigurationTest
{
	@Inject
	EntityManagerFactory emf;

	@Inject
	private JpaSpitterRepository repo;

	@Test
	public void testConfiguration()
	{
		assertNotNull(emf);
		assertNotNull(repo);
	}
}