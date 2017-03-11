package spring.data.jdbc;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@ComponentScan(basePackageClasses={RawJdbcQuery.class,RawJdbcInsert.class})
public class JdbcConfiguration
{
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource datasource)
	{
		return new JdbcTemplate(datasource);
	}

	@Profile("dev")
	@Bean
	public DataSource embeddedDatabase()
	{
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:sql/schema.sql")
				.addScript("classpath:sql/test-data.sql")
				.build();
	}

	@Profile("qa")
	@Bean
	public DataSource qaDataSource()
	{
		final BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName("org.h2.Driver");
		ds.setUsername("sa");
		ds.setPassword("");    // TODO: Take from ENV
		ds.setInitialSize(4);
		ds.setMaxActive(10);

		return ds;
	}

	@Profile("production")
	@Bean
	public DataSource dataSource()
	{
		JndiObjectFactoryBean jof = new JndiObjectFactoryBean();

		jof.setJndiName("jdbc/SpitterDataSource");
		jof.setResourceRef(true);
		jof.setProxyInterface(DataSource.class);

		return (DataSource) jof.getObject();
	}

}
