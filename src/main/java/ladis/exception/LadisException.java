package ladis.exception;

/**
 * Exception thrown for Ladis application errors.
 * Provides contextual error messages to guide users toward correct usage.
 *
 * Usage:
 *   throw new LadisException("Invalid task number. Please provide a valid task index.");
 */
public class LadisException extends Exception {
    /**
     * Constructs a LadisException with the given message.
     * The message should be user-friendly and suggest corrective action.
     *
     * @param message The error message describing what went wrong and how to fix it.
     */
    public LadisException(String message) {
        super(message);
    }

    /**
     * Constructs a LadisException with a message and underlying cause.
     * Useful for wrapping other exceptions with application-specific context.
     *
     * @param message The error message describing the Ladis-specific error.
     * @param cause The underlying exception that caused this error.
     */
    public LadisException(String message, Throwable cause) {
        super(message, cause);
    }
}
