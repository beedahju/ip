import java.io.IOException;

public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        tasks.addTask(new Todo(description));
        ui.showTaskAdded("[T][ ] " + description, tasks.size());
        saveToStorage(tasks, ui, storage);
        return false;
    }

    private void saveToStorage(TaskList tasks, UI ui, Storage storage) {
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showWarning("Oops! Something went wrong while saving to disk.");
        }
    }
}
