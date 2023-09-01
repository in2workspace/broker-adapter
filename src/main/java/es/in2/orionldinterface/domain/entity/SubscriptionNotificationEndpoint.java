package es.in2.orionldinterface.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionNotificationEndpoint {
    @JsonProperty("uri") private String uri;
}
