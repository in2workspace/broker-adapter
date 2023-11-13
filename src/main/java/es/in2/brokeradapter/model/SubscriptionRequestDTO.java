package es.in2.brokeradapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class SubscriptionRequestDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("notification-endpoint-uri")
    private String notificationEndpointUri;

    @JsonProperty("entities")
    private List<String> entities;

}
