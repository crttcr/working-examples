package spring.data.jdbc;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring.data.domain.Spitter;
import spring.data.jdbc.raw.JdbcConfiguration;
import spring.data.jdbc.raw.RawJdbcInsert;
import spring.data.jdbc.raw.RawJdbcQuery;

/**
 * Test class for RawJdbcInsert
 *
 * Spitter example drawn from Spring in Action 4th Edition by Craig Walls and Manning Publications Co.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JdbcConfiguration.class)
@ActiveProfiles("dev")
public class RawJdbcInsertTest
{
	@Inject
	DataSource ds;

	@Test
	public void testInsert()
	{
		// Arrange
		//
		Spitter spitter = new Spitter(null, "SlickWilly", "***", "William J. Clinton");
		RawJdbcInsert insert = new RawJdbcInsert(ds);
		RawJdbcQuery query = new RawJdbcQuery(ds);


		// Act
		//
		insert.addSpitter(spitter);
		List<Spitter> result = query.find();

		// Assert
		//
		assertTrue(result.size() == 2);
	}
}
