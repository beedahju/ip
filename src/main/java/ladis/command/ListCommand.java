package ladis.command;

import ladis.storage.Storage;
import ladis.task.TaskList;
import ladis.ui.UI;

/**
 * Command to display all tasks in the task list.
 * Shows all tasks to the user including their status and details.
 */
public class ListCommand extends Command {
    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks The task list to display.
     * @param ui The UI to show the task list.
     * @param storage The storage (not used).
     * @return false to continue execution (list command does not exit).
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showList(tasks);
        return false;
    }
}
