package ladis.command;

import java.io.IOException;

import ladis.storage.Storage;
import ladis.task.TaskList;
import ladis.task.Todo;
import ladis.ui.UI;

/**
 * Command to add a new todo task.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Constructs an AddTodoCommand with the given description.
     *
     * @param description The description of the todo task.
     */
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
            ui.showWarning("Could not save task to disk.");
        }
    }
}
