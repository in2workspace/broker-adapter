package es.in2.orionld.service.impl;

import es.in2.orionld.config.BrokerPathProperties;
import es.in2.orionld.config.BrokerProperties;
import es.in2.orionld.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PublishServiceImplTest {

    @Mock
    private ApplicationUtils applicationUtils;

    @Mock
    private BrokerProperties brokerProperties;

    @Mock
    private BrokerPathProperties brokerPathProperties;

    @InjectMocks
    private PublishServiceImpl publishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPublishEntity() {
        // Arrange
        String entity = "{\"id\":\"urn:ngsi-ld:Entity:123\",\"type\":\"Entity\",\"name\":\"Sample Entity\"}";
        Mockito.when(brokerProperties.domain()).thenReturn("https://example.com");

        // Check if paths is null, and provide a default value if it is
        BrokerPathProperties defaultPaths = new BrokerPathProperties("/api/v1/entities", "/api/v1/subscriptions");
        Mockito.when(brokerProperties.paths()).thenReturn(defaultPaths);

        Mockito.when(brokerPathProperties.entities()).thenReturn("/api/v1/entities");
        doNothing().when(applicationUtils).postRequest(anyString(), anyString());

        // Act
        publishService.publishEntity(entity);

        // Assert
        verify(brokerProperties, times(1)).domain();
        verify(applicationUtils, times(1)).postRequest("https://example.com/api/v1/entities", entity);
    }
}
