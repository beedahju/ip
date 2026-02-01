package ladis.task;

import java.time.LocalDate;
import java.time.LocalTime;

import ladis.exception.DateTimeParseException;
import ladis.util.DateTimeParser;

/**
 * Represents a task with a deadline date and optional time.
 * Extends Task to include deadline-specific functionality.
 */
public class Deadline extends Task {
    private final LocalDate deadlineDay;
    private final LocalTime deadlineTime;

    /**
     * Constructs a Deadline task by parsing the deadline string.
     *
     * @param description The task description.
     * @param deadlineDay The deadline date and optional time as a string (e.g., "2024-12-25 14:30").
     * @throws DateTimeParseException If the date/time parsing fails.
     */
    public Deadline(String description, String deadlineDay) throws DateTimeParseException {
        super(description, TaskType.DEADLINE);
        String[] dateTimeParts = deadlineDay.trim().split(" ");
        this.deadlineDay = DateTimeParser.parseDate(dateTimeParts[0]);
        this.deadlineTime = dateTimeParts.length > 1 ? DateTimeParser.parseTime(dateTimeParts[1]) : null;
    }

    /**
     * Constructs a Deadline task with explicit date and time objects.
     *
     * @param description The task description.
     * @param deadlineDay The deadline date.
     * @param deadlineTime The deadline time (can be null).
     */
    public Deadline(String description, LocalDate deadlineDay, LocalTime deadlineTime) {
        super(description, TaskType.DEADLINE);
        this.deadlineDay = deadlineDay;
        this.deadlineTime = deadlineTime;
    }

    public LocalDate getDeadlineDay() {
        return deadlineDay;
    }

    public LocalTime getDeadlineTime() {
        return deadlineTime;
    }

    @Override
    public String toFileString() {
        String result = super.toFileString() + " | " + DateTimeParser.serializeDate(deadlineDay);
        if (deadlineTime != null) {
            result += " " + DateTimeParser.serializeTime(deadlineTime);
        }
        return result;
    }

    @Override
    public String toString() {
        String timeStr = deadlineTime != null ? ", " + DateTimeParser.formatTime(deadlineTime) : "";
        return super.toString() + " (by: " + DateTimeParser.formatDate(deadlineDay) + timeStr + ")";
    }
}
