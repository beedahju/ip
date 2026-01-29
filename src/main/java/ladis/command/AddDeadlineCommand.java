package ladis.command;

import java.io.IOException;
import ladis.task.TaskList;
import ladis.task.Deadline;
import ladis.ui.UI;
import ladis.storage.Storage;
import ladis.exception.LadisException;
import ladis.exception.DateTimeParseException;

public class AddDeadlineCommand extends Command {
    private final String[] parts;

    public AddDeadlineCommand(String[] parts) {
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
