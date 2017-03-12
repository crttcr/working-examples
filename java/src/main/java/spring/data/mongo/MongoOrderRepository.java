package spring.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

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
}
