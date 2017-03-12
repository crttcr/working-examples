package spring.data.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Repository interface for Order domain type.
 *
 * Once configured, Spring Data makes, uh, pretty simple.
 *
 * @author reid.dev
 */
public interface MongoOrderRepository
extends MongoRepository<Order, String>
{
	Order findByCustomer(String customer);
	List<Order> findByCustomerLike(String customer);
	List<Order> findByCustomerLikeAndType(String customer);


	// Specify a custom query with a JSON string defining the match.
	// The ?0 represents the first parameter in the query, so
	// the Json call should look like this
	//
	// repo.findOrdersForChuck("typeA");
	//
	@Query("{'customer': 'Charles', 'type': ?0 }")
	List<Order> findOrdersForChuck(String type);
}
