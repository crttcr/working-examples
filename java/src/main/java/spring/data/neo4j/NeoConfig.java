package spring.data.neo4j;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories(basePackages="spring.data.neo4j")  // Note, the default package, when not provided is this configuration package.
@EnableTransactionManagement
public class NeoConfig
{
	@Bean
	public org.neo4j.ogm.config.Configuration config()
	{
		org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();

		config.driverConfiguration()
		.setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver")
		.setURI("bolt://localhost:7687"); // FIXME: What is the URI?
		return config;
	}


	@Bean
	public SessionFactory sessionFactory()
	{
		// The Neo4j session can be configured by this configuration object,
		// or by the ogm.properties in the resources folder.
		//
		return new SessionFactory(config(), "spring.data.neo4j");
	}

	@Bean
	public Neo4jTransactionManager transactionManager()
	{
		Session session = sessionFactory().openSession();
		return new Neo4jTransactionManager(session);
	}

	//	//	@Bean(destroyMethod="shutdown")
	//	//	public GraphDatabaseService gdb()
	//	//	{
	//	//		return new GraphDatabaseFactory().newEmbeddedDatabase("/tmp/graphdb");
	//	//	}
	//
	//	@Bean
	//	public BeanPostProcessor persistenceTranslation()
	//	{
	//		return new PersistenceExceptionTranslationPostProcessor();
	//	}
	//
	//	@Override
	//	public SessionFactory getSessionFactory()
	//	{
	//		// TODO Auto-generated method stub
	//		return null;
	//	}
}
