package spring.data.jdbc;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JdbcConfiguration.class)
@ActiveProfiles("dev")
public class JdbcConfigurationTest
{
	@Inject
	DataSource ds;

	@Test
	public void testConfiguration()
	{
		assertNotNull(ds);
	}

}
