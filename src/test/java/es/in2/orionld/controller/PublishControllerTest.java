package es.in2.orionld.controller;

import es.in2.orionld.service.impl.PublishServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PublishControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private PublishServiceImpl publishService;

    @InjectMocks
    private PublishController publishController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(publishController)
                .build();
    }

    @Test
    void publishOrionLdEntityWithValidDataThenReturnStatus200() throws Exception {
        // Define a constant for the valid JSON request
        String jsonRequest = "{"
                + "\"@type\":\"ProductOffering\","
                + "\"id\":\"urn:ngsi-ld:product-offering:1234\","
                + "\"category\":\"B2C product orders\","
                + "\"channel\":[{"
                + "\"id\":\"1\","
                + "\"name\":\"Online channel\""
                + "}],"
                + "\"description\":\"Product Order illustration sample\","
                + "\"externalId\":\"PO-456\""
                + "}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andReturn();

        // Verify that the service method was called and response status is 200 OK
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

    @Test
    void publishOrionLdEntityWithInvalidDataThenReturnStatus400() throws Exception {
        // Define a constant for the invalid JSON request
        String jsonRequest = " ".trim();

            mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/publish").
                            contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest)
            )
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }

}
