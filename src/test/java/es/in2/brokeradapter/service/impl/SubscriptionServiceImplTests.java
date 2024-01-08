package es.in2.brokeradapter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.configuration.properties.BrokerPathProperties;
import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import es.in2.brokeradapter.domain.*;
import es.in2.brokeradapter.utils.HttpUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static es.in2.brokeradapter.utils.HttpUtils.patchRequest;
import static es.in2.brokeradapter.utils.HttpUtils.postRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)

class SubscriptionServiceImplTests {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private BrokerProperties brokerProperties;

    @Mock
    private BrokerPathProperties brokerPathProperties;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        subscriptionService = new SubscriptionServiceImpl(objectMapper, brokerProperties);
    }

    private SubscriptionRequest subscriptionRequest = SubscriptionRequest.builder()
            .id("1234")
            .type("Subscription")
            .entities(List.of("ProductOffering"))
            .notificationEndpointUri("https://example.com")
            .build();

    private SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
            .id("1234")
            .type("Subscription")
            .entityList(List.of(SubscriptionEntityDTO.builder()
                    .type("ProductOffering")
                    .build()))
            .notification(SubscriptionNotificationDTO.builder()
                    .subscriptionEndpointDTO(
                            SubscriptionEndpointDTO.builder()
                                    .uri("https://example.com")
                                    .accept("application/json")
                                    .receiverInfo(List.of(RetrievalInfoContentTypeDTO.builder()
                                            .contentType("application/json")
                                            .build()))
                                    .build())
                    .build())
            .build();

    @Test
    void testCreateSubscription() {
        // Arrange
        SubscriptionRequest subscriptionRequest = SubscriptionRequest.builder()
                .id("1234")
                .type("Subscription")
                .entities(List.of("ProductOffering"))
                .notificationEndpointUri("https://example.com")
                .build();
        // Mock the postRequest method
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {


            // Mock the broker properties and the HTTP request
            when(brokerProperties.internalDomain()).thenReturn("http://example.com");
            when(brokerProperties.paths()).thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
            when(HttpUtils.getRequest(anyString(), anyString(), anyList())).thenReturn(Mono.just("[]")); // Empty list of subscriptions
            when(HttpUtils.postRequest(anyString(), anyString(), anyList(), anyString())).thenReturn(Mono.empty());
            Mono<Void> result = subscriptionService.createSubscription("processId", subscriptionRequest);
            // Assert
            result.as(StepVerifier::create).verifyComplete();

        }

    }

    @Test
    void testUpdateSubscription() {
        // Arrange
        SubscriptionRequest subscriptionRequest = SubscriptionRequest.builder()
                .id("1234")
                .type("Subscription")
                .entities(List.of("ProductOffering"))
                .notificationEndpointUri("https://example.com")
                .build();

        String getResponse = "[ {\n" +
                "  \"id\" : \"1234\",\n" +
                "  \"type\" : \"Subscription\",\n" +
                "  \"entities\" : [ {\n" +
                "    \"type\" : \"ProductOffering\"\n" +
                "  }, {\n" +
                "    \"type\" : \"ProductOrder\"\n" +
                "  } ],\n" +
                "  \"notification\" : {\n" +
                "    \"endpoint\" : {\n" +
                "      \"accept\" : \"application/json\",\n" +
                "      \"receiverInfo\" : [ {\n" +
                "        \"Content-Type\" : \"application/json\"\n" +
                "      } ],\n" +
                "      \"uri\" : \"https://example.com\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"timesFailed\" : 0,\n" +
                "  \"timesSent\" : 0\n" +
                "} ]";

        // Mock the postRequest method
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {


            // Mock the broker properties and the HTTP request
            when(brokerProperties.internalDomain()).thenReturn("http://example.com");
            when(brokerProperties.paths()).thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
            when(HttpUtils.getRequest(anyString(), anyString(), anyList())).thenReturn(Mono.just(getResponse)); // List of subscriptions
            when(HttpUtils.patchRequest(anyString(), anyString(), anyList(), anyString())).thenReturn(Mono.empty());
            Mono<Void> result = subscriptionService.createSubscription("processId", subscriptionRequest);
            // Assert
            result.as(StepVerifier::create).verifyComplete();

        }


    }

    @Test
    void testUpdateSubscription_sameSubscriptions() {
        // Arrange
        SubscriptionRequest subscriptionRequest = SubscriptionRequest.builder()
                .id("1234")
                .type("Subscription")
                .entities(List.of("ProductOffering"))
                .notificationEndpointUri("https://example.com")
                .build();

        String getResponse = "[ {\n" +
                "  \"id\" : \"1234\",\n" +
                "  \"type\" : \"Subscription\",\n" +
                "  \"entities\" : [ {\n" +
                "    \"type\" : \"ProductOffering\"\n" +
                "  }],\n" +
                "  \"notification\" : {\n" +
                "    \"endpoint\" : {\n" +
                "      \"accept\" : \"application/json\",\n" +
                "      \"receiverInfo\" : [ {\n" +
                "        \"Content-Type\" : \"application/json\"\n" +
                "      } ],\n" +
                "      \"uri\" : \"https://example.com\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"timesFailed\" : 0,\n" +
                "  \"timesSent\" : 0\n" +
                "} ]";

        // Mock the postRequest method
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {


            // Mock the broker properties and the HTTP request
            when(brokerProperties.internalDomain()).thenReturn("http://example.com");
            when(brokerProperties.paths()).thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
            when(HttpUtils.getRequest(anyString(), anyString(), anyList())).thenReturn(Mono.just(getResponse)); // List of subscriptions
            when(HttpUtils.patchRequest(anyString(), anyString(), anyList(), anyString())).thenReturn(Mono.empty());
            Mono<Void> result = subscriptionService.createSubscription("processId", subscriptionRequest);
            // Assert
            result.as(StepVerifier::create).verifyComplete();

        }


    }
}

