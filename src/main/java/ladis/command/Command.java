package ladis.command;

import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.TaskList;
import ladis.ui.UI;

/**
 * Abstract base class for all commands.
 * All command types extend this class to implement the execute method.
 */
public abstract class Command {
    /**
     * Executes the command with access to the task list, UI, and storage.
     *
     * @param tasks The task list to operate on.
     * @param ui The UI to display output.
     * @param storage The storage to persist changes.
     * @return true if this command is an exit command (terminates the program), false otherwise.
     * @throws LadisException If an error occurs during command execution.
     */
    public abstract boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException;
}
