package ladis.command;

import ladis.exception.LadisException;
import ladis.storage.Storage;
import ladis.task.Task;
import ladis.task.TaskList;
import ladis.ui.UI;

/**
 * Command to find tasks matching a given keyword.
 * Displays all tasks whose descriptions contain the search keyword (case-insensitive).
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the given search keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching all tasks and displaying matching results.
     *
     * @param tasks The TaskList to search through.
     * @param ui The UI instance to display results.
     * @param storage The Storage instance (not used for this command).
     * @return false to continue execution (find command does not exit).
     * @throws LadisException If no keyword is provided.
     */
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        StringBuilder results = new StringBuilder("Here are the matching tasks in your list:\n");
        int count = 0;

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                count++;
                results.append(count).append(". ").append(task.toString()).append("\n");
            }
        }

        if (count == 0) {
            ui.showMessage("No matching tasks found.");
        } else {
            ui.showMessage(results.toString());
        }

        return false;
    }
}
