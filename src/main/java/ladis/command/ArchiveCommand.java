package ladis.command;

import java.io.IOException;

import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.Task;
import ladis.task.TaskList;
import ladis.ui.UI;

/**
 * Command to archive a task from the list.
 * Archived tasks are moved to a separate archive file and removed from the main list.
 */
public class ArchiveCommand extends Command {
    private final int index;

    /**
     * Constructs an ArchiveCommand with the specified task index.
     *
     * @param index The zero-indexed position of the task to archive.
     */
    public ArchiveCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        if (index < 0 || index >= tasks.size()) {
            throw new LadisException("Very funny. Now give me a valid task number.");
        }
        Task archived = tasks.removeTask(index);
        try {
            storage.saveArchived(archived);
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showWarning("Could not save archived task to disk.");
        }
        ui.showTaskArchived(archived.toString(), tasks.size());
        return false;
    }
}
