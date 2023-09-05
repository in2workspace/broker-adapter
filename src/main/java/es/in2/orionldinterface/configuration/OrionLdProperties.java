package es.in2.orionldinterface.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OrionLdProperties {

    @Value("${orion-ld-if.orion-ld.domain}")
    private String orionLdDomain;

    @Value("${orion-ld-if.orion-ld.path.entities}")
    private String orionLdEntitiesPath;

}
