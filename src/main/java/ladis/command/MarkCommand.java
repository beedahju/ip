package ladis.command;

import java.io.IOException;

import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.TaskList;
import ladis.ui.UI;

/**
 * Command to mark a task as done.
 * Marks the specified task as complete and persists the change to storage.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Constructs a MarkCommand with the specified task index.
     *
     * @param index The zero-indexed position of the task to mark as done.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark command by marking the task and saving to storage.
     *
     * @param tasks The task list containing the task to mark.
     * @param ui The UI to display the result.
     * @param storage The storage to persist the change.
     * @return false to continue execution (mark command does not exit).
     * @throws LadisException If the task index is invalid.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        tasks.markTask(index);
        ui.showTaskMarked(tasks.getTask(index).toString());
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
