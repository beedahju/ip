package ladis.command;

import ladis.task.TaskList;
import ladis.ui.UI;
import ladis.storage.Storage;

public class ExitCommand extends Command {
    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) {
        ui.showGoodbye();
        return true;
    }
}
