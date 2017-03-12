package spring.data.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore("You need to have mongod running on localhost:27017 before running this test.")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MongoConfiguration.class)
public class MongoOrderRepositoryTest
{
	@Autowired
	MongoOperations mongo;

	@Inject
	MongoOrderRepository subject;

	@Before
	public void setUp() throws Exception
	{
		subject.deleteAll();
	}

	@Test
	public void testConfiguration()
	{
		assertNotNull(mongo);
	}

	@Test
	public void countEmpty()
	{
		// Arrange + act
		//
		long count = subject.count();

		// Assert
		//
		assertEquals(0L, count);
	}

	@Test
	public void testSave()
	{
		// Arrange
		//
		Order order = new Order();
		order.setCustomer("Andy");

		// Act
		//
		Order result = subject.save(order);

		// Assert
		//
		assertNotNull(result);
		assertEquals("Andy", result.getCustomer());
	}

	@Test
	public void testFindAll()
	{
		// Arrange
		//
		Order order = new Order();
		order.setCustomer("Benji");
		Order result = subject.save(order);

		// Act
		//
		List<Order> found = subject.findAll();

		// Assert
		//
		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertEquals(order.getCustomer(), result.getCustomer());
		assertEquals(order.getCustomer(), found.get(0).getCustomer());
	}

	@Test
	public void testFindOneExampleOf()
	{
		// Arrange
		//
		String[] customers = {"A", "B", "C", "D", "E" };
		for (String s: customers)
		{
			Order order = new Order();
			order.setCustomer(s);
			@SuppressWarnings("unused")
			Order result = subject.save(order);
		}

		Order prototype = new Order();
		prototype.setCustomer("D");
		Example<Order> example = Example.of(prototype);

		// Act
		//
		Order found = subject.findOne(example);

		// Assert
		//
		assertNotNull(found);
		assertEquals("D", found.getCustomer());
	}

	// Testing the custom method derived from the interface by the Spring Data DSL.
	//
	@Test
	public void testFindByCustomerName()
	{
		// Arrange
		//
		String[] customers = {"A", "B", "C", "D", "E" };
		for (String s: customers)
		{
			Order order = new Order();
			order.setCustomer(s);
			@SuppressWarnings("unused")
			Order result = subject.save(order);
		}

		// Act
		//
		Order found = subject.findByCustomer("C");

		// Assert
		//
		assertNotNull(found);
		assertEquals("C", found.getCustomer());
	}
}
