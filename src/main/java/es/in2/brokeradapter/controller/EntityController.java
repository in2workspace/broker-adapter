package es.in2.brokeradapter.controller;

import es.in2.brokeradapter.service.EntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static es.in2.brokeradapter.utils.MessageUtils.*;

@Slf4j
@RestController
@RequestMapping("/api/v2/entities")
@RequiredArgsConstructor
@Tag(name = "Entity Controller", description = "APIs for managing entities")
public class EntityController {

    private final EntityService entityService;


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an entity", description = "Creates a new entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_201, description = RESPONSE_CODE_201_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = RESPONSE_CODE_400_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_401, description = RESPONSE_CODE_401_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_403, description = RESPONSE_CODE_403_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = RESPONSE_CODE_500_DESCRIPTION)
    })
    public Mono<Void> createEntity(@RequestBody String requestBody) {
        String processId = UUID.randomUUID().toString();
        return entityService.postEntity(processId, requestBody);
    }


    @GetMapping("/{entityId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get entity by ID", description = "Retrieves an entity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_200, description = RESPONSE_CODE_200_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = RESPONSE_CODE_400_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_401, description = RESPONSE_CODE_401_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_403, description = RESPONSE_CODE_403_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_404, description = RESPONSE_CODE_404_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = RESPONSE_CODE_500_DESCRIPTION)
    })
    public Mono<String> getEntityById(@PathVariable("entityId") String entityId) {
        String processId = UUID.randomUUID().toString();
        return entityService.getEntityById(processId, entityId);
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an entity", description = "Updates an existing entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_204, description = RESPONSE_CODE_204_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = RESPONSE_CODE_400_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_401, description = RESPONSE_CODE_401_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_403, description = RESPONSE_CODE_403_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = RESPONSE_CODE_500_DESCRIPTION)
    })
    public Mono<Void> updateEntity(@RequestBody String requestBody) {
        String processId = UUID.randomUUID().toString();
        return entityService.updateEntity(processId, requestBody);
    }

    @DeleteMapping("/{entityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete entity by ID", description = "Deletes an entity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_204, description = RESPONSE_CODE_204_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = RESPONSE_CODE_400_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_401, description = RESPONSE_CODE_401_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_403, description = RESPONSE_CODE_403_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = RESPONSE_CODE_500_DESCRIPTION)
    })
    public Mono<Void> deleteEntityById(@PathVariable("entityId") String entityId) {
        String processId = UUID.randomUUID().toString();
        return entityService.deleteEntityById(processId, entityId);
    }

}
