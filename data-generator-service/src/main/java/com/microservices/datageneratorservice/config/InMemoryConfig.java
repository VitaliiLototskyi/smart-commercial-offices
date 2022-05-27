package com.microservices.datageneratorservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class InMemoryConfig {

    @Autowired
    private DataSource datasource;

    @Value("classpath:data_queries.sql")
    Resource resource;

    @PostConstruct
    public void loadIfInMemory() throws Exception {
        ScriptUtils.executeSqlScript(datasource.getConnection(), resource);
    }
}
