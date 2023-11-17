package es.in2.brokeradapter.controller;

import es.in2.brokeradapter.service.DeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/delete")
@RequiredArgsConstructor
public class DeleteController {
    private final DeleteService deleteService;

    @DeleteMapping("/{entityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrionLdEntity(@PathVariable("entityId") String entityId) {
        log.info(">>> DELETE /api/v1/delete/{}", entityId);
        deleteService.deleteEntity(entityId);
        log.debug(" > Entity deleted successfully");
    }
}
