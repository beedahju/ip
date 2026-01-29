import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public void markTask(int index) throws LadisExeception {
        if (isValidIndex(index)) {
            tasks.get(index).mark();
        } else {
            throw new LadisExeception("Very funny. Now give me a valid task number.");
        }
    }

    public void unmarkTask(int index) throws LadisExeception {
        if (isValidIndex(index)) {
            tasks.get(index).unmark();
        } else {
            throw new LadisExeception("Very funny. Now give me a valid task number.");
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
