package es.in2.orionld.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

/**
 * Configuration of the broker to connect to.
 * @param domain - address of the broker, can be internal
 * @param paths - paths to be used with that broker instance
 */
@ConfigurationProperties(prefix = "broker")
public record BrokerProperties(String domain, @NestedConfigurationProperty BrokerPathProperties paths) {

	@ConstructorBinding
	public BrokerProperties(String domain, BrokerPathProperties paths) {
		this.domain = domain;
		this.paths = Optional.ofNullable(paths).orElse(new BrokerPathProperties(null, null));
	}
}
