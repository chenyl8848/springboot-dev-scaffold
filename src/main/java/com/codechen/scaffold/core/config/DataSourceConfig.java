package com.codechen.scaffold.core.config;

import com.codechen.scaffold.core.constant.DataSourceTypeEnum;
import com.codechen.scaffold.core.holder.DataSourceContextHolder;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

/**
 * @author cyl
 * @date 2023-04-28 11:36
 * @description 多数据源配置类
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public AbstractRoutingDataSource routingDataSource(DataSource primaryDataSource, DataSource slaveDataSource) {

        LinkedHashMap<Object, Object> targetDataSources = Maps.newLinkedHashMap();

        targetDataSources.put(DataSourceTypeEnum.PRIMARY.name(), primaryDataSource);
        targetDataSources.put(DataSourceTypeEnum.SLAVE.name(), slaveDataSource);

        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                String dataSourceType = DataSourceContextHolder.getDataSourceType();
                if (StringUtils.isBlank(dataSourceType)) {
                    return DataSourceTypeEnum.PRIMARY.name();
                }
                return dataSourceType;
            }
        };

        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }

    /**
     * 事务管理器
     * @return
     */
    @Primary
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 事务管理器
     * @return
     */
    @Bean
    public DataSourceTransactionManager slaveDataSourceTransactionManager(@Qualifier("slaveDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
