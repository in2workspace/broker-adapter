package es.in2.orionldinterface.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "api")
public class ContextBrokerConfigApi {
    private String subscriptionUrl;
    private String entitiesUrl;
    private String accessToken;
}
