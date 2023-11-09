/*package es.in2.orionld.service.impl;

import es.in2.orionld.config.BrokerProperties;
import es.in2.orionld.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UpdateServiceImplTest {

    @Mock
    private ApplicationUtils applicationUtils;

    @Mock
    private BrokerProperties brokerProperties;

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
        when(brokerProperties.domain()).thenReturn("https://example.com");
        when(brokerProperties.paths().entities()).thenReturn("/entities");
        doNothing().when(applicationUtils).patchRequest(any(), any());

        // Act
        updateService.updateEntity(entity);

        // Assert
        verify(brokerProperties, times(1)).domain();
        verify(brokerProperties, times(1)).paths();
    }
}
*/