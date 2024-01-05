package es.in2.brokeradapter.configuration;

import es.in2.brokeradapter.configuration.properties.NgsiLdSubscriptionConfigProperties;
import es.in2.brokeradapter.domain.SubscriptionRequest;
import es.in2.brokeradapter.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BrokerSubscriptionInitializer {
    private final NgsiLdSubscriptionConfigProperties subscriptionConfiguration;
    private final SubscriptionService subscriptionService;

    @EventListener(ApplicationReadyEvent.class)
    public Mono<Void> setBrokerSubscription() {
        String processId = UUID.randomUUID().toString();
        log.info("ProcessID: {} - Setting Orion-LD Entities subscription...", processId);
        SubscriptionRequest brokerSubscriptionRequest = SubscriptionRequest.builder()
                .id("urn:ngsi-ld:Subscription:" + UUID.randomUUID())
                .type("Subscription")
                .notificationEndpointUri(subscriptionConfiguration.notificationEndpoint())
                .entities(subscriptionConfiguration.entityTypes())
                .build();

        log.debug("ProcessID: {} - Broker Subscription: {}", processId, brokerSubscriptionRequest.toString());
        return subscriptionService.createSubscription(processId, brokerSubscriptionRequest)
                .doOnSuccess(response -> log.info("ProcessID: {} - Default subscription created successfully", processId))
                .doOnError(e -> {
                    log.error("ProcessID: {} - Error creating default subscription", processId, e);
                    if (e instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                    }
                });
    }
}
