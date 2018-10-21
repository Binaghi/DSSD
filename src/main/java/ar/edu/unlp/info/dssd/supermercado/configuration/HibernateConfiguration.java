package ar.edu.unlp.info.dssd.supermercado.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {
	
	 @Autowired
	 private Environment environment;

	@Bean
	  public LocalSessionFactoryBean sessionFactory() {
	    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	    sessionFactory.setDataSource(dataSource());
	    sessionFactory.setPackagesToScan(
	            new String[] {"ar.edu.unlp.info.dssd.supermercado.entities"});
	    sessionFactory.setHibernateProperties(hibernateProperties());

	    return sessionFactory;
	  }

	  @Bean
	  public DataSource dataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
	    /*dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/empleados");
	    dataSource.setUsername("root");
	    dataSource.setPassword("");*/

	    return dataSource;
	  }

	  @Bean
	  public PlatformTransactionManager hibernateTransactionManager() {
	    HibernateTransactionManager transactionManager
	            = new HibernateTransactionManager();
	    transactionManager.setSessionFactory(sessionFactory().getObject());
	    return transactionManager;
	  }

	  private final Properties hibernateProperties() {
	    Properties hibernateProperties = new Properties();

	    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
	    hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
	    hibernateProperties.setProperty("hibernate.show_sql", "false");
	    hibernateProperties.setProperty("hibernate.format_sql", "true");
	    hibernateProperties.setProperty("hibernate.use_sql_comments", "false");

	    return hibernateProperties;
	  }
}
