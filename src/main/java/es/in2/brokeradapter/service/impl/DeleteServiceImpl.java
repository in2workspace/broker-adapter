package es.in2.brokeradapter.service.impl;

import es.in2.brokeradapter.config.BrokerProperties;
import es.in2.brokeradapter.service.DeleteService;
import es.in2.brokeradapter.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteServiceImpl implements DeleteService {
    private final ApplicationUtils applicationUtils;
    private final BrokerProperties brokerProperties;

    @Override
    public void deleteEntity(String entityId) {
        log.debug(">>> Deleting entity with entity id: {}", entityId);
        String orionLdURL = brokerProperties.internalDomain() + brokerProperties.paths().entities() + "/" + entityId;
        log.debug(" > Orion-LD URL: {}", orionLdURL);
        applicationUtils.deleteRequest(orionLdURL);
    }
}
