package com.example.demo.infrastructure.adapters.out.secrets;

import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AwsSecretsManager {

    private final SecretsManagerClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public AwsSecretsManager(SecretsManagerClient client) {
        this.client = client;
    }

    public <T> T getSecret(String secretName, Class<T> clazz) {
        GetSecretValueResponse resp = client.getSecretValue(
                GetSecretValueRequest.builder().secretId(secretName).build()
        );
        try {
            return mapper.readValue(resp.secretString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing secret JSON", e);
        }
    }
}
