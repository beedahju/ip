package ladis.task;

import java.util.ArrayList;

import ladis.exception.LadisException;

/**
 * Represents a list of tasks.
 * Provides operations to add, remove, retrieve, and manage tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList initialized with a given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the task list by index.
     *
     * @param index The zero-indexed position of the task to remove.
     * @return The removed task.
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list by index.
     *
     * @param index The zero-indexed position of the task.
     * @return The task at the given index.
     */
    public Task getTask(int index) {
        Task task = tasks.get(index);
        assert task != null : "Retrieved task should never be null";
        return task;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        int size = tasks.size();
        assert size >= 0 : "Task list size should never be negative";
        return size;
    }

    /**
     * Marks a task as done by index.
     *
     * @param index The zero-indexed position of the task to mark.
     * @throws LadisException If the index is invalid.
     */
    public void markTask(int index) throws LadisException {
        if (isValidIndex(index)) {
            Task task = tasks.get(index);
            assert task != null : "Task should exist at valid index";
            task.mark();
            assert task.isDone() : "Task should be marked as done after calling mark()";
        } else {
            throw new LadisException("Very funny. Now give me a valid task number.");
        }
    }

    /**
     * Marks a task as not done by index.
     *
     * @param index The zero-indexed position of the task to unmark.
     * @throws LadisException If the index is invalid.
     */
    public void unmarkTask(int index) throws LadisException {
        if (isValidIndex(index)) {
            Task task = tasks.get(index);
            assert task != null : "Task should exist at valid index";
            task.unmark();
            assert !task.isDone() : "Task should not be marked as done after calling unmark()";
        } else {
            throw new LadisException("Very funny. Now give me a valid task number.");
        }
    }

    /**
     * Checks if the given index is valid for this task list.
     *
     * @param index The index to validate.
     * @return true if the index is valid, false otherwise.
     */
    private boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Returns the underlying task list.
     *
     * @return The ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
