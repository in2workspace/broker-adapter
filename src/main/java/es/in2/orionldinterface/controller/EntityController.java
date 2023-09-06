package es.in2.orionldinterface.controller;

import es.in2.orionldinterface.service.OrionLdPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/entities")
@RequiredArgsConstructor
public class EntityController {

    private final OrionLdPublisherService orionLdPublisherService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void publishOrionLdEntity(@RequestBody String body) {
        log.debug("Entity received: {}", body);
        orionLdPublisherService.publishEntity(body);
    }

}
