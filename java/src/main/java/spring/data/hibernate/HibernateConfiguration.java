package spring.data.hibernate;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import spring.data.domain.Spitter;

@Configuration
@ComponentScan
public class HibernateConfiguration
{

	@Bean
	public DataSource embeddedDatabase()
	{
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.addScript("classpath:sql/schema.sql")
			.addScript("classpath:sql/test-data.sql")
			.build();
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource datasource)
	{
		Properties p = new Properties();
		p.setProperty("dialect", "org.hibernate.dialect.H2Dialect");

		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(datasource);
		sfb.setPackagesToScan(new String[] { "spring.data.hibernate" });
		sfb.setHibernateProperties(p);
		sfb.setAnnotatedClasses(new Class[] { Spitter.class });

		return sfb;
	}

	// Enable translation of exceptions to Spring standard runtime exceptions
	//
	@Bean
	public BeanPostProcessor persistenceTranslation()
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
