package app.mongo.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String exception) {
        super(exception);
    }
}
