package spring.data.hibernate;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HibernateConfiguration.class)
public class HibernateConfigurationTest
{
	@Inject
	DataSource ds;

	@Inject
	LocalSessionFactoryBean lsfb;

	@Test
	public void testConfiguration()
	{
		assertNotNull(ds);
		assertNotNull(lsfb);
	}

}
