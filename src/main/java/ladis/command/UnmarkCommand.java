package ladis.command;

import java.io.IOException;

import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.TaskList;
import ladis.ui.UI;

/**
 * Command to mark a task as not done.
 * Marks the specified task as incomplete and persists the change to storage.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Constructs an UnmarkCommand with the specified task index.
     *
     * @param index The zero-indexed position of the task to unmark.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the unmark command by unmarking the task and saving to storage.
     *
     * @param tasks The task list containing the task to unmark.
     * @param ui The UI to display the result.
     * @param storage The storage to persist the change.
     * @return false to continue execution (unmark command does not exit).
     * @throws LadisException If the task index is invalid.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        tasks.unmarkTask(index);
        ui.showTaskUnmarked(tasks.getTask(index).toString());
        saveToStorage(tasks, ui, storage);
        return false;
    }

    /**
     * Saves the task list to storage.
     *
     * @param tasks The task list to save.
     * @param ui The UI to show warnings if saving fails.
     * @param storage The storage to save tasks to.
     */
    private void saveToStorage(TaskList tasks, UI ui, Storage storage) {
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showWarning("Could not save task to disk.");
        }
    }
}
