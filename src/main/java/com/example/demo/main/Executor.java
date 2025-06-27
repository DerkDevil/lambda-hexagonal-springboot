package com.example.demo.main;


import org.example.clients.AwsClients;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Executor {


    public static void main(String[] args) {

        String accessKey = System.getenv("ACCESS_KEY");
        String secretKey = System.getenv("SECRET_KEY");
        String secretName = System.getenv("SECRET_NAME");
        String region = System.getenv("REGION");
        String bucketName = System.getenv("BUCKET_NAME");
        String key = System.getenv("KEY");

        AwsBasicCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);
        AwsClients aws = AwsClients.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .build();


        Path destino = Paths.get("archivo-descargado.txt");

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        aws.s3().getObject(getObjectRequest, destino);


        String secretJson = aws.secretsManager()
                .getSecretValue(b -> b.secretId(secretName))
                .secretString();

        System.out.println(secretJson);


    }
}
