package com.corretora.api.config;

import com.zaxxer.hikari.HikariConfig;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.corretora.api",
        entityManagerFactoryRef = "mySqlEntityManagerFactory",
        transactionManagerRef = "mySqlTransactionManager"
)
public class DataSourceConfig extends HikariConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "mySqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource createConnection() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getRequiredProperty("spring.datasource.driverClassName"));
        dataSourceBuilder.url(env.getRequiredProperty("spring.datasource.url"));
        dataSourceBuilder.username(env.getRequiredProperty("spring.datasource.username"));
        dataSourceBuilder.password(env.getRequiredProperty("spring.datasource.password"));
        return dataSourceBuilder.build();
    }

    @Bean(name = "mySqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("mySqlDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.corretora.api.entity")
                .build();
    }

    @Bean(name = "mySqlTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("mySqlEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
