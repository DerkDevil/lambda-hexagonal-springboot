package com.example.demo.infrastructure.adapters.out.secrets;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import java.net.URI;

public class SecretsManagerClientFactory {

    public static SecretsManagerClient create() {
        try {
            var client = SecretsManagerClient.builder()
                    .credentialsProvider(
                            StaticCredentialsProvider.create(
                                    AwsBasicCredentials.create("test","test")
                            )
                    )
                    .region(Region.US_EAST_1)
                    .endpointOverride(URI.create("http://host.docker.internal:4566"))
                    .build();
            client.listSecrets();

            return client;
        } catch (Exception e) {
            System.err.println("[Factory] ERROR al crear o conectar SecretsManagerClient:");
            e.printStackTrace();
            throw e;
        }
    }
}