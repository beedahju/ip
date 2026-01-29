public abstract class Task {
    private final String description;
    private boolean isDone;
    private final TaskType taskType;

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

    @Override
    public String toString() {
        return taskType.getIcon() + getStatusIcon() + " " + description;
    }
}
