package spring.data.jdbc;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring.data.domain.Spitter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JdbcConfiguration.class)
@ActiveProfiles("dev")
public class RawJdbcQueryTest
{
	@Inject
	DataSource ds;

	@Test
	public void testFind()
	{
		// Arrange
		//
		RawJdbcQuery query = new RawJdbcQuery(ds);

		// Act
		//
		List<Spitter> list = query.find();

		// Assert
		//
		assertNotNull(list);
		assertNotNull(list.get(0));
	}

	@Test
	public void testFindById()
	{
		// Arrange
		//
		RawJdbcQuery query = new RawJdbcQuery(ds);

		// Act
		//
		Spitter s = query.findOne(1L);

		// Assert
		//
		assertNotNull(s);
	}
}
