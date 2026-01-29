import java.io.IOException;

public class AddEventCommand extends Command {
    private final String[] eventParts;

    public AddEventCommand(String[] eventParts) {
        this.eventParts = eventParts;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisExeception {
        try {
            tasks.addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
            ui.showTaskAdded("[E][ ] " + eventParts[0] + " (from: " + eventParts[1] + " to: " + eventParts[2] + ")", tasks.size());
            saveToStorage(tasks, ui, storage);
        } catch (DateTimeParseException e) {
            throw new LadisExeception(e.getMessage());
        }
        return false;
    }

    private void saveToStorage(TaskList tasks, UI ui, Storage storage) {
        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            ui.showWarning("Oops! Something went wrong while saving to disk.");
        }
    }
}
