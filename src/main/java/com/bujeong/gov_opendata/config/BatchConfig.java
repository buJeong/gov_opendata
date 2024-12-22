package com.bujeong.gov_opendata.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

    private final DataSource metadbDataSource;
    private final PlatformTransactionManager metadbTransactionManager;

    public BatchConfig(@Qualifier(DataSourceConfig.METADB_DATASOURCE) DataSource metaDataSource, @Qualifier(TransactionManagerConfig.METADB_TRANSACTION_MANAGER) PlatformTransactionManager metaTransactionManager) {
        this.metadbDataSource = metaDataSource;
        this.metadbTransactionManager = metaTransactionManager;
    }

    @Override
    protected DataSource getDataSource() {
        return metadbDataSource;
    }

    @Override
    protected PlatformTransactionManager getTransactionManager() {
        return metadbTransactionManager;
    }
}
