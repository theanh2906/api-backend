package com.backend.api.configurations;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories("com.backend.api.repositories")
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Value("${spring.data.postgres.host}")
    private String host;

    @Value("${spring.data.postgres.port}")
    private int port;

    @Value("${spring.data.postgres.database}")
    private String database;

    @Value("${spring.data.postgres.username}")
    private String username;

    @Value("${spring.data.postgres.password}")
    private String password;

    @Value("#{new Integer('${r2dbc.db.connection.minimum.idle}')}")
    private Integer initialSize;

    @Value("#{new Integer('${spring.datasource.hikari.maximum-pool-size}')}")
    private Integer maxSize;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(io.r2dbc.spi.ConnectionFactoryOptions.DRIVER, "pool")
                        .option(io.r2dbc.spi.ConnectionFactoryOptions.PROTOCOL, "postgresql")
                        .option(io.r2dbc.spi.ConnectionFactoryOptions.HOST, host)
                        .option(io.r2dbc.spi.ConnectionFactoryOptions.PORT, port)
                        .option(io.r2dbc.spi.ConnectionFactoryOptions.USER, username)
                        .option(io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD, password)
                        .option(io.r2dbc.spi.ConnectionFactoryOptions.DATABASE, database)
                        .option(io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE, maxSize)
                        .option(io.r2dbc.pool.PoolingConnectionFactoryProvider.INITIAL_SIZE, initialSize)
                        .build());
    }
}
