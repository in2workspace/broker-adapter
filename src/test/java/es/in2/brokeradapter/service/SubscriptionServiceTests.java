package es.in2.brokeradapter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import es.in2.brokeradapter.service.impl.EntityServiceImpl;
import es.in2.brokeradapter.service.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(EntityServiceImpl.class)
class SubscriptionServiceTests {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private BrokerProperties brokerProperties;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

//    @Test
//    void createSubscription() {
//        // todo: add test for createSubscription
//    }

//    @Test
//    void createSubscriptionObject() {
//        // todo: add test for createSubscriptionObject
//    }

//    @Test
//    void getSubscriptionsFromBroker() {
//        // todo: add test for getSubscriptionsFromBroker
//    }

//    @Test
//    void postSubscription() {
//        // todo: add test for postSubscription
//    }

//    @Test
//    void updateSubscription() {
//        // todo: add test for updateSubscription
//    }

//    @Test
//    void checkIfSubscriptionExists() {
//        // todo: add test for checkIfSubscriptionExists
//    }

//    @Test
//    void checkIfSubscriptionAttributesAreEquals() {
//        // todo: add test for checkIfSubscriptionAttributesAreEquals
//    }

//    @Test
//    void checkIfBothSubscriptionsHaveTheSameEndpointAttribute() {
//        // todo: add test for checkIfBothSubscriptionsHaveTheSameEndpointAttribute
//    }

    // Old test code ----------------------------------------------------------

//    @Test
//    void testProcessSubscriptionRequestCreatesNewSubscription() throws JsonProcessingException {
//        String json = "{\"id\":\"urn:ngsi-ld:Subscription:123\",\"type\":\"Subscription\",\"notification-endpoint-uri\":\"https://example.com/notify\",\"entities\":[\"urn:ngsi-ld:Entity:456\"]}";
//        // Arrange
//        SubscriptionRequestDTO subscriptionRequestDTO = createSubscriptionRequestDTO(json);
//        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
//        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
//        when(utils.getRequest(any())).thenReturn("[]"); // Simulate no existing subscriptions
//        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
//        when(brokerPathProperties.subscriptions()).thenReturn("/example"); // Simulate paths
//        // Act
//        subscriptionService.processSubscriptionRequest(subscriptionRequestDTO);
//        // Assert
//        verify(utils, times(1)).postRequest(any(), any());
//    }

//    @Test
//    void testProcessSubscriptionRequestNoActionTaken() throws JsonProcessingException {
//        String json = "{\"id\":\"urn:ngsi-ld:Subscription:123\",\"type\":\"Subscription\",\"notification-endpoint-uri\":\"https://example.com/notify\",\"entities\":[\"urn:ngsi-ld:Entity:456\"]}";
//        // Arrange
//        SubscriptionRequestDTO subscriptionRequestDTO = createSubscriptionRequestDTO(json);
//        List<SubscriptionDTO> existingSubscriptions = createExistingSubscriptions();
//        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
//        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
//        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
//        when(brokerPathProperties.subscriptions()).thenReturn("/example"); // Simulate paths
//        when(utils.getRequest(any())).thenReturn(convertSubscriptionsToJson(existingSubscriptions));
//        // Act
//        subscriptionService.processSubscriptionRequest(subscriptionRequestDTO);
//        // Assert
//        verify(utils, never()).patchRequest(any(), any());
//    }

//    @Test
//    void testProcessSubscriptionRequestJsonParsingError() {
//        // Arrange
//        String invalidJson = "{invalid_json}";
//        when(utils.getRequest(any())).thenReturn("[]"); // Simulate no existing subscriptions
//        // Act and Assert
//        assertThrows(JsonParseException.class, () -> subscriptionService.processSubscriptionRequest(createSubscriptionRequestDTO(invalidJson)));
//    }

//    @Test
//    void testProcessSubscriptionRequestCreatesNewSubscriptionWhenNoExistingSubscriptions() throws JsonProcessingException {
//        String json = "{\"id\":\"urn:ngsi-ld:Subscription:123\",\"type\":\"Subscription\",\"notification-endpoint-uri\":\"https://example.com/notify\",\"entities\":[\"urn:ngsi-ld:Entity:456\"]}";
//        // Arrange
//        SubscriptionRequestDTO subscriptionRequestDTO = createSubscriptionRequestDTO(json);
//        when(utils.getRequest(any())).thenReturn("[]"); // Simulate no existing subscriptions
//        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
//        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
//        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
//        when(brokerPathProperties.subscriptions()).thenReturn("/example"); // Simulate paths
//        // Act
//        subscriptionService.processSubscriptionRequest(subscriptionRequestDTO);
//        // Assert
//        verify(utils, times(1)).postRequest(any(), any());
//    }

//    private SubscriptionRequestDTO createSubscriptionRequestDTO(String json) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(json, SubscriptionRequestDTO.class);
//    }

//    private List<SubscriptionDTO> createExistingSubscriptions() {
//        List<SubscriptionDTO> existingSubscriptions = new ArrayList<>();
//        SubscriptionDTO subscription1 = new SubscriptionDTO();
//        subscription1.setType("Subscription");
//        SubscriptionEndpointDTO endpoint1 = SubscriptionEndpointDTO.builder().uri("https://example.com/notify").build();
//        subscription1.setNotification(SubscriptionNotificationDTO.builder().subscriptionEndpointDTO(endpoint1).build());
//        List<SubscriptionEntityDTO> entities1 = Collections.singletonList(SubscriptionEntityDTO.builder().type("urn:ngsi-ld:Entity:456").build());
//        subscription1.setEntityList(entities1);
//        existingSubscriptions.add(subscription1);
//        return existingSubscriptions;
//    }

//    private String convertSubscriptionsToJson(List<SubscriptionDTO> subscriptions) {
//        return "[]";
//    }

}

