package es.in2.orionld.controller;

import es.in2.orionld.service.EntityRetrievalService;
import es.in2.orionld.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class EntitiesControllerTest {
    @Mock
    private MockMvc mockMvc;

    @Mock
    private EntityRetrievalService entityRetrievalService;

    @Mock
    private ApplicationUtils applicationUtils;

    @InjectMocks
    private EntitiesController entitiesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(entitiesController)
                .build();
    }

    @Test
    void testGetRequest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/entities/urn:ngsi-ld:product-offering:1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
