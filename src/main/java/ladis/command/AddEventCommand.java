package ladis.command;

import java.io.IOException;
import ladis.task.TaskList;
import ladis.task.Event;
import ladis.ui.UI;
import ladis.storage.Storage;
import ladis.exception.LadisException;
import ladis.exception.DateTimeParseException;

public class AddEventCommand extends Command {
    private final String[] eventParts;

    public AddEventCommand(String[] eventParts) {
        this.eventParts = eventParts;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisException {
        try {
            tasks.addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
            ui.showTaskAdded("[E][ ] " + eventParts[0] + " (from: " + eventParts[1] + " to: " + eventParts[2] + ")", tasks.size());
            saveToStorage(tasks, ui, storage);
        } catch (DateTimeParseException e) {
            throw new LadisException(e.getMessage());
        }
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
