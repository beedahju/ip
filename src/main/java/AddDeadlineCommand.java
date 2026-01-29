import java.io.IOException;

public class AddDeadlineCommand extends Command {
    private final String[] parts;

    public AddDeadlineCommand(String[] parts) {
        this.parts = parts;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisExeception {
        try {
            tasks.addTask(new Deadline(parts[0], parts[1]));
            ui.showTaskAdded("[D][ ] " + parts[0] + " (by: " + parts[1] + ")", tasks.size());
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
