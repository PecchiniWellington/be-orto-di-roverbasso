package com.ortoroverbasso.ortorovebasso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backblaze.b2.client.B2StorageClientImpl;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.webApiHttpClient.B2StorageHttpClientBuilder;

@Configuration
public class B2Config {

    @Value("${backblaze.key-id}")
    private String keyId;

    @Value("${backblaze.application-key}")
    private String appKey;

    @Value("${backblaze.bucket-name}")
    private String bucketName;

    @Value("${backblaze.bucket-id}")
    private String bucketId;

    @Bean
    public B2StorageClientImpl b2StorageClient() throws B2Exception {
        return (B2StorageClientImpl) B2StorageHttpClientBuilder
                .builder(keyId, appKey, "OrtoDiRoverbasso-App")
                .build();
    }

    @Bean
    public String bucketName() {
        return bucketName;
    }

    @Bean
    public String bucketId() {
        return bucketId;
    }
}
