package spring.data.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoOrderRepository
extends MongoRepository<Order, String>
{

}
