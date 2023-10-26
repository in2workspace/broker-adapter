package es.in2.orionld.service.impl;

import es.in2.orionld.config.ApplicationProperties;
import es.in2.orionld.service.PublishService;
import es.in2.orionld.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishServiceImpl implements PublishService {

    private final ApplicationUtils applicationUtils;
    private final ApplicationProperties applicationProperties;

    @Override
    public void publishEntity(String entity) {
        log.debug(">>> Publishing entity...");
        String orionLdURL = applicationProperties.getOrionLdDomain() + applicationProperties.getOrionLdEntitiesPath();
        log.debug(" > Orion-LD URL: {}", orionLdURL);
        log.debug(" > Request body: {}", entity);
        applicationUtils.postRequest(orionLdURL, entity);
        log.debug(" > Entity uploaded successfully");
    }

}
