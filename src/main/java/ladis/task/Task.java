package ladis.task;

/**
 * Abstract base class representing a task in the task list.
 * Provides common properties and methods for all task types (Todo, Deadline, Event).
 */
public abstract class Task {
    protected final String description;
    protected boolean isDone;
    protected final TaskType taskType;

    /**
     * Constructs a Task with the given description and type.
     *
     * @param description The task description.
     * @param taskType The type of task (TODO, DEADLINE, or EVENT).
     */
    public Task(String description, TaskType taskType) {
        this.description = description;
        this.taskType = taskType;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "[X]" if done, "[ ]" if not done.
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        isDone = true;
        assert isDone : "Task should be marked as done after calling mark()";
    }

    /**
     * Marks the task as not done.
     */
    public void unmark() {
        isDone = false;
        assert !isDone : "Task should not be marked as done after calling unmark()";
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the task is marked as done.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the type of this task.
     *
     * @return The TaskType of this task.
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * Converts the task to a file-friendly string format for storage.
     * Format: taskTypeIcon | isDone (0/1) | description
     *
     * @return The file representation of the task.
     */
    public String toFileString() {
        return taskType.getIcon().replaceAll("[\\[\\]]", "") + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the task for display.
     * Format: [taskTypeIcon][status] description
     *
     * @return The display representation of the task.
     */
    @Override
    public String toString() {
        return taskType.getIcon() + getStatusIcon() + " " + description;
    }
}
