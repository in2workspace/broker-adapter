package es.in2.orionldinterface.service.impl;

import es.in2.orionldinterface.configuration.OrionLdProperties;
import es.in2.orionldinterface.service.EntityPublisherService;
import es.in2.orionldinterface.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityPublisherServiceImpl implements EntityPublisherService {

    private final ApplicationUtils applicationUtils;
    private final OrionLdProperties orionLdProperties;


    @Override
    public void publishEntity(String entity) {

        log.debug("Recieved new entity");
        log.debug("Uploading new entity..");
        applicationUtils.postRequest(orionLdProperties.getOrionLdDomain()
                + orionLdProperties.getOrionLdEntitiesPath(), entity);
        log.debug("Entity uploaded successfully");

    }
}


