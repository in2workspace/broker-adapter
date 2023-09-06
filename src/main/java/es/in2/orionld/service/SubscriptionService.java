package es.in2.orionld.service;

import es.in2.orionld.model.SubscriptionRequestDTO;

public interface SubscriptionService {
    void processSubscriptionRequest(SubscriptionRequestDTO subscriptionRequestDTO);
}

