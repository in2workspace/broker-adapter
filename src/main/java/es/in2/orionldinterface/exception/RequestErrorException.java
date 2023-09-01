package es.in2.orionldinterface.exception;

public class RequestErrorException extends RuntimeException {
    public RequestErrorException(String message) {
        super(message);
    }
}
