package es.in2.brokeradapter.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(EntityServiceImpl.class)
class EntityServiceTests {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private BrokerProperties brokerProperties;

    @InjectMocks
    private EntityServiceImpl entityService;

//    @Test
//    void testPostEntity() {
//        // Arrange
//        String processId = "123";
//        String requestBody = "{\"id\":\"456\"}";
//        when(entityService.postEntity(any(), any()).thenReturn(Mono.empty()));
//
//        // Act
//        Mono<Void> result = entityService.postEntity(processId, requestBody);
//
//        // Assert
//        StepVerifier.create(result)
//                .expectComplete()
//                .verify();
//    }

//    @Test
//    void testGetEntityById() {
//        // todo: add test for getEntityById
//    }

//    @Test
//    void testUpdateEntity() {
//        // todo: add test for updateEntity
//    }

//    @Test
//    void testDeleteEntityById() {
//        // todo: add test for deleteEntityById
//    }

    // All tests -----------------------------------------------------------------------------------------

//    @Test
//    void testPublishEntity() {
//        // Arrange
//        String entity = "{\"id\":\"urn:ngsi-ld:Entity:123\",\"type\":\"Entity\",\"name\":\"Sample Entity\"}";
//        Mockito.when(brokerProperties.internalDomain()).thenReturn("https://example.com");
//        // Check if paths is null, and provide a default value if it is
//        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
//        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
//        Mockito.when(brokerPathProperties.entities()).thenReturn("/api/v1/entities");
//        doNothing().when(utils).postRequest(anyString(), anyString());
//        // Act
//        publishService.publishEntity(entity);
//        // Assert
//        verify(brokerProperties, times(1)).internalDomain();
//        verify(utils, times(1)).postRequest("https://example.com/api/v1/entities", entity);
//    }

//    @Test
//    void testGetEntity() {
//        String entityId = "urn:ngsi-ld:sample-entity";
//        String expectedResponse = "Sample entity data";
//        Mockito.when(brokerProperties.internalDomain()).thenReturn("https://example.com");
//        // Check if paths is null, and provide a default value if it is
//        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
//        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
//        Mockito.when(brokerPathProperties.entities()).thenReturn("/api/v1/entities");
//        Mockito.when(utils.getRequest("https://example.com/api/v1/entities/urn:ngsi-ld:sample-entity"))
//                .thenReturn(expectedResponse);
//        String actualResponse = entityRetrievalService.getEntity(entityId);
//        assertEquals(expectedResponse, actualResponse);
//    }

//    @Test
//    void testUpdateEntity() {
//        // Arrange
//        String entity = "{\"id\":\"urn:ngsi-ld:Entity:123\",\"type\":\"Entity\",\"name\":\"Sample Entity\"}";
//        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
//        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
//        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
//        Mockito.when(brokerPathProperties.entities()).thenReturn("/api/v1/entities");
//        doNothing().when(utils).patchRequest(any(), any());
//        // Act
//        updateService.updateEntity(entity);
//        // Assert
//        verify(brokerProperties, times(1)).internalDomain();
//        verify(brokerProperties, times(1)).paths();
//    }

//    @Test
//    void testDeleteEntity() {
//        String entityId = "urn:ngsi-ld:sample-entity";
//        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
//        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
//        when(brokerProperties.internalDomain()).thenReturn("https://example.com");
//        Mockito.when(brokerPathProperties.entities()).thenReturn("/api/v1/entities");
//        doNothing().when(utils).deleteRequest(any());
//        deleteService.deleteEntity(entityId);
//        // Assert
//        verify(brokerProperties, times(1)).internalDomain();
//        verify(brokerProperties, times(1)).paths();
//        // Verify that applicationUtils.deleteRequest was called with the correct URL
//        verify(utils).deleteRequest("https://example.com/api/v1/entities/" + entityId);
//    }

}
