package com.backend.api.services;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.security.keyvault.secrets.SecretClient;
import com.backend.api.configurations.AzureConfiguration;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AzureService {
    protected ServiceBusSenderClient senderClient;
    protected ServiceBusProcessorClient processorClient;
    @Autowired
    private SecretClient secretClient;

    @PostConstruct
    private void initClient() {
        System.err.println(secretClient.getSecret("ServiceBusQueueName").getValue());
        this.senderClient = new ServiceBusClientBuilder()
                .connectionString(secretClient.getSecret("ServiceBusConnectionString").getValue())
                .sender()
                .queueName(secretClient.getSecret("ServiceBusQueueName").getValue())
                .buildClient();
        this.processorClient = new ServiceBusClientBuilder()
                .connectionString(secretClient.getSecret("ServiceBusConnectionString").getValue())
                .processor()
                .queueName(secretClient.getSecret("ServiceBusQueueName").getValue())
                .processMessage(AzureConfiguration::processMessage)
                .processError(AzureConfiguration::processError)
                .maxConcurrentCalls(5)
                .buildProcessorClient();
    }
}
