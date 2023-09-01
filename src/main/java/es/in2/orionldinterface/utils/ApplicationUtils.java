package es.in2.orionldinterface.utils;

import es.in2.orionldinterface.exception.RequestErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class ApplicationUtils {

    public String getRequest(String url) {

        // Create request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send request asynchronously
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // Verify Response HttpStatus
        checkGetResponse(response);

        return response.thenApply(HttpResponse::body).join();
    }

    private void checkGetResponse(CompletableFuture<HttpResponse<String>> response) {

        String statusCode = response.thenApply(HttpResponse::statusCode).join().toString();
        String body = response.thenApply(HttpResponse::body).join();

        if (statusCode.equals("200")) {
            log.debug("Request successful");
        } else if (statusCode.equals("404")) {
            log.error("Not found");
            throw new NoSuchElementException("Not found: " + body);
        } else {
            log.error("Bad Request");
            throw new RequestErrorException("Bad Request: " + body);
        }
    }

    public void postRequest(String url, String requestBody) {

        // Create request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send request asynchronously
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // Verify Response HttpStatus
        checkResponse(response);
    }

    public void patchRequest(String url, String requestBody) {
        // Create request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))  // Use PATCH method
                .build();

        // Send request asynchronously
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // Verify Response HttpStatus
        checkResponse(response);
    }

    private void checkResponse(CompletableFuture<HttpResponse<String>> response) {
        String statusCode = response.thenApply(HttpResponse::statusCode).join().toString();
        String body = response.thenApply(HttpResponse::body).join();

        switch (statusCode) {
            case "201", "200" -> log.debug("Request successful");
            case "204" -> log.debug("Successful Patch");
            default -> {
                log.error("Bad Request");
                throw new RequestErrorException("Bad Request: " + body);
            }
        }
    }
}
