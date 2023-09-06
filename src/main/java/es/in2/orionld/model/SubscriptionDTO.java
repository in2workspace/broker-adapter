package es.in2.orionld.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("entities")
    private List<SubscriptionEntityDTO> entityList;

    @JsonProperty("notification")
    private SubscriptionNotificationDTO notification;

    @Override
    public String toString() {
        return "SubscriptionDTO {" +
                "id " + id +
                ", type = " + type +
                ", entityList = " + entityList +
                ", notification = " + notification +
                '}';
    }

}
