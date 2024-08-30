package com.backend.api;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.storage.queue.QueueClient;
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
    private ServiceBusProcessorClient processorClient;
    @Override
    public void run(String... args) throws Exception {
//        queueClient.create();
//        SendMessageResult sendMessageResult = queueClient.sendMessage("test");
//        QueueMessageItem queueMessageItem = queueClient.receiveMessage();
        processorClient.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiBackendApplication.class, args);
    }

}
