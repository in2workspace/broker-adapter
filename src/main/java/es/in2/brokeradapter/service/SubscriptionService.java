package es.in2.brokeradapter.service;

import es.in2.brokeradapter.model.SubscriptionRequestDTO;

public interface SubscriptionService {
    void processSubscriptionRequest(SubscriptionRequestDTO subscriptionRequestDTO);
}

