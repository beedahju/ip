package ladis.task;

/**
 * Represents a simple todo task without any date/time constraints.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The task description.
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
