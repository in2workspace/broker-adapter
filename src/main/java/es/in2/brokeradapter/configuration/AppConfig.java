package es.in2.brokeradapter.configuration;

import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppConfig {

   private final BrokerProperties brokerProperties;

   @Bean
   public void configurationProperties() {
         log.debug("Broker properties: ");
         log.debug("External domain: {}", brokerProperties.externalDomain());
         log.debug("Internal domain: {}", brokerProperties.internalDomain());
         log.debug("Entities path: {}", brokerProperties.paths().entities());
         log.debug("Subscriptions path: {}", brokerProperties.paths().subscriptions());
   }

}
