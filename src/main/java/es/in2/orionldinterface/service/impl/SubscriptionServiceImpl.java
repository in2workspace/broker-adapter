package es.in2.orionldinterface.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.orionldinterface.configuration.OrionLdProperties;
import es.in2.orionldinterface.domain.dto.*;
import es.in2.orionldinterface.exception.SubscriptionConfigException;
import es.in2.orionldinterface.service.SubscriptionService;
import es.in2.orionldinterface.utils.ApplicationUtils;
import es.in2.orionldinterface.utils.OrionInterfaceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final OrionLdProperties orionLdproperties;
    private final ApplicationUtils applicationUtils;

    @Override
    public void createSubscription(OrionLdSubscriptionRequestDTO orionLdSubscriptionRequestDTO) {
        log.debug(">>> Creating or updating Orion-LD subscription...");

        // Map Orion-LD subscription request to Orion-LD subscription DTO
        SubscriptionDTO newSubscription = createSubscriptionDTO(orionLdSubscriptionRequestDTO);
        log.debug(" > Orion-LD subscription: {}", newSubscription.toString());

        try {
            // Get Orion-LD stored subscriptions
            List<SubscriptionDTO> subscriptionList = getOrionLdSubscriptions();
            log.debug(" > Orion-LD subscriptions: {}", subscriptionList.toString());


            if (subscriptionExists(newSubscription, subscriptionList)) {
                // Subscription with the same endpoint and entity list already exists, do nothing
                log.debug(" > Subscription with the same endpoint and entity list already exists. Does not need to be created or updated.");
            } else {
                // Check if subscription already exists or needs to be updated
                boolean createSubscription = true;
                for (SubscriptionDTO existingSubscription : subscriptionList) {
                    if (areEndpointsEqual(existingSubscription, newSubscription)) {
                        // Update subscription if the endpoint is the same but entities are different
                        log.debug(" > Updating subscription with the same endpoint but different entities.");
                        updateSubscription(existingSubscription, newSubscription);
                        createSubscription = false;
                    } else if (areSubscriptionsEqual(existingSubscription, newSubscription)) {
                        // Subscription already exists and is identical, no need to create a new one
                        log.debug(" > Subscription already exists. Does not need to be created.");
                        createSubscription = false;
                    }
                }

                if (createSubscription) {
                    // Create & publish Subscription (POST)
                    createOrionLdSubscription(newSubscription);
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Error while handling subscription: {}", e.getMessage());
            throw new SubscriptionConfigException("Error while handling subscription.");
        }
    }



    private boolean areEndpointsEqual(SubscriptionDTO subscription1, SubscriptionDTO subscription2) {
        return Objects.equals(subscription1.getNotification().getEndpointDTO().getUri(),
                subscription2.getNotification().getEndpointDTO().getUri());
    }



    private static SubscriptionDTO createSubscriptionDTO(OrionLdSubscriptionRequestDTO orionLdSubscriptionRequestDTO) {
        List<EntityDTO> entityDTOList = new ArrayList<>();
        orionLdSubscriptionRequestDTO.getEntities().forEach(item ->
                entityDTOList.add(EntityDTO.builder()
                        .type(item)
                        .build()));

        return SubscriptionDTO.builder()
                .id(OrionInterfaceUtils.SUBSCRIPTION_ID_PFIX + RandomStringUtils.randomAlphanumeric(6))
                .type(orionLdSubscriptionRequestDTO.getType())
                .entityList(entityDTOList)
                .notification(
                        NotificationDTO.builder()
                                .endpointDTO(
                                        EndpointDTO.builder()
                                                .uri(orionLdSubscriptionRequestDTO.getNotificationEndpointUri())
                                                .build())
                                .build()
                )
                .build();
    }

    private boolean subscriptionExists(SubscriptionDTO subscriptionDTO, List<SubscriptionDTO> subscriptionList) {
        return subscriptionList.stream()
                .anyMatch(subscription -> areSubscriptionsEqual(subscription, subscriptionDTO));
    }

    private boolean areSubscriptionsEqual(SubscriptionDTO subscription1, SubscriptionDTO subscription2) {
        return subscription1.getType().equals(subscription2.getType())
                && compareEntityLists(subscription1.getEntityList(), subscription2.getEntityList())
                && Objects.equals(subscription1.getNotification().getEndpointDTO().getUri(), subscription2.getNotification().getEndpointDTO().getUri());
    }

    private boolean compareEntityLists(List<EntityDTO> list1, List<EntityDTO> list2) {
        // First, ensure that both lists have the same size
        if (list1.size() != list2.size()) {
            return false;
        }
        // Create a set of types from list1
        Set<String> typesInList1 = list1.stream()
                .map(EntityDTO::getType)
                .collect(Collectors.toSet());
        // Check if all types in list2 are present in the set of types from list1
        return list2.stream()
                .allMatch(entity -> typesInList1.contains(entity.getType()));
    }

    private List<SubscriptionDTO> getOrionLdSubscriptions() throws JsonProcessingException {
        // Orion-LD URL
        String orionLdURL = orionLdproperties.getOrionLdDomain() + orionLdproperties.getOrionLdSubscriptionsPath();
        log.debug(" > Getting Orion-LD subscriptions from: {}", orionLdURL);
        // Get subscriptions from Orion-LD
        String response = applicationUtils.getRequest(orionLdURL);
        // Parse response to SubscriptionDTO list
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, SubscriptionDTO.class));
    }

    private void createOrionLdSubscription(SubscriptionDTO subscriptionDTO) throws JsonProcessingException {
        // Orion-LD URL
        String orionLdURL = orionLdproperties.getOrionLdDomain() + orionLdproperties.getOrionLdSubscriptionsPath();
        log.debug(" > Posting subscription to Orion-LD: {}", orionLdURL);
        // Parse subscription to JSON String object.
        String requestBody = new ObjectMapper().writeValueAsString(subscriptionDTO);
        log.debug(" > Posting subscription to Orion-LD: {}", requestBody);
        // Post subscription to Context Broker
        applicationUtils.postRequest(orionLdURL, requestBody);
    }

    private void updateSubscription(SubscriptionDTO existingSubscription, SubscriptionDTO newSubscription) throws JsonProcessingException {
        // Orion-LD URL for updating the existing subscription
        String orionLdURL = orionLdproperties.getOrionLdDomain() + orionLdproperties.getOrionLdSubscriptionsPath() + "/" + existingSubscription.getId();
        log.debug("Updating subscription in Orion-LD: {}", orionLdURL);

        // Copy the ID from the existing subscription to the new subscription
        newSubscription.setId(existingSubscription.getId());

        // Parse the new subscription (with the same ID) to JSON String object for the update
        String requestBody = new ObjectMapper().writeValueAsString(newSubscription);
        log.debug("Updating subscription in Orion-LD: {}", requestBody);

        // Update subscription in Context Broker
        applicationUtils.patchRequest(orionLdURL, requestBody);
        log.debug("Subscription updated successfully");
    }



}
