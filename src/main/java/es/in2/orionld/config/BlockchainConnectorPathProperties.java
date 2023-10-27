package es.in2.orionld.config;

import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Optional;

public record BlockchainConnectorPathProperties(String entities, String subscriptions) {

	@ConstructorBinding
	public BlockchainConnectorPathProperties(String entities, String subscriptions) {
		this.entities = Optional.ofNullable(entities).orElse("/ngsi-ld/v1/entities");
		this.subscriptions = Optional.ofNullable(subscriptions).orElse("/ngsi-ld/v1/subscriptions");
	}
}
