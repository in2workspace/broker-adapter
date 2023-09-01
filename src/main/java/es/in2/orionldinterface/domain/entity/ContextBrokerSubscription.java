package es.in2.orionldinterface.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContextBrokerSubscription {
    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("entities")
    private List<SubscriptionEntity> entities;

    @JsonProperty("status")
    private String status;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("notification")
    private SubscriptionNotification notification;
}
