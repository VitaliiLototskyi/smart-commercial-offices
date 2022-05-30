package com.microservices.datageneratorservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class StartupSQLConfig {

    private final DataSource datasource;

    @Value("classpath:data_queries.sql")
    Resource resource;

    public StartupSQLConfig(DataSource datasource) {
        this.datasource = datasource;
    }

    @PostConstruct
    public void loadIfInMemory() throws Exception {
        ScriptUtils.executeSqlScript(datasource.getConnection(), resource);
    }
}
