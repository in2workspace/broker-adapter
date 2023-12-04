package es.in2.brokeradapter.controller;

import es.in2.brokeradapter.service.DeleteService;
import es.in2.brokeradapter.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteControllerTest {
    @Mock
    private MockMvc mockMvc;

    @Mock
    private DeleteService deleteService;

    @Mock
    private ApplicationUtils applicationUtils;

    @InjectMocks
    private DeleteController deleteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(deleteController)
                .build();
    }

    @Test
    void testDeleteRequest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/delete/urn:ngsi-ld:product-offering:1234"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

}
