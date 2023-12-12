package es.in2.brokeradapter.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

/**
 * AppProperties
 *
 * @param contextEnabled - Enabled Context Properties
 */
@ConfigurationProperties(prefix = "app")
public record AppProperties(Boolean contextEnabled) {

    @ConstructorBinding
    public AppProperties(Boolean contextEnabled) {
        this.contextEnabled = Optional.ofNullable(contextEnabled).orElse(true);
    }

}
