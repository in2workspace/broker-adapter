package es.in2.brokeradapter.service;

import es.in2.brokeradapter.domain.SubscriptionRequest;
import reactor.core.publisher.Mono;

public interface SubscriptionService {
    Mono<Void> createSubscription(String processId, SubscriptionRequest subscriptionRequest);
}

