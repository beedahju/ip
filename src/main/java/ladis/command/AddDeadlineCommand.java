package ladis.command;

import java.io.IOException;

import ladis.exception.DateTimeParseException;
import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.Deadline;
import ladis.task.TaskList;
import ladis.ui.UI;

/**
 * Command to add a new deadline task.
 */
public class AddDeadlineCommand extends Command {
    private final String[] parts;

    /**
     * Constructs an AddDeadlineCommand with the given parts.
     *
     * @param parts An array containing [description, deadline].
     */
    public AddDeadlineCommand(String... parts) {
        this.parts = parts;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        try {
            tasks.addTask(new Deadline(parts[0], parts[1]));
            ui.showTaskAdded("[D][ ] " + parts[0] + " (by: " + parts[1] + ")", tasks.size());
            saveToStorage(tasks, ui, storage);
        } catch (DateTimeParseException e) {
            throw new LadisException(e.getMessage());
        }
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
