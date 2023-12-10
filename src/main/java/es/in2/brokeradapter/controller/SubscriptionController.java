package es.in2.brokeradapter.controller;

import es.in2.brokeradapter.domain.SubscriptionRequest;
import es.in2.brokeradapter.service.SubscriptionService;
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
import static es.in2.brokeradapter.utils.MessageUtils.RESPONSE_CODE_500_DESCRIPTION;

@Slf4j
@RestController
@RequestMapping("/api/v2/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscription Controller", description = "APIs for managing subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a subscription", description = "Creates a new subscription.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_201, description = RESPONSE_CODE_201_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_400, description = RESPONSE_CODE_400_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_401, description = RESPONSE_CODE_401_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_403, description = RESPONSE_CODE_403_DESCRIPTION),
            @ApiResponse(responseCode = RESPONSE_CODE_500, description = RESPONSE_CODE_500_DESCRIPTION)
    })
    public Mono<Void> createSubscription(@RequestBody SubscriptionRequest subscriptionRequest) {
        String processId = UUID.randomUUID().toString();
        return subscriptionService.createSubscription(processId, subscriptionRequest);
    }

}
