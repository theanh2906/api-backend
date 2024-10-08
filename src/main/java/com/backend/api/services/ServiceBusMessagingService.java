package com.backend.api.services;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.backend.api.models.MessageObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ServiceBusMessagingService extends AzureService {

    public void sendMessage(String message) {
        senderClient.sendMessage(new ServiceBusMessage(message));
    }

    public String sendMessage(MessageObject messageObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ServiceBusMessage message = new ServiceBusMessage(mapper.writeValueAsString(messageObj));
        senderClient.sendMessage(message);
        return message.getMessageId();
    }

    public void startProcessor() {
        this.processorClient.start();
    }
}
