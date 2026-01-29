import java.time.LocalDate;
import java.time.LocalTime;

public class Deadline extends Task {
    private final LocalDate deadlineDay;
    private final LocalTime deadlineTime;

    public Deadline(String description, String deadlineDay) throws DateTimeParseException {
        super(description, TaskType.DEADLINE);
        String[] dateTimeParts = deadlineDay.trim().split(" ");
        this.deadlineDay = DateTimeParser.parseDate(dateTimeParts[0]);
        this.deadlineTime = dateTimeParts.length > 1 ? DateTimeParser.parseTime(dateTimeParts[1]) : null;
    }

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
