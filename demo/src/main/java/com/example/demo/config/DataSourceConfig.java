package com.example.demo.config;

import com.example.demo.infrastructure.adapters.out.secrets.AwsSecretsManager;
import com.example.demo.infrastructure.adapters.out.secrets.SecretsManagerClientFactory;
import com.example.demo.infrastructure.adapters.out.secrets.DatabaseSecret;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(@Value("${db.secret.name}") String secretName) {
        var secretsClient = SecretsManagerClientFactory.create();
        var secretsManager = new AwsSecretsManager(secretsClient);

        DatabaseSecret secret = secretsManager.getSecret(secretName, DatabaseSecret.class);

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(secret.getUrl());
        ds.setUsername(secret.getUsername());
        ds.setPassword(secret.getPassword());
        return ds;
    }
}
