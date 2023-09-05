package es.in2.orionldinterface.domain.dto;

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
    private List<EntityDTO> entityList;

    @JsonProperty("notification")
    private NotificationDTO notification;

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
