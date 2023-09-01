package es.in2.orionldinterface.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private String id;
    private String type;
    private List<EntityDTO> entities;
    private String status;
    @JsonProperty("isActive")
    private boolean isActive;
    private SubscriptionNotificationDTO notification;
}