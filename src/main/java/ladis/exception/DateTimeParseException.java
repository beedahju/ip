package ladis.exception;

/**
 * Exception thrown when date/time parsing fails.
 */
public class DateTimeParseException extends LadisException {
    /**
     * Constructs a DateTimeParseException with the given message.
     *
     * @param message The error message.
     */
    public DateTimeParseException(String message) {
        super(message);
    }
}
