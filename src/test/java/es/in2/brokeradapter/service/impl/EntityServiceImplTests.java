package es.in2.brokeradapter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.configuration.properties.BrokerPathProperties;
import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import es.in2.brokeradapter.exception.JsonReadingException;
import es.in2.brokeradapter.utils.HttpUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static es.in2.brokeradapter.utils.HttpUtils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EntityServiceImplTests {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private BrokerProperties brokerProperties;


    @InjectMocks
    private EntityServiceImpl entityService;

    @Test
    void testPostEntity() throws JsonProcessingException {

        // Arrange
        when(brokerProperties.internalDomain()).thenReturn("https://example.com");
        when(brokerProperties.paths()).thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        String requestBody = "{\"requestBody\":\"example\"}";
        // Mock the postRequest method
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {
            when(postRequest(anyString(), anyString(), anyList(), anyString())).thenReturn(Mono.empty());
            // Act
            JsonNode jsonNode = new ObjectMapper().readTree(requestBody);
            when(objectMapper.readTree(requestBody)).thenReturn(jsonNode);
            Mono<Void> result = entityService.postEntity("processId", requestBody);
            // Assert
            result.as(StepVerifier::create).verifyComplete();
        }
    }

    @Test
    void testPostEntity_WhenErrorOccurs() throws JsonProcessingException {
        // Arrange
        String requestBody = "{\"requestBody\":\"example\"}";
        when(brokerProperties.internalDomain())
                .thenReturn("https://example.com");
        when(brokerProperties.paths())
                .thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        // Mock the postRequest method to return an error
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {
            when(postRequest(anyString(), anyString(), anyList(), anyString()))
                    .thenReturn(Mono.error(new RuntimeException("Simulated error")));
            JsonNode jsonNode = new ObjectMapper().readTree(requestBody);
            when(objectMapper.readTree(requestBody)).thenReturn(jsonNode);
            // Act & Assert
            entityService.postEntity("processId", requestBody)
                    .as(StepVerifier::create)
                    .expectError()
                    .verify();
        }
    }

    @Test
    void testGetEntityById() {
        // Arrange
        String expectedResponse = "response";
        when(brokerProperties.internalDomain()).thenReturn("https://example.com");
        when(brokerProperties.paths()).thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        // Mock the postRequest method
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {
            when(getRequest(anyString(), anyString(), anyList())).thenReturn(Mono.just(expectedResponse));
            // Act
            Mono<String> result = entityService.getEntityById("processId", "requestBody");
            // Assert
            result.as(StepVerifier::create).expectNext(expectedResponse).verifyComplete();
        }
    }

    @Test
    void testGetEntityById_WhenErrorOccurs() {
        // Arrange
        when(brokerProperties.internalDomain())
                .thenReturn("https://example.com");
        when(brokerProperties.paths())
                .thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        // Mock the postRequest method to return an error
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {
            when(getRequest(anyString(), anyString(), anyList()))
                    .thenReturn(Mono.error(new RuntimeException("Simulated error")));
            // Act & Assert
            entityService.getEntityById("processId", "entityId")
                    .as(StepVerifier::create)
                    .expectError()
                    .verify();
        }
    }

    @Test
    void testUpdateEntity() throws Exception {
        // Arrange
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.has(anyString())).thenReturn(true);
        when(jsonNode.get(anyString())).thenReturn(jsonNode);
        when(jsonNode.asText()).thenReturn("id");
        when(objectMapper.readTree(anyString())).thenReturn(jsonNode);
        when(brokerProperties.internalDomain()).thenReturn("https://example.com");
        when(brokerProperties.paths()).thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        // Mock the postRequest method
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {
            when(patchRequest(anyString(), anyString(), anyList(), anyString())).thenReturn(Mono.empty());
            // Act
            Mono<Void> result = entityService.updateEntity("processId", "id");
            // Assert
            result.as(StepVerifier::create).verifyComplete();
        }
    }

    @Test
    void testUpdateEntity_WhenErrorOccurs() throws Exception {
        // Arrange
        JsonNode jsonNode = mock(JsonNode.class);
        when(jsonNode.has(anyString())).thenReturn(true);
        when(jsonNode.get(anyString())).thenReturn(jsonNode);
        when(jsonNode.asText()).thenReturn("id");
        when(objectMapper.readTree(anyString())).thenReturn(jsonNode);
        when(brokerProperties.internalDomain())
                .thenReturn("https://example.com");
        when(brokerProperties.paths())
                .thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        // Mock the postRequest method to return an error
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {
            when(getRequest(anyString(), anyString(), anyList()))
                    .thenReturn(Mono.error(new RuntimeException("Simulated error")));
            // Act & Assert
            entityService.updateEntity("processId", "requestBody")
                    .as(StepVerifier::create)
                    .expectError()
                    .verify();
        }
    }

    @Test
    void testGetEntityIdFromRequestBody_WhenIdFieldNotPresent_ShouldThrowJsonReadingException() throws JsonProcessingException {
        // Arrange
        String requestBody = "{\"someKey\": \"someValue\"}";  // JSON without "id" field
        // Mock ObjectMapper behavior
        when(objectMapper.readTree(requestBody)).thenReturn(mock(JsonNode.class));
        // Act & Assert
        assertThrows(JsonReadingException.class,
                () -> entityService.updateEntity("processId", requestBody),
                "Expected JsonReadingException when 'id' field is not present");
    }

    @Test
    void testGetEntityIdFromRequestBody_WhenJsonParsingError_ShouldThrowJsonReadingException() throws JsonProcessingException {
        // Arrange
        String requestBody = "invalidJson";  // Invalid JSON causing parsing error
        // Mock ObjectMapper behavior to simulate parsing error
        when(objectMapper.readTree(requestBody)).thenThrow(new RuntimeException("Simulated parsing error"));

        // Act & Assert
        assertThrows(JsonReadingException.class,
                () -> entityService.updateEntity("processId", requestBody),
                "Expected JsonReadingException when JSON parsing error occurs");
    }

    @Test
    void testDeleteEntity() {
        // Arrange
        when(brokerProperties.internalDomain()).thenReturn("https://example.com");
        when(brokerProperties.paths()).thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        // Mock the postRequest method
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {
            when(deleteRequest(anyString(), anyString(), anyList())).thenReturn(Mono.empty());
            // Act
            Mono<Void> result = entityService.deleteEntityById("processId", "entityId");
            // Assert
            result.as(StepVerifier::create).verifyComplete();
        }
    }

    @Test
    void testDeleteEntityById_WhenErrorOccurs() {
        // Arrange
        when(brokerProperties.internalDomain())
                .thenReturn("https://example.com");
        when(brokerProperties.paths())
                .thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        // Mock the postRequest method to return an error
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {
            when(deleteRequest(anyString(), anyString(), anyList()))
                    .thenReturn(Mono.error(new RuntimeException("Simulated error")));
            // Act & Assert
            entityService.deleteEntityById("processId", "entityId")
                    .as(StepVerifier::create)
                    .expectError()
                    .verify();
        }
    }

}