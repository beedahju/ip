public class Deadline extends Task {
    private final String deadlineDay;

    public Deadline(String description, String deadlineDay) {
        super(description, TaskType.DEADLINE);
        this.deadlineDay = deadlineDay;
    }

    public String getDeadlineDay() {
        return deadlineDay;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + deadlineDay;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadlineDay + ")";
    }
}
