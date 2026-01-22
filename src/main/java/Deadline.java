public class Deadline extends Task {
    private final String deadlineDay;

    public Deadline(String description, String deadlineDay) {
        super(description);
        this.deadlineDay = deadlineDay;
    }

    @Override
    public String toString() {
        return "[D]" + getStatusIcon() + " " + super.toString() + " (by: " + deadlineDay + ")";
    }
}
