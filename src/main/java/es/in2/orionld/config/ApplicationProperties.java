package es.in2.orionld.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationProperties {

    @Value("${orion-ld-if.orion-ld.domain}")
    private String orionLdDomain;

    @Value("${orion-ld-if.orion-ld.path.entities}")
    private String orionLdEntitiesPath;

    @Value("${orion-ld-if.orion-ld.path.subscriptions}")
    private String orionLdSubscriptionsPath;

}
