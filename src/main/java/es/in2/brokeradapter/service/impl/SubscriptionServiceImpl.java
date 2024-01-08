package es.in2.brokeradapter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import es.in2.brokeradapter.domain.*;
import es.in2.brokeradapter.exception.SubscriptionCreationException;
import es.in2.brokeradapter.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

import static es.in2.brokeradapter.utils.HttpUtils.*;
import static es.in2.brokeradapter.utils.MessageUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final ObjectMapper objectMapper;
    private final BrokerProperties brokerProperties;

    @Override
    public Mono<Void> createSubscription(String processId, SubscriptionRequest subscriptionRequest) {
        // Get all subscriptions from Context Broker
        return createSubscriptionObject(processId, subscriptionRequest)
                .flatMap(newSubscription -> getSubscriptionsFromBroker(processId)
                        .doOnSuccess(result -> log.debug(SUBSCRIPTION_RETRIEVED_MESSAGE, processId))
                        .doOnError(e -> log.error(ERROR_RETRIEVING_SUBSCRIPTION_MESSAGE, processId, e.getMessage()))
                        .flatMap(subscriptionList -> {
                            if (subscriptionList.isEmpty()) {
                                // Use Case: The subscription list is empty.
                                log.debug("ProcessId: {}, Subscription list is empty. Creating new subscription.", processId);
                                return postSubscription(processId, newSubscription)
                                        .doOnSuccess(result -> log.debug(SUBSCRIPTION_CREATED_MESSAGE, processId))
                                        .doOnError(e -> log.error(ERROR_CREATING_SUBSCRIPTION_MESSAGE, processId, e.getMessage()));
                            } else {
                                Optional<SubscriptionDTO> subscriptionItemFound = checkIfSubscriptionExists(newSubscription, subscriptionList);

                                if (subscriptionItemFound.isPresent()) {
                                    log.debug("ProcessId: {}, Subscription Entity Found: {}", processId, subscriptionItemFound.get());
                                    log.debug("ProcessId: {}, Subscription already exists. Checking if it needs to be updated.", processId);
                                    SubscriptionDTO subscriptionItem = subscriptionItemFound.get();
                                    if (checkIfSubscriptionAttributesAreEquals(subscriptionItem, newSubscription)) {
                                        // Use Case: The subscription you are trying to create already exists in the list
                                        // and the endpoint and  entities are the same.
                                        log.debug("ProcessId: {}, Does not need to be created.", processId);
                                        return Mono.empty();
                                    } else {
                                        // Use Case: The subscription you are trying to create already exists in the list
                                        // but the endpoint and the entities are different.
                                        log.debug("ProcessId: {}, Updating subscription.", processId);
                                        return updateSubscription(processId, subscriptionItem, newSubscription)
                                                .doOnSuccess(result -> log.debug(SUBSCRIPTION_UPDATED_MESSAGE, processId))
                                                .doOnError(e -> log.error(ERROR_UPDATING_SUBSCRIPTION_MESSAGE, processId, e.getMessage()));
                                    }
                                } else {
                                    log.debug("ProcessId: {}, Subscription Entity Not Found", processId);
                                    // Use Case: The subscription you are trying to create does not exist in the list.
                                    log.debug("ProcessId: {}, Subscription does not exist. Creating new subscription.", processId);
                                    return postSubscription(processId, newSubscription)
                                            .doOnSuccess(result -> log.debug(SUBSCRIPTION_CREATED_MESSAGE, processId))
                                            .doOnError(e -> log.error(ERROR_CREATING_SUBSCRIPTION_MESSAGE, processId, e.getMessage()));
                                }
                            }
                        })
                );

    }

    private Mono<SubscriptionDTO> createSubscriptionObject(String processId, SubscriptionRequest subscriptionRequest) {
        // Create Entity List you want to subscribe to
        List<SubscriptionEntityDTO> subscriptionEntityDTOList = new ArrayList<>();
        subscriptionRequest.entities().forEach(item -> subscriptionEntityDTOList.add(
                SubscriptionEntityDTO.builder().type(item).build()));
        // Create Notification you want to be notified
        SubscriptionNotificationDTO subscriptionNotificationDTO = SubscriptionNotificationDTO.builder()
                .subscriptionEndpointDTO(SubscriptionEndpointDTO.builder()
                        .uri(subscriptionRequest.notificationEndpointUri())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .receiverInfo((List.of(new RetrievalInfoContentTypeDTO())))
                        .build())
                .build();
        // Return Subscription Object
        return Mono.just(SubscriptionDTO.builder()
                        .id(subscriptionRequest.id())
                        .type(subscriptionRequest.type())
                        .entityList(subscriptionEntityDTOList)
                        .notification(subscriptionNotificationDTO)
                        .build()
                )
                .doOnSuccess(result -> log.debug(SUBSCRIPTION_OBJECT_CREATED_MESSAGE, processId))
                .doOnError(e -> log.error(ERROR_CREATING_SUBSCRIPTION_OBJECT_MESSAGE, processId, e.getMessage()));
    }

    private Mono<List<SubscriptionDTO>> getSubscriptionsFromBroker(String processId) {
        String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().subscriptions();
        List<Map.Entry<String, String>> headers = List.of(new AbstractMap.SimpleEntry<>(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE));
        return getRequest(processId, brokerURL, headers)
                .flatMap(response -> {
                    try {
                        // Parse subscription response to Subscription list
                        return Mono.just(objectMapper.readValue(response,
                                objectMapper.getTypeFactory().constructCollectionType(List.class, SubscriptionDTO.class)));
                    } catch (JsonProcessingException e) {
                        log.error(ERROR_PARSING_SUBSCRIPTION_TO_JSON_MESSAGE, processId, e.getMessage());
                        return Mono.error(new SubscriptionCreationException(ERROR_PARSING_SUBSCRIPTIONS_MESSAGE));
                    }
                });
    }

    private Mono<Void> postSubscription(String processId, SubscriptionDTO subscriptionDTO) {
        try {
            String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().subscriptions();
            List<Map.Entry<String, String>> headers = List.of(
                    new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
            return postRequest(processId, brokerURL, headers, objectMapper.writeValueAsString(subscriptionDTO))
                    .doOnSuccess(result -> log.debug(SUBSCRIPTIONS_FETCHED_SUCCESSFULLY_MESSAGE, processId))
                    .doOnError(e -> log.error(ERROR_FETCHING_SUBSCRIPTIONS_MESSAGE, processId, e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error(ERROR_PARSING_SUBSCRIPTION_TO_JSON_MESSAGE, processId, e.getMessage());
            return Mono.error(new SubscriptionCreationException(ERROR_PARSING_SUBSCRIPTIONS_MESSAGE));
        }
    }

    private Mono<Void> updateSubscription(String processId, SubscriptionDTO existingSubscription, SubscriptionDTO newSubscription) {
        try {
            String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().subscriptions() + "/" + existingSubscription.getId();
            // Copy the ID from the existing subscription to the new subscription
            newSubscription.setId(existingSubscription.getId());
            // Headers
            List<Map.Entry<String, String>> headers = List.of(
                    new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
            return patchRequest(processId, brokerURL, headers, objectMapper.writeValueAsString(newSubscription))
                    .doOnSuccess(result -> log.debug(SUBSCRIPTIONS_FETCHED_SUCCESSFULLY_MESSAGE, processId))
                    .doOnError(e -> log.error(ERROR_FETCHING_SUBSCRIPTIONS_MESSAGE, processId, e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error(ERROR_PARSING_SUBSCRIPTION_TO_JSON_MESSAGE, processId, e.getMessage());
            return Mono.error(new SubscriptionCreationException(ERROR_PARSING_SUBSCRIPTIONS_MESSAGE));
        }
    }

    private Optional<SubscriptionDTO> checkIfSubscriptionExists(SubscriptionDTO subscriptionDTO, List<SubscriptionDTO> subscriptionList) {
        return subscriptionList.stream().findAny().filter(subscriptionItem -> subscriptionItem.getId().equals(subscriptionDTO.getId()));
    }

    private boolean checkIfSubscriptionAttributesAreEquals(SubscriptionDTO subscription1, SubscriptionDTO subscription2) {
        return checkIfBothSubscriptionsHaveTheSameEndpointAttribute(subscription1, subscription2)
                && checkIfBothSubscriptionsHaveTheSameEntityList(subscription1.getEntityList(), subscription2.getEntityList());
    }

    private boolean checkIfBothSubscriptionsHaveTheSameEndpointAttribute(SubscriptionDTO subscription1, SubscriptionDTO subscription2) {
        return Objects.equals(subscription1.getNotification().getSubscriptionEndpointDTO().getUri(),
                subscription2.getNotification().getSubscriptionEndpointDTO().getUri());
    }

    private boolean checkIfBothSubscriptionsHaveTheSameEntityList(List<SubscriptionEntityDTO> entityList1, List<SubscriptionEntityDTO> entityList2) {
        if (entityList1.size() != entityList2.size()) {
            return false;
        } else {
            Set<String> typesInList1 = entityList1.stream().map(SubscriptionEntityDTO::getType).collect(Collectors.toSet());
            return entityList2.stream().allMatch(entity -> typesInList1.contains(entity.getType()));
        }
    }

}
