package ladis.command;

import java.io.IOException;
import ladis.task.TaskList;
import ladis.ui.UI;
import ladis.storage.Storage;
import ladis.exception.LadisException;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        tasks.unmarkTask(index);
        ui.showTaskUnmarked(tasks.getTask(index).toString());
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
