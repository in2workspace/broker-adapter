package es.in2.orionld.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionNotificationDTO {

    @JsonProperty("endpoint")
    private SubscriptionEndpointDTO subscriptionEndpointDTO;

}
