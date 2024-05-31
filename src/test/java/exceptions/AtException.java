package exceptions;

public class AtException extends RuntimeException {

    public AtException(Throwable cause) {
        super(cause);
    }

    public AtException(String message) {
        super(message);
    }
}
