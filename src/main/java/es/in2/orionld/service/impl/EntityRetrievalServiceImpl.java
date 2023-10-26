package es.in2.orionld.service.impl;

import es.in2.orionld.config.ApplicationProperties;
import es.in2.orionld.service.EntityRetrievalService;
import es.in2.orionld.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityRetrievalServiceImpl implements EntityRetrievalService {

    private final ApplicationUtils applicationUtils;
    private final ApplicationProperties applicationProperties;

    @Override
    public String getEntity(String entityId) {
        log.debug(">>> Getting entity with entity id: {}", entityId);
        String orionLdURL = applicationProperties.getOrionLdDomain() + applicationProperties.getOrionLdEntitiesPath() + "/" + entityId;
        log.debug(" > Orion-LD URL: {}", orionLdURL);
        return applicationUtils.getRequest(orionLdURL);
    }
}
