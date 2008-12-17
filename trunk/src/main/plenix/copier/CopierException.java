package plenix.copier;

@SuppressWarnings("serial")
public class CopierException extends RuntimeException {
    public CopierException(String message, Throwable cause) {
        super(message, cause);
    }

    public CopierException(String message) {
        super(message);
    }

    public CopierException(Throwable cause) {
        super(cause);
    }
}
