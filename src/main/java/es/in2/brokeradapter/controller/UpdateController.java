package es.in2.brokeradapter.controller;

import es.in2.brokeradapter.service.UpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/update")
@RequiredArgsConstructor
public class UpdateController {

    private final UpdateService updateService;

    @PatchMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchOrionLdEntity(@RequestBody String body) {
        log.info(">>> PATCH /api/v1/entities");
        log.debug(" > Entity received: {}", body);
        updateService.updateEntity(body);
    }

}
