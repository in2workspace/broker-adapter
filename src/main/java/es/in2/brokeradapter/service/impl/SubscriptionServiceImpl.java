package es.in2.brokeradapter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.config.BrokerProperties;
import es.in2.brokeradapter.exception.SubscriptionCreationException;
import es.in2.brokeradapter.model.SubscriptionDTO;
import es.in2.brokeradapter.model.SubscriptionEndpointDTO;
import es.in2.brokeradapter.model.SubscriptionEntityDTO;
import es.in2.brokeradapter.model.SubscriptionNotificationDTO;
import es.in2.brokeradapter.model.SubscriptionRequestDTO;
import es.in2.brokeradapter.service.SubscriptionService;
import es.in2.brokeradapter.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    public static final String SUBSCRIPTION_ID_PREFIX = "urn:ngsi-ld:Subscription:";
    private final BrokerProperties brokerProperties;
    private final ApplicationUtils applicationUtils;

    @Override
    public void processSubscriptionRequest(SubscriptionRequestDTO subscriptionRequestDTO) {
        log.info(">>> Creating Orion-LD subscription...");
        // Create SubscriptionDTO from SubscribeRequestDTO
        SubscriptionDTO newSubscription = createSubscriptionDTO(subscriptionRequestDTO);
        log.debug(" > Orion-LD subscription: {}", newSubscription.toString());
        // Get Orion-LD stored subscriptions
        List<SubscriptionDTO> subscriptionListFound = getSubscriptions();
        log.debug(" > Orion-LD subscriptions found: {}", subscriptionListFound.toString());
        // The list of subscriptions returned by Orion-LD is empty, so we create the subscription.
        if(subscriptionListFound.isEmpty()) {
            createSubscription(newSubscription);
        }
        // The list of subscriptions returned by Orion-LD is not empty, so we check if the subscription already exists.
        else if (checkIfSubscriptionExists(newSubscription, subscriptionListFound)) {
            // The subscription you are trying to create has the same notification endpoint and entity list as an
            // existing subscription. Therefore, it is not necessary to create a new subscription.
            log.info(" > Subscription already exists. Does not need to be created.");
        }
        // In this case, the subscription does not exactly exist within the list, so we create or update it.
        else {
            subscriptionListFound.forEach(subscription -> {
                // If the endpoints are the same and the entities are not the same, we update the subscription.
                if (areEndpointsEqual(subscription, newSubscription)
                        && !areEntityListEqual(subscription.getEntityList(), newSubscription.getEntityList())) {
                    // Update subscription if the endpoint is the same but entities are different
                    log.debug(" > Updating subscription with the same endpoint but different entities.");
                    updateSubscription(subscription, newSubscription);
                }
                // If the endpoints are not the same, we create a new subscription.
                else if (!areEndpointsEqual(subscription, newSubscription)) {
                    // Create & publish Subscription (POST)
                    createSubscription(newSubscription);
                }
            });
        }
    }

    private static SubscriptionDTO createSubscriptionDTO(SubscriptionRequestDTO subscriptionRequestDTO) {
        // Create random ID for the subscription
        String id = SUBSCRIPTION_ID_PREFIX + UUID.randomUUID();
        // Create SubscriptionEntityDTO list from SubscribeRequestDTO
        List<SubscriptionEntityDTO> subscriptionEntityDTOList = new ArrayList<>();
        subscriptionRequestDTO.getEntities().forEach(item -> subscriptionEntityDTOList.add(
                SubscriptionEntityDTO.builder().type(item).build()));
        // Create SubscriptionEndpointDTO from SubscribeRequestDTO
        SubscriptionEndpointDTO subscriptionEndpointDTO = SubscriptionEndpointDTO.builder()
                .uri(subscriptionRequestDTO.getNotificationEndpointUri())
                .build();
        // Create SubscriptionNotificationDTO from SubscriptionEndpointDTO
        SubscriptionNotificationDTO subscriptionNotificationDTO = SubscriptionNotificationDTO.builder()
                .subscriptionEndpointDTO(subscriptionEndpointDTO)
                .build();
        // Create SubscriptionDTO from SubscribeRequestDTO
        return SubscriptionDTO.builder()
                .id(id)
                .type(subscriptionRequestDTO.getType())
                .entityList(subscriptionEntityDTOList)
                .notification(subscriptionNotificationDTO)
                .build();
    }

    private List<SubscriptionDTO> getSubscriptions() {
        // Orion-LD URL
        String orionLdURL = brokerProperties.domain() +
                brokerProperties.paths().subscriptions();
        log.debug(" > Getting Orion-LD subscriptions from: {}", orionLdURL);
        // Get subscriptions from Orion-LD
        String response = applicationUtils.getRequest(orionLdURL);
        log.debug(" > Orion-LD subscriptions: {}", response);
        // Parse subscriptions to SubscriptionDTO list
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, SubscriptionDTO.class));
        } catch (JsonProcessingException e) {
            log.error("Error parsing subscriptions from Orion-LD: {}", e.getMessage());
            throw new SubscriptionCreationException("Error parsing subscriptions from Orion-LD");
        }

    }

    private boolean checkIfSubscriptionExists(SubscriptionDTO subscriptionDTO, List<SubscriptionDTO> subscriptionList) {
        return subscriptionList.stream()
                .anyMatch(subscription -> areSubscriptionsEqual(subscription, subscriptionDTO));
    }

    private boolean areSubscriptionsEqual(SubscriptionDTO subscription1, SubscriptionDTO subscription2) {
        return areEndpointsEqual(subscription1, subscription2)
                && areEntityListEqual(subscription1.getEntityList(), subscription2.getEntityList());
    }

    private boolean areEndpointsEqual(SubscriptionDTO subscription1, SubscriptionDTO subscription2) {
        return Objects.equals(subscription1.getNotification().getSubscriptionEndpointDTO().getUri(),
                subscription2.getNotification().getSubscriptionEndpointDTO().getUri());
    }

    private boolean areEntityListEqual(List<SubscriptionEntityDTO> list1, List<SubscriptionEntityDTO> list2) {
        // First, ensure that both lists have the same size
        if (list1.size() != list2.size()) {
            return false;
        }
        // Create a set of types from list1
        Set<String> typesInList1 = list1.stream()
                .map(SubscriptionEntityDTO::getType)
                .collect(Collectors.toSet());
        // Check if all types in list2 are present in the set of types from list1
        return list2.stream()
                .allMatch(entity -> typesInList1.contains(entity.getType()));
    }



    private void createSubscription(SubscriptionDTO subscriptionDTO) {
        if(subscriptionDTO.getNotification().getSubscriptionEndpointDTO().getReceiverInfo() == null) {
            subscriptionDTO.getNotification().getSubscriptionEndpointDTO().setReceiverInfo(new ArrayList<>());
        }
        // Orion-LD URL
        String orionLdURL = brokerProperties.domain() + brokerProperties.paths().subscriptions();
        log.debug(" > Posting subscription to Orion-LD: {}", orionLdURL);
        // Parse subscription to JSON String object.
        String requestBody;
        try {
            requestBody = new ObjectMapper().writeValueAsString(subscriptionDTO);
            log.debug(" > Posting subscription to Orion-LD: {}", requestBody);
        } catch (JsonProcessingException e) {
            log.error("Error parsing subscription to JSON: {}", e.getMessage());
            throw new SubscriptionCreationException("Error parsing subscription to JSON");
        }
        // Post subscription to Context Broker
        applicationUtils.postRequest(orionLdURL, requestBody);
    }

    private void updateSubscription(SubscriptionDTO existingSubscription, SubscriptionDTO newSubscription) {
        // Orion-LD URL for updating the existing subscription
        String orionLdURL = brokerProperties.domain() + brokerProperties.paths().subscriptions() + "/" + existingSubscription.getId();
        log.debug("Updating subscription in Orion-LD: {}", orionLdURL);
        // Copy the ID from the existing subscription to the new subscription
        newSubscription.setId(existingSubscription.getId());
        // Parse the new subscription (with the same ID) to JSON String object for the update
        String requestBody;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            requestBody = objectMapper.writeValueAsString(newSubscription);
            log.debug("Updating subscription in Orion-LD: {}", requestBody);
        } catch (JsonProcessingException e) {
            log.error("Error parsing subscription to JSON: {}", e.getMessage());
            throw new SubscriptionCreationException("Error parsing subscription to JSON");
        }
        // Update subscription in Context Broker
        applicationUtils.patchRequest(orionLdURL, requestBody);
        log.debug("Subscription updated successfully");
    }

}
