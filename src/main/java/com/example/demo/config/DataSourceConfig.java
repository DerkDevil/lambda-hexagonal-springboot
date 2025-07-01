package com.example.demo.config;

import com.example.demo.infrastructure.adapters.out.secrets.DatabaseSecret;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.example.aws.services.SecretsManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    private final SecretsManagerService secretsManagerService;
    private final ObjectMapper objectMapper;

    public DataSourceConfig(SecretsManagerService secretsManagerService,
                            ObjectMapper objectMapper) {
        this.secretsManagerService = secretsManagerService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public DataSource dataSource(@Value("${db.secret.name}") String secretName) {
        String secretJson = secretsManagerService.getSecretValue(secretName);

        DatabaseSecret secret;
        try {
            secret = objectMapper.readValue(secretJson, DatabaseSecret.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing database secret JSON", e);
        }

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(secret.getUrl());
        ds.setUsername(secret.getUsername());
        ds.setPassword(secret.getPassword());
        return ds;
    }
}
