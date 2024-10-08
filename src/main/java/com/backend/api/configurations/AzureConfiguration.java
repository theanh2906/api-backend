package com.backend.api.configurations;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.backend.api.models.MessageObject;
import com.backend.api.shared.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                .clientId(Constant.Azure.Credential.CLIENT_ID)
                .clientSecret(Constant.Azure.Credential.CLIENT_SECRET)
                .tenantId(Constant.Azure.Credential.TENANT_ID)
                .build();
    }

    @Bean
    public QueueClient queueClient() {
        return new QueueClientBuilder()
                .credential(tokenCredential())
                .queueName(Constant.Azure.Storage.Queue.QUEUE_NAME)
                .endpoint(Constant.Azure.Storage.Queue.QUEUE_NAME_ENDPOINT)
                .connectionString(Constant.Azure.Storage.CONNECTION_STRING)
                .buildClient();
    }

    @Bean
    public SecretClient secretClient() {
        return new SecretClientBuilder()
                .vaultUrl(Constant.Azure.KeyVault.VAULT_URL)
                .credential(tokenCredential())
                .buildClient();
    }

    @Bean
    public KeyClient keyClient() {
        return new KeyClientBuilder()
                .credential(tokenCredential())
                .vaultUrl(Constant.Azure.KeyVault.VAULT_URL)
                .buildClient();
    }

    public static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        ObjectMapper mapper = new ObjectMapper();
        System.out.printf("Processing message. Id: %s, Sequence #: %s. Contents: %s%n",
                message.getMessageId(), message.getSequenceNumber(), message.getBody());
        MessageObject object = message.getBody().toObject(MessageObject.class);
    }

    public static void processError(ServiceBusErrorContext context) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
                context.getFullyQualifiedNamespace(), context.getEntityPath());
    }
}
