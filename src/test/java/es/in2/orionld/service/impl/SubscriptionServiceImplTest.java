package es.in2.orionld.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.orionld.config.ApplicationProperties;
import es.in2.orionld.model.*;
import es.in2.orionld.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SubscriptionServiceImplTest {

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private ApplicationUtils applicationUtils;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        subscriptionService = new SubscriptionServiceImpl(applicationProperties, applicationUtils);
    }

    @Test
    void testProcessSubscriptionRequestCreatesNewSubscription() throws JsonProcessingException {

        String json = "{\"id\":\"urn:ngsi-ld:Subscription:123\",\"type\":\"Subscription\",\"notification-endpoint-uri\":\"https://example.com/notify\",\"entities\":[\"urn:ngsi-ld:Entity:456\"]}";
        // Arrange
        SubscriptionRequestDTO subscriptionRequestDTO = createSubscriptionRequestDTO(json);

        when(applicationUtils.getRequest(any())).thenReturn("[]"); // Simulate no existing subscriptions

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
