package es.in2.brokeradapter.service.impl;

import es.in2.brokeradapter.config.BrokerProperties;
import es.in2.brokeradapter.service.EntityRetrievalService;
import es.in2.brokeradapter.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityRetrievalServiceImpl implements EntityRetrievalService {

	private final ApplicationUtils applicationUtils;
	private final BrokerProperties brokerProperties;

	@Override
	public String getEntity(String entityId) {
		log.debug(">>> Getting entity with entity id: {}", entityId);
		String orionLdURL = brokerProperties.domain() + brokerProperties.paths().entities() + "/" + entityId;
		log.debug(" > Orion-LD URL: {}", orionLdURL);
		return applicationUtils.getRequest(orionLdURL);
	}
}
