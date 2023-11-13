package es.in2.brokeradapter.controller;

import es.in2.brokeradapter.service.EntityRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/entities")
@RequiredArgsConstructor
public class EntitiesController {

    private final EntityRetrievalService entityRetrievalService;

    @GetMapping("/{entityId}")
    @ResponseStatus(HttpStatus.OK)
    public String getOrionLdEntity(@PathVariable("entityId") String entityId) {
        log.info(">>> GET /api/v1/entities/{}", entityId);
        return entityRetrievalService.getEntity(entityId);
    }

}
