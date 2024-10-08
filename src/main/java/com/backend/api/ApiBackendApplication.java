package com.backend.api;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.storage.queue.QueueClient;
import com.backend.api.services.ServiceBusMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ApiBackendApplication implements CommandLineRunner {
    @Autowired
    private QueueClient queueClient;
    @Autowired
    private ServiceBusSenderClient senderClient;
    @Autowired
    private ServiceBusMessagingService messagingService;
    @Override
    public void run(String... args) throws Exception {
//        queueClient.create();
//        SendMessageResult sendMessageResult = queueClient.sendMessage("test");
//        QueueMessageItem queueMessageItem = queueClient.receiveMessage();
//        messagingService.startProcessor();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiBackendApplication.class, args);
    }

}
