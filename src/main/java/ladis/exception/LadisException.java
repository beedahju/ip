package ladis.exception;

/**
 * Exception thrown for Ladis application errors.
 */
public class LadisException extends Exception {
    /**
     * Constructs a LadisException with the given message.
     *
     * @param message The error message.
     */
    public LadisException(String message) {
        super(message);
    }
}
