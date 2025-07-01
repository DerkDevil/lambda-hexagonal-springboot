package com.example.demo.config;

import com.example.demo.infrastructure.adapters.out.secrets.DatabaseSecret;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.example.aws.services.SecretsManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error parsing database secret JSON", e);
        }

        String jdbcUrl = String.format(
                "jdbc:postgresql://%s:%d/%s",
                secret.getUrl(),
                secret.getPort(),
                secret.getDbname()
        );

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(secret.getUsername());
        ds.setPassword(secret.getPassword());

        return ds;
    }
}
