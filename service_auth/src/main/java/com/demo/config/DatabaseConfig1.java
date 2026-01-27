package com.demo.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "authEntityManagerFactory", basePackages = {
		"com.demo.repository" }, transactionManagerRef = "authTransactionManager")
public class DatabaseConfig1 {

	@Autowired
	Environment env;

	@Primary
	@Bean(name = "authDataSource")
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(env.getProperty("auth.datasource.url"));
		ds.setUsername(env.getProperty("auth.datasource.username"));
		ds.setPassword(env.getProperty("auth.datasource.password"));
		ds.setDriverClassName("org.postgresql.Driver");
		return ds;
	}

	@Primary
	@Bean(name = "authEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManager() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan("tn.isie.auth.model");

		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		bean.setJpaVendorAdapter(adapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("auth.jpa.hibernate.ddl-auto"));
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		bean.setJpaPropertyMap(properties);

		// Définir explicitement le nom de l'unité de persistance
		bean.setPersistenceUnitName("authPersistenceUnit");
		bean.setPackagesToScan("com.demo.model");

		return bean;

	}

	@Primary
	@Bean("authTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("authEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Primary
	@Bean(initMethod = "migrate", name = "flywayProject")
	public Flyway flyway(@Qualifier("authDataSource") DataSource dataSource) {
		// Configurer Flyway avec la source de données recrutementDataSource
		return Flyway.configure().dataSource(dataSource).locations("classpath:db/migration/auth") 
				.baselineOnMigrate(true) // Permet d'initialiser la table de suivi Flyway

				.load();
	}
}