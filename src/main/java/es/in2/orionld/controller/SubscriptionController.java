package es.in2.orionld.controller;

import es.in2.orionld.model.SubscriptionRequestDTO;
import es.in2.orionld.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscribe")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createSubscription(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
        subscriptionService.processSubscriptionRequest(subscriptionRequestDTO);
    }

}
