package spring.data.jdbctemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring.data.domain.Spitter;
import spring.data.jdbc.template.JdbcTemplateConfiguration;
import spring.data.jdbc.template.JdbcTemplateSpitterRepository;
import spring.data.jdbc.template.SpitterRepository;

/**
 * Test class for JdbcSpitterRepository
 *
 * Spitter example drawn from Spring in Action 4th Edition by Craig Walls and Manning Publications Co.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JdbcTemplateConfiguration.class)
@ActiveProfiles("dev")
public class JdbcTemplateSpitterRepositoryTest
{
	@Autowired
	private JdbcTemplate jdbc;
	SpitterRepository subject = null;

	@Before
	public void setUp()
	{
		subject = new JdbcTemplateSpitterRepository(jdbc);
	}

	@Test
	public void testConfiguration()
	{
		assertNotNull(jdbc);
	}

	@Test
	public void testSave()
	{
		// Arrange
		//
		Spitter sp = new Spitter(null, "Andrew", "s0c3950FKw04'aF(#K)FfkzAkCfaF", "Andy Crow");

		// Act
		//
		Spitter result = subject.save(sp);

		// Assert
		//
		assertNotNull(result);
		assertEquals("Andy Crow", result.getFullname());
	}

	@Test
	public void testCount()
	{
		// Arrange
		//
		long before = subject.count();
		Spitter sp = new Spitter(null, "Andrew", "s0c3950FKw04'aF(#K)FfkzAkCfaF", "Axel Crow");
		subject.save(sp);

		// Act
		//
		long after = subject.count();

		// Assert
		//
		assertEquals(before + 1, after);
	}

	@Test
	public void testFindOne()
	{
		// Arrange
		//
		Spitter sp = new Spitter(null, "Andrew", "s0c3950F", "Ansel Crow");

		// Act
		//
		Spitter saved = subject.save(sp);
		Spitter found = subject.findOne(saved.getId());

		// Assert
		//
		assertNotNull(found);
		assertEquals("Ansel Crow", found.getFullname());
	}

	@Test
	public void testFind()
	{
		// Arrange + Act
		//
		List<Spitter> found = subject.find();

		// Assert
		//
		assertNotNull(found);
		assertEquals(3, found.size());
	}

	@Test
	public void testFindByUsername()
	{
		// Arrange
		//
		Spitter sp = new Spitter(null, "Mickey", "s0c3950FKw04'aF(#K)FfkzAkCfaF", "Mork Crow");

		// Act
		//
		Spitter saved = subject.save(sp);
		Spitter found = subject.findByUsername(saved.getUsername());

		// Assert
		//
		assertNotNull(found);
		assertEquals("Mickey", found.getUsername());
	}
}
