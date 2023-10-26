package es.in2.orionld.controller;

import es.in2.orionld.service.PublishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/publish")
@RequiredArgsConstructor
public class PublishController {

    private final PublishService publishService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void publishOrionLdEntity(@RequestBody String body) {
        log.info(">>> POST /api/v1/entities");
        log.debug(" > Entity received: {}", body);
        publishService.publishEntity(body);
    }

}
