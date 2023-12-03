package es.in2.brokeradapter.controller;

import es.in2.brokeradapter.service.EntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntityControllerTests {

    @Mock
    private EntityService entityService;

    @InjectMocks
    private EntityController entityController;

    // Arrange
    private final String entity = "{"
            + "\"@type\":\"ProductOffering\","
            + "\"id\":\"urn:ngsi-ld:product-offering:12345678\","
            + "\"category\":\"B2C product orders\","
            + "\"channel\":[{"
            + "\"id\":\"1\","
            + "\"name\":\"Online channel\""
            + "}],"
            + "\"description\":\"Product Order illustration sample\","
            + "\"externalId\":\"PO-456\""
            + "}";
    private final String entityId = "urn:ngsi-ld:product-offering:12345678";

    @Test
    void testCreateEntity() {
        when(entityService.postEntity(anyString(), anyString())).thenReturn(Mono.empty());
        // Act & Assert
        WebTestClient
                .bindToController(entityController)
                .build()
                .post()
                .uri("/api/v1/entities")
                .bodyValue(entity)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testGetEntityById() {
        when(entityService.getEntityById(anyString(), anyString())).thenReturn(Mono.just(entity));
        // Act & Assert
        WebTestClient
                .bindToController(entityController)
                .build()
                .get()
                .uri("/api/v1/entities/{entityId}", entityId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(entity);
    }

    @Test
    void testUpdateEntity() {
        // Arrange
        when(entityService.updateEntity(anyString(), anyString())).thenReturn(Mono.empty());
        // Act & Assert
        WebTestClient
                .bindToController(entityController)
                .build()
                .patch()
                .uri("/api/v1/entities")
                .bodyValue(entity)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteEntityById() {
        // Arrange
        when(entityService.deleteEntityById(anyString(), anyString())).thenReturn(Mono.empty());
        // Act & Assert
        WebTestClient
                .bindToController(entityController)
                .build()
                .delete()
                .uri("/api/v1/entities/{entityId}", entityId)
                .exchange()
                .expectStatus().isNoContent();
    }

}
