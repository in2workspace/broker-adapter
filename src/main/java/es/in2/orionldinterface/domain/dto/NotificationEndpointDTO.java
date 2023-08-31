package es.in2.orionldinterface.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEndpointDTO {
    private String uri;
    private String accept;
}
