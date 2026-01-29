package ladis.command;

import java.io.IOException;
import ladis.task.TaskList;
import ladis.ui.UI;
import ladis.storage.Storage;
import ladis.exception.LadisException;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        tasks.markTask(index);
        ui.showTaskMarked(tasks.getTask(index).toString());
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
