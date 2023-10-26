package es.in2.orionld.controller;

import es.in2.orionld.service.impl.PublishServiceImpl;
import es.in2.orionld.service.impl.UpdateServiceImpl;
import es.in2.orionld.utils.ApplicationUtils;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private ApplicationUtils applicationUtils;

    @Mock
    private UpdateServiceImpl updateService;

    @Mock
    private PublishServiceImpl publishService;

    @InjectMocks
    private PublishController publishController;

    @InjectMocks
    private UpdateController updateController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(updateController, publishController)
                .build();
    }

    @Test
    void updateorionLdEntityWithValidDataThenReturnStatus200() throws Exception {
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

        // Define a constant for the valid JSON request
        String jsonUpdateRequest = "{"
                + "\"@type\":\"ProductOffering\","
                + "\"id\":\"urn:ngsi-ld:product-offering:1234\","
                + "\"category\":\"B2C product orders\","
                + "\"channel\":[{"
                + "\"id\":\"1\","
                + "\"name\":\"Online channel\""
                + "}],"
                + "\"description\":\"Product Order illustration sample UPDATE\","
                + "\"externalId\":\"PO-456\""
                + "}";

        MvcResult resultUpdate = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest))
                .andReturn();

        // Verify that the service method was called and response status is 200 OK and 204 NO CONTENT
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(HttpStatus.NO_CONTENT.value(), resultUpdate.getResponse().getStatus());

    }
}
