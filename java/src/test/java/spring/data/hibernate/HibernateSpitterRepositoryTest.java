package spring.data.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring.data.domain.Spitter;

/**
 * Test class for HibernateSpitterRepository
 *
 * Spitter example drawn from Spring in Action 4th Edition by Craig Walls and Manning Publications Co.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HibernateConfiguration.class)
public class HibernateSpitterRepositoryTest
{
	@Inject
	SessionFactory lsfb;

	private HibernateSpitterRepository subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new HibernateSpitterRepository(lsfb);
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
		assertFalse(found.isEmpty());
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
