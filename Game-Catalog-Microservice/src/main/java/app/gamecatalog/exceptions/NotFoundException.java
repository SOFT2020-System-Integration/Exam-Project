package app.gamecatalog.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
