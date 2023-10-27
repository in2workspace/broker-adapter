package es.in2.orionld.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

@ConfigurationProperties(prefix = "blockchain-connector")
public record BlockchainConnectorProperties(String domain, @NestedConfigurationProperty BlockchainConnectorPathProperties paths) {

	@ConstructorBinding
	public BlockchainConnectorProperties(String domain, BlockchainConnectorPathProperties paths) {
		this.domain = domain;
		this.paths = Optional.ofNullable(paths).orElse(new BlockchainConnectorPathProperties(null, null));
	}
}
