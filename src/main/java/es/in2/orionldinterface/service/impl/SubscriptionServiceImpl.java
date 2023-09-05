package es.in2.orionldinterface.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.orionldinterface.configuration.OrionLdProperties;
import es.in2.orionldinterface.domain.dto.*;
import es.in2.orionldinterface.exception.SubscriptionConfigException;
import es.in2.orionldinterface.service.SubscriptionService;
import es.in2.orionldinterface.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final OrionLdProperties orionLdproperties;
    private final ApplicationUtils applicationUtils;

    public void createDefaultSubscription(OrionLdSubscriptionRequestDTO orionLdSubscriptionRequestDTO) {

        log.debug("Creating default subscription...");

        List<EntityDTO> entityDTOList = new ArrayList<>();
        orionLdSubscriptionRequestDTO.getEntities().forEach(item ->
                entityDTOList.add(EntityDTO.builder()
                        .type(item)
                        .build()));
        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                .id(orionLdSubscriptionRequestDTO.getId())
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

        try {
            // get Orion-LD stored subscriptions
            List<SubscriptionDTO> subscriptionList = getOrionLdSubscriptions();

            // create/update subscription
            if (subscriptionList.isEmpty()) {
                // create & publish Subscription (POST)
                createOrionLdSubscription(subscriptionDTO);
            } else {
                // update & publish Subscription (PATCH)
                updateSubscription(subscriptionDTO);
            }
        } catch (JsonProcessingException e) {
            throw new SubscriptionConfigException("Error while handling subscription.");
        }
    }

    private List<SubscriptionDTO> getOrionLdSubscriptions() throws JsonProcessingException {
        // Orion-LD URL
        String orionLdURL = orionLdproperties.getOrionLdDomain() + orionLdproperties.getOrionLdSubscriptionsPath();
        log.debug("URL {}", orionLdURL);
        String response = applicationUtils.getRequest(orionLdURL);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                response,
                objectMapper.getTypeFactory().constructCollectionType(List.class, SubscriptionDTO.class));
    }

    private void createOrionLdSubscription(SubscriptionDTO subscriptionDTO) throws JsonProcessingException {
        // Orion-LD URL
        String orionLdURL = orionLdproperties.getOrionLdDomain() + orionLdproperties.getOrionLdSubscriptionsPath();
        log.debug("URL {}", orionLdURL);
        // Parse subscription to JSON String object.
        String requestBody = new ObjectMapper().writeValueAsString(subscriptionDTO);
        log.debug("Posting subscription to Context Broker: {}", requestBody);
        // Post subscription to Context Broker
        applicationUtils.postRequest(orionLdURL, requestBody);
        log.debug("Subscription created successfully");
    }

    private void updateSubscription(SubscriptionDTO subscriptionDTO) throws JsonProcessingException {
        String orionLdURL = orionLdproperties.getOrionLdDomain() + orionLdproperties.getOrionLdSubscriptionsPath() + "/" + subscriptionDTO.getId();
        log.debug("URL {}", orionLdURL);

        String requestBody = new ObjectMapper().writeValueAsString(subscriptionDTO);
        log.debug("Updating subscription in Context Broker: {}", requestBody);

        applicationUtils.patchRequest(orionLdURL, requestBody);
        log.debug("Subscription updated successfully");
    }

}
