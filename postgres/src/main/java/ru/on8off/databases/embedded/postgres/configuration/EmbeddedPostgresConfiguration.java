package ru.on8off.databases.embedded.postgres.configuration;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"ru.on8off.databases.embedded.postgres.repository"},
        transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "entityManagerFactory"
)
public class EmbeddedPostgresConfiguration {
    @Value("${db.port}")
    private Integer port;
    @Value("${db.dataDirectory}")
    private String dataDirectory;
    @Value("${db.cleanDataDirectory}")
    private Boolean cleanDataDirectory;



    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() throws IOException {
        JpaTransactionManager manager = new JpaTransactionManager(entityManagerFactory().getObject());
        manager.setNestedTransactionAllowed(true);
        return manager;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("ru.on8off.databases.embedded.postgres.repository.entity");
        emf.setPersistenceUnitName("postgresql");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        emf.setJpaPropertyMap(properties);
        return emf;
    }

    @Bean
    @Primary
    public Flyway flyway() throws IOException {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource())
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    @Primary
    public DataSource dataSource() throws IOException {
        EmbeddedPostgres embeddedPostgres = EmbeddedPostgres.builder()
                .setPort(port)
                .setDataDirectory(dataDirectory)
                .setCleanDataDirectory(cleanDataDirectory)
                .start();
        return embeddedPostgres.getPostgresDatabase();
    }


}
