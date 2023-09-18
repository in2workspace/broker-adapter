package es.in2.orionld.controller;


import es.in2.orionld.service.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
class SubscriptionControllerTest {

     MockMvc mockMvc;

    @Mock
    SubscriptionServiceImpl subscriptionService;

    @InjectMocks
     SubscriptionController subscriptionController;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController)
                .setControllerAdvice(new SubscriptionExceptionHandler())
                .build();
    }

    @Test
    void createSubscriptionWithValidDataThenReturnStatus200() throws Exception {
        // Define a constant for the valid JSON request
        String jsonRequest = "{"
                + "\"id\":\"yourId\","
                + "\"type\":\"yourType\","
                + "\"notification-endpoint-uri\":\"https://example.com/endpoint\","
                + "\"entities\":[\"entity1\", \"entity2\"]"
                + "}";

        // Perform the POST request to the controller
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        // Verify that the service method was called and response status is 200 OK
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }
    @Test
    void createSubscriptionWithInvalidDataThenReturnStatus400() throws Exception {
        // Define a constant for the invalid JSON request
        String jsonRequest = "{ ... }";

        // Perform the POST request to the controller
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        // Verify that the service method was called and response status is 400 Bad Request
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

}
