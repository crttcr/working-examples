package spring.data.redis;

import org.springframework.data.repository.CrudRepository;

public interface
AccountRepository
extends CrudRepository<Account, String>
{

}
