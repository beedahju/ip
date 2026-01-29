package ladis.command;

import java.io.IOException;
import ladis.task.Task;
import ladis.task.TaskList;
import ladis.ui.UI;
import ladis.storage.Storage;
import ladis.exception.LadisException;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        if (index < 0 || index >= tasks.size()) {
            throw new LadisException("Very funny. Now give me a valid task number.");
        }
        Task removed = tasks.removeTask(index);
        ui.showTaskRemoved(removed.toString(), tasks.size());
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
