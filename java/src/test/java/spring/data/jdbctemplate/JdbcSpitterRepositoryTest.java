package spring.data.jdbctemplate;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring.data.domain.Spitter;
import spring.data.jdbc.JdbcConfiguration;
import spring.data.jdbc.RawJdbcInsert;
import spring.data.jdbc.RawJdbcQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JdbcConfiguration.class)
@ActiveProfiles("dev")
public class JdbcSpitterRepositoryTest
{
	@Autowired
	private DataSource ds;

	@Autowired
	private JdbcTemplate jdbc;

	@Test
	public void testConfiguration()
	{
		assertNotNull(jdbc);
	}

	@Test
	public void test()
	{
		JdbcSpitterRepository repo = new JdbcSpitterRepository(jdbc);

		Spitter sp = new Spitter(null, "A", "B", "C");

		Spitter result = repo.save(sp);

		System.out.println(result);
		assertNotNull(result);

		RawJdbcInsert insert = new RawJdbcInsert(ds);
		Spitter sp2 = new Spitter(null, "XXXX", "YYYY", "ZZZZZZ");
		insert.addSpitter(sp2);


		// Act
		//
		RawJdbcQuery query = new RawJdbcQuery(ds);
		List<Spitter> r2 = query.find();
		System.out.println(r2);

		List<Spitter> r3 = repo.find();
		System.out.println(r3);

	}

}
