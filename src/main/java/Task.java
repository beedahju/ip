public abstract class Task {
    protected final String description;
    protected boolean isDone;
    protected final TaskType taskType;

    public Task(String description, TaskType taskType) {
        this.description = description;
        this.taskType = taskType;
        this.isDone = false;    
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String toFileString() {
        return taskType.getIcon().replaceAll("[\\[\\]]", "") + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return taskType.getIcon() + getStatusIcon() + " " + description;
    }
}
