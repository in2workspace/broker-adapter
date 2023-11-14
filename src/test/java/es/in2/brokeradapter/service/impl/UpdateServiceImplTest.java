package es.in2.brokeradapter.service.impl;

import es.in2.brokeradapter.config.BrokerPathProperties;
import es.in2.brokeradapter.config.BrokerProperties;
import es.in2.brokeradapter.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UpdateServiceImplTest {

    @Mock
    private ApplicationUtils applicationUtils;

    @Mock
    private BrokerProperties brokerProperties;

    @Mock
    private BrokerPathProperties brokerPathProperties;

    @InjectMocks
    private UpdateServiceImpl updateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateEntity() {
        // Arrange
        String entity = "{\"id\":\"urn:ngsi-ld:Entity:123\",\"type\":\"Entity\",\"name\":\"Sample Entity\"}";
        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);
        when(brokerProperties.internalDomain()).thenReturn("www.example.com");
        Mockito.when(brokerPathProperties.entities()).thenReturn("/api/v1/entities");
        doNothing().when(applicationUtils).patchRequest(any(), any());

        // Act
        updateService.updateEntity(entity);

        // Assert
        verify(brokerProperties, times(1)).internalDomain();
        verify(brokerProperties, times(1)).paths();
    }
}
