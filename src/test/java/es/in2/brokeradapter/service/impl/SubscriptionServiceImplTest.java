package es.in2.brokeradapter.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.config.BrokerPathProperties;
import es.in2.brokeradapter.config.BrokerProperties;
import es.in2.brokeradapter.model.*;
import es.in2.brokeradapter.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SubscriptionServiceImplTest {

    @Mock
    private BrokerProperties brokerProperties;

    @Mock
    private BrokerPathProperties brokerPathProperties;

    @Mock
    private ApplicationUtils applicationUtils;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
        when(brokerPathProperties.subscriptions()).thenReturn("/example");
        subscriptionService = new SubscriptionServiceImpl(brokerProperties, applicationUtils);
    }

    @Test
    void testProcessSubscriptionRequestCreatesNewSubscription() throws JsonProcessingException {

        String json = "{\"id\":\"urn:ngsi-ld:Subscription:123\",\"type\":\"Subscription\",\"notification-endpoint-uri\":\"https://example.com/notify\",\"entities\":[\"urn:ngsi-ld:Entity:456\"]}";
        // Arrange
        SubscriptionRequestDTO subscriptionRequestDTO = createSubscriptionRequestDTO(json);
        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);

        when(applicationUtils.getRequest(any())).thenReturn("[]"); // Simulate no existing subscriptions
        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
        when(brokerPathProperties.subscriptions()).thenReturn("/example"); // Simulate paths

        // Act
        subscriptionService.processSubscriptionRequest(subscriptionRequestDTO);

        // Assert
        verify(applicationUtils, times(1)).postRequest(any(), any());
    }

    @Test
    void testProcessSubscriptionRequestNoActionTaken() throws JsonProcessingException {

        String json = "{\"id\":\"urn:ngsi-ld:Subscription:123\",\"type\":\"Subscription\",\"notification-endpoint-uri\":\"https://example.com/notify\",\"entities\":[\"urn:ngsi-ld:Entity:456\"]}";
        // Arrange
        SubscriptionRequestDTO subscriptionRequestDTO = createSubscriptionRequestDTO(json);
        List<SubscriptionDTO> existingSubscriptions = createExistingSubscriptions();

        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
        when(brokerPathProperties.subscriptions()).thenReturn("/example"); // Simulate paths

        when(applicationUtils.getRequest(any())).thenReturn(convertSubscriptionsToJson(existingSubscriptions));

        // Act
        subscriptionService.processSubscriptionRequest(subscriptionRequestDTO);

        // Assert
        verify(applicationUtils, never()).patchRequest(any(), any());
    }

    @Test
    void testProcessSubscriptionRequestJsonParsingError() {
        // Arrange
        String invalidJson = "{invalid_json}";
        when(applicationUtils.getRequest(any())).thenReturn("[]"); // Simulate no existing subscriptions

        // Act and Assert
        assertThrows(JsonParseException.class, () -> subscriptionService.processSubscriptionRequest(createSubscriptionRequestDTO(invalidJson)));
    }

    @Test
    void testProcessSubscriptionRequestCreatesNewSubscriptionWhenNoExistingSubscriptions() throws JsonProcessingException {
        String json = "{\"id\":\"urn:ngsi-ld:Subscription:123\",\"type\":\"Subscription\",\"notification-endpoint-uri\":\"https://example.com/notify\",\"entities\":[\"urn:ngsi-ld:Entity:456\"]}";
        // Arrange
        SubscriptionRequestDTO subscriptionRequestDTO = createSubscriptionRequestDTO(json);
        when(applicationUtils.getRequest(any())).thenReturn("[]"); // Simulate no existing subscriptions

        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
        when(brokerPathProperties.subscriptions()).thenReturn("/example"); // Simulate paths

        // Act
        subscriptionService.processSubscriptionRequest(subscriptionRequestDTO);

        // Assert
        verify(applicationUtils, times(1)).postRequest(any(), any());
    }

    private SubscriptionRequestDTO createSubscriptionRequestDTO(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, SubscriptionRequestDTO.class);
    }

    private List<SubscriptionDTO> createExistingSubscriptions() {
        List<SubscriptionDTO> existingSubscriptions = new ArrayList<>();

        SubscriptionDTO subscription1 = new SubscriptionDTO();
        subscription1.setType("Subscription");
        SubscriptionEndpointDTO endpoint1 = SubscriptionEndpointDTO.builder().uri("https://example.com/notify").build();
        subscription1.setNotification(SubscriptionNotificationDTO.builder().subscriptionEndpointDTO(endpoint1).build());
        List<SubscriptionEntityDTO> entities1 = Collections.singletonList(SubscriptionEntityDTO.builder().type("urn:ngsi-ld:Entity:456").build());
        subscription1.setEntityList(entities1);
        existingSubscriptions.add(subscription1);

        return existingSubscriptions;
    }

    private String convertSubscriptionsToJson(List<SubscriptionDTO> subscriptions) {
        return "[]";
    }
}