package es.in2.brokeradapter.service.impl;
import static org.mockito.Mockito.*;

import es.in2.brokeradapter.config.BrokerPathProperties;
import es.in2.brokeradapter.config.BrokerProperties;
import es.in2.brokeradapter.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
public class DeleteServiceImplTest {

    @Mock
    private ApplicationUtils applicationUtils;

    @Mock
    private BrokerProperties brokerProperties;
    @Mock
    private BrokerPathProperties brokerPathProperties;

    @InjectMocks
    private DeleteServiceImpl deleteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteEntity() {
        String entityId = "urn:ngsi-ld:sample-entity";

        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
        when(brokerProperties.internalDomain()).thenReturn("https://example.com");
        Mockito.when(brokerPathProperties.entities()).thenReturn("/api/v1/entities");
        doNothing().when(applicationUtils).deleteRequest(any());


        deleteService.deleteEntity(entityId);

        // Assert
        verify(brokerProperties, times(1)).internalDomain();
        verify(brokerProperties, times(1)).paths();

        // Verify that applicationUtils.deleteRequest was called with the correct URL
        verify(applicationUtils).deleteRequest("https://example.com/api/v1/entities/" + entityId);
    }
}
