package com.bujeong.gov_opendata.config;

import com.bujeong.gov_opendata.config.custom.EntityManagerFactoryCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class TransactionManagerConfig {
    public static final String OPENDB_ENTITY_MANAGER_FACTORY = "opendbEntityManagerFactory";
    public static final String METADB_TRANSACTION_MANAGER = "metadbTransactionManager";
    public static final String OPENDB_TRANSACTION_MANAGER = "opendbTransactionManager";

    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;
    private final ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadbDataProviders;
    private final EntityManagerFactoryBuilder entityManagerFactoryBuilder;


    @Bean(name = METADB_TRANSACTION_MANAGER)
    public PlatformTransactionManager metadbTransactionManager(@Qualifier(DataSourceConfig.METADB_DATASOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name = OPENDB_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean opendbEntityManagerFactory(@Qualifier(DataSourceConfig.OPENDB_DATASOURCE) DataSource dataSource)  {
        return EntityManagerFactoryCreator.builder()
                .properties(jpaProperties)
                .hibernateProperties(hibernateProperties)
                .metadataProviders(metadbDataProviders)
                .entityManagerFactoryBuilder(entityManagerFactoryBuilder)
                .dataSource(dataSource)
                .packages("com.bujeong.gov_opendata.repository.entity")
                .persistenceUnit("opendb")
                .build()
                .create();
    }

    @Bean(name = OPENDB_TRANSACTION_MANAGER)
    public PlatformTransactionManager opendbTransactionManager(@Qualifier(OPENDB_ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }

    @Configuration
    @EnableJpaRepositories(
            basePackages = "com.bujeong.gov_opendata.repository"
            ,entityManagerFactoryRef = OPENDB_ENTITY_MANAGER_FACTORY
            ,transactionManagerRef = OPENDB_TRANSACTION_MANAGER
    )
    public static class OpendbJpaRepositoriesConfig{}
}
