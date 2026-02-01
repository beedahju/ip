package ladis.command;

import ladis.storage.Storage;
import ladis.task.TaskList;
import ladis.ui.UI;

/**
 * Command to exit the application.
 * Displays a goodbye message and signals the program to terminate.
 */
public class ExitCommand extends Command {
    /**
     * Executes the exit command by showing a goodbye message.
     *
     * @param tasks The task list (not used).
     * @param ui The UI to display the goodbye message.
     * @param storage The storage (not used).
     * @return true to signal program termination.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showGoodbye();
        return true;
    }
}
