package es.in2.orionld.service.impl;

import es.in2.orionld.config.ApplicationProperties;
import es.in2.orionld.utils.ApplicationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PublishServiceImplTest {

    @Mock
    private ApplicationUtils applicationUtils;

    @Mock
    private ApplicationProperties applicationProperties;

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
        when(applicationProperties.getOrionLdDomain()).thenReturn("https://example.com");
        when(applicationProperties.getOrionLdEntitiesPath()).thenReturn("/entities");
        doNothing().when(applicationUtils).postRequest(anyString(), anyString());

        // Act
        publishService.publishEntity(entity);

        // Assert
        verify(applicationProperties, times(1)).getOrionLdDomain();
        verify(applicationProperties, times(1)).getOrionLdEntitiesPath();
        verify(applicationUtils, times(1)).postRequest("https://example.com/entities", entity);
    }
}
