public class Deadline extends Task {
    private final String deadlineDay;

    public Deadline(String description, String deadlineDay) {
        super(description, TaskType.DEADLINE);
        this.deadlineDay = deadlineDay;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadlineDay + ")";
    }
}
