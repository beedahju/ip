package ladis.task;

/**
 * Enumeration of task types with their corresponding display icons.
 */
public enum TaskType {
    /**
     * Simple todo task.
     */
    TODO("[T]"),
    /**
     * Task with a deadline.
     */
    DEADLINE("[D]"),
    /**
     * Task that spans a time period.
     */
    EVENT("[E]");

    private final String icon;

    /**
     * Constructs a TaskType with the given icon string.
     *
     * @param icon The display icon for this task type.
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Returns the icon string for this task type.
     *
     * @return The task type icon.
     */
    public String getIcon() {
        return this.icon;
    }
}
