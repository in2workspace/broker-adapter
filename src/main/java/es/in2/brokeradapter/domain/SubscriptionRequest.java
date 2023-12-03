package es.in2.brokeradapter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record SubscriptionRequest(
        @JsonProperty("id") String id,
        @JsonProperty("type") String type,
        @JsonProperty("notification-endpoint-uri") String notificationEndpointUri,
        @JsonProperty("entities") List<String> entities
) {
}
