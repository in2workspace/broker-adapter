package es.in2.orionldinterface.controller;

import es.in2.orionldinterface.domain.dto.OrionLdSubscriptionRequestDTO;
import es.in2.orionldinterface.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createSubscription(@RequestBody OrionLdSubscriptionRequestDTO orionLdSubscriptionRequestDTO) {
        subscriptionService.createSubscription(orionLdSubscriptionRequestDTO);
    }

}
