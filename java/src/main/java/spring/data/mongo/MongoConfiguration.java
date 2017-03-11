package spring.data.mongo;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@Configuration
@ComponentScan
@EnableMongoRepositories(basePackages="spring.data.mongo")
public class MongoConfiguration
{

	@Bean
	public MongoClientFactoryBean mongo()
	{
		MongoClientFactoryBean mongo = new MongoClientFactoryBean();
		mongo.setHost("localhost");

		return mongo;
	}

	@Bean
	public MongoOperations mongoTemplate(Mongo mongo)
	{
		return new MongoTemplate(mongo, "OrdersDB");
	}


	// Enable translation of exceptions to Spring standard runtime exceptions
	//
	@Bean
	public BeanPostProcessor persistenceTranslation()
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
