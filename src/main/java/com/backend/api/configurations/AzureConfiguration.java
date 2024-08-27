package com.backend.api.configurations;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.backend.api.shared.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class AzureConfiguration {
    @Bean
    public Function<String, String> uppercase() {
        return String::toUpperCase;
    }

    @Bean
    public Function<String, String> reverse() {
        return payload -> new StringBuilder(payload).reverse().toString();
    }

    @Bean
    public TokenCredential tokenCredential() {
        return new ClientSecretCredentialBuilder()
                .clientId(Constant.Azure.CLIENT_ID)
                .clientSecret(Constant.Azure.CLIENT_SECRET)
                .tenantId(Constant.Azure.TENANT_ID)
                .build();
    }

    @Bean
    public QueueClient queueClient() {
        return new QueueClientBuilder()
                .credential(tokenCredential())
                .queueName(Constant.Azure.QUEUE_NAME)
                .endpoint(Constant.Azure.QUEUE_NAME_ENDPOINT)
                .connectionString(Constant.Azure.STORAGE_CONNECTION_STRING)
                .buildClient();
    }

    @Bean
    public SecretClient secretClient() {
        return new SecretClientBuilder()
                .vaultUrl(Constant.Azure.VAULT_URL)
                .credential(tokenCredential())
                .buildClient();
    }

    @Bean
    public KeyClient keyClient() {
        return new KeyClientBuilder()
                .credential(tokenCredential())
                .vaultUrl(Constant.Azure.VAULT_URL)
                .buildClient();
    }
}
