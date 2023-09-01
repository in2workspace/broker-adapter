package es.in2.orionldinterface.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "subscription")
public class ContextBrokerConfigSubscription {
    private List<Map<String, Object>> entities;
    private String name;
    private String idPrefix;
    private String type;
    private String notificationEndpointUri;
}
