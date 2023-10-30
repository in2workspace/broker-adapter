package es.in2.orionld.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.in2.orionld.config.BrokerProperties;
import es.in2.orionld.exception.JsonReadingException;
import es.in2.orionld.service.UpdateService;
import es.in2.orionld.utils.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {

    private final ApplicationUtils applicationUtils;
    private final BrokerProperties brokerProperties;

    @Override
    public void updateEntity(String entity) {
        log.debug(">>> Updating entity...");
        String orionLdURL = brokerProperties.domain() + brokerProperties.paths().entities() +  "/" + extractIdFromEntity(entity) + "/attrs";
        log.debug(" > Orion-LD URL: {}", orionLdURL);
        log.debug(" > Request body: {}", entity);
        applicationUtils.patchRequest(orionLdURL, entity);
        log.debug(" > Entity updated successfully");
    }

    public static String extractIdFromEntity(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            if (jsonNode.has("id")) {
                return jsonNode.get("id").asText();
            } else {
                throw new JsonReadingException("Entity ID field not found");
            }
        } catch (Exception e) {
            throw new JsonReadingException("Error while reading entity JSON");
        }
    }

}
