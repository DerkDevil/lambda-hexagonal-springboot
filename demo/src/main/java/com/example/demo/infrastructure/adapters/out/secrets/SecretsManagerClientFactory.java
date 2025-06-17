package com.example.demo.infrastructure.adapters.out.secrets;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

public class SecretsManagerClientFactory {

    public static SecretsManagerClient create() {
        // Toma la región de la env var AWS_REGION
        return SecretsManagerClient.builder()
                .region(Region.of(System.getenv("AWS_REGION")))
                .build();
    }
}
