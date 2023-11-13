package es.in2.brokeradapter.utils;

import es.in2.brokeradapter.exception.RequestErrorException;
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

	public static final String ACCEPT_HEADER = "Accept";
	public static final String TYPE_APPLICATION_JSON = "application/json";
	public static final String CONTENT_TYPE_HEADER = "Content-Type";

	public String getRequest(String url) {
		// Create request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header(ACCEPT_HEADER, TYPE_APPLICATION_JSON)
				.GET()
				.build();
		// Send request asynchronously
		CompletableFuture<HttpResponse<String>> response =
				client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
		// Verify Response HttpStatus
		checkGetResponse(response);
		return response.thenApply(HttpResponse::body).join();
	}

	public void postRequest(String url, String requestBody) {
		// Create request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.headers(CONTENT_TYPE_HEADER, TYPE_APPLICATION_JSON, ACCEPT_HEADER, TYPE_APPLICATION_JSON)
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
				.headers(CONTENT_TYPE_HEADER, TYPE_APPLICATION_JSON, ACCEPT_HEADER, TYPE_APPLICATION_JSON)
				.method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))  // Use PATCH method
				.build();
		// Send request asynchronously
		CompletableFuture<HttpResponse<String>> response =
				client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
		// Verify Response HttpStatus
		checkResponse(response);
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
