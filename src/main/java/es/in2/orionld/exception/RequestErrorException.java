package es.in2.orionld.exception;

public class RequestErrorException extends RuntimeException {
    public RequestErrorException(String message) {
        super(message);
    }
}
