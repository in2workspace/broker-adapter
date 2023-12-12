package es.in2.brokeradapter.configuration;

import es.in2.brokeradapter.configuration.properties.AppProperties;
import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import es.in2.brokeradapter.configuration.properties.OpenApiProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppConfig {

    private final BrokerProperties brokerProperties;
    private final OpenApiProperties openApiProperties;
    private final AppProperties appProperties;

    @PostConstruct
    public void init() {
        log.debug("Broker properties: ");
        log.debug("External domain: {}", brokerProperties.externalDomain());
        log.debug("Internal domain: {}", brokerProperties.internalDomain());
        log.debug("Entities path: {}", brokerProperties.paths().entities());
        log.debug("Subscriptions path: {}", brokerProperties.paths().subscriptions());
        log.debug("OpenApi properties: {}", openApiProperties);
        log.debug("App properties: {}", appProperties);
    }

}
