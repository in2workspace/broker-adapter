package es.in2.orionldinterface.controller;


import es.in2.orionldinterface.service.EntityPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/publisher")
@RequiredArgsConstructor
public class EntityPublisherController {

    private final EntityPublisherService entityPublisherService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void recieveEvent(@RequestBody String body) {
        log.debug("Entity received: {}", body);
        entityPublisherService.publishEntity(body);

    }

}
