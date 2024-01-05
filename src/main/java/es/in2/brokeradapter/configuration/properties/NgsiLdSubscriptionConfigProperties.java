package es.in2.brokeradapter.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;
import java.util.Optional;

@ConfigurationProperties(prefix = "ngsi-subscription")
public record NgsiLdSubscriptionConfigProperties(String notificationEndpoint, String subscriptionType, String idPrefix,
                                                 List<String> entityTypes) {

    @ConstructorBinding
    public NgsiLdSubscriptionConfigProperties(String notificationEndpoint, String subscriptionType, String idPrefix,
                                              List<String> entityTypes) {
        this.notificationEndpoint = Optional.ofNullable(notificationEndpoint).orElse("http://blockchain-connector-core:8080/notifications/broker");
        this.subscriptionType = Optional.ofNullable(subscriptionType).orElse("Subscription");
        this.idPrefix = Optional.ofNullable(idPrefix).orElse("urn:ngsi-ld:Subscription:");
        this.entityTypes = Optional.ofNullable(entityTypes).orElse(List.of());
    }

}
