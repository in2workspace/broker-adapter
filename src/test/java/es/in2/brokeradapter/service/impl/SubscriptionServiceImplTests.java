package es.in2.brokeradapter.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.configuration.properties.BrokerPathProperties;
import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import es.in2.brokeradapter.domain.*;
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

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

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
        when(brokerProperties.internalDomain()).thenReturn("https://example.com");
        when(brokerProperties.paths()).thenReturn(new BrokerPathProperties("/entities", "/subscriptions"));
        // Mock the postRequest method
        try (MockedStatic<HttpUtils> httpUtilsMockedStatic = Mockito.mockStatic(HttpUtils.class)) {

            // Mock the createSubscriptionObject method
            // todo: mock the createSubscriptionObject method
//            when(subscriptionService.createSubscriptionObject("processId", subscriptionRequest))
//                    .thenReturn(Mono.just(new SubscriptionDTO()));

            when(postRequest(anyString(), anyString(), anyList(), anyString())).thenReturn(Mono.empty());
            when(patchRequest(anyString(), anyString(), anyList(), anyString())).thenReturn(Mono.empty());
            // Act
            Mono<Void> result = subscriptionService.createSubscription("processId", subscriptionRequest);
            // Assert
            result.as(StepVerifier::create).expectComplete();
        }
    }


    // todo: add test for every use case within the createSubscription method
    private Method createSubscriptionObject() throws NoSuchMethodException {
        Method method = SubscriptionServiceImpl.class
                .getDeclaredMethod("createSubscriptionObject", String.class, SubscriptionRequest.class);
        method.setAccessible(true);
        return method;
    }

    private Method getSubscriptionsFromBroker() throws NoSuchMethodException {
        Method method = SubscriptionServiceImpl.class
                .getDeclaredMethod("getSubscriptionsFromBroker", String.class);
        method.setAccessible(true);
        return method;
    }

    private Method postSubscription() throws NoSuchMethodException {
        Method method = SubscriptionServiceImpl.class
                .getDeclaredMethod("postSubscription", String.class, SubscriptionDTO.class);
        method.setAccessible(true);
        return method;
    }

    private Method updateSubscription() throws NoSuchMethodException {
        Method method = SubscriptionServiceImpl.class
                .getDeclaredMethod("updateSubscription", String.class, SubscriptionDTO.class, SubscriptionDTO.class);
        method.setAccessible(true);
        return method;
    }

    private Method checkIfSubscriptionExists() throws NoSuchMethodException {
        Method method = SubscriptionServiceImpl.class
                .getDeclaredMethod("checkIfSubscriptionExists", SubscriptionDTO.class, List.class);
        method.setAccessible(true);
        return method;
    }

    private Method checkIfSubscriptionAttributesAreEquals() throws NoSuchMethodException {
        Method method = SubscriptionServiceImpl.class
                .getDeclaredMethod("checkIfSubscriptionAttributesAreEquals", SubscriptionDTO.class, SubscriptionDTO.class);
        method.setAccessible(true);
        return method;
    }

    private Method checkIfBothSubscriptionsHaveTheSameEndpointAttribute() throws NoSuchMethodException {
        Method method = SubscriptionServiceImpl.class
                .getDeclaredMethod("checkIfBothSubscriptionsHaveTheSameEndpointAttribute", SubscriptionDTO.class, SubscriptionDTO.class);
        method.setAccessible(true);
        return method;

    }

    private Method checkIfBothSubscriptionsHaveTheSameEntityList() throws NoSuchMethodException {
        Method method = SubscriptionServiceImpl.class
                .getDeclaredMethod("checkIfBothSubscriptionsHaveTheSameEntityList", List.class, List.class);
        method.setAccessible(true);
        return method;

    }

}

