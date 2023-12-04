package es.in2.brokeradapter.controller;

import es.in2.brokeradapter.domain.SubscriptionRequest;
import es.in2.brokeradapter.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTests {

    @Mock
    private SubscriptionService subscriptionService;

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Test
    void testCreateSubscription() {
        // Arrange
        SubscriptionRequest subscriptionRequest = SubscriptionRequest.builder()
                .id("urn:ngsi-ld:Subscription:12345678")
                .type("Subscription")
                .entities(List.of("ProductOffering"))
                .notificationEndpointUri("http://localhost:8080/notifications")
                .build();
        when(subscriptionService.createSubscription(anyString(), any())).thenReturn(Mono.empty());
        // Act & Assert
        WebTestClient
                .bindToController(subscriptionController)
                .build()
                .post()
                .uri("/api/v2/subscriptions")
                .bodyValue(subscriptionRequest)
                .exchange()
                .expectStatus().isCreated();
    }

}
