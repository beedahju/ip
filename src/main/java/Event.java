public class Event extends Task {
    private final String startDate;
    private final String endDate;

    public Event(String description, String startDate, String endDate) {
        super(description, TaskType.EVENT);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + startDate + " | " + endDate;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + startDate + " to: " + endDate + ")";
    }

}
