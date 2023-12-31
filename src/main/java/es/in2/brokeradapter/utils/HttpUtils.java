package es.in2.brokeradapter.utils;

import es.in2.brokeradapter.exception.EntityAlreadyExistException;
import es.in2.brokeradapter.exception.ForbiddenAccessException;
import es.in2.brokeradapter.exception.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static es.in2.brokeradapter.utils.MessageUtils.*;

@Slf4j
public class HttpUtils {

    private HttpUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final WebClient WEB_CLIENT = WebClient.builder().build();
    public static final String APPLICATION_JSON_LD = "application/ld+json";

    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    public static Mono<Void> postRequest(String processId, String url, List<Map.Entry<String, String>> headers, String requestBody) {
        log.debug("ProcessId: {}, POST request to: {}", processId, url);
        log.debug("ProcessId: {}, POST request headers: {}", processId, headers);
        log.debug("ProcessId: {}, POST request body: {}", processId, requestBody);
        return WEB_CLIENT.post()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(entry -> httpHeaders.add(entry.getKey(), entry.getValue())))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status != null && status.is4xxClientError(),
                        clientResponse -> check4xxResponse(processId, clientResponse))
                .onStatus(status -> status != null && status.is5xxServerError(),
                        clientResponse -> check5xxResponse(processId, clientResponse))
                .bodyToMono(Void.class);
    }

    public static Mono<String> getRequest(String processId, String url, List<Map.Entry<String, String>> headers) {
        log.debug("ProcessId: {}, GET request to: {}", processId, url);
        log.debug("ProcessId: {}, GET request headers: {}", processId, headers);
        return WEB_CLIENT.get()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(entry -> httpHeaders.add(entry.getKey(), entry.getValue())))
                .retrieve()
                .onStatus(status -> status != null && status.is4xxClientError(),
                        clientResponse -> check4xxResponse(processId, clientResponse))
                .onStatus(status -> status != null && status.is5xxServerError(),
                        clientResponse -> check5xxResponse(processId, clientResponse))
                .bodyToMono(String.class);
    }

    public static Mono<Void> patchRequest(String processId, String url, List<Map.Entry<String, String>> headers, String requestBody) {
        log.debug("ProcessId: {}, PATCH request to: {}", processId, url);
        log.debug("ProcessId: {}, PATCH request headers: {}", processId, headers);
        log.debug("ProcessId: {}, PATCH request body: {}", processId, requestBody);
        return WEB_CLIENT.patch()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(entry -> httpHeaders.add(entry.getKey(), entry.getValue())))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status != null && status.is4xxClientError(),
                        clientResponse -> check4xxResponse(processId, clientResponse))
                .onStatus(status -> status != null && status.is5xxServerError(),
                        clientResponse -> check5xxResponse(processId, clientResponse))
                .bodyToMono(Void.class);
    }

    public static Mono<Void> deleteRequest(String processId, String url, List<Map.Entry<String, String>> headers) {
        log.debug("ProcessId: {}, DELETE request to: {}", processId, url);
        log.debug("ProcessId: {}, DELETE request headers: {}", processId, headers);
        return WEB_CLIENT.delete()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(entry -> httpHeaders.add(entry.getKey(), entry.getValue())))
                .retrieve()
                .onStatus(status -> status != null && status.is4xxClientError(),
                        clientResponse -> check4xxResponse(processId, clientResponse))
                .onStatus(status -> status != null && status.is5xxServerError(),
                        clientResponse -> check5xxResponse(processId, clientResponse))
                .bodyToMono(Void.class);
    }

    private static Mono<? extends Throwable> check4xxResponse(String processId, ClientResponse clientResponse) {
        if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
            log.warn(RESOURCE_NOT_FOUND_MESSAGE, processId);
            return Mono.error(new NoSuchElementException("Error during request: Not Found - Status: " + clientResponse.statusCode()));
        } else if (clientResponse.statusCode() == HttpStatus.UNAUTHORIZED) {
            log.warn(UNAUTHORIZED_ACCESS_MESSAGE, processId);
            return Mono.error(new UnauthorizedAccessException("Error during request: Unauthorized - Status: " + clientResponse.statusCode()));
        } else if (clientResponse.statusCode() == HttpStatus.FORBIDDEN) {
            log.warn(ACCESS_FORBIDDEN_MESSAGE, processId);
            return Mono.error(new ForbiddenAccessException("Error during request: Forbidden - Status: " + clientResponse.statusCode()));
        } else if (clientResponse.statusCode() == HttpStatus.CONFLICT) {
            log.warn(ENTITY_ALREADY_EXIST_MESSAGE, processId);
            return Mono.error(new EntityAlreadyExistException("Error during request: Conflict - Status: " + clientResponse.statusCode()));
        } else {
            log.error(ERROR_DURING_REQUEST_MESSAGE, processId, clientResponse.statusCode());
            return Mono.error(new RuntimeException("Error during request: " + clientResponse.statusCode()));
        }
    }

    private static Mono<? extends Throwable> check5xxResponse(String processId, ClientResponse clientResponse) {
        log.error("ProcessId: {}, Server error during get request: {}", processId, clientResponse.statusCode());
        return Mono.error(new RuntimeException("Server error during get request:" + clientResponse.statusCode()));
    }

}
