package es.in2.brokeradapter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.brokeradapter.configuration.properties.BrokerProperties;
import es.in2.brokeradapter.exception.JsonReadingException;
import es.in2.brokeradapter.service.EntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static es.in2.brokeradapter.utils.HttpUtils.*;
import static es.in2.brokeradapter.utils.MessageUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {

    private final ObjectMapper objectMapper;
    private final BrokerProperties brokerProperties;

    @Override
    public Mono<Void> postEntity(String processId, String requestBody) {
        String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().entities();
        log.debug(BROKER_URL_VALUE_MESSAGE, processId, brokerURL);
        List<Map.Entry<String, String>> headers = List.of(
                new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_LD));
        return postRequest(processId, brokerURL, headers, requestBody)
                .doOnSuccess(result -> log.info(RESOURCE_CREATED_MESSAGE, processId))
                .doOnError(e -> log.error(ERROR_CREATING_RESOURCE_MESSAGE, e.getMessage()));
    }

    @Override
    public Mono<String> getEntityById(String processId, String entityId) {
        String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().entities() + "/" + entityId;
        log.debug(BROKER_URL_VALUE_MESSAGE, processId, brokerURL);
        List<Map.Entry<String, String>> headers = List.of(new AbstractMap.SimpleEntry<>(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE));
        return getRequest(processId, brokerURL, headers)
                .doOnSuccess(result -> log.info(RESOURCE_RETRIEVED_MESSAGE, processId))
                .doOnError(e -> log.error(ERROR_RETRIEVING_RESOURCE_MESSAGE, e.getMessage()));
    }

    @Override
    public Mono<Void> updateEntity(String processId, String requestBody) {
        return getEntityIdFromRequestBody(processId, requestBody)
                .flatMap(entityId -> {
                    String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().entities() + "/" + entityId + "/attrs";
                    log.debug(BROKER_URL_VALUE_MESSAGE, processId, brokerURL);
                    List<Map.Entry<String, String>> headers = List.of(new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_LD));
                    return patchRequest(processId, brokerURL, headers, requestBody);
                })
                .doOnSuccess(result -> log.info(RESOURCE_UPDATED_MESSAGE, processId))
                .doOnError(e -> log.error(ERROR_UPDATING_RESOURCE_MESSAGE, e.getMessage()));
    }

    private Mono<String> getEntityIdFromRequestBody(String processId, String requestBody) {
        try {
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            if (jsonNode.has("id")) {
                return Mono.just(jsonNode.get("id").asText());
            } else {
                log.error(ENTITY_ID_NOT_FOUND_ERROR_MESSAGE, processId);
                throw new JsonReadingException("Entity ID field not found");
            }
        } catch (Exception e) {
            log.error(READING_JSON_ENTITY_ERROR_MESSAGE, processId, e.getMessage());
            throw new JsonReadingException(e.getMessage());
        }
    }

    @Override
    public Mono<Void> deleteEntityById(String processId, String entityId) {
        String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().entities() + "/" + entityId;
        log.debug(BROKER_URL_VALUE_MESSAGE, processId, brokerURL);
        List<Map.Entry<String, String>> headers = List.of(new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_LD));
        return deleteRequest(processId, brokerURL, headers)
                .doOnSuccess(result -> log.info(RESOURCE_DELETED_MESSAGE, processId))
                .doOnError(e -> log.error(ERROR_DELETING_RESOURCE_MESSAGE, e.getMessage()));
    }

}
