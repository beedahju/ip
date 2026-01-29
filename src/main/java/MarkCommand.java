import java.io.IOException;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public boolean execute(TaskList tasks, UI ui, Storage storage) throws LadisExeception {
        tasks.markTask(index);
        ui.showTaskMarked(tasks.getTask(index).toString());
        saveToStorage(tasks, ui, storage);
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
