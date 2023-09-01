package es.in2.orionldinterface.service.impl;

import es.in2.orionldinterface.configuration.ContextBrokerConfigApi;
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
    private final ContextBrokerConfigApi contextBrokerConfigApi;


    @Override
    public void publishEntity(String entity) {

        log.debug("Recieved new entity");
        log.debug("Uploading new entity..");
        applicationUtils.postRequest(contextBrokerConfigApi.getEntitiesUrl(), entity);
        log.debug("Entity uploaded successfully");

    }
}
