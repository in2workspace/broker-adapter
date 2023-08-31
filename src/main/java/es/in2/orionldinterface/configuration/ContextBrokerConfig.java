package es.in2.orionldinterface.configuration;

import es.in2.orionldinterface.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ContextBrokerConfig {

    private final SubscriptionService subscriptionService;

    @Bean
    @Profile("!default")
    public void setDefaultSubscriptions() {

        try {
            subscriptionService.createDefaultSubscription();
        } catch (Exception e) {
            log.error("Error creating default subscription", e);
            Thread.currentThread().interrupt();
        }

    }

}
