package es.in2.brokeradapter.utils;

import es.in2.brokeradapter.exception.ForbiddenAccessException;
import es.in2.brokeradapter.exception.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class HttpUtils {

    private static final WebClient WEB_CLIENT = WebClient.builder().build();
    public static final String APPLICATION_JSON_LD = "application/ld+json";

    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    public static Mono<Void> postRequest(String processId, String url, List<Map.Entry<String, String>> headers, String requestBody) {
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
            log.warn("ProcessId: {}, Resource not found", processId);
            return Mono.error(new NoSuchElementException("Error during get request: " + clientResponse.statusCode()));
        } else if (clientResponse.statusCode() == HttpStatus.UNAUTHORIZED) {
            log.warn("ProcessId: {}, Unauthorized access", processId);
            return Mono.error(new UnauthorizedAccessException("Error during get request: Unauthorized"));
        } else if (clientResponse.statusCode() == HttpStatus.FORBIDDEN) {
            log.warn("ProcessId: {}, Access forbidden", processId);
            return Mono.error(new ForbiddenAccessException("Error during get request: Forbidden"));
        } else {
            log.error("ProcessId: {}, Error during get request: {}", processId, clientResponse.statusCode());
            return Mono.error(new RuntimeException("Error during get request:" + clientResponse.statusCode()));
        }
    }

    private static Mono<? extends Throwable> check5xxResponse(String processId, ClientResponse clientResponse) {
        log.error("ProcessId: {}, Server error during get request: {}", processId, clientResponse.statusCode());
        return Mono.error(new RuntimeException("Server error during get request:" + clientResponse.statusCode()));
    }

}
