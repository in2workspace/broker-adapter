package es.in2.orionldinterface.service;

import es.in2.orionldinterface.domain.dto.OrionLdSubscriptionRequestDTO;

public interface SubscriptionService {
    void createDefaultSubscription(OrionLdSubscriptionRequestDTO orionLdSubscriptionRequestDTO);
}

