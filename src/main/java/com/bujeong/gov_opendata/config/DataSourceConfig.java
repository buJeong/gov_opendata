package com.bujeong.gov_opendata.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
    public static final String METADB_DATASOURCE = "metadbDataSource";
    public static final String OPENDB_DATASOURCE = "opendbDataSource";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.metadb.hikari")
    public HikariConfig metadbHikariConfig() {
        return new HikariConfig();
    }

    @Primary
    @Bean(METADB_DATASOURCE)
    public DataSource metadbDataSource() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(metadbHikariConfig()));
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.opendb.hikari")
    public HikariConfig opendbHikariConfig() {
        return new HikariConfig();
    }

    @Bean(OPENDB_DATASOURCE)
    public DataSource opendbDataSource() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(opendbHikariConfig()));
    }
    
//    @Bean
//    @Qualifier("jpaTransactionManager")
//    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
//    	JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory);
//    	jpaTransactionManager.setDataSource(opendbDataSource());
//    	
//    	return jpaTransactionManager;
//    }
    
}
