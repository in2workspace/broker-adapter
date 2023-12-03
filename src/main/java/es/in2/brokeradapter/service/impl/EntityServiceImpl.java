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

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {

    private final ObjectMapper objectMapper;
    private final BrokerProperties brokerProperties;

    @Override
    public Mono<Void> postEntity(String processId, String requestBody) {
        String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().entities();
        List<Map.Entry<String, String>> headers = List.of(
                new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_LD));
        return postRequest(processId, brokerURL, headers, requestBody)
                .doOnSuccess(result -> log.info("ProcessId: {}, Resource created successfully", processId))
                .doOnError(e -> log.error("Error while creating resource: {}", e.getMessage()));
    }

    @Override
    public Mono<String> getEntityById(String processId, String entityId) {
        String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().entities() + "/" + entityId;
        List<Map.Entry<String, String>> headers = List.of(new AbstractMap.SimpleEntry<>(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE));
        return getRequest(processId, brokerURL, headers)
                .doOnSuccess(result -> log.info("ProcessId: {}, Resource retrieved successfully", processId))
                .doOnError(e -> log.error("Error while retrieving resource: {}", e.getMessage()));
    }

    @Override
    public Mono<Void> updateEntity(String processId, String requestBody) {
        return getEntityIdFromRequestBody(processId, requestBody)
                .flatMap(entityId -> {
                    String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().entities() + "/" + entityId + "/attrs";
                    List<Map.Entry<String, String>> headers = List.of(new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_LD));
                    return patchRequest(processId, brokerURL, headers, requestBody);
                })
                .doOnSuccess(result -> log.info("ProcessId: {}, Resource updated successfully", processId))
                .doOnError(e -> log.error("Error while updating resource: {}", e.getMessage()));
    }

    Mono<String> getEntityIdFromRequestBody(String processId, String requestBody) {
        try {
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            if (jsonNode.has("id")) {
                return Mono.just(jsonNode.get("id").asText());
            } else {
                log.error("ProcessId: {}, Entity ID field not found", processId);
                throw new JsonReadingException("Entity ID field not found");
            }
        } catch (Exception e) {
            log.error("ProcessId: {}, Error while reading entity JSON: {}", processId, e.getMessage());
            throw new JsonReadingException("Error while reading entity JSON");
        }
    }

    @Override
    public Mono<Void> deleteEntityById(String processId, String entityId) {
        String brokerURL = brokerProperties.internalDomain() + brokerProperties.paths().entities() + "/" + entityId;
        List<Map.Entry<String, String>> headers = List.of(new AbstractMap.SimpleEntry<>(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_LD));
        return deleteRequest(processId, brokerURL, headers)
                .doOnSuccess(result -> log.info("ProcessId: {}, Resource deleted successfully", processId))
                .doOnError(e -> log.error("Error while deleting resource: {}", e.getMessage()));
    }

}
