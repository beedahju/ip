package ladis.task;

import java.time.LocalDate;
import java.time.LocalTime;

import ladis.exception.DateTimeParseException;
import ladis.util.DateTimeParser;

/**
 * Represents a task that occurs over a time period with a start and end date/time.
 * Extends Task to include event-specific date range functionality.
 */
public class Event extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Constructs an Event task by parsing the start and end date/time strings.
     *
     * @param description The task description.
     * @param startDate The event start date and optional time (e.g., "2024-12-25 14:30").
     * @param endDate The event end date and optional time (e.g., "2024-12-26 16:00").
     * @throws DateTimeParseException If the date/time parsing fails.
     */
    public Event(String description, String startDate, String endDate) throws DateTimeParseException {
        super(description, TaskType.EVENT);

        String[] startParts = startDate.trim().split(" ");
        this.startDate = DateTimeParser.parseDate(startParts[0]);
        this.startTime = startParts.length > 1 ? DateTimeParser.parseTime(startParts[1]) : null;

        String[] endParts = endDate.trim().split(" ");
        this.endDate = DateTimeParser.parseDate(endParts[0]);
        this.endTime = endParts.length > 1 ? DateTimeParser.parseTime(endParts[1]) : null;
    }

    /**
     * Constructs an Event task with explicit date and time objects.
     *
     * @param description The task description.
     * @param startDate The event start date.
     * @param endDate The event end date.
     * @param startTime The event start time (can be null).
     * @param endTime The event end time (can be null).
     */
    public Event(String description, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        super(description, TaskType.EVENT);
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toFileString() {
        String result = super.toFileString() + " | " + DateTimeParser.serializeDate(startDate);
        if (startTime != null) {
            result += " " + DateTimeParser.serializeTime(startTime);
        }
        result += " | " + DateTimeParser.serializeDate(endDate);
        if (endTime != null) {
            result += " " + DateTimeParser.serializeTime(endTime);
        }
        return result;
    }

    @Override
    public String toString() {
        String startTimeStr = startTime != null ? ", " + DateTimeParser.formatTime(startTime) : "";
        String endTimeStr = endTime != null ? ", " + DateTimeParser.formatTime(endTime) : "";
        return super.toString() + " (from: " + DateTimeParser.formatDate(startDate)
                + startTimeStr + " to: " + DateTimeParser.formatDate(endDate) + endTimeStr + ")";
    }

}
