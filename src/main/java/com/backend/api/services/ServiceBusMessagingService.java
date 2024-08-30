package com.backend.api.services;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.backend.api.models.MessageObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceBusMessagingService {
    @Autowired
    private ServiceBusSenderClient senderClient;

    public void sendMessage(String message) {
        senderClient.sendMessage(new ServiceBusMessage(message));
    }

    public void sendMessage(MessageObject messageObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        senderClient.sendMessage(new ServiceBusMessage(mapper.writeValueAsString(messageObj)));
    }
}
