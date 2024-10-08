package com.backend.api.functions;

import com.azure.security.keyvault.secrets.SecretClient;
import com.backend.api.dtos.ResponseDto;
import com.backend.api.models.MessageObject;
import com.backend.api.services.ServiceBusMessagingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.QueueOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class MyAzureFunction {
    @Autowired
    private Function<String, String> uppercase;
    @Autowired
    private SecretClient secretClient;
    @Autowired
    private ServiceBusMessagingService messagingService;

    // The FunctionCatalog leverages the Spring Cloud Function framework.
    @Autowired private FunctionCatalog functionCatalog;
    @FunctionName("test")
    public ResponseEntity<?> plainBean(
            @HttpTrigger(name = "req",
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    methods = HttpMethod.GET,
                    route = "items/{id}") HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id
            ) {

        return ResponseEntity.ok(new ResponseDto("Success"));
    }

    @FunctionName("queue")
    public void adQueue(
            @HttpTrigger(name = "req",
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    methods = HttpMethod.GET) HttpRequestMessage<Optional<String>> request,
//            @QueueOutput(name = "outputQueue", queueName = "benna-queue") OutputBinding<String> outputQueue,
            ExecutionContext context
    ) {
//        String queueMessage = "Hello, world!";
//        outputQueue.setValue(queueMessage);
    }

    @FunctionName("scf")
    public ResponseEntity<?> springCloudFunction(
            @HttpTrigger(name = "req",
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    methods = HttpMethod.GET) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context
    ) {

        // Use SCF composition. Composed functions are not just spring beans but SCF such.
        Function<String, String> composed = this.functionCatalog.lookup("reverse|uppercase");

        return ResponseEntity.ok(new ResponseDto(secretClient.getSecret("clientId").getValue()));
    }

    @FunctionName("message")
    public ResponseEntity<?> sendMessage(
            @HttpTrigger(name = "req",
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    methods = HttpMethod.POST) HttpRequestMessage<Optional<Object>> request,
            ExecutionContext context
    ) {
        final MessageObject message = new MessageObject();
        ObjectMapper mapper = new ObjectMapper();
        try {
            message.setId(String.valueOf(System.currentTimeMillis()));
            message.setData(mapper.writeValueAsString(request.getBody().orElse("")));
            message.setType("request");
            this.messagingService.sendMessage(message);
            return ResponseEntity.ok(message);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new ResponseDto(e.getMessage()));
        }
    }

}
