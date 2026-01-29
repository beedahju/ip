package ladis.command;

import ladis.task.TaskList;
import ladis.ui.UI;
import ladis.storage.Storage;
import ladis.exception.LadisException;

public abstract class Command {
    /**
     * Execute the command
     * @return true if the command is an exit command, false otherwise
     */
    public abstract boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException;
}
