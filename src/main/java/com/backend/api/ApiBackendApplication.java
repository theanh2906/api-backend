package com.backend.api;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.models.QueueMessageItem;
import com.azure.storage.queue.models.SendMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ApiBackendApplication implements CommandLineRunner {
    @Autowired
    private QueueClient queueClient;
    @Override
    public void run(String... args) throws Exception {
//        queueClient.create();
//        SendMessageResult sendMessageResult = queueClient.sendMessage("test");
//        QueueMessageItem queueMessageItem = queueClient.receiveMessage();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiBackendApplication.class, args);
    }

}
